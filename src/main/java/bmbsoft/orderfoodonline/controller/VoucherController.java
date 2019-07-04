package bmbsoft.orderfoodonline.controller;

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
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.model.VoucherRequest;
import bmbsoft.orderfoodonline.model.VoucherResponse;
import bmbsoft.orderfoodonline.model.shared.CategoryRequest;
import bmbsoft.orderfoodonline.model.shared.VoucherLiteResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.service.VoucherService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class VoucherController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

	@Autowired
	VoucherService voucherService;

	@Autowired
	LanguageService l;

	@RequestMapping(value = "/api/voucher/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			rs = this.voucherService.getAll(pageIndex, pageSize);
			if (rs.getStatus() != 0)
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/voucher/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			VoucherResponse voucher = voucherService.viewDetail(id);
			if (null == voucher) {
				responseGet.setStatus(7);
				responseGet.setMessage("Voucher not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			responseGet.setStatus(0);
			responseGet.setContent(voucher);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/voucher/getByCode/{voucherCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getByVoucherCode(@PathVariable @Validated String voucherCode) {
		ResponseGet rs = new ResponseGet();
		try {

			if (voucherCode == null || voucherCode.isEmpty()) {
				rs.setStatus(7);
				rs.setMessage("voucherCode not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			VoucherLiteResponse voucher = voucherService.getByCode(voucherCode, null);
			if (null == voucher) {
				rs.setStatus(0);
				rs.setMessage("Voucher not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(voucher);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/voucher/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Validated VoucherRequest model, BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (result.hasErrors()) {
				responseGet.setStatus(7);
				responseGet.setMessage(result.getFieldError().getDefaultMessage());
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			if (voucherService.create(model) == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(1);
			responseGet.setMessage("Error when process the data.");
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/voucher/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Validated VoucherRequest model,
			BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (result.hasErrors()) {
				responseGet.setStatus(7);
				responseGet.setMessage(result.getFieldError().getCode().toString());
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			Voucher c = this.voucherService.getById(id);
			if (null == c) {
				responseGet.setStatus(0);
				responseGet.setMessage("voucher not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			if (voucherService.update(model, c)) {
				responseGet.setStatus(0);
				responseGet.setMessage("update success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(1);
			responseGet.setMessage("Error when process the data");
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

	@RequestMapping(value = "/admin/voucher/delete-many", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody CategoryRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(8);
				rs.setMessage("Ids is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String msg = voucherService.deleteMany(cr.getIds());
			if (msg.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Delete success.");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(99);
			rs.setMessage(msg);
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
