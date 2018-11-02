package ags.goldenlionerp.application.purchase.purchaseorder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.application.item.itemLocation.ItemLocation;
import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.item.itemmaster.ItemMasterRepository;
import ags.goldenlionerp.application.item.uomconversion.UomConversionService;
import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.discount.DiscountMaster;
import ags.goldenlionerp.application.setups.discount.DiscountRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.application.setups.taxcode.TaxRule;
import ags.goldenlionerp.application.setups.taxcode.TaxRuleRepository;

@Service
public class PurchaseOrderService {

	@Autowired private PurchaseOrderRepository repo;
	@Autowired private NextNumberService nnServ;
	@Autowired private CompanyRepository compRepo;
	@Autowired private AddressBookRepository addrRepo;
	@Autowired private ItemMasterRepository itemRepo;
	@Autowired private UomConversionService uomServ;
	@Autowired private TaxRuleRepository taxRepo;
	@Autowired private DiscountRepository discRepo;
	
	public PurchaseOrder createPurchaseOrder(PurchaseOrder poRequest) {
		poRequest = setPk(poRequest);
		poRequest = setInfoFromCompany(poRequest);
		poRequest = setInfoFromVendor(poRequest);
		poRequest = setInfoFromItems(poRequest);
		poRequest = setDates(poRequest);
		poRequest = setCostsAndQuantities(poRequest);
		poRequest = setDiscountInfo(poRequest);
		poRequest = setTaxInfo(poRequest);
		poRequest = setMiscellaneous(poRequest);
		return repo.save(poRequest);
	}

	private PurchaseOrder setPk(PurchaseOrder poRequest) {
		PurchaseOrderPK oldPk = poRequest.getPk();
		
		int docNo = oldPk.getPurchaseOrderNumber();
		if (docNo == 0) {
			docNo = nnServ.findNextDocumentNumber(
						oldPk.getCompanyId(), 
						oldPk.getPurchaseOrderType(), 
						YearMonth.from(poRequest.getOrderDate())
					);
		}
		
		PurchaseOrderPK newPk = new PurchaseOrderPK(
				oldPk.getCompanyId(),
				docNo,
				oldPk.getPurchaseOrderType()
			);
		poRequest.setPk(newPk);
		return poRequest;
	}
	
	private PurchaseOrder setInfoFromVendor(PurchaseOrder poRequest) {
		AddressBookMaster vendor = addrRepo.findById(poRequest.getVendorId())
				.filter(ven -> ven.getAccountPayable() == true)
				.filter(ven -> ven.getApSetting().isPresent())
				.orElseThrow(() -> new ResourceNotFoundException("Business partner with id " + poRequest.getVendorId() + " either does not exist or is not a vendor"));
		
		if (poRequest.getTransactionCurrency() == null || poRequest.getTransactionCurrency().isEmpty()) {
			poRequest.setTransactionCurrency(vendor.getApSetting().get().getCurrencyCodeTransaction());
		}
		if (poRequest.getPaymentTermCode() == null || poRequest.getPaymentTermCode().isEmpty()) {
			poRequest.setPaymentTermCode(vendor.getApSetting().get().getPaymentTermCode());
		}
		if (poRequest.getTaxCode() == null || poRequest.getTaxCode().isEmpty()) {
			poRequest.setTaxCode(vendor.getApSetting().get().getTaxCode());
		}
		if (poRequest.getReceiverId() == null || poRequest.getReceiverId().isEmpty()) {
			poRequest.setReceiverId(poRequest.getVendorId());
		}
		return poRequest;
	}
	
	private PurchaseOrder setInfoFromCompany(PurchaseOrder poRequest) {
		String coId = poRequest.getPk().getCompanyId();
		Company company = compRepo.findById(coId)
							.orElseThrow(() -> new ResourceNotFoundException("Company with id " + coId + " does not exist."));
		poRequest.setBaseCurrency(company.getCurrencyCodeBase());
		return poRequest;
	}
	
	private PurchaseOrder setInfoFromItems(PurchaseOrder poRequest) {
		List<PurchaseDetail> details = poRequest.getDetails().stream()
										.map(this::setInfoFromItem)
										.collect(Collectors.toList());
		poRequest.setDetails(details);
		return poRequest;
	}
	
	private PurchaseDetail setInfoFromItem(PurchaseDetail poDetail) {
		ItemMaster item;
		if (poDetail.getItem() == null) {
			item = itemRepo.findById(poDetail.getItemCode())
					.orElseThrow(() -> new ResourceNotFoundException("Item with id " + poDetail.getItemCode() + " does not exist"));
		}else {
			item = poDetail.getItem();
			poDetail.setItemCode(item.getItemCode());
		}
		
		String primaryUom = item.getUnitsOfMeasure().getPrimaryUnitOfMeasure();
		poDetail.setPrimaryUnitOfMeasure(primaryUom);
		if (poDetail.getUnitOfMeasure() == null || poDetail.getUnitOfMeasure().isEmpty()) {
			poDetail.setUnitOfMeasure(primaryUom);
		}
		
		String secondaryUom = item.getUnitsOfMeasure().getSecondaryUnitOfMeasure();
		poDetail.setSecondaryUnitOfMeasure(secondaryUom);
		
		poDetail.setGlClass(item.getGlClass());
		poDetail.setDescription(item.getDescription());
		poDetail.setLineType(item.getTransactionType());
		
		if (poDetail.getLocationId() == null || poDetail.getLocationId().isEmpty()) {
			ItemLocation primLocation = item.getItemLocations().stream()
											.filter(il -> il.getPk().getBusinessUnitId().equals(poDetail.getBusinessUnitId()))
											.filter(ItemLocation::isPrimary)
											.findFirst()
											.orElse(null);
			if (primLocation != null) {
				poDetail.setLocationId(primLocation.getPk().getLocationId());
				poDetail.setSerialLotNo(primLocation.getPk().getSerialLotNo());
			}
		}
		
		return poDetail;
	}
	
