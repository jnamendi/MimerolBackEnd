package bmbsoft.orderfoodonline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bmbsoft.orderfoodonline.entities.Promotion;
import bmbsoft.orderfoodonline.entities.PromotionLineitem;
import bmbsoft.orderfoodonline.model.shared.PromotionLineitemResponse;

@JsonInclude(value = Include.NON_NULL)
public class PromotionResponse {

	private Long promotionId;
	private String name; 
	private Date startDate;
	private Date endDate;
	private Integer value;
	private Boolean isApplyAll;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private int status;
	private Long minOrder; 
	// private Set<PromotionLineitem> promotionLineitems = new
	// HashSet<PromotionLineitem>(0);
	private List<PromotionLineitemResponse> promotionLineitem;
	 
	private String code;
	private String restaurantName;
	private Long restaurantId;
	
	private Promotion promotion;

	public List<PromotionLineitemResponse> getPromotionLineitem() {
		return promotionLineitem;
	}

	public void setPromotionLineitem(List<PromotionLineitemResponse> promotionLineitem) {
		this.promotionLineitem = promotionLineitem;
	}

	private List<LanguageViewModel> languageLst;

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Boolean getIsApplyAll() {
		return isApplyAll;
	}

	public void setIsApplyAll(Boolean isApplyAll) {
		this.isApplyAll = isApplyAll;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getMinOrder() {
		return minOrder;
	}

	public void setMinOrder(Long minOrder) {
		this.minOrder = minOrder;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
}
