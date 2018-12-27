package ags.goldenlionerp.application.ap.voucher;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.ar.invoice.VoidedAttributeConverter;
import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.entities.Voidable;

@Entity
@Table(name="T0411")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="PVUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="PVDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="PVTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="PVUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="PVDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="PVTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="PVCID")),
})
public class PayableVoucher extends DatabaseEntity<PayableVoucherPK> implements Voidable{

	@EmbeddedId @JsonUnwrapped
	private PayableVoucherPK pk;
	
	@Column(name="PVDOCIDT")
	private LocalDate voucherDate;
	
	@Column(name="PVVMS")
	private String voucherMatchStatus = "";
	
	@Column(name="PVICU")
	private int batchNumber;
	
	@Column(name="PVICUT")
	private String batchType = "";
	
	@Column(name="PVGLDT")
	private LocalDate glDate;
	
	@Column(name="PVPYID")
	private String payerId = "";
	
	@Column(name="PVVNID")
	private String vendorId = "";
	
	@Column(name="PVDUDT")
	private LocalDate dueDate;
	
	@Column(name="PVDCDT")
	private LocalDate discountDueDate;
	
	@Column(name="PVCLDT")
	private LocalDate closedDate;
	
	@Column(name="PVAMNT", precision=19, scale=5)
	private BigDecimal netAmount = new BigDecimal(0);
	
	@Column(name="PVAOP", precision=19, scale=5)
	private BigDecimal openAmount = new BigDecimal(0);
	
	@Column(name="PVADSCA", precision=19, scale=5)
	private BigDecimal discountAmountAvailable = new BigDecimal(0);
	
	@Column(name="PVADSCT", precision=19, scale=5)
	private BigDecimal discountAmountTaken = new BigDecimal(0);
	
	@Column(name="PVTAXAB", precision=19, scale=5)
	private BigDecimal baseTaxableAmount = new BigDecimal(0);
	
	@Column(name="PVTAXAM", precision=19, scale=5)
	private BigDecimal taxableAmount = new BigDecimal(0);
	
	@Column(name="PVFEA", precision=19, scale=5)
	private BigDecimal foreignExtendedAmount = new BigDecimal(0);
	
	@Column(name="PVFDA", precision=19, scale=5)
	private BigDecimal foreignDiscountAmountAvailable = new BigDecimal(0);
	
	@Column(name="PVFDT", precision=19, scale=5)
	private BigDecimal foreignDiscountAmountTaken = new BigDecimal(0);
	
	@Column(name="PVFTB", precision=19, scale=5)
	private BigDecimal foreignBaseTaxableAmount = new BigDecimal(0);
	
	@Column(name="PVFTA", precision=19, scale=5)
	private BigDecimal foreignTaxableAmount = new BigDecimal(0);
	
	@Column(name="PVCRCB")
	private String baseCurrency = "";
	
	@Column(name="PVCRCT")
	private String transactionCurrency = "";
	
