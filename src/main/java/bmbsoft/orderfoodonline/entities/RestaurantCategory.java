package bmbsoft.orderfoodonline.entities;
// Generated Aug 1, 2018 11:13:58 AM by Hibernate Tools 5.2.10.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RestaurantCategory generated by hbm2java
 */
@Entity
@Table(name = "restaurant_category", catalog = "ofo_prod")
public class RestaurantCategory implements java.io.Serializable {

	private Long resCategoryId;
	private Category category;
	private Restaurant restaurant;

	public RestaurantCategory() {
	}

	public RestaurantCategory(Category category, Restaurant restaurant) {
		this.category = category;
		this.restaurant = restaurant;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "res_category_id", unique = true, nullable = false)
	public Long getResCategoryId() {
		return this.resCategoryId;
	}

	public void setResCategoryId(Long resCategoryId) {
		this.resCategoryId = resCategoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", nullable = false)
	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
