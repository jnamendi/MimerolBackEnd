package bmbsoft.orderfoodonline.response;

public class ResponseGetPaging {
	private int status;
	private String message;
	private Data content;
	private String errorType;
	
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

	public Data getContent() {
		return content;
	}

	public void setContent(Data data) {
		this.content = data;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
}
