package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.RestaurantArea;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository(value = "restaurantAreaDAO")
@Transactional(rollbackFor = Exception.class)
public class RestaurantAreaDAO {
    public static final Logger logger = LoggerFactory.getLogger(RestaurantAreaDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public boolean save(final RestaurantAreaDAO resArea) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(resArea);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public List<RestaurantArea> getZoneIdByRestaurantId(long restaurantId) {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
        CriteriaQuery<RestaurantArea> query = criteriaBd.createQuery(RestaurantArea.class);
        Root<RestaurantArea> root = query.from(RestaurantArea.class);
        query.select(root).where(criteriaBd.equal(root.get("restaurant"), restaurantId));
        return session.createQuery(query).getResultList();
    }

    public boolean checkZone(long zoneId,long restaurantId) {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
        CriteriaQuery<RestaurantArea> query = criteriaBd.createQuery(RestaurantArea.class);
        Root<RestaurantArea> root = query.from(RestaurantArea.class);
        query.select(root).where(toPredicate(root, criteriaBd, zoneId, restaurantId));
        return !session.createQuery(query).getResultList().isEmpty();
    }

    private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, long zoneId, long restaurantId) {
        Predicate predicate = cb.conjunction();
        predicate = cb.and(predicate, cb.equal(root.<Long>get("restaurant"), restaurantId));
        predicate = cb.and(predicate, cb.equal(root.<Integer>get("zone"), zoneId));
        return predicate;
    }
}
