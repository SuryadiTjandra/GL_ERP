package ags.goldenlionerp.application.purchase.purchasereceipt;

import com.querydsl.core.types.Predicate;

public class PurchaseReceiptPredicates {

	public static Predicate sameReceiptNoAs(PurchaseReceipt receipt) {
		
		QPurchaseReceipt rec = QPurchaseReceipt.purchaseReceipt;
		return rec.pk.companyId.eq(receipt.getPk().getCompanyId())
		  .and(rec.pk.purchaseReceiptNumber.eq(receipt.getPk().getPurchaseReceiptNumber()))
		  .and(rec.pk.purchaseReceiptType.eq(receipt.getPk().getPurchaseReceiptType()));
	}
}
