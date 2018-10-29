package ags.goldenlionerp.application.item.itemtransaction;

import org.springframework.data.repository.PagingAndSortingRepository;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

public interface ItemTransactionRepository extends 
	PagingAndSortingRepository<ItemTransaction, ItemTransactionPK>,
	QuerydslUsingBaseRepository<ItemTransaction, QItemTransaction> {

}
