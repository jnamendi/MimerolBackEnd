package bmbsoft.orderfoodonline.model;

import javax.validation.constraints.NotNull;

public class VoucherLineItemRequest {

	@NotNull
	private Long voucherId;
	
	@NotNull
	private int numberOfCode;

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public int getNumberOfCode() {
		return numberOfCode;
	}

	public void setNumberOfCode(int numberOfCode) {
		this.numberOfCode = numberOfCode;
	} 

}
