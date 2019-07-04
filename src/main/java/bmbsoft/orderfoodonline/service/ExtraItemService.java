package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.ExtraItemDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.ExtraItem;
import bmbsoft.orderfoodonline.entities.Country;

@Service
public class ExtraItemService {
	public static final Logger logger = LoggerFactory.getLogger(ExtraItemService.class);
 
	@Autowired
	ExtraItemDAO ExtraItemDAO;
	
	
	public List<ExtraItem> getAll() {
		return ExtraItemDAO.getAll();
	}

	public void create(final ExtraItem c) {
		ExtraItemDAO.Create(c);
	}

	public void update(final ExtraItem c) {
		ExtraItemDAO.Update(c);
	}

	public ExtraItem getById(final long id) {
		return ExtraItemDAO.getById(id);
	}


}
