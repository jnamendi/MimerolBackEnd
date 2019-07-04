package bmbsoft.orderfoodonline.dao;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

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

import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.entities.VoucherLineitem;
import bmbsoft.orderfoodonline.model.VoucherRequest;
import bmbsoft.orderfoodonline.model.shared.VoucherLiteResponse;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "voucherDAO")
@Transactional(rollbackOn = Exception.class)
public class VoucherDAO {
	public static final Logger logger = LoggerFactory.getLogger(VoucherDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean Create(VoucherRequest pr) {
		Session session = this.sessionFactory.getCurrentSession();
		Voucher promotion = this.modelToEnity(pr, null);
		return (null == session.save(promotion)) ? false : true;
	}

	public Boolean Update(VoucherRequest pr, Voucher p) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(this.modelToEnity(pr, p));
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean updateBase(Voucher p) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(p);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Voucher getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Voucher.class, id);
	}

	public boolean getByIdAndCode(final long id, String voucherCode) {
		Session session = this.sessionFactory.getCurrentSession();
		List<VoucherLineitem> lvl = session
				.createQuery("FROM VoucherLineitem v inner join v.voucher vc where v.code=:code and vc.voucherId=:vId")
				.setParameter("code", voucherCode).setParameter("vId", id).getResultList();
		if (lvl != null && lvl.size() > 0) {
			return true;
		}
		return false;
	}

	public Voucher getByCode(String code) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<VoucherLineitem> query = cb.createQuery(VoucherLineitem.class);
		Root<VoucherLineitem> from = query.from(VoucherLineitem.class);
		Join<VoucherLineitem, Voucher> join = from.join("voucher");

		query = query.select(from).distinct(true)
				.where(cb.and(cb.equal(from.get("code"), code),
						cb.equal(from.get("status"), Constant.Status.Publish.getValue()),
						cb.equal(join.get("status"), Constant.Status.Publish.getValue()),
						cb.greaterThanOrEqualTo(join.<Date>get("endDate"), new Date()),
						cb.lessThanOrEqualTo(join.<Date>get("startDate"), new Date())));

		List<VoucherLineitem> lst = session.createQuery(query).getResultList();

		if (lst != null && lst.size() > 0) {
			return lst.get(0).getVoucher();
		}

		return null;
	}

	public void delete(final Voucher promotion) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(promotion);
	}

	public List<Voucher> getAll(int firstResult, int maxResult) {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Voucher> query = cb.createQuery(Voucher.class);
		Root<Voucher> form = query.from(Voucher.class);

		// List<Predicate> predicates = new ArrayList<>();
		// predicates.add(cb.equal(rootRestaurant.get("keySearch"), key));
		// query.select(form).where(predicates.stream().toArray(Predicate[]::new));
		// query.distinct(form.get("menuId") != null);
		query.select(form);
		List<Voucher> menus = (maxResult == 0) ? session.createQuery(query).getResultList()
				: session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

		return menus;
	}

	private Voucher modelToEnity(VoucherRequest vr, Voucher v) {
		if (v == null) {
			Voucher np = new Voucher();
			np.setName(vr.getName());
			np.setCode(vr.getCode());
			np.setStartDate(vr.getStartDate());
			np.setEndDate(vr.getEndDate());
			np.setDescription(vr.getDescription());
			np.setCreatedDate(new Date());
			np.setValue(vr.getValue());
			// np.setCreatedBy();
			return np;
		}
		v.setName(vr.getName());
		v.setCode(vr.getCode());
		v.setStartDate(vr.getStartDate());
		v.setEndDate(vr.getEndDate());
		v.setDescription(vr.getDescription());
		v.setModifiedDate(new Date());
		v.setValue(vr.getValue());
		// p.setModifiedBy(modifiedBy);
		return v;
	}
}
