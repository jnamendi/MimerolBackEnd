package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.MenuDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.dao.MediaDAO;
import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.Media;

@Service
public class MediaService {
	public static final Logger logger = LoggerFactory.getLogger(MediaService.class);

	@Autowired
	MediaDAO mediaDAO;

	public List<Media> getAll() {
		return mediaDAO.getAll();
	}
	@Async
	public void create(final Media c) {
		mediaDAO.Create(c);
	}

	public void update(final Media c) {
		mediaDAO.Update(c);
	}

	public Media getById(final long id) {
		return mediaDAO.getById(id);
	}

}
