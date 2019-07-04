package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 

import bmbsoft.orderfoodonline.dao.LanguageDAO;
import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;

@Service
public class LanguageService {
	public static final Logger logger = LoggerFactory.getLogger(LanguageService.class);

	@Autowired
	LanguageDAO languageDAO;

	public List<LanguageViewModel> getAll() {
		return languageDAO.getAll();
	}

	public boolean save(final Language l) {
		return languageDAO.save(l);
	}

	public Language getById(final long id) {
		return languageDAO.getById(id);
	}

	public boolean delete(final Language l) {
		return languageDAO.delete(l);
	}

	public Boolean checkExistLang(String code) {
		return languageDAO.checkExistLang(code);
	}

	public Language getLanguageByCode(String codeLang) {
		return languageDAO.getLanguageByCode(codeLang);
	}

	/**
	 * 
	 * @param contentDef
	 * @param lang
	 *            if null getAll translate, else get translate by language id.
	 * @return List<LanguageViewModel>
	 */
	public List<LanguageViewModel> translate(ContentDefinition contentDef, Language lang) {
		if (contentDef == null)
			return null;

		List<LanguageViewModel> listLangModel = new LinkedList<>();
		Set<ContentEntry> setContentEntry = contentDef.getContentEntries();
		Map<Language, List<ContentEntry>> mapCategoryEntry = setContentEntry.stream()
				.collect(Collectors.groupingBy(ContentEntry::getLanguage));
		if (lang != null) {
			LanguageViewModel langModel = new LanguageViewModel();
			langModel.setLanguageId(lang.getLanguageId());
			langModel.setCode(lang.getCode());
			langModel.setName(lang.getName());
			List<ContentDefModel> listContentDefModel = new LinkedList<>();

			if (setContentEntry != null && setContentEntry.size() > 0) {
				Predicate<ContentEntry> pre = p -> !p.getLanguage().getCode().equals(lang.getCode());
				setContentEntry.removeIf(pre);
				setContentEntry.forEach(c -> {
					ContentDefModel contentDefModel = new ContentDefModel();
					contentDefModel.setCode(c.getCode());
					contentDefModel.setValue(c.getValue());

					listContentDefModel.add(contentDefModel);
				});

			}
			langModel.setContentDef(listContentDefModel);
			listLangModel.add(langModel);
			return listLangModel;
		}

		mapCategoryEntry.entrySet().forEach(ca -> {
			LanguageViewModel langModel = new LanguageViewModel();
			Language langitem = ca.getKey();
			langModel.setLanguageId(langitem.getLanguageId());
			langModel.setCode(langitem.getCode());
			langModel.setName(langitem.getName());
			List<ContentDefModel> listContentDefModel = new LinkedList<>();
			mapCategoryEntry.get(langitem).forEach(en -> {
				ContentDefModel contentDefModel = new ContentDefModel();
				contentDefModel.setCode(en.getCode());
				contentDefModel.setValue(en.getValue());
				listContentDefModel.add(contentDefModel);
			});
			langModel.setContentDef(listContentDefModel);
			listLangModel.add(langModel);
		});
		return listLangModel;
	}

	public List<String> hashMapTranslate(ContentDefinition contentDef, Language lang) {
		 
		List<String> hm = new ArrayList<>(); 
		Set<ContentEntry> setContentEntry = contentDef.getContentEntries(); 
		if (lang != null) {
			 
			if (setContentEntry != null && setContentEntry.size() > 0) {
				Predicate<ContentEntry> pre = p -> !p.getLanguage().getCode().equals(lang.getCode());
				setContentEntry.removeIf(pre);
				setContentEntry.forEach(c -> {
					ContentDefModel contentDefModel = new ContentDefModel();
					contentDefModel.setCode(c.getCode());
					contentDefModel.setValue(c.getValue());

					hm.add(c.getValue());
				});

			}
			 
			return hm;
		}
 
		return hm;
	}
	public HashMap<String,String> hashMapTranslate1(ContentDefinition contentDef, Language lang) {
		 
		HashMap<String,String> hm = new HashMap<>(); 
		Set<ContentEntry> setContentEntry = contentDef.getContentEntries(); 
		if (lang != null) {
			 
			if (setContentEntry != null && setContentEntry.size() > 0) {
				Predicate<ContentEntry> pre = p -> !p.getLanguage().getCode().equals(lang.getCode());
				setContentEntry.removeIf(pre);
				setContentEntry.forEach(c -> {
					ContentDefModel contentDefModel = new ContentDefModel();
					contentDefModel.setCode(c.getCode());
					contentDefModel.setValue(c.getValue());

					hm.put(c.getCode(), c.getValue());
				});

			}
			 
			return hm;
		}
 
		return hm;
	}
}
