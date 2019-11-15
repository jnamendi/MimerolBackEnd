package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.UserDAO;
import bmbsoft.orderfoodonline.entities.*;
import bmbsoft.orderfoodonline.model.RoleModel;
import bmbsoft.orderfoodonline.model.UserInfoResponse;
import bmbsoft.orderfoodonline.model.UserViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRestaurantService urService;

	public List<User> findAll() {
		return userDAO.findAll();
	}

	public List<UserViewModel> getAllUserSortByName() throws Exception {
		List<User> users = userDAO.getAllUserSortByName();
		List<UserViewModel> lst = new ArrayList<>();

		if (users != null && users.size() > 0) {
			users.forEach(ur -> {
				UserViewModel u = new UserViewModel();
				u.setUserId(ur.getUserId());
				u.setUserName(ur.getUserName());
				u.setFullName(ur.getFullName());
				u.setEmail(ur.getEmail());
				boolean isEmail = CommonHelper.isValidEmailAddress(ur.getEmail());
				if (isEmail) {
					u.setAliasName(ur.getEmail());
				} else if (StringUtils.isNumeric(ur.getUserName())) {
					u.setAliasName(ur.getFullName() + " - " + ur.getEmail());
				} else {
					u.setAliasName(ur.getFullName() + " - " + ur.getUserName());
				}
				lst.add(u);
			});
		}
		return lst;
	}

	public ResponseGetPaging getPaging(final int pageIndex, final int pageSize, final String name, Integer status,
			Integer accountType) throws Exception {
		int totalRecord = userDAO.countGetAll(name, status, accountType);
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<User> users = userDAO.getPaging(firstResult, maxResult, name, status, accountType);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (users == null || users.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<UserViewModel> userModels = MappingToModel(users);

		if (totalRecord > 0 && !userModels.isEmpty()) {
			content.setData(userModels);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	private List<UserViewModel> MappingToModel(List<User> users) {
		List<UserViewModel> vm = new ArrayList<>();
		users.forEach(u -> {
			UserViewModel uv = new UserViewModel();
			uv.setUserId(u.getUserId());
			uv.setUserName(u.getUserName());
			uv.setEmail(u.getEmail());
			uv.setFullName(u.getFullName());
			uv.setPhone(u.getPhone());
			uv.setReceiveNewsLetter(u.getReceiveNewsletter());
			uv.setAccountType(Constant.AccountType.valueOf(u.getAccountType()));
			uv.setStatus(u.getStatus());
			uv.setRoles(setRoleNewUser(u.getUserId()));
			vm.add(uv);
		});

		return vm;

	}

	public String[] setRoleNewUser(final Long id) {

		List<String> listStrRole = new LinkedList<>();
		try {
			// get role by user id
			List<RoleModel> listRoleModel = roleService.getRoleAssigneByUser(id);
			if (null != listRoleModel && !listRoleModel.isEmpty()) {
				listRoleModel.forEach(roleModel -> {
					if (!roleModel.getCode().equals(Constant.ROLE_CODE_OWNER)) {
						listStrRole.add(roleModel.getCode());
					}
				});
			} else {
				Role role = roleService.getRoleByCode("guest");
				if (null == role) {
					RoleModel roleModel = new RoleModel();
					roleModel.setCode("guest");
					roleModel.setName("Guest");
					if (roleService.createRole(roleModel)) {
						role = roleService.getRoleByCode("guest");
					}
				}
				listStrRole.add(role.getCode());
			}
			// check in table UserRestaurant
			List<UserRestaurant> listUr = urService.getByUser(id);
			if (null != listUr && listUr.size() > 0) {
				Role role = roleService.getRoleByCode(Constant.ROLE_CODE_OWNER);
				if (null == role) {
					RoleModel roleModel = new RoleModel();
					roleModel.setCode(Constant.ROLE_CODE_OWNER);
					roleModel.setName("Owner");
					if (roleService.createRole(roleModel)) {
						role = roleService.getRoleByCode(Constant.ROLE_CODE_OWNER);
					}
				}
				listStrRole.add(role.getCode());
			}
			String[] arrayStringRole = listStrRole.toArray(new String[listStrRole.size()]);
			return arrayStringRole;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
	@Transactional
	public User findById(final Long id) {
		return userDAO.findById(id);
	}
	@Transactional
	public UserInfo getByUserInfoId(final Long id) {
		return userDAO.getByUserInfoId(id);
	}

	@Transactional
	public List<UserInfoResponse> getAllByUserInfo(Long userId) {
		List<UserInfoResponse> uf = userDAO.getAllByUserInfo(userId);
		return uf;
	}

	public Boolean save(final User user) {
		return userDAO.save(user);
	}

	public boolean updateUserInfo(UserInfo u) {
		return userDAO.updateUserInfo(u);
	}

	public Boolean save(UserViewModel vm) {
		return userDAO.save(vm);
	}

	public Boolean update(UserViewModel vm, User u) {
		return userDAO.update(vm, u);
	}

	public Boolean delete(User user) {
		return userDAO.save(user);
	}

	public boolean isUserExist(User user) {
		// User us = userDAO.findById(user.getUserId());
		return true; // us != null;
	}

	@Transactional
	@Async
	public User getUserByEmail(String email, int provider) {
		User u = userDAO.getUserByEmail(email, provider);

		return u;
	}

	@Transactional
	@Async
	//@Cacheable(value = "getPermissionUserByEmail")
	public boolean getPermissionUserByEmail(String email, int provider, String module, String action) {
		System.out.println("Service processing..." + email);
		User s = userDAO.getUserByEmail(email, provider);
		boolean isTr = false;

		if (s == null)
			return false;
		Set<UserRole> sur = s.getUserRoles();
		if (sur != null) {
			// check role
			for (UserRole ur : sur) {
				if (ur.getRole() != null) {
					// check role-module
					logger.info("get role:" + ur.getRole().getName());
					Set<RoleModule> rm = ur.getRole().getRoleModules();
					if (rm != null && rm.size() > 0) {
						logger.info("role:" + ur.getRole().getName());
						rm = rm.stream()
								.filter(p -> p.getModuleAction().getAction().equals(action)
										&& p.getModuleAction().getModuleName().equals(module)
										&& p.getUserRestaurant() == null)
								.collect(Collectors.toSet());

						if (rm != null && rm.size() > 0) {
							logger.info("role ! owner:" + ur.getRole().getName());
							isTr = true;
							break;
						}
					}
				}
			}
		}

		return isTr;
	}

	@Transactional
	@Async
	public User getUserByEmail(String email, int provider, int accountType) {
		User u = userDAO.getUserByEmail(email, provider, accountType);

		return u;
	}

	@Transactional
	@Async
	public User getLoginUserByEmail(String email, int provider) {
		User u = userDAO.getLoginUserByEmail(email, provider);

		return u;
	}

	public User getUserByToken(String token) {
		User u = userDAO.getUserByToken(token);
		return u;
	}

	public User getUserByUserName(String userName) {
		User u = userDAO.getUserByUserName(userName);
		return u;
	}

	public boolean checkExistEmail(String email, int provider) {
		User u = getUserByEmail(email, provider);
		return u != null && !u.getEmail().isEmpty();
	}

	public boolean checkExistEmail(String email, int provider, int accountType) {
		User u = getUserByEmail(email, provider, accountType);
		return u != null && !u.getEmail().isEmpty();
	}

	public Boolean checkExistUserName(String userName, int provider) {
		User u = getUserByUserName(userName);
		return u != null && !u.getUserName().isEmpty();
	}

	public UserViewModel loadUserByUsername(String username) {
		// User u = userDAO.getUserByUserName(username);
		User u = userDAO.getUserByEmail(username, Constant.Provider.NORMAL.getValue());
		if (u != null) {
			UserViewModel uvm = new UserViewModel();
			uvm.setEmail(u.getEmail());
			uvm.setPassword(u.getUserHash());
			uvm.setUserId(u.getUserId());
			// todo get Role
			return uvm;
		}
		return null;
	}

	public boolean createPasswordResetTokenForUser(User u, String token) {
		String hpw = CommonHelper.HasPw(token);
		u.setUserHash(hpw);
		u.setUserSalt(hpw);
		Date d = new Date();
		u.setModifiedDate(d);
		return this.save(u);
	}

	public String checkAssign(final Long userId, final Long[] roleIds) {
		StringBuilder message = new StringBuilder();
		if (findById(userId) == null) {
			return message.append(" not exists user id " + userId).toString();
		}
		if (null == roleIds) {
			return message.append(" not exists list role id" + roleIds).toString();
		} else {
			for (Long roleId : roleIds) {
				if (null == roleService.getRoleById(roleId))
					message.append(" not exists role id " + roleId).toString();
				if (userDAO.checkAssignExistsed(userId, roleId)) {
					return message.append(" assigned role for user id: " + userId + " with role id: " + roleId)
							.toString();
				}
			}
		}
		return message.toString();
	}

	@Transactional
	public String Assign(final Long userId, final Long[] roleIds) {
		String a = checkAssign(userId, roleIds);
		if (null == a || a.isEmpty()) {
			for (Long roleId : roleIds) {
				if (!userDAO.assignRole(userId, roleId))
					return "can not save user id: " + userId + " with role id: " + roleId;
			}
		} else {
			return a;
		}
		return "";
	}

	// assign for new user: b1 remove role by user id. b2 new assign.
	@Transactional
	public String updateAssign(final Long userId, final Long[] roleIds) {
		if (!userDAO.removeAssignRole(userId)) {
			return "can not remove assign for user id: " + userId;
		}
		String a = checkAssign(userId, roleIds);
		if (null == a || a.isEmpty()) {
			for (Long roleId : roleIds) {
				if (!userDAO.assignRole(userId, roleId))
					return "can not save user id: " + userId + " with role id: " + roleId;
			}
		} else {
			return a;
		}
		return "";
	}
}
