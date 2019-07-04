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

import bmbsoft.orderfoodonline.entities.FoodType; 

@Repository(value = "foodTypeDAO")
@Transactional(rollbackOn = Exception.class)
public class FoodTypeDAO {
	public static final Logger logger = LoggerFactory.getLogger(FoodTypeDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void Create(final FoodType FoodType) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(FoodType);
	}

	public void Update(final FoodType FoodType) { 
		Session session = this.sessionFactory.getCurrentSession(); 
		session.update(FoodType);
	}

	public FoodType getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(FoodType.class, id);
	}

	public void delete(final FoodType FoodType) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(FoodType);
	}

	public List<FoodType> getAll() {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT a FROM FoodType a ";
			Query<FoodType> c = s.createQuery(q);

			return c.list();
		} catch (Exception e) {
			return null;
		} finally {
			s.close();
		}
	} 
}
