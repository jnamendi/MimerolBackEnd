package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

public class AttributeGroupResponse {
	private Long attributGroupId;
	private String attributeGroupCode;
	public Long getAttributGroupId() {
		return attributGroupId;
	}
	public void setAttributGroupId(Long attributGroupId) {
		this.attributGroupId = attributGroupId;
	}
	public String getAttributeGroupCode() {
		return attributeGroupCode;
	}
	public void setAttributeGroupCode(String attributeGroupCode) {
		this.attributeGroupCode = attributeGroupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<AttributeResponse> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeResponse> attributes) {
		this.attributes = attributes;
	}
	private String groupName;
	private List<AttributeResponse> attributes;
}
