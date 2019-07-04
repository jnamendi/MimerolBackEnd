package bmbsoft.orderfoodonline.model;

public class PaymentProviderViewModel {
	private String name;
	private int status;
	private int sortOrder;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	private Long PaymentProviderId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPaymentProviderId() {
		return PaymentProviderId;
	}
	public void setPaymentProviderId(Long paymentProviderId) {
		PaymentProviderId = paymentProviderId;
	}
}
