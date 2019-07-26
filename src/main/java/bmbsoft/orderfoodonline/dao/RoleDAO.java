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

@Repository(value = "roleDAO")
@Transactional(rollbackOn = Exception.class)
public class RoleDAO {
	public static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean Update(final RoleModel roleModel) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Role role = modelToEntity(roleModel);
			session.update(role);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean Create(final RoleModel roleModel) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(modelToEntity(roleModel));
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public List<RoleModel> getListRole() throws Exception {
		Session s = this.sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBd = s.getCriteriaBuilder();
		CriteriaQuery<Role> queryRole = criteriaBd.createQuery(Role.class);
		Root<Role> rootRole = queryRole.from(Role.class);
		queryRole.select(rootRole)
				.where(criteriaBd.notEqual(rootRole.get("status"), Constant.Status.Deleted.getValue()));
		List<Role> listRole = s.createQuery(queryRole).getResultList();
		List<RoleModel> listRoleModel = new LinkedList<>();
		if (null == listRole) {
			return null;
		}
		listRole.forEach(role -> {
			listRoleModel.add(entityToModel(role));
		});
		return listRoleModel;
	}

	public RoleModel getDetail(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Role role = session.get(Role.class, id);
		return (role == null) ? null : entityToModel(role);
	}

	public boolean deleteRole(Role r) {

		Session session = this.sessionFactory.getCurrentSession();
		try {

			session.update(r);
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	// convert
	private RoleModel entityToModel(Role role) {
		RoleModel rModel = new RoleModel(role.getRoleId(), role.getName(), role.getCode(), role.getStatus());
		return rModel;
	}

	private Role modelToEntity(RoleModel roleModel) {
		Role role = new Role();
		Long id = roleModel.getRoleId();
		if (null == id) {
			role.setName(roleModel.getName());
			role.setCode(roleModel.getCode());
			// role.setCreatedBy("New_order_to_admin hasd code"); // miss create by
			role.setStatus(roleModel.getStatus());
			role.setCreatedDate(new Date());
		} else {
			role = getById(id);
			role.setName(roleModel.getName());
			role.setCode(roleModel.getCode());
			role.setStatus(roleModel.getStatus());
		}
		return role;
	}

	public Role getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Role.class, id);
	}

	public void delete(final Role Role) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Role);
	}

	// get role by code
	public Role getRoleByCode(final String roleCode) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
			CriteriaQuery<Role> queryRole = criteriaBd.createQuery(Role.class);
			Root<Role> rootRole = queryRole.from(Role.class);
			queryRole.select(rootRole).where(criteriaBd.equal(rootRole.get("code"), roleCode));
			Role role = session.createQuery(queryRole).getSingleResult();
			return role;
		} catch (NoResultException e) {
			// Ignore this because not found by input condition
			return null;
		}
	}

	public List<RoleModel> getRoleAssigneByUser(final Long userid) {
		List<RoleModel> RoleModels = new LinkedList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
			CriteriaQuery<UserRole> queryRole = criteriaBd.createQuery(UserRole.class);
			Root<UserRole> rootRole = queryRole.from(UserRole.class);
			queryRole.select(rootRole).where(criteriaBd.equal(rootRole.join("user").get("userId"), userid));
			List<UserRole> listRole = session.createQuery(queryRole).getResultList();
			if (null == listRole || listRole.isEmpty()) {
				return null;
			}
			listRole.forEach(userRole -> {
				RoleModels.add(entityToModel(userRole.getRole()));
			});
			return RoleModels;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public UserRole getUserRoleByUser(final Long userid) {
		// List<RoleModel> RoleModels = new LinkedList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
			CriteriaQuery<UserRole> queryRole = criteriaBd.createQuery(UserRole.class);
			Root<UserRole> rootRole = queryRole.from(UserRole.class);
			queryRole.select(rootRole).where(criteriaBd.equal(rootRole.join("user").get("userId"), userid));
			UserRole ur = session.createQuery(queryRole).getSingleResult();
			return ur;
		} catch (NoResultException e) {
			// Ignore this because not found by input condition
			return null;
		}
	}
}
