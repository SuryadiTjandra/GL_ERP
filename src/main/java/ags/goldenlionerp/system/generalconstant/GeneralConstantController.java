package ags.goldenlionerp.system.generalconstant;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@RepositoryRestController
public class GeneralConstantController {

	
	@GetMapping("/generalConstant")
	public ResponseEntity<GeneralConstant> get() {
		GeneralConstant gc = GeneralConstant.getInstance();
		return new ResponseEntity<>(gc, HttpStatus.OK);
	}
	
	@PostMapping("/generalConstant")
	public ResponseEntity<?> post() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PutMapping({"/generalConstant/{id}", "/generalConstant"})
	public ResponseEntity<?> put() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@DeleteMapping({"/generalConstant/{id}", "/generalConstant"})
	public ResponseEntity<?> delete() {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PatchMapping({"/generalConstant"})
	public ModelAndView patch(@RequestBody ModelMap model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		return new ModelAndView("forward://api/generalConstant/00", model);
		
	}
}
