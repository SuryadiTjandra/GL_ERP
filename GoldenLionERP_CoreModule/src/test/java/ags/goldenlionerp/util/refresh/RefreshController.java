package ags.goldenlionerp.util.refresh;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@TestComponent
class RefreshController {

	@Autowired EntityManager em;
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh() {
		em.flush(); em.clear();
		return ResponseEntity.noContent().build();
	}
}
