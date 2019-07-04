package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.FoodTypeDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.FoodType;
import bmbsoft.orderfoodonline.entities.Country;

@Service
public class FoodTypeService {
	public static final Logger logger = LoggerFactory.getLogger(FoodTypeService.class);
 
	@Autowired
	FoodTypeDAO FoodTypeDAO;
	
	
	public List<FoodType> getAll() {
		return FoodTypeDAO.getAll();
	}

	public void create(final FoodType c) {
		FoodTypeDAO.Create(c);
	}

	public void update(final FoodType c) {
		FoodTypeDAO.Update(c);
	}

	public FoodType getById(final long id) {
		return FoodTypeDAO.getById(id);
	}


}
