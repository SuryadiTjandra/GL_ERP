package ags.goldenlionerp.application.itemstock.stocktransaction;

import org.springframework.data.repository.PagingAndSortingRepository;

import ags.goldenlionerp.application.itemstock.itemtransaction.QItemTransaction;
import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

public interface StockTransactionRepository extends 
	PagingAndSortingRepository<StockTransaction, StockTransactionPK>,
	QuerydslUsingBaseRepository<StockTransaction, QItemTransaction> {

}
