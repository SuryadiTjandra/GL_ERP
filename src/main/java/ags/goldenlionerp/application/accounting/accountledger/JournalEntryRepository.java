package ags.goldenlionerp.application.accounting.accountledger;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.entities.UndeleteableRepository;
import ags.goldenlionerp.entities.VoidableRepository;

//@RepositoryRestResource(collectionResourceRel="journalEntries", path="journalEntries")
@NoRepositoryBean
public interface JournalEntryRepository extends 
	VoidableRepository<JournalEntry, JournalEntryPK>,
	UndeleteableRepository<JournalEntry, JournalEntryPK>{

}
