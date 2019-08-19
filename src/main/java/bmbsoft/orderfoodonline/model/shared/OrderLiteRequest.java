package bmbsoft.orderfoodonline.model.shared;

import bmbsoft.orderfoodonline.model.ListMenuItem;

import java.util.List;

public class OrderLiteRequest {
	private Long orderId;
	private String orderCode;
	private int status;
	private String languageCode;
	private List<MenuItemLiteResponse> orderLineItems;

	public List<MenuItemLiteResponse> getOrderLineItems() {
		return orderLineItems;
	}
	public void setOrderLineItems(List<MenuItemLiteResponse> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
}
