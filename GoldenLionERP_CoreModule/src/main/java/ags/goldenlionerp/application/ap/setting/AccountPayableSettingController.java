package ags.goldenlionerp.application.ap.setting;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@RepositoryRestController
public class AccountPayableSettingController {
	
	@PostMapping("/accountPayableSettings")
	public ResponseEntity<?> post(){
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
	}
	
	@DeleteMapping("/accountPayableSettings/{id}")
	public ResponseEntity<?> delete(){
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
	}
	
	@GetMapping("/addresses/{id}/apSetting")
	public ModelAndView getAssociation(@PathVariable("id") String id) {
		return new ModelAndView("forward://api/accountPayableSettings/" + id);
	}
	
}
