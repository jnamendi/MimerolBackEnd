package bmbsoft.orderfoodonline.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.entities.VoucherLineitem;
import bmbsoft.orderfoodonline.model.VoucherLineItemRequest;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "voucherLineItemDAO")
@Transactional(rollbackOn = Exception.class)
public class VoucherLineItemDAO {
	public static final Logger logger = LoggerFactory.getLogger(VoucherDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean create(VoucherLineitem p) {
		Session session = this.sessionFactory.getCurrentSession();
		return (null == session.save(p)) ? false : true;
	}

	public Boolean update(VoucherLineitem pli) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(pli);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public VoucherLineitem getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(VoucherLineitem.class, id);
	}

	public void delete(final VoucherLineitem pli) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(pli);
	}

	public List<VoucherLineitem> getAll(int firstResult, int maxResult) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<VoucherLineitem> query = cb.createQuery(VoucherLineitem.class);
		Root<VoucherLineitem> form = query.from(VoucherLineitem.class);

		// List<Predicate> predicates = new ArrayList<>();
		// predicates.add(cb.equal(rootRestaurant.get("keySearch"), key));
		// query.select(form).where(predicates.stream().toArray(Predicate[]::new));
		// query.distinct(form.get("menuId") != null);
		query.select(form);
		List<VoucherLineitem> plis = (maxResult == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return plis;
	}

	public String createVoucherLineItem(VoucherLineItemRequest vlirs, Voucher p) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction(); 
		try {
			for (int i = 0; i < vlirs.getNumberOfCode(); i++) {
				VoucherLineitem vli = new VoucherLineitem();
				vli.setCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
				vli.setVoucher(p);
				vli.setCreatedDate(new Date());
				vli.setModifiedDate(new Date());
				vli.setStatus(Constant.Status.Publish.getValue());

				s.save(vli); 
			}
			t.commit();
			return "";
		} catch (Exception e) {
			t.rollback();
			return e.toString();
		} finally {
			s.close();
		}
	}
}
