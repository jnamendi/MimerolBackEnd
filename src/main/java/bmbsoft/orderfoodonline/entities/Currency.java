package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Currency generated by hbm2java
 */
@Entity
@Table(name = "currency")
public class Currency implements java.io.Serializable {

	private Long currencyId;
	private String code;
	private Boolean isDefault;
	private String name;
	private String symbolLeft;
	private String symbolRight;
	private Integer isStatus;
	private Long roundDecimal;
	private Boolean isHide;
	private Integer sortOrdinal;
	private Date createdDate;
	private Set<CurrencyRate> currencyRates = new HashSet<CurrencyRate>(0);

	public Currency() {
	}

	public Currency(String code, Boolean isDefault, String name, String symbolLeft, String symbolRight,
			Integer isStatus, Long roundDecimal, Boolean isHide, Integer sortOrdinal, Date createdDate,
			Set<CurrencyRate> currencyRates) {
		this.code = code;
		this.isDefault = isDefault;
		this.name = name;
		this.symbolLeft = symbolLeft;
		this.symbolRight = symbolRight;
		this.isStatus = isStatus;
		this.roundDecimal = roundDecimal;
		this.isHide = isHide;
		this.sortOrdinal = sortOrdinal;
		this.createdDate = createdDate;
		this.currencyRates = currencyRates;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "currency_id", unique = true, nullable = false)
	public Long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "code", length = 3)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "is_default")
	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "symbol_left")
	public String getSymbolLeft() {
		return this.symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	@Column(name = "symbol_right")
	public String getSymbolRight() {
		return this.symbolRight;
	}

	public void setSymbolRight(String symbolRight) {
		this.symbolRight = symbolRight;
	}

	@Column(name = "is_status")
	public Integer getIsStatus() {
		return this.isStatus;
	}

	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}

	@Column(name = "round_decimal", precision = 10, scale = 2)
	public Long getRoundDecimal() {
		return this.roundDecimal;
	}

	public void setRoundDecimal(Long roundDecimal) {
		this.roundDecimal = roundDecimal;
	}

	@Column(name = "is_hide")
	public Boolean getIsHide() {
		return this.isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	@Column(name = "sort_ordinal")
	public Integer getSortOrdinal() {
		return this.sortOrdinal;
	}

	public void setSortOrdinal(Integer sortOrdinal) {
		this.sortOrdinal = sortOrdinal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currency")
	public Set<CurrencyRate> getCurrencyRates() {
		return this.currencyRates;
	}

	public void setCurrencyRates(Set<CurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}

}
