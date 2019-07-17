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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ContentDefinition generated by hbm2java
 */
@Entity
@Table(name = "content_definition")
public class ContentDefinition implements java.io.Serializable {

	private Long contentDepId;
	private String name;
	private Set<Attribute> attributes = new HashSet<Attribute>(0);
	private Set<Menu> menus = new HashSet<Menu>(0);
	private Set<Category> categories = new HashSet<Category>(0);
	private Set<MenuItem> menuItems = new HashSet<MenuItem>(0);
	private Set<Promotion> promotions = new HashSet<Promotion>(0);
	private Set<AttributeGroup> attributeGroups = new HashSet<AttributeGroup>(0);
	private Set<ExtraItem> extraItems = new HashSet<ExtraItem>(0);
	private Set<Restaurant> restaurants = new HashSet<Restaurant>(0);
	private Set<ContentEntry> contentEntries = new HashSet<ContentEntry>(0);
	private Set<MenuExtraItem> menuExtraItems = new HashSet<MenuExtraItem>(0);

	public ContentDefinition() {
	}

	public ContentDefinition(String name) {
		this.name = name;
	}

	public ContentDefinition(String name, Set<Attribute> attributes, Set<Menu> menus, Set<Category> categories,
			Set<MenuItem> menuItems, Set<Promotion> promotions, Set<AttributeGroup> attributeGroups,
			Set<ExtraItem> extraItems, Set<Restaurant> restaurants, Set<ContentEntry> contentEntries,
			Set<MenuExtraItem> menuExtraItems) {
		this.name = name;
		this.attributes = attributes;
		this.menus = menus;
		this.categories = categories;
		this.menuItems = menuItems;
		this.promotions = promotions;
		this.attributeGroups = attributeGroups;
		this.extraItems = extraItems;
		this.restaurants = restaurants;
		this.contentEntries = contentEntries;
		this.menuExtraItems = menuExtraItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "content_dep_id", unique = true, nullable = false)
	public Long getContentDepId() {
		return this.contentDepId;
	}

	public void setContentDepId(Long contentDepId) {
		this.contentDepId = contentDepId;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<Attribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<MenuItem> getMenuItems() {
		return this.menuItems;
	}

	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<Promotion> getPromotions() {
		return this.promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<AttributeGroup> getAttributeGroups() {
		return this.attributeGroups;
	}

	public void setAttributeGroups(Set<AttributeGroup> attributeGroups) {
		this.attributeGroups = attributeGroups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<ExtraItem> getExtraItems() {
		return this.extraItems;
	}

	public void setExtraItems(Set<ExtraItem> extraItems) {
		this.extraItems = extraItems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<Restaurant> getRestaurants() {
		return this.restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<ContentEntry> getContentEntries() {
		return this.contentEntries;
	}

	public void setContentEntries(Set<ContentEntry> contentEntries) {
		this.contentEntries = contentEntries;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentDefinition")
	public Set<MenuExtraItem> getMenuExtraItems() {
		return this.menuExtraItems;
	}

	public void setMenuExtraItems(Set<MenuExtraItem> menuExtraItems) {
		this.menuExtraItems = menuExtraItems;
	}

}