package ags.goldenlionerp.application.itemstock.stocktransaction;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.item.itemmaster.ItemMasterRepository;
import ags.goldenlionerp.documents.DocumentDetailId;
import ags.goldenlionerp.documents.ItemTransaction;
import ags.goldenlionerp.documents.ItemTransactionObserver;
import ags.goldenlionerp.documents.ItemTransactionService;

@Component
public class StockItemTransactionObserver implements ItemTransactionObserver {

	private ItemMasterRepository itemRepo;
	private StockTransactionService service;

	@Autowired
	public StockItemTransactionObserver(Collection<ItemTransactionService> observables, 
			StockTransactionService service,
			ItemMasterRepository itemRepo) {
		observables.forEach(obs -> obs.registerObserver(this));
		this.service = service;
		this.itemRepo = itemRepo;
	}
	
	@Override
	public void handleCreated(ItemTransaction transaction) {
		//prevent infinite creation loop
		if (transaction instanceof StockTransaction)
			return;
		
		ItemMaster item = itemRepo.findById(transaction.getItemCode())
							.orElseThrow(() -> new ResourceNotFoundException());
		
		//if item isn't a stock item, do nothing
		if (!item.getTransactionType().equals("S"))
			return;
		
		DocumentDetailId transPk = transaction.getId();
		StockTransactionPK pk = new StockTransactionPK(
				transPk.getCompanyId(), 
				transPk.getDocumentNumber(), 
				transPk.getDocumentType(), 
				transPk.getSequence());
		
		StockTransaction stock = new StockTransaction.Builder(pk)
									.businessUnitId(transaction.getBusinessUnitId())
									.businessPartnerId(transaction.getBusinessPartnerId())
									.documentDateTime(transaction.getTransactionDate().atStartOfDay())
									.glDate(transaction.getTransactionDate())
									.itemCode(item.getItemCode())
									.itemDescription(item.getDescription())
									.locationId(transaction.getLocationId())
									.quantity(transaction.getTransactionQuantity())
									.unitOfMeasure(transaction.getUnitOfMeasure())
									.primaryTransactionQuantity(transaction.getPrimaryTransactionQuantity())
									.primaryUnitOfMeasure(transaction.getPrimaryUnitOfMeasure())
									.secondaryTransactionQuantity(transaction.getSecondaryTransactionQuantity())
									.secondaryUnitOfMeasure(transaction.getSecondaryUnitOfMeasure())
									.unitCost(transaction.getUnitCost())
									.extendedCost(transaction.getExtendedCost())
									.fromOrTo(transaction.isAdditive() ? "F" : "T")
									.glClass(item.getGlClass())
									.orderNumber(transaction.getOrderNumber())
									.orderSequence(transaction.getOrderSequence())
									.orderType(transaction.getOrderType())
									.build();
		service.createStockTransaction(stock);
	}

}
