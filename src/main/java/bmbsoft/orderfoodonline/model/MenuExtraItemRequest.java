package bmbsoft.orderfoodonline.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class MenuExtraItemRequest {
	@NotNull(message = "name is field required.")
	private List<LanguageViewModel> languageLst;
	@NotNull(message = "controlType is field required.")
	private int extraItemType;
	@NotNull(message = "extraItemLst is field required.")
	private List<ExtraItemRequest> extraItemLst;

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public List<ExtraItemRequest> getExtraItemLst() {
		return extraItemLst;
	}

	public void setExtraItemLst(List<ExtraItemRequest> extraItem) {
		this.extraItemLst = extraItem;
	}

	public int getExtraItemType() {
		return extraItemType;
	}

	public void setExtraItemType(int controlType) {
		this.extraItemType = controlType;
	}
}
