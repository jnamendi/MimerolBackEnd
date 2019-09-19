package bmbsoft.orderfoodonline.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuItemRequest {

	@NotNull(message = "Menuid is field required.")
	private Long menuId;
	private Double price;
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
	@NotNull(message = "languageLst is field required.")
	private List<LanguageViewModel> languageLst;
	private List<MenuExtraItemRequest> menuExtraLst;
	
	 
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<MenuExtraItemRequest> getMenuExtraLst() {
		return menuExtraLst;
	}

	public void setMenuExtraLst(List<MenuExtraItemRequest> menuExtra) {
		this.menuExtraLst = menuExtra;
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
}
