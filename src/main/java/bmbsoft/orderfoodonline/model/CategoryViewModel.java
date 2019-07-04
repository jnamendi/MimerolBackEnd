package bmbsoft.orderfoodonline.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class CategoryViewModel {
	private Long categoryId;
	private String categoryName; 
	private List<LanguageViewModel> languageLst; 
	private Integer sortOrder;
	private Integer status;
	private String imageUrl;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	} 
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	 
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	} 
}
