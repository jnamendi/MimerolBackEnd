package bmbsoft.orderfoodonline.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuItemRequest {

	@NotNull(message = "Menuid is field required.")
	private Long menuId;
	private Long price; 
	private Boolean isCombo;
	private Integer sortOrder;
	private Integer status;
	@NotNull(message = "languageLst is field required.")
	private List<LanguageViewModel> languageLst;
	private List<MenuExtraItemRequest> menuExtraLst;
	
	 
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
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
}
