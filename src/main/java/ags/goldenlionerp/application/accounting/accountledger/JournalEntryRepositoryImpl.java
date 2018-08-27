package ags.goldenlionerp.application.accounting.accountledger;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryRepositoryImpl implements JournalEntryRepository {

	@Autowired
	private AccountingEntryRepository entryRepo;
	
	@Override
	public Page<JournalEntry> findAllIncludeVoided(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<JournalEntry> findIncludeVoided(JournalEntryPK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<JournalEntry> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<JournalEntry> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<JournalEntry> findById(JournalEntryPK id) {
		List<AccountingEntry> entries = entryRepo.findByJournalId
				(id.getCompanyId(), id.getDocumentNumber(), id.getDocumentType(), id.getLedgerType());
		
		if (entries.isEmpty())
			return Optional.empty();
		
		JournalEntryImpl journal = new JournalEntryImpl(entries);
		return Optional.of(journal);
	}

	@Override
	public Iterable<JournalEntry> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<JournalEntry> findAllById(Iterable<JournalEntryPK> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends JournalEntry> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JournalEntry> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(JournalEntryPK id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(JournalEntry entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends JournalEntry> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
