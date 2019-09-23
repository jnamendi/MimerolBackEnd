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
import bmbsoft.orderfoodonline.dao.DistrictDAO;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.model.DistrictViewModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class DistrictService {
	public static final Logger logger = LoggerFactory.getLogger(DistrictService.class);

	@Autowired
	private DistrictDAO DistrictDAO;
	@Autowired
	private CityService cityService;
	@Autowired
	private CityDAO cityDao;

	@Transactional
	public List<DistrictViewModel> getAll() {
		List<DistrictViewModel> listModel = new LinkedList<>();
		List<District> Districts = DistrictDAO.getAll();
		if (null == Districts) {
			return null;
		}
		Districts.forEach(District -> {
			listModel.add(convertEntityToModel(District));
		});
		return listModel;
	}
	
	@Transactional
	public List<DistrictViewModel> getAllByCityId(long cityId) {
		List<DistrictViewModel> listModel = new LinkedList<>();
		List<District> Districts = DistrictDAO.getDistrictByCityId(cityId);
		if (null == Districts) {
			return null;
		}
		Districts.forEach(District -> {
			listModel.add(convertEntityToModel(District));
		});
		return listModel;
	}

	public District getDistrictByName(String districtCode, String cityCode) {
		District dis = DistrictDAO.getDistrictByName(districtCode, cityCode);
		return dis;
	}

	@Transactional
	public Boolean create(final DistrictViewModel DistrictModel) {
		District District = convertModelToEntity(DistrictModel);
		return DistrictDAO.Create(District);
	}

	@Transactional
	public boolean update(final DistrictViewModel DistrictModel) {
		District District = convertModelToEntity(DistrictModel);
		return DistrictDAO.Update(District);
	}

	@Transactional
	public DistrictViewModel getById(final long id) {
		return convertEntityToModel(DistrictDAO.getById(id));
	}
	
	@Transactional
	public District getBaseById(final long id) {
		return DistrictDAO.getById(id);
	}
	
	@Transactional
	public Boolean delete(District c) { 
		c.setStatus(Constant.Status.Deleted.getValue());
		c.setModifiedDate(new Date());
		
		return DistrictDAO.Update(c);
	}

	// miss Function get District by code -> check exists?

	private District convertModelToEntity(final DistrictViewModel districtModel) {
		District district = new District();
		if (null == districtModel.getDistrictId()) {
			// new
			district.setName(districtModel.getName());
			district.setCode(districtModel.getCode());
			district.setStatus(districtModel.getStatus());
			// District.setCreatedBy(createdBy); miss
			district.setCreatedDate(new Date());
			if (null != districtModel.getCity().getCityId())
				district.setCity(cityDao.getById(districtModel.getCity().getCityId()));
		} else {
			// update
			district = DistrictDAO.getById(districtModel.getDistrictId());
			district.setName(districtModel.getName());
			district.setCode(districtModel.getCode());
			district.setStatus(districtModel.getStatus());
			district.setModifiedDate(new Date());
			// District.setModifiedBy(modifiedBy); miss
			if (null != districtModel.getCity().getCityId())
				district.setCity(cityDao.getById(districtModel.getCity().getCityId()));
		}
		return district;
	}

	public DistrictViewModel convertEntityToModel(final District district) {
		if (null == district)
			return null;
		DistrictViewModel model = new DistrictViewModel();
		model.setDistrictId(district.getDistrictId());
		model.setCity(cityService.convertEntityToModel(district.getCity()));
		model.setName(district.getName());
		model.setCode(district.getCode());
		model.setCreatedBy(district.getCreatedBy());
		model.setStatus(district.getStatus());
		return model;
	}

}
