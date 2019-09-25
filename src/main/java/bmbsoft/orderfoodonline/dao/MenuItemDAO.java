package bmbsoft.orderfoodonline.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.ExtraItem;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.MenuExtraItem;
import bmbsoft.orderfoodonline.entities.MenuItem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.UserRestaurant;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.ExtraItemRequest;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.MenuExtraItemRequest;
import bmbsoft.orderfoodonline.model.MenuItemRequest;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "menuItemDAO")
@Transactional
public class MenuItemDAO {
	public static final Logger logger = LoggerFactory.getLogger(MenuItemDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LanguageDAO languageDAO;

	public String save(final MenuItemRequest vm, MenuItem e, Menu m, MultipartFile file) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		StringBuilder message = new StringBuilder();
		try {
			ContentDefinition cd = new ContentDefinition();
			cd.setName("Menu_item");
			session.persist(cd);

			MenuItem menuItem = this.convertModelToEntity(vm, e, m);
			menuItem.setContentDefinition(cd);

			String imageUrl = CommonHelper.doUpload(null, file);
			menuItem.setPicturePath(imageUrl);

			session.persist(menuItem);

			List<LanguageViewModel> lvm = vm.getLanguageLst();
			if (lvm != null && lvm.size() > 0) {
				String error = this.insertEntry(session, lvm, cd);
				if (error != null && !error.isEmpty()) {
					transaction.rollback();
					return message.append(error).toString();
				}
			}

			// insert MenuExtraItemRequest
			List<MenuExtraItemRequest> meis = vm.getMenuExtraLst();
			if (meis != null && meis.size() > 0) {
				for (MenuExtraItemRequest mei : meis) {

					ContentDefinition cdMei = new ContentDefinition();
					cdMei.setName("Menu_extral_item");
					session.persist(cdMei);

					MenuExtraItem menuEx = new MenuExtraItem();
					menuEx.setMenuItem(menuItem);
					menuEx.setContentDefinition(cdMei);
					menuEx.setType(mei.getExtraItemType());
					session.persist(menuEx);

					String error = this.insertEntry(session, mei.getLanguageLst(), cdMei);
					if (error != null && !error.isEmpty()) {
						message.append(error);
					}
					if (mei.getExtraItemLst() != null) {
						for (ExtraItemRequest exrq : mei.getExtraItemLst()) {
							ContentDefinition cdEx = new ContentDefinition();
							cdEx.setName("Extra_item");
							session.persist(cdEx);

							ExtraItem ex = new ExtraItem();
							ex.setContentDefinition(cdEx);
							ex.setMenuExtraItem(menuEx);
							ex.setPrice(exrq.getPrice());
							session.persist(ex);
							String error1 = this.insertEntry(session, exrq.getExtraItem(), cdEx);
							if (error1 != null && !error1.isEmpty()) {
								message.append(error1);
							}
						}
					}

				}
			}
			if (!message.toString().isEmpty()) {
				transaction.rollback();
				return message.toString();
			}
			transaction.commit();
			return message.toString();
		} catch (Exception ex) {
			logger.error(ex.toString());
			transaction.rollback();
			return message.append(ex.toString()).toString();
		} finally {
			session.close();
		}
	}

	private String insertEntry(Session session, List<LanguageViewModel> lvm, ContentDefinition cd) {
		if (lvm == null)
			return null;
		String msg = null;
		for (LanguageViewModel langItem : lvm) {
			Language lang = languageDAO.getById(langItem.getLanguageId());
			if (lang != null) {
				// save content entry
				for (ContentDefModel cdm : langItem.getContentDef()) {
					if (cdm.getValue() == null || cdm.getValue().isEmpty()) {
						msg = "Value is field required. " + cdm.getCode();
						break;
					}
					ContentEntry ce = new ContentEntry();
					ce.setCode(cdm.getCode().trim());
					ce.setValue(cdm.getValue().trim());
					ce.setContentDefinition(cd);
					ce.setLanguage(lang);

					session.persist(ce);
				}
			} else {
				msg = " Do not exist language.";
			}
		}
		return msg;
	}

	private void deleteEntryAnDef(Session session, ContentDefinition cd) {
		String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
		Long cdId = cd.getContentDepId();
		session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();

		String deleteContenDef = "DELETE FROM content_definition WHERE content_dep_id=:cd";
		session.createNativeQuery(deleteContenDef).setParameter("cd", cdId).executeUpdate();
	}

