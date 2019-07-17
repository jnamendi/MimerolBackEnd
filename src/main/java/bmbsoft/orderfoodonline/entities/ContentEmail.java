package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ContentEmail generated by hbm2java
 */
@Entity
@Table(name = "content_email")
public class ContentEmail implements java.io.Serializable {

	private Long contentId;
	private Language language;
	private String subject;
	private String title;
	private int type;
	private String contentBody;
	private Integer isStatus;
	private Date createdDate;
	private Date modifiedDate;
	private String to;
	private String cc;

	public ContentEmail() {
	}

	public ContentEmail(Language language, String subject, String title, int type, String contentBody) {
		this.language = language;
		this.subject = subject;
		this.title = title;
		this.type = type;
		this.contentBody = contentBody;
	}

	public ContentEmail(Language language, String subject, String title, int type, String contentBody, Integer isStatus,
			Date createdDate, Date modifiedDate, String to, String cc) {
		this.language = language;
		this.subject = subject;
		this.title = title;
		this.type = type;
		this.contentBody = contentBody;
		this.isStatus = isStatus;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.to = to;
		this.cc = cc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "content_id", unique = true, nullable = false)
	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "language_id", nullable = false)
	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Column(name = "Subject", nullable = false)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "type", nullable = false)
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "content_body", nullable = false, length = 65535)
	public String getContentBody() {
		return this.contentBody;
	}

	public void setContentBody(String contentBody) {
		this.contentBody = contentBody;
	}

	@Column(name = "is_status")
	public Integer getIsStatus() {
		return this.isStatus;
	}

	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "to", length = 65535)
	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Column(name = "cc", length = 65535)
	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

}