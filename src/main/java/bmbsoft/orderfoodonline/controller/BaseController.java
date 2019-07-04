package bmbsoft.orderfoodonline.controller;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.RoleModule;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.entities.UserRole;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.Constant;

/**
 * todo: error message type
 */
@RestController
public class BaseController {
	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	public Environment environment;

	@Autowired
	public UserService us;

	public static void isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		// Object principal = authentication.getPrincipal();
		String username = authentication.getName(); // email

		// get role

		// check role
		// check role-module

	}
	
	public boolean permission(String module, String action) {
		return true;
//		SecurityContext context = SecurityContextHolder.getContext();
//		Authentication authentication = context.getAuthentication();
//		// Object principal = authentication.getPrincipal();
//		String username = authentication.getName(); // email
//		// get role
//		return us.getPermissionUserByEmail(username, Constant.Provider.NORMAL.getValue(), module, action);

	}
}