	public String update(final MenuItemRequest vm, MenuItem e, Menu m, MultipartFile file) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		StringBuilder message = new StringBuilder();
		try {
			MenuItem menuItem = this.convertModelToEntity(vm, e, m);

			String imageUrl = CommonHelper.doUpload(e.getPicturePath(), file);
			menuItem.setPicturePath(imageUrl);

			session.update(menuItem);

			ContentDefinition cd = e.getContentDefinition();

			List<LanguageViewModel> lvm = vm.getLanguageLst();
			if (lvm != null && lvm.size() > 0) {
				String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
				Long cdId = cd.getContentDepId();
				session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();

				String error = this.insertEntry(session, lvm, cd);
				if (error != null) {
					transaction.rollback();
					return message.append(error).toString();
				}
			}
			// check model request. notEmty delete and create
			List<MenuExtraItemRequest> meirs = vm.getMenuExtraLst();
			if (meirs != null && meirs.size() > 0) {
				// delete multiple table;
				Set<MenuExtraItem> meis = e.getMenuExtraItems();
				if (meis != null && meis.size() > 0) {
					for (MenuExtraItem mei : meis) {

						ContentDefinition meiDef = mei.getContentDefinition();
						if (meiDef != null) {
							// 1:delete table extra_item, content_entry, content_def
							Set<ExtraItem> exs = mei.getExtraItems();
							if (exs != null && exs.size() > 0)
								for (ExtraItem ex : exs) {
									ContentDefinition exDef = ex.getContentDefinition();
									if (exDef != null) {
										String q = "DELETE FROM extra_item WHERE content_dep_id=:cd";
										Long cdId = exDef.getContentDepId();
										session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();
										this.deleteEntryAnDef(session, exDef);
									}
								}
							// 2: delete table MenuExtraItem, content_entry, content_def,
							String q = "DELETE FROM menu_extra_item WHERE content_dep_id=:cd";
							Long cdId = meiDef.getContentDepId();
							session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();
							this.deleteEntryAnDef(session, meiDef);
						}
					}
				}
				// insert MenuExtraItemRequest
				for (MenuExtraItemRequest meir : meirs) {

					ContentDefinition cdMei = new ContentDefinition();
					cdMei.setName("Menu_extral_item");
					session.save(cdMei);

					MenuExtraItem menuEx = new MenuExtraItem();
					menuEx.setMenuItem(menuItem);
					menuEx.setContentDefinition(cdMei);
					menuEx.setType(meir.getExtraItemType());
					session.save(menuEx);

					String error = this.insertEntry(session,
							meir.getLanguageLst() != null ? meir.getLanguageLst() : null, cdMei);
					if (error != null) {
						message.append(error);
					}
					if (meir.getExtraItemLst() != null) {
						for (ExtraItemRequest exrq : meir.getExtraItemLst()) {
							ContentDefinition cdEx = new ContentDefinition();
							cdEx.setName("Extra_item");
							session.persist(cdEx);

							ExtraItem ex = new ExtraItem();
							ex.setContentDefinition(cdEx);
							ex.setMenuExtraItem(menuEx);
							ex.setPrice(exrq.getPrice());
							session.save(ex);
							String error1 = this.insertEntry(session, exrq.getExtraItem(), cdEx);
							if (error1 != null) {
								message.append(error1);
							}
						}
					}

				}
			}
			if (!message.toString().isEmpty()) {
				transaction.rollback();
				return message.toString();
			}
			transaction.commit();
			message.append("");
			return message.toString();
		} catch (

		Exception ex) {
			logger.error(ex.toString());
			transaction.rollback();
			return ex.toString();
		} finally {
			session.close();
		}
	}

	public void delete(final MenuItem menuItem) {
		Session session = this.sessionFactory.getCurrentSession();
		menuItem.setIsStatus(Constant.Status.Deleted.getValue());
		session.update(menuItem);
	}

	public MenuItem getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(MenuItem.class, id);
	}

	public List<MenuItem> getAll(int first, int max, Long menuId, String name, String codeLanguage, Integer status) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MenuItem> query = cb.createQuery(MenuItem.class);
		Root<MenuItem> form = query.from(query.getResultType());
		query.select(form);
		query.where(this.toPredicate(form, cb, menuId, name, codeLanguage, status));
		query.distinct(form.get("menuItemId") != null);
		query.orderBy(cb.desc(form.get("menuItemId")));

		return (max == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(first).setMaxResults(max).getResultList();
	}

	public List<MenuItem> getAllByOwner(int first, int max, String codeLanguage, Integer status, Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MenuItem> query = cb.createQuery(MenuItem.class);
		Root<MenuItem> form = query.from(query.getResultType());
		query.select(form);
		query.where(this.toPredicateOwner(form, cb, codeLanguage, status, userId));
		query.distinct(form.get("menuItemId") != null);
		query.orderBy(cb.desc(form.get("menuItemId")));

		return (max == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(first).setMaxResults(max).getResultList();
	}

	public int countGetAll(Long menuId, String name, String codeLanguage, Integer status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<MenuItem> countFrom = queryCount.from(MenuItem.class);
		queryCount.select(cb.countDistinct(countFrom));
		queryCount.where(toPredicate(countFrom, cb, menuId, name, codeLanguage, status));

		Long totalCount = null;
		try {
			totalCount = session.createQuery(queryCount).getSingleResult();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}
		return totalCount.intValue();
	}

	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, Long menuId, String name, String codeLang,
			Integer status) {
		Predicate predicate = cb.conjunction();
		Join<MenuItem, ContentDefinition> definitionJoin = root.join("contentDefinition");
		Join<ContentDefinition, ContentEntry> entryJoin = definitionJoin.join("contentEntries");
		Join<ContentEntry, Language> langJoin = entryJoin.join("language");
		if (null != menuId && menuId > 0) {
			predicate = cb.and(predicate, cb.equal(root.join("menu").<Long>get("menuId"), menuId));
		}
		if (null != name && !name.isEmpty()) {
			predicate = cb.and(predicate, cb.like(entryJoin.<String>get("value"), "%" + name + "%"));
		}
		if (null != codeLang && !codeLang.isEmpty()) {
			predicate = cb.and(predicate, cb.equal(langJoin.<String>get("code"), codeLang));
		}
		if (null == status) {
			predicate = cb.and(predicate,
					cb.notEqual(root.<Integer>get("isStatus"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("isStatus"), status));
		}
		return predicate;
	}

	private Predicate toPredicateOwner(Root<?> root, CriteriaBuilder cb, String codeLang, Integer status, Long userId) {
		Predicate predicate = cb.conjunction();
		Join<MenuItem, ContentDefinition> definitionJoin = root.join("contentDefinition");
		Join<ContentDefinition, ContentEntry> entryJoin = definitionJoin.join("contentEntries");
		Join<ContentEntry, Language> langJoin = entryJoin.join("language");
		Join<MenuItem, Menu> menu = root.join("menu");
		Join<Menu, Restaurant> restaurant = menu.join("restaurant");
		Join<Restaurant, UserRestaurant> userRestaurant = restaurant.join("userRestaurants");
		if (userId != null) {
			predicate = cb.and(predicate, cb.equal(userRestaurant.join("user").<Long>get("userId"), userId));
		}

		if (null != codeLang && !codeLang.isEmpty()) {
			predicate = cb.and(predicate, cb.equal(langJoin.<String>get("code"), codeLang));
		}
		if (null == status) {
			predicate = cb.and(predicate,
					cb.notEqual(root.<Integer>get("isStatus"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("isStatus"), status));
		}
		return predicate;
	}

	private MenuItem convertModelToEntity(final MenuItemRequest vm, MenuItem e, Menu m) {
		if (e == null) {
			e = new MenuItem();
		}
		e.setMenu(m);
		e.setPrice(vm.getPrice());
		e.setIsCombo(vm.getIsCombo());
		e.setSortOrder(vm.getSortOrder());
		if (vm.getStatus() != null) {
			e.setIsStatus(vm.getStatus());
		} else e.setIsStatus(Constant.Status.Publish.getValue());

		e.setAvailableMonday(vm.getAvailableMonday());
		e.setAvailableTuesday(vm.getAvailableTuesday());
		e.setAvailableWednesday(vm.getAvailableWednesday());
		e.setAvailableThursday(vm.getAvailableThursday());
		e.setAvailableFriday(vm.getAvailableFriday());
		e.setAvailableSaturday(vm.getAvailableSaturday());
		e.setAvailableSunday(vm.getAvailableSunday());
		e.setOutOfStock(vm.getOutOfStock());
		e.setPriority(vm.getPriority());
		return e;
	}
}
