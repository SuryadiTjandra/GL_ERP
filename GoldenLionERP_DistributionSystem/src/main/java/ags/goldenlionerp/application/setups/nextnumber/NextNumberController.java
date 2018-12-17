package ags.goldenlionerp.application.setups.nextnumber;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
public class NextNumberController extends NoPutController{

	@Override
	@PutMapping("/nextNumbers/{id}")
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
}
