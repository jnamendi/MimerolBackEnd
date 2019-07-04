package bmbsoft.orderfoodonline.model;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class sModel {

	private Long meidaId;
	private String url; 
	
	public sModel() {
	}

	public Long getMeidaId() {
		return meidaId;
	}

	public void setMeidaId(Long meidaId) {
		this.meidaId = meidaId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
 
}
