package ags.goldenlionerp.application.item.lotmaster;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.item.itemmaster.ItemMasterRepository;
import ags.goldenlionerp.documents.ItemTransaction;
import ags.goldenlionerp.documents.ItemTransactionObserver;
import ags.goldenlionerp.documents.ItemTransactionService;

@Component
public class LotMasterTransactionObserver implements ItemTransactionObserver {

	private LotMasterService service;
	private ItemMasterRepository itemRepo;
	
	@Autowired
	public LotMasterTransactionObserver(Collection<ItemTransactionService> observables, LotMasterService service, ItemMasterRepository itemRepo) {
		observables.forEach(observable -> observable.registerObserver(this));
		this.service = service;
		this.itemRepo = itemRepo;
	}
	
	@Override
	public void handleCreation(ItemTransaction transaction) {
		ItemMaster item = itemRepo.findById(transaction.getItemCode())
								.orElseThrow(() -> new ResourceNotFoundException());
		//if item doesn't need to be tracked, then do nothing
		if (!item.isInventoryLotCreation() && !item.isSerialNumberRequired())
			return;
		
		//if the provided serial numbers don't match the quantity, then throw an exception
		if (transaction.getTransactionQuantity().intValue() != transaction.getSerialOrLotNumbers().size())
			throw new IllegalArgumentException("How many serial numbers provided must match quantity!");

		
		if (transaction.isAdditive()) {
			service.createLotsWithSerialNumbers(
					transaction.getBusinessUnitId(), 
					transaction.getItemCode(), 
					transaction.getSerialOrLotNumbers(), 
					transaction.getId());
		} else {
			service.markLotsAsUsed(transaction.getBusinessUnitId(), 
					transaction.getItemCode(), 
					transaction.getSerialOrLotNumbers());
		}
	}

	@Override
	public void handleVoidation(ItemTransaction transaction) {
		ItemMaster item = itemRepo.findById(transaction.getItemCode())
							.orElseThrow(() -> new ResourceNotFoundException());
		//if item doesn't need to be tracked, then do nothing
		if (!item.isInventoryLotCreation() && !item.isSerialNumberRequired())
			return;
		
		//if the provided serial numbers don't match the quantity, then throw an exception
		if (transaction.getTransactionQuantity().intValue() != transaction.getSerialOrLotNumbers().size())
			throw new IllegalArgumentException("How many serial numbers provided must match quantity!");

		
		if (transaction.isAdditive()) {
			service.deleteLotsWithSerialNumbers(
					transaction.getBusinessUnitId(), 
					transaction.getItemCode(), 
					transaction.getSerialOrLotNumbers());
		} else {
			service.unmarkLotsAsUsed(transaction.getBusinessUnitId(), 
					transaction.getItemCode(), 
					transaction.getSerialOrLotNumbers());
		}
		
	}

}
