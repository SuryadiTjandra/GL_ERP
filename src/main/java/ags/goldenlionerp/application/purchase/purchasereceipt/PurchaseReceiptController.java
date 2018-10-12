package ags.goldenlionerp.application.purchase.purchasereceipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

@RepositoryRestController
public class PurchaseReceiptController {

	@Autowired
	private PurchaseReceiptRepository repo;
	
	@DeleteMapping("/purchaseReceipts/{id}")
	public ResponseEntity<?> deleteNotAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	
	
}
