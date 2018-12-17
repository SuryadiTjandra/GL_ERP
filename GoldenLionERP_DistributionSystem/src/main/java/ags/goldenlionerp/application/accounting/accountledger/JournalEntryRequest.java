package ags.goldenlionerp.application.accounting.accountledger;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class JournalEntryRequest implements JournalEntry{
	
	private JournalEntryPK pk;
	
	private String businessUnitId;
	
	private LocalDate documentDate;
	
	private LocalDate glDate;
	
	private int batchNumber;
	
	private String batchType;
	
	private String businessPartnerId;
	
	private String description;
	
	private String transactionCurrency;
	
	private BigDecimal exchangeRate;
	
	private int currentAccountingPeriod;
	
	private int currentFiscalYear;
	
	private boolean voided = false;
	
	private List<AccountingEntry> entries;

	@JsonCreator
	public JournalEntryRequest(
			@JsonProperty("entries") List<AccountingEntry> entries,
			@JsonProperty("companyId") String companyId,
			@JsonProperty("documentNumber") int documentNumber,
			@JsonProperty("documentType") String documentType,
			@JsonProperty("ledgerType") String ledgerType) {
		this.entries = entries.isEmpty() ? Collections.emptyList() : entries;
		JournalEntryPK pk = new JournalEntryPK(companyId, documentNumber, documentType, ledgerType);
		setPk(pk);
	}
	
	/*
	@JsonCreator
	public JournalEntryRequest(
			@JsonProperty("companyId") String companyId, 
			@JsonProperty("documentNumber") int documentNumber, 
			@JsonProperty("documentType") String documentType, 
			@JsonProperty("ledgerType") String ledgerType,
			@JsonProperty("entryDate") LocalDate entryDate,
			@JsonProperty("glDate") LocalDate glDate, 
			@JsonProperty("batchNumber") int batchNumber, 
			@JsonProperty("batchType") String batchType, 
			@JsonProperty("description") String description,
			@JsonProperty("transactionCurrency") String transactionCurrency, 
			@JsonProperty("currentAccountingPeriod") int currentAccountingPeriod,
			@JsonProperty("currentFiscalYear") int currentFiscalYear,
			@JsonProperty("entries") List<AccountingEntry> entries) {
		setEntries(entries);
		
		JournalEntryPK pk = new JournalEntryPK(companyId, documentNumber, documentType, ledgerType);
		setPk(pk);
		
		setEntryDate(entryDate);
		//setGlDate(glDate);
		setBatchNumber(batchNumber);
		setBatchType(batchType);
		setDescription(description);
		setTransactionCurrency(transactionCurrency);
		setCurrentAccountingPeriod(currentAccountingPeriod);
		setCurrentFiscalYear(currentFiscalYear);
	}*/
	
	public JournalEntryPK getPk() {
		return pk;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public LocalDate getDocumentDate() {
		return documentDate;
	}

	public LocalDate getGlDate() {
		return glDate;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public String getBatchType() {
		return batchType;
	}

	public String getBusinessPartnerId() {
		return businessPartnerId;
	}

	public String getDescription() {
		return description;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public int getCurrentAccountingPeriod() {
		return currentAccountingPeriod;
	}

	public int getCurrentFiscalYear() {
		return currentFiscalYear;
	}

	public boolean isVoided() {
		return voided;
	}

	public List<AccountingEntry> getEntries() {
		return entries;
	}
	
	void setPk(JournalEntryPK pk) {
		this.pk = pk;
		this.entries.forEach(ae -> {
			AccountingEntryPK aepk = ae.getPk();
			AccountingEntryPK newAepk = new AccountingEntryPK(
					pk.getCompanyId(), 
					pk.getDocumentNumber(), 
					pk.getDocumentType(), 
					aepk != null ? aepk.getDocumentSequence() : 0, //accounting entry can have null pk during jackson desrialization
					pk.getLedgerType());
			ae.setPk(newAepk);
		});
	}

	void setDocumentDate(LocalDate entryDate) {
		this.documentDate = entryDate;
		this.entries.forEach(ae -> ae.setDocumentDate(entryDate));
	}

	void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
		this.entries.forEach(ae -> ae.setBatchNumber(batchNumber));
	}

	void setBatchType(String batchType) {
		this.batchType = batchType;
		this.entries.forEach(ae -> ae.setBatchType(batchType));
	}

	void setDescription(String description) {
		this.description = description;
		this.entries.forEach(ae -> ae.setDescription(description));
	}
	
	void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
		this.entries.forEach(ae -> ae.setTransactionCurrency(transactionCurrency));
	}
	
	void setDomesticOrForeignTransaction(String doft) {
		this.entries.forEach(ae -> ae.setDomesticOrForeignTransaction(doft));
	}

	void setCurrentAccountingPeriod(int currentAccountingPeriod) {
		this.currentAccountingPeriod = currentAccountingPeriod;
		this.entries.forEach(ae -> ae.setCurrentAccountingPeriod(currentAccountingPeriod));
	}

	void setCurrentFiscalYear(int currentFiscalYear) {
		this.currentFiscalYear = currentFiscalYear;
		this.entries.forEach(ae -> ae.setCurrentFiscalYear(currentFiscalYear));
	}
	
	void setEntries(List<AccountingEntry> entries) {
		if (entries == null || entries.size() < 2) {
			throw new IllegalArgumentException("Must have at least two entries");
		}
		
		if (!checkTotalAmountEqualZero(entries)) {
			throw new IllegalArgumentException("Debit and credit total amounts must be the same");
		}

		this.entries = entries;
	}

	
	private boolean checkTotalAmountEqualZero(List<AccountingEntry> entries) {
		BigDecimal total = entries.stream()
							.map(AccountingEntry::getAmount)
							.reduce(BigDecimal::add)
							.get();
		return total.compareTo(ZERO) == 0;
	}
	
	void setBaseCurrency(String baseCurrency) {
		this.entries.forEach(ae -> ae.setBaseCurrency(baseCurrency));
	}

	@Override
	public void voidDocument() {
		throw new UnsupportedOperationException("Cannot call this method from this JournalEntry subclass");
	}

	@Override
	public JournalEntryPK getId() {
		return getPk();
	}

	@Override
	public String getDomesticOrForeignTransaction() {
		throw new UnsupportedOperationException("Cannot call this method from this JournalEntry subclass");
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
		this.entries.forEach(ae -> ae.setBusinessUnitId(businessUnitId));
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
		this.entries.forEach(ae -> ae.setGlDate(glDate));
	}

	void setBusinessPartnerId(String businessPartnerId) {
		this.businessPartnerId = businessPartnerId;
		this.entries.forEach(ae -> ae.setBusinessPartnerId(businessPartnerId));
	}

	void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
		this.entries.forEach(ae -> ae.setExchangeRate(exchangeRate));
	}

	
}
