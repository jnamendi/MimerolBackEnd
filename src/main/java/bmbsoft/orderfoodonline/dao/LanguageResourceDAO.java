package bmbsoft.orderfoodonline.dao;

import java.util.List;

import javax.transaction.Transactional; 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.LanguageResource; 

@Repository(value = "languageResourceDAO")
@Transactional(rollbackOn = Exception.class)
public class LanguageResourceDAO {
	public static final Logger logger = LoggerFactory.getLogger(LanguageResourceDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public void Create(final LanguageResource l) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(l);
	}
	
	public void Update(final LanguageResource l) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(l);
	}

	public LanguageResource getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(LanguageResource.class, id);
	}

	public void delete(final LanguageResource l) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(l);
	}

	public List<LanguageResource> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		List<LanguageResource> cs= s.createQuery("FROM LanguageResource", LanguageResource.class).getResultList();
		return cs;
	}
}
