package ags.goldenlionerp.application.addresses.bankaccount;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.basecomponents.ParentChildrenService;

@Service
public class BankAccountService extends ParentChildrenService<AddressBookMaster, BankAccount, String, BankAccountPK>{

	public Collection<BankAccount> saveBankAccountsForAddress(String addressNumber, Collection<Map<String, Object>> requests){
		return super.saveChildrenToParent(addressNumber, requests);
	}
	
	@Autowired
	protected BankAccountService(AddressBookRepository parentRepo, BankAccountRepository childRepo) {
		super(parentRepo, childRepo);
	}

	@Override
	protected void setIdToMatchParent(String parentId, Map<String, Object> childRequest) {
		childRequest.put("addressNumber", parentId);		
	}

	@Override
	protected BankAccountPK constructChildId(Map<String, Object> childRequest) {
		return new BankAccountPK(
				childRequest.get("addressNumber").toString(), 
				childRequest.get("bankId").toString(), 
				childRequest.get("bankAccountNumber").toString()
				);
	}

	@Override
	protected BankAccount constructChild(Map<String, Object> childRequest) {
		BankAccountPK pk = constructChildId(childRequest);
		BankAccount ba = new BankAccount();
		ba.setPk(pk);
		ba.setDescription(childRequest.get("description").toString());
		return ba;
	}

}
