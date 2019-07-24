package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import bmbsoft.orderfoodonline.model.AttributeViewModel;
import bmbsoft.orderfoodonline.model.RestaurantWorkTimeModel;

public class RestaurantLiteResponse2 {
	private Long restaurantId;
	private Long mediaId;
	private Double latitude;
	private Double longitude;
	private String name;
	private String slogan;
	private String address;
	private String description;
	private String openTime;
	private String closeTime;
	private List<RestaurantWorkTimeModel> restaurantWorkTimeModels;
	private String phone1;
	private String urlSlug;
	private String imageUrl;
	private double rating;
	private int numOfReview;
	private int numOfFavouries;
	private long minPrice;
	private List<UserRequest> userIds;
	private List<CategoryLiteRequest> categoryIds;
	private List<AttributeViewModel> attributeLst;
	private List<PromotionLineitemResponse> promotionLineItems;
	private Long deliveryCost;
	private String estTime;
	private int status;
	private boolean restaurantClosed;

	public Long getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Long deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getEstTime() {
		return estTime;
	}

	public void setEstTime(String estTime) {
		this.estTime = estTime;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUrlSlug() {
		return urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getNumOfReview() {
		return numOfReview;
	}

	public void setNumOfReview(int numOfReview) {
		this.numOfReview = numOfReview;
	}

	public int getNumOfFavouries() {
		return numOfFavouries;
	}

	public void setNumOfFavouries(int numOfFavouries) {
		this.numOfFavouries = numOfFavouries;
	}

	public long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(long minPrice) {
		this.minPrice = minPrice;
	}

	public List<UserRequest> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<UserRequest> userIds) {
		this.userIds = userIds;
	}

	public List<CategoryLiteRequest> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<CategoryLiteRequest> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<AttributeViewModel> getAttributeLst() {
		return attributeLst;
	}

	public void setAttributeLst(List<AttributeViewModel> attributeLst) {
		this.attributeLst = attributeLst;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<PromotionLineitemResponse> getPromotionLineItems() {
		return promotionLineItems;
	}

	public void setPromotionLineItems(List<PromotionLineitemResponse> promotionLineItems) {
		this.promotionLineItems = promotionLineItems;
	}

	public boolean getRestaurantClosed() {
		return restaurantClosed;
	}

	public void setRestaurantClosed(boolean restaurantClosed) {
		this.restaurantClosed = restaurantClosed;
	}

	public List<RestaurantWorkTimeModel> getRestaurantWorkTimeModels() {
		return restaurantWorkTimeModels;
	}

	public void setRestaurantWorkTimeModels(List<RestaurantWorkTimeModel> restaurantWorkTimeModels) {
		this.restaurantWorkTimeModels = restaurantWorkTimeModels;
	}
}
