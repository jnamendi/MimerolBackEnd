package bmbsoft.orderfoodonline.model;

import bmbsoft.orderfoodonline.model.shared.PromotionLineitemResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
public class RestaurantViewModel {
	private Long restaurantId;
	private Long mediaId;
	private String restaurantName;
	private String slogan;
	private String address;
	private double latitude;
	private double longitude;
	private String openTime;
	private String closeTime;
	private List<RestaurantWorkTimeModel> restaurantWorkTimeModels;
	private String phone1;
	private String phone2;
	private String shipArea;
	private Date createdDate;
	private double rating;
	private int numOfReview;
	private List<HashMap> categoryIds;
	private List<HashMap> attributes;
	private Long minPrice; // original
	private String price; // convert
	private float currencyRate;
	private String symbolLeft;
	private String district;
	private Long districtId;
	private Long deliveryCost;
	private String estDeliveryTime;
	private List<HashMap> userIds;
	private List<PromotionLineitemResponse> promotionLineItems;
	private String city; 
	private boolean restaurantClosed;
	private Long cityId;
	private Integer typeReceive;
	private List<RestaurantDeliveryAreaModel> workArea;

	public Integer getTypeReceive() {
		return typeReceive;
	}

	public void setTypeReceive(Integer typeReceive) {
		this.typeReceive = typeReceive;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
 

	public String getSymbolLeft() {
		return symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	public String getSymbolRight() {
		return symbolRight;
	}

	public void setSymbolRight(String symbolRight) {
		this.symbolRight = symbolRight;
	}

	private String symbolRight;
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public float getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(float currencyRate) {
		this.currencyRate = currencyRate;
	}

	public List<HashMap> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<HashMap> attributes) {
		this.attributes = attributes;
	}

	private List<LanguageViewModel> languageLst;
	private List<AttributeViewModel> attributeLst;
	private List<PaymentProviderViewModel> paymentProviderLst;
	//private List<CategoryViewModel> categoryIds;
	private List<String> owners;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUrlSlug() {
		return urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public List<AttributeViewModel> getAttributeLst() {
		return attributeLst;
	}

	public void setAttributeLst(List<AttributeViewModel> attributeLst) {
		this.attributeLst = attributeLst;
	}

	public String getShipArea() {
		return shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	public List<PaymentProviderViewModel> getPaymentProviderLst() {
		return paymentProviderLst;
	}

	public void setPaymentProviderLst(List<PaymentProviderViewModel> paymentProviderLst) {
		this.paymentProviderLst = paymentProviderLst;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
//
//	public List<CategoryViewModel> getCategoryIds() {
//		return categoryIds;
//	}
//
//	public void setCategoryIds(List<CategoryViewModel> categoryIds) {
//		this.categoryIds = categoryIds;
//	}

	private int status;
	private int sortOrder;
	private String urlSlug;

	public List<String> getOwners() {
		return owners;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<HashMap> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<HashMap> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setCategoryIdArray(List categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setUserIdArray(List userIds){this.userIds = userIds;}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNumOfReview() {
		return numOfReview;
	}

	public void setNumOfReview(int numOfReview) {
		this.numOfReview = numOfReview;
	}

	public Long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private String imageUrl;
	private String addressDesc;
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Long deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getEstDeliveryTime() {
		return estDeliveryTime;
	}

	public void setEstDeliveryTime(String estTime) {
		this.estDeliveryTime = estTime;
	}

	public List<HashMap> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<HashMap> userIds) {
		this.userIds = userIds;
	}

	public List<PromotionLineitemResponse> getPromotionLineItems() {
		return promotionLineItems;
	}

	public void setPromotionLineItems(List<PromotionLineitemResponse> promotionLineItems) {
		this.promotionLineItems = promotionLineItems;
	}

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public boolean isRestaurantClosed() {
		return restaurantClosed;
	}

	public void setRestaurantClosed(boolean restaurantClosed) {
		this.restaurantClosed = restaurantClosed;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public List<RestaurantWorkTimeModel> getRestaurantWorkTimeModels() {
		return restaurantWorkTimeModels;
	}

	public void setRestaurantWorkTimeModels(List<RestaurantWorkTimeModel> restaurantWorkTimeModels) {
		this.restaurantWorkTimeModels = restaurantWorkTimeModels;
	}

	public List<RestaurantDeliveryAreaModel> getWorkArea() {
		return workArea;
	}

	public void setWorkArea(List<RestaurantDeliveryAreaModel> workArea) {
		this.workArea = workArea;
	}
}
