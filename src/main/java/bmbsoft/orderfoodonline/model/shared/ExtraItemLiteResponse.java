package bmbsoft.orderfoodonline.model.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ExtraItemLiteResponse {
	private Long extraItemId;
	private Double price;
	private Double priceRate;
	private String name;
	private String priceRateDisplay;

	public Long getExtraItemId() {
		return extraItemId;
	}

	public void setExtraItemId(Long extraItemId) {
		this.extraItemId = extraItemId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPriceRate() {
		return priceRate;
	}

	public void setPriceRate(Double priceRate) {
		this.priceRate = priceRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPriceRateDisplay() {
		return priceRateDisplay;
	}

	public void setPriceRateDisplay(String priceRateDisplay) {
		this.priceRateDisplay = priceRateDisplay;
	}
}
