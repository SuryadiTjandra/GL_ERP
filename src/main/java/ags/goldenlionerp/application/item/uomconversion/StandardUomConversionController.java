package ags.goldenlionerp.application.item.uomconversion;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
@RequestMapping("/uomconversions/")
public class StandardUomConversionController extends NoPutController{

	@Override @PutMapping("{id}")
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
}
