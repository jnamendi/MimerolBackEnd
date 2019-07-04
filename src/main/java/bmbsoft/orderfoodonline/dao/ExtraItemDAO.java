package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.ExtraItem;
import bmbsoft.orderfoodonline.entities.MenuExtraItem; 

@Repository(value = "extraItemDAO")
@Transactional(rollbackOn = Exception.class)
public class ExtraItemDAO {
	public static final Logger logger = LoggerFactory.getLogger(ExtraItemDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void Create(final ExtraItem ExtraItem) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(ExtraItem);
	}

	public void Update(final ExtraItem ExtraItem) { 
		Session session = this.sessionFactory.getCurrentSession(); 
		session.update(ExtraItem);
	}

	public ExtraItem getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(ExtraItem.class, id);
	}
	public MenuExtraItem getByExtraItemId(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(MenuExtraItem.class, id);
	}

	public void delete(final ExtraItem ExtraItem) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(ExtraItem);
	}

	public List<ExtraItem> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM ExtraItem a ";
			Query<ExtraItem> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	} 
}
