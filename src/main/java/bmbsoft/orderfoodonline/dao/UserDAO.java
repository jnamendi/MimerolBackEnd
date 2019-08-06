package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.Role;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.entities.UserInfo;
import bmbsoft.orderfoodonline.entities.UserRole;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.model.UserInfoResponse;
import bmbsoft.orderfoodonline.model.UserViewModel;
import bmbsoft.orderfoodonline.service.ContentEmailService;
import bmbsoft.orderfoodonline.service.EmailService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import bmbsoft.orderfoodonline.util.RandomStringHelper;
import jlibs.core.util.regex.TemplateMatcher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Repository(value = "userDAO")
@Transactional(rollbackFor = Exception.class)
public class UserDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private UserRoleDAO userRoleDao;

	@Autowired
	public Environment environment;

	@Autowired
	private ContentEmailService ce;

	@Autowired
	private EmailService emailService;

	public Boolean save(final User user) {
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			session.saveOrUpdate(user);
			
			userRoleDao.deleteByUser(user.getUserId());
			
			UserRole ur=new UserRole() ;
			ur.setUser(user);
			ur.setRole(roleDAO.getRoleByCode(Constant.ROLE_CODE_GUEST));
			session.saveOrUpdate(ur);
			
			
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean save(UserViewModel vm) {
		Session session = sessionFactory.getCurrentSession();
		// Transaction t = session.beginTransaction();
		try {
			User u = new User();
			u.setEmail(vm.getEmail());
			u.setUserName(vm.getEmail());
			u.setCreatedDate(new Date());
			u.setFullName(vm.getFullName());
			u.setStatus(vm.getStatus());
			u.setIsLock(false);
			u.setAccountType(vm.getAccountType().getValue());
			u.setReceiveNewsletter(vm.getReceiveNewsLetter());

			RandomStringHelper gen = new RandomStringHelper(8, ThreadLocalRandom.current());
			String token = gen.nextString();

			u.setUserHash(CommonHelper.HasPw(token));
			u.setUserSalt(CommonHelper.HasPw(token));
			u.setProvider(Constant.Provider.NORMAL.getValue());
			u.setPhone(vm.getPhone());

			session.save(u);

			// Set<UserRole> sur = new HashSet<>();

			if (vm.getRoles() != null && vm.getRoles().length > 0) {
				boolean del = userRoleDao.deleteByUser(u.getUserId());

				for (int i = 0; i < vm.getRoles().length; i++) {
					// delete role
					Role r = roleDAO.getRoleByCode(vm.getRoles()[i]);
					if (r != null) {

						UserRole userRole = new UserRole();
						userRole.setRole(r);
						userRole.setUser(u);

						boolean ur = userRoleDao.save(userRole);

					}
				}
			}

			if (vm.getLanguageCode() != null && !vm.getLanguageCode().isEmpty()) {
				try {
					// String appUrl = request.getScheme() + "://" + request.getServerName();
					String appUrl = environment.getProperty("frontend.url");
					String emailFrom = environment.getProperty("email.from");
					String siteTitle = environment.getProperty("site.title");
					String displayEmailName = environment.getProperty("display.email.name");

					ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.CreateNewUser.getValue(),
							vm.getLanguageCode());
					if (cm != null) {
						TemplateMatcher matcher = new TemplateMatcher("${", "}");
						Map<String, String> vars = new HashMap<String, String>();
						vars.put("url", appUrl);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
						String str_date = dateFormat.format(new Date());
						vars.put("year", str_date);
						vars.put("fullName", u.getFullName().isEmpty() ? u.getUserName() : u.getFullName());
						vars.put("userName", u.getUserName());
						vars.put("password", token);
						vars.put("siteName", siteTitle);

						String body = matcher.replace(cm.getBody(), vars);

						// // title
						// TemplateMatcher title = new TemplateMatcher("${", "}");
						// Map<String, String> t = new HashMap<String, String>();
						// t.put("siteName", siteTitle);
						// String trpc = title.replace(cm.getSubject(), t);

						String trpc = cm.getSubject();
						Executors.newSingleThreadExecutor().execute(new Runnable() {
							public void run() {
								try {
									emailService.sendMessage(emailFrom, vm.getEmail(), trpc, body, displayEmailName);
								} catch (MessagingException | IOException e) {
									logger.error(e.toString());
								}
							}
						});
					}

				} catch (Exception e) {
					logger.error(e.toString());
				}
			}

			// t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			// t.rollback();
			return false;
		}
	}

	public Boolean update(UserViewModel vm, User u) {
		Session session = sessionFactory.getCurrentSession();
		// Transaction t = session.beginTransaction();
		try {
			u.setFullName(vm.getFullName());
			if (vm.getStatus() != null)
				u.setStatus(vm.getStatus());
			u.setAccountType(vm.getAccountType().getValue());
			u.setReceiveNewsletter(vm.getReceiveNewsLetter());

			// RandomStringHelper gen = new RandomStringHelper(8,
			// ThreadLocalRandom.current());
			// String token = gen.nextString();
			//
			// u.setUserHash(CommonHelper.HasPw(token));
			// u.setUserSalt(CommonHelper.HasPw(token));
			u.setProvider(Constant.Provider.NORMAL.getValue());
			u.setPhone(vm.getPhone());
			u.setReceiveNewsletter(vm.getReceiveNewsLetter());

			if (vm.getRoles() != null && vm.getRoles().length > 0) {
				boolean del = userRoleDao.deleteByUser(u.getUserId());
				for (int i = 0; i < vm.getRoles().length; i++) {
					// delete role
					Role r = roleDAO.getRoleByCode(vm.getRoles()[i]);
					if (r != null) {
						UserRole userRole = new UserRole();
						userRole.setRole(r);
						userRole.setUser(u);

						boolean ur = userRoleDao.save(userRole);

					}
				}
			}
			// u.setUserRoles(sur);
			session.update(u);
			// if (vm.getLanguageCode() != null && !vm.getLanguageCode().isEmpty()) {
			// try {
			// // String appUrl = request.getScheme() + "://" + request.getServerName();
			// String appUrl = environment.getProperty("frontend.url");
			// String emailFrom = environment.getProperty("email.from");
			// String siteTitle = environment.getProperty("site.title");
			// String displayEmailName = environment.getProperty("display.email.name");
			//
			// ContentEmaiLViewModel cm =
			// ce.getByType(Constant.EmailType.CreateNewUser.getValue(),
			// vm.getLanguageCode());
			// if (cm != null) {
			// TemplateMatcher matcher = new TemplateMatcher("${", "}");
			// Map<String, String> vars = new HashMap<String, String>();
			// vars.put("url", appUrl);
			// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			// String str_date = dateFormat.format(new Date());
			// vars.put("year", str_date);
			// vars.put("fullName", u.getFullName().isEmpty() ? u.getUserName() :
			// u.getFullName());
			// vars.put("userName", u.getUserName());
			// vars.put("password", token);
			// vars.put("siteName", siteTitle);
			//
			// String body = matcher.replace(cm.getBody(), vars);
			//
			// // title
			//// TemplateMatcher title = new TemplateMatcher("${", "}");
			//// Map<String, String> t = new HashMap<String, String>();
			//// t.put("siteName", siteTitle);
			//// String trpc = title.replace(cm.getSubject(), t);
			// String trpc = cm.getSubject();
			// Executors.newSingleThreadExecutor().execute(new Runnable() {
			// public void run() {
			// try {
			// emailService.sendMessage(emailFrom, vm.getEmail(), trpc, body,
			// displayEmailName);
			// } catch (MessagingException | IOException e) {
			// logger.error(e.toString());
			// }
			// }
			// });
			// }
			//
			// } catch (Exception e) {
			// logger.error(e.toString());
			// }
			// }

			// t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			// t.rollback();
			return false;
		}
	}

	public User findById(final Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			User u = session.get(User.class, id);

			return u;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public List<User> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("FROM User", User.class).getResultList();
	}

	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, String name, Integer status, Integer accountType) {
		Predicate predicate = cb.conjunction();
		if (name != null && !name.isEmpty()) {
			predicate = cb.and(predicate, cb.like(root.get("fullName"), "%" + name + "%"));
			predicate = cb.or(predicate, cb.like(root.get("email"), "%" + name + "%"));
		}
		if (null == status) {
			predicate = cb.and(predicate, cb.notEqual(root.<Integer>get("status"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("status"), status));
		}
		if (null == accountType) {
			predicate = cb.and(predicate,
					cb.notEqual(root.<Integer>get("accountType"), Constant.AccountType.Anonymous.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("accountType"), accountType));
		}
		return predicate;
	}

	public List<User> getAllUserSortByName() throws Exception {
		Session s = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> from = cq.from(User.class);
		cq.select(from).where(toPredicate(from, cb, null, Constant.Status.Publish.getValue(), null));
		cq.orderBy(cb.asc(from.get("userName")));
		List<User> users = s.createQuery(cq).getResultList();
		return users;
	}

	public List<User> getPaging(final int firstResult, final int maxResult, String name, Integer status,
			Integer accountType) throws Exception {
		Session s = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> from = cq.from(User.class);
		cq.select(from).where(toPredicate(from, cb, name, status, accountType));
		cq.orderBy(cb.desc(from.get("createdDate")));
		List<User> users = (maxResult == 0) ? s.createQuery(cq).getResultList()
				: s.createQuery(cq).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
		return users;
	}

	public int countGetAll(String name, Integer status, Integer accountType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<User> countFrom = queryCount.from(User.class);
		queryCount.select(cb.count(countFrom)).where(toPredicate(countFrom, cb, name, status, accountType));
		try {
			Long totalCount = session.createQuery(queryCount).getSingleResult();
			return totalCount.intValue();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}
	}

	public User getUserByEmail(String email, int provider) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session
				.createNativeQuery("SELECT * FROM user u where u.email=:email and u.provider=:prov", User.class)
				.setParameter("email", email).setParameter("prov", provider).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public User getUserByEmail(String email, int provider, int accountType) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session.createNativeQuery(
				"SELECT * FROM user u where u.email=:email and u.provider=:prov and u.account_type=:acType and u.status=:status",
				User.class).setParameter("email", email).setParameter("prov", provider)
				.setParameter("acType", accountType).setParameter("status", Constant.Status.Publish.getValue()).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public User getUserByEmailAndStatus(String email, Integer status) {
		Session session = this.sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		List<Predicate> pre = new ArrayList<>();

		if (status != null) {
			pre.add(cb.equal(root.get("status"), status));
		}
		pre.add(cb.equal(root.get("email"), email));
		cq.select(root).where(pre.stream().toArray(Predicate[]::new));

		List<User> listUser = session.createQuery(cq).getResultList();
		return listUser.isEmpty() ? null : listUser.get(0);
	}

	public User getLoginUserByEmail(String email, int provider) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session
				.createNativeQuery("SELECT * FROM user u where u.email=:email and u.provider=:prov and u.status=:sta",
						User.class)
				.setParameter("email", email).setParameter("prov", provider)
				.setParameter("sta", Constant.Status.Publish.getValue()).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public User getUserByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session.createNativeQuery("SELECT * FROM user u where u.user_name=:un", User.class)
				.setParameter("un", userName).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public User getUserByToken(String token) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session.createNativeQuery("SELECT * FROM user u where u.reset_token=:tk", User.class)
				.setParameter("tk", token).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public Boolean checkAssignExistsed(final Long userId, final Long roleId) {
		Session session = this.sessionFactory.getCurrentSession();
		UserRole userRole = null;
		try {
			CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
			CriteriaQuery<UserRole> query = criteriaBd.createQuery(UserRole.class);
			Root<UserRole> rootUserRole = query.from(UserRole.class);
			query.select(rootUserRole);
			Predicate a = criteriaBd.equal(rootUserRole.get("role").<Long>get("roleId"), roleId);
			Predicate b = criteriaBd.equal(rootUserRole.get("user").<Long>get("userId"), userId);
			query.where(criteriaBd.and(a, b));
			userRole = session.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			// Ignore this because as per your logic this is ok!
		}
		return (userRole == null) ? false : true;
	}

	public Boolean assignRole(final Long userId, final Long roleId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			UserRole userRole = new UserRole();
			userRole.setRole(roleDAO.getById(roleId));
			userRole.setUser(findById(userId));
			session.save(userRole);
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public Boolean removeAssignRole(final Long userId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
			CriteriaDelete<UserRole> delete = criteriaBd.createCriteriaDelete(UserRole.class);
			Root<UserRole> rootUserRole = delete.from(UserRole.class);
			delete.where(criteriaBd.equal(rootUserRole.get("user").<Long>get("userId"), userId));
			session.createQuery(delete).executeUpdate();
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public boolean updateUserInfo(UserInfo u) {
		Session s = sessionFactory.getCurrentSession();

		try {
			s.saveOrUpdate(u);
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}

	}

	public UserInfo getByUserInfoId(final Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			UserInfo u = session.get(UserInfo.class, id);

			return u;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public List<UserInfoResponse> getAllByUserInfo(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {

			List<UserInfo> u = session
					.createQuery("SELECT uf FROM UserInfo uf inner join uf.user u where u.userId=:uId")
					.setParameter("uId", userId).list();

			List<UserInfoResponse> luif = new ArrayList<>();
			if (u != null) {

				for (UserInfo uf : u) {
					UserInfoResponse uif = new UserInfoResponse();
					uif.setContactName(uf.getContactName());
					uif.setEmail(uf.getEmail());
					uif.setEmergencyNumber1(uf.getEmergencyNumber1());
					uif.setEmergencyNumber2(uf.getEmergencyNumber2());
					uif.setWebsite(uf.getWebsite());
					uif.setUserInfoId(uf.getUserInfoId());
					luif.add(uif);
				}

				return luif;
			}
			return null;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

}
