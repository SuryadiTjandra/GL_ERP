package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.google.common.collect.Lists;

import ags.goldenlionerp.application.item.uomconversion.UomConversionService;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseDetail;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrder;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderPK;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.documents.ItemTransactionObserver;
import ags.goldenlionerp.documents.ItemTransactionService;

@Service
public class PurchaseReceiptService implements ItemTransactionService{
	
	@Autowired
	private NextNumberService nnServ;
	@Autowired
	private PurchaseReceiptRepository repo;
	@Autowired
	private PurchaseOrderRepository orderRepo;
	@Autowired
	private UomConversionService uomServ;
	
	private List<ItemTransactionObserver> observers = new ArrayList<>();

	@Override
	public void registerObserver(ItemTransactionObserver observer) {
		observers.add(observer);
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
		for (PurchaseReceipt rec : receiptHead.getDetails()) {
			observers.forEach(observer -> observer.handleCreation(rec));
		}
			
	}
	
	public PurchaseReceiptHeader completePurchaseReceiptInfo(PurchaseReceiptHeader receiptHead) {
		receiptHead = setInfoFromPurchaseOrder(receiptHead);
		
		LocalDate docDate = receiptHead.getTransactionDate();
		receiptHead.getDetails().forEach(d -> d.setReceiptDate(docDate));
		return receiptHead;
	}

	private PurchaseReceiptHeader setDocumentAndBatchNumber(PurchaseReceiptHeader receiptHead) {
		String companyId = receiptHead.getCompanyId();
		String type = "OV"; //TODO
		YearMonth date = YearMonth.from(receiptHead.getTransactionDate());
		int docNo = receiptHead.getDocumentNumber() == 0 ?
						nnServ.findNextDocumentNumber(companyId, type, date):
						receiptHead.getDocumentNumber();
		
		for (int i = 0; i < receiptHead.getDetails().size(); i++) {
			PurchaseReceipt d = receiptHead.getDetails().get(i);
			PurchaseReceiptPK pk = new PurchaseReceiptPK(companyId, docNo, type, (i + 1)*10);
			d.setPk(pk);
		}
		
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
		
		
		receipt.setSerialOrLotNumbers(receipt.getSerialOrLotNumbers());
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
				existingReceiptHead.getDocumentNumber(),
				existingReceiptHead.getDocumentType(),
				existingReceiptHead.getBusinessUnitId(),
				existingReceiptHead.getTransactionDate(),
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
		
		List<PurchaseReceipt> negateReceipts = new ArrayList<>();
		for (PurchaseReceipt toBeVoided: toBeVoideds) {
			//if the receipt is already voided then no need to void it again
			if (toBeVoided.isVoided())
				continue;
			
			PurchaseReceipt existing = existingReceiptHead.getDetails().stream()
										.filter(det -> det.getPk().equals(toBeVoided.getPk()))
										.findFirst()
										.orElseThrow(() -> new PurchaseReceiptException("Cannot void a nonexistent receipt!"));
			
			//create a copy of the current
			PurchaseReceiptPK negatePk = new PurchaseReceiptPK(
					existing.getPk().getCompanyId(), 
					existing.getPk().getPurchaseReceiptNumber(), 
					existing.getPk().getPurchaseReceiptType(), 
					existing.getPk().getSequence() + 1);
			PurchaseReceipt negatedRec = new PurchaseReceipt();
			negatedRec.setPk(negatePk);
			negatedRec.setOrderNumber(existing.getOrderNumber());
			negatedRec.setOrderType(existing.getOrderType());
			negatedRec.setOrderSequence(existing.getOrderSequence());
			negatedRec.setTransactionQuantity(existing.getTransactionQuantity().negate());
			negateReceipts.add(negatedRec);
			
			existing.setLastStatus("499");
			existing.setNextStatus("999");
			observers.forEach(obs -> obs.handleVoidation(existing));
			repo.save(existing);
		}
		
		PurchaseReceiptHeader negatedReceiptHead = new PurchaseReceiptHeader(
				existingReceiptHead.getCompanyId(),
				existingReceiptHead.getDocumentNumber(),
				existingReceiptHead.getDocumentType(),
				existingReceiptHead.getBusinessUnitId(),
				existingReceiptHead.getTransactionDate(),
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
		//handleCreation(negatedReceiptHead);
		repo.saveAll(negateReceipts);
	}

	public Object getDefaultPurchaseReceipt(String appParamCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", "11000");
		map.put("documentType", "OV");
		map.put("businessUnitId", "110");
		map.put("nextStatus", "440");
		map.put("lastStatus", "400");
		map.put("transactionDate", LocalDate.now());
		return map;
	}

}
