package ags.goldenlionerp.application.addresses.phone;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
public class PhoneNumberController extends NoPutController {

	@Autowired
	private PhoneNumberService service;
	
	@Override @PutMapping("/phoneNumbers/{id}")
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
	
	@PostMapping("/addresses/{id}/phoneNumbers")
	@ResponseBody
	public Collection<PhoneNumber> massPost(
			@PathVariable("id") String addressNumber, 
			@RequestBody Collection<Map<String, Object>> requests){
		return service.savePhoneNumbersForAddress(addressNumber, requests);
	}

}
