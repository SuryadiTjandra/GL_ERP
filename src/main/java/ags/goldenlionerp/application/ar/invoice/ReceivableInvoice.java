package ags.goldenlionerp.application.ar.invoice;

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

import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.entities.Voidable;

@Entity
@Table(name="T0311")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="RIUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="RIDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="RITMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="RIUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="RIDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="RITMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="RICID")),
})
public class ReceivableInvoice extends DatabaseEntity<ReceivableInvoicePK> implements Voidable{

	@EmbeddedId
	@JsonUnwrapped
	private ReceivableInvoicePK pk;
	
	@Column(name="RIDOCIDT")
	private LocalDate invoiceDate;
	
	@Column(name="RIIMS")
	private String invoiceMatchStatus = "";
	
	@Column(name="RIICU")
	private int batchNumber;
	
	@Column(name="RIICUT")
	private String batchType = "";
	
	@Column(name="RIGLDT")
	private LocalDate glDate;
	
	@Column(name="RICSID")
	private String customerId = "";
	
	@Column(name="RIPYID")
	private String payerId = "";
	
	@Column(name="RISLID")
	private String salesmanId = "";
	
	@Column(name="RIDUDT")
	private LocalDate dueDate;
	
	@Column(name="RIDCDT")
	private LocalDate discountDueDate;
	
	@Column(name="RICLDT")
	private LocalDate closedDate;
	
	@Column(name="RIAMNT", precision=19, scale=5)
	private BigDecimal netAmount = new BigDecimal(0);
	
	@Column(name="RIAOP", precision=19, scale=5)
	private BigDecimal openAmount = new BigDecimal(0);
	
	@Column(name="RIADSCA", precision=19, scale=5)
	private BigDecimal discountAmountAvailable = new BigDecimal(0);
	
	@Column(name="RIADSCT", precision=19, scale=5)
	private BigDecimal discountAmountTaken = new BigDecimal(0);
	
	@Column(name="RITAXAB", precision=19, scale=5)
	private BigDecimal baseTaxableAmount = new BigDecimal(0);
	
	@Column(name="RITAXAM", precision=19, scale=5)
	private BigDecimal taxableAmount = new BigDecimal(0);
	
	@Column(name="RIFEA", precision=19, scale=5)
	private BigDecimal foreignExtendedAmount = new BigDecimal(0);
	
	@Column(name="RIFDA", precision=19, scale=5)
	private BigDecimal foreignDiscountAmountAvailable = new BigDecimal(0);
	
	@Column(name="RIFDT", precision=19, scale=5)
	private BigDecimal foreignDiscountAmountTaken = new BigDecimal(0);
	
	@Column(name="RIFTB", precision=19, scale=5)
	private BigDecimal foreignBaseTaxableAmount = new BigDecimal(0);
	
	@Column(name="RIFTA", precision=19, scale=5)
	private BigDecimal foreignTaxableAmount = new BigDecimal(0);
	
	@Column(name="RICRCB")
	private String baseCurrency = "";
	
	@Column(name="RICRCT")
	private String transactionCurrency = "";
	
	@Column(name="RIEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = new BigDecimal(0);
	
	@Column(name="RITAXCD")
	private String taxCode = "";
	
	@Column(name="RITAXRT", precision=19, scale=15)
	private BigDecimal taxRate = new BigDecimal(0);
	
	@Column(name="RITAXAL")
	private String taxAllowance = "";
	
	@Column(name="RITAXINO")
	private String taxInvoiceNumber = "";
	
	@Column(name="RITAXIDT")
	private LocalDate taxInvoiceDate;
	
	@Column(name="RIINVNO")
	private String vendorInvoiceNumber = "";
	
	@Column(name="RIINVDT")
	private LocalDate vendorInvoiceDate;
	
	@Column(name="RIDOFT")
	private String domesticOrForeignTransaction = "";
	
	@Column(name="RIDSC")
	private String documentStatusCode = "";
	
	@Column(name="RIPTC")
	private String paymentTermCode = "";
	
	@Column(name="RIDOCONO")
	private int documentOrderNumber;
	
	@Column(name="RIDOCOTY")
	private String documentOrderType = "";
	
	@Column(name="RIDOCOSQ")
	private int documentOrderSequence;
	
	@Column(name="RIORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="RIORDOCTY")
	private String originalDocumentType = "";
	
	@Column(name="RIORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="RIDVS")
	private String documentVoidStatus = "";
	@Column(name="RIDVS", insertable=false, updatable=false)
	@Convert(converter=VoidedAttributeConverter.class)
	private Boolean voided = false;
	
	@Column(name="RIGLCLS")
	private String glClass = "";
	
	@Column(name="RIACID")
	private String accountId = "";
	
	@Column(name="RIBUID")
	private String businessUnitId = "";
	
	@Column(name="RIOBJ")
	private String objectAccountCode = "";
	
	@Column(name="RISUB")
	private String subsidiaryCode = "";
	
	@Column(name="RISUBL")
	private String subledger = "";
	
	@Column(name="RISUBLT")
	private String subledgerType = "";
	
	@Column(name="RIDESB1")
	private String accountDescription = "";
	
	@Column(name="RIDESB2")
	private String description = "";
	
	@Column(name="RIOBID")
	private String objectId = "";
		
	@Override
	public ReceivableInvoicePK getId() {
		return getPk();
	}

	public ReceivableInvoicePK getPk() {
		return pk;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public String getInvoiceMatchStatus() {
		return invoiceMatchStatus;
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

	public String getCustomerId() {
		return customerId;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getSalesmanId() {
		return salesmanId;
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

	public String getObjectId() {
		return objectId;
	}

	void setPk(ReceivableInvoicePK pk) {
		this.pk = pk;
	}

	void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	void setInvoiceMatchStatus(String invoiceMatchStatus) {
		this.invoiceMatchStatus = invoiceMatchStatus;
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

	void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
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

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	void setDescription(String description) {
		this.description = description;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean isVoided() {
		return voided;
	}
	
	void voidDocument() {
		this.documentVoidStatus = "V";
		this.closedDate = LocalDate.now();
		this.openAmount = BigDecimal.ZERO;
	}

}
