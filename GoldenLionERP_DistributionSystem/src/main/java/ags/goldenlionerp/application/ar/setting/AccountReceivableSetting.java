package ags.goldenlionerp.application.ar.setting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T0301")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="RMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="RMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="RMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="RMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="RMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="RMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="RMCID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="RMDTLS")),
})
public class AccountReceivableSetting extends SynchronizedDatabaseEntityImpl<String> {

	static final String DEFAULT_CURRENCY = "IDR";
	
	@Id
	@Column(name="RMANUM")
	private String addressNumber;
	
	@Column(name="RMPTC")
	private String paymentTermCode = "";
	
	@Column(name="RMCRCT")
	private String currencyCodeTransaction = DEFAULT_CURRENCY;
	
	@Column(name="RMINSP")
	private String paymentInstrument = "";
	
	@Column(name="RMCRM")
	private String creditStatus = "";
	
	@Column(name="RMCRL", precision=19, scale=5)
	private BigDecimal creditLimit = new BigDecimal(0);
	
	@Column(name="RMRCBS")
	private String rankBySales = "";
	
	@Column(name="RMRCBM")
	private String rankByProfitMargin = "";
	
	@Column(name="RMRCBAD")
	private String rankByAverageDay = "";
	
	@Column(name="RMBADT")
	private String billingAddressType = "";
	
	@Column(name="RMTDF", precision=9, scale=5)
	private BigDecimal tradeDiscountFactor = new BigDecimal(0);
	
	@Column(name="RMCPG")
	private String customerPriceGroup = "";
	
	@Column(name="RMARCLS")
	private String accountReceivableClass = "";
	
	@Column(name="RMTAXCD")
	private String taxCode = "";
	
	@Column(name="RMTAXID")
	private String taxId = "";
	
	@Column(name="RMSLID")
	private String salesmanAddressNumber;
	@JoinColumn(name="RMSLID", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY) 
	//@NotFound(action=NotFoundAction.IGNORE)
	//@Transient
	private AddressBookMaster salesman;
	
	@Column(name="RMAODT")
	private LocalDate accountOpenedDate;
	
	@Column(name="RMEXPDT")
	private LocalDate expiredDate ;
	
	@Column(name="RMDESC1")
	private String description = "";
	
	@JoinColumn(name="RMANUM", insertable=false, updatable=false)
	@ManyToOne(optional=false, fetch=FetchType.LAZY)	//should be OneToOne, but it doesn't allow lazy fetching
	private AddressBookMaster master;
	
	@Override
	public String getId() {
		return getAddressNumber();
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public String getPaymentTermCode() {
		return paymentTermCode;
	}

	public String getCurrencyCodeTransaction() {
		return currencyCodeTransaction;
	}

	public String getPaymentInstrument() {
		return paymentInstrument;
	}

	public String getCreditStatus() {
		return creditStatus;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public String getRankBySales() {
		return rankBySales;
	}

	public String getRankByProfitMargin() {
		return rankByProfitMargin;
	}

	public String getRankByAverageDay() {
		return rankByAverageDay;
	}

	public String getBillingAddressType() {
		return billingAddressType;
	}

	public BigDecimal getTradeDiscountFactor() {
		return tradeDiscountFactor;
	}

	public String getCustomerPriceGroup() {
		return customerPriceGroup;
	}

	public String getAccountReceivableClass() {
		return accountReceivableClass;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public String getTaxId() {
		return taxId;
	}

	public String getSalesmanAddressNumber() {
		return salesmanAddressNumber;
	}

	public Optional<AddressBookMaster> getSalesman() {
		return Optional.ofNullable(salesman);
	}

	public LocalDate getAccountOpenedDate() {
		return accountOpenedDate;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}

	public String getDescription() {
		return description;
	}

	void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
	}

	void setCurrencyCodeTransaction(String currencyCodeTransaction) {
		this.currencyCodeTransaction = currencyCodeTransaction;
	}

	void setPaymentInstrument(String paymentInstrument) {
		this.paymentInstrument = paymentInstrument;
	}

	void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	void setRankBySales(String rankBySales) {
		this.rankBySales = rankBySales;
	}

	void setRankByProfitMargin(String rankByProfitMargin) {
		this.rankByProfitMargin = rankByProfitMargin;
	}

	void setRankByAverageDay(String rankByAverageDay) {
		this.rankByAverageDay = rankByAverageDay;
	}

	void setBillingAddressType(String billingAddressType) {
		this.billingAddressType = billingAddressType;
	}

	void setTradeDiscountFactor(BigDecimal tradeDiscountFactor) {
		this.tradeDiscountFactor = tradeDiscountFactor;
	}

	void setCustomerPriceGroup(String customerPriceGroup) {
		this.customerPriceGroup = customerPriceGroup;
	}

	void setAccountReceivableClass(String accountReceivableClass) {
		this.accountReceivableClass = accountReceivableClass;
	}

	void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	void setSalesmanAddressNumber(String salesmanAddressNumber) {
		this.salesmanAddressNumber = salesmanAddressNumber;
	}

	void setSalesman(AddressBookMaster salesman) {
		this.salesman = salesman;
	}

	void setAccountOpenedDate(LocalDate accountOpenedDate) {
		this.accountOpenedDate = accountOpenedDate;
	}

	void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}

	void setDescription(String description) {
		this.description = description;
	}

	public AddressBookMaster getMaster() {
		return master;
	}

	void setMaster(AddressBookMaster master) {
		this.master = master;
	}

	
}
