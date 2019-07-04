package bmbsoft.orderfoodonline.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.PaymentProvider;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.Category;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.model.PaymentProviderViewModel;
import bmbsoft.orderfoodonline.model.shared.CategoryRequest;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.model.shared.PaymentProviderRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.DistrictService;
import bmbsoft.orderfoodonline.service.PaymentProviderService;
import bmbsoft.orderfoodonline.service.PaymentProviderService;
import bmbsoft.orderfoodonline.service.ResidenceTypeService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class PaymentProviderController {

	public static final Logger logger = LoggerFactory.getLogger(PaymentProviderController.class);
	@Autowired
	PaymentProviderService paymentProviderService;

	@RequestMapping(value = "/api/PaymentProvider/payment-provider/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "paymentName", required = false) String paymentName) {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		try {
			responseGetPaging = paymentProviderService.getAll(pageIndex, pageSize, paymentName);
			if (responseGetPaging == null) {
				responseGetPaging.setStatus(0);
				responseGetPaging.setMessage("Could not found items.");
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
			}
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGetPaging.setStatus(1);
			responseGetPaging.setMessage(e.toString());
			responseGetPaging.setContent(null);
			responseGetPaging.setErrorType("EXCEPTION");
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/PaymentProvider/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			PaymentProviderViewModel c = paymentProviderService.getById(id);
			if (null == c) {
				rs.setStatus(7);
				rs.setMessage("PaymentProvider invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType("EXCEPTION");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/PaymentProvider/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Validated PaymentProviderRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			PaymentProvider r = new PaymentProvider();
			r.setCreatedDate(new Date());
			r.setIsStatus(req.getStatus());
			r.setModifiedDate(new Date());
			r.setName(req.getName());
			r.setSetting(req.getSetting());

			if (paymentProviderService.save(r)) {
				rs.setStatus(0);
				rs.setMessage("save success.");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data.");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType("EXCEPTION");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/PaymentProvider/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			PaymentProvider c = paymentProviderService.getBaseById(id);
			if (null == c) {
				rs.setStatus(0);
				rs.setMessage("PaymentProvider not exists.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			if (paymentProviderService.delete(c)) {
				rs.setStatus(0);
				rs.setMessage("delete success.");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data.");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/PaymentProvider/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Ids is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				PaymentProvider c = paymentProviderService.getBaseById(id);
				if (c != null) {
					paymentProviderService.delete(c);
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
