package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * MenuItem generated by hbm2java
 */
@Entity
@Table(name = "menu_item")
public class MenuItem implements java.io.Serializable {

	private Long menuItemId;
	private ContentDefinition contentDefinition;
	private Menu menu;
	private Double price;
	private String picturePath;
	private Boolean isCombo;
	private String urlSlug;
	private Integer sortOrder;
	private Integer isStatus;
	private Boolean availableMonday;
	private Boolean availableTuesday;
	private Boolean availableWednesday;
	private Boolean availableThursday;
	private Boolean availableFriday;
	private Boolean availableSaturday;
	private Boolean availableSunday;
	private Boolean outOfStock;
	private Integer priority;
	private Set<OrderLineItem> orderLineItems = new HashSet<OrderLineItem>(0);
	private Set<MenuExtraItem> menuExtraItems = new HashSet<MenuExtraItem>(0);

	public MenuItem() {
	}

	public MenuItem(ContentDefinition contentDefinition, Menu menu) {
		this.contentDefinition = contentDefinition;
		this.menu = menu;
	}

	public MenuItem(Long menuItemId, ContentDefinition contentDefinition, Menu menu, Double price, String picturePath, Boolean isCombo, String urlSlug, Integer sortOrder, Integer isStatus, Boolean availableMonday, Boolean availableTuesday, Boolean availableWednesday, Boolean availableThursday, Boolean availableFriday, Boolean availableSaturday, Boolean availableSunday, Boolean outOfStock, Integer priority, Set<OrderLineItem> orderLineItems, Set<MenuExtraItem> menuExtraItems) {
		this.menuItemId = menuItemId;
		this.contentDefinition = contentDefinition;
		this.menu = menu;
		this.price = price;
		this.picturePath = picturePath;
		this.isCombo = isCombo;
		this.urlSlug = urlSlug;
		this.sortOrder = sortOrder;
		this.isStatus = isStatus;
		this.availableMonday = availableMonday;
		this.availableTuesday = availableTuesday;
		this.availableWednesday = availableWednesday;
		this.availableThursday = availableThursday;
		this.availableFriday = availableFriday;
		this.availableSaturday = availableSaturday;
		this.availableSunday = availableSunday;
		this.outOfStock = outOfStock;
		this.priority = priority;
		this.orderLineItems = orderLineItems;
		this.menuExtraItems = menuExtraItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "menu_item_id", unique = true, nullable = false)
	public Long getMenuItemId() {
		return this.menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_dep_id", nullable = false)
	public ContentDefinition getContentDefinition() {
		return this.contentDefinition;
	}

	public void setContentDefinition(ContentDefinition contentDefinition) {
		this.contentDefinition = contentDefinition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", nullable = false)
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Column(name = "price", precision = 10, scale = 2)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "picture_path")
	public String getPicturePath() {
		return this.picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Column(name = "is_combo")
	public Boolean getIsCombo() {
		return this.isCombo;
	}

	public void setIsCombo(Boolean isCombo) {
		this.isCombo = isCombo;
	}

	@Column(name = "url_slug")
	public String getUrlSlug() {
		return this.urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

	@Column(name = "sort_order")
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "is_status")
	public Integer getIsStatus() {
		return this.isStatus;
	}

	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}

	@Column(name = "avai_mon")
	public Boolean getAvailableMonday() {
		return availableMonday;
	}

	public void setAvailableMonday(Boolean availableMonday) {
		this.availableMonday = availableMonday;
	}

	@Column(name = "avai_tue")
	public Boolean getAvailableTuesday() {
		return availableTuesday;
	}

	public void setAvailableTuesday(Boolean availableTuesday) {
		this.availableTuesday = availableTuesday;
	}

	@Column(name = "avai_wed")
	public Boolean getAvailableWednesday() {
		return availableWednesday;
	}

	public void setAvailableWednesday(Boolean availableWednesday) {
		this.availableWednesday = availableWednesday;
	}

	@Column(name = "avai_thu")
	public Boolean getAvailableThursday() {
		return availableThursday;
	}

	public void setAvailableThursday(Boolean availableThursday) {
		this.availableThursday = availableThursday;
	}

	@Column(name = "avai_fri")
	public Boolean getAvailableFriday() {
		return availableFriday;
	}

	public void setAvailableFriday(Boolean availableFriday) {
		this.availableFriday = availableFriday;
	}

	@Column(name = "avai_sat")
	public Boolean getAvailableSaturday() {
		return availableSaturday;
	}

	public void setAvailableSaturday(Boolean availableSaturday) {
		this.availableSaturday = availableSaturday;
	}

	@Column(name = "avai_sun")
	public Boolean getAvailableSunday() {
		return availableSunday;
	}

	public void setAvailableSunday(Boolean availableSunday) {
		this.availableSunday = availableSunday;
	}

	@Column(name = "out_of_stock")
	public Boolean getOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(Boolean outOfStock) {
		this.outOfStock = outOfStock;
	}

	@Column(name = "priority")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuItem")
	public Set<OrderLineItem> getOrderLineItems() {
		return this.orderLineItems;
	}

	public void setOrderLineItems(Set<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuItem")
	public Set<MenuExtraItem> getMenuExtraItems() {
		return this.menuExtraItems;
	}

	public void setMenuExtraItems(Set<MenuExtraItem> menuExtraItems) {
		this.menuExtraItems = menuExtraItems;
	}

}
