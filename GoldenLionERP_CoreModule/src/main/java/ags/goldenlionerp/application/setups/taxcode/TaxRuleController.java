package ags.goldenlionerp.application.setups.taxcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class TaxRuleController {

	@Autowired
	private TaxRuleRepository repo;
	@Autowired
	private TaxRuleIdConverter conv;
	
	@PutMapping("/taxRules/{id}")
	public ResponseEntity<?> noPutAllowed(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@GetMapping("/taxRules/{id}") @ResponseBody
	public Resource<?> getActiveRule(@PathVariable("id") String id, PersistentEntityResourceAssembler assmbler){
		TaxRule rule = null;
		if (id.contains("_")) {
			TaxRulePK pk = (TaxRulePK) conv.fromRequestId(id, TaxRulePK.class);
			rule = repo.findById(pk)
					.orElseThrow(() -> new ResourceNotFoundException());
		}else {
			rule = repo.findActiveTaxRule(id)
					.orElseThrow(() -> new ResourceNotFoundException());
		}
		
		return assmbler.toFullResource(rule);
	}
	
}
