package bmbsoft.orderfoodonline.model.shared;

import java.util.List;

import javax.validation.constraints.NotNull;

import bmbsoft.orderfoodonline.model.AttributeViewModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;

public class AttributeGroupRequest {
	private Long attributGroupId;
	@NotNull
	private String code; 
	public Long getAttributGroupId() {
		return attributGroupId;
	}
	public void setAttributGroupId(Long attributGroupId) {
		this.attributGroupId = attributGroupId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}
	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}
	private int status;
	private List<LanguageViewModel> languageLst; 
}
