package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class CategoryRestaurantModel {
 
	private String title;
	private String urlSlug;
	private Integer status;
	private Long mediaId;
	private String imgUrl;
	private List<LanguageViewModel> languageLst;
	private List<RestaurantViewModel> listRestaurant;	
	private List<AttributeViewModel> attributeLst;
	private List<CategoryViewModel> categoryLst;
//	private AddressSearchModel textSearch;
 
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlSlug() {
		return urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RestaurantViewModel> getListRestaurant() {
		return listRestaurant;
	}

	public void setListRestaurant(List<RestaurantViewModel> listRestaurant) {
		this.listRestaurant = listRestaurant;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public List<AttributeViewModel> getAttributeLst() {
		return attributeLst;
	}

	public void setAttributeLst(List<AttributeViewModel> attributeLst) {
		this.attributeLst = attributeLst;
	}

//	public AddressSearchModel getTextSearch() {
//		return textSearch;
//	}

//	public void setTextSearch(AddressSearchModel textSearch) {
//		this.textSearch = textSearch;
//	}

	public CategoryRestaurantModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<CategoryViewModel> getCategoryLst() {
		return categoryLst;
	}

	public void setCategoryLst(List<CategoryViewModel> categoryLst) {
		this.categoryLst = categoryLst;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
