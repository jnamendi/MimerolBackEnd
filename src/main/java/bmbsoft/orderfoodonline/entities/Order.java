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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Order generated by hbm2java
 */
@Entity
@Table(name = "order", catalog = "ofo")
public class Order implements java.io.Serializable {

	private Long orderId;
	private Restaurant restaurant;
	private User user;
	private String restaurantName;
	private Date orderDate;
	private Long totalPrice;
	private Integer status;
	private String currencyCode;
	private Long taxTotal;
	private String orderReq;
	private String checkSum;
	private String orderCode;
	private Long paymentWith;
	private String reasonReject;
	private String reasonCancel;
	private Set<OrderInfo> orderInfos = new HashSet<OrderInfo>(0);
	private Set<OrderPayment> orderPayments = new HashSet<OrderPayment>(0);
	private Set<OrderLineItem> orderLineItems = new HashSet<OrderLineItem>(0);

	public Order() {
	}

	public Order(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Order(Long orderId, Restaurant restaurant, User user, String restaurantName, Date orderDate, Long totalPrice, Integer status, String currencyCode, Long taxTotal, String orderReq, String checkSum, String orderCode, Long paymentWith, String reasonReject, String reasonCancel, Set<OrderInfo> orderInfos, Set<OrderPayment> orderPayments, Set<OrderLineItem> orderLineItems) {
		this.orderId = orderId;
		this.restaurant = restaurant;
		this.user = user;
		this.restaurantName = restaurantName;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.status = status;
		this.currencyCode = currencyCode;
		this.taxTotal = taxTotal;
		this.orderReq = orderReq;
		this.checkSum = checkSum;
		this.orderCode = orderCode;
		this.paymentWith = paymentWith;
		this.reasonReject = reasonReject;
		this.reasonCancel = reasonCancel;
		this.orderInfos = orderInfos;
		this.orderPayments = orderPayments;
		this.orderLineItems = orderLineItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "order_id", unique = true, nullable = false)
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", nullable = false)
	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "restaurant_name")
	public String getRestaurantName() {
		return this.restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date", length = 19)
	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "total_price", precision = 10, scale = 0)
	public Long getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "currency_code", length = 3)
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "tax_total", precision = 10, scale = 0)
	public Long getTaxTotal() {
		return this.taxTotal;
	}

	public void setTaxTotal(Long taxTotal) {
		this.taxTotal = taxTotal;
	}

	@Column(name = "order_req", length = 65535)
	public String getOrderReq() {
		return this.orderReq;
	}

	public void setOrderReq(String orderReq) {
		this.orderReq = orderReq;
	}

	@Column(name = "check_sum")
	public String getCheckSum() {
		return this.checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	@Column(name = "order_code")
	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "payment_with", precision = 10, scale = 0)
	public Long getPaymentWith() {
		return this.paymentWith;
	}

	public void setPaymentWith(Long paymentWith) {
		this.paymentWith = paymentWith;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	public Set<OrderInfo> getOrderInfos() {
		return this.orderInfos;
	}

	public void setOrderInfos(Set<OrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	public Set<OrderPayment> getOrderPayments() {
		return this.orderPayments;
	}

	public void setOrderPayments(Set<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	public Set<OrderLineItem> getOrderLineItems() {
		return this.orderLineItems;
	}

	public void setOrderLineItems(Set<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	@Column(name = "reason_reject")
	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}

	@Column(name = "reason_cancel")
	public String getReasonCancel() {
		return reasonCancel;
	}

	public void setReasonCancel(String reasonCancel) {
		this.reasonCancel = reasonCancel;
	}
}
