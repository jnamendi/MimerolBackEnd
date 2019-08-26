package bmbsoft.orderfoodonline.controller;

import java.util.Date;
import java.util.List;

import bmbsoft.orderfoodonline.entities.RestaurantArea;
import bmbsoft.orderfoodonline.service.RestaurantAreaService;
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
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.model.CountryViewModel;
import bmbsoft.orderfoodonline.model.DistrictViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.CityService;
import bmbsoft.orderfoodonline.service.DistrictService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class DistrictController {
	public static final Logger logger = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	DistrictService districtService;
	@Autowired
	CityService cityService;
	@Autowired
	RestaurantAreaService restaurantAreaService;

	@RequestMapping(value = "/api/district/getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		List<DistrictViewModel> c = null;
		try {
			c = this.districtService.getAll();
			if (null == c || c.isEmpty()) {
				responseGetPaging.setStatus(0);
				responseGetPaging.setMessage("Could not found items.");
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
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
	
	@RequestMapping(value = "/api/district/get-district-by-city/{cityId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBy(@PathVariable long cityId) {
		ResponseGet responseGet = new ResponseGet();
		List<DistrictViewModel> c = null;
		try {
			c = this.districtService.getAllByCityId(cityId);
			if (c == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("district not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(c);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/district/get-district-by-restaurant-city/{restaurantId}/{cityId}", method = RequestMethod.GET)
	public ResponseEntity<?> getDistrictByRestaurant(@PathVariable long restaurantId, @PathVariable long cityId) {
		ResponseGet responseGet = new ResponseGet();
		List<DistrictViewModel> c = null;
		try {
			c = restaurantAreaService.getDistrictByRestaurantAndCity(restaurantId, cityId);
			if (c == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("district not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(c);
			return new ResponseEntity<>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/district/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			DistrictViewModel c = districtService.getById(id);
			if (c == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("district not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(c);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/district/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody DistrictViewModel model) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (districtService.create(model) == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data.");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/district/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody DistrictViewModel model) {
		ResponseGet rs = new ResponseGet();
		try {
			DistrictViewModel c = this.districtService.getById(id);
			if (null == c) {
				rs.setStatus(7);
				rs.setMessage("city not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (null == c.getCity().getCityId()) {
				rs.setStatus(7);
				rs.setMessage("no input city id");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			} else {
				if (null == cityService.getById(c.getCity().getCityId())) {
					rs.setStatus(7);
					rs.setMessage("city not exists");
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}
			}
			if (districtService.update(model) == true) {
				rs.setStatus(0);
				rs.setMessage("update success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/district/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			District c = this.districtService.getBaseById(id);
			if (null == c) {
				rs.setStatus(7);
				rs.setMessage("district not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			if (districtService.delete(c)) {
				rs.setStatus(0);
				rs.setMessage("delete success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/district/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Ids is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				District c = districtService.getBaseById(id);
				if (c != null) {
					districtService.delete(c);
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
