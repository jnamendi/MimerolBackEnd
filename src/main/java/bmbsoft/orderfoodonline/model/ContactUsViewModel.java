package bmbsoft.orderfoodonline.model;

import java.util.Date;

import bmbsoft.orderfoodonline.util.Constant.ContactType;

public class ContactUsViewModel {
	private String subject;
	private long contactId; 
	private ContactType contactType;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String contactMessage;
	private int status;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactMessage() {
		return contactMessage;
	}
	public void setContactMessage(String contactMessage) {
		this.contactMessage = contactMessage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	} 
}
