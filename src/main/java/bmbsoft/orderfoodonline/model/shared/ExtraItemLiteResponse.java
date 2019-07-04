package bmbsoft.orderfoodonline.model.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ExtraItemLiteResponse {
	private Long extraItemId;
	private Long price;
	private Long priceRate;
	private String name;

	public Long getExtraItemId() {
		return extraItemId;
	}

	public void setExtraItemId(Long extraItemId) {
		this.extraItemId = extraItemId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getPriceRate() {
		return priceRate;
	}

	public void setPriceRate(Long priceRate) {
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

	private String priceRateDisplay;
}
