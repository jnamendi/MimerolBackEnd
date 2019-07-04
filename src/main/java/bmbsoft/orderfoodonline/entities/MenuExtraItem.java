package bmbsoft.orderfoodonline.entities;
// Generated Aug 1, 2018 11:13:58 AM by Hibernate Tools 5.2.10.Final

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
 * MenuExtraItem generated by hbm2java
 */
@Entity
@Table(name = "menu_extra_item", catalog = "ofo_prod")
public class MenuExtraItem implements java.io.Serializable {

	private Long menuExtraItemId;
	private ContentDefinition contentDefinition;
	private MenuItem menuItem;
	private Integer type;
	private Set<ExtraItem> extraItems = new HashSet<ExtraItem>(0);

	public MenuExtraItem() {
	}

	public MenuExtraItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public MenuExtraItem(ContentDefinition contentDefinition, MenuItem menuItem, Integer type,
			Set<ExtraItem> extraItems) {
		this.contentDefinition = contentDefinition;
		this.menuItem = menuItem;
		this.type = type;
		this.extraItems = extraItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "menu_extra_item_id", unique = true, nullable = false)
	public Long getMenuExtraItemId() {
		return this.menuExtraItemId;
	}

	public void setMenuExtraItemId(Long menuExtraItemId) {
		this.menuExtraItemId = menuExtraItemId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_dep_id")
	public ContentDefinition getContentDefinition() {
		return this.contentDefinition;
	}

	public void setContentDefinition(ContentDefinition contentDefinition) {
		this.contentDefinition = contentDefinition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_item_id", nullable = false)
	public MenuItem getMenuItem() {
		return this.menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuExtraItem")
	public Set<ExtraItem> getExtraItems() {
		return this.extraItems;
	}

	public void setExtraItems(Set<ExtraItem> extraItems) {
		this.extraItems = extraItems;
	}

}
