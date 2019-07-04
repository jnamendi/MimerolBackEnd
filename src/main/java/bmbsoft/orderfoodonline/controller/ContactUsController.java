package bmbsoft.orderfoodonline.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.City;
import bmbsoft.orderfoodonline.entities.ContactUs;
import bmbsoft.orderfoodonline.entities.ContentEmail;
import bmbsoft.orderfoodonline.model.CityViewModel;
import bmbsoft.orderfoodonline.model.ContactUsViewModel;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.ContactUsService;
import bmbsoft.orderfoodonline.service.ContentEmailService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class ContactUsController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(ContactUsController.class);

	@Autowired
	ContactUsService fs;

	@RequestMapping(value = "/api/contact-us/getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		List<ContactUsViewModel> c = null;
		try {
			// permission
			if (!permission(Constant.Module.ContactUs, Constant.Action.getAll)) {
				responseGetPaging.setStatus(7);
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGetPaging.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
			}
			c = fs.getAll();
			if (null == c || c.isEmpty()) {
				responseGetPaging.setStatus(8);
				responseGetPaging.setMessage("Could not found item.");
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
			}
			Data rsm = new Data();
			rsm.setTotalCount(c.size());
			rsm.setPageIndex(0);
			rsm.setPageSize(Constant.PageNumber);
			rsm.setData(c);
			responseGetPaging.setStatus(0);
			responseGetPaging.setContent(rsm);
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGetPaging.setStatus(1);
			responseGetPaging.setMessage(e.toString());
			responseGetPaging.setContent(null);
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/contact-us/save", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> getAll(@RequestBody ContactUsViewModel vm) {
		ResponseGet rs = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.ContactUs, Constant.Action.save)) {
//				rs.setStatus(7);
//				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				rs.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
//			}
			if (vm == null) {
				rs.setStatus(11);
				rs.setMessage("Field required ");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			boolean isEmail = CommonHelper.isValidEmailAddress(vm.getContactEmail());
			if (!isEmail) {
				rs.setStatus(13);
				rs.setMessage("Invalid email");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			String s = fs.save(vm);
			if (!s.isEmpty()) {
				rs.setStatus(8);
				rs.setMessage(s);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(8);
			rs.setMessage("Could not found item.");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/contact-us/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.ContactUs, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				ContactUsViewModel c = fs.getById(id);
				if (c != null) {
					ContactUs ct = new ContactUs();
					ct.setIsStatus(Constant.Status.Deleted.getValue());
					ct.setContactId(c.getContactId());
					fs.update(c);
				} else {
					s.append("Could not found item: " + id);
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

}
