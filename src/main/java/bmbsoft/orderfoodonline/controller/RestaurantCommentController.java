package bmbsoft.orderfoodonline.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.RestaurantComment;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.RestaurantCommentModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.model.shared.RestaurantCommentRequest;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.RestaurantCommentService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class RestaurantCommentController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantCommentController.class);

	@Autowired
	private RestaurantCommentService commentService;
	@Autowired
	private UserService usService;
	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping(value = "/api/restaurant-comment/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> findAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "resId", required = false) Long resId,
			@RequestParam(value = "userId", required = false) Long userId) {
		ResponseGetPaging rs = new ResponseGetPaging();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Comment, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			if (resId != null) {
				if (resId < 0 || null == restaurantService.getById(resId)) {
					rs.setStatus(7);
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
					rs.setMessage("restaurant not exits");
					return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
				}
			}
			if (userId != null) {
				if (userId < 0 || null == usService.findById(userId)) {
					rs.setStatus(7);
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
					rs.setMessage("user not exits");
					return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
				}
			}
			rs = this.commentService.getAll(pageIndex, pageSize, title, status, resId, userId);
			if (rs.getStatus() != 0)
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGetPaging>(rs, httpStatus);
		}
	}

	@RequestMapping(value = "/api/restaurant-comment/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommentById(@PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Comment, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			RestaurantCommentModel resComment = commentService.getDetailComment(id);
			if (resComment == null) {
				rs.setStatus(0);
				rs.setMessage("restaurant-comment not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(resComment);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	// Get 10 comment for home page
	@RequestMapping(value = "/api/restaurant-comment/getBySize/{size}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommentToAdminPage(@PathVariable(value = "size") int size) {
		ResponseGet responseGet = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Comment, Constant.Action.getBySize)) {
//				responseGet.setStatus(7);
//				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				responseGet.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
//			}
			List<RestaurantCommentModel> listComment = commentService.getCommentBySize(size, null);
			if (listComment == null || listComment.isEmpty()) {
				responseGet.setStatus(0);
				responseGet.setMessage("Could not found item.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(listComment);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	// Get 10 comment for home page
	// by restaurant id
	@RequestMapping(value = "/api/restaurant-comment/getBySize/{size}/{restaurantId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommentByRestaurant(@PathVariable @Validated int size,
			@PathVariable @Validated Long restaurantId) {
		ResponseGet responseGet = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Comment, Constant.Action.getByRestaurant)) {
//				responseGet.setStatus(7);
//				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				responseGet.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
//			}
			List<RestaurantCommentModel> listComment = commentService.getCommentBySize(size, restaurantId);
			if (listComment == null || listComment.isEmpty()) {
				responseGet.setStatus(0);
				responseGet.setMessage("Could not found item.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setContent(listComment);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}
	// Get 10 comment for home page
		// by restaurant id
		@RequestMapping(value = "/api/restaurant-comment/get-by-owner/{userId}", method = RequestMethod.GET)
		public ResponseEntity<?> getCommentByOwner(@PathVariable @Validated Long userId) {
			ResponseGet responseGet = new ResponseGet();
			try {
				// permission
				if (!permission(Constant.Module.Comment, Constant.Action.getByOwner)) {
					responseGet.setStatus(7);
					responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
					responseGet.setMessage("Access Denied!");
					return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
				}
				List<RestaurantCommentModel> listComment = commentService.getCommentByOwner(userId);
				if (listComment == null || listComment.isEmpty()) {
					responseGet.setStatus(0);
					responseGet.setMessage("Could not found item.");
					responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
					return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
				}
				responseGet.setStatus(0);
				responseGet.setContent(listComment);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			} catch (Exception e) {
				logger.error(e.toString());
				responseGet.setStatus(1);
				responseGet.setMessage(e.toString());
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
		}
	// save comment
	@RequestMapping(value = "/admin/restaurant-comment/save", method = RequestMethod.POST)
	public ResponseEntity<?> addComment(@RequestBody @Validated RestaurantCommentRequest req, BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Comment, Constant.Action.save)) {
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

			User u = usService.findById(req.getUserId());
			if (u == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("User not found.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			Restaurant rs = restaurantService.getById(req.getRestaurantId());
			if (rs == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("Restaurant not found.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}

			RestaurantComment rc = new RestaurantComment();
			rc.setRestaurant(rs);
			rc.setUser(u);
			boolean s = commentService.save(req, rc);

			if (s == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data.");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/restaurant-comment/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody @Validated RestaurantCommentRequest req, @PathVariable Long id,
			BindingResult result) {
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Comment, Constant.Action.update)) {
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
			User u = usService.findById(req.getUserId());
			if (u == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("User not found.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}

			Restaurant rs = restaurantService.getById(req.getRestaurantId());
			if (rs == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("Restaurant not found.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}

			RestaurantComment rc = commentService.findById(id);

			if (rc == null) {
				responseGet.setStatus(0);
				responseGet.setMessage("Could not found item.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}

			rc.setRestaurant(rs);
			rc.setUser(u);
			boolean s = commentService.save(req, rc);

			if (s == true) {
				responseGet.setStatus(0);
				responseGet.setMessage("save success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data.");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}

	// delete comment
	@RequestMapping(value = "/admin/restaurant-comment/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteComment(@PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Comment, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (commentService.isRestaurantCommentExist(id) == false) {
				rs.setStatus(7);
				rs.setMessage("Comment not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (commentService.delete(id)) {
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

	@RequestMapping(value = "/admin/restaurant-comment/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Comment, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Ids is field required");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				if (!commentService.delete(id)) {
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
