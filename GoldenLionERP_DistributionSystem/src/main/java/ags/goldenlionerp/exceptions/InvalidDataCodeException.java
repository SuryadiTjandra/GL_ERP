package ags.goldenlionerp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidDataCodeException extends RuntimeException {

	private static final long serialVersionUID = -2673769218901542584L;

	public InvalidDataCodeException(String message) {
		super(message);
	}
}
