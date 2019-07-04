package bmbsoft.orderfoodonline.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import bmbsoft.orderfoodonline.entities.ExtraItem;
import bmbsoft.orderfoodonline.entities.MenuItem;
import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.entities.OrderExtraItem;
import bmbsoft.orderfoodonline.entities.OrderInfo;
import bmbsoft.orderfoodonline.entities.OrderLineItem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.shared.OrderInfoRequest;
import bmbsoft.orderfoodonline.model.shared.OrderRequest;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

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

			session.save(oi);

			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
			return false;
		}
	}
}
