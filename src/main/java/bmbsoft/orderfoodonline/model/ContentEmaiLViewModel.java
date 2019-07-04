package bmbsoft.orderfoodonline.model;

import bmbsoft.orderfoodonline.util.Constant.Status;

public class ContentEmaiLViewModel {
	private Long contentEmailId;
	private String subject;
	private String title;
	private String body;
	private Status status;
	private String bcc;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getContentEmailId() {
		return contentEmailId;
	}

	public void setContentEmailId(Long contentEmailId) {
		this.contentEmailId = contentEmailId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	} 
}
