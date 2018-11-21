package ags.goldenlionerp.application.itemstock.stocktransaction;

import com.querydsl.core.types.Path;

import ags.goldenlionerp.documents.DocumentDetailPredicates;

public class StockTransactionPredicates extends DocumentDetailPredicates<StockTransaction, StockTransactionPK> {

	private static StockTransactionPredicates instance;
	
	public static StockTransactionPredicates getInstance() {
		if (instance == null) {
			instance = new StockTransactionPredicates(QStockTransaction.stockTransaction);
		}
		return instance;
	}
	
	private StockTransactionPredicates(Path<StockTransaction> qPath) {
		super(qPath);
	}

}
