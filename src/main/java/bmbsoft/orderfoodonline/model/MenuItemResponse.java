package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class MenuItemResponse {

	private Long menuItemId;
	private Long menuId; // Menu 
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	private Long restaurantId;
	private String restaurantName;
	private MenuViewModel menu;
	private Long price;
	private String imageUrl;
	private Boolean isCombo;
	private Integer sortOrder;
	private Integer status;
	private List<LanguageViewModel> languageLst;
	private List<MenuExtraItemResponse> menuExtraLst;

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public MenuViewModel getMenu() {
		return menu;
	}

	public void setMenu(MenuViewModel menu) {
		this.menu = menu;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsCombo() {
		return isCombo;
	}

	public void setIsCombo(Boolean isCombo) {
		this.isCombo = isCombo;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public List<MenuExtraItemResponse> getMenuExtraLst() {
		return menuExtraLst;
	}

	public void setMenuExtraLst(List<MenuExtraItemResponse> menuExtraLst) {
		this.menuExtraLst = menuExtraLst;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
}
