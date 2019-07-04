package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bmbsoft.orderfoodonline.dao.CategoryDAO;
import bmbsoft.orderfoodonline.entities.Category;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.RestaurantCategory;
import bmbsoft.orderfoodonline.model.CategoryViewModel;
import bmbsoft.orderfoodonline.model.shared.CategoryReq;
import bmbsoft.orderfoodonline.model.shared.CategoryResponse;
import bmbsoft.orderfoodonline.model.shared.MenuLiteResponse;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class CategoryService {
	public static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private LanguageService languageService;

	@Transactional
	public List<CategoryViewModel> getAll(final String codeLang, Integer status) {
		List<Category> categorys = categoryDAO.getAll(codeLang, status);
		List<CategoryViewModel> categoryModels = new LinkedList<>();
		if (null != categorys && !categorys.isEmpty()) {
			categorys.forEach(category -> {
				if (null != category)
					categoryModels.add(convertCategoryEntityToModel(category, codeLang));
			});
			return categoryModels;
		}
		return null;
	}

	@Transactional
	@Async
	public List<CategoryViewModel> getAllSortBy(Language l, Integer status) {
		List<Category> categorys = categoryDAO.getAll(l.getCode(), status);
		List<CategoryViewModel> categoryModels = new LinkedList<>();
		if (null != categorys && !categorys.isEmpty()) {
			categorys.forEach(c -> {
				CategoryViewModel cm = new CategoryViewModel();
				HashMap<String, String> miNames = languageService.hashMapTranslate1(c.getContentDefinition(), l);
				String miName = miNames.get("category_name");
				cm.setCategoryId(c.getCategoryId());
				cm.setCategoryName(miName);
				
				categoryModels.add(cm);
			});
			return categoryModels.stream().sorted(Comparator.comparing(CategoryViewModel::getCategoryName)).collect(Collectors.toList());
		}
		return null;
	}

	@Transactional
	public ResponseGetPaging getCategoryByLanguageId(final int pageIndex, final int pageSize, final String title,
			Integer status, String codeLang) throws Exception {
		int totalRecord = categoryDAO.countGetAll(title, status, codeLang);
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Category> Categorys = categoryDAO.getCategoryByLanguage(firstResult, maxResult, title, status, codeLang);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (Categorys == null || Categorys.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found item.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<CategoryViewModel> CategoryModels = new LinkedList<>();
		if (Categorys != null && !Categorys.isEmpty()) {
			Categorys.forEach(res -> {
				CategoryModels.add(convertCategoryEntityToModel(res, codeLang));
			});
		}
		if (totalRecord > 0 && !CategoryModels.isEmpty()) {
			content.setData(CategoryModels);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	@Transactional
	public String save(CategoryReq vm, Long id, MultipartFile file) {
		return categoryDAO.save(vm, id, file);
	}

	@Transactional
	public Category getById(final long id) {
		return categoryDAO.getById(id);
	}

	@Transactional
	public CategoryViewModel getDetail(final long id) {
		Category c = categoryDAO.getById(id);
		return (c == null) ? null : convertCategoryEntityToModel(c, null);
	}

	@Transactional
	public boolean delete(Category c) {
		c.setStatus(Constant.Status.Deleted.getValue());
		c.setModifiedDate(new Date());
		return categoryDAO.update(c);
	}

	public String deleteMany(final String[] ids) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			long id = Long.valueOf(ids[i]);
			Category c = categoryDAO.getById(id);
			if (c != null) {
				c.setStatus(Constant.Status.Deleted.getValue());
				c.setModifiedDate(new Date());

				categoryDAO.update(c);
			} else {
				s.append("Could not found item: " + ids[i]);
			}
		}
		return s.toString();
	}

	private CategoryViewModel convertCategoryEntityToModel(Category category, String codeLang) {
		if (null == category.getContentDefinition())
			return null;
		Language lang = (codeLang == null || codeLang.isEmpty()) ? null : languageService.getLanguageByCode(codeLang);
		CategoryViewModel categoryModel = new CategoryViewModel();
		categoryModel.setCategoryId(category.getCategoryId());
		categoryModel.setCategoryName(category.getTitle());
		categoryModel.setSortOrder(category.getSortOrder());
		categoryModel.setStatus(category.getStatus());
		categoryModel.setLanguageLst(languageService.translate(category.getContentDefinition(), lang));
		categoryModel.setImageUrl(category.getImageUrl());

		return categoryModel;
	}

	@Transactional
	public List<CategoryResponse> getByDistrict(Language l) {
		List<Category> categorys = categoryDAO.getCategoryByLanguage(1, 0, null, Constant.Status.Publish.getValue(),
				l.getCode());
		List<CategoryResponse> lcr = new ArrayList<>();
		if (categorys != null && categorys.size() > 0) {
			categorys.forEach(c -> {
				CategoryResponse cr = new CategoryResponse();
				cr.setCategoryId(c.getCategoryId());
				cr.setSortOrder(c.getSortOrder());
				List<String> a = languageService.hashMapTranslate(c.getContentDefinition(), l);
				cr.setName(a != null && a.size() > 0 ? a.get(0) : "");

				Set<RestaurantCategory> rc = c.getRestaurantCategories();
				if (rc != null && rc.size() > 0) {
					// number of restaurant
					cr.setNumberOfRestaurant(rc.size());
				}

				lcr.add(cr);
			});
		}

		return lcr;
	}
}
