package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.RestaurantDeliveryCost;
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

@Repository(value = "RestaurantDeliveryCostDao")
public class RestaurantDeliveryCostDAO {
    public static final Logger logger = LoggerFactory.getLogger(RestaurantCommentDAO.class);
    @Autowired
    private SessionFactory sessionFactory;

    public RestaurantDeliveryCost findById(final long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(RestaurantDeliveryCost.class, id);
    }

    public Boolean save(RestaurantDeliveryCost restaurantDeliveryCost) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.saveOrUpdate(restaurantDeliveryCost);
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
        RestaurantDeliveryCost res = this.findById(id);
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

    public List<RestaurantDeliveryCost> getDeliveryCostByRestaurant(final long restaurantId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RestaurantDeliveryCost> query = cb.createQuery(RestaurantDeliveryCost.class);
            Root<RestaurantDeliveryCost> form = query.from(RestaurantDeliveryCost.class);
            Join<RestaurantDeliveryCost, Restaurant> restaurant = form.join("restaurant");
            List<Predicate> predicates = new LinkedList<>();
            predicates.add(cb.and(cb.equal(restaurant.<Long>get("restaurantId"), restaurantId)));
            query.select(form).where(predicates.stream().toArray(Predicate[]::new));
            List<RestaurantDeliveryCost> c = session.createQuery(query).getResultList();
            List<RestaurantDeliveryCost> listRestaurantDeliveryCost = new LinkedList<>();

            for (RestaurantDeliveryCost res : c) {
                listRestaurantDeliveryCost.add(res);
            }

            return listRestaurantDeliveryCost;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }



    public Boolean deleteByRestaurantId(long restaurantId) {
        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();
        try {
            String q = "DELETE FROM restaurant_delivery_cost WHERE restaurant_id=:resId";
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
