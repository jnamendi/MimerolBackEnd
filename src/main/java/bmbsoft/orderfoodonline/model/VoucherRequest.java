package bmbsoft.orderfoodonline.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class VoucherRequest {
	@NotNull(message="name is field required.")
	private String name;
	private String code;
	@NotNull(message="startDate is field required.")
	private Date startDate;
	@NotNull(message="endDate is field required.")
	private Date endDate;
	private String description;
	@NotNull(message="value is field required.")
	private Long value;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
