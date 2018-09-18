package ags.goldenlionerp.application.sales.salesorder;

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
public class SalesOrderController {

	@Autowired
	private SalesOrderRepository repo;
	@Autowired
	private RepositoryEntityLinks links;
	@Autowired
	private SalesOrderService service;
	
	@RequestMapping(path="/salesOrders/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
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
