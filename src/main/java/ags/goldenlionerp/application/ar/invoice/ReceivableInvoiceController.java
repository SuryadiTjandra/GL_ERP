package ags.goldenlionerp.application.ar.invoice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.support.DefaultedPageable;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
@RequestMapping("/invoices/")
public class ReceivableInvoiceController {

	@Autowired
	private ReceivableInvoiceRepository repo;
	@Autowired
	private ReceivableInvoiceService service;
	@Autowired
	private ReceivableInvoiceIdConverter converter;
	@Autowired
	private RepositoryEntityLinks links;
	
	@RequestMapping(path="/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping
	public ResponseEntity<?> post(@RequestBody ReceivableInvoice invoiceRequest, 
			PersistentEntityResourceAssembler assembler) throws URISyntaxException{
		if (repo.existsById(invoiceRequest.getId()))
			throw new ResourceAlreadyExistsException("invoice", invoiceRequest.getId());
		
		ReceivableInvoice createdInvoice = service.create(invoiceRequest);
		Resource<?> invAsResource = assembler.toFullResource(createdInvoice);
		URI createdLink = new URI(links.linkToSingleResource(ReceivableInvoice.class, createdInvoice.getId()).getHref());
		return ResponseEntity.created(createdLink).body(invAsResource);
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Resource<?> getSingle(
			@PathVariable("id") String id, 
			@RequestParam(name="includeVoided", defaultValue="false") boolean includeVoided,
			PersistentEntityResourceAssembler assembler){
		
		ReceivableInvoicePK pk = (ReceivableInvoicePK) converter.fromRequestId(id, ReceivableInvoicePK.class);
		
		Optional<ReceivableInvoice> invoice = includeVoided ?
				repo.findIncludeVoided(pk) : repo.findById(pk);

		return assembler.toFullResource(invoice.orElseThrow(() -> new ResourceNotFoundException()));
	}
	
	@GetMapping @ResponseBody
	public Resources<?> getCollection(
			@RequestParam(name="includeVoided", defaultValue="false") boolean includeVoided,
			DefaultedPageable pageable,
			Sort sort,
			PersistentEntityResourceAssembler assembler){
		
		Page<ReceivableInvoice> page = includeVoided ?
				repo.findAllIncludeVoided(pageable.getPageable()):
				repo.findAll(pageable.getPageable());
				
		PagedResourcesAssembler<ReceivableInvoice> pagedAssembler = new PagedResourcesAssembler<>(null, null);
		return pagedAssembler.toResource(page);
		//return assembler.toFullResource(list);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> voidInvoice(@PathVariable("id") String id){
		ReceivableInvoicePK pk = (ReceivableInvoicePK) converter.fromRequestId(id, ReceivableInvoicePK.class);
		service.voidInvoice(pk);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
