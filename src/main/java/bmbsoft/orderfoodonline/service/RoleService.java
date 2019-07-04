package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.RoleDAO;
import bmbsoft.orderfoodonline.entities.Role;
import bmbsoft.orderfoodonline.model.RoleModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class RoleService {
	public static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	RoleDAO RoleDAO;

	@Transactional
	public List<RoleModel> getRoles() throws Exception {
		return RoleDAO.getListRole();
	}

	@Transactional
	public RoleModel getRoleById(Long id) {
		return RoleDAO.getDetail(id);
	}

	@Transactional
	public Role getRoleBaseById(Long id) {
		return RoleDAO.getById(id);
	}

	@Transactional
	public Boolean createRole(RoleModel roleModel) {
		return RoleDAO.Create(roleModel);
	}

	@Transactional
	public Boolean updateRole(RoleModel roleModel) {
		return RoleDAO.Update(roleModel);
	}

	@Transactional
	public boolean deleteRole(Role r) {
		r.setStatus(Constant.Status.Deleted.getValue());
		r.setModifiedDate(new Date());

		return RoleDAO.deleteRole(r);
	}

	@Transactional
	public Role getRoleByCode(final String roleCode) {
		return RoleDAO.getRoleByCode(roleCode);
	}

	@Transactional
	public List<RoleModel> getRoleAssigneByUser(final Long userid) {
		return RoleDAO.getRoleAssigneByUser(userid);
	}

}
