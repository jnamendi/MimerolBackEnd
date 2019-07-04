package bmbsoft.orderfoodonline.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Rating;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.RatingViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "RatingDAO")
@Transactional(rollbackOn = Exception.class)
public class RatingDAO {

	public static final Logger logger = LoggerFactory.getLogger(RatingDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean create(final Rating Rating) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(Rating)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean update(final Rating Rating) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(Rating);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Rating getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Rating a = session.get(Rating.class, id);

			return a;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public ResponseGetPaging getAll(int pageIndex, int pageSize, String resName) {
		Session s = this.sessionFactory.getCurrentSession();
		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		try {
			int currentPage = (pageIndex < 1) ? 1 : pageIndex;
			int firstResult = (currentPage - 1) * pageSize;
			int maxResult = currentPage * pageSize;

			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Rating> cq = cb.createQuery(Rating.class);
			Root<Rating> from = cq.from(Rating.class);

			Join<Rating, Restaurant> join = from.join("restaurant");
			cq.where(cb.notEqual(from.get("isStatus"), Constant.Status.Deleted.getValue()));
			if (resName != null && !resName.isEmpty()) {
				cq.where(cb.like(join.get("name"), '%' + resName + '%'));
			}
			cq.orderBy(cb.desc(from.get("createdDate")));

			TypedQuery<Rating> tq = s.createQuery(cq);
			List<Rating> ratinges = tq.getResultList();

			int totalRecord = ratinges.size();
			ratinges = maxResult == 0 ? tq.getResultList()
					: tq.setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

			if (ratinges == null || ratinges.size() <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found items.");
				content.setTotalCount(0);
				rs.setContent(content);
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return rs;
			}

			List<RatingViewModel> rvm = new LinkedList<>();
			ratinges.forEach(r -> {
				RatingViewModel rv = convertEntityToModel(r);
				rvm.add(rv);
			});

			if (totalRecord > 0 && !rvm.isEmpty()) {
				content.setData(rvm);
				content.setPageIndex(pageIndex);
				content.setPageSize(pageSize);
				content.setTotalCount(totalRecord);
				rs.setContent(content);
				rs.setStatus(0);
				rs.setMessage("Success.");

				return rs;
			} else
				return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage("Error in process data.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return rs;
		}
	}

	private RatingViewModel convertEntityToModel(Rating r) {
		RatingViewModel vm = new RatingViewModel();
		vm.setRatingId(r.getRatingId());
		vm.setComment(r.getRatingComment());

		vm.setCreatedDate(r.getCreatedDate());
		vm.setDelivery(r.getDelivery());
		vm.setQuality(r.getQuality());
		vm.setStatus(r.getIsStatus());

		Restaurant rt = r.getRestaurant();
		if (rt != null) {
			vm.setRestaurantId(rt.getRestaurantId());
			vm.setRestaurantName(rt.getName());
		}

		User u = r.getUser();
		if (u != null) {
			vm.setUserId(u.getUserId());
			vm.setUserName(u.getUserName());
			vm.setFullName(u.getFullName());
		}

		return vm;
	}

}
