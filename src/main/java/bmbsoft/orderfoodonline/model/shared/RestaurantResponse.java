package bmbsoft.orderfoodonline.model.shared;

import bmbsoft.orderfoodonline.model.RestaurantCategoryViewModel;
import bmbsoft.orderfoodonline.model.RestaurantViewModel;

import java.util.List;

public class RestaurantResponse {
	private List<RestaurantViewModel> restaurants;
	private List<RestaurantCategoryViewModel> categories;
	private Object rankPrice;
	
	public Object getRankPrice() {
		return rankPrice;
	}

	public void setRankPrice(Object rankPrice) {
		this.rankPrice = rankPrice;
	}
	
	public List<RestaurantViewModel> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(List<RestaurantViewModel> restaurants) {
		this.restaurants = restaurants;
	}
	public List<RestaurantCategoryViewModel> getCategories() {
		return categories;
	}
	public void setCategories(List<RestaurantCategoryViewModel> categories) {
		this.categories = categories;
	}
}
