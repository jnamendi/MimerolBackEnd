package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class AttributeGroupViewModel {
	private Long attributGroupId;
	private String code; 
	private int status;
	private List<LanguageViewModel> languageLst;
	private List<AttributeViewModel> attributeViewModels;

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
 
	public int isStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<AttributeViewModel> getAttributeViewModels() {
		return attributeViewModels;
	}

	public void setAttributeViewModels(List<AttributeViewModel> attributeViewModels) {
		this.attributeViewModels = attributeViewModels;
	}

	public List<LanguageViewModel> getLanguageLst() {
		return languageLst;
	}

	public void setLanguageLst(List<LanguageViewModel> languageLst) {
		this.languageLst = languageLst;
	}

}
