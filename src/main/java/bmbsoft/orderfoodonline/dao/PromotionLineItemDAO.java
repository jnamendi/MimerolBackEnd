package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.PromotionLineitem;

@Repository(value = "promotionLineItemDAO")
@Transactional(rollbackOn = Exception.class)
public class PromotionLineItemDAO {
	public static final Logger logger = LoggerFactory.getLogger(PromotionDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean create(PromotionLineitem p) {
		Session session = this.sessionFactory.getCurrentSession();
		return (null == session.save(p)) ? false : true;
	}

	public Boolean update(PromotionLineitem pli) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(pli);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public PromotionLineitem getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(PromotionLineitem.class, id);
	}

	public boolean getByCode(final String code, long resId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<PromotionLineitem> lst = session.createQuery(
				"FROM PromotionLineitem pi inner join pi.restaurant r where pi.code=:code and r.restaurantId=:res")
				.setParameter("code", code).setParameter("res", resId).getResultList();
		if (lst != null && lst.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean getByCode(final String code) {
		Session session = this.sessionFactory.getCurrentSession();
		List<PromotionLineitem> lst = session.createQuery("FROM PromotionLineitem pi where pi.code=:code")
				.setParameter("code", code).getResultList();
		if (lst != null && lst.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean getByProId(String code, long promotionId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<PromotionLineitem> lst = session.createQuery(
				"FROM PromotionLineitem p inner join p.promotion pr where pr.promotionId=:prId and p.code=:code")
				.setParameter("prId", promotionId).setParameter("code", code).getResultList();
		if (lst != null && lst.size() > 0) {
			return true;
		}
		return false;
	}

	public void delete(final PromotionLineitem pli) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(pli);
	}

	public List<PromotionLineitem> getAll(int firstResult, int maxResult) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<PromotionLineitem> query = cb.createQuery(PromotionLineitem.class);
		Root<PromotionLineitem> form = query.from(PromotionLineitem.class);

		// List<Predicate> predicates = new ArrayList<>();
		// predicates.add(cb.equal(rootRestaurant.get("keySearch"), key));
		// query.select(form).where(predicates.stream().toArray(Predicate[]::new));
		// query.distinct(form.get("menuId") != null);
		query.select(form);
		List<PromotionLineitem> plis = (maxResult == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return plis;
	}

}
