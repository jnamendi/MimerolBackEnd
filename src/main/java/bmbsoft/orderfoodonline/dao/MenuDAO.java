package bmbsoft.orderfoodonline.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.UserRestaurant;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.shared.MenuRequest;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "menuDAO")
@Transactional(rollbackOn = Exception.class)
public class MenuDAO {
	public static final Logger logger = LoggerFactory.getLogger(MenuDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private RestaurantDAO restaurantDAO;
	@Autowired
	private LanguageDAO languageDAO;

	public Menu getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Menu.class, id);
	}

	public Boolean delete(Menu m) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			m.setStatus(Constant.Status.Deleted.getValue());
			m.setModifiedDate(new Date());

			session.saveOrUpdate(m);

			transaction.commit();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}
	}

	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, String name, Integer status, String codeLang,
			Long restaurantId) {
		Predicate predicate = cb.conjunction();
		Join<Menu, ContentDefinition> definitionJoin = root.join("contentDefinition");
		Join<ContentDefinition, ContentEntry> entryJoin = definitionJoin.join("contentEntries");
		Join<ContentEntry, Language> langJoin = entryJoin.join("language");
		if (null != restaurantId && restaurantId > 0) {
			predicate = cb.and(predicate, cb.equal(root.join("restaurant").<Long>get("restaurantId"), restaurantId));
		}
		if (null != name && !name.isEmpty()) {
			predicate = cb.and(predicate, cb.like(entryJoin.<String>get("value"), "%" + name + "%"));
		}
		if (null != codeLang && !codeLang.isEmpty()) {
			predicate = cb.and(predicate, cb.equal(langJoin.<String>get("code"), codeLang));
		}
		if (null == status) {
			predicate = cb.and(predicate, cb.notEqual(root.<Integer>get("status"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("status"), status));
		}
		return predicate;
	}

	public int countGetAll(String title, Integer status, String codeLang, Long restaurantId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<Menu> countFrom = queryCount.from(Menu.class);
		queryCount.select(cb.countDistinct(countFrom));
		queryCount.where(toPredicate(countFrom, cb, title, status, codeLang, restaurantId));
		Long totalCount = null;
		try {
			totalCount = session.createQuery(queryCount).getSingleResult();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}
		return totalCount.intValue();
	}

	public List<Menu> getAll(final int firstResult, final int maxResult, String title, Integer status, String codeLang,
			Long restaurantId, boolean isSort) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Menu> queryMenu = cb.createQuery(Menu.class);
		Root<Menu> rootMenu = queryMenu.from(queryMenu.getResultType());
		queryMenu.select(rootMenu);
		queryMenu.where(toPredicate(rootMenu, cb, title, status, codeLang, restaurantId));
		queryMenu.distinct(rootMenu.get("menuId") != null);
		if (isSort) {
			queryMenu.orderBy(cb.asc(rootMenu.get("sortOrder")));
		} else {
			queryMenu.orderBy(cb.desc(rootMenu.get("modifiedDate")));
		}

		List<Menu> Menus = (maxResult == 0) ? session.createQuery(queryMenu).getResultList()
				: session.createQuery(queryMenu).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
		return Menus;
	}

	public List<Menu> getAll(String codeLang, Long restaurantId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Menu> queryMenu = cb.createQuery(Menu.class);
		Root<Menu> rootMenu = queryMenu.from(queryMenu.getResultType());
		queryMenu.select(rootMenu);
		queryMenu.where(toPredicate(rootMenu, cb, null, Constant.Status.Publish.getValue(), codeLang, restaurantId));
		queryMenu.distinct(rootMenu.get("menuId") != null);
		List<Menu> Menus = session.createQuery(queryMenu).getResultList();
		return Menus;
	}

	public String updateMenu(final MenuRequest menuModel, Menu menu, MultipartFile file) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			StringBuilder message = new StringBuilder();
			menu.setName(menuModel.getName());
			menu.setUrlSlug(CommonHelper.toPrettyURL(menuModel.getName()));
			menu.setModifiedDate(new Date());
			menu.setStatus(menuModel.getStatus());
			menu.setSortOrder(menuModel.getSortOrder());

			String imageUrl = CommonHelper.doUpload(menu.getImageUrl(), file);
			menu.setImageUrl(imageUrl);

			ContentDefinition cd = menu.getContentDefinition();

			String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
			Long cdId = cd.getContentDepId();
			int d = session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();

			boolean isF = false;
			List<LanguageViewModel> lvm = menuModel.getLanguageLst();
			if (lvm != null && lvm.size() > 0) {
				for (LanguageViewModel l : lvm) {
					Language lang = languageDAO.getById(l.getLanguageId());
					if (lang != null) {
						// save content entry
						for (ContentDefModel cdm : l.getContentDef()) {
							if (cdm.getValue() == null && cdm.getValue().isEmpty()) {
								isF = true;
								message.append("Value is field required. " + cdm.getCode());
								break;
							}
							ContentEntry ce = new ContentEntry();
							ce.setCode(cdm.getCode().trim());
							ce.setValue(cdm.getValue().trim());
							ce.setContentDefinition(cd);
							ce.setLanguage(lang);

							session.save(ce);
						}
					} else {
						isF = true;
						message.append("Do not exist language.");
						break;
					}
				}
			}

			if (!isF) {
				session.saveOrUpdate(menu);

				transaction.commit();
				return "";
			}
			return message.toString();
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e.toString());
			return e.toString();
		} finally {
			session.close();
		}
	}

	public String create(final MenuRequest menuModel, Menu menu, MultipartFile file) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		StringBuilder message = new StringBuilder();
		try {
			// content definition
			ContentDefinition cd = new ContentDefinition();
			cd.setName(menuModel.getName());
			session.save(cd);

			menu.setName(menuModel.getName());
			menu.setUrlSlug(CommonHelper.toPrettyURL(menuModel.getName()));
			menu.setCreatedDate(new Date());
			menu.setStatus(menuModel.getStatus());
			menu.setContentDefinition(cd);
			menu.setSortOrder(menuModel.getSortOrder());
			String imageUrl = CommonHelper.doUpload(null, file);
			menu.setImageUrl(imageUrl);

			boolean isF = false;
			List<LanguageViewModel> lvm = menuModel.getLanguageLst();
			if (lvm != null && lvm.size() > 0) {
				for (LanguageViewModel l : lvm) {
					Language lang = languageDAO.getById(l.getLanguageId());
					if (lang != null) {
						// save content entry
						for (ContentDefModel cdm : l.getContentDef()) {
							if (cdm.getValue() == null && cdm.getValue().isEmpty()) {
								isF = true;
								message.append("Value is field required. " + cdm.getCode());
								break;
							}
							ContentEntry ce = new ContentEntry();
							ce.setCode(cdm.getCode().trim());
							ce.setValue(cdm.getValue().trim());
							ce.setContentDefinition(cd);
							ce.setLanguage(lang);

							session.save(ce);
						}
					} else {
						isF = true;
						message.append("Do not exist language.");
						break;
					}
				}
			}

			if (!isF) {
				session.save(menu);

				transaction.commit();
				return "";
			}
			return message.toString();
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e.toString());
			return message.append(e.toString()).toString();
		} finally {
			session.close();
		}
	}

	/**
	 * @param vm
	 * @return new Menu miss ContenDefinition or old Menu updated
	 */
	private Menu convertModelToEntity(final MenuRequest vm) {
		Menu menu = new Menu();
		if (vm.getMenuId() != null && vm.getMenuId() > 0) {
			menu = getById(vm.getMenuId());
			// miss modified by.
			menu.setName(vm.getName());
			menu.setUrlSlug(CommonHelper.toPrettyURL(vm.getName()));
			menu.setModifiedDate(new Date());
			menu.setStatus(vm.getStatus());
			menu.setSortOrder(vm.getSortOrder());
			return menu;
		}
		// new menu object miss filed ContenDefinition
		// miss create by.
		menu.setName(vm.getName());
		menu.setRestaurant(restaurantDAO.getById(vm.getRestaurantId()));
		menu.setUrlSlug(CommonHelper.toPrettyURL(vm.getName()));
		menu.setCreatedDate(new Date());
		menu.setStatus(vm.getStatus());
		menu.setSortOrder(vm.getSortOrder());
		return menu;
	}

	public List<Menu> getMenuByOwner(final int firstResult, final int maxResult, long idOwner) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Menu> queryMenu = cb.createQuery(Menu.class);
		Root<Menu> rootRestaurant = queryMenu.from(Menu.class);

		Join<Menu, Restaurant> menuResJoin = rootRestaurant.join("restaurant");
		Join<Restaurant, UserRestaurant> userResJoin = menuResJoin.join("userRestaurants");

		List<Predicate> predicates = new ArrayList<>();
		// predicates.add(cb.equal(rootRestaurant.get("keySearch"), key));
		predicates.add(cb.equal(rootRestaurant.get("status"), Constant.Status.Publish.getValue()));
		if (idOwner > 0)
			predicates.add(cb.equal(userResJoin.join("user").<Long>get("userId"), idOwner));
		queryMenu.select(rootRestaurant).where(predicates.stream().toArray(Predicate[]::new));

		queryMenu.distinct(rootRestaurant.get("menuId") != null);
		List<Menu> menus = (maxResult == 0) ? session.createQuery(queryMenu).getResultList()
				: session.createQuery(queryMenu).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return menus;
	}
}
