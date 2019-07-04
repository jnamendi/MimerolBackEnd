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

	public ResponseGetPaging getAll(int pageIndex, int pageSize, String paymentName) {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			int currentPage = (pageIndex < 1) ? 1 : pageIndex;
			int firstResult = (currentPage - 1) * pageSize;
			int maxResult = currentPage * pageSize;

			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<PaymentProvider> cq = cb.createQuery(PaymentProvider.class);
			Root<PaymentProvider> from = cq.from(PaymentProvider.class);

			//Join<PaymentProvider, Restaurant> join = from.join("restaurant");
			cq.where(cb.notEqual(from.get("isStatus"), Constant.Status.Deleted.getValue()));
			if (paymentName != null && !paymentName.isEmpty()) {
				cq.where(cb.like(from.get("name"), '%' + paymentName + '%'));
			}
			cq.orderBy(cb.desc(from.get("sortOrder")));

			TypedQuery<PaymentProvider> tq = s.createQuery(cq);
			List<PaymentProvider> PaymentProvideres = tq.getResultList();

			int totalRecord = PaymentProvideres.size();
			PaymentProvideres = maxResult == 0 ? tq.getResultList()
					: tq.setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

			ResponseGetPaging rs = new ResponseGetPaging();
			if (PaymentProvideres == null || PaymentProvideres.size() <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found items.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return rs;
			}

			List<PaymentProviderViewModel> rvm = new LinkedList<>();
			PaymentProvideres.forEach(r -> {
				PaymentProviderViewModel rv = convertEntityToModel(r);
				rvm.add(rv);
			});
			Data content = new Data();
			if (totalRecord > 0 && !rvm.isEmpty()) {
				content.setData(rvm);
				content.setPageIndex(pageIndex);
				content.setPageSize(pageSize);
				content.setTotalCount(totalRecord);
				rs.setContent(content);
				rs.setStatus(0);
				rs.setMessage("Success.");

				return rs;
			}

			rs.setStatus(8);
			rs.setMessage("Could not found items.");
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

			return rs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private PaymentProviderViewModel convertEntityToModel(PaymentProvider r) {
		PaymentProviderViewModel vm = new PaymentProviderViewModel();
		vm.setPaymentProviderId(r.getPaymentProviderId());
		vm.setName(r.getName());
		vm.setStatus(r.getIsStatus());
		vm.setSortOrder(r.getSortOrder());
		
		return vm;
	}
}
