package ags.goldenlionerp.application.itemstock.itemtransaction;

import com.querydsl.core.types.Path;

import ags.goldenlionerp.application.item.itemtransaction.QItemTransaction;
import ags.goldenlionerp.documents.DocumentDetailPredicates;

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
