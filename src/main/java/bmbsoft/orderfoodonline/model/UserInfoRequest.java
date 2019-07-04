package bmbsoft.orderfoodonline.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserInfoRequest {
	@NotNull(message = "E-mail address is required")
	private Long userId;
	private Long userInfoId;
	private String emergencyNumber1;
	private String emergencyNumber2;
	private String contactName;
	@Email
	private String email;
	private String website;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmergencyNumber1() {
		return emergencyNumber1;
	}

	public void setEmergencyNumber1(String mergencyNumber1) {
		this.emergencyNumber1 = mergencyNumber1;
	}

	public String getEmergencyNumber2() {
		return emergencyNumber2;
	}

	public void setEmergencyNumber2(String mergencyNumber2) {
		this.emergencyNumber2 = mergencyNumber2;
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

	public Long getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}
}
