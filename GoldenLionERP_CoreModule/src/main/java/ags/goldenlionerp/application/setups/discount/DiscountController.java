package ags.goldenlionerp.application.setups.discount;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class DiscountController {

	@Autowired private DiscountRepository repo;
	
	@PutMapping("/discounts/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@GetMapping("/discounts/{id}/calculate")
	@ResponseBody
	public DiscountCalculationResult calculateDiscount(
			@PathVariable("id") String discountCode,
			@RequestParam("amount") BigDecimal amount) {
		
		DiscountMaster disc = repo.findById(discountCode)
								.orElseThrow(() -> new ResourceNotFoundException());
		return disc.calculateDiscount(amount);
	}
}
