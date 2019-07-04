package bmbsoft.orderfoodonline.model.shared;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class RestaurantCommentRequest {
	  
	private String description;
 
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getResCommentId() {
		return resCommentId;
	}
	public void setResCommentId(Long resCommentId) {
		this.resCommentId = resCommentId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	private Long resCommentId;
	private String title;
	private Double starPerMark;
	private Double starQuality;
	private Double starShip; 
	@NotNull
	private Long restaurantId;
	@NotNull
	private Long userId; 
	private Integer status;
}
