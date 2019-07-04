package bmbsoft.orderfoodonline.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class CityViewModel {

	private Long cityId;
	private CountryViewModel country;
	private String name;
	private String code;
	private int status;
	private String createdBy;
	private Date createDate;

	public CityViewModel() {
	}

	public CityViewModel(Long cityId, CountryViewModel country, String name, String code, int status, String createdBy,
			Date createDate) {
		super();
		this.cityId = cityId;
		this.country = country;
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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public CountryViewModel getCountry() {
		return country;
	}

	public void setCountry(CountryViewModel country) {
		this.country = country;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
