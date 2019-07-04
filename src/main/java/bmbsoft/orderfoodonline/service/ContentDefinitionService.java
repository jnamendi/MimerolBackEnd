package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.ContentDefinitionDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.Country;

@Service
public class ContentDefinitionService {
	public static final Logger logger = LoggerFactory.getLogger(ContentDefinitionService.class);

	@Autowired
	ContentDefinitionDAO contentDefinitionDAO;

	public List<ContentDefinition> getAll() {
		return contentDefinitionDAO.getAll();
	}

	public Long save(final ContentDefinition c) {
		return contentDefinitionDAO.save(c);
	}

	public ContentDefinition getById(final long id) {
		return contentDefinitionDAO.getById(id);
	}

}
