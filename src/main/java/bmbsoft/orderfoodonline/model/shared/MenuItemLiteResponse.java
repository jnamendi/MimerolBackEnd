package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class MenuItemLiteResponse {
	@NotNull(message = "menuItemId is required")
	private Long menuItemId;
	private Long menuId;
	private String menuItemName;
	private String urlSlug;
	private Long priceOriginal;
	private String priceRateDisplay;
	private Long priceRate;
	private String urlImge;
	private String desc;
	private Boolean available;
	private Boolean outOfStock;
	private float currencyRate;
	private String symbolLeft;
	@NotNull(message = "quantity is required")
	private int quantity;
	@NotNull(message = "totalPrice is required")
	private Long totalPrice;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	private List<MenuExtraItemLiteResponse> menuExraItems;
	
	public Long getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}
	public String getMenuItemName() {
		return menuItemName;
	}
	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}
	public String getUrlSlug() {
		return urlSlug;
	}
	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}
	public double getPriceOriginal() {
		return priceOriginal;
	}
	public void setPriceOriginal(Long priceOriginal) {
		this.priceOriginal = priceOriginal;
	}
	public String getPriceRateDisplay() {
		return priceRateDisplay;
	}
	public void setPriceRateDisplay(String priceRateDisplay) {
		this.priceRateDisplay = priceRateDisplay;
	}
	public double getPriceRate() {
		return priceRate;
	}
	public void setPriceRate(Long priceRate) {
		this.priceRate = priceRate;
	}
	public String getUrlImge() {
		return urlImge;
	}
	public void setUrlImge(String urlImge) {
		this.urlImge = urlImge;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public float getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(float currencyRate) {
		this.currencyRate = currencyRate;
	}
	public String getSymbolLeft() {
		return symbolLeft;
	}
	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public List<MenuExtraItemLiteResponse> getMenuExraItems() {
		return menuExraItems;
	}
	public void setMenuExraItems(List<MenuExtraItemLiteResponse> menuExraItems) {
		this.menuExraItems = menuExraItems;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Boolean getOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(Boolean outOfStock) {
		this.outOfStock = outOfStock;
	}
}
