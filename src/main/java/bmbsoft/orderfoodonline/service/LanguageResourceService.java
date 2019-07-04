package bmbsoft.orderfoodonline.service;

 
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import bmbsoft.orderfoodonline.dao.LanguageResourceDAO;
import bmbsoft.orderfoodonline.entities.LanguageResource;
import bmbsoft.orderfoodonline.util.Constant; 

@Service
@Transactional
public class LanguageResourceService {
	public static final Logger logger = LoggerFactory.getLogger(LanguageResourceService.class);

	@Autowired
	LanguageResourceDAO LanguageResourceDAO;

	public List<LanguageResource> getAll() {
		return LanguageResourceDAO.getAll();
	}

	public void create(final LanguageResource l) {
		LanguageResourceDAO.Create(l);
	}

	public void update(final LanguageResource l) {
		LanguageResourceDAO.Update(l);
	}

	public LanguageResource getById(final long id) {
		return LanguageResourceDAO.getById(id);
	}

	public boolean delete(LanguageResource c) {
		try {
			c.setStatus(Constant.Status.Deleted.hashCode());
			c.setModifiedDate(new Date());
			LanguageResourceDAO.Update(c);
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return false;
	}

}
