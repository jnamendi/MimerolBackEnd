package bmbsoft.orderfoodonline.model;

import javax.validation.constraints.NotNull;

public class FavouriesRequest { 
	@NotNull(message="restaurantid is field required.")
	private Long restaurantId;
	
	@NotNull(message="userId is field required.")
	private Long userId;
	
	private int status;
	 
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
}
