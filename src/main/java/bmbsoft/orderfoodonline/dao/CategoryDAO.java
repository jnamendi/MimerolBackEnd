package bmbsoft.orderfoodonline.dao;

import java.util.List;

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

import bmbsoft.orderfoodonline.entities.Category;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.shared.CategoryReq;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "categoryDAO")
@Transactional(rollbackFor = Exception.class)
public class CategoryDAO {
	public static final Logger logger = LoggerFactory.getLogger(CategoryDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ContentDefinitionDAO cds;

	@Autowired
	ContentEntryDAO ces;

	@Autowired
	LanguageDAO ls;

	@Autowired
	MediaDAO ms;

	@Autowired
	private LanguageService languageService;

	public boolean update(Category c) {
		try {
			Session s = sessionFactory.getCurrentSession();
			s.saveOrUpdate(c);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());

			return false;
		}
	}

	public String save(CategoryReq vm, Long id, MultipartFile file) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			if (id != null) {
				Category cat = session.get(Category.class, id);
				if (cat == null) {
					return "99|File not found";
				}

				ContentDefinition cd = cat.getContentDefinition();

				String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
				Long cdId = cd.getContentDepId();
				int d = session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();

				Boolean isF = false;
				String msg = "";

				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								if (cdm.getValue() == null && cdm.getValue().isEmpty()) {
									isF = true;
									msg = "Value is field required. " + cdm.getCode();
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
							msg = "Do not exist language.";
							break;
						}
					}
				}

				if (!isF) {
					cat.setSortOrder(vm.getSortOrder());
					cat.setStatus(vm.getStatus());

					String imageUrl = CommonHelper.doUpload(cat.getImageUrl(), file);
					cat.setImageUrl(imageUrl);

					session.update(cat);

					t.commit();
					return "";
				}
				return "99|" + msg;
			} else {
				// content defi
				ContentDefinition cd = new ContentDefinition();
				cd.setName("Category_Name");
				session.save(cd);

				Category cat = new Category();
				cat.setStatus(vm.getStatus());
				cat.setSortOrder(vm.getSortOrder());
				cat.setContentDefinition(cd);

				String imageUrl = CommonHelper.doUpload(null, file);
				cat.setImageUrl(imageUrl);

				boolean isF = false;
				String msg = "";
				// Language
				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								if (cdm.getValue() == null && cdm.getValue().isEmpty()) {
									isF = true;
									msg = "Value is field required. " + cdm.getCode();
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
							msg = "Do not exist language.";
							break;
						}
					}
				}
				if (!isF) {
					session.save(cat);

					t.commit();
					return "";
				}
				return "99|" + msg;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
			return "99|" + e.getMessage();
		} finally {
			session.close();
		}
	}

	public Category getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Category c = session.get(Category.class, id);
		return c;
		// Session s = null;
		// try {
		// s = this.sessionFactory.openSession();
		// String q = "SELECT l FROM Category l " + " INNER JOIN l.contentDefinition c "
		// + " WHERE l.isDeleted ='false' AND l.categoryId=:id";
		// Query l = s.createQuery(q).setParameter("id", id);
		//
		// Category c = (Category) l.list().get(0);
		//
		// return c;
		// } catch (Exception e) {
		// logger.error(e.getMessage());
		// return null;
		// } finally {
		// s.close();
		// }
	}

	public void delete(final Category Category) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Category);
	}

	public List<Category> getAll(final String codeLang, Integer status) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Category> queryCategory = cb.createQuery(Category.class);
		Root<Category> rootCategory = queryCategory.from(queryCategory.getResultType());
		queryCategory.select(rootCategory).where(toPredicate(rootCategory, cb, null, status, codeLang));
		queryCategory.distinct(rootCategory.get("categoryId") != null);
		queryCategory.orderBy(cb.desc(rootCategory.get("modifiedDate")));
		List<Category> category = session.createQuery(queryCategory).getResultList();
		return category;
	}

	// NHPhong: add new join forPredicate

	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, String name, Integer status, String codeLang) {
		Predicate predicate = cb.conjunction();
		Join<Category, ContentDefinition> definitionJoin = root.join("contentDefinition");
		Join<ContentDefinition, ContentEntry> entryJoin = definitionJoin.join("contentEntries");
		Join<ContentEntry, Language> langJoin = entryJoin.join("language");
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

	public int countGetAll(String title, Integer status, String codeLang) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<Category> countFrom = queryCount.from(Category.class);
		queryCount.select(cb.countDistinct(countFrom));
		queryCount.where(toPredicate(countFrom, cb, title, status, codeLang));
		try {
			Long totalRecord = session.createQuery(queryCount).getSingleResult();
			return totalRecord.intValue();
		} catch (NoResultException e) {
			// Ignore this because as per your logic this is ok!
			return 0;
		}
	}

	public List<Category> getCategoryByLanguage(final int firstResult, final int maxResult, String title,
			Integer status, String codeLang) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Category> queryCategory = cb.createQuery(Category.class);
		Root<Category> rootCategory = queryCategory.from(queryCategory.getResultType());

		queryCategory.select(rootCategory).where(toPredicate(rootCategory, cb, title, status, codeLang));
		queryCategory.distinct(rootCategory.get("categoryId") != null);
		queryCategory.orderBy(cb.desc(rootCategory.get("modifiedDate")));
		List<Category> category = (maxResult == 0) ? session.createQuery(queryCategory).getResultList()
				: session.createQuery(queryCategory).setFirstResult(firstResult).setMaxResults(maxResult)
						.getResultList();
		return category;
	}
}
