package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class AttributeViewModel {
	private Long attributGroupId;
	private Long attributeId; 
	private String code;
	private List<LanguageViewModel> languageLst;
	 
	private int status;
	public Long getAttributGroupId() {
		return attributGroupId;
	}
	public void setAttributGroupId(Long attributGroupId) {
		this.attributGroupId = attributGroupId;
	}  
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	} 
	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}
	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
