package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class MenuExtraItemLiteResponse {
	private Long menuExtraItemId;
	private int extraItemType;

	private List<ExtraItemLiteResponse> extraitems;

	public Long getMenuExtraItemId() {
		return menuExtraItemId;
	}

	public void setMenuExtraItemId(Long menuExtraItemId) {
		this.menuExtraItemId = menuExtraItemId;
	}

	public int getExtraItemType() {
		return extraItemType;
	}

	public void setExtraItemType(int extraItemType) {
		this.extraItemType = extraItemType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExtraItemLiteResponse> getExtraitems() {
		return extraitems;
	}

	public void setExtraitems(List<ExtraItemLiteResponse> extraitems) {
		this.extraitems = extraitems;
	}

	private String name;
}
