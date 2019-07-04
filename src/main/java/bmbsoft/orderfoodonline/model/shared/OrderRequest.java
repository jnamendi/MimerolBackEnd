package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import bmbsoft.orderfoodonline.model.OrderLineItemRequest; 

public class OrderRequest {
	private Long orderId;
	private Long restaurantId;
	private Long userId;
	private long taxTotal; 
	private String currencyCode;
	
	private List<OrderLineItemRequest> orderLineItemsRequest;
	
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
 
	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public long getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(long taxTotal) {
		this.taxTotal = taxTotal;
	}

	public List<OrderLineItemRequest> getOrderLineItemsRequest() {
		return orderLineItemsRequest;
	}

	public void setOrderLineItemsRequest(List<OrderLineItemRequest> orderLineItemsRequest) {
		this.orderLineItemsRequest = orderLineItemsRequest;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	private Long totalPrice;
}
