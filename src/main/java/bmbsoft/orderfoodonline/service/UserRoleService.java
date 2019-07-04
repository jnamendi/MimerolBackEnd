package bmbsoft.orderfoodonline.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.UserRoleDAO;
import bmbsoft.orderfoodonline.entities.UserRole;

@Service
public class UserRoleService {
	public static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);

	@Autowired
	UserRoleDAO RoleDAO;
 
	@Transactional
	public boolean save(UserRole r) {
		return RoleDAO.save(r);
	}
 
	@Transactional
	public boolean deleteRole(Long r) {
		 return RoleDAO.deleteByUser(r); 
	}
 
}
