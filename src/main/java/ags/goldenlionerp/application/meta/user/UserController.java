package ags.goldenlionerp.application.meta.user;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
public class UserController extends NoPutController {

	@Autowired
	private UserService service;
	@Autowired
	private UserGroupService grService;
	@Autowired
	private RepositoryEntityLinks links;
	
	@PutMapping("/users/{id}")
	@Override
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
	
	@PostMapping("/users/")
	public ResponseEntity<?> post(@RequestBody Map<String, Object> request) throws Exception{
		UserBase user = service.post(request);
		String userLink = links.linkToSingleResource(User.class, user.getId()).getHref();
		return ResponseEntity.created(new URI(userLink)).body(user);
	}
	
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> patch(
			@PathVariable("id") String userId, 
			@RequestBody Map<String, Object> request){
		
		UserBase user = service.patch(userId, request);
		return ResponseEntity.ok(user);

	}
	
	@RequestMapping(path="/users/{id}/password", method= {POST, PUT, PATCH})
	public ResponseEntity<?> changePassword(
			@PathVariable("id") String userId, 
			@RequestBody Map<String, Object> request){
		
		service.changePassword(userId, 
				(String) request.getOrDefault("userSecurityCode", ""), 
				(String) request.getOrDefault("userSecurityCodeRe", ""));
		return ResponseEntity.noContent().build();
	}
}
