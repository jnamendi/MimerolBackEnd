package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.CityDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.City;
import bmbsoft.orderfoodonline.model.CityViewModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class CityService {
	public static final Logger logger = LoggerFactory.getLogger(CityService.class);

	@Autowired
	private CityDAO cityDAO;
	@Autowired
	private CountryDAO countryDAO;
	@Autowired
	private CountryService countryService;

	@Transactional
	public List<CityViewModel> getAll() {
		List<CityViewModel> listModel = new LinkedList<>();
		List<City> Citys = cityDAO.getAll();
		if (null == Citys) {
			return null;
		}
		Citys.forEach(City -> {
			listModel.add(convertEntityToModel(City));
		});
		return listModel;
	}

	@Transactional
	public Boolean create(final CityViewModel CityModel) {
		City City = convertModelToEntity(CityModel);
		return cityDAO.Create(City);
	}

	@Transactional
	public boolean update(final CityViewModel CityModel) {
		City City = convertModelToEntity(CityModel);
		return cityDAO.Update(City);
	}

	@Transactional
	public CityViewModel getById(final long id) {
		return convertEntityToModel(cityDAO.getById(id));
	}

	@Transactional
	public City getBaseById(final long id) {
		return cityDAO.getById(id);
	}

	public boolean delete(City c) {
		c.setStatus(Constant.Status.Deleted.getValue());
		c.setModifiedDate(new Date());

		return cityDAO.Update(c);
	}

	// miss Function get City by code -> check exists?

	private City convertModelToEntity(final CityViewModel cityModel) {
		City city = new City();
		if (null == cityModel.getCityId()) {
			// new
			city.setCityName(cityModel.getName());
			city.setCityCode(cityModel.getCode());
			city.setStatus(cityModel.getStatus());
			// city.setCreatedBy(createdBy); miss
			city.setCreatedDate(new Date());
			// check countryId -> true get country by id
			if (null != cityModel.getCountry().getCountryId())
				city.setCountry(countryDAO.getById(cityModel.getCountry().getCountryId()));
		} else {
			// update
			city = cityDAO.getById(cityModel.getCityId());
			city.setCityName(cityModel.getName());
			city.setCityCode(cityModel.getCode());
			city.setStatus(cityModel.getStatus());
			// city.setModifiedBy(modifiedBy); miss
			if (null != cityModel.getCountry().getCountryId())
				city.setCountry(countryDAO.getById(cityModel.getCountry().getCountryId()));
			city.setModifiedDate(new Date());
		}
		return city;
	}

	public CityViewModel convertEntityToModel(final City city) {
		if (null == city)
			return null;
		CityViewModel model = new CityViewModel();
		model.setCityId(city.getCityId());
		model.setCountry(countryService.convertEntityToModel(city.getCountry()));
		model.setName(city.getCityName());
		model.setCode(city.getCityCode());
		model.setCreatedBy(city.getCreatedBy());
		model.setCreateDate(city.getCreatedDate());
		model.setStatus(city.getStatus());
		return model;
	}
}
