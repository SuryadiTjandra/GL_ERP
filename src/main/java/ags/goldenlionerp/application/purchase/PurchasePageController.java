package ags.goldenlionerp.application.purchase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchasePageController {

	@GetMapping("/purchaseOrder")
	public String purchaseOrderPage() {
		return "purchaseOrder";
	}
}
