package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bmbsoft.orderfoodonline.model.LanguageViewModel;

public class CategoryReq {
	private Long categoryId;
	private String name;
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}
	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
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
	 
	@NotNull(message = "languageLst is field required")
	private List<LanguageViewModel> languageLst;
	private Integer sortOrder;
	private Integer status;
	
}
