package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

public class CategoryResponse {
	private Long categoryId;
	private int numberOfRestaurant;
	private String name;
	private int sortOrder;
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
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getNumberOfRestaurant() {
		return numberOfRestaurant;
	}
	public void setNumberOfRestaurant(int numberOfRestaurant) {
		this.numberOfRestaurant = numberOfRestaurant;
	}
}
