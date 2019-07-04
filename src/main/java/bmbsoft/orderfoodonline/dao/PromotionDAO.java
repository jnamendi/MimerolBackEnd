package bmbsoft.orderfoodonline.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import bmbsoft.orderfoodonline.controller.BaseController;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Promotion;
import bmbsoft.orderfoodonline.entities.PromotionLineitem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.UserRestaurant;
import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.entities.VoucherLineitem;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.PromotionRequest;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "promotionDAO")
@Transactional(rollbackOn = Exception.class)
public class PromotionDAO extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(PromotionDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	LanguageDAO ls;

	public String Create(PromotionRequest req, Restaurant r) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		try {
			// content defi
			ContentDefinition cd = new ContentDefinition();
			cd.setName("Promotion_Name");
			session.save(cd);

			Promotion promotion = this.modelToEnity(req, null);
			promotion.setContentDefinition(cd);

			boolean isF = false;
			String msg = "";
			// Language
			List<LanguageViewModel> lvm = req.getLanguageLst();
			if (lvm != null && lvm.size() > 0) {
				for (LanguageViewModel l : lvm) {
					Language lang = ls.getById(l.getLanguageId());
					if (lang != null) {
						// save content entry
						for (ContentDefModel cdm : l.getContentDef()) {
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
			session.save(promotion);

			// pro line
			PromotionLineitem p = new PromotionLineitem();
			p.setCode(req.getCode().trim());
			p.setPromotion(promotion);
			p.setRestaurant(r);

			session.save(p);

			if (!isF) {
				t.commit();
				return "";
			}
			return "99|" + msg;

		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
			return "99|" + e.getMessage();
		} finally {
			session.close();
		}
	}

	public String Update(PromotionRequest req, Promotion p, Restaurant r) {

		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		try {

			ContentDefinition cd = p.getContentDefinition();

			String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
			Long cdId = cd.getContentDepId();
			int d = session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();

			Boolean isF = false;
			String msg = "";

			List<LanguageViewModel> lvm = req.getLanguageLst();
			if (lvm != null && lvm.size() > 0) {
				for (LanguageViewModel l : lvm) {
					Language lang = ls.getById(l.getLanguageId());
					if (lang != null) {
						// save content entry
						for (ContentDefModel cdm : l.getContentDef()) {
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
			Promotion promotion = this.modelToEnity(req, p);
			session.update(promotion);

			// delete proLine
			String de = "DELETE FROM promotion_lineitem WHERE promotion_id=:proId";
			int delP = session.createNativeQuery(de).setParameter("proId", p.getPromotionId()).executeUpdate();

			// pro line
			PromotionLineitem pl = new PromotionLineitem();
			pl.setCode(req.getCode().trim());
			pl.setPromotion(promotion);
			pl.setRestaurant(r);

			session.save(pl);

			if (!isF) {

				t.commit();
				return "";
			}
			return "99|" + msg;

		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
			return "99|" + e.getMessage();
		} finally {
			session.close();
		}
	}

	public Promotion getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Promotion.class, id);
	}

	public void delete(final Promotion promotion) {
		Session session = this.sessionFactory.getCurrentSession();
		promotion.setStatus(Constant.Status.Deleted.getValue());
		promotion.setModifiedDate(new Date());

		session.update(promotion);
	}

	public List<Promotion> getAll(int firstResult, int maxResult, Integer status) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Promotion> query = cb.createQuery(Promotion.class);
		Root<Promotion> form = query.from(Promotion.class);
		if (null == status) {
			query = query.where(cb.and(cb.notEqual(form.<Integer>get("status"), Constant.Status.Deleted.getValue())));
		} else {
			query = query.where(cb.and(cb.equal(form.<Integer>get("status"), status)));
		}
		query.select(form);
		List<Promotion> menus = (maxResult == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return menus;
	}

	public List<Promotion> getAllByOwner(int firstResult, int maxResult, Long userId, Integer status) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Promotion> query = cb.createQuery(Promotion.class);
		Root<Promotion> form = query.from(Promotion.class);
		Join<Promotion, PromotionLineitem> join = form.join("promotionLineitems");
		Join<Promotion, PromotionLineitem> restaurant = join.join("restaurant");
		Join<Restaurant, UserRestaurant> userRestaurant = restaurant.join("userRestaurants");
		List<Predicate> predicates = new ArrayList<>();
		if (userId != null)
			predicates.add(cb.equal(userRestaurant.join("user").<Long>get("userId"), userId));
		if (status == null) {
			predicates.add(cb.notEqual(form.get("status"), Constant.Status.Deleted.getValue()));
		} else {
			predicates.add(cb.equal(form.get("status"), status));
		}
		query.select(form).where(predicates.stream().toArray(Predicate[]::new));

		query.distinct(form.get("promotionId") != null);

		query.select(form);
		List<Promotion> menus = (maxResult == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return menus;
	}

	private Promotion modelToEnity(PromotionRequest pr, Promotion p) {
		if (p == null) {
			Promotion np = new Promotion();
			np.setStartDate(pr.getStartDate());
			np.setEndDate(pr.getEndDate());
			np.setValue(pr.getValue());
			np.setCreatedDate(new Date());
			np.setMinOrder(pr.getMinOrder());
			np.setStatus(pr.getStatus());
			np.setModifiedDate(new Date());
			// np.setCreatedBy();
			return np;
		}
		p.setStartDate(pr.getStartDate());
		p.setEndDate(pr.getEndDate());
		p.setValue(pr.getValue());
		p.setModifiedDate(new Date());
		p.setMinOrder(pr.getMinOrder());
		p.setStatus(pr.getStatus());
		// p.setModifiedBy(modifiedBy);
		return p;
	}

	public Promotion getByCode(String code, Long resId) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<PromotionLineitem> query = cb.createQuery(PromotionLineitem.class);
		Root<PromotionLineitem> from = query.from(PromotionLineitem.class);
		Join<PromotionLineitem, Promotion> join = from.join("promotion");
		Join<PromotionLineitem, Restaurant> restaurant = from.join("restaurant");

		query = query.select(from).distinct(true)
				.where(cb.and(cb.equal(from.get("code"), code), cb.equal(restaurant.get("restaurantId"), resId),
						cb.equal(join.get("status"), Constant.Status.Publish.getValue()),
						cb.greaterThanOrEqualTo(join.<Date>get("endDate"), new Date()),
						cb.lessThanOrEqualTo(join.<Date>get("startDate"), new Date())));

		List<PromotionLineitem> lst = session.createQuery(query).getResultList();

		if (lst != null && lst.size() > 0) {
			return lst.get(0).getPromotion();
		}

		return null;
	}
}
