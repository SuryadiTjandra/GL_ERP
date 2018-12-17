package ags.goldenlionerp.application.ar.invoice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyVoidedException extends RuntimeException {

	private static final long serialVersionUID = -6763261753820635559L;

	public AlreadyVoidedException(String message) {
		super(message);
	}
}
