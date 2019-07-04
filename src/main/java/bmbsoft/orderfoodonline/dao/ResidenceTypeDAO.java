package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.ResidenceType;

@Repository(value = "ResidenceTypeDAO")
@Transactional(rollbackOn = Exception.class)
public class ResidenceTypeDAO {
	public static final Logger logger = LoggerFactory.getLogger(ResidenceTypeDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean Create(final ResidenceType residenceType) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(residenceType)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean Update(final ResidenceType residenceType) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(residenceType);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public ResidenceType getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(ResidenceType.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void delete(final ResidenceType residenceType) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(residenceType);
	}

	public List<ResidenceType> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			List<ResidenceType> cs = s.createQuery("FROM ResidenceType", ResidenceType.class).getResultList();
			return cs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
