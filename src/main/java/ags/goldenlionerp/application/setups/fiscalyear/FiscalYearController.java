package ags.goldenlionerp.application.setups.fiscalyear;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RepositoryRestController
public class FiscalYearController {

	@Autowired
	private FiscalYearService service;
	@Autowired
	private FiscalYearPKIdConverter converter;
	@Autowired
	private RepositoryEntityLinks links;

	@PutMapping("/fiscalYears/{id}")
	public ResponseEntity<?> noPutAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@PostMapping("/fiscalYears/{id}/extend")
	public ResponseEntity<?> extend(@PathVariable("id") String idOfOldYear, PersistentEntityResourceAssembler assembler) {
		FiscalYearPK pk = (FiscalYearPK) converter.fromRequestId(idOfOldYear, FiscalYearPK.class);
		try {
			FiscalYear newYear = service.extendFiscalYear(pk);
			Link newYearLink = links.linkToSingleResource(FiscalYear.class, newYear.getPk());
			URI newYearUri = URI.create(newYearLink.getHref());
			return ResponseEntity.created(newYearUri).body(assembler.toResource(newYear));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
}
