package bmbsoft.orderfoodonline.entities;
// Generated May 12, 2018 1:32:31 AM by Hibernate Tools 5.2.8.Final

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
 * DistrictRestaurant generated by hbm2java
 */
@Entity
@Table(name = "district_restaurant")
public class DistrictRestaurant implements java.io.Serializable {

	private Long districtResId;
	private District district;
	private Restaurant restaurant;

	public DistrictRestaurant() {
	}

	public DistrictRestaurant(District district, Restaurant restaurant) {
		this.district = district;
		this.restaurant = restaurant;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "district_res_id", unique = true, nullable = false)
	public Long getDistrictResId() {
		return this.districtResId;
	}

	public void setDistrictResId(Long districtResId) {
		this.districtResId = districtResId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id", nullable = false)
	public District getDistrict() {
		return this.district;
	}

	public void setDistrict(District district) {
		this.district = district;
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
