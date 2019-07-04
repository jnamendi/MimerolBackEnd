package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import javax.validation.constraints.NotNull;

import bmbsoft.orderfoodonline.model.OrderLineItemRequest;

public class OrderItem {
	@NotNull(message = "totalSubPrice is required")
	private Long totalSubPrice;
	@NotNull(message = "taxTotal is required")
	private Long taxTotal;
	@NotNull(message = "totalPrice is required")
	private Long totalPrice;

	public Long getTotalSubPrice() {
		return totalSubPrice;
	}

	public void setTotalSubPrice(Long totalSubPrice) {
		this.totalSubPrice = totalSubPrice;
	}

	public Long getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Long taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<MenuItemLiteResponse> getOrderItemsRequest() {
		return OrderItemsRequest;
	}

	public void setOrderItemsRequest(List<MenuItemLiteResponse> orderItemsRequest) {
		OrderItemsRequest = orderItemsRequest;
	}

	@NotNull(message = "OrderItemsRequest is required")
	private List<MenuItemLiteResponse> OrderItemsRequest;
}
