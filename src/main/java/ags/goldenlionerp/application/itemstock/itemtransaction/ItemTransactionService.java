package ags.goldenlionerp.application.itemstock.itemtransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTransactionService {

	@Autowired private ItemTransactionRepository repo;
	
	public ItemTransaction createItemTransaction(ItemTransaction itRequest) {
		//TODO others
		return repo.save(itRequest);
	}
	
}
