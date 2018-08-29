package ags.goldenlionerp.application.purchase.purchaseorder;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
public class PurchaseOrderController {

	@RequestMapping(path="/purchaseOrders/{id}", method= {RequestMethod.PATCH, RequestMethod.PUT})
	public ResponseEntity<?> noUpdateAllowed() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@DeleteMapping("/purchaseOrders/{id}")
	public ResponseEntity<?> delete(){
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
}
