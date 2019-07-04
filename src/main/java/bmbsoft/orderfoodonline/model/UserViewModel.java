package bmbsoft.orderfoodonline.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bmbsoft.orderfoodonline.util.Constant.AccountType;
import bmbsoft.orderfoodonline.util.Constant.Provider; 

@JsonInclude(value = Include.NON_NULL)
public class UserViewModel {

	private Long userId;
	@Email(message="Email invalid")
	@Size(max = 255)
	private String email;
	@Size(max = 500)
	private String password;

	private String[] roles;

	private Provider provider;
	@Size(max = 255)
	private String fullName;
	@Size(max = 255)
	private String userName;
	@Size(max = 45)
	private String phone;
	
	private Integer status;
	
	@Size(max = 36)
	private String token;

	private String languageCode = "en";
	private AccountType accountType;
	private boolean receiveNewsLetter;
	private String aliasName;
	private List<AddressViewModel> addresses;
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	@Transient
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (roles != null && roles.length > 0) {
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return authorities;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public boolean getReceiveNewsLetter() {
		return receiveNewsLetter;
	}

	public void setReceiveNewsLetter(boolean receiveNewsLetter) {
		this.receiveNewsLetter = receiveNewsLetter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public List<AddressViewModel> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressViewModel> addresses) {
		this.addresses = addresses;
	} 

}
