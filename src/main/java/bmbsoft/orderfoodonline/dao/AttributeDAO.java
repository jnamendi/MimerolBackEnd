package bmbsoft.orderfoodonline.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Attribute;
import bmbsoft.orderfoodonline.entities.AttributeGroup;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.AttributeViewModel;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "attributeDAO")
@Transactional(rollbackOn = Exception.class)
public class AttributeDAO {
	public static final Logger logger = LoggerFactory.getLogger(AttributeDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	LanguageService ls;

	public boolean saveBase(final Attribute vm) {
		try {
			Session session = this.sessionFactory.getCurrentSession();

			session.saveOrUpdate(vm);

			return true;

		} catch (Exception e) {
			logger.error(e.toString());

			return false;
		}
	}

	@Transactional
	public void save(final AttributeViewModel vm, AttributeGroup ag) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			ContentDefinition cd = new ContentDefinition();
			cd.setName("attribute_name");
			session.save(cd);

			Attribute u = new Attribute();
			u.setCode(vm.getCode());
			u.setIsStatus(vm.getStatus());
			u.setContentDefinition(cd);
			u.setAttributeGroup(ag);
			
			if (vm.getLanguageLst() != null && vm.getLanguageLst().size() > 0) {
				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								ContentEntry ce = new ContentEntry();
								ce.setCode(cdm.getCode().trim());
								ce.setValue(cdm.getValue().trim());
								ce.setContentDefinition(cd);
								ce.setLanguage(lang);

								session.save(ce);
							}
						}
					}
				}
			}
			session.save(u);

			t.commit();
		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();
		} finally {
			session.close();
		}
	}

	public Attribute getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Attribute.class, id);
	}

	public void delete(final Attribute a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
	}

	public List<Attribute> getAll() {  
		try {
			Session s = this.sessionFactory.getCurrentSession();
			String q = "SELECT a FROM Attribute a where a.isStatus=:s";
			Query<Attribute> c = s.createQuery(q).setParameter("s", Constant.Status.Publish.getValue());

			return c.list();
		} catch (Exception e) {
			return null;
		}  
	}

	@Transactional
	public void update(AttributeViewModel vm, Attribute at) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			ContentDefinition cd = at.getContentDefinition();

			at.setCode(vm.getCode());

			String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
			Long cdId = cd.getContentDepId();
			int d = session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();

			if (vm.getLanguageLst() != null && vm.getLanguageLst().size() > 0) {
				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								ContentEntry ce = new ContentEntry();
								ce.setCode(cdm.getCode().trim());
								ce.setValue(cdm.getValue().trim());
								ce.setContentDefinition(cd);
								ce.setLanguage(lang);

								session.save(ce);
							}
						}
					}
				}
			}
			session.update(at);

			t.commit();
		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();
		} finally {
			session.close();
		}

	}
}
