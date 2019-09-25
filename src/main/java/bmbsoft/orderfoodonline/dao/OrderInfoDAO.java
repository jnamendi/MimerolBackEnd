package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.entities.OrderInfo;
import bmbsoft.orderfoodonline.model.shared.OrderInfoRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "orderInfoDAO")
@Transactional(rollbackOn = Exception.class)
public class OrderInfoDAO {
	public static final Logger logger = LoggerFactory.getLogger(OrderInfoDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean create(OrderInfoRequest req) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			OrderInfo oi = new OrderInfo();
			oi.setOrder(req.getOrder());
			oi.setCompanyName(req.getCompanyName());
			oi.setInfoEmail(req.getEmal());
			oi.setInfoName(req.getName());
			oi.setInfoNumber(req.getNumber());
			oi.setAddress(req.getAddress());
			oi.setCity(req.getCity());
			oi.setDistrict(req.getDistrict());
			oi.setTime(req.getTime());
			oi.setRemark(req.getRemark());
			oi.setZone(req.getZone());

			session.save(oi);

			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
			return false;
		} finally {
			session.close();
		}
	}

	public OrderInfo getByOrderId(Long orderId) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<OrderInfo> query = cb.createQuery(OrderInfo.class);
		Root<OrderInfo> root = query.from(query.getResultType());
		Join<OrderInfo, Order> order = root.join("order");

		query.select(root);
		query.select(root).where(cb.equal(order.get("orderId"), orderId));

		List<OrderInfo> orderInfo = session.createQuery(query).getResultList();
		return orderInfo == null || orderInfo.isEmpty() ? null : orderInfo.get(0);
	}
}
