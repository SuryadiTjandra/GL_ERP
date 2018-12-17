package ags.goldenlionerp.application.accounting.accountledger;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository repo;
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private NextNumberService nnServ;
	
	public JournalEntry createJournalEntry(JournalEntryRequest request) {
		request = setPk(request);
		request = setDomesticOrForeign(request);
		request = setEntrySequences(request);
		
		return request;
	}
	
	private JournalEntryRequest setPk(JournalEntryRequest request) {
		JournalEntryPK oldPk = request.getPk();
		
		Company company = compRepo.findById(request.getPk().getCompanyId())
							.orElseThrow(() -> new ResourceNotFoundException("Could not find company with id " + request.getPk().getCompanyId()));
							
		String baseCur = company.getCurrencyCodeBase();
		String tranCur = request.getTransactionCurrency();
		
		int docNo = oldPk.getDocumentNumber();
		if (docNo == 0) {
			docNo = nnServ.findNextDocumentNumber(request.getPk().getCompanyId(), request.getPk().getDocumentType(), YearMonth.now());
		}
		JournalEntryPK newPk = new JournalEntryPK(
					oldPk.getCompanyId(),
					docNo,
					oldPk.getDocumentType(),
					baseCur.equals(tranCur) ? "AA" : "CA" //TODO find where these come from
				);
		request.setPk(newPk);
		return request;
	}
	
	private JournalEntryRequest setDomesticOrForeign(JournalEntryRequest request) {
		Company company = compRepo.findById(request.getPk().getCompanyId())
							.orElseThrow(() -> new ResourceNotFoundException("Could not find company with id " + request.getPk().getCompanyId()));
		
		String baseCur = company.getCurrencyCodeBase();
		request.setBaseCurrency(baseCur);
		
		String tranCur = request.getTransactionCurrency();
		String doft = tranCur.equals(baseCur) ? "D" : "F";
		request.setDomesticOrForeignTransaction(doft);
		
		return request;
	}
	
	private JournalEntryRequest setEntrySequences(JournalEntryRequest request) {
		for (int i = 0; i < request.getEntries().size(); i++) {
			AccountingEntryPK aepk = new AccountingEntryPK(
					request.getPk().getCompanyId(),
					request.getPk().getDocumentNumber(),
					request.getPk().getDocumentType(),
					(i + 1) * 10,
					request.getPk().getLedgerType()
				);
			request.getEntries().get(i).setPk(aepk);
		}
		
		return request;
	}
	
	public JournalEntry voidEntry(JournalEntryPK pk) {
		JournalEntry journal = repo.findById(pk)
								.orElseThrow(() -> new ResourceNotFoundException("Couldn't find the journal entry with id " + pk));
		journal.voidDocument();
		repo.save(journal);
		
		return repo.findIncludeVoided(pk).get();
	}
}
