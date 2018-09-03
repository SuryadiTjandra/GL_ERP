package ags.goldenlionerp.application.purchase.purchaseorder;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@RepositoryRestController
public class PurchaseOrderController {
	@Autowired
	private PurchaseOrderRepository repo;
	@Autowired
	private PurchaseOrderService service;
	@Autowired
	private RepositoryEntityLinks links;

	@RequestMapping(path="/purchaseOrders/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@DeleteMapping("/purchaseOrders/{id}")
	public ResponseEntity<?> delete(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping("/purchaseOrders")
	public ResponseEntity<?> createNewPurchaseOrder(@RequestBody PurchaseOrder poRequest,
			PersistentEntityResourceAssembler assembler){
		if (repo.existsById(poRequest.getPk()))
			throw new ResourceAlreadyExistsException("purchase order", poRequest.getPk());
		
		PurchaseOrder po = service.createPurchaseOrder(poRequest);
		
		URI location = URI.create(links.linkToSingleResource(PurchaseOrder.class, po.getId()).getHref());
		
		return ResponseEntity.created(location).body(assembler.toFullResource(po));
	}
}
