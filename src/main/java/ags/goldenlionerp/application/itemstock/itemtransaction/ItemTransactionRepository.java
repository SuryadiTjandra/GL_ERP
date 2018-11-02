package ags.goldenlionerp.application.itemstock.itemtransaction;

import org.springframework.data.repository.PagingAndSortingRepository;

import ags.goldenlionerp.application.item.itemtransaction.QItemTransaction;
import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

public interface ItemTransactionRepository extends 
	PagingAndSortingRepository<ItemTransaction, ItemTransactionPK>,
	QuerydslUsingBaseRepository<ItemTransaction, QItemTransaction> {

}
