package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.model.PaymentProviderViewModel;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.PaymentProviderService;
import bmbsoft.orderfoodonline.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PaymentProviderController {

	public static final Logger logger = LoggerFactory.getLogger(PaymentProviderController.class);
	@Autowired
	PaymentProviderService paymentProviderService;

	@RequestMapping(value = "/api/PaymentProvider/getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAllProvider() {
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
