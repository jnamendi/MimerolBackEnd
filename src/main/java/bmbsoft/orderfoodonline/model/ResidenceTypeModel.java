package bmbsoft.orderfoodonline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class ResidenceTypeModel {

	private Long residenceTypeId;
	private String name;

	public ResidenceTypeModel() {
		super();
	}

	public ResidenceTypeModel(Long residenceTypeId, String name) {
		super();
		this.residenceTypeId = residenceTypeId;
		this.name = name;
	}

	public Long getResidenceTypeId() {
		return residenceTypeId;
	}

	public void setResidenceTypeId(Long residenceTypeId) {
		this.residenceTypeId = residenceTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
