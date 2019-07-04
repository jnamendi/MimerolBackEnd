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

import bmbsoft.orderfoodonline.entities.Address;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.AddressRequest;
import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.AddressService;
import bmbsoft.orderfoodonline.service.DistrictService;
import bmbsoft.orderfoodonline.service.ResidenceTypeService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class AddressController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(AddressController.class);
	@Autowired
	AddressService addressService;
	@Autowired
	ResidenceTypeService residenceTypeService;
	@Autowired
	UserService userService;
	@Autowired
	DistrictService districtService;

	@RequestMapping(value = "/api/address/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Address, Constant.Action.getAll)) {
				responseGetPaging.setStatus(7);
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGetPaging.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
			}
			responseGetPaging = this.addressService.getAll(pageIndex, pageSize, title, status);
			if (responseGetPaging.getStatus() != 0)
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGetPaging.setStatus(1);
			responseGetPaging.setMessage(e.toString());
			responseGetPaging.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/address/getByUserId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByUser(@PathVariable Long userId) {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		List<AddressViewModel> vm = null;
		try {
			// // permission
			// if (!permission(Constant.Module.Address, Constant.Action.getByUserId)) {
			// responseGetPaging.setStatus(7);
			// responseGetPaging.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
			// responseGetPaging.setMessage("Access Denied!");
			// return new ResponseEntity<ResponseGetPaging>(responseGetPaging,
			// HttpStatus.BAD_REQUEST);
			// }
			if (userId == null || userId <= 0) {
				responseGetPaging.setStatus(7);
				responseGetPaging.setMessage("Field userId is required");
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
			}

			vm = this.addressService.getByUserId(userId);
			if (vm == null || vm.isEmpty()) {
				responseGetPaging.setStatus(8);
				responseGetPaging.setMessage("File not found.");
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
			}
			Data rsm = new Data();
			rsm.setTotalCount(vm.size());
			rsm.setPageIndex(0);
			rsm.setPageSize(Constant.PageNumber);
			rsm.setData(vm);
			responseGetPaging.setStatus(0);
			responseGetPaging.setContent(rsm);
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

	@RequestMapping(value = "/api/address/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Address, Constant.Action.getById)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			AddressViewModel c = addressService.getById(id);
			if (null == c) {
				responseGet.setStatus(8);
				responseGet.setMessage("Address invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			responseGet.setStatus(0);
			responseGet.setContent(c);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType("EXCEPTION");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/address/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Validated AddressRequest req, BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Address, Constant.Action.save)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				responseGet.setStatus(7);
				responseGet.setMessage(result.getFieldError().getDefaultMessage());
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			User u = userService.findById(req.getUserId());
			if (u == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("User invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			// String dCode = CommonHelper.toPrettyURL(req.getDistrict());
			// String cCode = CommonHelper.toPrettyURL(req.getCity());
			// District district = districtService.getDistrictByName(dCode, cCode);
			//
			// if (null == district) {
			// responseGet.setStatus(7);
			// responseGet.setMessage("District invalid.");
			// responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
			// return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			// }
			District dt = districtService.getBaseById(req.getDistrictId());
			if (dt == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("District invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			Address address = new Address();
			address.setCreatedDate(new Date());
			address.setDistrict(dt);
			address.setUser(u);
			address.setPhoneNumber(req.getPhoneNumber());
			address.setWard(req.getWard());
			address.setAddress(req.getAddress());
			address.setIsStatus(Constant.Status.Publish.getValue());
			address.setAddressDesc(req.getAddressDesc());

			if (addressService.create(address) == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success.");
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
			responseGet.setErrorType("EXCEPTION");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/address/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Validated AddressRequest req,
			BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Address, Constant.Action.update)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				responseGet.setStatus(7);
				responseGet.setMessage(result.getAllErrors().toString());
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			User u = userService.findById(req.getUserId());
			if (u == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("User invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			District dt = districtService.getBaseById(req.getDistrictId());
			if (dt == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("District invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			// String dCode = CommonHelper.toPrettyURL(req.getDistrict());
			// String cCode = CommonHelper.toPrettyURL(req.getCity());
			// District district = districtService.getDistrictByName(dCode, cCode);
			//
			// if (district == null) {
			// responseGet.setStatus(7);
			// responseGet.setMessage("District Or City invalid.");
			// responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
			// return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			// }

			Address address = this.addressService.getBaseById(id);
			if (null == address) {
				responseGet.setStatus(8);
				responseGet.setMessage("Address invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			address.setCreatedDate(new Date());
			address.setDistrict(dt);
			address.setUser(u);
			address.setPhoneNumber(req.getPhoneNumber());
			address.setWard(req.getWard());
			address.setAddress(req.getAddress());
			address.setIsStatus(req.getStatus());
			address.setAddressDesc(req.getAddressDesc());

			if (addressService.update(address) == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("update success.");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(1);
			responseGet.setMessage("Error when process the data.");
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType("EXCEPTION");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/address/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Address, Constant.Action.delete)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			Address c = this.addressService.getBaseById(id);
			if (null == c) {
				responseGet.setStatus(8);
				responseGet.setMessage("Address not exists.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			if (addressService.delete(c)) {
				responseGet.setStatus(0);
				responseGet.setMessage("delete success.");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(1);
			responseGet.setMessage("Error when process the data.");
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/address/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Address, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				Address c = addressService.getBaseById(id);
				if (c != null) {
					c.setIsStatus(Constant.Status.Deleted.getValue());
					c.setModifiedDate(new Date());
					addressService.update(c);
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
