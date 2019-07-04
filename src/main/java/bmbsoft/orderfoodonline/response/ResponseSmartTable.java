package bmbsoft.orderfoodonline.response;

import bmbsoft.orderfoodonline.model.shared.SmartTableResult;

public class ResponseSmartTable<T> {
	private int status;
	private String message;
	private String errorType;
	private SmartTableResult<T> content;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SmartTableResult<T> getContent() {
		return content;
	}

	public void setContent(SmartTableResult<T> content) {
		this.content = content;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

}
