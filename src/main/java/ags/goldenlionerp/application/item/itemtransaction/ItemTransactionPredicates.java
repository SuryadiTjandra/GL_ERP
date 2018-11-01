package ags.goldenlionerp.application.item.itemtransaction;

import com.querydsl.core.types.Path;

import ags.goldenlionerp.entities.DocumentDetailPredicates;

public class ItemTransactionPredicates extends DocumentDetailPredicates<ItemTransaction, ItemTransactionPK> {

	private static ItemTransactionPredicates instance;
	
	public static ItemTransactionPredicates getInstance() {
		if (instance == null) {
			instance = new ItemTransactionPredicates(QItemTransaction.itemTransaction);
		}
		return instance;
	}
	
	private ItemTransactionPredicates(Path<ItemTransaction> qPath) {
		super(qPath);
	}

}
