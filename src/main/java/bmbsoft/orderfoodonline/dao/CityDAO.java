package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.City;

@Repository(value = "cityDAO")
@Transactional(rollbackOn = Exception.class)
public class CityDAO {
	public static final Logger logger = LoggerFactory.getLogger(CityDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean Create(final City city) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(city)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean Update(final City city) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(city);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public City getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(City.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public void delete(final City city) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(city);
	}

	public List<City> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			List<City> cs = s.createQuery("FROM City order by city_name", City.class).getResultList();
			return cs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
