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

	@RequestMapping(value = "/api/PaymentProvider/getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAllProvider(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			List<PaymentProviderViewModel> c = paymentProviderService.getAllProviders();
			if (null == c) {
				rs.setStatus(7);
				rs.setMessage("PaymentProvider invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType("EXCEPTION");
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/PaymentProvider/getByRestaurant/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			List<PaymentProviderViewModel> c = paymentProviderService.getPaymentProvidersByRestaurant(id);
			if (null == c) {
				rs.setStatus(7);
				rs.setMessage("PaymentProvider invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType("EXCEPTION");
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}
	}

}
