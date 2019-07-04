package bmbsoft.orderfoodonline.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.RestaurantComment;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.entities.UserRestaurant;
import bmbsoft.orderfoodonline.model.RestaurantCommentModel;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "restaurantCommentDAO")
@Transactional(rollbackFor = Exception.class)
public class RestaurantCommentDAO {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantCommentDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean save(RestaurantComment restaurantComment) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.saveOrUpdate(restaurantComment);
			t.commit();
			return true;
		} catch (Exception e) {
			t.rollback();
			return false;
		} finally {
			session.close();
		}
	}

	public RestaurantComment findById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(RestaurantComment.class, id);
	}

	public RestaurantCommentModel getDetail(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		RestaurantComment resComment = session.get(RestaurantComment.class, id);
		return (resComment != null) ? convertToCommentModel(resComment) : null;
	}

	public Boolean delete(final Long id) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			RestaurantComment resComment = session.get(RestaurantComment.class, id);
			resComment.setStatus(Constant.Status.Deleted.getValue());

			save(resComment);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}
	}

	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, String title, Integer status, Long resId,
			Long userId) {
		Predicate predicate = cb.conjunction();
		if (null != title && !title.isEmpty()) {
			predicate = cb.and(predicate, cb.like(root.<String>get("title"), "%" + title + "%"));
			predicate = cb.or(predicate, cb.like(root.<String>get("description"), "%" + title + "%"));
		}
		if (null == status) {
			predicate = cb.and(predicate, cb.notEqual(root.<Integer>get("status"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("status"), status));
		}
		if (null != resId) {
			predicate = cb.and(predicate, cb.equal(root.join("restaurant").get("restaurantId"), resId));
		}
		if (null != userId) {
			predicate = cb.and(predicate, cb.equal(root.join("user").get("userId"), userId));
		}
		return predicate;
	}

	public int countGetAll(String title, Integer status, Long resId, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<RestaurantComment> countFrom = queryCount.from(RestaurantComment.class);
		queryCount.select(cb.countDistinct(countFrom));
		queryCount.where(toPredicate(countFrom, cb, title, status, resId, userId));
		try {
			Long totalCount = session.createQuery(queryCount).getSingleResult();
			return totalCount.intValue();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}
	}

	public List<RestaurantComment> getAll(final int firstResult, final int maxResult, String title, Integer status,
			Long resId, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RestaurantComment> queryComment = cb.createQuery(RestaurantComment.class);
		Root<RestaurantComment> form = queryComment.from(queryComment.getResultType());
		queryComment.select(form);
		queryComment.where(toPredicate(form, cb, title, status, resId, userId));
		queryComment.orderBy(cb.desc(form.get("modifiedDate")));
		List<RestaurantComment> RestaurantComments = (maxResult == 0)
				? session.createQuery(queryComment).getResultList()
				: session.createQuery(queryComment).setFirstResult(firstResult).setMaxResults(maxResult)
						.getResultList();

		return RestaurantComments;
	}

	// NHPhong function get comment to homepage using native query.
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<RestaurantCommentModel> getCommentToAdminPageNative(int rowsNumber, String sortby) {
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT  u.user_name AS userName, res.address_line_1  AS addressLine1 ,");
			sql.append("comment.description AS description ");
			sql.append("FROM restaurant_comment comment ");
			sql.append("INNER JOIN  user u on comment.user_id = u.user_id ");
			sql.append("INNER JOIN  restaurant res on comment.restaurant_id = res.restaurant_id ");
			sql.append("ORDER BY :sortby DESC ");
			sql.append("LIMIT :rows");

			Query<RestaurantCommentModel> query = session.createNativeQuery(sql.toString())
					.setParameter("sortby", "comment." + sortby).setParameter("rows", rowsNumber)
					.setResultTransformer(Transformers.aliasToBean(RestaurantCommentModel.class));
			List<RestaurantCommentModel> listResCommentModel = query.list();
			return listResCommentModel;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RestaurantCommentModel> getCommentBySize(int rowsNumber, Long restaurantId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("Select e from " + RestaurantComment.class.getName() + " e ");
			if (restaurantId != null) {
				sql.append(" inner join e.restaurant r where r.restaurantId=" + restaurantId);
				sql.append(" AND e.status=" + Constant.Status.Publish.getValue());
			} else {
				sql.append(" where e.status=" + Constant.Status.Publish.getValue());
			}

			sql.append(" ORDER BY e.createdDate DESC ");

			List<RestaurantComment> listComment = session.createQuery(sql.toString()).setMaxResults(rowsNumber).list();
			List<RestaurantCommentModel> listResCommentModel = new LinkedList<RestaurantCommentModel>();
			for (RestaurantComment restaurantComment : listComment) {
				listResCommentModel.add(convertToCommentModel(restaurantComment));
			}
			return listResCommentModel;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public List<RestaurantCommentModel> getCommentByOwner(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<RestaurantComment> query = cb.createQuery(RestaurantComment.class);
			Root<RestaurantComment> form = query.from(RestaurantComment.class);
			Join<RestaurantComment, Restaurant> restaurant = form.join("restaurant");
			Join<Restaurant, UserRestaurant> userRestaurant = restaurant.join("userRestaurants");

			List<Predicate> predicates = new LinkedList<>();
			predicates.add(cb.and(cb.equal(userRestaurant.join("user").<Long>get("userId"), userId)));
			predicates.add(cb.and(cb.notEqual(form.<Integer>get("status"), Constant.Status.Deleted.getValue())));
			query.select(form).where(predicates.stream().toArray(Predicate[]::new));
			List<RestaurantComment> c = session.createQuery(query).getResultList();
			List<RestaurantCommentModel> listResCommentModel = new LinkedList<RestaurantCommentModel>();
			for (RestaurantComment restaurantComment : c) {
				listResCommentModel.add(convertToCommentModel(restaurantComment));
			}
			return listResCommentModel;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
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
		if (u != null) {
			vm.setUserName(u.getUserName());
			vm.setFullName(u.getFullName());
			vm.setUserId(u.getUserId());
		}

		Restaurant r = res.getRestaurant();
		if (r != null) {
			vm.setRestaurantName(r.getName());
			vm.setAddressLine(r.getAddressLine());
			vm.setRestaurantId(r.getRestaurantId());
		}
		vm.setAddressLine(res.getRestaurant().getAddressLine());
		vm.setDescription(res.getDescription());
		return vm;
	}

}
