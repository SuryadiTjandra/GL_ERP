package ags.goldenlionerp.application.system.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class PasswordConfirmFailException extends RuntimeException {

	private static final long serialVersionUID = -6884817556682604251L;
	private static final String DEFAULT_MESSAGE = "Password and password confirmation do not match.";

	PasswordConfirmFailException(String message) {
		super(message);
	}
	
	PasswordConfirmFailException() {
		super(DEFAULT_MESSAGE);
	}
}
