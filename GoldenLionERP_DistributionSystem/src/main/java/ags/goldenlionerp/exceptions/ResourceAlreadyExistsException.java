package ags.goldenlionerp.exceptions;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -7362782847942096815L;

	public ResourceAlreadyExistsException(String resourceName, Serializable id) {
		super("Resource " + resourceName + " with id " + id + "already exists.");
	}
}
