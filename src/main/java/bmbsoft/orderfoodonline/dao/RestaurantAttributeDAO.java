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
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.entities.Attribute;
import bmbsoft.orderfoodonline.entities.RestaurantAttribute;

@Repository(value = "restaurantAttributeDAO")
public class RestaurantAttributeDAO {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantAttributeDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(final RestaurantAttribute a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(a);
	}

	public RestaurantAttribute getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(RestaurantAttribute.class, id);
	}

	public void delete(final RestaurantAttribute a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
	}

	public List<RestaurantAttribute> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM RestaurantAttribute a ";
			Query<RestaurantAttribute> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}

	public boolean deleteByResId(long resId) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			String q = "DELETE FROM restaurant_attribute ra WHERE ra.restaurant_id=:resId";
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
