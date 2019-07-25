package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.entities.*;
import bmbsoft.orderfoodonline.model.OrderViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.UserRestaurantService;
import bmbsoft.orderfoodonline.util.Constant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "orderDAO")
@Transactional(rollbackOn = Exception.class)
public class OrderDAO {
	public static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserRestaurantService userRestaurantService;

	public boolean update(final Order Order) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(Order);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean createHis(final OrderHistory hi) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(hi);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Order getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(Order.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public void delete(final Order Order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Order);
	}

	public ResponseGetPaging getAll(int pageIndex, int pageSize, String orderCode, String restaurantName,
			Integer status) {
		Session s = this.sessionFactory.getCurrentSession();
		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		try {
			int currentPage = (pageIndex < 1) ? 1 : pageIndex;
			int firstResult = (currentPage - 1) * pageSize;
			int maxResult = currentPage * pageSize;

			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Order> query = cb.createQuery(Order.class);
			Root<Order> from = query.from(Order.class);

			List<Predicate> predicates = new LinkedList<>();
			if (orderCode != null && !orderCode.isEmpty()) {
				predicates.add(cb.equal(from.get("orderCode"), orderCode));
			}

			if (restaurantName != null && !restaurantName.isEmpty()) {
				predicates.add(cb.like(from.join("restaurant").get("name"), "%" + restaurantName + "%"));
			}

			if (status != null) {
				predicates.add(cb.equal(from.get("status"), status));
			}
			query.orderBy(cb.desc(from.get("orderDate")));

			query.select(from).where(predicates.stream().toArray(Predicate[]::new));
			TypedQuery<Order> tq = s.createQuery(query);
			List<Order> orders = tq.getResultList();

			int totalRecord = orders.size();
			orders = maxResult == 0 ? tq.getResultList()
					: tq.setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

			if (orders == null || orders.size() <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found items.");
				content.setTotalCount(0);
				rs.setContent(content);
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return rs;
			}

			List<OrderViewModel> rvm = new LinkedList<>();
			orders.forEach(r -> {
				OrderViewModel rv = convertEntityToModel(r);
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

	private OrderViewModel convertEntityToModel(Order o) {
		OrderViewModel vm = new OrderViewModel();
		vm.setOrderId(o.getOrderId());
		vm.setOrderCode(o.getOrderCode());
		vm.setOrderDate(o.getOrderDate());
		vm.setStatus(o.getStatus());
		vm.setTotalPrice(o.getTotalPrice());

		Restaurant r = o.getRestaurant();
		if (r != null) {
			vm.setRestaurantName(r.getName());
			vm.setCityName(r.getCity());
			if(r.getDistrict()!=null) {
				vm.setDistrictName(r.getDistrict().getName());
			}
			
		}
		User u = o.getUser();
		if (u != null) {
			vm.setUserName(u.getUserName());
		}
		return vm;
	}

	public List<Order> getOderByUser(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Order> query = cb.createQuery(Order.class);
		Root<Order> form = query.from(Order.class);

		List<Predicate> predicates = new LinkedList<>();
		// predicates.add(cb.equal(rootRestaurant.get("keySearch"), key));
		predicates.add(cb.and(cb.equal(form.join("user").<Long>get("userId"), userId)));
		predicates.add(cb.and(cb.notEqual(form.<Integer>get("status"), Constant.Status.Deleted.getValue())));
		query.select(form).where(predicates.stream().toArray(Predicate[]::new));
		List<Order> menus = session.createQuery(query).getResultList();

		return menus;
	}

	@Transactional
	public List<Order> getOrderByOwner(Long ownerId) {
		try {
			List<UserRestaurant> userRestaurantList = userRestaurantService.getByUser(ownerId);
			List<Long> restaurantId = userRestaurantList.stream().map(p -> p.getRestaurant().getRestaurantId()).collect(Collectors.toList());

			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Order> query = cb.createQuery(Order.class);
			Root<Order> root = query.from(Order.class);
			Expression<String> exp = root.get("restaurant");

			List<Predicate> predicates = new LinkedList<>();
			predicates.add(cb.and(exp.in(restaurantId)));
			predicates.add(cb.and(cb.notEqual(root.<Integer>get("status"), Constant.Status.Deleted.getValue())));
			query.select(root).where(predicates.stream().toArray(Predicate[]::new)).orderBy(cb.desc(root.get("orderId")));
			List<Order> orders = session.createQuery(query).getResultList();

			return orders;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public List<Order> getOrderBy(int firstResult, int maxResult, Long restaurantId, Long status) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Order> query = cb.createQuery(Order.class);
			Root<Order> from = query.from(Order.class);

			Join<Order, Restaurant> restaurant = from.join("restaurant");

			List<Predicate> predicates = new LinkedList<>();
			if (status != null) {
				predicates.add(cb.and(cb.equal(from.<Integer>get("status"), status)));
			}
			predicates.add(cb.and(cb.equal(restaurant.get("restaurantId"), restaurantId)));
			query.select(from).where(predicates.stream().toArray(Predicate[]::new)).orderBy(cb.desc(from.get("orderDate")));
			List<Order> menus = (maxResult == 0) ? session.createQuery(query).getResultList()
					: session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

			return menus;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
	
	public int countGetAll(Long restaurantId, Long status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<Order> countFrom = queryCount.from(Order.class);
		queryCount.select(cb.countDistinct(countFrom));

		Join<Order, Restaurant> restaurant = countFrom.join("restaurant");

		List<Predicate> predicates = new LinkedList<>();
		if (status != null) {
			predicates.add(cb.and(cb.equal(countFrom.<Integer>get("status"), status)));
		}
		predicates.add(cb.and(cb.equal(restaurant.get("restaurantId"), restaurantId))); 
		queryCount.where(predicates.stream().toArray(Predicate[]::new)).orderBy(cb.desc(countFrom.get("orderDate")));
		
		Long totalCount = null;
		try {
			totalCount = session.createQuery(queryCount).getSingleResult();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}
		return totalCount.intValue();
	}

}
