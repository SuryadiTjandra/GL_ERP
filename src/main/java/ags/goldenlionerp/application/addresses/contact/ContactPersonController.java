package ags.goldenlionerp.application.addresses.contact;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class ContactPersonController {

	@Autowired
	private ContactPersonService service;
	
	@PutMapping("/contacts/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping("/addresses/{id}/contacts")
	@ResponseBody
	public Collection<ContactPerson> massPost(
			@PathVariable("id") String addressNumber, 
			@RequestBody Collection<Map<String, Object>> patchRequests){
		return service.saveContactsForAddress(addressNumber, patchRequests);
	}
}
