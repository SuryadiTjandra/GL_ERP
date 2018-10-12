package ags.goldenlionerp.application.purchase.purchasereceipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public class PurchaseReceiptController {

	@Autowired
	private PurchaseReceiptRepository repo;
	
	
}
