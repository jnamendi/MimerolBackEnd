package bmbsoft.orderfoodonline.model;

import java.util.List;

import bmbsoft.orderfoodonline.model.shared.OrderExtraItemRequest;

public class OrderLineItemRequest { 
	private Long menuItemId; 
	private Double price; // gia original
	private Double discount; // tong gia giamr
	private Double totalPrice;  // tong gia =
	private int quantity;
	private int status;
	private String menuItemName;
	
	private List<OrderExtraItemRequest> extraItemRequest;

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public List<OrderExtraItemRequest> getExtraItemRequest() {
		return extraItemRequest;
	}

	public void setExtraItemRequest(List<OrderExtraItemRequest> extraItemRequest) {
		this.extraItemRequest = extraItemRequest;
	}
}
