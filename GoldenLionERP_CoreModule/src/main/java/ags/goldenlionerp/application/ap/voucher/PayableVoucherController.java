package ags.goldenlionerp.application.ap.voucher;

import java.net.URI;
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
@RequestMapping("/vouchers/")
public class PayableVoucherController {

	@Autowired
	private PayableVoucherService service;
	@Autowired
	private PayableVoucherIDConverter idconverter;
	@Autowired
	private PayableVoucherRepository repo;
	@Autowired
	private RepositoryEntityLinks links;
	
	@RequestMapping(path="/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> voidInvoice(@PathVariable("id") String id){
		PayableVoucherPK pk = (PayableVoucherPK) idconverter.fromRequestId(id, PayableVoucherPK.class);
		service.voidVoucher(pk);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Resource<?> getSingle(
			@PathVariable("id") String id, 
			@RequestParam(name="includeVoided", defaultValue="false") boolean includeVoided,
			PersistentEntityResourceAssembler assembler){
		
		PayableVoucherPK pk = (PayableVoucherPK) idconverter.fromRequestId(id, PayableVoucherPK.class);
		
		Optional<PayableVoucher> invoice = includeVoided ?
				repo.findIncludeVoided(pk) : repo.findById(pk);

		return assembler.toFullResource(invoice.orElseThrow(() -> new ResourceNotFoundException()));
	}
	
	@GetMapping @ResponseBody
	public Resources<?> getCollection(
			@RequestParam(name="includeVoided", defaultValue="false") boolean includeVoided,
			DefaultedPageable pageable,
			Sort sort,
			PersistentEntityResourceAssembler assembler){
		
		Page<PayableVoucher> page = includeVoided ?
				repo.findAllIncludeVoided(pageable.getPageable()):
				repo.findAll(pageable.getPageable());
		Page<Object> objPage = page.map(e -> (Object) e);
				
		PagedResourcesAssembler<Object> pagedAssembler = new PagedResourcesAssembler<>(null, null);
		return pagedAssembler.toResource(objPage, assembler);
		//return assembler.toFullResource(list);
	}
	
	@PostMapping
	public ResponseEntity<?> post(@RequestBody PayableVoucher newVoucher,
			PersistentEntityResourceAssembler assembler){
		if (repo.existsById(newVoucher.getId()))
			throw new ResourceAlreadyExistsException("voucher", newVoucher.getId());
		
		PayableVoucher createdVoucher = service.create(newVoucher);
		
		URI resourceUri = URI.create(
				links.linkToSingleResource(PayableVoucher.class, createdVoucher.getId())
						.getHref()
			);
		
		return ResponseEntity.created(resourceUri).body(assembler.toFullResource(createdVoucher));
	}
	
}
