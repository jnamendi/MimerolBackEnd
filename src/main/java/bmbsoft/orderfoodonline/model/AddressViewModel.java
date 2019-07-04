package bmbsoft.orderfoodonline.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class AddressViewModel {

	private Long addressId;
	// private Long districtId;
	private String district;
	private String country;
	private String city;
	private Long cityId;
	private Long districtId;
	private Long CountryId;
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getCountryId() {
		return CountryId;
	}

	public void setCountryId(Long countryId) {
		CountryId = countryId;
	}

	// private Long residenceTypeId;
	private String residenceTypeName;
	private Long userId;
	private String userName;
	private String ward;
	private String residenceName;
	private String address;
	private String phoneNumber;
	private String roomNumber;
	private Date createdDate;
	private String createdBy;
	private String email;
	private String addressDesc;
	
	public AddressViewModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressViewModel(Long addressId, String districtName, String countryName, String cityName,
			String residenceTypeName, String userName, String ward, String residenceName, String address,
			String phoneNumber, String roomNumber, Date createdDate, String createdBy) {
		super();
		this.addressId = addressId;
		this.district = districtName;
		this.country = countryName;
		this.city = cityName;
		this.residenceTypeName = residenceTypeName;
		this.userName = userName;
		this.ward = ward;
		this.residenceName = residenceName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.roomNumber = roomNumber;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getResidenceTypeName() {
		return residenceTypeName;
	}

	public void setResidenceTypeName(String residenceTypeName) {
		this.residenceTypeName = residenceTypeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getResidenceName() {
		return residenceName;
	}

	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	} 

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

}
