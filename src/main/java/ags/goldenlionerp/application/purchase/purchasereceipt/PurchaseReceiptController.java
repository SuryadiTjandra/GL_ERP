package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;

import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@RepositoryRestController
public class PurchaseReceiptController {

	@Autowired
	private PurchaseReceiptRepository repo;
	@Autowired
	private PurchaseReceiptIdConverter conv; 
	@Autowired
	private PurchaseReceiptService service;
	
	@DeleteMapping("/purchaseReceipts/{id}")
	public ResponseEntity<?> deleteNotAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PutMapping("/purchaseReceipts/{id}")
	public ResponseEntity<?> putNotAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@GetMapping("/purchaseReceipts/{id}") @ResponseBody
	public Resource<PurchaseReceiptHeader> getPurchaseReceipt(@PathVariable("id") String receiptId, PersistentEntityResourceAssembler assmbler){
		PurchaseReceiptPK pk = (PurchaseReceiptPK) conv.fromRequestId(receiptId, PurchaseReceiptPK.class);
		PurchaseReceipt receipt = repo.findById(pk)
									.orElseThrow(() -> new ResourceNotFoundException());
		
		List<PurchaseReceipt> receipts = Lists.newArrayList(repo.findAll(PurchaseReceiptPredicates.getInstance().sameHeaderAs(receipt)));
		PurchaseReceiptHeader header = new PurchaseReceiptHeader(receipts);
		
		return new PurchaseReceiptHeaderResource(header, assmbler);
	}
	
	@GetMapping("/purchaseReceipts/{id}/sameReceipt")
	@ResponseBody
	public Resources<PersistentEntityResource> getReceiptsInSameBatch(
			@PathVariable("id") String receiptId, 
			PersistentEntityResourceAssembler assembler){
		
		PurchaseReceiptPK pk = (PurchaseReceiptPK) conv.fromRequestId(receiptId, PurchaseReceiptPK.class);
		PurchaseReceipt receipt = repo.findById(pk)
									.orElseThrow(() -> new ResourceNotFoundException());
		
		Iterable<PurchaseReceipt> receipts = repo.findAll(PurchaseReceiptPredicates.getInstance().sameHeaderAs(receipt));
		List<PersistentEntityResource> receiptResources = 
				StreamSupport.stream(receipts.spliterator(), false)
					.map(pr -> assembler.toResource(pr))
					.collect(Collectors.toList());
		
		return new Resources<>(receiptResources);
		
	}
	
	@PostMapping("/purchaseReceipts")
	public ResponseEntity<?> post(@RequestBody PurchaseReceiptHeader receiptHead, PersistentEntityResourceAssembler assembler){
		Predicate pred = PurchaseReceiptPredicates.getInstance().sameHeaderAs(receiptHead.getDetails().get(0).getPk());
		if (repo.exists(pred))
			throw new ResourceAlreadyExistsException("purchaseReceipt", receiptHead.getDetails().get(0).getPk());
		
		receiptHead = service.createPurchaseReceipt(receiptHead);
		PurchaseReceiptHeaderResource headRes = new PurchaseReceiptHeaderResource(receiptHead, assembler);
		URI resUri = URI.create(headRes.getId().getHref());
		
		return ResponseEntity.created(resUri).body(headRes);
		//return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@PatchMapping("/purchaseReceipts/{id}")
	public ResponseEntity<?> patch(@PathVariable("id") String id,
			@RequestBody PurchaseReceiptHeader receiptHead, 
			PersistentEntityResourceAssembler assembler){
		PurchaseReceiptPK pk = (PurchaseReceiptPK) conv.fromRequestId(id, PurchaseReceipt.class);
		if (!repo.existsById(pk))
			throw new ResourceNotFoundException();
		
		receiptHead.setIdInfo(pk.getCompanyId(), pk.getPurchaseReceiptNumber(), pk.getPurchaseReceiptType());
		
		receiptHead = service.updatePurchaseReceipt(receiptHead);
		PurchaseReceiptHeaderResource headRes = new PurchaseReceiptHeaderResource(receiptHead, assembler);
		return ResponseEntity.ok(headRes);
	}
	
	@ExceptionHandler(PurchaseReceiptException.class)
	public ResponseEntity<?> handleException(PurchaseReceiptException exception){
		return ResponseEntity.unprocessableEntity().body(exception.getMessage());
	}
}
