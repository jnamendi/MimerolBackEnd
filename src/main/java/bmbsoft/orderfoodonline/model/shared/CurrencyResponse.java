package bmbsoft.orderfoodonline.model.shared;

public class CurrencyResponse {
	 
	private Long currencyId;
	private String code; 
	private String name;
	private String symbolLeft;
	private String symbolRight; 
	private Long roundDecimal;
	private Double rate;
	
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbolLeft() {
		return symbolLeft;
	}
	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}
	public String getSymbolRight() {
		return symbolRight;
	}
	public void setSymbolRight(String symbolRight) {
		this.symbolRight = symbolRight;
	}
	public Long getRoundDecimal() {
		return roundDecimal;
	}
	public void setRoundDecimal(Long roundDecimal) {
		this.roundDecimal = roundDecimal;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}  
}
