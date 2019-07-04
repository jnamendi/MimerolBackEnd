package bmbsoft.orderfoodonline.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.model.VoucherLineItemRequest;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.VoucherLineItemService;
import bmbsoft.orderfoodonline.service.VoucherService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class VoucherLineItemController {
	public static final Logger logger = LoggerFactory.getLogger(VoucherLineItemController.class);

	@Autowired
	VoucherLineItemService pliService;

	@Autowired
	VoucherService vs;

	@RequestMapping(value = "/admin/voucher-line-item/save", method = RequestMethod.POST)
	public ResponseEntity<?> addMenu(@RequestBody VoucherLineItemRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getCode().toString());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (req == null) {
				rs.setStatus(7);
				rs.setMessage("Input invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			Voucher p = vs.getById(req.getVoucherId());
			if (p == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found VoucherId.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			String mesage = this.pliService.create(req, p);
			if (mesage == null || mesage.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("save success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage(mesage);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

}
