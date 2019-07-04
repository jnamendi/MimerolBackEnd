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
import bmbsoft.orderfoodonline.entities.OrderLineItem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.shared.OrderRequest;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "orderTransactionDAO")
@Transactional(rollbackOn = Exception.class)
public class OrderTransactionDAO {
	public static final Logger logger = LoggerFactory.getLogger(OrderTransactionDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private RestaurantDAO rd;

	@Autowired
	private UserDAO ud;

	@Autowired
	private MenuItemDAO mid;

	@Autowired
	private ExtraItemDAO eid;

	ObjectMapper mapper = new ObjectMapper();

	boolean isOk = true;
	String msg = "";

	public String create(OrderRequest req) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			// check restaurant
			Restaurant r = rd.getById(req.getRestaurantId());
			if (r == null) {
				return "Restaurant not exist";
			}
			User u = null;
			if (req.getUserId() == null) {
				u = new User();
				String hpw = CommonHelper.HasPw("1234567aA");
				u.setUserHash(hpw);
				u.setUserSalt(hpw);
				u.setProvider(Constant.Provider.NORMAL.getValue());
				u.setUserName(UUID.randomUUID().toString());
				u.setFullName("anonymous");
				u.setEmail("anonymous@change.me");
				u.setCreatedDate(new Date());
				u.setStatus(Constant.Status.Publish.getValue());
				u.setReceiveNewsletter(true);
				u.setAccountType(Constant.AccountType.Private.getValue());
				u.setIsLock(false);

				session.save(u);
				// create user anomyus
			} else {
				// check user
				u = ud.findById(req.getUserId());
			}

			if (u == null) {
				return "User not exist";
			}

			// save order
			Order o = new Order();
			o.setCurrencyCode(req.getCurrencyCode());
			o.setOrderDate(new Date());
			o.setUser(u);
			o.setRestaurant(r);
			o.setStatus(Constant.Order.New.getValue());
			o.setTaxTotal(req.getTaxTotal());
			o.setTotalPrice(req.getTotalPrice());
			String json1 = mapper.writeValueAsString(req);
			o.setOrderReq(json1);

			session.save(o);

			// save orderline item
			if (req.getOrderLineItemsRequest() != null && req.getOrderLineItemsRequest().size() > 0) {
				req.getOrderLineItemsRequest().forEach(ol -> {
					OrderLineItem oli = new OrderLineItem();
					oli.setCreatedDate(new Date());
					oli.setOrder(o);
					MenuItem mi = mid.getById(ol.getMenuItemId());
					if (mi != null) {
						oli.setMenuItem(mi);
					} else {
						isOk = false;
						msg = "Menu item is not exist.";
					}
					oli.setUnitPrice(ol.getPrice());
					oli.setQuantity(ol.getQuantity());
					oli.setStatus(Constant.Status.Publish.getValue());
					oli.setMenuItemName(ol.getMenuItemName());
					oli.setDiscountTotal(ol.getDiscount());
					oli.setTotal(ol.getTotalPrice());

					session.save(oli);

					// save extraItem
					if (ol.getExtraItemRequest() != null && ol.getExtraItemRequest().size() > 0) {
						ol.getExtraItemRequest().forEach(ex -> {
							OrderExtraItem ei = new OrderExtraItem();
							ei.setMenuItemId(mi.getMenuItemId());
							ei.setTotalPrice(ex.getPrice());
							ei.setUnitPrice(ex.getUnitPrice());
							ExtraItem exi = eid.getById(ex.getExtraItemId());
							if (exi != null)
								ei.setMenuExtraItemId(exi.getExtraItemId());
							else {
								isOk = false;
								msg = "Menu item is not exist.";
							}
							session.save(ei);
						});
					}
				});
			}
			if (isOk) {
				t.commit();
			}

			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
			return e.toString();
		}
	}

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

	public List<Order> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			List<Order> cs = s.createQuery("FROM Order", Order.class).getResultList();
			return cs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
