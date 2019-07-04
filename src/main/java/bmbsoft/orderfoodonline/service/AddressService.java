package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.AddressDAO;
import bmbsoft.orderfoodonline.dao.DistrictDAO;
import bmbsoft.orderfoodonline.dao.ResidenceTypeDAO;
import bmbsoft.orderfoodonline.dao.UserDAO;
import bmbsoft.orderfoodonline.entities.Address;
import bmbsoft.orderfoodonline.entities.City;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class AddressService {

	@Autowired
	private AddressDAO addressDAO;
	@Autowired
	private DistrictDAO districtDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ResidenceTypeDAO residenceTypeDAO;

	@Transactional
	public ResponseGetPaging getAll(final int pageIndex, final int pageSize, final String title, Integer status) {
		int totalRecord = addressDAO.getAll(0, 0, title, status).size();
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Address> Addresss = addressDAO.getAll(firstResult, maxResult, title, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (Addresss == null || Addresss.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}
		{
			List<AddressViewModel> listModel = new LinkedList<>();
			Addresss.forEach(Address -> {
				listModel.add(convertEntityToModel(Address));
			});

			content.setData(listModel);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");

			return rs;
		}
	}

	@Transactional
	public List<AddressViewModel> getByUserId(Long userId) {
		List<AddressViewModel> listModel = new LinkedList<>();
		List<Address> Addresss = addressDAO.getByUser(userId);
		if (null == Addresss) {
			return null;
		}
		Addresss.forEach(Address -> {
			listModel.add(convertEntityToModel(Address));
		});
		return listModel;
	}

	@Transactional
	public Boolean create(final Address address) {
		return addressDAO.create(address);
	}

	@Transactional
	public boolean update(final Address address) {
		return addressDAO.update(address);
	}

	@Transactional
	public AddressViewModel getById(final long id) {
		return convertEntityToModel(addressDAO.getById(id));
	}

	@Transactional
	public Address getBaseById(final long id) {
		return addressDAO.getById(id);
	}

	// @Transactional
	// public Boolean delete(final long id) {
	// Address c = addressDAO.getById(id);
	// if (c == null) {
	// return false;
	// }
	// c.setStatus(Constant.Status.Deleted.getValue());
	// return addressDAO.Update(c);
	// }

	/**
	 * use function: before you need check exists of user, district and
	 * residenceType
	 * 
	 * @param addressRq
	 * @return
	 */

	private AddressViewModel convertEntityToModel(final Address address) {
		if (null == address)
			return null;
		AddressViewModel model = new AddressViewModel();
		model.setAddressId(address.getAddressId());
		User u = address.getUser();
		District d = address.getDistrict();
		if (d != null) {
			City c = d.getCity();
			Country ct = c.getCountry();
			// if final version remove, because not check database.
			if (address.getDistrict() != null) {
				model.setCountry(ct.getName());
				model.setCity(c.getCityName());
				model.setDistrict(d.getName());
				model.setCityId(c.getCityId());
				model.setDistrictId(d.getDistrictId());
				model.setCountryId(ct.getCountryId());
			}
		}

		model.setUserId(u.getUserId());
		model.setUserName(u.getFullName());
		model.setPhoneNumber(address.getPhoneNumber());

		model.setWard(address.getWard());
		model.setAddress(address.getAddress());
		model.setEmail(address.getEmailAdd());
		model.setCreatedBy(address.getCreatedBy());
		model.setCreatedDate(address.getCreatedDate());
		model.setAddressDesc(address.getAddressDesc());
		
		return model;
	}

	public boolean delete(Address address) {
		address.setIsStatus(Constant.Status.Deleted.getValue());
		address.setModifiedDate(new Date());

		return addressDAO.update(address);
	}
}
