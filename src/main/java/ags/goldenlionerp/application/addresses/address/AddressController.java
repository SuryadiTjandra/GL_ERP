package ags.goldenlionerp.application.addresses.address;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class AddressController {
	
	@Autowired
	private AddressService service;

	@PutMapping("/addresses/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PatchMapping("/addresses/{id}")
	@ResponseBody
	public AddressBookMaster patch(@PathVariable("id") String id, @RequestBody Map<String, Object> patchRequest) throws Exception {
		return service.patch(id, patchRequest);
	}
}
