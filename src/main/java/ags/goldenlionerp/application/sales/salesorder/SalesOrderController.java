package ags.goldenlionerp.application.sales.salesorder;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@RepositoryRestController
public class SalesOrderController {

	@Autowired
	private SalesOrderRepository repo;
	@Autowired
	private RepositoryEntityLinks links;
	@Autowired
	private SalesOrderService service;
	@Autowired
	private SalesOrderIdConverter conv;
	
	@PutMapping(path="/salesOrders/{id}")
	public ResponseEntity<?> noUpdateAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PatchMapping("/salesOrders/{id}")
	public ResponseEntity<?> updateSalesOrder(@PathVariable("id") String id, @RequestBody SalesOrder soRequest,
			PersistentEntityResourceAssembler assembler){
		
		SalesOrderPK pk = (SalesOrderPK) conv.fromRequestId(id, SalesOrderPK.class);
		soRequest.setPk(pk);
		SalesOrder updatedSo = service.updateSalesOrder(soRequest);
		
		URI location = URI.create(links.linkToSingleResource(SalesOrder.class, updatedSo.getId()).getHref());
		
		return ResponseEntity.created(location).body(assembler.toFullResource(updatedSo));
		
	}
	
	@DeleteMapping("/salesOrders/{id}")
	public ResponseEntity<?> delete(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping("/salesOrders")
	public ResponseEntity<?> createNewPurchaseOrder(@RequestBody SalesOrder soRequest,
			PersistentEntityResourceAssembler assembler){
		if (repo.existsById(soRequest.getPk()))
			throw new ResourceAlreadyExistsException("sales order", soRequest.getPk());
		
		SalesOrder so = service.createSalesOrder(soRequest);
		
		URI location = URI.create(links.linkToSingleResource(SalesOrder.class, so.getId()).getHref());
		
		return ResponseEntity.created(location).body(assembler.toFullResource(so));
	}
}
