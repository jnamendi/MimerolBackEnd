package bmbsoft.orderfoodonline.model;

import java.util.List;

import bmbsoft.orderfoodonline.model.shared.OrderExtraItemRequest;

public class OrderLineItemRequest { 
	private Long menuItemId; 
	 
	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
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

	private long price; // gia original
	private long discount; // tong gia giamr
	private long totalPrice;  // tong gia = 
	private int quantity;
	private int status;
	private String menuItemName;
	
	private List<OrderExtraItemRequest> extraItemRequest;
	
}
