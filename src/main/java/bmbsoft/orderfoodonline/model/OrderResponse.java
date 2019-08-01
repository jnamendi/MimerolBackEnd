package bmbsoft.orderfoodonline.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

	public Long getUserId() {
		return userId;
	}
	private String userName;
	private String email;
	private String phone;
	private int paymentType;
	private Date orderDate;
	private Long totalPrice;
	private Integer status;
	private String currencyCode;
	private Long taxTotal; 
	private Long deliveryCost;
	private String symbolLeft;
	private Long paymentWith;

	private List<OrderInfoResponse> orderInfos = new LinkedList<>();

	private List<OrderLineItemResponse> orderLineItems = new LinkedList<>();

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
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

	public Long getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Long taxTotal) {
		this.taxTotal = taxTotal;
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

	public List<OrderInfoResponse> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderInfoResponse> orderInfos) {
		this.orderInfos = orderInfos;
	}

	public List<OrderLineItemResponse> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItemResponse> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public Long getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Long deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getSymbolLeft() {
		return symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	public Long getPaymentWith() {
		return paymentWith;
	}

	public void setPaymentWith(Long paymentWith) {
		this.paymentWith = paymentWith;
	}

}
