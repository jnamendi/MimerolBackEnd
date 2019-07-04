package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.ContentEntryDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Country;

@Service
public class ContentEntryService {
	public static final Logger logger = LoggerFactory.getLogger(ContentEntryService.class);

	@Autowired
	ContentEntryDAO ContentEntryDAO;

	public List<ContentEntry> getAll() {
		return ContentEntryDAO.getAll();
	}

	public void save(final ContentEntry c) {
		ContentEntryDAO.save(c);
	}

	public ContentEntry getById(final long id) {
		return ContentEntryDAO.getById(id);
	}

	public ContentEntry getByContentDefAndLang(Long condefId, Long langId, String code) {
		return ContentEntryDAO.getByContentDefAndLang(condefId, langId, code);
	}
	
}
