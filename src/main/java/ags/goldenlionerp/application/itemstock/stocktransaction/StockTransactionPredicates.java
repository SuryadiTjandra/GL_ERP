package ags.goldenlionerp.application.itemstock.stocktransaction;

import com.querydsl.core.types.Path;

import ags.goldenlionerp.application.itemstock.itemtransaction.QItemTransaction;
import ags.goldenlionerp.documents.DocumentDetailPredicates;

public class StockTransactionPredicates extends DocumentDetailPredicates<StockTransaction, StockTransactionPK> {

	private static StockTransactionPredicates instance;
	
	public static StockTransactionPredicates getInstance() {
		if (instance == null) {
			instance = new StockTransactionPredicates(QItemTransaction.itemTransaction);
		}
		return instance;
	}
	
	private StockTransactionPredicates(Path<StockTransaction> qPath) {
		super(qPath);
	}

}
