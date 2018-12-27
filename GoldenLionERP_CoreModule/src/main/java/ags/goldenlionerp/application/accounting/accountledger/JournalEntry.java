package ags.goldenlionerp.application.accounting.accountledger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ags.goldenlionerp.entities.Voidable;

public interface JournalEntry extends Voidable {

	boolean isVoided();

	void voidDocument();

	JournalEntryPK getId();

	JournalEntryPK getPk();

	LocalDate getDocumentDate();

	int getBatchNumber();

	String getDescription();

	String getDomesticOrForeignTransaction();
	
	String getTransactionCurrency();
	
	BigDecimal getExchangeRate();
	
	String getBusinessUnitId();
	
	String getBusinessPartnerId();

	int getCurrentAccountingPeriod();

	int getCurrentFiscalYear();

	List<AccountingEntry> getEntries();

	

}