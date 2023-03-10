package bmbsoft.orderfoodonline.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ExtraItemRequest {
	@NotNull(message = "price is field required.")
	private Double price;
	@NotNull(message = "extraItem is field required.")
	private List<LanguageViewModel> extraItem;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<LanguageViewModel> getExtraItem() {
		return extraItem;
	}

	public void setExtraItem(List<LanguageViewModel> extraItem) {
		this.extraItem = extraItem;
	}

}
