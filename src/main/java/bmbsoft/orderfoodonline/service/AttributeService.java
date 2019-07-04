package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.AttributeDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.Attribute;
import bmbsoft.orderfoodonline.entities.AttributeGroup;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Attribute;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.AttributeGroupViewModel;
import bmbsoft.orderfoodonline.model.AttributeViewModel;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.shared.AttributeGroupRequest;

@Service
public class AttributeService {
	public static final Logger logger = LoggerFactory.getLogger(AttributeService.class);

	@Autowired
	AttributeDAO attribute;

	@Autowired
	LanguageService languageService;

	@Transactional
	public List<AttributeViewModel> getAll(String languageCode) {
		List<Attribute> att = attribute.getAll();

		List<AttributeViewModel> agv = new ArrayList<>();
		att.forEach(ag -> {
			AttributeViewModel avm = new AttributeViewModel();
			Language lang = (languageCode == null || languageCode.isEmpty()) ? null
					: languageService.getLanguageByCode(languageCode);

			avm.setLanguageLst(languageService.translate(ag.getContentDefinition(), lang));
			avm.setStatus(ag.getIsStatus());
			agv.add(avm);

		});

		return agv;
	}

	public void save(final AttributeViewModel c,AttributeGroup ag) {
		attribute.save(c,ag);
	}

	@Transactional
	public AttributeViewModel getById(final long id, String languageCode) {
		Attribute at = attribute.getById(id);
		if (at == null)
			return null;
		
		AttributeViewModel avm = new AttributeViewModel();
		Language lang = (languageCode == null || languageCode.isEmpty()) ? null
				: languageService.getLanguageByCode(languageCode);

		avm.setLanguageLst(languageService.translate(at.getContentDefinition(), lang));
		avm.setStatus(at.getIsStatus());
		return avm;
	}

	public Attribute getBaseById(final long id) {
		return attribute.getById(id);
	}

	public void delete(Attribute c) {
		attribute.saveBase(c);
	}

	public void update(AttributeViewModel vm, Attribute at) {
		attribute.update(vm,at);
	}

}
