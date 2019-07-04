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

import bmbsoft.orderfoodonline.entities.ContentDefinition;

@Repository(value = "contentDefinitionDAO")
@Transactional(rollbackFor = Exception.class)
public class ContentDefinitionDAO {
	public static final Logger logger = LoggerFactory.getLogger(ContentDefinitionDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Long save(final ContentDefinition c) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(c); 
		return c.getContentDepId();
	}

	public ContentDefinition getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(ContentDefinition.class, id);
	}

	public void delete(final ContentDefinition ContentDefinition) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(ContentDefinition);
	}

	public List<ContentDefinition> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM ContentDefinition a ";
			Query<ContentDefinition> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	}
}
