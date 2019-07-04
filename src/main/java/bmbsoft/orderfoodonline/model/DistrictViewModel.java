package bmbsoft.orderfoodonline.model;

import java.sql.Date;

public class DistrictViewModel {

	private Long districtId;
	private CityViewModel city;
	private String name;
	private String code;
	private Integer status;
	private String createdBy;
	private Date createDate;

	public DistrictViewModel() {
	}

	public DistrictViewModel(Long districtId, CityViewModel city, String name, String code, Integer status,
			String createdBy, Date createDate) {
		super();
		this.districtId = districtId;
		this.city = city;
		this.name = name;
		this.code = code;
		this.status = status;
		this.createdBy = createdBy;
		this.createDate = createDate;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public CityViewModel getCity() {
		return city;
	}

	public void setCity(CityViewModel city) {
		this.city = city;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
