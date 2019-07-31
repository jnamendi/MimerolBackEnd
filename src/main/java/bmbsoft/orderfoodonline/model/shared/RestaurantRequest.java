package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import javax.validation.constraints.NotNull;

import bmbsoft.orderfoodonline.model.RestaurantWorkTimeModel;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bmbsoft.orderfoodonline.model.AttributeViewModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.PaymentProviderViewModel;

public class RestaurantRequest {
	private Long restaurantId;

	@NotNull(message = "restaurant name is required")
	private String restaurantName;
	private String slogan;

	@NotNull(message = "address is required")
	private String address;
	@NotNull(message = "latitude is required")
	private double latitude;
	@NotNull(message = "longitude is required")
	private double longitude;

	// TODO: Delete redundant fields
	private String openTime;
	private String closeTime;

	@NotNull(message = "Time is required")
	private List<RestaurantWorkTimeModel> restaurantWorkTimeModels;

	private String phone1;
	private String phone2;
	private String shipArea;
	@NotNull(message = "minPrice is required")
	private Long minPrice;

	@NotNull(message = "city is required")
	private String city;

	private String district;
	private String addressDesc;

	private String imageUrl;

	private List<LanguageViewModel> languageLst;
	private List<AttributeViewModel> attributeLst;
	private List<PaymentProviderViewModel> paymentProviderLst;

	private int status;
	private int sortOrder;
	@NotNull(message = "categoryIds is required")
//	private List<CategoryLiteRequest> categoryIds;
	private List<Integer> categoryIds;

//	private List<UserRequest> userIds;

	private List<Long> userIds;



	@NotNull(message = "estDeliveryTime is required")
	private String estDeliveryTime;
	@NotNull(message = "deliveryCost is required")
	private Long deliveryCost;
 
	private Long districtId;

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
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

	public String getShipArea() {
		return shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public List<AttributeViewModel> getAttributeLst() {
		return attributeLst;
	}

	public void setAttributeLst(List<AttributeViewModel> attributeLst) {
		this.attributeLst = attributeLst;
	}

	public List<PaymentProviderViewModel> getPaymentProviderLst() {
		return paymentProviderLst;
	}

	public void setPaymentProviderLst(List<PaymentProviderViewModel> paymentProviderLst) {
		this.paymentProviderLst = paymentProviderLst;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

//	public List<CategoryLiteRequest> getCategoryIds() {
//		return categoryIds;
//	}
//
//	public void setCategoryIds(List<CategoryLiteRequest> categoryIds) {
//		this.categoryIds = categoryIds;
//	}

	public List<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

//	public List<UserRequest> getUserIds() {
//		return userIds;
//	}
//
//	public void setUserIds(List<UserRequest> userIds) {
//		this.userIds = userIds;
//	}

	public Long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}

	public String getEstDeliveryTime() {
		return estDeliveryTime;
	}

	public void setEstDeliveryTime(String estDeliveryTime) {
		this.estDeliveryTime = estDeliveryTime;
	}

	public Long getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Long deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public List<RestaurantWorkTimeModel> getRestaurantWorkTimeModels() {
		return restaurantWorkTimeModels;
	}

	public void setRestaurantWorkTimeModels(List<RestaurantWorkTimeModel> restaurantWorkTimeModels) {
		this.restaurantWorkTimeModels = restaurantWorkTimeModels;
	}
}
