package bmbsoft.orderfoodonline.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.RestaurantPaymentProvider;

@Repository(value = "restaurantPaymentProviderDAO")
public class RestaurantPaymentProviderDAO {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantPaymentProviderDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(final RestaurantPaymentProvider a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(a);
	}

	public RestaurantPaymentProvider getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(RestaurantPaymentProvider.class, id);
	}

	public void delete(final RestaurantPaymentProvider a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
	}

	public List<RestaurantPaymentProvider> getAll() {
		Session s = null;
		Transaction t = s.beginTransaction();
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM RestaurantPaymentProvider a ";
			Query<RestaurantPaymentProvider> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}

	public boolean deleteByResId(Long resId) {
		Session s = this.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			String q = "DELETE FROM restaurant_payment_provider WHERE restaurant_id=:resId";
			int c = s.createSQLQuery(q).setParameter("resId", resId).executeUpdate();
			t.commit();

			return c > 0;

		} catch (Exception e) {
			t.rollback();
			return false;
		} finally {
			s.close();
		}
	}
}
