package bmbsoft.orderfoodonline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class RestaurantCategoryViewModel { 
	private Long categoryId; 
	private String categoryName; 
	private int numberOfRest; 
	 
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getNumberOfRest() {
		return numberOfRest;
	}
	public void setNumberOfRest(int numberOfRest) {
		this.numberOfRest = numberOfRest;
	} 
}
