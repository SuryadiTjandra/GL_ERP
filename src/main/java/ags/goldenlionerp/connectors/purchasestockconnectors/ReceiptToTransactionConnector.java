package ags.goldenlionerp.connectors.purchasestockconnectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.itemstock.itemtransaction.ItemTransaction;
import ags.goldenlionerp.application.itemstock.itemtransaction.ItemTransactionPK;
import ags.goldenlionerp.application.itemstock.itemtransaction.ItemTransactionService;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceipt;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptPK;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptService;
import ags.goldenlionerp.connectors.ModuleConnector;

@Component
public class ReceiptToTransactionConnector implements ModuleConnector<PurchaseReceipt, ItemTransaction> {

	private ItemTransactionService itServ;
	
	public ReceiptToTransactionConnector(@Autowired PurchaseReceiptService prServ, @Autowired ItemTransactionService itServ) {
		prServ.registerConnector(this);
		this.itServ = itServ;
	}
	
	@Override
	public void handleCreated(PurchaseReceipt receipt) {
		if (!receipt.getLineType().equals("S"))
			return;
		
		PurchaseReceiptPK recPk = receipt.getPk();
		ItemTransactionPK pk = new ItemTransactionPK(recPk.getCompanyId(), recPk.getDocumentNumber(), recPk.getDocumentType(), recPk.getSequence());
		ItemTransaction it = new ItemTransaction.Builder(pk)
								.businessUnitId(receipt.getBusinessUnitId())
								.batchNumber(receipt.getBatchNumber())
								.batchType(receipt.getBatchType())
								.documentDateTime(receipt.getDocumentDate().atStartOfDay())
								.glDate(receipt.getDocumentDate())
								.itemCode(receipt.getItemCode())
								.locationId(receipt.getLocationId())
								.serialLotNo(receipt.getSerialLotNo())
								.itemDescription(receipt.getItemDescription())
								.description(receipt.getDescription())
								.quantity(receipt.getQuantity())
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
								.orderNumber(receipt.getPurchaseOrderNumber())
								.orderType(receipt.getPurchaseOrderType())
								.orderSequence(receipt.getPurchaseOrderSequence())
								.build();
		
		itServ.createItemTransaction(it);
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
