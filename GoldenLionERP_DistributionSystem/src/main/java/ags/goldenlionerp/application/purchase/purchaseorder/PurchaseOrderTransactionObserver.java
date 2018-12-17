package ags.goldenlionerp.application.purchase.purchaseorder;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceipt;
import ags.goldenlionerp.documents.ItemTransaction;
import ags.goldenlionerp.documents.ItemTransactionObserver;
import ags.goldenlionerp.documents.ItemTransactionService;

@Component
public class PurchaseOrderTransactionObserver implements ItemTransactionObserver{

	private PurchaseOrderService service;

	@Autowired
	public PurchaseOrderTransactionObserver(Collection<ItemTransactionService> observables, PurchaseOrderService service) {
		observables.forEach(obs -> obs.registerObserver(this));
		this.service = service;
	}
	
	@Override
	public void handleCreation(ItemTransaction transaction) {
		if (!(transaction instanceof PurchaseReceipt))
			return;
		
		service.receiveOrder(transaction.getId().getCompanyId(), 
				transaction.getOrderNumber(), 
				transaction.getOrderType(), 
				transaction.getOrderSequence(), 
				transaction.getTransactionQuantity(),
				transaction.getUnitOfMeasure());
	}

	@Override
	public void handleVoidation(ItemTransaction transaction) {
		if (!(transaction instanceof PurchaseReceipt))
			return;
		
		service.receiveOrder(transaction.getId().getCompanyId(), 
				transaction.getOrderNumber(), 
				transaction.getOrderType(), 
				transaction.getOrderSequence(), 
				transaction.getTransactionQuantity().negate(),
				transaction.getUnitOfMeasure());
	}

}
