package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import ags.goldenlionerp.connectors.ModuleConnected;
import ags.goldenlionerp.connectors.ModuleConnector;

@Service
public class PurchaseReceiptService implements ModuleConnected<PurchaseReceipt>{
	
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
	
	private List<ModuleConnector<PurchaseReceipt, ?>> connectors = new ArrayList<>();

	public void registerConnector(ModuleConnector<PurchaseReceipt, ?> connector) {
		connectors.add(connector);
	}
	
	@Transactional
	public PurchaseReceiptHeader createPurchaseReceipt(PurchaseReceiptHeader receiptHead) {
		receiptHead = setDocumentAndBatchNumber(receiptHead);
		receiptHead = completePurchaseReceiptInfo(receiptHead);
		handleCreation(receiptHead);
		
		receiptHead = new PurchaseReceiptHeader(
				Lists.newArrayList(repo.saveAll(receiptHead.getDetails()))
			);		
		return receiptHead;
	}
	
	private void handleCreation(PurchaseReceiptHeader receiptHead) {
		//create new lotmasters if the items has serial numbers
		for (PurchaseReceipt rec : receiptHead.getDetails()) {
			if (!itemRepo.findById(rec.getItemCode()).get().isSerialNumberRequired())
				continue;
			//check if quantity is integer
			if (rec.getTransactionQuantity().stripTrailingZeros().scale() > 0)
				throw new IllegalArgumentException("The quantity for this item must be an integer!");
			//check if each item has a distinct serial number
			if (rec.getTransactionQuantity().intValue() != rec.getSerialOrLotNumbers().size())
				throw new IllegalArgumentException("Must input distinct serial number for each received item!");
			
			lotServ.createLotsWithSerialNumbers(rec.getBusinessUnitId(), rec.getItemCode(), rec.getSerialOrLotNumbers(), rec.getPk());
		}
		
		//let the order service receive the orders
		for (PurchaseReceipt rec : receiptHead.getDetails()) {
			orderService.receiveOrder(rec.getPk().getCompanyId(), 
					rec.getOrderNumber(), 
					rec.getOrderType(), 
					rec.getOrderSequence(), 
					rec.getTransactionQuantity(),
					rec.getUnitOfMeasure());
		}
		
		//handle optional operations after purchase receipt creation
		for (ModuleConnector<PurchaseReceipt, ?> con: connectors) {
			con.handleCreated(receiptHead.getDetails());
		}
				
	}
	
	public PurchaseReceiptHeader completePurchaseReceiptInfo(PurchaseReceiptHeader receiptHead) {
		receiptHead = setInfoFromPurchaseOrder(receiptHead);
		
		LocalDate docDate = receiptHead.getDocumentDate();
		receiptHead.getDetails().forEach(d -> d.setReceiptDate(docDate));
		return receiptHead;
	}

	private PurchaseReceiptHeader setDocumentAndBatchNumber(PurchaseReceiptHeader receiptHead) {
		String companyId = receiptHead.getCompanyId();
		String type = "OV"; //TODO
		YearMonth date = YearMonth.from(receiptHead.getDocumentDate());
		int docNo = receiptHead.getPurchaseReceiptNumber() == 0 ?
						nnServ.findNextDocumentNumber(companyId, type, date):
						receiptHead.getPurchaseReceiptNumber();
		
		for (int i = 0; i < receiptHead.getDetails().size(); i++) {
			PurchaseReceipt d = receiptHead.getDetails().get(i);
			PurchaseReceiptPK pk = new PurchaseReceiptPK(companyId, docNo, type, (i + 1)*10);
			d.setPk(pk);
		}
		//receiptHead.getDetails().forEach(d -> {
		//	PurchaseReceiptPK pk = new PurchaseReceiptPK(companyId, docNo, type, d.getPk().getSequence());
		//	d.setPk(pk);
		//});
		
		String batchType = "O"; //TODO
		int batchNo = nnServ.findNextNumber(companyId, batchType, date).getNextSequence();
		receiptHead.setBatchNumber(batchNo);
		receiptHead.setBatchType(batchType);
							
		return receiptHead;
	}
	
