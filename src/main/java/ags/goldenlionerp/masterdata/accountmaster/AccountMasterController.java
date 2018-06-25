package ags.goldenlionerp.masterdata.accountmaster;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RepositoryRestController
public class AccountMasterController {
	@PutMapping("/accountMasters/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
