package ags.goldenlionerp.application.accounting.accountledger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@RequestMapping("/api/journalEntries/")
public class JournalEntryController {

	@Autowired
	private JournalEntryRepository repo;
	@Autowired
	private JournalEntryIdConverter conv;
	@Autowired
	private RepositoryRestConfiguration config;
	
	@RequestMapping(path="/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@GetMapping("/{id}") @ResponseBody
	public Resource<?> getSingle(HttpServletRequest request, @PathVariable("id") String id){
		JournalEntryPK pk = (JournalEntryPK) conv.fromRequestId(id, JournalEntryPK.class);
		JournalEntry journal = repo.findById(pk)
									.orElseThrow(() -> new ResourceNotFoundException());
		
		Link link = new Link(request.getRequestURL().toString());
		Resource<JournalEntry> jourRes = new Resource<>(journal, link);
		return jourRes;
	}
}
