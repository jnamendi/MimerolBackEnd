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
	private String menuName;
	private Double price;
	private String imageUrl;
	private Boolean isCombo;
	private Integer sortOrder;
	private Integer status;
	private Boolean availableMonday;
	private Boolean availableTuesday;
	private Boolean availableWednesday;
	private Boolean availableThursday;
	private Boolean availableFriday;
	private Boolean availableSaturday;
	private Boolean availableSunday;
	private Boolean outOfStock;
	private Integer priority;
	private List<LanguageViewModel> languageLst;
	private List<MenuExtraItemResponse> menuExtraLst;

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getCombo() {
		return isCombo;
	}

	public void setCombo(Boolean combo) {
		isCombo = combo;
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

	public Boolean getAvailableMonday() {
		return availableMonday;
	}

	public void setAvailableMonday(Boolean availableMonday) {
		this.availableMonday = availableMonday;
	}

	public Boolean getAvailableTuesday() {
		return availableTuesday;
	}

	public void setAvailableTuesday(Boolean availableTuesday) {
		this.availableTuesday = availableTuesday;
	}

	public Boolean getAvailableWednesday() {
		return availableWednesday;
	}

	public void setAvailableWednesday(Boolean availableWednesday) {
		this.availableWednesday = availableWednesday;
	}

	public Boolean getAvailableThursday() {
		return availableThursday;
	}

	public void setAvailableThursday(Boolean availableThursday) {
		this.availableThursday = availableThursday;
	}

	public Boolean getAvailableFriday() {
		return availableFriday;
	}

	public void setAvailableFriday(Boolean availableFriday) {
		this.availableFriday = availableFriday;
	}

	public Boolean getAvailableSaturday() {
		return availableSaturday;
	}

	public void setAvailableSaturday(Boolean availableSaturday) {
		this.availableSaturday = availableSaturday;
	}

	public Boolean getAvailableSunday() {
		return availableSunday;
	}

	public void setAvailableSunday(Boolean availableSunday) {
		this.availableSunday = availableSunday;
	}

	public Boolean getOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(Boolean outOfStock) {
		this.outOfStock = outOfStock;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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
}
