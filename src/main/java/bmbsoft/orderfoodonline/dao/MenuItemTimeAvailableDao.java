package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.MenuItemTimeAvailable;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.RestaurantWorkTime;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@Repository(value = "MenuItemTimeAvailableDao")
public class MenuItemTimeAvailableDao {
    public static final Logger logger = LoggerFactory.getLogger(MenuItemTimeAvailableDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public MenuItemTimeAvailable findById(final long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(MenuItemTimeAvailable.class, id);
    }

    public Boolean save(MenuItemTimeAvailable menuItemTimeAvailable) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.saveOrUpdate(menuItemTimeAvailable);
            t.commit();
            return true;
        } catch (Exception e) {
            t.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public Boolean delete(final long id) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        MenuItemTimeAvailable res = this.findById(id);
        try {
            if(res != null) {
                session.delete(res);
                return true;
            } else
            {
                return false;
            }
        } catch (Exception e) {
            t.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public List<MenuItemTimeAvailable> getMenuTimeAvailable(final long menuItemId) {
        Session session = sessionFactory.getCurrentSession();

            CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
            CriteriaQuery<MenuItemTimeAvailable> query = criteriaBd.createQuery(MenuItemTimeAvailable.class);
            Root<MenuItemTimeAvailable> root = query.from(MenuItemTimeAvailable.class);
            query.select(root).where(criteriaBd.equal(root.get("menuItem"), menuItemId));
            return session.createQuery(query).getResultList();
    }

    public Boolean deleteById(long Id) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        try {
            String q = "DELETE FROM menu_item_time_available WHERE menu_item_time_available_id=:Id";
            int c = s.createNativeQuery(q).setParameter("Id", Id).executeUpdate();
            t.commit();
            return c > 0;
        } catch (Exception e) {
            t.rollback();
            return false;
        } finally {
            s.close();
        }
    }
}
