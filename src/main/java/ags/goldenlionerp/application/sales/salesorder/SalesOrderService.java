package ags.goldenlionerp.application.sales.salesorder;

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
import ags.goldenlionerp.application.item.uomconversion.UomConversionService;
import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.discount.DiscountMaster;
import ags.goldenlionerp.application.setups.discount.DiscountRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.application.setups.taxcode.TaxRule;
import ags.goldenlionerp.application.setups.taxcode.TaxRuleRepository;
import ags.goldenlionerp.masterdata.itemLocation.ItemLocation;
import ags.goldenlionerp.masterdata.itemmaster.ItemMaster;
import ags.goldenlionerp.masterdata.itemmaster.ItemMasterRepository;

@Service
public class SalesOrderService {
	
	@Autowired
	private SalesOrderRepository repo;
	@Autowired private NextNumberService nnServ;
	@Autowired private CompanyRepository compRepo;
	@Autowired private AddressBookRepository addrRepo;
	@Autowired private ItemMasterRepository itemRepo;
	@Autowired private UomConversionService uomServ;
	@Autowired private TaxRuleRepository taxRepo;
	@Autowired private DiscountRepository discRepo;

	public SalesOrder createSalesOrder(SalesOrder soRequest) {
		soRequest = setPk(soRequest);
		soRequest = setInfoFromCompany(soRequest);
		soRequest = setInfoFromCustomer(soRequest);
		soRequest = setInfoFromItems(soRequest);
		soRequest = setDates(soRequest);
		soRequest = setPricesAndQuantities(soRequest);
		soRequest = setDiscountInfo(soRequest);
		soRequest = setTaxInfo(soRequest);
		soRequest = setMiscellaneous(soRequest);
		return repo.save(soRequest);
	}

	private SalesOrder setPk(SalesOrder soRequest) {
		SalesOrderPK oldPk = soRequest.getPk();
		
		int docNo = oldPk.getSalesOrderNumber();
		if (docNo == 0) {
			docNo = nnServ.findNextDocumentNumber(
						oldPk.getCompanyId(), 
						oldPk.getSalesOrderType(), 
						YearMonth.from(soRequest.getOrderDate())
					);
		}
		
		SalesOrderPK newPk = new SalesOrderPK(
				oldPk.getCompanyId(),
				docNo,
				oldPk.getSalesOrderType()
			);
		soRequest.setPk(newPk);
		return soRequest;	
	}

	private SalesOrder setInfoFromCompany(SalesOrder soRequest) {
		String coId = soRequest.getPk().getCompanyId();
		Company company = compRepo.findById(coId)
							.orElseThrow(() -> new ResourceNotFoundException("Company with id " + coId + " does not exist."));
		soRequest.setBaseCurrency(company.getCurrencyCodeBase());
		return soRequest;
	}

	private SalesOrder setInfoFromCustomer(SalesOrder soRequest) {
		AddressBookMaster customer = addrRepo.findById(soRequest.getCustomerId())
				.filter(cus -> cus.getAccountReceivable() == true)
				.filter(cus -> cus.getArSetting().isPresent())
				.orElseThrow(() -> new ResourceNotFoundException("Business partner with id " + soRequest.getCustomerId() + " either does not exist or is not a customer"));
		
		if (soRequest.getTransactionCurrency() == null || soRequest.getTransactionCurrency().isEmpty()) {
			soRequest.setTransactionCurrency(customer.getArSetting().get().getCurrencyCodeTransaction());
		}
		if (soRequest.getPaymentTermCode() == null || soRequest.getPaymentTermCode().isEmpty()) {
			soRequest.setPaymentTermCode(customer.getArSetting().get().getPaymentTermCode());
		}
		if (soRequest.getTaxCode() == null || soRequest.getTaxCode().isEmpty()) {
			soRequest.setTaxCode(customer.getArSetting().get().getTaxCode());
		}
		if (soRequest.getReceiverId() == null || soRequest.getReceiverId().isEmpty()) {
			soRequest.setReceiverId(soRequest.getCustomerId());
		}
		
		return soRequest;
	}

	private SalesOrder setInfoFromItems(SalesOrder soRequest) {
		List<SalesDetail> details = soRequest.getDetails().stream()
				.map(this::setInfoFromItem)
				.collect(Collectors.toList());
		soRequest.setDetails(details);
		return soRequest;
	}
	
