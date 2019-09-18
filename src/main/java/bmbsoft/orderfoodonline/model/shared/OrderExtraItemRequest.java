package bmbsoft.orderfoodonline.model.shared;

public class OrderExtraItemRequest {
	private Double price;
	private Double unitPrice;
	private Long extraItemId;
	private Long menuItemId;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Long getExtraItemId() {
		return extraItemId;
	}
	public void setExtraItemId(Long extraItemId) {
		this.extraItemId = extraItemId;
	}
	public Long getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	} 
}
