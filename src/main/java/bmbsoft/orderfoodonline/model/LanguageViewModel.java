package bmbsoft.orderfoodonline.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LanguageViewModel {
	private Long languageId;
	private String name;
	private String code; 
	private bmbsoft.orderfoodonline.util.Constant.Status Status;
	private List<ContentDefModel> contentDef;

	public LanguageViewModel() {
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ContentDefModel> getContentDef() {
		return contentDef;
	}

	public void setContentDef(List<ContentDefModel> contentDef) {
		this.contentDef = contentDef;
	}

	public bmbsoft.orderfoodonline.util.Constant.Status getStatus() {
		return Status;
	}

	public void setStatus(bmbsoft.orderfoodonline.util.Constant.Status status) {
		Status = status;
	}
 
}
