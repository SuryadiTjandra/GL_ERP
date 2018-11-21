package ags.goldenlionerp.application.itemstock.stocktransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockTransactionService {

	@Autowired private StockTransactionRepository repo;
	
	public StockTransaction createStockTransaction(StockTransaction stRequest) {
		//TODO others
		return repo.save(stRequest);
	}
	
}
