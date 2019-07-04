package bmbsoft.orderfoodonline.model.shared;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class RatingRequest {
	@NotNull
	private Long restaurantId;
	@NotNull
	private Long userId;
	@NotNull
	private double quality;

	@NotNull
	private double delivery;

	@NotNull
	private String comment;
	private int status;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

	public double getDelivery() {
		return delivery;
	}

	public void setDelivery(double delivery) {
		this.delivery = delivery;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
