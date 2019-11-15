package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.UserFCM;
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
import java.util.ArrayList;
import java.util.List;

@Repository(value = "userFCMDAO")
@Transactional(rollbackFor = Exception.class)
public class UserFCMDAO {
    public static final Logger logger = LoggerFactory.getLogger(UserFCMDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public boolean save(UserFCM userFCM) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(userFCM);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public List<UserFCM> getByUser(long userId) {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
        CriteriaQuery<UserFCM> query = criteriaBd.createQuery(UserFCM.class);
        Root<UserFCM> form = query.from(UserFCM.class);
        query.select(form).where(criteriaBd.equal(form.join("user").get("userId"), userId));
        List<UserFCM> userFCMS = session.createQuery(query).getResultList();
        return userFCMS;
    }

    public UserFCM getByUserIdAndDeviceId(Long userId, String deviceId) {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBd = session.getCriteriaBuilder();
        CriteriaQuery<UserFCM> query = criteriaBd.createQuery(UserFCM.class);
        Root<UserFCM> root = query.from(UserFCM.class);

        List<Predicate> predicates = new ArrayList<>();

        //Adding predicates in case of parameter not being null
        if (userId != null) {
            predicates.add(
                    criteriaBd.equal(root.get("user"), userId));
        }
        if (deviceId != null) {
            predicates.add(
                    criteriaBd.equal(root.get("deviceId"), deviceId));
        }
        query.select(root)
                .where(predicates.toArray(new Predicate[]{}));
        List<UserFCM> userFCMS = session.createQuery(query).getResultList();
        return userFCMS.isEmpty() ? null : userFCMS.get(0);
    }
}
