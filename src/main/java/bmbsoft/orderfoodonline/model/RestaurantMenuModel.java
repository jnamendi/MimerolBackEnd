package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class RestaurantMenuModel {

	private RestaurantViewModel restaurant;
	private List<MenuViewModel> menus;

	public RestaurantViewModel getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantViewModel restaurant) {
		this.restaurant = restaurant;
	}

	public List<MenuViewModel> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuViewModel> menus) {
		this.menus = menus;
	}
}
