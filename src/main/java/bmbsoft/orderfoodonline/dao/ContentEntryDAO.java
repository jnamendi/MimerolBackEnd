package bmbsoft.orderfoodonline.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.entities.ContentEntry;

@Repository(value = "contentEntryDAO")
@Transactional(rollbackFor = Exception.class)
public class ContentEntryDAO {
	public static final Logger logger = LoggerFactory.getLogger(ContentEntryDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(final ContentEntry contentEntry) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(contentEntry);
	}

	public void update(final ContentEntry contentEntry) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(contentEntry);
	}

	public ContentEntry getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(ContentEntry.class, id);
	}

	public void delete(final ContentEntry contentEntry) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(contentEntry);
	}

	public int deleteByContentDefId(final Long condefId) {
		Session session = this.sessionFactory.getCurrentSession();
		String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";

		return session.createNativeQuery(q).setParameter("cd", condefId).executeUpdate();
	}

	public List<ContentEntry> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM ContentEntry a ";
			Query<ContentEntry> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}

	public ContentEntry getByContentDefAndLang(Long condefId, Long langId, String code) {
		Session s = sessionFactory.getCurrentSession();
		try {
			String q = "select a from ContentEntry a " + "inner join a.contentDefinition b "
					+ "inner join a.language l "
					+ "where b.contentDepId=:condefId and l.languageId=:langId and a.code=:code";
			Query<ContentEntry> c = s.createQuery(q).setParameter("langId", langId).setParameter("condefId", condefId)
					.setParameter("code", code);

			ContentEntry ce = c.list().get(0);
			return ce;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			s.close();
		}
	}

}
