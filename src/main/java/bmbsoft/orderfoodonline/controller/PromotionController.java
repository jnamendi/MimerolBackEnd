package bmbsoft.orderfoodonline.controller;

import java.util.List;
import java.util.Set;

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

import bmbsoft.orderfoodonline.entities.Promotion;
import bmbsoft.orderfoodonline.entities.PromotionLineitem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.model.PromotionRequest;
import bmbsoft.orderfoodonline.model.PromotionResponse;
import bmbsoft.orderfoodonline.model.shared.PromotionLineitemResponse;
import bmbsoft.orderfoodonline.model.shared.PromotionLiteResponse;
import bmbsoft.orderfoodonline.model.shared.VoucherLiteResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.PromotionLineItemService;
import bmbsoft.orderfoodonline.service.PromotionService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class PromotionController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(PromotionController.class);

	@Autowired
	PromotionService promotionService;

	@Autowired
	RestaurantService res;

	@Autowired
	PromotionLineItemService pis;

	@RequestMapping(value = "/api/promotion/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "languageCode", required = false) String languageCode,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = this.promotionService.getAll(pageIndex, pageSize, languageCode, status);
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

	@RequestMapping(value = "/api/promotion/get-all-by-owner/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllByOwner(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "languageCode", required = false) String languageCode,
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.getAllByOwner)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = this.promotionService.getAllByOwner(pageIndex, pageSize, languageCode, userId, status);
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

	@RequestMapping(value = "/api/promotion/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			PromotionResponse promotion = promotionService.getResponseById(id);
			if (null == promotion) {
				rs.setStatus(0);
				rs.setMessage("Promotion not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(promotion);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/promotion/get-multi-code-by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMultiCodeById(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.getMultiCoreById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			PromotionResponse promotion = promotionService.getMultiCodeByid(id);
			if (null == promotion) {
				rs.setStatus(0);
				rs.setMessage("Promotion not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(promotion);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/promotion/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Validated PromotionRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.save)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			// check restaurant
			Restaurant r = res.getById(req.getRestaurantId());
			if (r == null) {
				rs.setStatus(8);
				rs.setMessage("Restaurant not found");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			// check code
			boolean p = pis.getByOnlyCode(req.getCode());
			if (p) {
				rs.setStatus(7);
				rs.setMessage("Code is exist.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String msg = promotionService.create(req, r);
			if (msg.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("save success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(1);
			rs.setMessage(msg);
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/promotion/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Validated PromotionRequest req,
			BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.update)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			PromotionResponse c = this.promotionService.viewDetail(id);
			if (null == c) {
				rs.setStatus(0);
				rs.setMessage("promotion not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			// check restaurant
			Restaurant r = res.getById(req.getRestaurantId());
			if (r == null) {
				rs.setStatus(8);
				rs.setMessage("Restaurant not found.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			// check code

			if (!c.getCode().trim().equals(req.getCode().trim())) {
				// check code
				boolean ex = pis.getByOnlyCode(req.getCode());
				if (ex) {
					rs.setStatus(7);
					rs.setMessage("Code is exist.");
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}
			}

			String msg = promotionService.update(req, c.getPromotion(), r);
			if (msg.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("update success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/promotion/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Promotion, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			Promotion promotion = this.promotionService.getById(id);
			if (null == promotion) {
				rs.setStatus(0);
				rs.setMessage("promotion not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			this.promotionService.delete(promotion);
			rs.setStatus(0);
			rs.setMessage("delete success");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/promotion/getByCode/{promotionCode}/{restaurantId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByPromotionCode(@PathVariable @Validated String promotionCode,
			@PathVariable @Validated Long restaurantId) {
		ResponseGet rs = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Promotion, Constant.Action.getByCode)) {
//				rs.setStatus(7);
//				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				rs.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
//			}
			if (promotionCode == null || promotionCode.isEmpty()) {
				rs.setStatus(7);
				rs.setMessage("promotionCode not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			// check restaurant
			Restaurant r = res.getById(restaurantId);
			if (r == null) {
				rs.setStatus(8);
				rs.setMessage("Restaurant not found.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			PromotionLiteResponse promotion = promotionService.getByCode(promotionCode, restaurantId, null);
			if (null == promotion) {
				rs.setStatus(7);
				rs.setMessage("Promotion not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			rs.setStatus(0);
			rs.setContent(promotion);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

}