	private PurchaseReceiptHeader setInfoFromPurchaseOrder(PurchaseReceiptHeader receiptHead) {
		receiptHead.getDetails().forEach(d -> setInfoFromPurchaseDetail(d));
		return receiptHead;
	}

	private PurchaseReceipt setInfoFromPurchaseDetail(PurchaseReceipt receipt) {
		PurchaseOrderPK poPk = new PurchaseOrderPK(receipt.getPk().getCompanyId(), receipt.getOrderNumber(), receipt.getOrderType());
		PurchaseOrder po = orderRepo.findById(poPk)
								.orElseThrow(() -> new ResourceNotFoundException("Could not find PurchaseOrder with id " + poPk));
		PurchaseDetail pd = po.getDetails().stream().filter(d -> d.getPk().getPurchaseOrderSequence() == receipt.getOrderSequence())
								.findFirst()
								.orElseThrow(() -> new ResourceNotFoundException("Could not find PurchaseOrder with id " + poPk + " and sequence " + receipt.getOrderSequence()));
		
		if (!receipt.getVendorId().equals(pd.getVendorId()))
			throw new PurchaseReceiptException("The vendor of the receipt does not match the vendor of the order");
		
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
		receipt.setPrimaryTransactionQuantity(receipt.getTransactionQuantity().multiply(ucf));
		
		BigDecimal pdToReceipt = uomServ.findConversionValue(receipt.getItemCode(), pd.getUnitOfMeasure(), receipt.getUnitOfMeasure());
		receipt.setUnitCost(pdToReceipt.multiply(pd.getUnitCost()));
		receipt.setExtendedCost(receipt.getUnitCost().multiply(receipt.getTransactionQuantity()));
		
		//rtax = rexcost/pdexcost * pdtax
		receipt.setTaxAmount(receipt.getExtendedCost().divide(pd.getExtendedCost(), RoundingMode.HALF_UP).multiply(pd.getTaxAmount()));
		return receipt;
	}
	
	@Transactional
	public PurchaseReceiptHeader updatePurchaseReceipt(PurchaseReceiptHeader receiptHead) {
		voidReceipts(receiptHead);
		
		//get the existing receipts first
		PurchaseReceiptHeader existingReceiptHead = new PurchaseReceiptHeader(Lists.newArrayList(repo.findAll(
				PurchaseReceiptPredicates.getInstance().sameHeaderAs(receiptHead.getDetails().get(0))
			)));
		List<PurchaseReceiptPK> existingPks = existingReceiptHead.getDetails().stream().map(rec -> rec.getPk()).collect(Collectors.toList());	
		
		//select the new receipts from the request
		List<PurchaseReceipt> newReceipts = receiptHead.getDetails().stream()
											.filter(rec -> !existingPks.contains(rec.getPk()))
											.collect(Collectors.toList());
		if (newReceipts.isEmpty())
			return existingReceiptHead;
		
		PurchaseReceiptHeader newReceiptHead = new PurchaseReceiptHeader(
				existingReceiptHead.getCompanyId(),
				existingReceiptHead.getPurchaseReceiptNumber(),
				existingReceiptHead.getPurchaseReceiptType(),
				existingReceiptHead.getBusinessUnitId(),
				existingReceiptHead.getDocumentDate(),
				existingReceiptHead.getVendorId(),
				existingReceiptHead.getCustomerOrderNumber(),
				existingReceiptHead.getDescription(),				
				newReceipts
			);
		newReceiptHead.setBatchNumber(existingReceiptHead.getBatchNumber());
		newReceiptHead.setBatchType(existingReceiptHead.getBatchType());
		//complete info for new receipts
		newReceiptHead = completePurchaseReceiptInfo(newReceiptHead);
		
		List<PurchaseReceipt> allReceipts = Stream.of(existingReceiptHead.getDetails(), newReceiptHead.getDetails())
												.flatMap(List::stream)
												.collect(Collectors.toList());
		//set sequence numbers for missing ones
		int maxSeq = allReceipts.stream().mapToInt(rec -> rec.getPk().getSequence()).max().orElse(0);
		List<PurchaseReceipt> unsequenceds = allReceipts.stream()
												.filter(rec -> rec.getPk().getSequence() == 0)
												.collect(Collectors.toList());
		for (int i = 0; i < unsequenceds.size(); i++) {
			PurchaseReceiptPK oldPk = unsequenceds.get(i).getPk();
			PurchaseReceiptPK pk = new PurchaseReceiptPK(
					oldPk.getCompanyId(), 
					oldPk.getPurchaseReceiptNumber(), 
					oldPk.getPurchaseReceiptType(), 
					maxSeq + 10*(i + 1));
			unsequenceds.get(i).setPk(pk);
		}
		
		//save the created receipts
		handleCreation(receiptHead);
		repo.saveAll(newReceiptHead.getDetails());
		
		return new PurchaseReceiptHeader(Lists.newArrayList(repo.findAll(
				PurchaseReceiptPredicates.getInstance().sameHeaderAs(receiptHead.getDetails().get(0))
			)));
	}

