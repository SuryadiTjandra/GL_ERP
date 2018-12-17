package ags.goldenlionerp.application.accounting.accountledger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@RepositoryRestController
@RequestMapping("/api/journalEntries/")
public class JournalEntryController {

	@Autowired
	private JournalEntryRepository repo;
	@Autowired
	private JournalEntryIdConverter conv;
	@Autowired
	private JournalEntryService service;

	
	@RequestMapping(path="/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@GetMapping("/{id}") @ResponseBody
	public Resource<?> getSingle(HttpServletRequest request, 
			@PathVariable("id") String id, 
			@RequestParam(name="includeVoided", defaultValue="false") boolean includeVoided){
		JournalEntryPK pk = (JournalEntryPK) conv.fromRequestId(id, JournalEntryPK.class);
		JournalEntry journal = (includeVoided ? repo.findIncludeVoided(pk) : repo.findById(pk))
									.orElseThrow(() -> new ResourceNotFoundException());
		
		Link link = new Link(request.getRequestURL().toString());
		Resource<JournalEntry> jourRes = new Resource<>(journal, link);
		return jourRes;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> voidDocument(@PathVariable("id") String id){
		JournalEntryPK pk = (JournalEntryPK) conv.fromRequestId(id, JournalEntryPK.class);
		service.voidEntry(pk);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<?> postJournal(@RequestBody JournalEntryRequest request){
		if (repo.findIncludeVoided(request.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException("Journal Entry", request.getId());
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
}
