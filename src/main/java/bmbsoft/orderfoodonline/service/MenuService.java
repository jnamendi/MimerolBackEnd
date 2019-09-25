package bmbsoft.orderfoodonline.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bmbsoft.orderfoodonline.dao.CurrencyDAO;
import bmbsoft.orderfoodonline.dao.MenuDAO;
import bmbsoft.orderfoodonline.entities.ExtraItem;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Menu;
import bmbsoft.orderfoodonline.entities.MenuExtraItem;
import bmbsoft.orderfoodonline.entities.MenuItem;
import bmbsoft.orderfoodonline.model.MenuViewModel;
import bmbsoft.orderfoodonline.model.shared.CurrencyResponse;
import bmbsoft.orderfoodonline.model.shared.ExtraItemLiteResponse;
import bmbsoft.orderfoodonline.model.shared.MenuExtraItemLiteResponse;
import bmbsoft.orderfoodonline.model.shared.MenuItemLiteResponse;
import bmbsoft.orderfoodonline.model.shared.MenuLiteResponse;
import bmbsoft.orderfoodonline.model.shared.MenuRequest;
import bmbsoft.orderfoodonline.model.shared.MenuResponse;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class MenuService {
	public static final Logger logger = LoggerFactory.getLogger(MenuService.class);

	@Autowired
	private MenuDAO menuDAO;
	@Autowired
	private LanguageService languageService;
	@Autowired
	CurrencyDAO currencyDAO;

	@Transactional
	@Async
	public ResponseGetPaging getAll(final int pageIndex, final int pageSize, final String name, Integer status,
			String codeLang, Long restaurantId) throws Exception {
		int totalRecord = menuDAO.countGetAll(name, status, codeLang, restaurantId);
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Menu> Menus = menuDAO.getAll(firstResult, maxResult, name, status, codeLang, restaurantId, false);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (Menus == null || Menus.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("File not found.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<MenuViewModel> menuModels = new LinkedList<>();
		if (Menus != null && !Menus.isEmpty()) {
			Menus.forEach(res -> {
				menuModels.add(entityToModel(res, codeLang));
			});
		}

		if (totalRecord > 0 && !menuModels.isEmpty()) {
			content.setData(menuModels);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	@Async
	public String create(final MenuRequest menuModel, Menu m, MultipartFile file) {
		return menuDAO.create(menuModel, m, file);
	}

	@Transactional
	@Async
	public MenuViewModel viewDetail(final Long id) {
		Menu menu = menuDAO.getById(id);
		return (null == menu) ? null : entityToModel(menu, null);
	}

	@Async
	public String update(final MenuRequest menuModel, Menu m, MultipartFile file) {
		return menuDAO.updateMenu(menuModel, m, file);
	}

	@Async
	public boolean delete(Menu m) {
		return menuDAO.delete(m);
	}

	@Async
	public Menu getById(final long id) {
		return menuDAO.getById(id);
	}

	@Transactional
	@Async
	public ResponseGetPaging getMenuByOwner(final int pageIndex, final int pageSize, long idOwner, String codeLang) {
		int totalRecord = menuDAO.getMenuByOwner(0, 0, idOwner).size();
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Menu> menus = menuDAO.getMenuByOwner(firstResult, maxResult, idOwner);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (menus == null || menus.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<MenuViewModel> menuModels = new LinkedList<>();
		if (menus != null && !menus.isEmpty()) {
			menus.forEach(m -> {
				menuModels.add(this.entityToModel(m, codeLang));
			});
		}

		if (totalRecord > 0 && !menuModels.isEmpty()) {
			content.setData(menuModels);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	/**
	 * 
	 * @param m
	 * @param codeLang:
	 *            null -> get all translate
	 * @return
	 */
	private MenuViewModel entityToModel(Menu m, String codeLang) {
		if (null == m.getContentDefinition())
			return null;
		Language lang = (codeLang == null || codeLang.isEmpty()) ? null : languageService.getLanguageByCode(codeLang);
		MenuViewModel vm = new MenuViewModel();
		vm.setMenuId(m.getMenuId());
		vm.setName(m.getName());
		vm.setUrl_slug(m.getUrlSlug());
		vm.setStatus(m.getStatus());
		vm.setRestaurantId(m.getRestaurant().getRestaurantId());
		vm.setRestaurantName(m.getRestaurant().getName());
		vm.setLanguageLst(languageService.translate(m.getContentDefinition(), lang));
		return vm;
	}

	@Transactional
	@Async
	public List<MenuLiteResponse> getAllByRestaurant(Long restaurantId, Language l) throws Exception {
		List<Menu> res = menuDAO.getAll(l.getCode(), restaurantId);

		List<MenuLiteResponse> lm = new ArrayList<>();
		if (res != null && res.size() > 0) {
			res.forEach(m -> {
				MenuLiteResponse mlr = new MenuLiteResponse();
				mlr.setMenuId(m.getMenuId());
				HashMap<String, String> miNames = languageService.hashMapTranslate1(m.getContentDefinition(), l);
				String miName = miNames.get("menu_name");

				mlr.setMenuName(miName);
				// menu
				lm.add(mlr);
			});
			return lm.stream().sorted(Comparator.comparing(MenuLiteResponse::getMenuName)).collect(Collectors.toList());
		}
		return lm;

	}

	@Transactional
	@Async
	public MenuResponse getByRestaurant(Long restaurantId, Language l) throws Exception {
		MenuResponse mr = new MenuResponse();

		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur == null) {
			cur.setRate(1d);
		}

		List<Menu> res = menuDAO.getAll(1, 0, null, Constant.Status.Publish.getValue(), l.getCode(), restaurantId,
				true);

		List<MenuLiteResponse> lm = new ArrayList<>();
		List<MenuItemLiteResponse> lmlr = new ArrayList<>();

		if (res != null && res.size() > 0) {
			res.forEach(m -> {				
				// menu item
				Set<MenuItem> smi = m.getMenuItems();
				if (smi != null && smi.size() > 0) {
					smi = smi.stream().filter(p -> p.getIsStatus() == Constant.Status.Publish.getValue())
							.collect(Collectors.toSet());
					if(smi.size() > 0) {
						MenuLiteResponse mlr = new MenuLiteResponse();
						mlr.setMenuId(m.getMenuId());
						mlr.setSortOrder(m.getSortOrder());
						List<String> names = languageService.hashMapTranslate(m.getContentDefinition(), l);

						String name = names != null && names.size() > 0 ? names.get(0) : "";
						mlr.setMenuName(name);
						mlr.setCurrencyCode(cur.getCode());
						mlr.setUrlSlug(CommonHelper.toPrettyURL(name));
						
						// menu
						lm.add(mlr); 
						mr.setMennu(lm);
						
						smi.forEach(mi -> {
							MenuItemLiteResponse milr = new MenuItemLiteResponse();
							milr.setMenuItemId(mi.getMenuItemId());
							milr.setMenuId(m.getMenuId());

							HashMap<String, String> miNames = languageService.hashMapTranslate1(mi.getContentDefinition(),
									l);
							String miName = miNames.get("menu_item_name");
							String desc = miNames.get("menu_item_description");

							milr.setMenuItemName(miName);
							milr.setDesc(desc);
							milr.setUrlImge(mi.getPicturePath());
							milr.setUrlSlug(CommonHelper.toPrettyURL(miName));
							milr.setPriceOriginal(mi.getPrice());
							String priceConvert = CommonHelper.formatDecimal(mi.getPrice() * cur.getRate(), l.getCode(),
									cur.getCode());
							milr.setPriceRateDisplay(priceConvert);
							milr.setCurrencyRate(cur.getRate());
							milr.setSymbolLeft(cur.getSymbolLeft());
							milr.setPriceRate(mi.getPrice() * cur.getRate());
							milr.setAvailable(checkItemAvailable(mi.getAvailableMonday(), mi.getAvailableTuesday(), mi.getAvailableWednesday(),
							mi.getAvailableThursday(), mi.getAvailableFriday(), mi.getAvailableSaturday(), mi.getAvailableSunday()));
							milr.setOutOfStock(mi.getOutOfStock());
							milr.setPriority(mi.getPriority());
							// get menu extra
							Set<MenuExtraItem> smei = mi.getMenuExtraItems();
							List<MenuExtraItemLiteResponse> lmeilr = new ArrayList<>();

							if (smei != null && smei.size() > 0) {
								smei.forEach(mx -> {
									MenuExtraItemLiteResponse meilr = new MenuExtraItemLiteResponse();
									meilr.setMenuExtraItemId(mx.getMenuExtraItemId());
									meilr.setExtraItemType(mx.getType());
									HashMap<String, String> meNames = languageService
											.hashMapTranslate1(mx.getContentDefinition(), l);
									String meName = meNames.get("menu_extra_item_name");
									meilr.setName(meName);
									// add to menu extra item
									lmeilr.add(meilr);

									// get extra item

									Set<ExtraItem> sei = mx.getExtraItems();
									List<ExtraItemLiteResponse> leilr = new ArrayList();

									if (sei != null && sei.size() > 0) {

										sei.forEach(se -> {

											ExtraItemLiteResponse eilr = new ExtraItemLiteResponse();
											eilr.setExtraItemId(se.getExtraItemId());
											Double p = se.getPrice() == null ? 0 : se.getPrice();
											eilr.setPrice(p);
											String exPrice = CommonHelper.formatDecimal(p * cur.getRate(), l.getCode(),
													cur.getCode());
											eilr.setPriceRateDisplay(exPrice);
											eilr.setPriceRate(p * cur.getRate());

											HashMap<String, String> exNames = languageService
													.hashMapTranslate1(se.getContentDefinition(), l);
											String exName = exNames.get("menu_extra_item_title");
											eilr.setName(exName);

											leilr.add(eilr);

										});

									}

									// add extra item
									meilr.setExtraitems(leilr);

									// add to menu item
									milr.setMenuExraItems(lmeilr);
								});
							}

							// add to menu item
							lmlr.add(milr);
						});
					}
					
				}
				//sort menu item by priority
				lmlr.sort((item1, item2) -> item1.getMenuId().compareTo(item2.getMenuId()) != 0 ? item1.getMenuId().compareTo(item2.getMenuId())
						:  item1.getPriority().compareTo(item2.getPriority()) != 0 ? item1.getPriority().compareTo(item2.getPriority()) : item1.getMenuItemName().compareTo(item2.getMenuItemName()));
				mr.setMenuItems(lmlr);
			});

			return mr;
		}
		return null;
	}

	private boolean checkItemAvailable(boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
		LocalDate localDate = LocalDate.now();
		boolean available = true;
		switch (localDate.getDayOfWeek()) {
			case MONDAY: available = mon; break;
			case TUESDAY: available = tue; break;
			case WEDNESDAY: available = wed; break;
			case THURSDAY: available = thu; break;
			case FRIDAY: available = fri; break;
			case SATURDAY: available = sat; break;
			case SUNDAY: available = sun; break;
		}
		return available;
	}
}
