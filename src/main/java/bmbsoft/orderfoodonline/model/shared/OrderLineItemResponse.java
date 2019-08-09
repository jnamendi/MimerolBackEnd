package bmbsoft.orderfoodonline.model.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bmbsoft.orderfoodonline.entities.MenuExtraItem;
import bmbsoft.orderfoodonline.entities.MenuItem;
import bmbsoft.orderfoodonline.entities.Order;

public class OrderLineItemResponse {
	private Long orderLineItemId;  
	private Long unitPrice;
	private Integer quantity;  
	private String menuItemName;
//	private List<MenuExtraItemLiteResponse> menuExraItems = new ArrayList<>();
//
//	public List<MenuExtraItemLiteResponse> getMenuExraItems() {
//		return menuExraItems;
//	}
//	public void setMenuExraItems(List<MenuExtraItemLiteResponse> menuExraItems) {
//		this.menuExraItems = menuExraItems;
//	}
	public Long getOrderLineItemId() {
		return orderLineItemId;
	}
	public void setOrderLineItemId(Long orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}
	public Long getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getMenuItemName() {
		return menuItemName;
	}
	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}
	public Long getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(Long discountTotal) {
		this.discountTotal = discountTotal;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	private Long discountTotal;
	private Long total;
	private Date createdDate;
}
