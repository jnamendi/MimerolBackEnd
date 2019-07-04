package bmbsoft.orderfoodonline.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.ResidenceTypeDAO;
import bmbsoft.orderfoodonline.entities.ResidenceType;
import bmbsoft.orderfoodonline.model.ResidenceTypeModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class ResidenceTypeService {

	public static final Logger logger = LoggerFactory.getLogger(ResidenceTypeService.class);

	@Autowired
	private ResidenceTypeDAO ResidenceTypeDAO;

	@Transactional
	public List<ResidenceTypeModel> getAll() {
		List<ResidenceTypeModel> listModel = new LinkedList<>();
		List<ResidenceType> ResidenceTypes = ResidenceTypeDAO.getAll();
		if (null == ResidenceTypes) {
			return null;
		}
		ResidenceTypes.forEach(ResidenceType -> {
			listModel.add(convertEntityToModel(ResidenceType));
		});
		return listModel;
	}

	@Transactional
	public Boolean create(final ResidenceTypeModel ResidenceTypeModel) {
		ResidenceType ResidenceType = convertModelToEntity(ResidenceTypeModel);
		return ResidenceTypeDAO.Create(ResidenceType);
	}

	@Transactional
	public boolean update(final ResidenceTypeModel ResidenceTypeModel) {
		ResidenceType ResidenceType = convertModelToEntity(ResidenceTypeModel);
		return ResidenceTypeDAO.Update(ResidenceType);
	}

	@Transactional
	public ResidenceTypeModel getById(final long id) {
		return convertEntityToModel(ResidenceTypeDAO.getById(id));
	}

	@Transactional
	public Boolean delete(final long id) {
		ResidenceType c = ResidenceTypeDAO.getById(id);
		if (c == null) {
			return false;
		}
		c.setStatus(Constant.Status.Deleted.getValue());
		return ResidenceTypeDAO.Update(c);
	}

	// miss Function get ResidenceType by code -> check exists?

	public ResidenceTypeModel convertEntityToModel(final ResidenceType residenceType) {
		if (null == residenceType)
			return null;
		ResidenceTypeModel model = new ResidenceTypeModel();
		model.setResidenceTypeId(residenceType.getResidenceTypeId());
		model.setName(residenceType.getName());
		// model.setCreatedBy(residenceType.getCreatedBy());
		// model.setStatus(residenceType.getStatus());
		// model.setCreateDate(residenceType.getCreatedDate());
		return model;
	}

	private ResidenceType convertModelToEntity(final ResidenceTypeModel model) {
		ResidenceType residenceType = new ResidenceType();
		if (null == model.getResidenceTypeId()) {
			// new
			residenceType.setName(model.getName());
			residenceType.setStatus(Constant.Status.Publish.getValue());

		} else {
			// update
			residenceType = ResidenceTypeDAO.getById(model.getResidenceTypeId());
			residenceType.setName(model.getName());
		}
		return residenceType;
	}
}
