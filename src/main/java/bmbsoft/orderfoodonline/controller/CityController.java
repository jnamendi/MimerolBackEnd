package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.entities.City;
import bmbsoft.orderfoodonline.entities.RestaurantArea;
import bmbsoft.orderfoodonline.model.CityViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.CityService;
import bmbsoft.orderfoodonline.service.CountryService;
import bmbsoft.orderfoodonline.service.RestaurantAreaService;
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
public class CityController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	CityService cityService;
	@Autowired
	CountryService countryService;
	@Autowired
	RestaurantAreaService restaurantAreaService;

	@RequestMapping(value = "/api/city/getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		List<CityViewModel> c = null;
		try {
			c = this.cityService.getAll();
			if (null == c || c.isEmpty()) {
				responseGetPaging.setStatus(0);
				responseGetPaging.setMessage("Could not found item.");
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

	@RequestMapping(value = "/api/city/getByRestaurantId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getByRestaurantId(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			List<CityViewModel> c = restaurantAreaService.getCityByRestaurant(id);

			if (c == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("city not exists");
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

	@RequestMapping(value = "/api/city/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			CityViewModel c = cityService.getById(id);
			if (c == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("city not exists");
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

	@RequestMapping(value = "/admin/city/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CityViewModel model) {
		ResponseGet responseGet = new ResponseGet();
		try {
			if (cityService.create(model) == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data.");
			responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/city/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody CityViewModel model) {
		ResponseGet responseGet = new ResponseGet();
		try {
			CityViewModel c = this.cityService.getById(id);
			if (null == c) {
				responseGet.setStatus(0);
				responseGet.setMessage("city not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			if (null == c.getCountry().getCountryId()) {
				responseGet.setStatus(0);
				responseGet.setMessage("no input country id");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			} else {
				if (null == countryService.getById(c.getCountry().getCountryId())) {
					responseGet.setStatus(0);
					responseGet.setMessage("country not exists");
					responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
					return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
				}
			}
			if (cityService.update(model) == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("update success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data");
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/city/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			City c = cityService.getBaseById(id);
			if (null == c) {
				responseGet.setStatus(0);
				responseGet.setMessage("city not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			if (cityService.delete(c)) {
				responseGet.setStatus(0);
				responseGet.setMessage("delete success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data");
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/city/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				City c = cityService.getBaseById(id);
				if (c != null) {
					cityService.delete(c);
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
