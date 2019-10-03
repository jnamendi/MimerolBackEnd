package bmbsoft.orderfoodonline.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bmbsoft.orderfoodonline.dao.MenuItemDAO;
import bmbsoft.orderfoodonline.entities.ExtraItem;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.MenuExtraItem;
import bmbsoft.orderfoodonline.entities.MenuItem;
import bmbsoft.orderfoodonline.model.ExtraItemResponse;
import bmbsoft.orderfoodonline.model.MenuExtraItemResponse;
import bmbsoft.orderfoodonline.model.MenuItemRequest;
import bmbsoft.orderfoodonline.model.MenuItemResponse;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class MenuItemService {
	public static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);

	@Autowired
	private MenuItemDAO menuItemDAO;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private MenuItemTimeAvailableService menuItemTimeAvailableService;

	public String create(MenuItemRequest vm, MenuItem e, Menu m, MultipartFile file) {
		return menuItemDAO.save(vm, e, m, file);
	}

	public String update(MenuItemRequest vm, MenuItem e, Menu m, MultipartFile file) {
		return menuItemDAO.update(vm, e, m, file);
	}

	@Transactional
	@Async
	public MenuItem getBaseById(final long id) {
		return menuItemDAO.getById(id);
	}

	@Transactional
	@Async
	public MenuItemResponse getDetail(final long id) {
		MenuItem m = menuItemDAO.getById(id);
		return (m == null) ? null : this.entityToModel(m, null);
	}

	@Transactional
	@Async
	public void delete(MenuItem item) {
		menuItemDAO.delete(item);
	}

	@Transactional
	@Async
	public ResponseGetPaging getAll(int pageIndex, int pageSize, String name, Integer status, String codeLanguage,
			Long menuId) throws Exception {

		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<MenuItem> Categories = menuItemDAO.getAll(firstResult, maxResult, menuId, name, codeLanguage, status);
		int totalRecord = menuItemDAO.countGetAll(menuId, name, codeLanguage, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();

		if (Categories == null || Categories.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<MenuItemResponse> lmr = new LinkedList<>();
		if (Categories != null && !Categories.isEmpty()) {
			Categories.forEach(res -> {
				lmr.add(entityToModel(res, codeLanguage));
			});
		}
		if (totalRecord > 0 && !lmr.isEmpty()) {
			content.setData(lmr);
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
	@Async
	public ResponseGetPaging getAllByOwner(int pageIndex, int pageSize, Integer status, String codeLanguage,
			Long userId) throws Exception {

		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<MenuItem> Categorys = menuItemDAO.getAllByOwner(firstResult, maxResult, codeLanguage, status,
				userId);
		int totalRecord = menuItemDAO.getAllByOwner(0, 0, codeLanguage, status, userId).size();

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();

		if (Categorys == null || Categorys.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<MenuItemResponse> lmr = new LinkedList<>();
		if (Categorys != null && !Categorys.isEmpty()) {
			Categorys.forEach(res -> {
				lmr.add(entityToModel(res, codeLanguage));
			});
		}
		if (totalRecord > 0 && !lmr.isEmpty()) {
			content.setData(lmr);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	private MenuItemResponse entityToModel(MenuItem e, String codeLang) {
		Language lang = (codeLang == null || codeLang.isEmpty()) ? null : languageService.getLanguageByCode(codeLang);
		MenuItemResponse vm = new MenuItemResponse();
		vm.setMenuItemId(e.getMenuItemId());
		vm.setPrice(e.getPrice());
		vm.setCombo(e.getIsCombo());
		vm.setSortOrder(e.getSortOrder());
		vm.setStatus(e.getIsStatus());
		vm.setImageUrl(e.getPicturePath());
		vm.setAvailableMonday(e.getAvailableMonday());
		vm.setAvailableTuesday(e.getAvailableTuesday());
		vm.setAvailableWednesday(e.getAvailableWednesday());
		vm.setAvailableThursday(e.getAvailableThursday());
		vm.setAvailableFriday(e.getAvailableFriday());
		vm.setAvailableSaturday(e.getAvailableSaturday());
		vm.setAvailableSunday(e.getAvailableSunday());
		vm.setOutOfStock(e.getOutOfStock());
		vm.setPriority(e.getPriority());
		vm.setLanguageLst(languageService.translate(e.getContentDefinition(), lang));
		vm.setListMenuTimeAvailableModel(menuItemTimeAvailableService.getByMenutId(e.getMenuItemId()));
		if (e.getMenu() != null) {
			vm.setMenuId(e.getMenu().getMenuId());
			if (e.getMenu().getRestaurant() != null) {
				vm.setRestaurantId(e.getMenu().getRestaurant().getRestaurantId());
				vm.setRestaurantName(e.getMenu().getRestaurant().getName());
			}
			List<String> names = languageService.hashMapTranslate(e.getMenu().getContentDefinition(), lang);

			String name = names != null && names.size() > 0 ? names.get(0) : "";
			vm.setMenuName(name);
		}

		List<MenuExtraItemResponse> meirs = new LinkedList<>();
		Set<MenuExtraItem> meis = e.getMenuExtraItems();
		if (meis != null && meis.size() > 0) {

			meis.forEach(item -> {
				MenuExtraItemResponse meir = new MenuExtraItemResponse();
				meir.setMenuExtraItemId(item.getMenuExtraItemId());
				meir.setExtraItemType(item.getType());
				if (item.getContentDefinition() != null)
					meir.setLanguageLst(languageService.translate(item.getContentDefinition(), lang));

				List<ExtraItemResponse> eirs = new LinkedList<>();
				Set<ExtraItem> exis = item.getExtraItems();
				if (exis != null && exis.size() > 0) {
					exis.forEach(ex -> {
						ExtraItemResponse exr = new ExtraItemResponse();
						exr.setExtraItemId(ex.getExtraItemId());
						exr.setPrice(ex.getPrice());
						if (ex.getContentDefinition() != null)
							exr.setExtraItem(languageService.translate(ex.getContentDefinition(), lang));

						eirs.add(exr);
					});
				}
				meir.setExtraItemLst(eirs);

				meirs.add(meir);
			});
		}

		vm.setMenuExtraLst(meirs);
		return vm;
	}
}
