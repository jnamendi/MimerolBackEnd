package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.entities.CloseOpen;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.model.AddressSearchModel;
import bmbsoft.orderfoodonline.model.CityViewModel;
import bmbsoft.orderfoodonline.model.RestaurantViewModel;
import bmbsoft.orderfoodonline.model.RestaurantWorkTimeModel;
import bmbsoft.orderfoodonline.model.shared.*;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.CategoryService;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.service.UserRestaurantService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class RestaurantController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	private LanguageService langService;

	@Autowired
	CategoryService cs;

	@Autowired
	UserRestaurantService urs;

	Gson gson = new Gson();

	@RequestMapping(value = "/api/restaurant/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging rs = new ResponseGetPaging();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = this.restaurantService.getAll(pageIndex, pageSize, name, status);
			if (rs.getStatus() != 0)
				return new ResponseEntity<>(rs, HttpStatus.OK);
			return new ResponseEntity<>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/restaurant/getById/{id}/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id,
			@PathVariable(value = "languageCode", required = false) String languageCode) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if (id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Restaurant invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			RestaurantViewModel r = restaurantService.viewDetail(id, languageCode);
			if (r == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(r);
			httpStatus = HttpStatus.OK;
			return new ResponseEntity<>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);
		}
	}

	@PostMapping("/admin/restaurant/insert")
	public ResponseEntity<?> insert(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam String restaurantAdminModel) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.insert)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			RestaurantRequest req = gson.fromJson(restaurantAdminModel, RestaurantRequest.class);
			if (req == null) {
				rs.setStatus(7);
				rs.setMessage("Request is null");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			// check exist resName

			boolean checkResName = restaurantService.getByName(req.getRestaurantName());

			if (checkResName) {
				rs.setStatus(0);
				rs.setMessage("Restaurant is exist.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			if(!req.getRestaurantWorkTimeModels().isEmpty()) {
				for(RestaurantWorkTimeModel restaurantWorkTimeModel : req.getRestaurantWorkTimeModels()) {
					if(restaurantWorkTimeModel.getList() != null && !restaurantWorkTimeModel.getList().isEmpty()) {
						for(CloseOpen co : restaurantWorkTimeModel.getList()) {
							if (CommonHelper.compareTime(co.getOpenTime(), co.getCloseTime())) {
								rs.setStatus(7);
								rs.setMessage("Opentime > CloseTime");
								rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
								return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
							}
						}
					}
				}
			}
			String msg = restaurantService.save(req, null, file);
			if (msg.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}

			rs.setStatus(7);
			rs.setMessage(msg);
			rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(0);
			rs.setMessage(e.getMessage());
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/restaurant/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Validated RestaurantRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.save)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if(!req.getRestaurantWorkTimeModels().isEmpty()) {
				for(RestaurantWorkTimeModel restaurantWorkTimeModel : req.getRestaurantWorkTimeModels()) {
					if(restaurantWorkTimeModel.getList() != null && !restaurantWorkTimeModel.getList().isEmpty()) {
						for(CloseOpen co : restaurantWorkTimeModel.getList()) {
							if (CommonHelper.compareTime(co.getOpenTime(), co.getCloseTime())) {
								rs.setStatus(7);
								rs.setMessage("Opentime > CloseTime");
								rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
								return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
							}
						}
					}
				}
			}

			String msg = restaurantService.save(req, null, null);
			if (msg.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}

			rs.setStatus(7);
			rs.setMessage(msg);
			rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(0);
			rs.setMessage(e.getMessage());
		}
		return new ResponseEntity<>(rs, httpStatus);
	}

	@PostMapping("/admin/restaurant/edit/{id}")
	public ResponseEntity<?> edit(@PathVariable long id,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam String restaurantAdminModel) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.edit)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			RestaurantRequest req = gson.fromJson(restaurantAdminModel, RestaurantRequest.class);

			if (req == null) {
				rs.setStatus(7);
				rs.setMessage("Request is null");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if(!req.getRestaurantWorkTimeModels().isEmpty()) {
				for(RestaurantWorkTimeModel restaurantWorkTimeModel : req.getRestaurantWorkTimeModels()) {
					if(restaurantWorkTimeModel.getList() != null && !restaurantWorkTimeModel.getList().isEmpty()) {
						for(CloseOpen co : restaurantWorkTimeModel.getList()) {
							if (CommonHelper.compareTime(co.getOpenTime(), co.getCloseTime())) {
								rs.setStatus(7);
								rs.setMessage("Opentime > CloseTime");
								rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
								return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
							}
						}
					}
				}
			}
			Restaurant r = restaurantService.getById(id);
			if (r != null && r.getRestaurantId() > 0) {
				req.setRestaurantId(id);

				String msg = restaurantService.save(req, r, file);
				if (msg.isEmpty()) {
					rs.setStatus(0);
					rs.setMessage("Ok");
					return new ResponseEntity<>(rs, HttpStatus.OK);
				}

				// check exist resName
				if (!req.getRestaurantName().trim().equals(r.getName().trim())) {
					boolean checkResName = restaurantService.getByName(req.getRestaurantName());

					if (checkResName) {
						rs.setStatus(0);
						rs.setMessage("Restaurant name is exist.");
						rs.setErrorType(Constant.ErrorTypeCommon.EXIST_ITEM);
						return new ResponseEntity<>(rs, HttpStatus.OK);
					}
				}

				rs.setStatus(7);
				rs.setMessage(msg);
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

			} else {
				rs.setStatus(0);
				rs.setMessage("Could not found items.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(0);
			rs.setMessage(e.getMessage());
		}
		return new ResponseEntity<>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/restaurant/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Validated RestaurantRequest req,
			BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.OK;

		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.update)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			if(!req.getRestaurantWorkTimeModels().isEmpty()) {
				for(RestaurantWorkTimeModel restaurantWorkTimeModel : req.getRestaurantWorkTimeModels()) {
					if(restaurantWorkTimeModel.getList() != null && !restaurantWorkTimeModel.getList().isEmpty()) {
						for(CloseOpen co : restaurantWorkTimeModel.getList()) {
							if (CommonHelper.compareTime(co.getOpenTime(), co.getCloseTime())) {
								rs.setStatus(7);
								rs.setMessage("Opentime > CloseTime");
								rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
								return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
							}
						}
					}
				}
			}

			Restaurant r = restaurantService.getById(id);
			if (r != null && r.getRestaurantId() > 0) {
				req.setRestaurantId(id);

				String msg = restaurantService.save(req, r, null);
				if (msg.isEmpty()) {
					rs.setStatus(0);
					rs.setMessage("Ok");

					return new ResponseEntity<>(rs, HttpStatus.OK);
				}

				rs.setStatus(7);
				rs.setMessage(msg);
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

			} else {
				rs.setStatus(0);
				rs.setMessage("File not found.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<>(rs, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			rs.setMessage(e.getMessage());

			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/restaurant/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			Restaurant r = restaurantService.getById(id);
			if (r == null) {
				rs.setStatus(0);
				rs.setMessage("Restaurant not exists.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			r.setStatus(Constant.Status.Deleted.getValue());

			boolean s = restaurantService.delete(r);
			if (s) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				rs.setErrorType(Constant.ErrorTypeCommon.OK);
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<>(rs, httpStatus);
			}

			rs.setStatus(1);
			rs.setMessage("Error when process the data.");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);

		} catch (Exception e) {
			logger.info(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/restaurant/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Ids is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				Restaurant ct = restaurantService.getById(id);
				if (ct != null) {
					ct.setStatus(Constant.Status.Deleted.getValue());
					ct.setModifiedDate(new Date());

					restaurantService.delete(ct);
				} else {
					s.append("Could not found item: " + id);
				}
			}
			if (s.toString().isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Delete success.");
				rs.setErrorType(Constant.ErrorTypeCommon.OK);
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<>(rs, httpStatus);
			}
			rs.setStatus(6);
			rs.setMessage(s.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.DELETE_MANY);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<>(rs, httpStatus);
		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/restaurant/getByName/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getByName(@PathVariable(value = "name", required = false) String name) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// // permission
			// if (!permission(Constant.Module.Restaurant, Constant.Action.getByName)) {
			// rs.setStatus(7);
			// rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
			// rs.setMessage("Access Denied!");
			// return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			// }
			if (name == null || name.isEmpty()) {
				rs.setStatus(7);
				rs.setMessage("Required String parameter 'name' is not valid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			List<RestaurantViewModel> restaurantViewModels = restaurantService.getRestaurantsByname(name);
			if (restaurantViewModels == null || restaurantViewModels.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("File not found.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Success.");
			rs.setContent(restaurantViewModels);
			return new ResponseEntity<>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);
		}

	}

	@RequestMapping(value = "/api/restaurant/get-all-registered-city/{cityName}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCityByKey(@PathVariable(value = "cityName", required = false) String cityName) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// // permission
			// if (!permission(Constant.Module.Restaurant,
			// Constant.Action.getAllRegisteredCity)) {
			// rs.setStatus(7);
			// rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
			// rs.setMessage("Access Denied!");
			// return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			// }
			if (cityName == null || cityName.isEmpty()) {
				rs.setStatus(7);
				rs.setMessage("key is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			List<CityViewModel> lstCity = restaurantService.getCityByKey(cityName);
			if (lstCity == null || lstCity.size() <= 0) {
				rs.setStatus(0);
				rs.setMessage("Could not found.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Success.");
			rs.setContent(lstCity);
			return new ResponseEntity<>(rs, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);
		}
	}

	/**
	 * 
	 * */
	@RequestMapping(value = "/admin/restaurant/get-by-district", method = RequestMethod.POST)
	public ResponseEntity<?> getByDistrict(@RequestBody @Validated AddressSearchModel req, BindingResult result) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ResponseGet responseGet = new ResponseGet();
		try {
			// // permission
			// if (!permission(Constant.Module.Restaurant, Constant.Action.getByDistrict)) {
			// responseGet.setStatus(7);
			// responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
			// responseGet.setMessage("Access Denied!");
			// return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			// }
			if (result.hasErrors()) {
				responseGet.setStatus(7);
				responseGet.setMessage(result.getAllErrors().toString());
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(responseGet, HttpStatus.OK);
			}

			String dCode = CommonHelper.toPrettyURL(req.getDistrict());
			String cCode = CommonHelper.toPrettyURL(req.getCity());

			Language l = langService.getLanguageByCode(req.getLanguageCode());
			if (l == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("language invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

				return new ResponseEntity<>(responseGet, HttpStatus.BAD_REQUEST);
			}
			String key = "";
			if (!dCode.isEmpty()) {
				key += dCode;
			}
			if (!cCode.isEmpty()) {
				key += "#" + cCode;
			}
			RestaurantResponse res = this.restaurantService.getByDistrict(req, l, key);
			if (res == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("Could not found items.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<>(responseGet, httpStatus);
			}
			responseGet.setStatus(0);
			responseGet.setMessage("Ok");
			responseGet.setContent(res);
			return new ResponseEntity<>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.getMessage());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<>(responseGet, httpStatus);
	}

	@RequestMapping(value = "/api/restaurant/get-restaurant-by/{id}/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getByRestaurantId(@PathVariable @Validated Long id,
			@PathVariable @Validated String languageCode) {

		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// // permission
			// if (!permission(Constant.Module.Restaurant,
			// Constant.Action.getByIdAndlanguageCode)) {
			// rs.setStatus(7);
			// rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
			// rs.setMessage("Access Denied!");
			// return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			// }
			if (id == null || id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Restaurant invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			Language l = langService.getLanguageByCode(languageCode);
			if (l == null) {
				rs.setStatus(7);
				rs.setMessage("Language invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			RestaurantLiteResponse2 r = restaurantService.getByIdAndLangauge(id, l);
			if (r == null) {
				rs.setStatus(0);
				rs.setMessage("File not found");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(r);
			httpStatus = HttpStatus.OK;
			return new ResponseEntity<>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);
		}

	}

	@RequestMapping(value = "/api/restaurant/get-restaurant-by/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByUserId(@PathVariable @Validated Long userId) {

		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.getByUserId)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			if (userId == null || userId <= 0) {
				rs.setStatus(7);
				rs.setMessage("userId invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}

			List<RestaurantLiteResponse3> r = urs.getByUserRestaurant(userId);
			if (r == null) {
				rs.setStatus(0);
				rs.setMessage("Could not fould item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(r);
			httpStatus = HttpStatus.OK;
			return new ResponseEntity<>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);
		}

	}

	@RequestMapping(value = "/api/restaurant/get-all-restaurant-sort-by-name", method = RequestMethod.GET)
	public ResponseEntity<?> getAllRestaurantSortByName() {

		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Restaurant, Constant.Action.getAllSortByName)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
			}
			List<RestaurantLiteResponse3> r = restaurantService.getAllRestaurantSortByName();
			if (r == null) {
				rs.setStatus(0);
				rs.setMessage("Could not fould item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(r);
			httpStatus = HttpStatus.OK;
			return new ResponseEntity<>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<>(rs, httpStatus);
		}

	}

}
