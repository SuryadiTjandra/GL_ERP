package ags.goldenlionerp.application.setups.branchplantconstant;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class BranchPlantConstantController {

	@Autowired
	private BranchPlantConstantRepository repo;
	
	@PutMapping(path="/businessUnits/{id}/configuration", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createWithPutOnAssociation(@PathVariable String id, @RequestBody BranchPlantConstant config) {
		if (repo.existsById(id))
			throw new EntityExistsException("A configuration for BusinessUnit "+ id + " already exists!");
		
		config.setBranchCode(id);
		repo.save(config);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
