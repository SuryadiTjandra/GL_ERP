package ags.goldenlionerp.application.addresses.bankaccount;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ags.goldenlionerp.basecomponents.NoPutController;

@RepositoryRestController
public class BankAccountController extends NoPutController{

	@Autowired
	private BankAccountService service;
	
	@Override @PutMapping("/bankAccounts/{id}")
	public ResponseEntity<?> noPutAllowed() {
		return super.noPutAllowed();
	}
	
	@PostMapping("/addresses/{id}/bankAccounts")
	@ResponseBody
	public Collection<BankAccount> massPost(
			@PathVariable("id") String addressNumber, 
			@RequestBody Collection<Map<String, Object>> requests){
		return service.saveBankAccountsForAddress(addressNumber, requests);
	}
}