	private PurchaseOrder setDates(PurchaseOrder poRequest) {
		LocalDate orderDate = poRequest.getOrderDate();
		if (poRequest.getRequestDate() == null)
			poRequest.setRequestDate(orderDate);
		
		poRequest.setReceiptDate(null); //because order is just created, not yet received
		poRequest.setClosedDate(null); //because order is just created
		return poRequest;
	}
	
	private PurchaseOrder setCostsAndQuantities(PurchaseOrder poRequest) {
		List<PurchaseDetail> processedDetails = poRequest.getDetails().stream()
				.map(this::setDetailCostsAndQuantities)
				.collect(Collectors.toList());
		poRequest.setDetails(processedDetails);
		return poRequest;
	}
	
	private PurchaseDetail setDetailCostsAndQuantities(PurchaseDetail poDetail) {
		poDetail.setOrderQuantity(poDetail.getQuantity());
		//set the followings because the order is just created
		poDetail.setReceivedQuantity(BigDecimal.ZERO); 
		poDetail.setReturnedQuantity(BigDecimal.ZERO);
		poDetail.setCancelledQuantity(BigDecimal.ZERO);
		poDetail.setOpenQuantity(poDetail.getQuantity());
		
		String primaryUom = poDetail.getPrimaryUnitOfMeasure();
		if (poDetail.getUnitOfMeasure() == null || poDetail.getUnitOfMeasure().isEmpty()) {
			poDetail.setUnitOfMeasure(primaryUom);
		}
		
		BigDecimal ucf = uomServ.findConversionValue(
				poDetail.getItemCode(), 
				poDetail.getUnitOfMeasure(),
				primaryUom
			);
		BigDecimal primaryQty = poDetail.getQuantity().multiply(ucf);
		
		poDetail.setPrimaryOrderQuantity(primaryQty);
		poDetail.setUnitConversionFactor(ucf);
		
		//extended quantity
		if (poDetail.isExtended()) {
			BigDecimal extConv = uomServ.findConversionValue(
				poDetail.getItemCode(), 
				poDetail.getUnitOfMeasure(),
				poDetail.getExtendedUnitOfMeasure()
			);
			BigDecimal extQty = poDetail.getQuantity().multiply(extConv);
			poDetail.setExtendedQuantity(extQty);
			
			BigDecimal extUcf = uomServ.findConversionValue(
				poDetail.getItemCode(), 
				poDetail.getExtendedUnitOfMeasure(),
				poDetail.getPrimaryUnitOfMeasure()
			);
			poDetail.setExtendedUnitConversionFactor(extUcf);
		}
		
		//costs
		if (poDetail.isExtended()) {
			BigDecimal extendedCost = poDetail.getExtendedUnitCost().multiply(poDetail.getExtendedQuantity());
			poDetail.setExtendedCost(extendedCost);
			poDetail.setUnitCost(extendedCost.divide(poDetail.getQuantity(), 5, RoundingMode.HALF_UP));
		} else {
			BigDecimal extendedCost = poDetail.getUnitCost().multiply(poDetail.getQuantity());
			poDetail.setExtendedCost(extendedCost);
		}
		
		
		return poDetail;
	}

	private PurchaseOrder setDiscountInfo(PurchaseOrder poRequest) {
		//apply unit/item discounts first
		List<PurchaseDetail> details = poRequest.getDetails().stream()
				.map(this::setDetailDiscountInfo)
				.collect(Collectors.toList());
		poRequest.setDetails(details);
		
		//start apply order discount
		if (poRequest.getDiscountCode() == null || poRequest.getDiscountCode().isEmpty())
			return poRequest;
		
		Optional<DiscountMaster> discOpt = discRepo.findById(poRequest.getDiscountCode());
		if (!discOpt.isPresent()) {
			return poRequest;
		}
		
		//find the discount rate
		DiscountMaster disc = discOpt.get();
		BigDecimal amount = poRequest.getCostAfterUnitDiscount();
		BigDecimal discountRate = disc.calculateDiscountPercentage(amount);
		
		poRequest.setDiscountRate(discountRate);
		return poRequest;
	}
	
