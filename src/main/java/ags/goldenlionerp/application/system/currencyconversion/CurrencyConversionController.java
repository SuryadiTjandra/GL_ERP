package ags.goldenlionerp.application.system.currencyconversion;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RepositoryRestController
public class CurrencyConversionController {

	@PutMapping("/currencyConversions/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
