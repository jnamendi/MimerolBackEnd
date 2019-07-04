package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Promotion generated by hbm2java
 */
@Entity
@Table(name = "promotion", catalog = "ofo")
public class Promotion implements java.io.Serializable {

	private Long promotionId;
	private ContentDefinition contentDefinition;
	private String name;
	private String code;
	private Date startDate;
	private Date endDate;
	private Integer value;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Integer valueType;
	private Long minOrder;
	private Integer status;
	private Set<PromotionLineitem> promotionLineitems = new HashSet<PromotionLineitem>(0);

	public Promotion() {
	}

	public Promotion(ContentDefinition contentDefinition, String name, String code, Date startDate, Date endDate,
			Integer value, Date createdDate, String createdBy, Date modifiedDate, String modifiedBy, Integer valueType,
			Long minOrder, Integer status, Set<PromotionLineitem> promotionLineitems) {
		this.contentDefinition = contentDefinition;
		this.name = name;
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		this.value = value;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.valueType = valueType;
		this.minOrder = minOrder;
		this.status = status;
		this.promotionLineitems = promotionLineitems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "promotion_id", unique = true, nullable = false)
	public Long getPromotionId() {
		return this.promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_dep_id")
	public ContentDefinition getContentDefinition() {
		return this.contentDefinition;
	}

	public void setContentDefinition(ContentDefinition contentDefinition) {
		this.contentDefinition = contentDefinition;
	}

	@Column(name = "name", length = 200)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "value")
	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
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

	@Column(name = "value_type")
	public Integer getValueType() {
		return this.valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	@Column(name = "min_order", precision = 10, scale = 0)
	public Long getMinOrder() {
		return this.minOrder;
	}

	public void setMinOrder(Long minOrder) {
		this.minOrder = minOrder;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
	public Set<PromotionLineitem> getPromotionLineitems() {
		return this.promotionLineitems;
	}

	public void setPromotionLineitems(Set<PromotionLineitem> promotionLineitems) {
		this.promotionLineitems = promotionLineitems;
	}

}
