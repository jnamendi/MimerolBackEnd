package bmbsoft.orderfoodonline.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PromotionRequest {
	@NotNull(message="startDate is field required.")
	private Date startDate;
	@NotNull(message="endDate is field required.")
	private Date endDate;
	private Long minOrder;
	@NotNull(message="value is field required.")
	private Integer value;
	private int status;
	private Boolean isApplyAll;
	private List<LanguageViewModel> languageLst;
	@NotNull(message="restaurantId is field required.")
	private long restaurantId;
	@NotNull(message="code is field required.")
	private String code;
	 
	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
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

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public Long getMinOrder() {
		return minOrder;
	}

	public void setMinOrder(Long minOrder) {
		this.minOrder = minOrder;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
 
}
