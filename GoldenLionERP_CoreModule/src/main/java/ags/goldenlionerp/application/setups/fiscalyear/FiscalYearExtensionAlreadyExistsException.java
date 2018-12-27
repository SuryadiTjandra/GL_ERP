package ags.goldenlionerp.application.setups.fiscalyear;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
class FiscalYearExtensionAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1045443746237975313L;

	public FiscalYearExtensionAlreadyExistsException(String message) {
		super(message);
	}
}
