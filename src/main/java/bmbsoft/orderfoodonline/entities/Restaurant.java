package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Restaurant generated by hbm2java
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements java.io.Serializable {

	private Long restaurantId;
	private ContentDefinition contentDefinition;
	private District district;
	private Zone zone;
	private String name;
	private String slogan;
	private String addressLine;
	private Double latitude;
	private Double longitude;
	private String openTime;
	private String closeTime;
	private String phone1;
	private String phone2;
	private Integer status;
	private String urlSlug;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String shipArea;
	private String keySearch;
	private Integer sortOrder;
	private Double minPrice;
	private String city;
	private String districtName;
	private String imageUrl;
	private Double deliveryCost;
	private String estimateDeliveryTime;
	private String addressDesc;
	private Integer typeReceive;
	private Set<Rating> ratings = new HashSet<Rating>(0);
	private Set<Favouries> favourieses = new HashSet<Favouries>(0);
	private Set<RestaurantComment> restaurantComments = new HashSet<RestaurantComment>(0);
	private Set<RestaurantAttribute> restaurantAttributes = new HashSet<RestaurantAttribute>(0);
	private Set<RestaurantPaymentProvider> restaurantPaymentProviders = new HashSet<RestaurantPaymentProvider>(0);
	private Set<Order> orders = new HashSet<Order>(0);
	private Set<RestaurantCategory> restaurantCategories = new HashSet<RestaurantCategory>(0);
	private Set<UserRestaurant> userRestaurants = new HashSet<UserRestaurant>(0);
	private Set<Menu> menus = new HashSet<Menu>(0);
	private Set<PromotionLineitem> promotionLineitems = new HashSet<PromotionLineitem>(0);
	private Set<RestaurantInfo> restaurantInfos = new HashSet<RestaurantInfo>(0);

	public Restaurant() {
	}

	public Restaurant(Long restaurantId, ContentDefinition contentDefinition, District district, Zone zone, String name, String slogan, String addressLine, Double latitude, Double longitude, String openTime, String closeTime, String phone1, String phone2, Integer status, String urlSlug, Date createdDate, String createdBy, Date modifiedDate, String modifiedBy, String shipArea, String keySearch, Integer sortOrder, Double minPrice, String city, String districtName, String imageUrl, Double deliveryCost, String estimateDeliveryTime, String addressDesc, Integer typeReceive, Set<Rating> ratings, Set<Favouries> favourieses, Set<RestaurantComment> restaurantComments, Set<RestaurantAttribute> restaurantAttributes, Set<RestaurantPaymentProvider> restaurantPaymentProviders, Set<Order> orders, Set<RestaurantCategory> restaurantCategories, Set<UserRestaurant> userRestaurants, Set<Menu> menus, Set<PromotionLineitem> promotionLineitems, Set<RestaurantInfo> restaurantInfos) {
		this.restaurantId = restaurantId;
		this.contentDefinition = contentDefinition;
		this.district = district;
		this.zone = zone;
		this.name = name;
		this.slogan = slogan;
		this.addressLine = addressLine;
		this.latitude = latitude;
		this.longitude = longitude;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.status = status;
		this.urlSlug = urlSlug;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.shipArea = shipArea;
		this.keySearch = keySearch;
		this.sortOrder = sortOrder;
		this.minPrice = minPrice;
		this.city = city;
		this.districtName = districtName;
		this.imageUrl = imageUrl;
		this.deliveryCost = deliveryCost;
		this.estimateDeliveryTime = estimateDeliveryTime;
		this.addressDesc = addressDesc;
		this.typeReceive = typeReceive;
		this.ratings = ratings;
		this.favourieses = favourieses;
		this.restaurantComments = restaurantComments;
		this.restaurantAttributes = restaurantAttributes;
		this.restaurantPaymentProviders = restaurantPaymentProviders;
		this.orders = orders;
		this.restaurantCategories = restaurantCategories;
		this.userRestaurants = userRestaurants;
		this.menus = menus;
		this.promotionLineitems = promotionLineitems;
		this.restaurantInfos = restaurantInfos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "restaurant_id", unique = true, nullable = false)
	public Long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_dep_id")
	public ContentDefinition getContentDefinition() {
		return this.contentDefinition;
	}

	public void setContentDefinition(ContentDefinition contentDefinition) {
		this.contentDefinition = contentDefinition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "districtId")
	public District getDistrict() {
		return this.district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zoneId")
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "slogan")
	public String getSlogan() {
		return this.slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	@Column(name = "address_line")
	public String getAddressLine() {
		return this.addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	@Column(name = "latitude", precision = 22, scale = 10)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", precision = 22, scale = 10)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "open_time", length = 20)
	public String getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	@Column(name = "close_time", length = 20)
	public String getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	@Column(name = "phone_1", length = 50)
	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(name = "phone_2", length = 50)
	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "url_slug")
	public String getUrlSlug() {
		return this.urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "created_by")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by")
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "ship_area", length = 65535)
	public String getShipArea() {
		return this.shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	@Column(name = "key_search", length = 500)
	public String getKeySearch() {
		return this.keySearch;
	}

	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}

	@Column(name = "sort_order")
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "min_price", precision = 10, scale = 2)
	public Double getMinPrice() {
		return this.minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	@Column(name = "city", length = 500)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "district_name", length = 500)
	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Column(name = "image_url")
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "delivery_cost", precision = 10, scale = 2)
	public Double getDeliveryCost() {
		return this.deliveryCost;
	}

	public void setDeliveryCost(Double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	@Column(name = "estimate_delivery_time")
	public String getEstimateDeliveryTime() {
		return this.estimateDeliveryTime;
	}

	public void setEstimateDeliveryTime(String estimateDeliveryTime) {
		this.estimateDeliveryTime = estimateDeliveryTime;
	}

	@Column(name = "address_desc", length = 65535)
	public String getAddressDesc() {
		return this.addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<Favouries> getFavourieses() {
		return this.favourieses;
	}

	public void setFavourieses(Set<Favouries> favourieses) {
		this.favourieses = favourieses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<RestaurantComment> getRestaurantComments() {
		return this.restaurantComments;
	}

	public void setRestaurantComments(Set<RestaurantComment> restaurantComments) {
		this.restaurantComments = restaurantComments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<RestaurantAttribute> getRestaurantAttributes() {
		return this.restaurantAttributes;
	}

	public void setRestaurantAttributes(Set<RestaurantAttribute> restaurantAttributes) {
		this.restaurantAttributes = restaurantAttributes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<RestaurantPaymentProvider> getRestaurantPaymentProviders() {
		return this.restaurantPaymentProviders;
	}

	public void setRestaurantPaymentProviders(Set<RestaurantPaymentProvider> restaurantPaymentProviders) {
		this.restaurantPaymentProviders = restaurantPaymentProviders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<RestaurantCategory> getRestaurantCategories() {
		return this.restaurantCategories;
	}

	public void setRestaurantCategories(Set<RestaurantCategory> restaurantCategories) {
		this.restaurantCategories = restaurantCategories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<UserRestaurant> getUserRestaurants() {
		return this.userRestaurants;
	}

	public void setUserRestaurants(Set<UserRestaurant> userRestaurants) {
		this.userRestaurants = userRestaurants;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<PromotionLineitem> getPromotionLineitems() {
		return this.promotionLineitems;
	}

	public void setPromotionLineitems(Set<PromotionLineitem> promotionLineitems) {
		this.promotionLineitems = promotionLineitems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	public Set<RestaurantInfo> getRestaurantInfos() {
		return this.restaurantInfos;
	}

	public void setRestaurantInfos(Set<RestaurantInfo> restaurantInfos) {
		this.restaurantInfos = restaurantInfos;
	}

	@Column(name = "type_receive")
	public Integer getTypeReceive() {
		return typeReceive;
	}

	public void setTypeReceive(Integer typeReceive) {
		this.typeReceive = typeReceive;
	}
}
