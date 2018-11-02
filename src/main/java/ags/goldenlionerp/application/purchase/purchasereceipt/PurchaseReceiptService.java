package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.google.common.collect.Lists;

import ags.goldenlionerp.application.item.itemmaster.ItemMasterRepository;
import ags.goldenlionerp.application.item.lotmaster.LotMasterService;
import ags.goldenlionerp.application.item.uomconversion.UomConversionService;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseDetail;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrder;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderPK;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderRepository;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderService;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;

@Service
public class PurchaseReceiptService {
	
	@Autowired
	private NextNumberService nnServ;
	@Autowired
	private PurchaseReceiptRepository repo;
	@Autowired
	private PurchaseOrderRepository orderRepo;
	@Autowired
	private PurchaseOrderService orderService;
	@Autowired
	private UomConversionService uomServ;
	@Autowired
	private LotMasterService lotServ;
	@Autowired
	private ItemMasterRepository itemRepo;

	@Transactional
	public PurchaseReceiptHeader createPurchaseReceipt(PurchaseReceiptHeader receiptHead) {
		receiptHead = completePurchaseReceiptInfo(receiptHead);
		
		//create new lotmasters if the items has serial numbers
		for (PurchaseReceipt rec : receiptHead.getDetails()) {
			if (!itemRepo.findById(rec.getItemCode()).get().isSerialNumberRequired())
				continue;
			//check if quantity is integer
			if (rec.getQuantity().stripTrailingZeros().scale() > 0)
				throw new IllegalArgumentException("The quantity for this item must be an integer!");
			//check if each item has a distinct serial number
			if (rec.getQuantity().intValue() != rec.getSerialNumbers().size())
				throw new IllegalArgumentException("Must input distinct serial number for each received item!");
			
			lotServ.createLotsWithSerialNumbers(rec.getBusinessUnitId(), rec.getItemCode(), rec.getSerialNumbers(), rec.getPk());
		}
		
		//let the order service receive the orders
		for (PurchaseReceipt rec : receiptHead.getDetails()) {
			orderService.receiveOrder(rec.getPk().getCompanyId(), 
					rec.getPurchaseOrderNumber(), 
					rec.getPurchaseOrderType(), 
					rec.getPurchaseOrderSequence(), 
					rec.getQuantity(),
					rec.getUnitOfMeasure());
		}
		
		receiptHead = new PurchaseReceiptHeader(
				Lists.newArrayList(repo.saveAll(receiptHead.getDetails()))
			);
		return receiptHead;
	}
	
	public PurchaseReceiptHeader completePurchaseReceiptInfo(PurchaseReceiptHeader receiptHead) {
		receiptHead = setDocumentAndBatchNumber(receiptHead);
		receiptHead = setInfoFromPurchaseOrder(receiptHead);
		
		LocalDate docDate = receiptHead.getDocumentDate();
		receiptHead.getDetails().forEach(d -> d.setReceiptDate(docDate));
		return receiptHead;
	}

	private PurchaseReceiptHeader setDocumentAndBatchNumber(PurchaseReceiptHeader receiptHead) {
		String companyId = receiptHead.getCompanyId();
		String type = "OV";
		int docNo = receiptHead.getPurchaseReceiptNumber() == 0 ?
						nnServ.findNextDocumentNumber(companyId, type, YearMonth.now()):
						receiptHead.getPurchaseReceiptNumber();
		
		receiptHead.getDetails().forEach(d -> {
			PurchaseReceiptPK pk = new PurchaseReceiptPK(companyId, docNo, type, d.getPk().getSequence());
			d.setPk(pk);
		});
		
		String batchType = "O";
		int batchNo = nnServ.findNextDocumentNumber(companyId, batchType, YearMonth.now());
		receiptHead.setBatchNumber(batchNo);
		receiptHead.setBatchType(batchType);
							
		return receiptHead;
	}
	
	private PurchaseReceiptHeader setInfoFromPurchaseOrder(PurchaseReceiptHeader receiptHead) {
		receiptHead.getDetails().forEach(d -> setInfoFromPurchaseDetail(d));
		return receiptHead;
	}

