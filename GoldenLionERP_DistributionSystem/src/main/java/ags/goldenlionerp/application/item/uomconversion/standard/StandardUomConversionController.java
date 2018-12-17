package ags.goldenlionerp.application.item.uomconversion.standard;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
@RequestMapping("/stduomconvs/")
public class StandardUomConversionController extends NoPutController{
	
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
