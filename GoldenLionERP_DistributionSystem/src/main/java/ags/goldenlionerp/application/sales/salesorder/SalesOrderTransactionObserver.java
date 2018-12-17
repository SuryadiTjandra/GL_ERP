package ags.goldenlionerp.application.sales.salesorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.sales.salesshipment.SalesShipment;
import ags.goldenlionerp.documents.ItemTransaction;
import ags.goldenlionerp.documents.ItemTransactionObserver;
import ags.goldenlionerp.documents.ItemTransactionService;

@Component
public class SalesOrderTransactionObserver implements ItemTransactionObserver {

	SalesOrderService service;
	
	@Autowired
	public SalesOrderTransactionObserver(List<ItemTransactionService> observables, SalesOrderService service) {
		observables.forEach(obs -> obs.registerObserver(this));
		this.service = service;
	}
	
	@Override
	public void handleCreation(ItemTransaction transaction) {
		if (!(transaction instanceof SalesShipment))
			return;

		service.shipOrder(transaction.getId().getCompanyId(), 
				transaction.getOrderNumber(), 
				transaction.getOrderType(), 
				transaction.getOrderSequence(), 
				transaction.getTransactionQuantity(), 
				transaction.getUnitOfMeasure());
	}

	@Override
	public void handleVoidation(ItemTransaction transaction) {
		if (!(transaction instanceof SalesShipment))
			return;

		service.shipOrder(transaction.getId().getCompanyId(), 
				transaction.getOrderNumber(), 
				transaction.getOrderType(), 
				transaction.getOrderSequence(), 
				transaction.getTransactionQuantity().negate(), 
				transaction.getUnitOfMeasure());
	}

}
