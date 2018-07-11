package ags.goldenlionerp.system.holiday;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RepositoryRestController
public class HolidayController {

	@PutMapping("/holidays/{id}")
	public ResponseEntity<?> noPutAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