	private PurchaseDetail setDetailDiscountInfo(PurchaseDetail poDetail) {
		if (poDetail.getUnitDiscountCode() == null || poDetail.getUnitDiscountCode().isEmpty())
			return poDetail;
		
		Optional<DiscountMaster> discOpt = discRepo.findById(poDetail.getUnitDiscountCode());
		if (!discOpt.isPresent()) {
			return poDetail;
		}
		
		//find the discount amount
		DiscountMaster disc = discOpt.get();
		BigDecimal amount = poDetail.getUnitCost();
		BigDecimal discountRate = disc.calculateDiscountPercentage(amount);
		
		poDetail.setUnitDiscountRate(discountRate);
		return poDetail;
	}
	
	private PurchaseOrder setTaxInfo(PurchaseOrder poRequest) {
		if (poRequest.getTaxCode() == null || poRequest.getTaxCode().isEmpty())
			return poRequest;
		
		TaxRule taxRule = taxRepo.findActiveTaxRuleAt(poRequest.getTaxCode(), poRequest.getOrderDate())
								.orElseThrow(() -> new ResourceNotFoundException("Tax rule with the code " + poRequest.getTaxCode() + " either does not exist or is not active"));
		
		poRequest.setTaxAllowance(taxRule.getTaxAllowance());
		
		BigDecimal taxRate = taxRule.getTaxPercentage1()
								.add(taxRule.getTaxPercentage2())
								.add(taxRule.getTaxPercentage3())
								.add(taxRule.getTaxPercentage4())
								.add(taxRule.getTaxPercentage5());
		poRequest.setTaxRate(taxRate);
		
		List<PurchaseDetail> details = poRequest.getDetails().stream()
										.map(this::setDetailTaxInfo)
										.collect(Collectors.toList());
		poRequest.setDetails(details);
		return poRequest;
	}
	
	private PurchaseDetail setDetailTaxInfo(PurchaseDetail poDetail) {
		BigDecimal amount = poDetail.getCostAfterUnitDiscount();
		if (poDetail.getTaxAllowance() == true) {
			/*
			 * In this case, the tax is already included in the amount,
			 * so we're looking for the amount before the tax first to get tax amount
			 */
			BigDecimal taxMultiplier = poDetail.getTaxRate().divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);
			BigDecimal amountBeforeTax = amount.divide(taxMultiplier, RoundingMode.HALF_UP);
			BigDecimal taxAmount = amount.subtract(amountBeforeTax);
			poDetail.setTaxAmount(taxAmount);
		} else {
			/*
			 *In this case, tax is not included, so to find the tax amount 
			 *we just multiply amount by tax rate
			 */
			BigDecimal taxAmount = poDetail.getTaxRate().multiply(amount);
			poDetail.setTaxAmount(taxAmount);
		}
		return poDetail;
	}
	
	private PurchaseOrder setMiscellaneous(PurchaseOrder poRequest) {
		
		poRequest.setLastStatus("220"); //from where?
		poRequest.setNextStatus("400"); //from where?
		
		return poRequest;
	}
	
	public PurchaseOrder getDefaultPurchaseOrder(String appParamCode) {
		//TODO actually fetch values from DB, not hardcode
		PurchaseOrderPK pk = new PurchaseOrderPK("11000", 0, "OP");
		PurchaseOrder po = new PurchaseOrder(Collections.emptyList());
		po.setPk(pk);
		po.setBusinessUnitId("110");
		po.setLastStatus("220");
		po.setNextStatus("400");
		return po;
	}
	
	public PurchaseOrder updatePurchaseOrder(PurchaseOrder poRequest) {
		
		//cancel all details marked for cancel
		List<PurchaseDetail> details = poRequest.getDetails();
		for (PurchaseDetail det : details) {
			if (det.isSetForVoid())
				det.voidDocument();
		}
		
		//only save detail changes, ignore changes to the order object
		PurchaseOrder oldPo = repo.findById(poRequest.getPk())
				.orElseThrow(() -> new ResourceNotFoundException());
		oldPo.setDetails(poRequest.getDetails());
		return repo.save(oldPo);
	}
	
	public void receiveOrder(String companyId, int purchaseOrderNumber, String purchaseOrderType, int sequence, BigDecimal receiveQuantity, String unitOfMeasure) {
		PurchaseOrderPK pk = new PurchaseOrderPK(companyId, purchaseOrderNumber, purchaseOrderType);
		PurchaseOrder order = repo.findById(pk)
								.orElseThrow(() -> new ResourceNotFoundException("Could not find purchase order with id " + pk));
		PurchaseDetail detail = order.getDetails().stream()
								.filter(det -> det.getPk().getPurchaseOrderSequence() == sequence)
								.findFirst().orElseThrow(() -> new ResourceNotFoundException("Could not find purchase order with id " + pk + " and sequence " + sequence));
		
		BigDecimal conversionValue = uomServ.findConversionValue(detail.getItemCode(), unitOfMeasure, detail.getUnitOfMeasure());
		receiveQuantity = receiveQuantity.multiply(conversionValue);
		
		detail.setReceivedQuantity(detail.getReceivedQuantity().add(receiveQuantity));
		detail.setOpenQuantity(detail.getOpenQuantity().subtract(receiveQuantity));
		repo.save(order);
	}
}
