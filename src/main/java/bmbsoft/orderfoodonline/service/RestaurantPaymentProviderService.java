package bmbsoft.orderfoodonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.RestaurantPaymentProviderDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.RestaurantPaymentProvider; 
import bmbsoft.orderfoodonline.entities.Country;

@Service
public class RestaurantPaymentProviderService {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantPaymentProviderService.class);

	@Autowired
	RestaurantPaymentProviderDAO rpp;

	public List<RestaurantPaymentProvider> getAll() {
		return rpp.getAll();
	}

	public void save(final RestaurantPaymentProvider c) {
		rpp.save(c);
	}

	public RestaurantPaymentProvider getById(final long id) {
		return rpp.getById(id);
	}

	public void delete(RestaurantPaymentProvider c) {
		rpp.save(c);
	}

	public boolean deleteByResId(Long resId) {
		return rpp.deleteByResId(resId);
	}
}
