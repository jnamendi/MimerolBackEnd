package bmbsoft.orderfoodonline.dao;

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

@Repository(value = "RestaurantWorkTimeDAO")
public class RestaurantWorkTimeDAO {
    public static final Logger logger = LoggerFactory.getLogger(RestaurantCommentDAO.class);
    @Autowired
    private SessionFactory sessionFactory;

    public RestaurantWorkTime findById(final long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(RestaurantWorkTime.class, id);
    }

    public Boolean save(RestaurantWorkTime restaurantWorkTime) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.saveOrUpdate(restaurantWorkTime);
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
        RestaurantWorkTime res = this.findById(id);
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

    public List<RestaurantWorkTime> getWorkTimeByRestaurant(final long restaurantId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RestaurantWorkTime> query = cb.createQuery(RestaurantWorkTime.class);
            Root<RestaurantWorkTime> form = query.from(RestaurantWorkTime.class);
            Join<RestaurantWorkTime, Restaurant> restaurant = form.join("restaurant");

            List<Predicate> predicates = new LinkedList<>();
            predicates.add(cb.and(cb.equal(restaurant.<Long>get("restaurantId"), restaurantId)));
            query.select(form).where(predicates.stream().toArray(Predicate[]::new));
            List<RestaurantWorkTime> c = session.createQuery(query).getResultList();
            List<RestaurantWorkTime> listResTimeModel = new LinkedList<>();
            for (RestaurantWorkTime res : c) {
                listResTimeModel.add(res);
            }
            return listResTimeModel;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public Boolean deleteByRestaurantId(long restaurantId) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        try {
            String q = "DELETE FROM restaurant_work_time WHERE restaurant_id=:resId";
            int c = s.createNativeQuery(q).setParameter("resId", restaurantId).executeUpdate();
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
