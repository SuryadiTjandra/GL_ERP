package ags.goldenlionerp.application.item.uomconversion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidUomConversionException extends RuntimeException {

	private static final long serialVersionUID = 4724486310043181161L;

	public InvalidUomConversionException(String uomFrom, String uomTo) {
		super("Could not convert from unit " + uomFrom + " to unit " + uomTo);
	}
	
	public InvalidUomConversionException(String message) {
		super(message);
	}
}
