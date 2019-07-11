package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FoodType generated by hbm2java
 */
@Entity
@Table(name = "food_type")
public class FoodType implements java.io.Serializable {

	private long foodTypeId;
	private String name;
	private String code;
	private String image;
	private String desc;
	private Boolean isSpecial;
	private Boolean isHot;
	private Boolean isHome;
	private Integer status;
	private String createdBy;
	private Date createdDate;
	private Integer sortOrder;
	private String urlSlug;

	public FoodType() {
	}

	public FoodType(long foodTypeId) {
		this.foodTypeId = foodTypeId;
	}

	public FoodType(long foodTypeId, String name, String code, String image, String desc, Boolean isSpecial,
			Boolean isHot, Boolean isHome, Integer status, String createdBy, Date createdDate, Integer sortOrder,
			String urlSlug) {
		this.foodTypeId = foodTypeId;
		this.name = name;
		this.code = code;
		this.image = image;
		this.desc = desc;
		this.isSpecial = isSpecial;
		this.isHot = isHot;
		this.isHome = isHome;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.sortOrder = sortOrder;
		this.urlSlug = urlSlug;
	}

	@Id

	@Column(name = "food_type_id", unique = true, nullable = false)
	public long getFoodTypeId() {
		return this.foodTypeId;
	}

	public void setFoodTypeId(long foodTypeId) {
		this.foodTypeId = foodTypeId;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "image")
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "desc")
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "is_special")
	public Boolean getIsSpecial() {
		return this.isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	@Column(name = "is_hot")
	public Boolean getIsHot() {
		return this.isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Column(name = "is_home")
	public Boolean getIsHome() {
		return this.isHome;
	}

	public void setIsHome(Boolean isHome) {
		this.isHome = isHome;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "created_by")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "sort_order")
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "url_slug")
	public String getUrlSlug() {
		return this.urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

}
