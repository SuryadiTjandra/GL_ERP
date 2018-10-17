package ags.goldenlionerp.application.item.uomconversion;

import static org.assertj.core.api.Assertions.setMaxElementsForPrinting;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
@RequestMapping("/stduomconvs/")
public class StandardUomConversionController extends NoPutController{

	@Autowired
	private StandardUomConversionService service;
	@Autowired
	private StandardUomConversionRepository repo;
	
	@Override @PutMapping("{id}")
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
	
	//@GetMapping("/convert") @ResponseBody
	//public BigDecimal getConversionValue(@RequestParam("from") String from, @RequestParam("to") String to) {
	//	return service.findConversionValue(from, to)
	//					.orElseThrow(() -> new ResourceNotFoundException("Could not convert from " + from + " to " + to));
	//}
}
