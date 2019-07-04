package bmbsoft.orderfoodonline.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import bmbsoft.orderfoodonline.entities.Address;
import bmbsoft.orderfoodonline.entities.Role;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.entities.UserRestaurant;
import bmbsoft.orderfoodonline.model.ActiveAccountViewModel;
import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.model.ChangePasswordViewModel;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.model.FacebookViewModel;
import bmbsoft.orderfoodonline.model.ResendEmailRequest;
import bmbsoft.orderfoodonline.model.RoleModel;
import bmbsoft.orderfoodonline.model.UserRegisterViewModel;
import bmbsoft.orderfoodonline.model.UserViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.AddressService;
import bmbsoft.orderfoodonline.service.ContentEmailService;
import bmbsoft.orderfoodonline.service.EmailService;
import bmbsoft.orderfoodonline.service.FBGraphService;
import bmbsoft.orderfoodonline.service.JwtService;
import bmbsoft.orderfoodonline.service.RoleService;
import bmbsoft.orderfoodonline.service.UserRestaurantService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import bmbsoft.orderfoodonline.util.Constant.Provider;
import bmbsoft.orderfoodonline.util.RandomStringHelper;
import jlibs.core.util.regex.TemplateMatcher;

@RestController
@CrossOrigin
public class UserController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	Gson gson = new Gson();

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRestaurantService urService;
	
	@Autowired
	AddressService ase;
	
	@Autowired
	private ContentEmailService ce;
	// -------------------Retrieve All
	// Users---------------------------------------------

	@RequestMapping(value = "/api/user/getUser/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> listAllUsers(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "accountType", required = false) Integer accountType,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging rs = new ResponseGetPaging();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = this.userService.getPaging(pageIndex, pageSize, name, status, accountType);
			if (rs.getStatus() != 0)
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGetPaging>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/user/get-all-user-sort-by-name", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUserSortByName() {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.getAllSortByName)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			List<UserViewModel> u = userService.getAllUserSortByName();
			if (u == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(u);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	// -------------------Retrieve Single
	// User------------------------------------------

	@RequestMapping(value = "/api/user/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Id invalid");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			User u = userService.findById(id);
			if (u == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			UserViewModel vm = MappToModel(u);
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(vm);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/admin/user/save", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody @Validated UserViewModel vm, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.save)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getAllErrors().toString());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			boolean b = userService.checkExistEmail(vm.getEmail(), Constant.Provider.NORMAL.getValue());
			if (b) {
				rs.setStatus(2);
				rs.setMessage("Exist an email.");
				rs.setErrorType(Constant.ErrorTypeCommon.EMAIL_EXISTS);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			boolean s = userService.save(vm);
			if (s) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data.");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	// ------------------- Update a User
	// ------------------------------------------------

	@RequestMapping(value = "/admin/user/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserViewModel vm) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.update)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Updating User with id {}", id);
			User currentUser = userService.findById(id);
			if (currentUser == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			if (!currentUser.getEmail().equals(vm.getEmail())) {
				boolean b = userService.checkExistEmail(vm.getEmail(), Constant.Provider.NORMAL.getValue());
				if (b) {
					rs.setStatus(2);
					rs.setMessage("Exist an email.");
					rs.setErrorType(Constant.ErrorTypeCommon.EMAIL_EXISTS);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}
			}

			boolean s = userService.update(vm, currentUser);
			if (s) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}

			rs.setStatus(1);
			rs.setMessage("Error when process the data. ");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);

		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/admin/user/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Fetching & Deleting User with id {}", id);
			User user = userService.findById(id);
			if (user == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			user.setStatus(Constant.Status.Deleted.getValue());
			user.setModifiedDate(new Date());

			boolean s = userService.delete(user);
			if (s) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}

			rs.setStatus(1);
			rs.setMessage("Error when process the data. ");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/user/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.User, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				User user = userService.findById(id);
				if (user == null) {
					s.append("Could not found item: " + id);
				} else {
					user.setStatus(Constant.Status.Deleted.getValue());
					user.setModifiedDate(new Date());

					userService.delete(user);
				}
			}
			if (s.toString().isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Delete success.");
				rs.setErrorType(Constant.ErrorTypeCommon.OK);
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(6);
			rs.setMessage(s.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.DELETE_MANY);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserRegisterViewModel user) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			logger.info("------------Validate");
			if (user == null) {
				rs.setStatus(4);
				rs.setMessage("Request is null.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUEST_IS_NULL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (user.getProvider() == null) {
				rs.setStatus(5);
				rs.setMessage("Field provider is required.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			User en = null;
			boolean ischeck = false;

			String result = "";
			logger.info("------------Provider=" + user.getProvider());
			if (user.getProvider() == Provider.FACEBOOK || user.getProvider() == Provider.GOOGLE) {
				logger.info("------------face or google");
				if (user.getId().isEmpty()) {
					rs.setStatus(5);
					rs.setMessage("Field Id is required.");
					rs.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}
				if (user.getAuthToken().isEmpty()) {
					rs.setStatus(5);
					rs.setMessage("Field AuthToken is required.");
					rs.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}

				boolean isEmail = CommonHelper.isValidEmailAddress(user.getEmail());
				if (!isEmail) {
					rs.setStatus(3);
					rs.setMessage("Invalid email");
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_EMAIL);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}

				logger.info("------------get by id");
				en = userService.getUserByUserName(user.getId());

				if (en != null) {

					logger.info("------------check status");
					if (en.getStatus() != Constant.Status.Publish.getValue()) {
						rs.setStatus(50);
						rs.setMessage("Account is deleted");
						rs.setErrorType(Constant.ErrorCodeUserApi.ACCOUNT_IS_DELETE);

						return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
					}

					logger.info("------------check authen");
					if (user.getProvider() == Provider.FACEBOOK) {
						FacebookViewModel f = new FacebookViewModel();
						FBGraphService fs = new FBGraphService(user.getAuthToken());
						f = fs.getResFb();

						if (f == null || !f.getId().equals(en.getUserName())) {
							rs.setStatus(5);
							rs.setMessage("Account not exist.");
							rs.setErrorType(Constant.ErrorCodeUserApi.INVALID_ACCOUNT);
							return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
						}

					} else if (user.getProvider() == Provider.GOOGLE) {
						// FBGraphService fbGraph = new FBGraphService("");
						// String graph = fbGraph.getFBGraph();
						// Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

					}

					logger.info("------------user valid");
					ischeck = true;
				} else {
					// return register
					logger.info("------------register new user facebook or g+");
					en = new User();
					en.setUserHash(user.getId());
					en.setUserSalt(user.getId());
					en.setProvider(user.getProvider().getValue());
					en.setUserName(user.getId());

					en.setEmail(user.getEmail());
					en.setCreatedDate(new Date());
					en.setFullName(user.getName() == null || user.getName().isEmpty()
							? user.getFirstName() + " " + user.getLastName()
							: user.getName());
					en.setStatus(Constant.Status.Publish.getValue());
					en.setIsLock(false);
					en.setAccountType(Constant.AccountType.Private.getValue());
					en.setReceiveNewsletter(false);

					String json1 = gson.toJson(user);
					en.setOriginalData(json1);
					logger.info("------------save ");
					boolean s = userService.save(en);
					if (s) {
						result = jwtService.generateTokenLogin(user.getEmail());
						UserViewModel vm = MappToModel(en);
						vm.setToken(result);

						vm.setToken(result);
						rs.setMessage("ok");
						rs.setContent(vm);
						logger.info("------------#End");
						return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
					} else {
						logger.info("------------#Error");
						rs.setStatus(1);
						rs.setMessage("Error when process the data.");
						rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
					}
				}
			} else {
				logger.info("------------Check validate");
				boolean isEmail = CommonHelper.isValidEmailAddress(user.getEmail());
				if (!isEmail) {
					rs.setStatus(3);
					rs.setMessage("Invalid email");
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_EMAIL);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}
				logger.info("------------get by Email");
				en = userService.getLoginUserByEmail(user.getEmail(), user.getProvider().getValue());
				if (en == null) {
					rs.setStatus(57);
					rs.setMessage("Account not exist.");
					rs.setErrorType(Constant.ErrorCodeUserApi.INVALID_ACCOUNT);
					httpStatus = HttpStatus.BAD_REQUEST;

					return new ResponseEntity<ResponseGet>(rs, httpStatus);
				}
				logger.info("------------Check status");
				if (en.getStatus() == Constant.Status.Deleted.getValue()) {
					rs.setStatus(50);
					rs.setMessage("Account is deleted");
					rs.setErrorType(Constant.ErrorCodeUserApi.ACCOUNT_IS_DELETE);
					httpStatus = HttpStatus.BAD_REQUEST;

					return new ResponseEntity<ResponseGet>(rs, httpStatus);
				}
				logger.info("------------Check pass");
				boolean isPass = CommonHelper.CheckPw(user.getPassword(), en.getUserHash());
				if (isPass) {
					ischeck = true;
				} else {
					rs.setStatus(51);
					rs.setMessage("Password not match.");
					rs.setErrorType(Constant.ErrorCodeUserApi.PASSWORD_NOT_MATCH);
					httpStatus = HttpStatus.BAD_REQUEST;

					return new ResponseEntity<ResponseGet>(rs, httpStatus);
				}
				logger.info("------------check user inactive");
				if (en.getStatus() == Constant.Status.InActive.getValue()) {
					rs.setStatus(58);
					rs.setMessage("Account not active.");
					rs.setErrorType(Constant.ErrorCodeUserApi.ACCOUNT_NOT_ACTIVE);
					httpStatus = HttpStatus.BAD_REQUEST;

					return new ResponseEntity<ResponseGet>(rs, httpStatus);
				}
			}
			if (ischeck) {
				result = jwtService.generateTokenLogin(user.getEmail());
				rs.setStatus(0);

				UserViewModel vm = MappToModel(en);
				vm.setToken(result);
				rs.setContent(vm);
				rs.setMessage("Ok");
				logger.info("------------#End");
				httpStatus = HttpStatus.OK;
			} else {
				logger.info("------------#Error");
				rs.setStatus(1);
				rs.setMessage("Error when process the data.");
				rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/user/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody UserViewModel req, HttpServletRequest request) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {

			logger.info("------------#Begin register");
			logger.info("------------Validate");
			if (req == null) {
				rs.setStatus(4);
				rs.setMessage("Request is null.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUEST_IS_NULL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (req.getEmail() == null || req.getEmail().isEmpty()) {
				rs.setStatus(5);
				rs.setMessage("Field Email is required.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			boolean isEmail = CommonHelper.isValidEmailAddress(req.getEmail());
			if (!isEmail) {
				rs.setStatus(3);
				rs.setMessage("Invalid email.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_EMAIL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Check exist email");
			User eu = userService.getUserByEmail(req.getEmail(), Constant.Provider.NORMAL.getValue());
			// todo check cho nay
			if (eu != null && eu.getStatus() != Constant.Status.Deleted.getValue()) {
				rs.setStatus(2);
				rs.setMessage("Exist an email.");
				rs.setErrorType(Constant.ErrorTypeCommon.EMAIL_EXISTS);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			logger.info("------------Create user");
			User u = new User();

			String hpw = CommonHelper.HasPw(req.getPassword());
			u.setUserHash(hpw);
			u.setUserSalt(hpw);
			u.setProvider(Constant.Provider.NORMAL.getValue());
			u.setUserName(req.getEmail());
			u.setFullName(req.getFullName());
			u.setEmail(req.getEmail());
			u.setCreatedDate(new Date());
			u.setStatus(Constant.Status.InActive.getValue());
			u.setReceiveNewsletter(true);
			u.setAccountType(Constant.AccountType.Private.getValue());
			u.setIsLock(false);

			// reset pass
			req.setPassword("xxxxxxxx");
			// create rolse default
			String json1 = gson.toJson(req);
			u.setOriginalData(json1);
			String token = UUID.randomUUID().toString();
			u.setResetToken(token);
			Date d = new Date();
			d.setMinutes(Constant.EXPIRATION);
			u.setExpiredDate(d);
			u.setStatus(Constant.Status.InActive.getValue());

			boolean s = userService.save(u);
			if (s) {
				String result = jwtService.generateTokenLogin(u.getEmail());
				rs.setStatus(0);
				rs.setMessage("ok");

				UserViewModel vm = MappToModel(u);
				vm.setToken(result);
				rs.setContent(vm);
				// send email
				try {
					logger.info("------------Send mail");
					// String appUrl = request.getScheme() + "://" + request.getServerName();
					String appUrl = environment.getProperty("fontend.url");
					String emailFrom = environment.getProperty("email.from");
					String siteTitle = environment.getProperty("site.title");
					String displayEmailName = environment.getProperty("display.email.name");

					ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.Register.getValue(),
							req.getLanguageCode());
					if (cm != null) {
						TemplateMatcher matcher = new TemplateMatcher("${", "}");
						Map<String, String> vars = new HashMap<String, String>();
						vars.put("url", appUrl);
						String urlV = appUrl + "/verify?token=" + token;
						vars.put("urlVerify", urlV);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
						String str_date = dateFormat.format(new Date());
						vars.put("year", str_date);
						vars.put("siteName", siteTitle);
						String body = matcher.replace(cm.getBody(), vars);

						Executors.newSingleThreadExecutor().execute(new Runnable() {
							public void run() {
								try {
									logger.info("------------send to " + req.getEmail());
									emailService.sendMessage(emailFrom, req.getEmail(), cm.getSubject(), body,
											displayEmailName);
									// emailService.sendEmail(email);(email);(passwordResetEmail);
								} catch (MessagingException | IOException e) {
									logger.error(e.toString());
								}
							}
						});
					}

				} catch (Exception e) {
					logger.error(e.toString());
				}
				logger.info("------------#End register");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			} else {
				logger.info("------------#Error");
				rs.setStatus(1);
				rs.setMessage("Error when process the data.");
				rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(4);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/user/check-exist-user/{email}/{provider}", method = RequestMethod.GET)
	public ResponseEntity<?> checkExistUser(@RequestParam("email") String email,
			@RequestParam("provider") int provider) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			boolean u = userService.checkExistEmail(email, provider);

			if (u) {
				rs.setStatus(0);
				rs.setMessage("ok");
				rs.setContent(u);
				httpStatus = HttpStatus.OK;
			} else {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/user/forgot-password", method = RequestMethod.POST)
	public ResponseEntity<?> forgotPassword(@RequestBody ResendEmailRequest req, HttpServletRequest request) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			logger.info("------------#Begin forgot-pass");
			if (req == null) {
				rs.setStatus(4);
				rs.setMessage("Request is null.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUEST_IS_NULL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Validate email");
			if (req.getEmail().isEmpty()) {
				rs.setStatus(5);
				rs.setMessage("Field email is required.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			boolean isEmail = CommonHelper.isValidEmailAddress(req.getEmail());
			if (!isEmail) {
				rs.setStatus(3);
				rs.setMessage("Invalid email.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_EMAIL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------get by email");
			User u = userService.getUserByEmail(req.getEmail(), Constant.Provider.NORMAL.getValue());
			if (u == null) {
				rs.setStatus(57);
				rs.setMessage("Account not exist.");
				rs.setErrorType(Constant.ErrorCodeUserApi.INVALID_ACCOUNT);

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Check user is delted");
			if (u.getStatus() != Constant.Status.Publish.getValue()) {
				rs.setStatus(57);
				rs.setMessage("Account not exist.");
				rs.setErrorType(Constant.ErrorCodeUserApi.INVALID_ACCOUNT);
				httpStatus = HttpStatus.BAD_REQUEST;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			//
			try {
				// String token = UUID.randomUUID().toString();
				RandomStringHelper gen = new RandomStringHelper(8, ThreadLocalRandom.current());
				String token = gen.nextString();
				logger.info("------------Sendmail");
				boolean s = userService.createPasswordResetTokenForUser(u, token);
				if (s) {
					try {
						// String appUrl = request.getScheme() + "://" + request.getServerName();
						String appUrl = environment.getProperty("fontend.url");
						String emailFrom = environment.getProperty("email.from");
						String siteTitle = environment.getProperty("site.title");
						String displayEmailName = environment.getProperty("display.email.name");

						ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.ChangePass.getValue(),
								req.getLanguageCode());
						if (cm != null) {
							TemplateMatcher matcher = new TemplateMatcher("${", "}");
							Map<String, String> vars = new HashMap<String, String>();
							vars.put("url", appUrl);
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
							String str_date = dateFormat.format(new Date());
							vars.put("year", str_date);
							vars.put("fullName", u.getFullName().isEmpty() ? u.getUserName() : u.getFullName());
							vars.put("userName", u.getUserName());
							vars.put("newpass", token);
							vars.put("siteName", siteTitle);

							String body = matcher.replace(cm.getBody(), vars);

							Executors.newSingleThreadExecutor().execute(new Runnable() {
								public void run() {
									try {
										emailService.sendMessage(emailFrom, req.getEmail(), cm.getSubject(), body,
												displayEmailName);
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
				logger.info("------------#End");
				rs.setStatus(0);
				rs.setMessage("A new password has been created for your account. please check your email.");
				rs.setContent(new HashMap() {
					{
						put("userName", u.getUserName());
					}
				});
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				//
				rs.setStatus(1);
				rs.setMessage(e.toString());
				rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
				rs.setContent("");
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/user/check-sendmail", method = RequestMethod.POST)
	public ResponseEntity<?> checkSendmail(HttpServletRequest request) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {

			// String appUrl = request.getScheme() + "://" + request.getServerName();
			String appUrl = environment.getProperty("fontend.url");
			String emailFrom = environment.getProperty("email.from");
			String dn = environment.getProperty("display.email.name");
			RandomStringHelper gen = new RandomStringHelper(8, ThreadLocalRandom.current());
			String token = gen.nextString();

			// Email message
			// SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			// passwordResetEmail.setFrom(emailFrom);
			// passwordResetEmail.setTo("bmbsoft.corp@gmail.com");
			// passwordResetEmail.setSubject("Password Reset Request");
			// passwordResetEmail
			// .setText("To the active account, click the link below:\n" + appUrl +
			// "/verify?token=" + token);

			// ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.Verify.getValue(),
			// "en-US");

			// String t = "<p>New password for your account</p>\r\n"
			// + "<p>A new password has been created for your account with
			// Vietnammm.com!</p>\r\n" + "\r\n"
			// + "<p>Hello do van dap,</p> \r\n" + "\r\n"
			// + "<p>You have just requested a new password for your customer account with
			// Vietnammm.com.\r\n"
			// + "Your new password and other account data are listed below.</p> \r\n" +
			// "\r\n"
			// + "<p>Username: dapdv@live.com</p> \r\n" + "<p>New password: z9kjb37f</p>
			// \r\n"
			// + "<p><a href=\"https://www.vietnammm.com/verify?token=82622fb00fc15a87ec22\"
			// target=\"_blank\" >XÁC NHẬN TÀI KHOẢN</a></p>\r\n"
			// + "<p>Yours sincerely, </p> \r\n" + "<p>Vietnammm.com</p> ";
			// emailService.sendMessage(emailFrom, "bmbsoft.corp@gmail.com", "Password Reset
			// Request", t + token,
			// "dap.dv@bmb");
			String a = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\"> \r\n" + "</head>\r\n" + "\r\n"
					+ "<body>\r\n"
					+ "    <div class=\"email-confirm\" style=\"float: left; width: 50%; margin: 0 auto\">\r\n"
					+ "        <div class=\"email-header\" style=\"float: left; width: 100%;\">\r\n"
					+ "            <img style=\"width: 65px; float: left;\" src=\"${url}/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n"
					+ "            <h2 style=\"float: right; color: #777474; margin-left: 20px;\">${name} Almost done! Please confirm your account.</h2>\r\n"
					+ "        </div> \r\n" + "    </div>\r\n" + "</body>\r\n" + "\r\n" + "</html>";

			// String b = String.format(a, "dapdv");
			String link = appUrl + "/verify?token=" + "dddd";
			TemplateMatcher matcher = new TemplateMatcher("${", "}");
			Map<String, String> vars = new HashMap<String, String>();
			vars.put("url", link);
			vars.put("name", "dap.dv");
			String body = matcher.replace(a, vars);

			Executors.newSingleThreadExecutor().execute(new Runnable() {
				public void run() {
					try {
						emailService.sendMessage(emailFrom, "bmbsoft.corp@gmail.com", "Test mail", body, dn);
						// emailService.sendEmail(email);(email);(passwordResetEmail);
					} catch (MessagingException | IOException e) {
						logger.error(e.toString());
					}
				}
			});

			httpStatus = HttpStatus.OK;
			rs.setStatus(0);
			rs.setMessage("Ok");

			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/user/change-pass", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordViewModel vm) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			logger.info("------------#Begin change pass");
			// check vm
			if (vm == null || vm.getUserId() == null || vm.getUserId() == null) {
				rs.setStatus(4);
				rs.setMessage("Request is null.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUEST_IS_NULL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Get user");
			// get user
			User u = userService.findById(vm.getUserId());
			if (u == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------#Check old pass");
			// check old pas
			boolean isPass = CommonHelper.CheckPw(vm.getOldPassword(), u.getUserHash());
			if (!isPass) {
				rs.setStatus(59);
				rs.setMessage("Old password is not match.");
				rs.setErrorType(Constant.ErrorCodeUserApi.OLD_PASS_NOT_MATCH);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Change pass");
			// change pass
			String pw = CommonHelper.HasPw(vm.getNewPassword());
			u.setUserHash(pw);
			u.setUserSalt(pw);
			u.setModifiedDate(new Date());
			u.setResetToken(null);
			u.setExpiredDate(null);

			boolean s = userService.save(u);
			if (s) {
				rs.setStatus(0);
				rs.setMessage("Change pass success.");
				httpStatus = HttpStatus.OK;
				logger.info("------------#End");
				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/user/verify", method = RequestMethod.POST)
	public ResponseEntity<?> activeAccount(@RequestBody ActiveAccountViewModel vm) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			logger.info("------------#Begin verify");

			// check vm
			if (vm == null || vm.getToken().isEmpty()) {
				rs.setStatus(4);
				rs.setMessage("Request is null.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUEST_IS_NULL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------get by token");
			// get user
			User u = userService.getUserByToken(vm.getToken());
			if (u == null) {
				rs.setStatus(57);
				rs.setMessage("Account not exist.");
				rs.setErrorType(Constant.ErrorCodeUserApi.INVALID_ACCOUNT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (u.getStatus() == Constant.Status.Publish.getValue()) {
				rs.setStatus(57);
				rs.setMessage("Account is actived.");
				rs.setErrorType(Constant.ErrorCodeUserApi.ACCOUNT_ACTIVE);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (u.getStatus() != Constant.Status.InActive.getValue()) {
				rs.setStatus(57);
				rs.setMessage("Account not exist.");
				rs.setErrorType(Constant.ErrorCodeUserApi.INVALID_ACCOUNT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			logger.info("------------check Exprired");
			// check token expired
			Calendar cal = Calendar.getInstance();
			if ((u.getExpiredDate().getTime() - cal.getTime().getTime()) <= 0) {

				rs.setStatus(60);
				rs.setMessage("Token was expired.");
				rs.setErrorType(Constant.ErrorCodeUserApi.TOKEN_EXPIRED);
				rs.setContent(new HashMap() {
					{
						put("email", u.getEmail());
					}
				});
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			// bind data
			u.setModifiedDate(new Date());
			u.setStatus(Constant.Status.Publish.getValue());
			logger.info("------------Update usert");

			boolean s = userService.save(u);
			if (s) {
				String result = jwtService.generateTokenLogin(u.getEmail());
				rs.setStatus(0);
				rs.setMessage("Active account success.");
				rs.setContent(new HashMap() {
					{
						put("token", result);
						put("fullName", u.getFullName().isEmpty() ? u.getUserName() : u.getFullName());
						put("userId", u.getUserId());
						put("role", userService.setRoleNewUser(u.getUserId()));
					}
				});
				httpStatus = HttpStatus.OK;
				logger.info("------------#End");
				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			logger.info("------------#Error");
			rs.setStatus(1);
			rs.setMessage("Error when process the data.");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/user/resend-email", method = RequestMethod.POST)
	public ResponseEntity<?> reSendmail(@RequestBody ResendEmailRequest req, HttpServletRequest request) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			logger.info("------------#Begin resend-email");
			if (req == null) {
				rs.setStatus(4);
				rs.setMessage("Request is null.");
				rs.setErrorType(Constant.ErrorTypeCommon.REQUEST_IS_NULL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Validate email");
			boolean isEmail = CommonHelper.isValidEmailAddress(req.getEmail());
			if (!isEmail) {
				rs.setStatus(3);
				rs.setMessage("Invalid email.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_EMAIL);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			logger.info("------------Get by Email");
			User eu = userService.getUserByEmail(req.getEmail(), Constant.Provider.NORMAL.getValue());
			if (eu == null) {
				rs.setStatus(2);
				rs.setMessage("Exist an email.");
				rs.setErrorType(Constant.ErrorTypeCommon.EMAIL_EXISTS);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String token = UUID.randomUUID().toString();
			eu.setResetToken(token);
			Date d = new Date();
			d.setMinutes(Constant.EXPIRATION);
			eu.setExpiredDate(d);

			logger.info("------------Save");
			userService.save(eu);

			// resend pass
			try {
				String appUrl = environment.getProperty("fontend.url");
				String emailFrom = environment.getProperty("email.from");
				String siteTitle = environment.getProperty("site.title");
				String displayEmailName = environment.getProperty("display.email.name");
				logger.info("------------Send email");
				ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.Register.getValue(), req.getLanguageCode());
				if (cm != null) {
					TemplateMatcher matcher = new TemplateMatcher("${", "}");
					Map<String, String> vars = new HashMap<String, String>();
					vars.put("url", appUrl);
					String urlV = appUrl + "/verify?token=" + token;
					vars.put("urlVerify", urlV);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
					String str_date = dateFormat.format(new Date());
					vars.put("year", str_date);
					vars.put("siteName", siteTitle);
					String body = matcher.replace(cm.getBody(), vars);

					Executors.newSingleThreadExecutor().execute(new Runnable() {
						public void run() {
							try {
								emailService.sendMessage(emailFrom, req.getEmail(), cm.getSubject(), body,
										displayEmailName);
								// emailService.sendEmail(email);(email);(passwordResetEmail);
							} catch (MessagingException | IOException e) {
								logger.error(e.toString());
							}
						}
					});
				}

			} catch (Exception e) {
				logger.error(e.toString());
				rs.setStatus(1);
				rs.setMessage(e.getMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
				httpStatus = HttpStatus.BAD_REQUEST;
			}

			httpStatus = HttpStatus.OK;
			rs.setStatus(0);
			rs.setMessage("Resend email success.");
			logger.info("------------#End");
			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			rs.setStatus(1);
			rs.setMessage(ex.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/user/assign-role/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> assignRole(@PathVariable long id,
			@RequestParam(value = "roleIds", required = false) @PathVariable Long[] roleIds) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (null == roleIds) {
				responseGet.setStatus(5);
				responseGet.setMessage("Field roleIds is required.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			String message = userService.Assign(id, roleIds);
			if (null == message || message.isEmpty()) {
				responseGet.setStatus(0);
				responseGet.setMessage("assign role for user id " + id + " success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(1);
			responseGet.setMessage(message);
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/user/update-assign-role/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAssignRole(@PathVariable long id,
			@RequestParam(value = "roleIds", required = false) @PathVariable Long[] roleIds) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (null == roleIds) {
				responseGet.setStatus(5);
				responseGet.setMessage("Field roleIds is required.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.REQUIRED_FIELD);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			String message = userService.updateAssign(id, roleIds);
			if (null == message || message.isEmpty()) {
				responseGet.setStatus(0);
				responseGet.setMessage("assign role for user id " + id + " success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(1);
			responseGet.setMessage(message);
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/check-face/{accToken}", method = RequestMethod.GET)
	public ResponseEntity<?> checkFace(@RequestParam String accToken) {

		FacebookViewModel f = new FacebookViewModel();
		FBGraphService fs = new FBGraphService(accToken);
		f = fs.getResFb();

		return new ResponseEntity<FacebookViewModel>(f, HttpStatus.OK);
	}

	private UserViewModel MappToModel(User u) {
		UserViewModel vm = new UserViewModel();
		vm.setFullName(u.getFullName());
		vm.setEmail(u.getEmail());
		vm.setPhone(u.getPhone());
		vm.setUserId(u.getUserId());
		vm.setAccountType(u.getAccountType() != null ? Constant.AccountType.valueOf(u.getAccountType()) : null);
		vm.setReceiveNewsLetter(u.getReceiveNewsletter());
		vm.setUserName(u.getUserName());
		vm.setProvider(Constant.Provider.valueOf(u.getProvider()));
		vm.setRoles(userService.setRoleNewUser(u.getUserId()));
		vm.setStatus(u.getStatus());

		vm.setAddresses(ase.getByUserId(u.getUserId())); 

		return vm;
	}

}
