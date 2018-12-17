package ags.goldenlionerp.application.item.uomconversion.itemspecific;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
@RequestMapping("/itemuomconvs/")
public class ItemUomConversionController extends NoPutController{

	@PutMapping("{id}")
	@Override
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
	
}
