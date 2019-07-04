package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.model.CountryViewModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class CountryService {
	public static final Logger logger = LoggerFactory.getLogger(CountryService.class);

	@Autowired
	private CountryDAO countryDAO;

	@Transactional
	public List<CountryViewModel> getAll() {
		List<CountryViewModel> listModel = new LinkedList<>();
		List<Country> countrys = countryDAO.getAll();
		if (null == countrys) {
			return null;
		}
		countrys.forEach(country -> {
			listModel.add(convertEntityToModel(country));
		});
		return listModel;
	}

	@Transactional
	public Boolean create(final CountryViewModel countryModel) {
		Country country = convertModelToEntity(countryModel);
		return countryDAO.Create(country);
	}

	@Transactional
	public boolean update(final CountryViewModel countryModel) {
		Country country = convertModelToEntity(countryModel);
		return countryDAO.Update(country);
	}

	@Transactional
	public CountryViewModel getById(final long id) {
		return convertEntityToModel(countryDAO.getById(id));
	}

	@Transactional
	public Country getBaseById(final long id) {
		return countryDAO.getById(id);
	}

	@Transactional
	public Boolean delete(Country c) {
		c.setStatus(Constant.Status.Deleted.getValue());
		c.setModifiedDate(new Date());
		
		return countryDAO.Update(c);
	}

	// miss Function get country by code -> check exists?

	public CountryViewModel convertEntityToModel(final Country country) {
		if (null == country)
			return null;
		CountryViewModel model = new CountryViewModel();
		model.setCountryId(country.getCountryId());
		model.setName(country.getName());
		model.setCode(country.getCode());
		model.setCreatedBy(country.getCreatedBy());
		model.setStatus(country.getStatus());
		model.setCreateDate(country.getCreatedDate());
		return model;
	}

	private Country convertModelToEntity(final CountryViewModel model) {
		Country country = new Country();
		if (null == model.getCountryId()) {
			// new
			country.setName(model.getName());
			country.setCode(model.getCode());
			country.setStatus(model.getStatus());
			// city.setCreatedBy(createdBy); miss
			country.setCreatedDate(new Date());

		} else {
			// update
			country = countryDAO.getById(model.getCountryId());
			country.setName(model.getName());
			country.setCode(model.getCode());
			country.setStatus(model.getStatus());
			// city.setModifiedBy(modifiedBy); miss
			country.setModifiedDate(new Date());
		}
		return country;
	}
}
