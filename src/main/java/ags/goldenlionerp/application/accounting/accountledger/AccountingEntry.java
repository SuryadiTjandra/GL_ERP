package ags.goldenlionerp.application.accounting.accountledger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import ags.goldenlionerp.application.ar.invoice.VoidedAttributeConverter;
import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.entities.Voidable;

@Entity
@Table(name="T0911")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="ALUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="ALDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="ALTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="ALUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="ALDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="ALTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="ALCID")),
})
public class AccountingEntry extends DatabaseEntity<AccountingEntryPK> implements Voidable{

	@EmbeddedId
	private AccountingEntryPK pk;
	
	@Column(name="ALBUID")
	private String businessUnitId = "";
	
	@Column(name="ALICU")
	private int batchNumber;
	
	@Column(name="ALICUT")
	private String batchType = "";
	
	@Column(name="ALDOCDT")
	private LocalDate documentDate;
	
	@Column(name="ALGLDT")
	private LocalDate glDate;
	
	@Column(name="ALOBJ")
	private String objectAccountCode = "";
	
	@Column(name="ALSUB")
	private String subsidiaryCode = "";
	
	@Column(name="ALSUBL")
	private String subledger = "";
	
	@Column(name="ALSUBLT")
	private String subledgerType = "";
	
	@Column(name="ALACID")
	private String accountId = "";
	
	@Column(name="ALDESB1")
	private String accountDescription = "";
	
	@Column(name="ALDESB2")
	private String description = "";
	
	@Column(name="ALCRCB")
	private String baseCurrency = "";
	
	@Column(name="ALCRCT")
	private String transactionCurrency = "";
	
