package ags.goldenlionerp.masterdata.itemLocation;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RepositoryRestController
public class ItemLocationController {
	@PutMapping("/itemLocations/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
