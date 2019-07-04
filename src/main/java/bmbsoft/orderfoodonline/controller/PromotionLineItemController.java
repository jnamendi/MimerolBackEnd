package bmbsoft.orderfoodonline.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.model.PromotionLineItemRequest;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.PromotionLineItemService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class PromotionLineItemController {
	public static final Logger logger = LoggerFactory.getLogger(PromotionLineItemController.class);

	@Autowired
	PromotionLineItemService pliService;

	@RequestMapping(value = "/admin/promotion-line-item/save", method = RequestMethod.POST)
	public ResponseEntity<?> addMenu(@RequestBody @Validated List<PromotionLineItemRequest> plirs,
			BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (result.hasErrors()) {
				responseGet.setStatus(7);
				responseGet.setMessage(result.getFieldError().getDefaultMessage());
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			if (plirs == null || plirs.isEmpty()) {
				responseGet.setStatus(7);
				responseGet.setMessage("Input invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			String mesage = this.pliService.create(plirs);
			if (mesage == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage(mesage);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}
}
