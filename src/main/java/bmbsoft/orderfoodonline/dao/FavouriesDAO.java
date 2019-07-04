package bmbsoft.orderfoodonline.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Address;
import bmbsoft.orderfoodonline.entities.Favouries;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "favoriesDAO")
@Transactional(rollbackOn = Exception.class)
public class FavouriesDAO {
	public static final Logger logger = LoggerFactory.getLogger(FavouriesDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final Favouries f) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			s.saveOrUpdate(f);
			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();

			return false;
		} finally {
			s.close();
		}
	}

	public Favouries getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Favouries.class, id);
	}

	public List<Favouries> getByUserId(Long restaurantId, final Long userId, Integer status) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Favouries> query = cb.createQuery(Favouries.class);
			Root<Favouries> from = query.from(Favouries.class);
			List<Predicate> pre = new ArrayList<>();
 
			if (restaurantId != null) {
				pre.add(cb.equal(from.join("restaurant").get("restaurantId"), restaurantId));
			}
			if (userId != null) {
				pre.add(cb.equal(from.join("user").<Long>get("userId"), userId));
			}
			if (status != null) {
				pre.add(cb.equal(from.get("isStatus"), status));
			}
			query.select(from).where(pre.stream().toArray(Predicate[]::new));

			List<Favouries> res = session.createQuery(query).getResultList();

			return res;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public boolean delete(final Favouries f) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			f.setIsStatus(Constant.Status.Deleted.getValue());
			f.setModifiedDate(new Date());

			s.update(f);

			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();

			return false;
		} finally {
			s.close();
		}
	}

	public List<Favouries> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM Favories a ";
			Query<Favouries> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}

	public List<Favouries> getAll(final int firstResult, final int maxResult, String title, Integer status)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Favouries> queryFavouries = cb.createQuery(Favouries.class);
		Root<Favouries> rootFavouries = queryFavouries.from(queryFavouries.getResultType());
		queryFavouries.select(rootFavouries);
		queryFavouries.where(toPredicate(rootFavouries, cb, title, status));
		queryFavouries.orderBy(cb.desc(rootFavouries.get("modifiedDate")));
		List<Favouries> favouriess = (maxResult == 0) ? session.createQuery(queryFavouries).getResultList()
				: session.createQuery(queryFavouries).setFirstResult(firstResult).setMaxResults(maxResult)
						.getResultList();
		return favouriess;
	}

	public int countGetAll(String title, Integer status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<Favouries> countFrom = queryCount.from(Favouries.class);
		queryCount.select(cb.count(countFrom));
		queryCount.where(toPredicate(countFrom, cb, title, status));
		try {
			Long totalCount = session.createQuery(queryCount).getSingleResult();
			return totalCount.intValue();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}

	}

	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, String title, Integer status) {
		Predicate predicate = cb.conjunction();
		if (null != title && !title.isEmpty()) {
			predicate = cb.and(predicate, cb.like(root.get("restaurant").<String>get("name"), "%" + title + "%"));
		}
		if (null == status) {
			predicate = cb.and(predicate,
					cb.notEqual(root.<Integer>get("isStatus"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("isStatus"), status));
		}
		return predicate;
	}

}
