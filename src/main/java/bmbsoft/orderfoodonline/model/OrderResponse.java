package bmbsoft.orderfoodonline.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bmbsoft.orderfoodonline.model.shared.MenuExtraItemLiteResponse;
import bmbsoft.orderfoodonline.model.shared.MenuItemLiteResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bmbsoft.orderfoodonline.model.shared.OrderLineItemResponse;

@JsonInclude(value = Include.NON_NULL)
public class OrderResponse {

	private Long orderId;
	private String orderCode;
	private Long restaurantId;
	private String restaurantName;
	private String imageUrl;
	private Long userId;
	private String userName;
	private String email;
	private String phone;
	private int paymentType;
	private Date orderDate;
	private Double totalPrice;
	private Integer status;
	private String currencyCode;
	private Double taxTotal;
	private Double deliveryCost;
	private String symbolLeft;
	private Double paymentWith;
	private String reasonReject;
	private String reasonCancel;
	private Double discountPercent;

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	private List<OrderInfoResponse> orderInfos = new LinkedList<>();

	private List<MenuItemLiteResponse> orderLineItems = new LinkedList<>();

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Double getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getSymbolLeft() {
		return symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	public Double getPaymentWith() {
		return paymentWith;
	}

	public void setPaymentWith(Double paymentWith) {
		this.paymentWith = paymentWith;
	}

	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}

	public String getReasonCancel() {
		return reasonCancel;
	}

	public void setReasonCancel(String reasonCancel) {
		this.reasonCancel = reasonCancel;
	}

	public List<OrderInfoResponse> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderInfoResponse> orderInfos) {
		this.orderInfos = orderInfos;
	}

	public List<MenuItemLiteResponse> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<MenuItemLiteResponse> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
}
