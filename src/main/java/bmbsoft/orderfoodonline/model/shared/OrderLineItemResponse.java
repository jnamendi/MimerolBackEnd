package bmbsoft.orderfoodonline.model.shared;

import java.util.Date;

public class OrderLineItemResponse {
	private Long orderLineItemId;  
	private Double unitPrice;
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
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
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
	public Double getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(Double discountTotal) {
		this.discountTotal = discountTotal;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	private Double discountTotal;
	private Double total;
	private Date createdDate;
}
