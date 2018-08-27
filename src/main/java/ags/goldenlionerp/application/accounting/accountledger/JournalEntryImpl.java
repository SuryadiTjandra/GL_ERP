package ags.goldenlionerp.application.accounting.accountledger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.DatabaseEntity;
import static java.math.BigDecimal.ZERO;

public class JournalEntryImpl extends DatabaseEntity<JournalEntryPK> implements JournalEntry {

	@JsonUnwrapped
	private JournalEntryPK pk;
	
	private List<AccountingEntry> entries;
	
	@SuppressWarnings("unused")
	private JournalEntryImpl(){}
	
	public JournalEntryImpl(List<AccountingEntry> entries){
		setEntries(entries);
		
		String companyId = entries.get(0).getPk().getCompanyId();
		boolean allCompanySame = entries.stream()
									.allMatch(ae -> ae.getPk().getCompanyId().equals(companyId));
		
		int documentNumber = entries.get(0).getPk().getDocumentNumber();
		boolean allDocnoSame = entries.stream()
									.allMatch(ae -> ae.getPk().getDocumentNumber() == documentNumber);
		
		String documentType = entries.get(0).getPk().getDocumentType();
		boolean allDoctySame = entries.stream()
									.allMatch(ae -> ae.getPk().getDocumentType().equals(documentType));
		
		String ledgerType = entries.get(0).getPk().getLedgerType();
		entries.stream()
									.allMatch(ae -> ae.getPk().getLedgerType().equals(ledgerType));
		
		LocalDate entryDate = entries.get(0).getDocumentDate();
		boolean allDocdateSame = entries.stream().allMatch(ae -> ae.getDocumentDate().equals(entryDate));
		
		LocalDate glDate = entries.get(0).getGlDate();
		boolean allGldateSame = entries.stream().allMatch(ae -> ae.getGlDate().equals(glDate));
		
		int batchNo = entries.get(0).getBatchNumber();
		boolean allBatchNoSame = entries.stream().allMatch(ae -> ae.getBatchNumber() == batchNo);
		
		String batchType = entries.get(0).getBatchType();
		boolean allBatchTypeSame = entries.stream().allMatch(ae -> ae.getBatchType().equals(batchType));
		
		String description = entries.get(0).getDescription();
		boolean allDescSame = entries.stream().allMatch(ae -> ae.getDescription().equals(description));
		
		String doft = entries.get(0).getDomesticOrForeignTransaction();
		boolean allDoftSame = entries.stream().allMatch(ae -> ae.getDomesticOrForeignTransaction().equals(doft));
		
		int cap = entries.get(0).getCurrentAccountingPeriod();
		boolean allCapSame = entries.stream().allMatch(ae -> ae.getCurrentAccountingPeriod() == cap);
		
		int cfy = entries.get(0).getCurrentFiscalYear();
		boolean allCfySame = entries.stream().allMatch(ae -> ae.getCurrentFiscalYear() == cfy);
		
		boolean voided = entries.get(0).isVoided();
		boolean allVoidSame = entries.stream().allMatch(ae -> ae.isVoided() == voided);
		
		if (allCompanySame && allDocnoSame && allDoctySame && allDocdateSame && allGldateSame && allBatchNoSame 
			&& allBatchTypeSame && allDescSame && allDoftSame && allCapSame && allCfySame && allVoidSame) {
			JournalEntryPK pk = new JournalEntryPK(companyId, documentNumber, documentType, ledgerType);
			this.pk = pk;
			
			/*this.entryDate = entryDate;
			this.glDate = glDate;
			this.batchNumber = batchNo;
			this.batchType = batchType;
			this.description = description;
			this.domesticOrForeignTransaction = doft;
			this.currentAccountingPeriod = cap;
			this.currentFiscalYear = cfy;
			this.voided = voided;*/
		} else {
			throw new IllegalArgumentException("All entries must have the same common attributes");
		}
	}

	@Override
	public boolean isVoided() {
		boolean voided = entries.get(0).isVoided();
		if (entries.stream().anyMatch(ae -> ae.isVoided() != voided))
			throw new IllegalStateException("Not all entries have the same voided status");
		return voided;
	}
	
	@Override
	public void voidDocument() {
		entries.forEach(ae -> ae.voidDocument());
	}
	
	@Override
	public JournalEntryPK getId() {
		return getPk();
	}

	@Override
	public JournalEntryPK getPk() {
		return pk;
	}

