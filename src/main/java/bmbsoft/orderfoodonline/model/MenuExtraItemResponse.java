package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class MenuExtraItemResponse {

	private Long menuExtraItemId;
	private int extraItemType;
	private List<LanguageViewModel> languageLst;
	private List<ExtraItemResponse> extraItemLst;

	public Long getMenuExtraItemId() {
		return menuExtraItemId;
	}

	public void setMenuExtraItemId(Long menuExtraItemId) {
		this.menuExtraItemId = menuExtraItemId;
	}

	public int getExtraItemType() {
		return extraItemType;
	}

	public void setExtraItemType(int type) {
		this.extraItemType = type;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

	public List<ExtraItemResponse> getExtraItemLst() {
		return extraItemLst;
	}

	public void setExtraItemLst(List<ExtraItemResponse> extraItemLst) {
		this.extraItemLst = extraItemLst;
	}
}
