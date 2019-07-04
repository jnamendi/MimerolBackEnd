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
 * RestaurantComment generated by hbm2java
 */
@Entity
@Table(name = "restaurant_comment", catalog = "ofo")
public class RestaurantComment implements java.io.Serializable {

	private Long resCommentId;
	private Restaurant restaurant;
	private User user;
	private Double starPerMark;
	private Double starQuality;
	private Double starShip;
	private Date createdDate;
	private String createdBy;
	private String description;
	private String title;
	private Integer status;
	private Date modifiedDate;
	private String modifiedBy;

	public RestaurantComment() {
	}

	public RestaurantComment(Restaurant restaurant, User user, Double starPerMark, Double starQuality, Double starShip,
			Date createdDate, String createdBy, String description, String title, Integer status, Date modifiedDate,
			String modifiedBy) {
		this.restaurant = restaurant;
		this.user = user;
		this.starPerMark = starPerMark;
		this.starQuality = starQuality;
		this.starShip = starShip;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.description = description;
		this.title = title;
		this.status = status;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "res_comment_id", unique = true, nullable = false)
	public Long getResCommentId() {
		return this.resCommentId;
	}

	public void setResCommentId(Long resCommentId) {
		this.resCommentId = resCommentId;
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

	@Column(name = "star_per_mark", precision = 22, scale = 0)
	public Double getStarPerMark() {
		return this.starPerMark;
	}

	public void setStarPerMark(Double starPerMark) {
		this.starPerMark = starPerMark;
	}

	@Column(name = "star_quality", precision = 22, scale = 0)
	public Double getStarQuality() {
		return this.starQuality;
	}

	public void setStarQuality(Double starQuality) {
		this.starQuality = starQuality;
	}

	@Column(name = "star_ship", precision = 22, scale = 0)
	public Double getStarShip() {
		return this.starShip;
	}

	public void setStarShip(Double starShip) {
		this.starShip = starShip;
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

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
