package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.CurrencyDAO;
import bmbsoft.orderfoodonline.dao.LanguageDAO;
import bmbsoft.orderfoodonline.dao.RestaurantDAO;
import bmbsoft.orderfoodonline.entities.*;
import bmbsoft.orderfoodonline.model.*;
import bmbsoft.orderfoodonline.model.shared.*;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

	@Autowired
	private RestaurantDAO restaurantDAO;
	@Autowired
	private LanguageDAO languageDAO;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private RestaurantCommentService restaurantCommentService;
	@Autowired
	private RestaurantWorkTimeService restaurantWorkTimeService;
	@Autowired
	private RestaurantAreaService restaurantAreaService;

	@Transactional
	public ResponseGetPaging getAll(final int pageIndex, final int pageSize, final String title, Integer status)
			throws Exception {
		int totalRecord = restaurantDAO.countGetAll(title, status);
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Restaurant> restaurants = restaurantDAO.getAll(firstResult, maxResult, title, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (restaurants == null || restaurants.isEmpty()) {
			rs.setStatus(8);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<RestaurantViewModel> restaurantModels = new LinkedList<>();
		if (restaurants != null && !restaurants.isEmpty()) {
			restaurants.forEach(res -> {
				restaurantModels.add(entityToModel(res, null));
			});
		}

		if (totalRecord > 0 && !restaurantModels.isEmpty()) {
			content.setData(restaurantModels);
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
	public String save(RestaurantRequest c, Restaurant model, MultipartFile file) {
		return restaurantDAO.save(c, model, file);
	}

	@Transactional
	@Async
	public Restaurant getById(final long id) {
		return restaurantDAO.getById(id);
	}

	@Async
	public boolean getByName(final String name) {
		return restaurantDAO.getByName(name);
	}

	@Transactional
	public RestaurantViewModel viewDetail(final long id, String languageCode) {
		Restaurant res = restaurantDAO.getById(id);
		Language l = languageDAO.getLanguageByCode(languageCode);
		if (l == null)
			return null;
		return (res == null) ? null : entityToModel(res, l);
	}

	@Transactional
	public RestaurantLiteResponse2 getByIdAndLangauge(final long id, Language l) throws ParseException {
		Restaurant res = restaurantDAO.getById(id);
		return (res == null || res.getStatus() != Constant.Status.Publish.getValue()) ? null
				: entityToModelLite2(res, l);
	}

	@Transactional
	public boolean delete(Restaurant r) {
		return restaurantDAO.saveCore(r);
	}

	@Transactional
	public RestaurantResponse getByDistrict(final AddressSearchModel req, Language l, String key)
			throws ParseException {
		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur == null) {
			cur.setRate(1d);
		}
		List<Restaurant> restaurants = restaurantDAO.getByDistrict(req.getLanguageCode(), key);
		RestaurantResponse rr = new RestaurantResponse();

		List<RestaurantViewModel> crvm = new LinkedList<>();
		List<RestaurantCategoryViewModel> rescat = new ArrayList<>();
		HashMap<String, Double> rankPrice = new HashMap<>();

		if (restaurants != null && restaurants.size() > 0) {

			for (Restaurant r : restaurants) {
				RestaurantViewModel vm = new RestaurantViewModel();
				List<RestaurantWorkTimeModel> rwt = restaurantWorkTimeService.getByRestaurantId(r.getRestaurantId());

				// bind restaurant
				vm.setRestaurantId(r.getRestaurantId());
				vm.setRestaurantName(r.getName()); // restaurant name
				vm.setAddress(r.getAddressLine());
				vm.setCloseTime(r.getCloseTime());
				vm.setOpenTime(r.getOpenTime());
				vm.setLongitude(r.getLatitude());
				vm.setLatitude(r.getLatitude());
				vm.setPhone1(r.getPhone1());
				vm.setShipArea(r.getShipArea());
				vm.setSlogan(r.getSlogan());
				vm.setStatus(r.getStatus());
				vm.setSortOrder(r.getSortOrder());
				vm.setCreatedDate(r.getCreatedDate());
				vm.setMinPrice(r.getMinPrice() * cur.getRate());
				vm.setImageUrl(r.getImageUrl());
				vm.setCurrencyRate(cur.getRate());
				vm.setSymbolLeft(cur.getSymbolLeft());
				vm.setSymbolRight(cur.getSymbolRight());
				vm.setDistrict(r.getDistrictName());
				vm.setTypeReceive(r.getTypeReceive());
				District dt = r.getDistrict();
				if (dt != null) {
					vm.setDistrictId(dt.getDistrictId());
					City city=dt.getCity();
					if(city!=null) {
						vm.setCityId(city.getCityId());
						vm.setCity(city.getCityName());
					}
				}
				Zone zone = r.getZone();
				if(zone != null) {
					vm.setZoneId(zone.getZoneId());
					vm.setZone(zone.getName());
				}
				vm.setDeliveryCost(r.getDeliveryCost());
				vm.setEstDeliveryTime(r.getEstimateDeliveryTime());
				vm.setCity(r.getCity());
				vm.setRestaurantClosed(!CommonHelper.checkBetweenTime(rwt));

				String priceConvert = CommonHelper.formatDecimal(r.getMinPrice() * cur.getRate(), l.getCode(),
						cur.getCode());
				vm.setPrice(priceConvert);
				// list category
				Set<RestaurantCategory> src = r.getRestaurantCategories();
				List<HashMap> categories = new ArrayList();

				if (src != null && src.size() > 0) {
					src = src.stream().filter(p -> p.getCategory().getStatus() == Constant.Status.Publish.getValue())
							.collect(Collectors.toSet());
					src.forEach(c -> {
						Category cg = c.getCategory();
						List<String> a = languageService.hashMapTranslate(cg.getContentDefinition(), l);
						HashMap hm = new HashMap();
						hm.put("categoryId", cg.getCategoryId());
						String cName = a != null && a.size() > 0 ? a.get(0) : "";
						hm.put("categoryName", cName);

						//
						RestaurantCategoryViewModel rcv = new RestaurantCategoryViewModel();
						rcv.setCategoryId(cg.getCategoryId());
						rcv.setCategoryName(cName);

						rescat.add(rcv);

						categories.add(hm);
					});
				}
				vm.setCategoryIds(categories);

				// Set rating
				Double rating = restaurantCommentService.getRatingForRestaurant(r.getRestaurantId());
				vm.setRating(rating);

				Set<RestaurantComment> srco = r.getRestaurantComments();
				if (srco != null && srco.size() > 0) {
					int c = (int) srco.stream().filter(p -> p.getStatus() == Constant.Status.Publish.getValue())
							.count();
					vm.setNumOfReview(c);
				}

				// #end rating
				crvm.add(vm);

				// promotion

				Set<PromotionLineitem> sp = r.getPromotionLineitems();
				List<PromotionLineitemResponse> lplr = new ArrayList<>();
				if (sp != null && sp.size() > 0) {

					sp = sp.stream()
							.filter(p -> new Date().after(p.getPromotion().getStartDate())
									&& new Date().before(p.getPromotion().getEndDate())
									&& p.getPromotion().getStatus() == Constant.Status.Publish.getValue())
							.collect(Collectors.toSet());

					sp.forEach(s -> {
						PromotionLineitemResponse p = new PromotionLineitemResponse();
						p.setValue(p.getValue()); // 10%

						Promotion pr = s.getPromotion();
						if (pr != null) {
							p.setValue(pr.getValue());

							HashMap<String, String> miNames = languageService
									.hashMapTranslate1(pr.getContentDefinition(), l);
							String miName = miNames.get("promotion_name");
							String desc = miNames.get("promotion_desc");

							p.setName(miName);
							p.setDesc(desc);
							p.setStartDate(pr.getStartDate());
							p.setEndDate(pr.getEndDate());
							p.setMinOrder(pr.getMinOrder() * cur.getRate());
						}
						p.setCode(s.getCode());
						lplr.add(p);

					});
				}
				vm.setPromotionLineItems(lplr);
			}
		}
		rr.setRestaurants(crvm);

		// list category
		if (rescat.size() > 0) {
			Map<Object, List<RestaurantCategoryViewModel>> rc = rescat.stream()
					.collect(Collectors.groupingBy(g -> g.getCategoryId()));
			List<RestaurantCategoryViewModel> lrcv = new ArrayList<>();
			rc.entrySet().forEach(c -> {
				RestaurantCategoryViewModel rcv = new RestaurantCategoryViewModel();
				rcv.setCategoryId((long) c.getKey());
				int count = c.getValue().size();

				rcv.setNumberOfRest(count);
				rcv.setCategoryName(c.getValue().get(0).getCategoryName());

				lrcv.add(rcv);
			});
			rr.setCategories(lrcv);
		}

		Restaurant minPrice = restaurants.stream().min(Comparator.comparing(Restaurant::getMinPrice))
				.orElseThrow(NoSuchElementException::new);
		Restaurant maxPrice = restaurants.stream().max(Comparator.comparing(Restaurant::getMinPrice))
				.orElseThrow(NoSuchElementException::new);
		if (minPrice != null && maxPrice != null) {
			rankPrice.put("minPriceRate", minPrice.getMinPrice() * cur.getRate());
			rankPrice.put("maxPriceRate", maxPrice.getMinPrice() * cur.getRate());
			rankPrice.put("minPrice",  minPrice.getMinPrice());
			rankPrice.put("maxPrice", maxPrice.getMinPrice());
			rr.setRankPrice(rankPrice);
		}

		return rr;
	}

	@Transactional
	@Async
	public List<RestaurantViewModel> getRestaurantsByname(final String name) throws Exception {
		List<Restaurant> restaurants = restaurantDAO.getRestaurantsByname(name);
		if (null == restaurants || restaurants.isEmpty())
			return null;
		List<RestaurantViewModel> restaurantModels = new LinkedList<>();
		restaurants.forEach(res -> {
			RestaurantViewModel resModel = new RestaurantViewModel();
			resModel.setRestaurantId(res.getRestaurantId());
			resModel.setRestaurantName(res.getName());
			resModel.setImageUrl(res.getImageUrl());
			resModel.setAddress(res.getAddressLine());
			restaurantModels.add(resModel);
		});
		return restaurantModels;
	}

	/**
	 * 
	 * @param res
	 *            : restaurant
	 * @param lang
	 *            : language null translate all, else translate by language.
	 * @return RestaurantViewModel
	 */
	private RestaurantViewModel entityToModel(Restaurant res, Language lang) {
		RestaurantViewModel c = new RestaurantViewModel();
		c.setRestaurantId(res.getRestaurantId());
		c.setRestaurantName(res.getName());
		c.setSlogan(res.getSlogan());
		c.setAddress(res.getAddressLine());
		c.setLatitude(res.getLatitude());
		c.setLongitude(res.getLongitude());
		c.setOpenTime(res.getOpenTime());
		c.setCloseTime(res.getCloseTime());
		c.setPhone1(res.getPhone1());
		c.setPhone2(res.getPhone2());
		c.setSortOrder(res.getSortOrder());
		c.setStatus(res.getStatus());
		c.setUrlSlug(res.getUrlSlug());
		c.setShipArea(res.getShipArea());
		c.setMinPrice(res.getMinPrice());
		c.setImageUrl(res.getImageUrl());
		c.setEstDeliveryTime(res.getEstimateDeliveryTime());
		c.setDeliveryCost(res.getDeliveryCost());
		
		District dt = res.getDistrict();
		if (dt != null) {
			c.setDistrictId(dt.getDistrictId());
			City city=dt.getCity();
			if(city!=null) {
				c.setCityId(city.getCityId());
				c.setCity(city.getCityName());
			}
		}

		Zone zone = res.getZone();
		if(zone != null) {
			c.setZone(zone.getName());
			c.setZoneId(zone.getZoneId());
		}
		c.setDistrict(res.getDistrictName());

		c.setAddressDesc(res.getAddressDesc());

		c.setRestaurantWorkTimeModels(restaurantWorkTimeService.getByRestaurantId(res.getRestaurantId()));

		c.setWorkArea(restaurantAreaService.getDistrictDeliveryListByRestaurant(res.getRestaurantId()));

		List<Long> ownerId = new ArrayList<>();
		if (res.getUserRestaurants() != null && !res.getUserRestaurants().isEmpty()) {
			res.getUserRestaurants().forEach(item -> {
				ownerId.add(item.getUser().getUserId());
			});
			c.setUserIdArray(ownerId);
			//c.setUserIds(owners);
		}
		Set<RestaurantAttribute> items = res.getRestaurantAttributes();
		List<AttributeViewModel> uvm = new ArrayList<>();
		for (RestaurantAttribute item : items) {
			AttributeViewModel vm = new AttributeViewModel();
			vm.setAttributeId(item.getRestaurantAttributeId());
			uvm.add(vm);
		}
		c.setAttributeLst(uvm);
		if (res.getContentDefinition() != null) {
			c.setLanguageLst(languageService.translate(res.getContentDefinition(), null));
		}

		Set<RestaurantCategory> src = res.getRestaurantCategories();

		if (src != null && src.size() > 0) {
			src = src.stream().filter(p -> p.getCategory().getStatus() == Constant.Status.Publish.getValue())
					.collect(Collectors.toSet());

			src.forEach(cc -> {
				Category cg = cc.getCategory();
				List<String> a = languageService.hashMapTranslate(cg.getContentDefinition(), lang);
				HashMap hm = new HashMap();
				List<Long> s = new ArrayList<>();
				s.add(cg.getCategoryId());
				c.setCategoryIdArray(s);
			});
		}


		// c.setUrlSlug(slg.slugify(vm.getName()));
		// c.setStatus(Constant.Status.Publish.hashCode());
		return c;
	}

	private RestaurantLiteResponse2 entityToModelLite2(Restaurant res, Language lang) throws ParseException {
		RestaurantLiteResponse2 c = new RestaurantLiteResponse2();
		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		Double rating = restaurantCommentService.getRatingForRestaurant(res.getRestaurantId());
		List<RestaurantWorkTimeModel> rwt = restaurantWorkTimeService.getByRestaurantId(res.getRestaurantId());
		if (cur == null) {
			cur.setRate(1d);
		}
		c.setRestaurantId(res.getRestaurantId());
		c.setName(res.getName());
		c.setSlogan(res.getSlogan());
		c.setAddress(res.getAddressDesc());
		c.setOpenTime(res.getOpenTime());
		c.setCloseTime(res.getCloseTime());
		c.setPhone1(res.getPhone1());
		c.setUrlSlug(res.getUrlSlug());
		c.setImageUrl(res.getImageUrl());
		c.setMinPrice(res.getMinPrice() * cur.getRate());
		c.setEstTime(res.getEstimateDeliveryTime());
		c.setDeliveryCost(res.getDeliveryCost());
		c.setStatus(res.getStatus());
		c.setEstTime(res.getEstimateDeliveryTime());
		c.setRating(rating);
		c.setLatitude(res.getLatitude());
		c.setLongitude(res.getLongitude());
		c.setRestaurantClosed(!CommonHelper.checkBetweenTime(rwt));

		c.setRestaurantWorkTimeModels(rwt);

		if (res.getContentDefinition() != null) {
			List<String> desc = languageService.hashMapTranslate(res.getContentDefinition(), lang);
			c.setDescription(desc != null && desc.size() > 0 ? desc.get(0) : "");
		}

		// comment
		Set<RestaurantComment> srco = res.getRestaurantComments();
		if (srco != null && srco.size() > 0) {
			int cr = (int) srco.stream().filter(p -> p.getStatus() == Constant.Status.Publish.getValue()).count();
			c.setNumOfReview(cr);
		}

		// favourites
		Set<Favouries> sf = res.getFavourieses();
		if (sf != null && sf.size() > 0) {
			c.setNumOfFavouries(
					(int) sf.stream().filter(p -> p.getIsStatus() == Constant.Status.Publish.getValue()).count());
		}
		// list category id.
		Set<RestaurantCategory> src = res.getRestaurantCategories();
		if (src != null && src.size() > 0) {
			List<CategoryLiteRequest> lcvm = new LinkedList<>();
			src = src.stream().filter(p -> p.getCategory().getStatus() == Constant.Status.Publish.getValue())
					.collect(Collectors.toSet());
			src.forEach(rc -> {
				Category cg = rc.getCategory();
				if (cg != null) {
					CategoryLiteRequest cc = new CategoryLiteRequest();
					cc.setCategoryId(cg.getCategoryId());
					List<String> catName = languageService.hashMapTranslate(cg.getContentDefinition(), lang);
					cc.setCategoryName(catName != null && catName.size() > 0 ? catName.get(0) : "");
					lcvm.add(cc);
				}
			});
			c.setCategoryIds(lcvm);
		}

		// promotion

		Set<PromotionLineitem> sp = res.getPromotionLineitems();
		List<PromotionLineitemResponse> lplr = new ArrayList<>();
		if (sp != null && sp.size() > 0) {
			sp = sp.stream()
					.filter(p -> new Date().after(p.getPromotion().getStartDate())
							&& new Date().before(p.getPromotion().getEndDate())
							&& p.getPromotion().getStatus() == Constant.Status.Publish.getValue())
					.collect(Collectors.toSet());

			sp.forEach(s -> {
				PromotionLineitemResponse p = new PromotionLineitemResponse();
				p.setValue(p.getValue()); // 10%

				Promotion pr = s.getPromotion();
				if (pr != null) {
					p.setValue(pr.getValue());

					HashMap<String, String> miNames = languageService.hashMapTranslate1(pr.getContentDefinition(),
							lang);
					String miName = miNames.get("promotion_name");
					String desc = miNames.get("promotion_desc");

					p.setName(miName);
					p.setDesc(desc);
					p.setStartDate(pr.getStartDate());
					p.setEndDate(pr.getEndDate());
					p.setMinOrder(pr.getMinOrder() * cur.getRate());
				}
				p.setCode(s.getCode());
				lplr.add(p);

			});
		}
		c.setPromotionLineItems(lplr);

		// list owner id.
		Set<UserRestaurant> sur = res.getUserRestaurants();
		if (sur != null && sur.size() > 0) {
			List<UserRequest> owners = new LinkedList<>();
			sur = sur.stream().filter(p -> p.getUser().getStatus() == Constant.Status.Publish.getValue())
					.collect(Collectors.toSet());

			sur.forEach(item -> {
				UserRequest u = new UserRequest();
				u.setUserId(item.getUser().getUserId());
				u.setUserName(item.getUser().getFullName());
				owners.add(u);
			});
			c.setUserIds(owners);
		}

		// atribute
		Set<RestaurantAttribute> ras = res.getRestaurantAttributes();
		List<AttributeViewModel> uvm = new LinkedList<>();
		for (RestaurantAttribute item : ras) {
			AttributeViewModel vm = new AttributeViewModel();
			vm.setAttributeId(item.getRestaurantAttributeId());
			uvm.add(vm);
		}
		c.setAttributeLst(uvm);
		return c;
	}

	public List<CityViewModel> getCityByKey(String key) {
		return restaurantDAO.getCityByKey(key);
	}

	@Async
	public List<RestaurantLiteResponse3> getAllRestaurantSortByName() throws Exception {
		List<Restaurant> ur = restaurantDAO.getAllSortByName();
		List<RestaurantLiteResponse3> r = new ArrayList<>();
		if (ur != null && ur.size() > 0) {
			ur.forEach(u -> {
				RestaurantLiteResponse3 r3 = new RestaurantLiteResponse3();
				r3.setRestaurantId(u.getRestaurantId());
				r3.setRestaurantName(u.getName());
				r.add(r3);
			});
		}

		return r;
	}
}
