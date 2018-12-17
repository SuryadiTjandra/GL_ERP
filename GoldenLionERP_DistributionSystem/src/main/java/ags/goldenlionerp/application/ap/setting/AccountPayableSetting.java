package ags.goldenlionerp.application.ap.setting;

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
@Table(name="T0401")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="PMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="PMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="PMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="PMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="PMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="PMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="PMCID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="PMDTLS")),
})
public class AccountPayableSetting extends SynchronizedDatabaseEntityImpl<String> {

	static final String DEFAULT_CURRENCY = "IDR";
	
	@Column(name="PMANUM")
	@Id
	private String addressNumber;
	
	@Column(name="PMPTC")
	private String paymentTermCode = "";
	
	@Column(name="PMCRCT")
	private String currencyCodeTransaction = DEFAULT_CURRENCY;
	
	@Column(name="PMINSP")
	private String paymentInstrument = "";
	
	@Column(name="PMCRM")
	private String creditStatus = "";
	
	@Column(name="PMSPG")
	private String supplierPriceGroup = "";
	
	@Column(name="PMITC05")
	private String landedCostRule = "";
	
	@Column(name="PMAPCLS")
	private String accountPayableClass = "";
	
	@Column(name="PMTAXCD")
	private String taxCode = "";
	
	@Column(name="PMTAXID")
	private String taxId = "";
	
	@Column(name="PMTAXAU")
	private String taxAuthorityNumber = "";
	@JoinColumn(name="PMTAXAU", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	//@NotFound(action=NotFoundAction.IGNORE)
	private AddressBookMaster taxAuthority;
	
	@JoinColumn(name="PMANUM", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false) //should be OneToOne, but it doesn't do lazy fetching properly
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

	public String getSupplierPriceGroup() {
		return supplierPriceGroup;
	}

	public String getLandedCostRule() {
		return landedCostRule;
	}

	public String getAccountPayableClass() {
		return accountPayableClass;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public String getTaxId() {
		return taxId;
	}

	public String getTaxAuthorityNumber() {
		return taxAuthorityNumber;
	}

	public AddressBookMaster getTaxAuthority() {
		return taxAuthority;
	}

	public AddressBookMaster getMaster() {
		return master;
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

	void setSupplierPriceGroup(String supplierPriceGroup) {
		this.supplierPriceGroup = supplierPriceGroup;
	}

	void setLandedCostRule(String landedCostRule) {
		this.landedCostRule = landedCostRule;
	}

	void setAccountPayableClass(String accountPayableClass) {
		this.accountPayableClass = accountPayableClass;
	}

	void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	void setTaxAuthorityNumber(String taxAuthorityNumber) {
		this.taxAuthorityNumber = taxAuthorityNumber;
	}

	void setTaxAuthority(AddressBookMaster taxAuthority) {
		this.taxAuthority = taxAuthority;
	}

	void setMaster(AddressBookMaster master) {
		this.master = master;
	}

}
