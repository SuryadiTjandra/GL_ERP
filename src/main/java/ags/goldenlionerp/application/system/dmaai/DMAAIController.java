package ags.goldenlionerp.application.system.dmaai;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class DMAAIController {

	@Autowired
	private DMAAIService service;
	
	@PutMapping("/dmaai/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PutMapping("/dmaaiDetails/{id}")
	public ResponseEntity<?> noPutAllowedDetail(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping("/dmaai/{headerId}/details")
	public ResponseEntity<?> postDetails(
			@PathVariable("headerId") int headerId,
			@RequestBody List<Map<String, Object>> postedDetails){
		
		Collection<DMAAIDetail> result = service.saveDetailsToHeader(headerId, postedDetails);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/dmaai/{headerId}")
	public ResponseEntity<?> deleteHeader(@PathVariable("headerId") int headerId){
		service.deleteHeaderAndAllDetails(headerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
