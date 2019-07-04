package bmbsoft.orderfoodonline.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;
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

import bmbsoft.orderfoodonline.entities.Role;
import bmbsoft.orderfoodonline.entities.UserRole;
import bmbsoft.orderfoodonline.model.RoleModel;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "userRoleDAO")
@Transactional(rollbackOn = Exception.class)
public class UserRoleDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserRoleDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final UserRole roleModel) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(roleModel);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public UserRole getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(UserRole.class, id);
	}

	public void delete(final UserRole Role) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Role);
	}

	public boolean deleteByUser(Long uId) {
		Session session = this.sessionFactory.getCurrentSession();

		String q = "DELETE FROM user_role WHERE user_id=:uId";
		int d = session.createNativeQuery(q).setParameter("uId", uId).executeUpdate();

		return d > 0;
	}

}
