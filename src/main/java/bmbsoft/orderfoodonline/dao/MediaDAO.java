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

import bmbsoft.orderfoodonline.entities.Media;
import bmbsoft.orderfoodonline.entities.Country;

@Repository(value = "mediaDAO")
@Transactional(rollbackFor = Exception.class)
public class MediaDAO {
	public static final Logger logger = LoggerFactory.getLogger(MediaDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void Create(final Media Media) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(Media);
	}

	public void Update(final Media Media) { 
		Session session = this.sessionFactory.getCurrentSession(); 
		session.update(Media);
	}

	public Media getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Media.class, id);
	}

	public void delete(final Media Media) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Media);
	}

	public List<Media> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM Media a ";
			Query<Media> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	} 
}
