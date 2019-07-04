package bmbsoft.orderfoodonline.model.shared;

public class OrderExtraItemRequest {
	private Long price;
	private Long unitPrice;
	private Long extraItemId;
	private Long menuItemId;
	
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Long unitPrice) {
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
