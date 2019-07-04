package bmbsoft.orderfoodonline.entities;
// Generated Aug 1, 2018 11:13:58 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ContactUs generated by hbm2java
 */
@Entity
@Table(name = "contact_us", catalog = "ofo_prod")
public class ContactUs implements java.io.Serializable {

	private Long contactId;
	private String subject;
	private Integer contactType;
	private String CName;
	private String CPhone;
	private String CEmail;
	private String CMessage;
	private Date createdDate;
	private Integer isStatus;

	public ContactUs() {
	}

	public ContactUs(String subject, Integer contactType, String CName, String CPhone, String CEmail, String CMessage,
			Date createdDate, Integer isStatus) {
		this.subject = subject;
		this.contactType = contactType;
		this.CName = CName;
		this.CPhone = CPhone;
		this.CEmail = CEmail;
		this.CMessage = CMessage;
		this.createdDate = createdDate;
		this.isStatus = isStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "contact_id", unique = true, nullable = false)
	public Long getContactId() {
		return this.contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	@Column(name = "subject")
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "contact_type")
	public Integer getContactType() {
		return this.contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	@Column(name = "c_name")
	public String getCName() {
		return this.CName;
	}

	public void setCName(String CName) {
		this.CName = CName;
	}

	@Column(name = "c_phone", length = 100)
	public String getCPhone() {
		return this.CPhone;
	}

	public void setCPhone(String CPhone) {
		this.CPhone = CPhone;
	}

	@Column(name = "c_email")
	public String getCEmail() {
		return this.CEmail;
	}

	public void setCEmail(String CEmail) {
		this.CEmail = CEmail;
	}

	@Column(name = "c_message", length = 65535)
	public String getCMessage() {
		return this.CMessage;
	}

	public void setCMessage(String CMessage) {
		this.CMessage = CMessage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "is_status")
	public Integer getIsStatus() {
		return this.isStatus;
	}

	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}

}
