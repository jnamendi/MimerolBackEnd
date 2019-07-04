package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Country;

@Repository(value = "countryDAO")
@Transactional(rollbackOn = Exception.class)
public class CountryDAO {
	public static final Logger logger = LoggerFactory.getLogger(CountryDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean Create(final Country country) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(country)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean Update(final Country country) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(country);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Country getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(Country.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void delete(final Country country) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(country);
	}

	public List<Country> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			List<Country> cs = s.createQuery("FROM Country", Country.class).getResultList();
			return cs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
