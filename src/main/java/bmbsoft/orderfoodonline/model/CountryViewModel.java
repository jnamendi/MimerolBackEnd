package bmbsoft.orderfoodonline.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class CountryViewModel {

	private Long countryId;
	private String name;
	private String code;
	private int status;
	private String createdBy;
	private Date createDate;

	public CountryViewModel() {
	}

	public CountryViewModel(Long countryId, String name, String code, int status, String createdBy, Date createDate) {
		super();
		this.countryId = countryId;
		this.name = name;
		this.code = code;
		this.status = status;
		this.createdBy = createdBy;
		this.createDate = createDate;
		
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
