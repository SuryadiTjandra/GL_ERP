package ags.goldenlionerp.application.accounting.accountledger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository repo;
	
	public JournalEntry voidEntry(JournalEntryPK pk) {
		JournalEntry journal = repo.findById(pk)
								.orElseThrow(() -> new ResourceNotFoundException("Couldn't find the journal entry with id " + pk));
		journal.voidDocument();
		repo.save(journal);
		
		return repo.findIncludeVoided(pk).get();
	}
}
