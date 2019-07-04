package bmbsoft.orderfoodonline.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.model.MenuViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.model.shared.MenuLiteResponse;
import bmbsoft.orderfoodonline.model.shared.MenuRequest;
import bmbsoft.orderfoodonline.model.shared.MenuResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.service.MenuService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class MenuController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private LanguageService languageService;

	Gson gson = new Gson();

	@RequestMapping(value = "/api/menu/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "languageCode", required = false) String languageCode,
			@RequestParam(value = "resId", required = false) Long restaurantId) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}

			if (languageCode != null) {
				if (languageCode.isEmpty() || null == languageService.getLanguageByCode(languageCode)) {
					rs.setStatus(7);
					rs.setMessage("Language code not exits");
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
					return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
				}
			}
			rs = this.menuService.getAll(pageIndex, pageSize, name, status, languageCode, restaurantId);
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

	@RequestMapping(value = "/api/menu/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMenuById(@PathVariable Long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.getById)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			MenuViewModel menuModel = menuService.viewDetail(id);
			if (menuModel == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("menu not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(menuModel);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/menu/get-menu-by-restaurant-id/{restaurantId}/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getMenuByRestaurantId(@PathVariable Long restaurantId, @PathVariable String languageCode) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.getMenuByRestaurantId)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			if (restaurantId <= 0) {
				responseGet.setStatus(7);
				responseGet.setMessage("Restaurant invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			Language l = languageService.getLanguageByCode(languageCode);
			if (l == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("language invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			List<MenuLiteResponse> menuModel = menuService.getAllByRestaurant(restaurantId, l);
			if (menuModel == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("menu not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(menuModel);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/menu/get-by-restaurant/{languageCode}/{restaurantId}", method = RequestMethod.GET)
	public ResponseEntity<?> getMenuByRestaurant(@PathVariable String languageCode, @PathVariable Long restaurantId) {
		ResponseGet rs = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Menu, Constant.Action.getByRestaurant)) {
//				rs.setStatus(7);
//				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				rs.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
//			}
			if (restaurantId == null || restaurantId <= 0) {
				rs.setStatus(7);
				rs.setMessage("restaurantId is required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			Language l = languageService.getLanguageByCode(languageCode);
			if (l == null) {
				rs.setStatus(7);
				rs.setMessage("language invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			MenuResponse menuModel = menuService.getByRestaurant(restaurantId, l);
			if (menuModel == null) {
				rs.setStatus(0);
				rs.setMessage("menu not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(menuModel);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/menu/save", method = RequestMethod.POST)
	public ResponseEntity<?> addMenu(@RequestBody @Validated MenuRequest req, BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.save)) {
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
			Restaurant rs = restaurantService.getById(req.getRestaurantId());
			if (rs == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("Restaurant not found.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			Menu m = new Menu();
			m.setRestaurant(rs);

			String mesage = menuService.create(req, m, null);
			if (mesage.isEmpty()) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data." + mesage);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/admin/menu/insert")
	public ResponseEntity<?> insert(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam String menuAdminModel) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.insert)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			MenuRequest req = gson.fromJson(menuAdminModel, MenuRequest.class);
			if (req == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("Request is null");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			Restaurant rs = restaurantService.getById(req.getRestaurantId());
			if (rs == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("Restaurant invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			Menu m = new Menu();
			m.setRestaurant(rs);

			String mesage = menuService.create(req, m, file);
			if (mesage.isEmpty()) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data." + mesage);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/admin/menu/edit/{id}")
	public ResponseEntity<?> edit(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam String menuAdminModel, @PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.edit)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			MenuRequest req = gson.fromJson(menuAdminModel, MenuRequest.class);

			if (req == null) {
				rs.setStatus(7);
				rs.setMessage("Request is null");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			Restaurant res = restaurantService.getById(req.getRestaurantId());
			if (res == null) {
				rs.setStatus(7);
				rs.setMessage("restaurant not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			Menu m = menuService.getById(id);
			if (m == null) {
				rs.setStatus(0);
				rs.setMessage("menu not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			m.setRestaurant(res);

			String mesage = menuService.update(req, m, file);
			if (mesage.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("update success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data." + mesage);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/menu/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody @Validated MenuRequest req, @PathVariable Long id,
			BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.save)) {
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
			Restaurant res = restaurantService.getById(req.getRestaurantId());
			if (res == null) {
				rs.setStatus(7);
				rs.setMessage("restaurant not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			Menu m = menuService.getById(id);
			if (m == null) {
				rs.setStatus(0);
				rs.setMessage("menu not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			m.setRestaurant(res);

			String mesage = menuService.update(req, m, null);
			if (mesage.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("update success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data." + mesage);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/menu/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			Menu m = menuService.getById(id);
			if (m == null) {
				rs.setStatus(0);
				rs.setMessage("menu not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			if (menuService.delete(m)) {
				rs.setStatus(0);
				rs.setMessage("delete success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data.");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/menu/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Ids is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				Menu m = menuService.getById(id);

				if (!menuService.delete(m)) {
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

	@RequestMapping(value = "/api/menu/getByOwner/{pageIndex}/{pageSize}/{id}/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getMenuByOwner(@PathVariable int pageIndex, @PathVariable int pageSize,
			@PathVariable long id, @PathVariable String languageCode) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Menu, Constant.Action.getByOwner)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			Language lang = languageService.getLanguageByCode(languageCode);
			if (null == lang) {
				rs.setStatus(7);
				rs.setMessage("Language code not exits");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = menuService.getMenuByOwner(pageIndex, pageSize, id, languageCode);
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
}
