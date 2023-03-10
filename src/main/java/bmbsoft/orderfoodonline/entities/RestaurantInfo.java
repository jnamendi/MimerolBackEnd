package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RestaurantInfo generated by hbm2java
 */
@Entity
@Table(name = "restaurant_info")
public class RestaurantInfo implements java.io.Serializable {

	private Long restaurantInfoId;
	private Restaurant restaurant;
	private String timeShip;
	private String payment;
	private String address;
	private String shipArea;
	private Date createdDate;
	private String createdBy;

	public RestaurantInfo() {
	}

	public RestaurantInfo(Restaurant restaurant, String timeShip, String payment, String address, String shipArea,
			Date createdDate, String createdBy) {
		this.restaurant = restaurant;
		this.timeShip = timeShip;
		this.payment = payment;
		this.address = address;
		this.shipArea = shipArea;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "restaurant_info_id", unique = true, nullable = false)
	public Long getRestaurantInfoId() {
		return this.restaurantInfoId;
	}

	public void setRestaurantInfoId(Long restaurantInfoId) {
		this.restaurantInfoId = restaurantInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurent_id")
	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Column(name = "time_ship", length = 65535)
	public String getTimeShip() {
		return this.timeShip;
	}

	public void setTimeShip(String timeShip) {
		this.timeShip = timeShip;
	}

	@Column(name = "payment", length = 65535)
	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Column(name = "address", length = 65535)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ship_area", length = 65535)
	public String getShipArea() {
		return this.shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "created_by")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
