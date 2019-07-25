package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.*;
import bmbsoft.orderfoodonline.model.*;
import bmbsoft.orderfoodonline.model.shared.CategoryLiteRequest;
import bmbsoft.orderfoodonline.model.shared.RestaurantRequest;
import bmbsoft.orderfoodonline.model.shared.UserRequest;
import bmbsoft.orderfoodonline.service.*;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository(value = "restaurantDAO")
@Transactional(rollbackOn = Exception.class)
public class RestaurantDAO {
	public static final Logger logger = LoggerFactory.getLogger(RestaurantDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ContentDefinitionService cds;

	@Autowired
	ContentEntryService ces;

	@Autowired
	LanguageService ls;

	@Autowired
	MediaService ms;

	@Autowired
	CategoryService categoryServies;

	@Autowired
	RestaurantAttributeService restaurantAttributeService;

	@Autowired
	RestaurantPaymentProviderService restaurantPaymentProviderService;

	@Autowired
	PaymentProviderService pps;

	@Autowired
	DistrictService ds;

	@Autowired
	private RestaurantWorkTimeService restaurantWorkTimeService;

	@Autowired
	private UserDAO userDao;

	public boolean saveCore(Restaurant rs) {
		try {
			Session session = this.sessionFactory.getCurrentSession();

			session.saveOrUpdate(rs);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public String save(RestaurantRequest vm, Restaurant model, MultipartFile file) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();

		try {

			if (model != null) {
				Boolean isF = false;
				String msg = "";

				District district = ds.getBaseById(vm.getDistrictId());

				if (district == null) {

					return "District is null";
				}

				Restaurant res = this.modelToEntity(vm, model);
				Long resId = res.getRestaurantId();

				res.setDistrict(district);
				res.setDistrictName(district.getName());

				City city = district.getCity();
				if (city != null) {
					res.setCity(city.getCityName());
				}

				ContentDefinition cd = res.getContentDefinition();

				// upload image
				String imgUpload = CommonHelper.doUpload(res.getImageUrl(), file);
				res.setImageUrl(imgUpload);

				// update res
				session.update(res);

				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					String q = "DELETE FROM content_entry WHERE content_dep_id=:cd";
					Long cdId = cd.getContentDepId();
					session.createNativeQuery(q).setParameter("cd", cdId).executeUpdate();
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								if (cdm.getValue() == null && cdm.getValue().isEmpty()) {
									isF = true;
									msg = "Value is field required. " + cdm.getCode();
									break;
								}
								ContentEntry ce = new ContentEntry();
								ce.setCode(cdm.getCode().trim());
								ce.setValue(cdm.getValue().trim());
								ce.setContentDefinition(cd);
								ce.setLanguage(lang);

								session.save(ce);
							}
						} else {
							isF = true;
							msg = "Do not exist language.";
							break;
						}
					}
				}

				List<RestaurantWorkTimeModel> rwt = vm.getRestaurantWorkTimeModels();
				if (!rwt.isEmpty()) {
					if(resId != null) restaurantWorkTimeService.deleteByRestaurantId(resId);
					for (RestaurantWorkTimeModel r : rwt) {
						RestaurantWorkTime reswt = new RestaurantWorkTime();
						reswt.setWeekday(r.getWeekDay());
						reswt.setStartTime(r.getOpenTime());
						reswt.setEndTime(r.getCloseTime());
						reswt.setRestaurant(res);
						session.save(reswt);
					}
				}

				// categories
				if (vm.getCategoryIds() != null && vm.getCategoryIds().size() > 0) {
					String qrc = "DELETE FROM restaurant_category WHERE restaurant_id=:resId";
					session.createNativeQuery(qrc).setParameter("resId", resId).executeUpdate();

					for (CategoryLiteRequest c : vm.getCategoryIds()) {
						Category ctg = categoryServies.getById(c.getCategoryId());
						if (ctg == null) {
							isF = true;
							msg = "Could not found item. categoryId=" + c;
							break;
						} else {
							RestaurantCategory rc = new RestaurantCategory();
							rc.setRestaurant(res);
							rc.setCategory(ctg);

							session.save(rc);
						}
					}
				}

				// res-attribute
				if (vm.getAttributeLst() != null && vm.getAttributeLst().size() > 0) {
					String rat = "DELETE FROM restaurant_attribute WHERE restaurant_id=:resId";
					session.createNativeQuery(rat).setParameter("resId", resId)
							.executeUpdate();

					for (AttributeViewModel atg : vm.getAttributeLst()) {
						RestaurantAttribute ra = new RestaurantAttribute();
						ra.setRestaurant(res);

						session.save(ra);
					}
				}

				// res-payment provider

				if (vm.getPaymentProviderLst() != null && vm.getPaymentProviderLst().size() > 0) {
					String rppv = "DELETE FROM restaurant_payment_provider WHERE restaurant_id=:resId";
					session.createNativeQuery(rppv).setParameter("resId", resId)
							.executeUpdate();

					for (PaymentProviderViewModel ppv : vm.getPaymentProviderLst()) {
						PaymentProvider pv = pps.getBaseById(ppv.getPaymentProviderId());
						if (pv != null) {
							RestaurantPaymentProvider rpp = new RestaurantPaymentProvider();
							rpp.setRestaurant(res);
							rpp.setPaymentProvider(pv);

							session.save(rpp);
							// rpp.s
						}
					}
				}

				// user_Restaurant
				if (vm.getUserIds() != null && vm.getUserIds().size() > 0) {
					String removeRoleOwner = "DELETE FROM user_restaurant WHERE restaurant_id=:resId";
					session.createNativeQuery(removeRoleOwner).setParameter("resId", resId)
							.executeUpdate();

					for (UserRequest c : vm.getUserIds()) {
						User user = userDao.findById(c.getUserId());
						if (user == null) {
							isF = true;
							msg = "Could not found item. user_id= " + c;
							break;
						} else {
							UserRestaurant ur = new UserRestaurant();
							ur.setRestaurant(res);
							ur.setUser(user);
							session.save(ur);
						}

					}
				}

				if (!isF) {

					t.commit();
					return "";
				}
				return msg;
			} else {
				District district = ds.getBaseById(vm.getDistrictId());

				if (district == null) {

					return "District is null";
				}

				// content defi
				ContentDefinition cd = new ContentDefinition();
				cd.setName(vm.getRestaurantName());
				session.save(cd);

				Restaurant res = this.modelToEntity(vm, null);
				res.setDistrict(district);
				res.setDistrictName(district.getName());

				City city = district.getCity();
				if (city != null) {
					res.setCity(city.getCityName());
				}

				String msg = "";
				boolean isF = false;

				// contendefi
				res.setContentDefinition(cd);

				// upload
				String imgUpload = CommonHelper.doUpload(null, file);
				res.setImageUrl(imgUpload);
				// save res
				session.save(res);

				// Language
				List<LanguageViewModel> lvm = vm.getLanguageLst();
				if (lvm != null && lvm.size() > 0) {
					for (LanguageViewModel l : lvm) {
						Language lang = ls.getById(l.getLanguageId());
						if (lang != null) {
							// save content entry
							for (ContentDefModel cdm : l.getContentDef()) {
								if (cdm.getValue() == null && cdm.getValue().isEmpty()) {
									isF = true;
									msg = "Value is field required. " + cdm.getCode();
									break;
								}
								ContentEntry ce = new ContentEntry();
								ce.setCode(cdm.getCode().trim());
								ce.setValue(cdm.getValue().trim());
								ce.setContentDefinition(cd);
								ce.setLanguage(lang);

								session.save(ce);
							}
						} else {
							isF = true;
							msg = "Do not exist language.";
							break;
						}
					}
				}

				// Attribute
				if (vm.getAttributeLst() != null && vm.getAttributeLst().size() > 0) {
					for (AttributeViewModel atg : vm.getAttributeLst()) {
						RestaurantAttribute ra = new RestaurantAttribute();
						ra.setRestaurant(res);

						session.save(ra);
					}
				}
				// Payment provider
				if (vm.getPaymentProviderLst() != null && vm.getPaymentProviderLst().size() > 0) {
					for (PaymentProviderViewModel ppv : vm.getPaymentProviderLst()) {
						PaymentProvider pv = pps.getBaseById(ppv.getPaymentProviderId());
						if (pv != null) {
							RestaurantPaymentProvider rpp = new RestaurantPaymentProvider();
							rpp.setRestaurant(res);
							rpp.setPaymentProvider(pv);

							session.save(rpp);
							// rpp.s
						}
					}
				}

				// category
				if (vm.getCategoryIds() != null && vm.getCategoryIds().size() > 0) {
					for (CategoryLiteRequest c : vm.getCategoryIds()) {
						Category ctg = categoryServies.getById(c.getCategoryId());
						if (ctg == null) {
							isF = true;
							msg = "Could not found item. category_id=" + c;
							break;
						} else {
							RestaurantCategory rc = new RestaurantCategory();
							rc.setRestaurant(res);
							rc.setCategory(ctg);

							session.save(rc);
						}
					}
				}

				// user_Restaurant
				if (vm.getUserIds() != null && vm.getUserIds().size() > 0) {
					// vm.getCategoryIds().forEach(userId -> {
					for (UserRequest c : vm.getUserIds()) {
						User user = userDao.findById(c.getUserId());
						if (user == null) {
							isF = true;
							msg = "Could not found item. user_id= " + c;
							break;
						} else {
							UserRestaurant ur = new UserRestaurant();
							ur.setRestaurant(res);
							ur.setUser(user);
							session.save(ur);
						}
					}
				}

				// set work time for restaurant
				List<RestaurantWorkTimeModel> rwt = vm.getRestaurantWorkTimeModels();
				if (!rwt.isEmpty()) {
					for (RestaurantWorkTimeModel r : rwt) {
						RestaurantWorkTime reswt = new RestaurantWorkTime();
						reswt.setWeekday(r.getWeekDay());
						reswt.setStartTime(r.getOpenTime());
						reswt.setEndTime(r.getCloseTime());
						reswt.setRestaurant(res);
						session.save(reswt);
					}
				}

				if (!isF) {

					t.commit();
					return "";
				}
				return msg;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return e.getMessage();
		}finally {
			session.close();
		}
	}

	private Restaurant modelToEntity(RestaurantRequest vm, Restaurant e) {
		// Slugify slg = new Slugify();
		if (e == null) {
			Restaurant c = new Restaurant();
			c.setName(vm.getRestaurantName().trim());
			c.setCreatedDate(new Date());
			c.setModifiedDate(new Date());
			c.setSlogan(vm.getSlogan());
			c.setAddressLine(vm.getAddress());
			c.setLatitude(vm.getLatitude());
			c.setLongitude(vm.getLongitude());
			c.setOpenTime(vm.getOpenTime());
			c.setCloseTime(vm.getCloseTime());
			c.setPhone1(vm.getPhone1());
			c.setPhone2(vm.getPhone2());
			c.setShipArea(vm.getShipArea());
			c.setUrlSlug(CommonHelper.toPrettyURL(vm.getRestaurantName()));
			c.setStatus(vm.getStatus());
			c.setSortOrder(vm.getSortOrder());
			c.setAddressDesc(vm.getAddressDesc());

//			c.setKeySearch(CommonHelper.toPrettyURL(vm.getDistrict()) + "#" + CommonHelper.toPrettyURL(vm.getCity()));
			// keysearch only city
			c.setKeySearch("#" + CommonHelper.toPrettyURL(vm.getCity()));
			c.setMinPrice(vm.getMinPrice());
			// c.setCity(vm.getCity());
			// c.setDistrict(vm.getDistrict());
			c.setDeliveryCost(vm.getDeliveryCost());
			c.setEstimateDeliveryTime(vm.getEstDeliveryTime());
			c.setImageUrl(vm.getImageUrl());

			return c;
		} else {
			e.setName(vm.getRestaurantName().trim());
			e.setSlogan(vm.getSlogan());
			e.setAddressLine(vm.getAddress());
			e.setLatitude(vm.getLatitude());
			e.setLongitude(vm.getLongitude());
			e.setOpenTime(vm.getOpenTime());
			e.setCloseTime(vm.getCloseTime());
			e.setPhone1(vm.getPhone1());
			e.setPhone2(vm.getPhone2());
			e.setShipArea(vm.getShipArea());
			e.setUrlSlug(CommonHelper.toPrettyURL(vm.getRestaurantName()));
			e.setSortOrder(vm.getSortOrder());
			e.setModifiedDate(new Date());
			e.setMinPrice(vm.getMinPrice());
			e.setAddressDesc(vm.getAddressDesc());
			// keysearch only city
			e.setKeySearch("#" + CommonHelper.toPrettyURL(vm.getCity()));
			// e.setCity(vm.getCity());
			e.setAddressDesc(vm.getAddressDesc());
			// e.setDistrict(vm.getDistrict());
			e.setImageUrl(vm.getImageUrl());
			e.setStatus(vm.getStatus());
			e.setDeliveryCost(vm.getDeliveryCost());
			e.setEstimateDeliveryTime(vm.getEstDeliveryTime());
			return e;
		}
	}

	public Restaurant getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Restaurant.class, id);
	}

	public boolean getByName(final String name) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Restaurant> query = session.createQuery("FROM Restaurant r where r.name=:n and r.status=:sta")
				.setParameter("n", name.trim()).setParameter("sta", Constant.Status.Publish.getValue()).getResultList();
		if (query != null && query.size() > 0) {
			return true;
		}
		return false;
	}

	public void delete(final Restaurant Restaurant) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Restaurant);
	}

	/**
	 * user for: filter and search.
	 * 
	 * @param root
	 *            : form
	 * @param cb:
	 *            criteriaBuilder
	 * @param name
	 *            : null -> get all
	 * @param status:
	 *            null -> get status publish
	 * @return
	 */
	private Predicate toPredicate(Root<?> root, CriteriaBuilder cb, String name, Integer status) {
		Predicate predicate = cb.conjunction();
		if (null != name && !name.isEmpty()) {
			predicate = cb.and(predicate, cb.like(root.<String>get("name"), "%" + name + "%"));
		}
		if (null == status) {
			predicate = cb.and(predicate, cb.notEqual(root.<Integer>get("status"), Constant.Status.Deleted.getValue()));
		} else {
			predicate = cb.and(predicate, cb.equal(root.<Integer>get("status"), status));
		}
		return predicate;
	}

	public int countGetAll(String title, Integer status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
		Root<Restaurant> countFrom = queryCount.from(Restaurant.class);
		queryCount.select(cb.count(countFrom));
		queryCount.where(toPredicate(countFrom, cb, title, status));
		try {
			Long totalCount = session.createQuery(queryCount).getSingleResult();
			return totalCount.intValue();
		} catch (NoResultException e) {
			// Ignore this because not found by input condition.
			return 0;
		}
	}

	public List<Restaurant> getAll(final int firstResult, final int maxResult, String title, Integer status)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Restaurant> queryRestaurant = cb.createQuery(Restaurant.class);
		Root<Restaurant> rootRestaurant = queryRestaurant.from(queryRestaurant.getResultType());
		queryRestaurant.select(rootRestaurant);
		queryRestaurant.where(toPredicate(rootRestaurant, cb, title, status));
		queryRestaurant.orderBy(cb.desc(rootRestaurant.get("modifiedDate")));
		List<Restaurant> restaurants = (maxResult == 0) ? session.createQuery(queryRestaurant).getResultList()
				: session.createQuery(queryRestaurant).setFirstResult(firstResult).setMaxResults(maxResult)
						.getResultList();
		return restaurants;
	}

	public List<Restaurant> getAllSortByName() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Restaurant> queryRestaurant = cb.createQuery(Restaurant.class);
		Root<Restaurant> rootRestaurant = queryRestaurant.from(queryRestaurant.getResultType());
		queryRestaurant.select(rootRestaurant);
		queryRestaurant.where(toPredicate(rootRestaurant, cb, null, Constant.Status.Publish.getValue()));
		queryRestaurant.orderBy(cb.asc(rootRestaurant.get("name")));
		List<Restaurant> restaurants = session.createQuery(queryRestaurant).getResultList();
		return restaurants;
	}

	@Async
	public List<Restaurant> getByDistrict(final String languageCode, String key) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Restaurant> queryRestaurant = cb.createQuery(Restaurant.class);
			Root<Restaurant> rootRestaurant = queryRestaurant.from(Restaurant.class);

			Join<Restaurant, ContentDefinition> definitionJoin = rootRestaurant.join("contentDefinition");
			Join<ContentDefinition, ContentEntry> entryJoin = definitionJoin.join("contentEntries");
			Join<ContentEntry, Language> langJoin = entryJoin.join("language");
			// Join<Restaurant, RestaurantCategory> rc =
			// rootRestaurant.join("restaurantCategories");

			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(rootRestaurant.get("keySearch"), key));
			predicates.add(cb.equal(rootRestaurant.get("status"), Constant.Status.Publish.getValue()));
			// predicates.add(cb.equal(rc.get("category").<Integer>get("status"),Constant.Status.Publish.getValue()));

			// TODO get price menu
			if (!languageCode.isEmpty()) {
				predicates.add(cb.equal(langJoin.get("code"), languageCode));
			}

			queryRestaurant.select(rootRestaurant).where(predicates.stream().toArray(Predicate[]::new));
			// queryRestaurant.orderBy(cb.desc(rc.get("category").get("sortOrder")));

			List<Restaurant> listRestaurant = session.createQuery(queryRestaurant).getResultList();

			return listRestaurant;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	public List<RestaurantCategory> getByResId(List<Long> id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<RestaurantCategory> query = cb.createQuery(RestaurantCategory.class);
			Root<RestaurantCategory> from = query.from(RestaurantCategory.class);

			return null;
		} catch (Exception e) {
			logger.error(e.toString());

			return null;
		}
	}

	public List<Restaurant> getRestaurantsByname(final String name) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Restaurant> queryRestaurant = cb.createQuery(Restaurant.class);
		Root<Restaurant> rootRestaurant = queryRestaurant.from(Restaurant.class);
		queryRestaurant.select(rootRestaurant).where(toPredicate(rootRestaurant, cb, name, null));
		queryRestaurant.orderBy(cb.desc(rootRestaurant.get("modifiedDate")));
		return session.createQuery(queryRestaurant).getResultList();
	}

	public List<CityViewModel> getCityByKey(String key) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			// CriteriaBuilder cb = session.getCriteriaBuilder();
			// CriteriaQuery<Restaurant> query = cb.createQuery(Restaurant.class);
			// Root<Restaurant> from = query.from(Restaurant.class);
			//
			// query.where(cb.like(from.get("city"), "%" + key + "%"),
			// cb.equal(from.get("status"), Constant.Status.Publish.getValue()));
			// query.orderBy(cb.asc(from.get("name")));
			// List<Restaurant> s = session.createQuery(query).getResultList();

			List<String> s = session
					.createQuery("select distinct p.city from Restaurant p where p.city like :key and p.status=:st",
							String.class)
					.setParameter("key", "%" + key + "%").setParameter("st", Constant.Status.Publish.getValue())
					.getResultList();

			List<CityViewModel> lcv = new ArrayList<>();
			if (s != null && s.size() > 0) {
				s.forEach(res -> {
					CityViewModel vm = new CityViewModel();
					vm.setName(res);
					vm.setStatus(Constant.Status.Publish.getValue());
					lcv.add(vm);
				});
			}
			return lcv;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
}
