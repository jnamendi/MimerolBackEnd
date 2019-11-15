package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.entities.UserInfo;
import bmbsoft.orderfoodonline.model.UserInfoRequest;
import bmbsoft.orderfoodonline.model.UserInfoResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.ContentEmailService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserInfoController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private UserService userService;

	@Autowired
	private ContentEmailService ce;
	// -------------------Retrieve All
	// Users---------------------------------------------

	@RequestMapping(value = "/admin/user-info/save", method = RequestMethod.POST)
	public ResponseEntity<?> addUserInfo(@RequestBody @Validated UserInfoRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
						if (!permission(Constant.Module.UserInfo, Constant.Action.save)) {
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
			User u = userService.findById(req.getUserId());
			if (u == null) {
				rs.setStatus(0);
				rs.setMessage("User not exits.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			UserInfo uf = new UserInfo();
			uf.setUser(u);
			uf.setContactName(req.getContactName());
			uf.setEmail(req.getEmail());
			uf.setEmergencyNumber1(req.getEmergencyNumber1());
			uf.setEmergencyNumber2(req.getEmergencyNumber2());
			uf.setWebsite(req.getWebsite());

			boolean s = userService.updateUserInfo(uf);
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

	@RequestMapping(value = "/admin/user-info/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserInfo(@PathVariable Long id, @RequestBody @Validated UserInfoRequest req,
			BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.UserInfo, Constant.Action.update)) {
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

			User u = userService.findById(req.getUserId());
			if (u == null) {
				rs.setStatus(7);
				rs.setMessage("User not exits.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			UserInfo uf = userService.getByUserInfoId(req.getUserInfoId());
			if (uf == null) {
				rs.setStatus(0);
				rs.setMessage("UserInfo not exits.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			uf.setUser(u);
			uf.setContactName(req.getContactName());
			uf.setEmail(req.getEmail());
			uf.setEmergencyNumber1(req.getEmergencyNumber1());
			uf.setEmergencyNumber2(req.getEmergencyNumber2());
			uf.setWebsite(req.getWebsite());

			boolean s = userService.updateUserInfo(uf);
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

	@RequestMapping(value = "/api/user-info/get-by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.UserInfo, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (id == null) {
				rs.setStatus(7);
				rs.setMessage("Field is required");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			UserInfo uf = userService.getByUserInfoId(id);
			if (uf == null) {
				rs.setStatus(0);
				rs.setMessage("UserInfo not exits.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			UserInfoResponse uif = new UserInfoResponse();
			uif.setContactName(uf.getContactName());
			uif.setEmail(uf.getEmail());
			uif.setEmergencyNumber1(uf.getEmergencyNumber1());
			uif.setEmergencyNumber2(uf.getEmergencyNumber2());
			uif.setWebsite(uf.getWebsite());

			rs.setStatus(0);
			rs.setMessage("OK");
			rs.setContent(uif);
			rs.setErrorType(Constant.ErrorTypeCommon.OK);

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/user-info/get-all/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUserInfo(@PathVariable Long userId) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.UserInfo, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			List<UserInfoResponse> uf = userService.getAllByUserInfo(userId);
			if (uf == null || uf.size() <= 0) {
				rs.setStatus(0);
				rs.setMessage("UserInfo not exits.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			rs.setStatus(0);
			rs.setMessage("OK");
			rs.setContent(uf);
			rs.setErrorType(Constant.ErrorTypeCommon.OK);
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
}
