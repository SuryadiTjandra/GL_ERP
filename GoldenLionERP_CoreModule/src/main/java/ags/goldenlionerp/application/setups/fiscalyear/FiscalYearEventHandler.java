package ags.goldenlionerp.application.setups.fiscalyear;

import java.time.LocalDate;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class FiscalYearEventHandler {

	@HandleBeforeCreate
	public void setFieldValues(FiscalYear fy) {
		//set the values of the fields to the proper values gotten from the getter method
		for (int n = 1; n <= FiscalYear.NUMBER_OF_PERIOD; n++) {
			fy.setEndDateOfPeriod(n, fy.getEndDateOfPeriod(n));
		}
	}
	
	@HandleBeforeSave
	public void checkNoPeriodOverlap(FiscalYear fy) {
		//check that no periods overlap with each other
		LocalDate prev = fy.getStartDate();
		for (int n = 1; n <= FiscalYear.NUMBER_OF_PERIOD; n++) {
			LocalDate cur = fy.getEndDateOfPeriod(n);
			if (cur.compareTo(prev) < 0)
				throw new FiscalYearInvalidPeriodException("End date of period must not be earlier than the previous period: Period " + n);
			
			prev = cur;
		}
	}
}
