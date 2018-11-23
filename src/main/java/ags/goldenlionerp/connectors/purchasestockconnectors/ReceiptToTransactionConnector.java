package ags.goldenlionerp.connectors.purchasestockconnectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.itemstock.stocktransaction.StockTransaction;
import ags.goldenlionerp.application.itemstock.stocktransaction.StockTransactionPK;
import ags.goldenlionerp.application.itemstock.stocktransaction.StockTransactionService;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceipt;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptPK;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptService;
import ags.goldenlionerp.connectors.ModuleConnector;

@Component
public class ReceiptToTransactionConnector implements ModuleConnector<PurchaseReceipt, StockTransaction> {

	private StockTransactionService itServ;
	
	public ReceiptToTransactionConnector(@Autowired PurchaseReceiptService prServ, @Autowired StockTransactionService itServ) {
		prServ.registerConnector(this);
		this.itServ = itServ;
	}
	
	@Override
	public void handleCreated(PurchaseReceipt receipt) {
		if (!receipt.getLineType().equals("S"))
			return;
		
		PurchaseReceiptPK recPk = receipt.getPk();
		StockTransactionPK pk = new StockTransactionPK(recPk.getCompanyId(), recPk.getDocumentNumber(), recPk.getDocumentType(), recPk.getSequence());
		StockTransaction it = new StockTransaction.Builder(pk)
								.businessUnitId(receipt.getBusinessUnitId())
								.batchNumber(receipt.getBatchNumber())
								.batchType(receipt.getBatchType())
								.documentDateTime(receipt.getTransactionDate().atStartOfDay())
								.glDate(receipt.getTransactionDate())
								.itemCode(receipt.getItemCode())
								.locationId(receipt.getLocationId())
								.serialLotNo(receipt.getSerialLotNo())
								.itemDescription(receipt.getItemDescription())
								.description(receipt.getDescription())
								.quantity(receipt.getTransactionQuantity())
								.unitOfMeasure(receipt.getUnitOfMeasure())
								.unitConversionFactor(receipt.getUnitConversionFactor())
								.primaryTransactionQuantity(receipt.getPrimaryTransactionQuantity())
								.primaryUnitOfMeasure(receipt.getUnitOfMeasure())
								.secondaryTransactionQuantity(receipt.getSecondaryTransactionQuantity())
								.secondaryUnitOfMeasure(receipt.getSecondaryUnitOfMeasure())
								.unitCost(receipt.getUnitCost())
								.extendedCost(receipt.getExtendedCost())
								.fromOrTo("F")
								.businessPartnerId(receipt.getVendorId())
								.glClass(receipt.getGlClass())
								.expiredDate(receipt.getExpiredDate())
								.orderNumber(receipt.getOrderNumber())
								.orderType(receipt.getOrderType())
								.orderSequence(receipt.getOrderSequence())
								.build();
		
		itServ.createStockTransaction(it);
	}

	@Override
	public void handleUpdated(PurchaseReceipt entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDeleted(PurchaseReceipt entity) {
		// TODO Auto-generated method stub
		
	}

	
}
