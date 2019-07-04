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

import bmbsoft.orderfoodonline.entities.AttributeGroup;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.shared.AttributeGroupRequest;
import bmbsoft.orderfoodonline.util.Constant;

@Repository(value = "attributeGroupDAO")
@Transactional(rollbackOn = Exception.class)
public class AttributeGroupDAO {
	public static final Logger logger = LoggerFactory.getLogger(AttributeGroupDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	LanguageDAO ls;

	public boolean saveBase(final AttributeGroup vm) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(vm);
			return true;
		} catch (Exception e) {
			logger.error(e.toString());

			return false;
		}
	}

	public void save(final AttributeGroupRequest vm) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			ContentDefinition cd = new ContentDefinition();
			cd.setName(vm.getCode());
			session.save(cd);

			AttributeGroup u = new AttributeGroup();
			u.setCode(vm.getCode());
			u.setCreatedDate(new Date());
			u.setStatus(vm.getStatus());
			u.setContentDefinition(cd);
			
			if (vm.getLanguageLst() != null && vm.getLanguageLst().size() > 0) {
				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								ContentEntry ce = new ContentEntry();
								ce.setCode(cdm.getCode());
								ce.setValue(cdm.getValue());
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

	public AttributeGroup getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(AttributeGroup.class, id);
	}

	public void delete(final AttributeGroup a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
	}

	public List<AttributeGroup> getAll() {
		Session s = sessionFactory.getCurrentSession();
		try {
			String q = "SELECT a FROM AttributeGroup a where a.status=:s";
			Query<AttributeGroup> c = s.createQuery(q).setParameter("s", Constant.Status.Publish.getValue());

			return c.list();
		} catch (Exception e) {
			return null;
		}
	}

	public void update(AttributeGroupRequest vm, AttributeGroup at) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			ContentDefinition cd = at.getContentDefinition();			 
  
			at.setCode(vm.getCode()); 
			at.setStatus(at.getStatus()); 
			
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
								ce.setCode(cdm.getCode());
								ce.setValue(cdm.getValue());
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
