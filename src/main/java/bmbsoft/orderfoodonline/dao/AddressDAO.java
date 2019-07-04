package bmbsoft.orderfoodonline.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Address;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "AddressDAO")
@Transactional(rollbackOn = Exception.class)
public class AddressDAO {

	public static final Logger logger = LoggerFactory.getLogger(AddressDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean create(final Address address) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(address)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean update(final Address address) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(address);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Address getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Address a = session.get(Address.class, id);

			return a;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/*
	 * private String address; private String emailAdd; private String phoneNumber;
	 */
	public List<Address> getAll(final int firstResult, final int maxResult, String title, Integer status) {
		Session s = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Address> cq = cb.createQuery(Address.class);
		Root<Address> from = cq.from(Address.class);
		Predicate predicate = cb.conjunction();
		if (null != title && !title.isEmpty()) {
			predicate = cb.and(predicate, cb.like(from.<String>get("address"), "%" + title + "%"));
			predicate = cb.or(predicate, cb.like(from.<String>get("emailAdd"), "%" + title + "%"));
			predicate = cb.or(predicate, cb.like(from.<String>get("phoneNumber"), "%" + title + "%"));
		}
		if (null == status) {
			predicate = cb.and(predicate,
					cb.notEqual(from.<Integer>get("isStatus"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(from.<Integer>get("isStatus"), status));
		}
		cq.select(from).where(predicate);

		List<Address> addresses = (maxResult == 0) ? s.createQuery(cq).getResultList()
				: s.createQuery(cq).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return addresses;
	}

	public List<Address> getByUser(final Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Address> queryAddress = cb.createQuery(Address.class);
			Root<Address> rootAddress = queryAddress.from(Address.class);
			queryAddress.select(rootAddress).where(cb.equal(rootAddress.join("user").<Long>get("userId"), userId),
					cb.equal(rootAddress.get("isStatus"), Constant.Status.Publish.getValue()));
			List<Address> listAddress = session.createQuery(queryAddress).getResultList();

			return listAddress;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
}
