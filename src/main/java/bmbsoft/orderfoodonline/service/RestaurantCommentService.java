package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.dao.RestaurantCommentDAO;
import bmbsoft.orderfoodonline.dao.RestaurantDAO;
import bmbsoft.orderfoodonline.dao.UserDAO;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.RestaurantComment;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.RestaurantCommentModel;
import bmbsoft.orderfoodonline.model.shared.RestaurantCommentRequest;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class RestaurantCommentService {
	@Autowired
	private RestaurantCommentDAO restaurantCommentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RestaurantDAO restauranDAO;

	@Transactional
	@Async
	public ResponseGetPaging getAll(final int pageIndex, final int pageSize, String title, Integer status, Long resId,
			Long userId) throws Exception {
		int totalRecord = restaurantCommentDAO.countGetAll(title, status, resId, userId);
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<RestaurantComment> listComment = restaurantCommentDAO.getAll(firstResult, maxResult, title, status, resId,
				userId);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (listComment == null || listComment.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

			return rs;
		}

		List<RestaurantCommentModel> listCommentModel = new LinkedList<>();
		for (RestaurantComment restaurantComment : listComment) {
			listCommentModel.add(convertToCommentModel(restaurantComment));
		}

		if (totalRecord > 0 && !listCommentModel.isEmpty()) {
			content.setData(listCommentModel);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	public RestaurantComment findById(final Long id) {
		return restaurantCommentDAO.findById(id);
	}

	public Boolean save(final RestaurantCommentRequest req, RestaurantComment rs) {
		return restaurantCommentDAO.save(castModelToEntityComment(req, rs));
	}

	public Boolean delete(final Long id) {
		return restaurantCommentDAO.delete(id);
	}

	public RestaurantCommentModel getDetailComment(final Long id) {
		return restaurantCommentDAO.getDetail(id);
	}

	public boolean isRestaurantCommentExist(Long restaurantCommentId) {
		RestaurantComment resComment = restaurantCommentDAO.findById(restaurantCommentId);
		return (resComment != null) ? true : false;
	}

	public List<RestaurantCommentModel> getCommentToAdminPageNative(int rowsNumber, String sortBy) {
		return restaurantCommentDAO.getCommentToAdminPageNative(rowsNumber, sortBy);
	}

	public List<RestaurantCommentModel> getCommentBySize(int rowsNumber, Long restaurantId) {
		return restaurantCommentDAO.getCommentBySize(rowsNumber, restaurantId);
	}
	
	public List<RestaurantCommentModel> getCommentByOwner(Long userId) {
		return restaurantCommentDAO.getCommentByOwner(userId);
	}

	private RestaurantComment castModelToEntityComment(RestaurantCommentRequest req, RestaurantComment rs) {

		if (rs.getResCommentId() == null) {
			rs.setResCommentId(req.getResCommentId());
			rs.setCreatedDate(new Date());
			rs.setStarPerMark(req.getStarPerMark());
			rs.setStarQuality(req.getStarQuality());
			rs.setStarShip(req.getStarShip());
			rs.setTitle(req.getTitle());
			rs.setDescription(req.getDescription());
			rs.setStatus(req.getStatus() == null ? Constant.Status.UnPublish.getValue() : req.getStatus());
			rs.setModifiedDate(new Date());

		} else {
			rs.setStarPerMark(req.getStarPerMark());
			rs.setStarQuality(req.getStarQuality());
			rs.setStarShip(req.getStarShip());
			rs.setTitle(req.getTitle());
			rs.setDescription(req.getDescription());
			rs.setStatus(req.getStatus() == null ? Constant.Status.UnPublish.getValue() : req.getStatus());
			rs.setModifiedDate(new Date());
		}
		return rs;
	}

	private RestaurantCommentModel convertToCommentModel(RestaurantComment res) {
		RestaurantCommentModel vm = new RestaurantCommentModel();
		vm.setResCommentId(res.getResCommentId());
		vm.setStarPerMark(res.getStarPerMark());
		vm.setStarQuality(res.getStarQuality());
		vm.setStarShip(res.getStarShip());
		vm.setTitle(res.getTitle());
		vm.setStatus(res.getStatus());
		vm.setCreatedDate(res.getCreatedDate());
		// for home
		User u = res.getUser();
		if (u != null && u.getStatus() == Constant.Status.Publish.getValue()) {
			vm.setUserName(u.getUserName());
			vm.setFullName(u.getFullName());
		}

		Restaurant r = res.getRestaurant();
		if (r != null && r.getStatus() == Constant.Status.Publish.getValue()) {
			vm.setRestaurantName(r.getName());
			vm.setAddressLine(r.getAddressLine());
		}

		vm.setDescription(res.getDescription());
		return vm;
	}
}
