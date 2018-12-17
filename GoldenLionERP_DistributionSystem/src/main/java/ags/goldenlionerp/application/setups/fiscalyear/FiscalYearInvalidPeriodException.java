package ags.goldenlionerp.application.setups.fiscalyear;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class FiscalYearInvalidPeriodException extends RuntimeException{

	private static final long serialVersionUID = -8107803608078144721L;

	public FiscalYearInvalidPeriodException(String message) {
		super(message);
	}
}