	@Column(name="PVEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = new BigDecimal(0);
	
	@Column(name="PVTAXCD")
	private String taxCode = "";
	
	@Column(name="PVTAXRT", precision=19, scale=15)
	private BigDecimal taxRate = new BigDecimal(0);
	
	@Column(name="PVTAXAL")
	private String taxAllowance = "";
	
	@Column(name="PVTAXINO")
	private String taxInvoiceNumber = "";
	
	@Column(name="PVTAXIDT")
	private LocalDate taxInvoiceDate;
	
	@Column(name="PVINVNO")
	private String vendorInvoiceNumber = "";
	
	@Column(name="PVINVDT")
	private LocalDate vendorInvoiceDate;
	
	@Column(name="PVDOFT")
	private String domesticOrForeignTransaction = "";
	
	@Column(name="PVDSC")
	private String documentStatusCode = "";
	
	@Column(name="PVPTC")
	private String paymentTermCode = "";
	
	@Column(name="PVDOCONO")
	private int documentOrderNumber;
	
	@Column(name="PVDOCOTY")
	private String documentOrderType = "";
	
	@Column(name="PVDOCOSQ")
	private int documentOrderSequence;
	
	@Column(name="PVORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="PVORDOCTY")
	private String originalDocumentType = "";
	
	@Column(name="PVORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="PVDVS")
	private String documentVoidStatus = "";
	@Column(name="PVDVS", insertable=false, updatable=false)
	@Convert(converter=VoidedAttributeConverter.class)
	private Boolean voided = false;
	
	@Column(name="PVGLCLS")
	private String glClass = "";
	
	@Column(name="PVACID")
	private String accountId = "";
	
	@Column(name="PVBUID")
	private String businessUnitId = "";
	
	@Column(name="PVOBJ")
	private String objectAccountCode = "";
	
	@Column(name="PVSUB")
	private String subsidiaryCode = "";
	
	@Column(name="PVSUBL")
	private String subledger = "";
	
	@Column(name="PVSUBLT")
	private String subledgerType = "";
	
	@Column(name="PVDESB1")
	private String accountDescription = "";
	
	@Column(name="PVDESB2")
	private String description = "";
	
	@Column(name="PVOBID")
	private String objectId = "";
	
	@Override
	public PayableVoucherPK getId() {
		return getPk();
	}

	public PayableVoucherPK getPk() {
		return pk;
	}

	public LocalDate getVoucherDate() {
		return voucherDate;
	}

	public String getVoucherMatchStatus() {
		return voucherMatchStatus;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public String getBatchType() {
		return batchType;
	}

	public LocalDate getGlDate() {
		return glDate;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getDiscountDueDate() {
		return discountDueDate;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public BigDecimal getOpenAmount() {
		return openAmount;
	}

	public BigDecimal getDiscountAmountAvailable() {
		return discountAmountAvailable;
	}

	public BigDecimal getDiscountAmountTaken() {
		return discountAmountTaken;
	}

	public BigDecimal getBaseTaxableAmount() {
		return baseTaxableAmount;
	}

	public BigDecimal getTaxableAmount() {
		return taxableAmount;
	}

	public BigDecimal getForeignExtendedAmount() {
		return foreignExtendedAmount;
	}

	public BigDecimal getForeignDiscountAmountAvailable() {
		return foreignDiscountAmountAvailable;
	}

	public BigDecimal getForeignDiscountAmountTaken() {
		return foreignDiscountAmountTaken;
	}

	public BigDecimal getForeignBaseTaxableAmount() {
		return foreignBaseTaxableAmount;
	}

	public BigDecimal getForeignTaxableAmount() {
		return foreignTaxableAmount;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public String getTaxAllowance() {
		return taxAllowance;
	}

	public String getTaxInvoiceNumber() {
		return taxInvoiceNumber;
	}

	public LocalDate getTaxInvoiceDate() {
		return taxInvoiceDate;
	}

	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}

	public LocalDate getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}

	public String getDomesticOrForeignTransaction() {
		return domesticOrForeignTransaction;
	}

	public String getDocumentStatusCode() {
		return documentStatusCode;
	}

	public String getPaymentTermCode() {
		return paymentTermCode;
	}

	public int getDocumentOrderNumber() {
		return documentOrderNumber;
	}

	public String getDocumentOrderType() {
		return documentOrderType;
	}

	public int getDocumentOrderSequence() {
		return documentOrderSequence;
	}

	public int getOriginalDocumentNumber() {
		return originalDocumentNumber;
	}

	public String getOriginalDocumentType() {
		return originalDocumentType;
	}

	public int getOriginalDocumentSequence() {
		return originalDocumentSequence;
	}

	public String getDocumentVoidStatus() {
		return documentVoidStatus;
	}

	public Boolean getVoided() {
		return voided;
	}

	public String getGlClass() {
		return glClass;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public String getObjectAccountCode() {
		return objectAccountCode;
	}

	public String getSubsidiaryCode() {
		return subsidiaryCode;
	}

	public String getSubledger() {
		return subledger;
	}

	public String getSubledgerType() {
		return subledgerType;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public String getDescription() {
		return description;
	}

	public String getObjectId() {
		return objectId;
	}

	void setPk(PayableVoucherPK pk) {
		this.pk = pk;
	}

	void setVoucherDate(LocalDate voucherDate) {
		this.voucherDate = voucherDate;
	}

	void setVoucherMatchStatus(String voucherMatchStatus) {
		this.voucherMatchStatus = voucherMatchStatus;
	}

	void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
	}

	void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	void setDiscountDueDate(LocalDate discountDueDate) {
		this.discountDueDate = discountDueDate;
	}

	void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	void setOpenAmount(BigDecimal openAmount) {
		this.openAmount = openAmount;
	}

	void setDiscountAmountAvailable(BigDecimal discountAmountAvailable) {
		this.discountAmountAvailable = discountAmountAvailable;
	}

	void setDiscountAmountTaken(BigDecimal discountAmountTaken) {
		this.discountAmountTaken = discountAmountTaken;
	}

	void setBaseTaxableAmount(BigDecimal baseTaxableAmount) {
		this.baseTaxableAmount = baseTaxableAmount;
	}

	void setTaxableAmount(BigDecimal taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	void setForeignExtendedAmount(BigDecimal foreignExtendedAmount) {
		this.foreignExtendedAmount = foreignExtendedAmount;
	}

	void setForeignDiscountAmountAvailable(BigDecimal foreignDiscountAmountAvailable) {
		this.foreignDiscountAmountAvailable = foreignDiscountAmountAvailable;
	}

	void setForeignDiscountAmountTaken(BigDecimal foreignDiscountAmountTaken) {
		this.foreignDiscountAmountTaken = foreignDiscountAmountTaken;
	}

	void setForeignBaseTaxableAmount(BigDecimal foreignBaseTaxableAmount) {
		this.foreignBaseTaxableAmount = foreignBaseTaxableAmount;
	}

	void setForeignTaxableAmount(BigDecimal foreignTaxableAmount) {
		this.foreignTaxableAmount = foreignTaxableAmount;
	}

	void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	void setTaxAllowance(String taxAllowance) {
		this.taxAllowance = taxAllowance;
	}

	void setTaxInvoiceNumber(String taxInvoiceNumber) {
		this.taxInvoiceNumber = taxInvoiceNumber;
	}

	void setTaxInvoiceDate(LocalDate taxInvoiceDate) {
		this.taxInvoiceDate = taxInvoiceDate;
	}

	void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}

	void setVendorInvoiceDate(LocalDate vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}

	void setDomesticOrForeignTransaction(String domesticOrForeignTransaction) {
		this.domesticOrForeignTransaction = domesticOrForeignTransaction;
	}

	void setDocumentStatusCode(String documentStatusCode) {
		this.documentStatusCode = documentStatusCode;
	}

	void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
	}

	void setDocumentOrderNumber(int documentOrderNumber) {
		this.documentOrderNumber = documentOrderNumber;
	}

	void setDocumentOrderType(String documentOrderType) {
		this.documentOrderType = documentOrderType;
	}

	void setDocumentOrderSequence(int documentOrderSequence) {
		this.documentOrderSequence = documentOrderSequence;
	}

	void setOriginalDocumentNumber(int originalDocumentNumber) {
		this.originalDocumentNumber = originalDocumentNumber;
	}

	void setOriginalDocumentType(String originalDocumentType) {
		this.originalDocumentType = originalDocumentType;
	}

	void setOriginalDocumentSequence(int originalDocumentSequence) {
		this.originalDocumentSequence = originalDocumentSequence;
	}

	void setDocumentVoidStatus(String documentVoidStatus) {
		this.documentVoidStatus = documentVoidStatus;
	}

	void setGlClass(String glClass) {
		this.glClass = glClass;
	}

	void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setObjectAccountCode(String objectAccountCode) {
		this.objectAccountCode = objectAccountCode;
	}

	void setSubsidiaryCode(String subsidiaryCode) {
		this.subsidiaryCode = subsidiaryCode;
	}

	void setSubledger(String subledger) {
		this.subledger = subledger;
	}

	void setSubledgerType(String subledgerType) {
		this.subledgerType = subledgerType;
	}

	void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Override
	public boolean isVoided() {
		return voided;
	}
	
	public void voidDocument() {
		this.documentVoidStatus = "V";
		this.closedDate = LocalDate.now();
		this.openAmount = BigDecimal.ZERO;
	}
	

}
