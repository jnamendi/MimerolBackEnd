package bmbsoft.orderfoodonline.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class RestaurantCommentModel {

	private String userName;
	private String fullName;
	private String addressLine;
	private String restaurantName;
	private String description;

	private Long resCommentId;
	private String title;
	private Double starPerMark;
	private Double starQuality;
	private Double starShip;
	private Boolean isLove;
	private Long restaurantId;
	private Long userId;
	private Date createdDate;
	private int status;
	
	public RestaurantCommentModel() {
		super();
	}

	public RestaurantCommentModel(Long resCommentId, String userName, String fullName, String addressLine1, String desc,
			String title, Double starPerMark, Double starQuantity, Double starShip, Boolean isLove, Long restaurantId,
			Long userId, Date createdDate) {
		super();
		this.resCommentId = resCommentId;
		this.userName = userName;
		this.fullName = fullName;
		this.addressLine = addressLine1;
		this.description = desc;
		this.title = title;
		this.starPerMark = starPerMark;
		this.starQuality = starQuantity;
		this.starShip = starShip;
		this.isLove = isLove;
		this.restaurantId = restaurantId;
		this.userId = userId;
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddressLine1() {
		return addressLine;
	}

	public void setAddressLine(String addressLine1) {
		this.addressLine = addressLine1;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getStarPerMark() {
		return starPerMark;
	}

	public void setStarPerMark(Double starPerMark) {
		this.starPerMark = starPerMark;
	}

	public Double getStarQuality() {
		return starQuality;
	}

	public void setStarQuality(Double starQuality) {
		this.starQuality = starQuality;
	}

	public Double getStarShip() {
		return starShip;
	}

	public void setStarShip(Double starShip) {
		this.starShip = starShip;
	}

	public Boolean getIsLove() {
		return isLove;
	}

	public void setIsLove(Boolean isLove) {
		this.isLove = isLove;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getResCommentId() {
		return resCommentId;
	}

	public void setResCommentId(Long resCommentId) {
		this.resCommentId = resCommentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

}
