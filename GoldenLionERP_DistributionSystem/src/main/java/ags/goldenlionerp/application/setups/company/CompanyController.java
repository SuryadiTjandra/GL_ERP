package ags.goldenlionerp.application.setups.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class CompanyController {
	@Autowired CompanyRepository repo;
	
	@PutMapping(value="/companies/{companyId}")
	public ResponseEntity<?> noPutAllowed(@PathVariable("companyId") String companyId) {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@PostMapping(path="/companies", consumes="application/json")
	public ResponseEntity<?> checkPost(@RequestBody Company company){
		if (repo.existsById(company.getCompanyId())) {
			return new ResponseEntity<>("The requested entity cannot be created because there is another entity with the id", HttpStatus.CONFLICT);
		}
		
		repo.save(company);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
