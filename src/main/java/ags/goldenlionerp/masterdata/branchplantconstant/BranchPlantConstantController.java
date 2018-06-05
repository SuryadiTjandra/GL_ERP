package ags.goldenlionerp.masterdata.branchplantconstant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/businessUnits/{id}/configuration")
public class BranchPlantConstantController {
	
	@Autowired private BranchPlantConstantService service;
	
	@PostMapping
	public ResponseEntity<?> postConfiguration(@PathVariable String id, @RequestBody BranchPlantConstant config){
		service.createConfiguration(id, config);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<?> putConfiguration(@PathVariable String id, @RequestBody BranchPlantConstant config){
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<?> patchConfiguration(@PathVariable String id, @RequestBody BranchPlantConstant config){
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteConfiguration(@PathVariable String id, @RequestBody BranchPlantConstant config){
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
