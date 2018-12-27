package ags.goldenlionerp.application.purchase.purchasereceipt;

import com.querydsl.core.types.Path;
import ags.goldenlionerp.documents.DocumentDetailPredicates;

public class PurchaseReceiptPredicates extends DocumentDetailPredicates<PurchaseReceipt, PurchaseReceiptPK>{

	private static PurchaseReceiptPredicates instance;
	
	protected PurchaseReceiptPredicates(Path<PurchaseReceipt> qPath) {
		super(qPath);
	}
	
	public static PurchaseReceiptPredicates getInstance() {
		if (instance == null)
			instance = new PurchaseReceiptPredicates(QPurchaseReceipt.purchaseReceipt);
		return instance;
	}

}
