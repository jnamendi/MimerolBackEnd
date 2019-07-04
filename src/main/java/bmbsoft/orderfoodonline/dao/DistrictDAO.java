package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.District;

@Repository(value = "districtDAO")
@Transactional(rollbackOn = Exception.class)
public class DistrictDAO {
	public static final Logger logger = LoggerFactory.getLogger(DistrictDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean Create(final District district) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(district)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean Update(final District District) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(District);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public District getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(District.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void delete(final District district) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(district);
	}

	public List<District> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			List<District> cs = s.createQuery("FROM District", District.class).getResultList();
			return cs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public List<District> getDistrictByCityId(long cityId) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<District> query = cb.createQuery(District.class);
		Root<District> root = query.from(District.class);
		
		Join<District, Object> join = root.join("city");
		query =query.select(root)
				.where(cb.and(
						cb.equal(join.get("cityId"), cityId)));
		
		List<District> c = session.createQuery(query).getResultList();
		if (c != null && c.size() > 0) {
			return c;
		}
		return null;
	}
	
	public District getDistrictByName(String districtCode, String cityCode) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<District> query = cb.createQuery(District.class);
		Root<District> root = query.from(District.class);
		
		Join<District, Object> join = root.join("city");
		query =query.select(root)
				.where(cb.and(
						cb.equal(root.get("code"), districtCode),
						cb.equal(join.get("cityCode"), cityCode)
						));
		
		List<District> c = session.createQuery(query).getResultList();
		if (c != null && c.size() > 0) {
			return c.get(0);
		}
		return null;
	}

}
