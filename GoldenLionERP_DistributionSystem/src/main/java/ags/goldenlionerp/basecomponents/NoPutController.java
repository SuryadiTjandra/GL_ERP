package ags.goldenlionerp.basecomponents;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class NoPutController {

	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
