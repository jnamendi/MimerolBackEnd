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
	private Double priceOriginal;
	private String priceRateDisplay;
	private Double priceRate;
	private String urlImge;
	private String desc;
	private Boolean available;
	private Boolean outOfStock;
	private Double currencyRate;
	private String symbolLeft;
	@NotNull(message = "quantity is required")
	private int quantity;
	@NotNull(message = "totalPrice is required")
	private Double totalPrice;

	private Integer priority;
	
	private List<MenuExtraItemLiteResponse> menuExraItems;

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
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

	public Double getPriceOriginal() {
		return priceOriginal;
	}

	public void setPriceOriginal(Double priceOriginal) {
		this.priceOriginal = priceOriginal;
	}

	public String getPriceRateDisplay() {
		return priceRateDisplay;
	}

	public void setPriceRateDisplay(String priceRateDisplay) {
		this.priceRateDisplay = priceRateDisplay;
	}

	public Double getPriceRate() {
		return priceRate;
	}

	public void setPriceRate(Double priceRate) {
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

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getSymbolLeft() {
		return symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<MenuExtraItemLiteResponse> getMenuExraItems() {
		return menuExraItems;
	}

	public void setMenuExraItems(List<MenuExtraItemLiteResponse> menuExraItems) {
		this.menuExraItems = menuExraItems;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