	@Column(name="ALEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="ALPOSTED")
	private String postStatus = "";
	
	@Column(name="ALAMNT", precision=19, scale=5)
	private BigDecimal amount = BigDecimal.ZERO;
	
	@Column(name="ALQTY", precision=19, scale=5)
	private BigDecimal quantity = BigDecimal.ONE;
	
	@Column(name="ALUOM")
	private String unitOfMeasure;
	
	@Column(name="ALORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="ALORDOCTY")
	private String originalDocumentType;
	
	@Column(name="ALORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="ALANUM")
	private String businessPartnerId;
	
	@Column(name="ALASID")
	private String assetId;
	
	@Column(name="ALINUM")
	private String itemNumber;
	
	@Column(name="ALDOFT")
	private String domesticOrForeignTransaction;
	
	@Column(name="ALCFTY")
	private String cashflowType;
	
	@Column(name="ALIPST")
	private String interconnectionPostingStatus;
	
	@Column(name="ALDVS")
	private String documentVoidStatus;
	@Column(name="ALDVS", insertable=false, updatable=false)
	@Convert(converter=VoidedAttributeConverter.class)
	private boolean voided;
	
	@Column(name="ALCAP")
	private int currentAccountingPeriod;
	
	@Column(name="ALCFY")
	private int currentFiscalYear;
	
	@Column(name="ALRECC")
	private String reconcileCode;
	
	@Column(name="ALGLF01")
	private String generalLedgerFlag01;
	
	@Column(name="ALGLF02")
	private String generalLedgerFlag02;
	
	@Column(name="ALGLF03")
	private String generalLedgerFlag03;
	
	@Column(name="ALGLF04")
	private String generalLedgerFlag04;
	
	@Column(name="ALGLF05")
	private String generalLedgerFlag05;
	
	@Column(name="ALGLF06")
	private String generalLedgerFlag06;
	
	@Column(name="ALGLF07")
	private String generalLedgerFlag07;
	
	@Column(name="ALGLF08")
	private String generalLedgerFlag08;
	
	@Column(name="ALGLF09")
	private String generalLedgerFlag09;
	
	@Column(name="ALGLF10")
	private String generalLedgerFlag10;
	
	@Column(name="ALDLNA")
	private Boolean deleteNotAllowed;
	
	@Column(name="ALOBID")
	private String objectId;
	
	@Column(name="ALRECID")
	private String recordId = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	
	@Override
	public AccountingEntryPK getId() {
		return getPk();
	}

	public AccountingEntryPK getPk() {
		return pk;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public String getBatchType() {
		return batchType;
	}

	public LocalDate getDocumentDate() {
		return documentDate;
	}

	public LocalDate getGlDate() {
		return glDate;
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

	public String getAccountId() {
		return accountId;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public String getDescription() {
		return description;
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

	public String getPostStatus() {
		return postStatus;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
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

	public String getBusinessPartnerId() {
		return businessPartnerId;
	}

	public String getAssetId() {
		return assetId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public String getDomesticOrForeignTransaction() {
		return domesticOrForeignTransaction;
	}

	public String getCashflowType() {
		return cashflowType;
	}

	public String getInterconnectionPostingStatus() {
		return interconnectionPostingStatus;
	}

	public String getDocumentVoidStatus() {
		return documentVoidStatus;
	}

	public int getCurrentAccountingPeriod() {
		return currentAccountingPeriod;
	}

	public int getCurrentFiscalYear() {
		return currentFiscalYear;
	}

	public String getReconcileCode() {
		return reconcileCode;
	}

	public String getGeneralLedgerFlag01() {
		return generalLedgerFlag01;
	}

	public String getGeneralLedgerFlag02() {
		return generalLedgerFlag02;
	}

	public String getGeneralLedgerFlag03() {
		return generalLedgerFlag03;
	}

	public String getGeneralLedgerFlag04() {
		return generalLedgerFlag04;
	}

	public String getGeneralLedgerFlag05() {
		return generalLedgerFlag05;
	}

	public String getGeneralLedgerFlag06() {
		return generalLedgerFlag06;
	}

	public String getGeneralLedgerFlag07() {
		return generalLedgerFlag07;
	}

	public String getGeneralLedgerFlag08() {
		return generalLedgerFlag08;
	}

	public String getGeneralLedgerFlag09() {
		return generalLedgerFlag09;
	}

	public String getGeneralLedgerFlag10() {
		return generalLedgerFlag10;
	}

	public boolean isDeleteNotAllowed() {
		return deleteNotAllowed;
	}

	void setPk(AccountingEntryPK pk) {
		this.pk = pk;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	void setDocumentDate(LocalDate documentDate) {
		this.documentDate = documentDate;
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
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

	void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	void setTransactionCurrency(String transacyionCurrency) {
		this.transactionCurrency = transacyionCurrency;
	}

	void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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

	void setBusinessPartnerId(String businessPartnerId) {
		this.businessPartnerId = businessPartnerId;
	}

	void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	void setDomesticOrForeignTransaction(String domesticOrForeignTransaction) {
		this.domesticOrForeignTransaction = domesticOrForeignTransaction;
	}

	void setCashflowType(String cashflowType) {
		this.cashflowType = cashflowType;
	}

	void setInterconnectionPostingStatus(String interconnectionPostingStatus) {
		this.interconnectionPostingStatus = interconnectionPostingStatus;
	}

	void setDocumentVoidStatus(String documentVoidStatus) {
		this.documentVoidStatus = documentVoidStatus;
	}

	void setCurrentAccountingPeriod(int currentAccountingPeriod) {
		this.currentAccountingPeriod = currentAccountingPeriod;
	}

	void setCurrentFiscalYear(int currentFiscalYear) {
		this.currentFiscalYear = currentFiscalYear;
	}

	void setReconcileCode(String reconcileCode) {
		this.reconcileCode = reconcileCode;
	}

	void setGeneralLedgerFlag01(String generalLedgerFlag01) {
		this.generalLedgerFlag01 = generalLedgerFlag01;
	}

	void setGeneralLedgerFlag02(String generalLedgerFlag02) {
		this.generalLedgerFlag02 = generalLedgerFlag02;
	}

	void setGeneralLedgerFlag03(String generalLedgerFlag03) {
		this.generalLedgerFlag03 = generalLedgerFlag03;
	}

	void setGeneralLedgerFlag04(String generalLedgerFlag04) {
		this.generalLedgerFlag04 = generalLedgerFlag04;
	}

	void setGeneralLedgerFlag05(String generalLedgerFlag05) {
		this.generalLedgerFlag05 = generalLedgerFlag05;
	}

	void setGeneralLedgerFlag06(String generalLedgerFlag06) {
		this.generalLedgerFlag06 = generalLedgerFlag06;
	}

	void setGeneralLedgerFlag07(String generalLedgerFlag07) {
		this.generalLedgerFlag07 = generalLedgerFlag07;
	}

	void setGeneralLedgerFlag08(String generalLedgerFlag08) {
		this.generalLedgerFlag08 = generalLedgerFlag08;
	}

	void setGeneralLedgerFlag09(String generalLedgerFlag09) {
		this.generalLedgerFlag09 = generalLedgerFlag09;
	}

	void setGeneralLedgerFlag10(String generalLedgerFlag10) {
		this.generalLedgerFlag10 = generalLedgerFlag10;
	}

	void setDeleteNotAllowed(boolean deleteNotAllowed) {
		this.deleteNotAllowed = deleteNotAllowed;
	}

	@Override
	public boolean isVoided() {
		return voided;
	}
	
	public void voidDocument() {
		this.documentVoidStatus = "V";
	}
	

}
