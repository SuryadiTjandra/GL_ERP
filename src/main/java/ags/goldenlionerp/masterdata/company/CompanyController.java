package ags.goldenlionerp.masterdata.company;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
//@RequestMapping("/api/companies/")
public class CompanyController {

	@PutMapping(value="/companies/{companyId}")
	public ResponseEntity<?> noPutAllowed(@PathVariable("companyId") String companyId) {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
}
