package ags.goldenlionerp.application.setups.businessunit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class BusinessUnitController {

	@Autowired BusinessUnitRepository repo;
	
	@PutMapping("/businessUnits/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping(path="/businessUnits/", consumes="application/json")
	public ResponseEntity<?> createBusinessUnit(@RequestBody BusinessUnit bu){
		if (repo.existsById(bu.getBusinessUnitId()))
			return new ResponseEntity<>("The requested entity cannot be created because there is another entity with the id",HttpStatus.CONFLICT);
		
		repo.save(bu);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
