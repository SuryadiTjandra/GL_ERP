package ags.goldenlionerp.application.accounting.accountledger;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import ags.goldenlionerp.entities.UndeleteableRepository;
import ags.goldenlionerp.entities.VoidableRepository;

@RestResource(exported=false)
public interface AccountingEntryRepository extends 
	VoidableRepository<AccountingEntry, AccountingEntryPK>,
	UndeleteableRepository<AccountingEntry, AccountingEntryPK>{

	@Query("SELECT ae FROM AccountingEntry ae "
			+ "WHERE ae.pk.companyId = ?1 "
			+ "AND ae.pk.documentNumber = ?2 "
			+ "AND ae.pk.documentType = ?3 "
			+ "AND ae.pk.ledgerType = ?4")
	List<AccountingEntry> findByJournalId(String companyId, int docNo, String docType, String ledgerType);
}
