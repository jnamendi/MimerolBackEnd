package bmbsoft.orderfoodonline.model.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

@JsonInclude(value=Include.NON_NULL)
public class MenuResponse {
	private List<MenuLiteResponse> mennu; 
	private List<MenuItemLiteResponse> menuItems;

	public List<MenuLiteResponse> getMennu() {
		return mennu;
	}

	public void setMennu(List<MenuLiteResponse> mennu) {
		this.mennu = mennu;
	}

	public List<MenuItemLiteResponse> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItemLiteResponse> menuItems) {
		this.menuItems = menuItems;
	}
}
