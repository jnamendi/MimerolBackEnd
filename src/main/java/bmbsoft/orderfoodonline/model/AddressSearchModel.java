package bmbsoft.orderfoodonline.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class AddressSearchModel {

	@NotNull
	private String languageCode;
	private String address;
	private String district;
	@NotNull
	private String city;

	public AddressSearchModel() {
		super();
	}

	public AddressSearchModel(@NotNull String languageId, String address, @NotNull String district,
			@NotNull String city) {
		super();
		this.languageCode = languageId;
		this.address = address;
		this.district = district;
		this.city = city;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}
