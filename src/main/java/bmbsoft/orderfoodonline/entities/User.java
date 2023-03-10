package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	private Long userId;
	private String userName;
	private String email;
	private String phone;
	private String userHash;
	private String userSalt;
	private String fullName;
	private Date createdDate;
	private Integer status;
	private Boolean isLock;
	private Integer provider;
	private String originalData;
	private String resetToken;
	private Date expiredDate;
	private Boolean receiveNewsletter;
	private Integer accountType;
	private Date modifiedDate;
	private String modifiedBy;
	private Set<Address> addresses = new HashSet<Address>(0);
	private Set<UserInfo> userInfos = new HashSet<UserInfo>(0);
	private Set<Order> orders = new HashSet<Order>(0);
	private Set<Rating> ratings = new HashSet<Rating>(0);
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<RestaurantComment> restaurantComments = new HashSet<RestaurantComment>(0);
	private Set<UserRestaurant> userRestaurants = new HashSet<UserRestaurant>(0);
	private Set<Favouries> favourieses = new HashSet<Favouries>(0);

	public User() {
	}

	public User(String userName, String userHash, String userSalt) {
		this.userName = userName;
		this.userHash = userHash;
		this.userSalt = userSalt;
	}

	public User(String userName, String email, String phone, String userHash, String userSalt, String fullName,
			Date createdDate, Integer status, Boolean isLock, Integer provider, String originalData, String resetToken,
			Date expiredDate, Boolean receiveNewsletter, Integer accountType, Date modifiedDate, String modifiedBy,
			Set<Address> addresses, Set<UserInfo> userInfos, Set<Order> orders, Set<Rating> ratings,
			Set<UserRole> userRoles, Set<RestaurantComment> restaurantComments, Set<UserRestaurant> userRestaurants,
			Set<Favouries> favourieses) {
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.userHash = userHash;
		this.userSalt = userSalt;
		this.fullName = fullName;
		this.createdDate = createdDate;
		this.status = status;
		this.isLock = isLock;
		this.provider = provider;
		this.originalData = originalData;
		this.resetToken = resetToken;
		this.expiredDate = expiredDate;
		this.receiveNewsletter = receiveNewsletter;
		this.accountType = accountType;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.addresses = addresses;
		this.userInfos = userInfos;
		this.orders = orders;
		this.ratings = ratings;
		this.userRoles = userRoles;
		this.restaurantComments = restaurantComments;
		this.userRestaurants = userRestaurants;
		this.favourieses = favourieses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", length = 45)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "user_hash", nullable = false, length = 500)
	public String getUserHash() {
		return this.userHash;
	}

	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	@Column(name = "user_salt", nullable = false, length = 500)
	public String getUserSalt() {
		return this.userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "is_lock")
	public Boolean getIsLock() {
		return this.isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	@Column(name = "provider")
	public Integer getProvider() {
		return this.provider;
	}

	public void setProvider(Integer provider) {
		this.provider = provider;
	}

	@Column(name = "original_data", length = 65535)
	public String getOriginalData() {
		return this.originalData;
	}

	public void setOriginalData(String originalData) {
		this.originalData = originalData;
	}

	@Column(name = "reset_token", length = 36)
	public String getResetToken() {
		return this.resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expired_date", length = 19)
	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Column(name = "receive_newsletter")
	public Boolean getReceiveNewsletter() {
		return this.receiveNewsletter;
	}

	public void setReceiveNewsletter(Boolean receiveNewsletter) {
		this.receiveNewsletter = receiveNewsletter;
	}

	@Column(name = "account_type")
	public Integer getAccountType() {
		return this.accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by")
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserInfo> getUserInfos() {
		return this.userInfos;
	}

	public void setUserInfos(Set<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<RestaurantComment> getRestaurantComments() {
		return this.restaurantComments;
	}

	public void setRestaurantComments(Set<RestaurantComment> restaurantComments) {
		this.restaurantComments = restaurantComments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRestaurant> getUserRestaurants() {
		return this.userRestaurants;
	}

	public void setUserRestaurants(Set<UserRestaurant> userRestaurants) {
		this.userRestaurants = userRestaurants;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Favouries> getFavourieses() {
		return this.favourieses;
	}

	public void setFavourieses(Set<Favouries> favourieses) {
		this.favourieses = favourieses;
	}

}
