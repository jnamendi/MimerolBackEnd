package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.Zone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository(value = "zoneDAO")
@Transactional(rollbackOn = Exception.class)
public class ZoneDAO {
    public static final Logger logger = LoggerFactory.getLogger(ZoneDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Zone getById(final long id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            return session.get(Zone.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<Zone> getAll() {
        Session s = this.sessionFactory.getCurrentSession();
        try {
            List<Zone> cs = s.createQuery("FROM zone", Zone.class).getResultList();
            return cs;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<Zone> getZoneByDistrictId(long districtId) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Zone> query = cb.createQuery(Zone.class);
        Root<Zone> root = query.from(Zone.class);

        Join<Zone, Object> join = root.join("district");
        query =query.select(root)
                .where(cb.and(
                        cb.equal(join.get("districtId"), districtId)));

        List<Zone> c = session.createQuery(query).getResultList();
        if (c != null && c.size() > 0) {
            return c;
        }
        return null;
    }

}
