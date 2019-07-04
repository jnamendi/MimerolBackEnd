package bmbsoft.orderfoodonline.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.Favouries;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.model.FavouriesRequest;
import bmbsoft.orderfoodonline.model.FavouriesViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.FavouriesService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class FavouriesController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(FavouriesController.class);

	@Autowired
	private FavouriesService fs;

	@Autowired
	private RestaurantService rs;

	@Autowired
	private UserService us;

	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param title:
	 *            search by name restaurant
	 * @param status:
	 *            null -> get status publish
	 * @return List favorite has pagging
	 */
	@RequestMapping(value = "/api/favourite/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Favourite, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = this.fs.getAll(pageIndex, pageSize, title, status);
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

	@RequestMapping(value = "/api/favourite/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Favourite, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			FavouriesViewModel c = this.fs.getDetail(id);
			if (c == null) {
				rs.setStatus(0);
				rs.setMessage("favourite not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/favourite/getByUserId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByUser(@PathVariable Long userId) {
		ResponseGet responseGet = new ResponseGet();
		List<FavouriesViewModel> vm = null;
		try {
//			// permission
//			if (!permission(Constant.Module.Favourite, Constant.Action.getByUserId)) {
//				responseGet.setStatus(7);
//				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				responseGet.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
//			}
			if (userId == null || userId <= 0) {
				responseGet.setStatus(7);
				responseGet.setMessage("Field userId is required");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			vm = this.fs.getByUserId(userId, Constant.Status.Publish.getValue());
			if (vm == null || vm.size() <= 0) {
				responseGet.setStatus(0);
				responseGet.setMessage("favourite not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(vm);

			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/favourite/get-by-restaurant-and-user/{restaurantId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByIdAndUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		ResponseGet responseGet = new ResponseGet();
		List<FavouriesViewModel> vm = null;
		try {
//			// permission
//			if (!permission(Constant.Module.Favourite, Constant.Action.getByRestaurantAndUser)) {
//				responseGet.setStatus(7);
//				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				responseGet.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
//			}
			if (restaurantId == null || restaurantId <= 0) {
				responseGet.setStatus(7);
				responseGet.setMessage("Field restaurantId is required");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			if (userId == null || userId <= 0) {
				responseGet.setStatus(7);
				responseGet.setMessage("Field userId is required");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			vm = this.fs.getByIdUserId(restaurantId, userId, null);
			if (vm == null || vm.size() <= 0) {
				responseGet.setStatus(0);
				responseGet.setMessage("favourite not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(vm);

			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/admin/favourite/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody FavouriesRequest req) {
		ResponseGet responseGet = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Favourite, Constant.Action.save)) {
//				responseGet.setStatus(7);
//				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				responseGet.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
//			}
			Restaurant restaurant = rs.getById(req.getRestaurantId());

			if (null == restaurant) {
				responseGet.setStatus(7);
				responseGet.setMessage("invalid restaurant");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			User user = us.findById(req.getUserId());
			if (null == user) {
				responseGet.setStatus(7);
				responseGet.setMessage("user not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			Favouries f = new Favouries();
			f.setRestaurant(restaurant);
			f.setUser(user);
			f.setIsStatus(req.getStatus());

			if (fs.save(f) == true) {
				responseGet.setStatus(0);
				responseGet.setContent(f.getFavoriesId());
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

	@RequestMapping(value = "/admin/favourite/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody FavouriesRequest req) {
		ResponseGet responseGet = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Favourite, Constant.Action.update)) {
//				responseGet.setStatus(7);
//				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				responseGet.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
//			}
			Favouries c = this.fs.getById(id);
			if (null == c) {
				responseGet.setStatus(0);
				responseGet.setMessage("favourite id not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}

			c.setIsStatus(req.getStatus());
			c.setRestaurant(rs.getById(req.getRestaurantId()));
			c.setUser(us.findById(req.getUserId()));

			if (fs.save(c) == true) {
				responseGet.setStatus(0);
				responseGet.setContent(c.getFavoriesId());
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

	@RequestMapping(value = "/admin/favourite/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Favourite, Constant.Action.delete)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			Favouries c = this.fs.getById(id);
			if (null == c) {
				responseGet.setStatus(0);
				responseGet.setMessage("favourite not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			if (fs.delete(c)) {
				responseGet.setStatus(0);
				responseGet.setMessage("delete success");
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

	@RequestMapping(value = "/admin/favourite/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Favourite, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("ids is invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				Favouries ct = this.fs.getById(id);
				if (ct != null) {
					fs.delete(ct);
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