	private void voidReceipts(PurchaseReceiptHeader receiptHead) {
		List<PurchaseReceipt> toBeVoideds = receiptHead.getDetails().stream()
												.filter(PurchaseReceipt::isToBeVoided)
												.collect(Collectors.toList());
		if (toBeVoideds.isEmpty())
			return;
		
		//get the existing receipts first
		PurchaseReceiptHeader existingReceiptHead = new PurchaseReceiptHeader(Lists.newArrayList(repo.findAll(
				PurchaseReceiptPredicates.getInstance().sameHeaderAs(receiptHead.getDetails().get(0))
			)));
		
		int i = 10;
		List<PurchaseReceipt> negateReceipts = new ArrayList<>();;
		for (PurchaseReceipt toBeVoided: toBeVoideds) {
			//if the receipt is already voided then no need to void it again
			if (toBeVoided.isVoided())
				continue;
			
			PurchaseReceipt existing = existingReceiptHead.getDetails().stream()
										.filter(det -> det.getPk().equals(toBeVoided.getPk()))
										.findFirst()
										.orElseThrow(() -> new PurchaseReceiptException("Cannot void a nonexistent receipt!"));
			
			int seq = receiptHead.getHighestSequence() + i;
			i += 10;
			
			//create a copy of the current
			PurchaseReceiptPK negatePk = new PurchaseReceiptPK(
					existing.getPk().getCompanyId(), 
					existing.getPk().getPurchaseReceiptNumber(), 
					existing.getPk().getPurchaseReceiptType(), 
					seq);
			PurchaseReceipt negatedRec = new PurchaseReceipt();
			negatedRec.setPk(negatePk);
			negatedRec.setOrderNumber(existing.getOrderNumber());
			negatedRec.setOrderType(existing.getOrderType());
			negatedRec.setOrderSequence(existing.getOrderSequence());
			negatedRec.setTransactionQuantity(existing.getTransactionQuantity().negate());
			negateReceipts.add(negatedRec);
			
			existing.setLastStatus("499");
			existing.setNextStatus("999");
			repo.save(existing);
		}
		
		PurchaseReceiptHeader negatedReceiptHead = new PurchaseReceiptHeader(
				existingReceiptHead.getCompanyId(),
				existingReceiptHead.getPurchaseReceiptNumber(),
				existingReceiptHead.getPurchaseReceiptType(),
				existingReceiptHead.getBusinessUnitId(),
				existingReceiptHead.getDocumentDate(),
				existingReceiptHead.getVendorId(),
				existingReceiptHead.getCustomerOrderNumber(),
				existingReceiptHead.getDescription(),				
				negateReceipts
			);
		negatedReceiptHead = completePurchaseReceiptInfo(negatedReceiptHead);
		negatedReceiptHead.getDetails().forEach(d -> {
			d.setLastStatus("499");
			d.setNextStatus("999");
		});
		handleCreation(negatedReceiptHead);
		repo.saveAll(negateReceipts);
	}
}
