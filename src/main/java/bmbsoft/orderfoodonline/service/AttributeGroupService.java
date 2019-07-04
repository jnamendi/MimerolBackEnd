package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.Session;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.AttributeGroupDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.Attribute;
import bmbsoft.orderfoodonline.entities.AttributeGroup;
import bmbsoft.orderfoodonline.entities.AttributeGroup;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.AttributeGroupViewModel;
import bmbsoft.orderfoodonline.model.AttributeViewModel;
import bmbsoft.orderfoodonline.model.shared.AttributeGroupRequest;
import bmbsoft.orderfoodonline.model.shared.AttributeGroupResponse;
import bmbsoft.orderfoodonline.model.shared.AttributeResponse;

@Service
public class AttributeGroupService {
	public static final Logger logger = LoggerFactory.getLogger(AttributeGroupService.class);

	@Autowired
	AttributeGroupDAO attributeGroup;

	@Autowired
	LanguageService languageService;

	@Transactional
	public List<AttributeGroupViewModel> getAll() {
		List<AttributeGroup> lag = attributeGroup.getAll();

		List<AttributeGroupViewModel> agv = new ArrayList<>();
		lag.forEach(ag -> {
			AttributeGroupViewModel vm = new AttributeGroupViewModel();

			vm.setCode(ag.getCode());
			vm.setAttributGroupId(ag.getAttibuteGroupId());
			vm.setLanguageLst(languageService.translate(ag.getContentDefinition(), null));
			agv.add(vm);

		});

		return agv;
	}

	@Transactional
	public List<AttributeGroupResponse> getAll(String languageCode) {
		List<AttributeGroup> lag = attributeGroup.getAll();

		List<AttributeGroupResponse> agv = new ArrayList<>();
		lag.forEach(ag -> {
			AttributeGroupResponse vm = new AttributeGroupResponse();
			Language lang = (languageCode == null || languageCode.isEmpty()) ? null
					: languageService.getLanguageByCode(languageCode);
			List<String> name = languageService.hashMapTranslate(ag.getContentDefinition(), lang);
			vm.setGroupName(name != null && name.size() > 0 ? name.get(0) : "");
			vm.setAttributeGroupCode(ag.getCode());

			List<AttributeResponse> avm = new ArrayList<>();
			Set<Attribute> a = ag.getAttributes();
			if (a != null && a.size() > 0) {
				a.forEach(at -> {
					AttributeResponse atvm = new AttributeResponse();
					List<String> attName = languageService.hashMapTranslate(at.getContentDefinition(), lang);
					atvm.setAttributeName(attName != null && attName.size() > 0 ? attName.get(0) : "");
					atvm.setAttributeCode(at.getCode());
					atvm.setAttributeId(at.getAtributeId());
					avm.add(atvm);
				});
			}
			vm.setAttributGroupId(ag.getAttibuteGroupId());
			vm.setAttributes(avm);
			agv.add(vm);

		});

		return agv;
	}

	@Transactional
	public void save(AttributeGroupRequest g) {
		attributeGroup.save(g);
	}

	@Transactional
	public void update(AttributeGroupRequest g, AttributeGroup at) {
		attributeGroup.update(g, at);
	}

	public AttributeGroup getBaseById(final long id) {
		return attributeGroup.getById(id);
	}

	@Transactional
	public AttributeGroupViewModel getById(final long id, String languageCode) {
		AttributeGroup ag = attributeGroup.getById(id);
		if (ag == null)
			return null;

		AttributeGroupViewModel vm = new AttributeGroupViewModel();
		Language lang = (languageCode == null || languageCode.isEmpty()) ? null
				: languageService.getLanguageByCode(languageCode);
		vm.setLanguageLst(languageService.translate(ag.getContentDefinition(), lang));

		List<AttributeViewModel> avm = new ArrayList<>();
		Set<Attribute> a = ag.getAttributes();
		if (a != null && a.size() > 0) {
			a.forEach(at -> {
				AttributeViewModel atvm = new AttributeViewModel();
				atvm.setLanguageLst(languageService.translate(at.getContentDefinition(), lang));
				atvm.setAttributeId(at.getAtributeId());
				avm.add(atvm);
			});
		}
		vm.setAttributGroupId(ag.getAttibuteGroupId());
		vm.setAttributeViewModels(avm);
		vm.setStatus(ag.getStatus());
		return vm;
	}

	public void delete(AttributeGroup c) {
		attributeGroup.saveBase(c);
	}

}
