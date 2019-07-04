package bmbsoft.orderfoodonline.model;

import javax.validation.constraints.NotNull;

public class PromotionLineItemRequest {
	@NotNull(message = "restaurantId is field required.")
	private Long restaurantId;
	@NotNull(message = "promotionId is field required.")
	private Long promotionId;
	@NotNull(message = "code is field required.")
	private String code;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