	private PurchaseReceipt setInfoFromPurchaseDetail(PurchaseReceipt receipt) {
		PurchaseOrderPK poPk = new PurchaseOrderPK(receipt.getPk().getCompanyId(), receipt.getPurchaseOrderNumber(), receipt.getPurchaseOrderType());
		PurchaseOrder po = orderRepo.findById(poPk)
								.orElseThrow(() -> new ResourceNotFoundException("Could not find PurchaseOrder with id " + poPk));
		PurchaseDetail pd = po.getDetails().stream().filter(d -> d.getPk().getPurchaseOrderSequence() == receipt.getPurchaseOrderSequence())
								.findFirst()
								.orElseThrow(() -> new ResourceNotFoundException("Could not find PurchaseOrder with id " + poPk + " and sequence " + receipt.getPurchaseOrderSequence()));
		
		receipt.setBaseCurrency(pd.getBaseCurrency());
		receipt.setTransactionCurrency(pd.getTransactionCurrency());
		receipt.setExchangeRate(pd.getExchangeRate());
		
		receipt.setItemCode(pd.getItemCode());
		receipt.setItemDescription(pd.getDescription());
		receipt.setLineType(pd.getLineType());
		
		if (isEmpty(receipt.getLocationId()))
			receipt.setLocationId(pd.getLocationId());
		if (isEmpty(receipt.getSerialLotNo()))
			receipt.setSerialLotNo(pd.getSerialLotNo());
		
		if (isEmpty(receipt.getUnitOfMeasure()))
			receipt.setUnitOfMeasure(pd.getUnitOfMeasure());
		receipt.setPrimaryUnitOfMeasure(pd.getPrimaryUnitOfMeasure());
		receipt.setSecondaryUnitOfMeasure(pd.getSecondaryUnitOfMeasure());
		receipt.setUnitConversionFactor(pd.getUnitConversionFactor());
		
		receipt.setPaymentTermCode(pd.getPaymentTermCode());
		receipt.setTaxCode(pd.getTaxCode());
		receipt.setTaxAllowance(pd.getTaxAllowance());
		receipt.setTaxRate(pd.getTaxRate());
		
		receipt.setDiscountCode(pd.getDiscountCode());
		receipt.setDiscountRate(pd.getDiscountRate());
		receipt.setUnitDiscountCode(pd.getUnitDiscountCode());
		receipt.setUnitDiscountRate(pd.getUnitDiscountRate());
		
		receipt.setAccountId(pd.getAccountId());
		receipt.setGlClass(pd.getGlClass());
		receipt.setProjectId(pd.getProjectId());
		
		setQuantityAndCostInfo(receipt, pd);
		
		receipt.setCategoryCode(pd.getCategoryCode());
		receipt.setBrandCode(pd.getBrandCode());
		receipt.setTypeCode(pd.getTypeCode());
		receipt.setLandedCostRule(pd.getLandedCostRule());
		
		receipt.setLandedCostRecordType(isEmpty(receipt.getLandedCostRule()) ? "" : "1");
		receipt.setLastStatus("400");
		receipt.setNextStatus(isEmpty(receipt.getLandedCostRule()) ? "440" : "425");
		
		receipt.setReferences(pd.getReferences());
		receipt.setPurchaseOptions(pd.getPurchaseOptions());
		receipt.setPortOfArrivalId(pd.getOrder().getPortOfArrivalId());
		receipt.setPortOfDepartureId(pd.getOrder().getPortOfDepartureId());
		receipt.setImportDeclarationNumber(pd.getOrder().getImportDeclarationNumber());
		receipt.setImportDeclarationDate(pd.getOrder().getImportDeclarationDate());
		
		return receipt;
	}

	private PurchaseReceipt setQuantityAndCostInfo(PurchaseReceipt receipt, PurchaseDetail pd) {
		receipt.setPrimaryUnitOfMeasure(pd.getPrimaryUnitOfMeasure());
		
		BigDecimal ucf = uomServ.findConversionValue(receipt.getItemCode(), receipt.getUnitOfMeasure(), receipt.getPrimaryUnitOfMeasure());
		receipt.setUnitConversionFactor(ucf);
		receipt.setPrimaryTransactionQuantity(receipt.getQuantity().multiply(ucf));
		
		BigDecimal pdToReceipt = uomServ.findConversionValue(receipt.getItemCode(), pd.getUnitOfMeasure(), receipt.getUnitOfMeasure());
		receipt.setUnitCost(pdToReceipt.multiply(pd.getUnitCost()));
		receipt.setExtendedCost(receipt.getUnitCost().multiply(receipt.getQuantity()));
		
		//rtax = rexcost/pdexcost * pdtax
		receipt.setTaxAmount(receipt.getExtendedCost().divide(pd.getExtendedCost(), RoundingMode.HALF_UP).multiply(pd.getTaxAmount()));
		return receipt;
	}
}
