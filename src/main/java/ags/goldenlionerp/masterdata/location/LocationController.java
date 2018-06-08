package ags.goldenlionerp.masterdata.location;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@RepositoryRestController
public class LocationController {

	@Autowired
	private LocationService service;
	@Autowired
	private LocationRepository repo;
	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	
	@PutMapping("/locations/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PutMapping("/businessUnits/{id}/locations")
	public ResponseEntity<?> putBusinessUnitLocations(@PathVariable String id, @RequestBody Collection<LocationMaster> locations) throws Exception{
		
		service.putCollection(id, locations);
		Collection<LocationMaster> updatedLocations = repo.findByPk_BusinessUnitId(id);
		return new ResponseEntity<>(mapper.writeValueAsString(updatedLocations), HttpStatus.OK);
	}
}