	private SalesDetail setInfoFromItem(SalesDetail soDetail) {
		ItemMaster item;
		if (soDetail.getItem() == null) {
			item = itemRepo.findById(soDetail.getItemCode())
					.orElseThrow(() -> new ResourceNotFoundException("Item with id " + soDetail.getItemCode() + " does not exist"));
		}else {
			item = soDetail.getItem();
			soDetail.setItemCode(item.getItemCode());
		}
		
		String primaryUom = item.getUnitsOfMeasure().getPrimaryUnitOfMeasure();
		soDetail.setPrimaryUnitOfMeasure(primaryUom);
		if (soDetail.getUnitOfMeasure() == null || soDetail.getUnitOfMeasure().isEmpty()) {
			soDetail.setUnitOfMeasure(primaryUom);
		}
		
		String secondaryUom = item.getUnitsOfMeasure().getSecondaryUnitOfMeasure();
		soDetail.setSecondaryUnitOfMeasure(secondaryUom);
		
		soDetail.setGlClass(item.getGlClass());
		soDetail.setDescription(item.getDescription());
		soDetail.setLineType(item.getTransactionType());
		
		if (soDetail.getLocationId() == null || soDetail.getLocationId().isEmpty()) {
			ItemLocation primLocation = item.getItemLocations().stream()
											.filter(il -> il.getPk().getBusinessUnitId().equals(soDetail.getBusinessUnitId()))
											.filter(ItemLocation::isPrimary)
											.findFirst()
											.orElse(null);
			if (primLocation != null) {
				soDetail.setLocationId(primLocation.getPk().getLocationId());
				soDetail.setSerialLotNo(primLocation.getPk().getSerialLotNo());
			}
		}
		
		return soDetail;
	}

	private SalesOrder setDates(SalesOrder soRequest) {
		LocalDate orderDate = soRequest.getOrderDate();
		if (soRequest.getRequestDate() == null)
			soRequest.setRequestDate(orderDate);
		
		soRequest.setClosedDate(null); //because order is just created
		return soRequest;
	}

	private SalesOrder setPricesAndQuantities(SalesOrder soRequest) {
		List<SalesDetail> processedDetails = soRequest.getDetails().stream()
				.map(this::setDetailPricesAndQuantities)
				.collect(Collectors.toList());
		soRequest.setDetails(processedDetails);
		return soRequest;
	}
	
	private SalesDetail setDetailPricesAndQuantities(SalesDetail soDetail) {
		//set the followings because the order is just created
		soDetail.setShippedQuantity(BigDecimal.ZERO); 
		soDetail.setReturnedQuantity(BigDecimal.ZERO);
		soDetail.setCancelledQuantity(BigDecimal.ZERO);
		soDetail.setOpenQuantity(soDetail.getQuantity());
		
		String primaryUom = soDetail.getPrimaryUnitOfMeasure();
		if (soDetail.getUnitOfMeasure() == null || soDetail.getUnitOfMeasure().isEmpty()) {
			soDetail.setUnitOfMeasure(primaryUom);
		}
		
		BigDecimal ucf = uomServ.findConversionValue(
				soDetail.getItemCode(), 
				soDetail.getUnitOfMeasure(),
				primaryUom
			);
		BigDecimal primaryQty = soDetail.getQuantity().multiply(ucf);
		
		soDetail.setPrimaryOrderQuantity(primaryQty);
		soDetail.setUnitConversionFactor(ucf);
		
		//extended quantity
		if (soDetail.isExtended()) {
			BigDecimal extConv = uomServ.findConversionValue(
				soDetail.getItemCode(), 
				soDetail.getUnitOfMeasure(),
				soDetail.getExtendedUnitOfMeasure()
			);
			BigDecimal extQty = soDetail.getQuantity().multiply(extConv);
			soDetail.setExtendedQuantity(extQty);
			
			BigDecimal extUcf = uomServ.findConversionValue(
				soDetail.getItemCode(), 
				soDetail.getExtendedUnitOfMeasure(),
				soDetail.getPrimaryUnitOfMeasure()
			);
			soDetail.setExtendedUnitConversionFactor(extUcf);
		}
		
		//prices
		if (soDetail.isExtended()) {
			BigDecimal extendedPrice = soDetail.getExtendedUnitPrice().multiply(soDetail.getExtendedQuantity());
			soDetail.setExtendedPrice(extendedPrice);
			soDetail.setUnitPrice(extendedPrice.divide(soDetail.getQuantity(), 5, RoundingMode.HALF_UP));
		} else {
			BigDecimal extendedPrice = soDetail.getUnitPrice().multiply(soDetail.getQuantity());
			soDetail.setExtendedPrice(extendedPrice);
		}
		
		
		return soDetail;
	}

