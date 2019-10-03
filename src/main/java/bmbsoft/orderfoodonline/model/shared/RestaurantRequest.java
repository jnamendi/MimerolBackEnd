package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import javax.validation.constraints.NotNull;

import bmbsoft.orderfoodonline.model.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private Double minPrice;

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
	private List<Integer> categoryIds;

	private List<Long> userIds;

	private List<Long> workArea;

	@NotNull(message = "estDeliveryTime is required")
	private String estDeliveryTime;
	@NotNull(message = "deliveryCost is required")
	private Double deliveryCost;
 
	private Long districtId;

	private Long zoneId;

	private List<DeliveryArea> deliveryArea;

	public List<DeliveryArea> getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(List<DeliveryArea> deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

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

	public List<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public String getEstDeliveryTime() {
		return estDeliveryTime;
	}

	public void setEstDeliveryTime(String estDeliveryTime) {
		this.estDeliveryTime = estDeliveryTime;
	}

	public Double getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Double deliveryCost) {
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

	public List<Long> getWorkArea() {
		return workArea;
	}

	public void setWorkArea(List<Long> workArea) {
		this.workArea = workArea;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}
}
