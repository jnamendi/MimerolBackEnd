package bmbsoft.orderfoodonline.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "languageDAO")
@Transactional(rollbackFor = Exception.class)
public class LanguageDAO {
	public static final Logger logger = LoggerFactory.getLogger(LanguageDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(final Language l) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			s.saveOrUpdate(l);
			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();

			return false;
		} finally {
			s.close();
		}
	}

	public Language getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Language.class, id);
	}

	public boolean delete(final Language l) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		try {
			l.setStatus(Constant.Status.Deleted.getValue());
			l.setModifiedDate(new Date());
			t.commit();
			return true;

		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();

			return false;
		} finally {
			s.close();
		}
	}

	public List<LanguageViewModel> getAll() {
		Session s = null;

		try {
			s = this.sessionFactory.openSession();
			// Transaction transaction = s.beginTransaction();
			String q = "SELECT l.language_id as languageId, l.name as name, l.code as code " + " FROM language l";
			// List<Object[]> l = s.createNativeQuery(q).getResultList();

			List<LanguageViewModel> l = s.createNativeQuery(q)
					// .setParameter("s", Constant.Status.Deleted.hashCode())
					.addScalar("languageId", StandardBasicTypes.LONG).addScalar("name").addScalar("code")
					.setResultTransformer(new AliasToBeanResultTransformer(LanguageViewModel.class)).list();

			// for (Object[] objects : l) {
			// //id = ((BigInteger) objects[0]).longValue();
			// }
			// transaction.commit();
			return l;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			s.close();
		}
	}

	public Boolean checkExistLang(String code) {
		Session s = null;
		try {
			s = this.sessionFactory.openSession();
			String q = "SELECT l FROM Language l WHERE l.code=:code";
			List<Language> l = s.createQuery(q).setParameter("code", code).getResultList();
			return l != null && l.size() > 0;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			s.close();
		}
	}

	public Language getLanguageByCode(final String codeLang) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Language> queryLanguage = cb.createQuery(Language.class);
			Root<Language> fromLang = queryLanguage.from(Language.class);
			queryLanguage.select(fromLang).where(cb.and(cb.equal(fromLang.get("code"), codeLang),
					cb.notEqual(fromLang.get("status"), Constant.Status.Deleted.getValue())));
			Language language = session.createQuery(queryLanguage).getSingleResult();
			return language;
		} catch (NoResultException e) {
			// Ignore this because not found by input condition
			return null;
		}
	}

}
