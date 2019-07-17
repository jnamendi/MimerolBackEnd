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
 * Rating generated by hbm2java
 */
@Entity
@Table(name = "rating")
public class Rating implements java.io.Serializable {

	private Long ratingId;
	private Restaurant restaurant;
	private User user;
	private Double quality;
	private Double delivery;
	private String ratingComment;
	private Date createdDate;
	private String createdBy;
	private Integer isStatus;
	private Date modifiedDate;
	private String modifiedBy;

	public Rating() {
	}

	public Rating(Restaurant restaurant, User user, Double quality, Double delivery, String ratingComment,
			Date createdDate, String createdBy, Integer isStatus, Date modifiedDate, String modifiedBy) {
		this.restaurant = restaurant;
		this.user = user;
		this.quality = quality;
		this.delivery = delivery;
		this.ratingComment = ratingComment;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.isStatus = isStatus;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "rating_id", unique = true, nullable = false)
	public Long getRatingId() {
		return this.ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "quality", precision = 22, scale = 0)
	public Double getQuality() {
		return this.quality;
	}

	public void setQuality(Double quality) {
		this.quality = quality;
	}

	@Column(name = "delivery", precision = 22, scale = 0)
	public Double getDelivery() {
		return this.delivery;
	}

	public void setDelivery(Double delivery) {
		this.delivery = delivery;
	}

	@Column(name = "rating_comment", length = 65535)
	public String getRatingComment() {
		return this.ratingComment;
	}

	public void setRatingComment(String ratingComment) {
		this.ratingComment = ratingComment;
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

	@Column(name = "is_status")
	public Integer getIsStatus() {
		return this.isStatus;
	}

	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by")
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}