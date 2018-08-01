package ags.goldenlionerp.application.setups.fiscalyear;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FiscalYearService {

	@Autowired
	private FiscalYearRepository repo;
	
	public FiscalYear extendFiscalYear(FiscalYearPK pk) throws ResourceNotFoundException, DataIntegrityViolationException{
		FiscalYear year = repo.findById(pk)
							.orElseThrow(() -> new ResourceNotFoundException());
		
		FiscalYearPK newPk = new FiscalYearPK(
									pk.getFiscalDatePattern(), 
									pk.getFiscalYear() + 1
								);
		
		if (repo.existsById(newPk))
			throw new DataIntegrityViolationException("The extension of fiscal year " + pk + " already exists! ");
		
		FiscalYear newYear = new FiscalYear();
		newYear.setPk(newPk);
		newYear.setStartDate(year.getStartDate().plusYears(1));
		for (int n = 1; n <= FiscalYear.NUMBER_OF_PERIOD; n++) {
			newYear.setEndDateOfPeriod(n, year.getEndDateOfPeriod(n).plusYears(1));
		}
		return repo.save(newYear);
	}
}
