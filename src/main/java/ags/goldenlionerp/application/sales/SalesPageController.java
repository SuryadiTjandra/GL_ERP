package ags.goldenlionerp.application.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ags.goldenlionerp.application.sales.salesorder.SalesOrderService;

@Controller
public class SalesPageController {
	@Autowired
	private SalesOrderService soService;
	
	@GetMapping("/salesOrder")
	public ModelAndView purchaseOrderPage(@RequestParam(name="appParam", required=false) String appParamCode) {
		return new ModelAndView("salesOrder", "defaultItem", soService.getDefaultSalesOrder(appParamCode));
	}
}
