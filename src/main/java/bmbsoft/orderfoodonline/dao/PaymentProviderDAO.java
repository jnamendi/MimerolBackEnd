package bmbsoft.orderfoodonline.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import bmbsoft.orderfoodonline.entities.RestaurantPaymentProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.PaymentProvider;
import bmbsoft.orderfoodonline.entities.PaymentProvider;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.model.PaymentProviderViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "paymentProviderDAO")
@Transactional(rollbackOn = Exception.class)
public class PaymentProviderDAO {
	public static final Logger logger = LoggerFactory.getLogger(PaymentProviderDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final PaymentProvider a) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(a);
			
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}
 
	public PaymentProvider getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(PaymentProvider.class, id);
	}

	public void delete(final PaymentProvider a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
	}

	public List<PaymentProvider> getAllProvider() {
		Session s = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<PaymentProvider> cq = cb.createQuery(PaymentProvider.class);
		Root<PaymentProvider> from = cq.from(PaymentProvider.class);
		cq.where(cb.notEqual(from.get("isStatus"), Constant.Status.Deleted.getValue()));
		return s.createQuery(cq).getResultList();
	}

	public List<PaymentProvider> getProviderByRestaurant(long restaurantId) {
		Session s = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<RestaurantPaymentProvider> cq = cb.createQuery(RestaurantPaymentProvider.class);
		Root<RestaurantPaymentProvider> rpp = cq.from(RestaurantPaymentProvider.class);
		cq.select(rpp).where(cb.equal(rpp.join("restaurant").get("restaurantId"), restaurantId));
		List<RestaurantPaymentProvider> rsProviderList = s.createQuery(cq).getResultList();
		List<PaymentProvider> pp = new LinkedList<>();
		if(rsProviderList != null && !rsProviderList.isEmpty()) {
			rsProviderList.forEach(item -> pp.add(item.getPaymentProvider()));
		}
		return pp;
	}
}
