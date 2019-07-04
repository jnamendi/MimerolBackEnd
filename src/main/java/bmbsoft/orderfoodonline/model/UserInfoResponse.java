package bmbsoft.orderfoodonline.model;

import bmbsoft.orderfoodonline.entities.User;

public class UserInfoResponse {
	private long userInfoId; 
	private String emergencyNumber1;
	private String emergencyNumber2;
	private String contactName;
	private String email;
	private String website;
	public long getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(long userInfoId) {
		this.userInfoId = userInfoId;
	}
	public String getEmergencyNumber1() {
		return emergencyNumber1;
	}
	public void setEmergencyNumber1(String emergencyNumber1) {
		this.emergencyNumber1 = emergencyNumber1;
	}
	public String getEmergencyNumber2() {
		return emergencyNumber2;
	}
	public void setEmergencyNumber2(String emergencyNumber2) {
		this.emergencyNumber2 = emergencyNumber2;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	} 
}
