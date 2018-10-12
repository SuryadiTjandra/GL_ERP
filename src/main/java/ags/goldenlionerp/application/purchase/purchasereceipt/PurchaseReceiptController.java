package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

import static ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptPredicates.*;

@RepositoryRestController
public class PurchaseReceiptController {

	@Autowired
	private PurchaseReceiptRepository repo;
	@Autowired
	private PurchaseReceiptIdConverter conv;
	
	@DeleteMapping("/purchaseReceipts/{id}")
	public ResponseEntity<?> deleteNotAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@GetMapping("/purchaseReceipts/{id}/sameReceipt")
	@ResponseBody
	public Resources<PersistentEntityResource> getReceiptsInSameBatch(
			@PathVariable("id") String receiptId, 
			PersistentEntityResourceAssembler assembler){
		
		PurchaseReceiptPK pk = (PurchaseReceiptPK) conv.fromRequestId(receiptId, PurchaseReceiptPK.class);
		PurchaseReceipt receipt = repo.findById(pk)
									.orElseThrow(() -> new ResourceNotFoundException());
		
		Iterable<PurchaseReceipt> receipts = repo.findAll(sameReceiptNoAs(receipt));
		List<PersistentEntityResource> receiptResources = 
				StreamSupport.stream(receipts.spliterator(), false)
					.map(pr -> assembler.toResource(pr))
					.collect(Collectors.toList());
		
		return new Resources<>(receiptResources);
		
	}
	
	@PostMapping("/purchaseReceipts")
	public ResponseEntity<?> post(@RequestBody PurchaseReceipt receipt){
		if (repo.existsById(receipt.getPk()))
			throw new ResourceAlreadyExistsException("purchaseReceipt", receipt.getPk());
		
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
}
