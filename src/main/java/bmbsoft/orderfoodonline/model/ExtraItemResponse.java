package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ExtraItemResponse {

	private Long extraItemId;
	private Double price;
	private List<LanguageViewModel> extraItem;

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

	public List<LanguageViewModel> getExtraItem() {
		return extraItem;
	}

	public void setExtraItem(List<LanguageViewModel> ExtraItem) {
		this.extraItem = ExtraItem;
	}
}