	@Override
	public LocalDate getDocumentDate() {
		LocalDate entryDate = entries.get(0).getDocumentDate();
		if (entries.stream().anyMatch(ae -> !ae.getDocumentDate().equals(entryDate)))
			throw new IllegalStateException("Not all entries have the same entry date");
		return entryDate;
	}

	@Override
	public int getBatchNumber() {
		int batchNumber = entries.get(0).getBatchNumber();
		if (entries.stream().anyMatch(ae -> ae.getBatchNumber() != batchNumber))
			throw new IllegalStateException("Not all entries have the same batch number");
		return batchNumber;
	}
	
	@Override
	public String getBusinessUnitId() {
		String buId = entries.get(0).getBusinessUnitId();
		if (entries.stream().anyMatch(ae -> !ae.getBusinessUnitId().equals(buId)))
			throw new IllegalStateException("Not all entries belong to the same business unit");
		return buId;
	}
	
	@Override
	public String getBusinessPartnerId() {
		String bpId = entries.get(0).getBusinessPartnerId();
		if (entries.stream().anyMatch(ae -> !ae.getBusinessPartnerId().equals(bpId)))
			throw new IllegalStateException("Not all entries handle the same business partner");
		return bpId;
	}

	@Override
	public String getDescription() {
		return entries.get(0).getDescription();
	}

	@Override
	public String getDomesticOrForeignTransaction() {
		String doft = entries.get(0).getDomesticOrForeignTransaction();
		if (entries.stream().anyMatch(ae -> !ae.getDomesticOrForeignTransaction().equals(doft)))
			throw new IllegalStateException("Not all entries have the same type of transaction");
		return doft;
	}
	
	@Override
	public String getTransactionCurrency() {
		String tCur = entries.get(0).getTransactionCurrency();
		if (entries.stream().anyMatch(ae -> !ae.getTransactionCurrency().equals(tCur)))
			throw new IllegalStateException("Not all entries use the same currency");
		return tCur;
	}
	
	@Override
	public BigDecimal getExchangeRate() {
		BigDecimal exRt = entries.get(0).getExchangeRate();
		if (entries.stream().anyMatch(ae -> ae.getExchangeRate().compareTo(exRt) != 0))
			throw new IllegalStateException("Not all entries use the same exchange rate");
		return exRt;
	}

	@Override
	public int getCurrentAccountingPeriod() {
		int cap = entries.get(0).getCurrentAccountingPeriod();
		if (entries.stream().anyMatch(ae -> ae.getCurrentAccountingPeriod() != cap))
			throw new IllegalStateException("Not all entries belong in the same accounting period");
		return cap;
	}

	@Override
	public int getCurrentFiscalYear() {
		int cfy = entries.get(0).getCurrentFiscalYear();
		if (entries.stream().anyMatch(ae -> ae.getCurrentFiscalYear() != cfy))
			throw new IllegalStateException("Not all entries belong in the same fiscal year");
		return cfy;
	}
	
	@Override
	public String getInputUserId() {
		String userId = entries.get(0).getInputUserId();
		if (entries.stream().anyMatch(ae -> !ae.getInputUserId().equals(userId)))
			throw new IllegalStateException("Not all entries created by the same user");
		return userId;
	}
	
	@Override
	public String getLastUpdateUserId() {
		String userId = entries.get(0).getLastUpdateUserId();
		if (entries.stream().anyMatch(ae -> !ae.getLastUpdateUserId().equals(userId)))
			throw new IllegalStateException("Not all entries updated by the same user");
		return userId;
	}
	
	@Override
	public LocalDateTime getInputDateTime() {
		LocalDateTime dt = entries.get(0).getInputDateTime();
		if (entries.stream().anyMatch(ae -> !ae.getInputDateTime().equals(dt)))
			throw new IllegalStateException("Not all entries created at the same time");
		return dt;
	}
	
	@Override
	public LocalDateTime getLastUpdateDateTime() {
		LocalDateTime dt = entries.get(0).getLastUpdateDateTime();
		if (entries.stream().anyMatch(ae -> !ae.getLastUpdateDateTime().equals(dt)))
			throw new IllegalStateException("Not all entries updated at the same time");
		return dt;
	}

	@Override
	public List<AccountingEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}
	
	void setEntries(List<AccountingEntry> entries) {
		if (entries == null || entries.size() < 2) {
			throw new IllegalArgumentException("Must have at least two entries");
		}
		
		if (!checkTotalAmountEqualZero(entries)) {
			throw new IllegalArgumentException("Debit and credit total amounts must be the same");
		}
		
		Collections.sort(entries, Comparator.comparingInt((AccountingEntry ae) -> ae.getPk().getDocumentSequence()));

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

	
}
