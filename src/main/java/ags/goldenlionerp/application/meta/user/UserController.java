package ags.goldenlionerp.application.meta.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
public class UserController extends NoPutController {

	@Autowired
	private UserService service;
	
	@PutMapping("/users/{id}")
	@Override
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
	
	@RequestMapping(path="/users/{id}/password", method= {POST, PUT, PATCH})
	public ResponseEntity<?> changePassword(
			@PathVariable("id") String userId, 
			@RequestBody Map<String, String> request){
		
		service.changePassword(userId, 
				request.getOrDefault("userSecurityCode", ""), 
				request.getOrDefault("userSecurityCodeRe", ""));
		return ResponseEntity.noContent().build();
	}
}
