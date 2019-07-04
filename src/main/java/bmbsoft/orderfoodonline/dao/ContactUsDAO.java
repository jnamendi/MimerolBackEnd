package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.ContactUs;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "contactUsDAO")
@Transactional(rollbackOn = Exception.class)
public class ContactUsDAO {
	public static final Logger logger = LoggerFactory.getLogger(ContactUsDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final ContactUs f) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			s.saveOrUpdate(f);
			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();

			return false;
		} finally {
			s.close();
		}
	}

	public ContactUs getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(ContactUs.class, id);
	}

	public ContactUs getByType(int type) {
		Session session = this.sessionFactory.getCurrentSession();
		String q = "SELECT * FROM ContactUs where contactType=:type and isStatus=:sta";
		List<ContactUs> ce = session.createNativeQuery(q).setParameter("sta", Constant.Status.Publish.getValue())
				.setParameter("type", type).list();
		if (ce != null && ce.size() > 0) {
			return ce.get(0);
		}
		return null;
	}

	public boolean delete(final ContactUs f) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			f.setIsStatus(Constant.Status.Deleted.getValue());
			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();

			return false;
		} finally {
			s.close();
		}
	}

	public List<ContactUs> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM ContactUs a where isStatus<>:sta";
			Query<ContactUs> c = s.createQuery(q).setParameter("sta", Constant.Status.Deleted.getValue());

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}
}
