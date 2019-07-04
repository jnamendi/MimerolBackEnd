package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.entities.UserRestaurant;

@Repository(value = "userRestaurantDAO")
@Transactional(rollbackFor = Exception.class)
public class UserRestaurantDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserRestaurantDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final UserRestaurant userRes) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(userRes);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public List<UserRestaurant> getByUser(final long userid) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
		CriteriaQuery<UserRestaurant> queryRole = criteriaBd.createQuery(UserRestaurant.class);
		Root<UserRestaurant> form = queryRole.from(UserRestaurant.class);
		queryRole.select(form).where(criteriaBd.equal(form.join("user").get("userId"), userid));
		List<UserRestaurant> listRole = session.createQuery(queryRole).getResultList();
		return listRole;
	}
}
