package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.RatingDAO;
import bmbsoft.orderfoodonline.dao.DistrictDAO;
import bmbsoft.orderfoodonline.dao.ResidenceTypeDAO;
import bmbsoft.orderfoodonline.dao.UserDAO;
import bmbsoft.orderfoodonline.entities.Rating;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.City;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.RatingViewModel;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class RatingService {

	@Autowired
	private RatingDAO ratingDAO;

	@Transactional
	public ResponseGetPaging getAll(int pageIndex, int pageSize, String resName) {
		ResponseGetPaging r = ratingDAO.getAll(pageIndex, pageSize, resName);
		return r;
	}

	@Transactional
	public Boolean create(final Rating Rating) {
		return ratingDAO.create(Rating);
	}

	@Transactional
	public Boolean update(final Rating Rating) {
		return ratingDAO.update(Rating);
	}

	@Transactional
	public RatingViewModel getById(final long id) {
		Rating r = ratingDAO.getById(id);
		if (r == null)
			return null;
		return convertEntityToModel(r);
	}

	private RatingViewModel convertEntityToModel(Rating r) {
		RatingViewModel vm = new RatingViewModel();
		vm.setRatingId(r.getRatingId());
		vm.setComment(r.getRatingComment());
		vm.setStatus(r.getIsStatus());
		vm.setCreatedDate(r.getCreatedDate());
		vm.setDelivery(r.getDelivery());
		vm.setQuality(r.getQuality());
		Restaurant rt = r.getRestaurant();
		if (rt != null && rt.getRestaurantId() != null && rt.getStatus() == Constant.Status.Publish.getValue()) {
			vm.setRestaurantId(rt.getRestaurantId());
			vm.setRestaurantName(rt.getName());
		}
		User u = r.getUser();

		if (u != null && u.getStatus() == Constant.Status.Publish.getValue()) {
			vm.setUserId(u.getUserId());
			vm.setUserName(u.getFullName());
		}
		return vm;
	}

	@Transactional
	public Rating getBaseById(final long id) {
		return ratingDAO.getById(id);
	}

	public boolean delete(Rating r) {
		r.setIsStatus(Constant.Status.Deleted.getValue());
		r.setModifiedDate(new Date());

		return ratingDAO.update(r);
	}
}
