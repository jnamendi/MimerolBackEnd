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

import bmbsoft.orderfoodonline.entities.ContentEmail;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "contentEmailDAO")
@Transactional(rollbackOn = Exception.class)
public class ContentEmailDAO {
	public static final Logger logger = LoggerFactory.getLogger(ContentEmailDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final ContentEmail f) {
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

	public ContentEmail getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(ContentEmail.class, id);
	}

	public ContentEmail getByType(int type, String code) {
		Session session = this.sessionFactory.getCurrentSession();
		String q = "SELECT a FROM ContentEmail a where type=:type and isStatus=:sta";
		List<ContentEmail> ce = session.createQuery(q).setParameter("sta", Constant.Status.Publish.getValue())
				.setParameter("type", type).list();
		if (ce != null && ce.size() > 0) {

			ContentEmail c = ce.get(0);
			if (code.isEmpty()) {
				return c;
			}

			Language l = c.getLanguage();
			ContentEmail cem = new ContentEmail();
			if (l.getCode().equals(code)) {
				cem.setSubject(c.getSubject());
				cem.setTitle(c.getTitle());
				cem.setContentBody(c.getContentBody());

				return cem;
			}

		}
		return null;
	}

	public boolean delete(final ContentEmail f) {
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

	public List<ContentEmail> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM ContentEmail a ";
			Query<ContentEmail> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}
}