	private SalesOrder setDiscountInfo(SalesOrder soRequest) {
		//apply unit/item discounts first
		List<SalesDetail> details = soRequest.getDetails().stream()
				.map(this::setDetailDiscountInfo)
				.collect(Collectors.toList());
		soRequest.setDetails(details);
		
		//start apply order discount
		if (soRequest.getDiscountCode() == null || soRequest.getDiscountCode().isEmpty())
			return soRequest;
		
		Optional<DiscountMaster> discOpt = discRepo.findById(soRequest.getDiscountCode());
		if (!discOpt.isPresent()) {
			return soRequest;
		}
		
		//find the discount rate
		DiscountMaster disc = discOpt.get();
		BigDecimal amount = soRequest.getPriceAfterUnitDiscount();
		BigDecimal discountRate = disc.calculateDiscountPercentage(amount);
		
		soRequest.setDiscountRate(discountRate);
		return soRequest;
	}
	
	private SalesDetail setDetailDiscountInfo(SalesDetail soDetail) {
		if (soDetail.getUnitDiscountCode() == null || soDetail.getUnitDiscountCode().isEmpty())
			return soDetail;
		
		Optional<DiscountMaster> discOpt = discRepo.findById(soDetail.getUnitDiscountCode());
		if (!discOpt.isPresent()) {
			return soDetail;
		}
		
		//find the discount amount
		DiscountMaster disc = discOpt.get();
		BigDecimal amount = soDetail.getUnitPrice();
		BigDecimal discountRate = disc.calculateDiscountPercentage(amount);
		
		soDetail.setUnitDiscountRate(discountRate);
		return soDetail;
	}

	private SalesOrder setTaxInfo(SalesOrder soRequest) {
		if (soRequest.getTaxCode() == null || soRequest.getTaxCode().isEmpty())
			return soRequest;
		
		TaxRule taxRule = taxRepo.findActiveTaxRuleAt(soRequest.getTaxCode(), soRequest.getOrderDate())
								.orElseThrow(() -> new ResourceNotFoundException("Tax rule with the code " + soRequest.getTaxCode() + " either does not exist or is not active"));
		
		soRequest.setTaxAllowance(taxRule.getTaxAllowance());
		
		BigDecimal taxRate = taxRule.getTaxPercentage1()
								.add(taxRule.getTaxPercentage2())
								.add(taxRule.getTaxPercentage3())
								.add(taxRule.getTaxPercentage4())
								.add(taxRule.getTaxPercentage5());
		soRequest.setTaxRate(taxRate);
		
		List<SalesDetail> details = soRequest.getDetails().stream()
										.map(this::setDetailTaxInfo)
										.collect(Collectors.toList());
		soRequest.setDetails(details);
		return soRequest;
	}
	
	private SalesDetail setDetailTaxInfo(SalesDetail soDetail) {
		BigDecimal amount = soDetail.getPriceAfterUnitDiscount();
		if (soDetail.getTaxAllowance() == true) {
			/*
			 * In this case, the tax is already included in the amount,
			 * so we're looking for the amount before the tax first to get tax amount
			 */
			BigDecimal taxMultiplier = soDetail.getTaxRate().divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);
			BigDecimal amountBeforeTax = amount.divide(taxMultiplier, RoundingMode.HALF_UP);
			BigDecimal taxAmount = amount.subtract(amountBeforeTax);
			soDetail.setTaxAmount(taxAmount);
		} else {
			/*
			 *In this case, tax is not included, so to find the tax amount 
			 *we just multiply amount by tax rate
			 */
			BigDecimal taxAmount = soDetail.getTaxRate().multiply(amount);
			soDetail.setTaxAmount(taxAmount);
		}
		return soDetail;
	}

	private SalesOrder setMiscellaneous(SalesOrder soRequest) {
		soRequest.setLastStatus("520"); //from where?
		soRequest.setNextStatus("560"); //from where?
		
		if (soRequest.getProfitCenterId() == null || soRequest.getProfitCenterId().isEmpty())
			soRequest.setProfitCenterId(soRequest.getBusinessUnitId());
		
		return soRequest;
	}
	
	public SalesOrder getDefaultSalesOrder(String appParamCode) {
		//TODO actually fetch values from DB, not hardcode
		SalesOrderPK pk = new SalesOrderPK("11000", 0, "SO");
		SalesOrder po = new SalesOrder(Collections.emptyList());
		po.setPk(pk);
		po.setBusinessUnitId("110");
		po.setLastStatus("520");
		po.setNextStatus("560");
		return po;
	}

	public SalesOrder updateSalesOrder(SalesOrder soRequest) {
		//cancel all details marked for cancel
		List<SalesDetail> details = soRequest.getDetails();
		for (SalesDetail det : details) {
			if (det.isSetForVoid())
				det.voidDocument();
		}
		
		//only save detail changes, ignore changes to the order object
		SalesOrder oldPo = repo.findById(soRequest.getPk())
				.orElseThrow(() -> new ResourceNotFoundException());
		oldPo.setDetails(soRequest.getDetails());
		return repo.save(oldPo);
	}

}
