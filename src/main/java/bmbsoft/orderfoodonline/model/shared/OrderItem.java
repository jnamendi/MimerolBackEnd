package bmbsoft.orderfoodonline.model.shared;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderItem {
	@NotNull(message = "totalSubPrice is required")
	private Double totalSubPrice;
	@NotNull(message = "taxTotal is required")
	private Double taxTotal;
	@NotNull(message = "totalPrice is required")
	private Double totalPrice;
	@NotNull(message = "OrderItemsRequest is required")
	private List<MenuItemLiteResponse> OrderItemsRequest;

	public Double getTotalSubPrice() {
		return totalSubPrice;
	}

	public void setTotalSubPrice(Double totalSubPrice) {
		this.totalSubPrice = totalSubPrice;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<MenuItemLiteResponse> getOrderItemsRequest() {
		return OrderItemsRequest;
	}

	public void setOrderItemsRequest(List<MenuItemLiteResponse> orderItemsRequest) {
		OrderItemsRequest = orderItemsRequest;
	}

}
