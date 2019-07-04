package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import bmbsoft.orderfoodonline.dao.RestaurantAttributeDAO; 
import bmbsoft.orderfoodonline.entities.RestaurantAttribute;

@Service
public class RestaurantAttributeService {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantAttributeService.class);

	@Autowired
	RestaurantAttributeDAO RestaurantAttributeDAO;

	public List<RestaurantAttribute> getAll() {
		return RestaurantAttributeDAO.getAll();
	}

	public void save(final RestaurantAttribute c) {
		RestaurantAttributeDAO.save(c);
	}

	public RestaurantAttribute getById(final long id) {
		return RestaurantAttributeDAO.getById(id);
	}

	public void delete(final long id) {
		RestaurantAttribute c = RestaurantAttributeDAO.getById(id);
		RestaurantAttributeDAO.delete(c);
	}
	
	public boolean deleteByResId(final long resId) { 
		return RestaurantAttributeDAO.deleteByResId(resId);
	}

}
