package ags.goldenlionerp.application.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderService;

@Controller
public class PurchasePageController {

	@Autowired
	private PurchaseOrderService poService;
	
	@GetMapping("/purchaseOrder")
	public ModelAndView purchaseOrderPage(@RequestParam(name="appParam", required=false) String appParamCode) {
		return new ModelAndView("purchaseOrder", "defaultItem", poService.getDefaultPurchaseOrder(appParamCode));
	}
}
