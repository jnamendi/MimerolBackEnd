package bmbsoft.orderfoodonline.model.shared;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RestaurantLiteResponse {
	private Long restaurantId;
	private Long mediaId;
	private String name;
	private String slogan;
	private String address;  
	private String description;
	private String openTime;
	private String closeTime;
	private String phone1;  
	private String urlSlug;
	private String imageUrl;
	private Long deliveryCost;
	private String estTime;
	
	public Long getDeliveryCost() {
		return deliveryCost;
	}
	public void setDeliveryCost(Long deliveryCost) {
		this.deliveryCost = deliveryCost;
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
	public long getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(long minPrice) {
		this.minPrice = minPrice;
	}
	private double rating;
	private int numOfReview;
	private int numOfFavouries; 
	private long minPrice;
}
