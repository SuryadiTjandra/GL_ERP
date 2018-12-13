package ags.goldenlionerp.application.sales.salesshipment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import static org.springframework.util.ObjectUtils.isEmpty;

import ags.goldenlionerp.application.item.uomconversion.UomConversionService;
import ags.goldenlionerp.application.sales.salesorder.SalesDetail;
import ags.goldenlionerp.application.sales.salesorder.SalesOrderPK;
import ags.goldenlionerp.application.sales.salesorder.SalesOrderRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.documents.ItemTransactionObserver;
import ags.goldenlionerp.documents.ItemTransactionService;

@Service
public class SalesShipmentService implements ItemTransactionService{

	@Autowired
	private SalesShipmentRepository repo;
	@Autowired private NextNumberService nnServ;
	@Autowired private SalesOrderRepository orderRepo;
	@Autowired private UomConversionService uomServ;

	private Collection<ItemTransactionObserver> observers = new HashSet<>();
	@Override
	public void registerObserver(ItemTransactionObserver observer) {
		observers.add(observer);
	}
	
	@Transactional
	public SalesShipmentHeader createSalesShipments(SalesShipmentHeader shipmentRequest) {
		shipmentRequest = setDocumentAndBatchNumber(shipmentRequest);
		shipmentRequest = completeShipmentInfo(shipmentRequest);
		
		handleCreation(shipmentRequest);
		return new SalesShipmentHeader(
				Lists.newArrayList(repo.saveAll(shipmentRequest.getDetails()))
		);
	}

	private SalesShipmentHeader setDocumentAndBatchNumber(SalesShipmentHeader shipmentRequest) {
		String companyId = shipmentRequest.getCompanyId();
		String docTy = "SI";
		LocalDate docDt = Optional.ofNullable(shipmentRequest.getDocumentDate()).orElse(LocalDate.now());
		int docNo = shipmentRequest.getDocumentNumber() != 0 ?
						shipmentRequest.getDocumentNumber() :
						nnServ.findNextDocumentNumber(companyId, docTy, YearMonth.from(docDt));
		
		for (int i = 0; i < shipmentRequest.getDetails().size(); i++) {
			int seq = (i + 1 ) * 10;
			SalesShipmentPK pk = new SalesShipmentPK(companyId, docNo, docTy, seq);
			shipmentRequest.getDetails().get(i).setPk(pk);
		}
		
		String batchType = "S";
		int batchNo = nnServ.findNextNumber(companyId, batchType, YearMonth.from(docDt)).getNextSequence();
		
		shipmentRequest.setBatchNumber(batchNo);
		shipmentRequest.setBatchType(batchType);
						
		return shipmentRequest;
	}
	
	private SalesShipmentHeader completeShipmentInfo(SalesShipmentHeader shipmentRequest) {
		shipmentRequest.getDetails().forEach(det -> setInfoFromSalesDetail(det));
		return shipmentRequest;
	}

	private Object setInfoFromSalesDetail(SalesShipment shipment) {
		SalesOrderPK pk = new SalesOrderPK(shipment.getPk().getCompanyId(), shipment.getOrderNumber(), shipment.getOrderType());
		
		SalesDetail sd = orderRepo.findById(pk)
								.orElseThrow(() -> new ResourceNotFoundException())
								.getDetails().stream()
								.filter(d -> d.getPk().getSequence() == shipment.getOrderSequence())
								.findFirst().orElseThrow(() -> new ResourceNotFoundException());
		
		if (!shipment.getCustomerId().equals(sd.getCustomerId()))
			throw new SalesShipmentException("The vendor of the receipt does not match the vendor of the order");
		
		
		shipment.setItemCode(sd.getItemCode());
		shipment.setItemDescription(sd.getItemDescription());
		shipment.setLineType(sd.getLineType());
		if (isEmpty(shipment.getLocationId()))
			shipment.setLocationId(sd.getLocationId());
		if (isEmpty(shipment.getSerialLotNo()))
			shipment.setSerialLotNo(sd.getSerialLotNo());
		
		shipment.setBaseCurrency(sd.getBaseCurrency());
		shipment.setTransactionCurrency(sd.getTransactionCurrency());
		shipment.setExchangeRate(sd.getExchangeRate());
		
		if (shipment.getTransactionQuantity() == null || shipment.getTransactionQuantity().compareTo(BigDecimal.ZERO) == 0)
			throw new SalesShipmentException("Quantity must be greater than zero!");
		
		if (isEmpty(shipment.getUnitOfMeasure()))
			shipment.setUnitOfMeasure(sd.getUnitOfMeasure());
		shipment.setPrimaryUnitOfMeasure(sd.getPrimaryUnitOfMeasure());
		shipment.setSecondaryUnitOfMeasure(sd.getSecondaryUnitOfMeasure());
		
		setQuantityAndCosts(shipment, sd);
		
		shipment.setTaxCode(sd.getTaxCode());
		shipment.setTaxAllowance(sd.getTaxAllowance());
		shipment.setTaxRate(sd.getTaxRate());
		
		shipment.setGuestServiceChargeRate(sd.getGuestServiceChargeRate());
		shipment.setProfitCenterId(sd.getProfitCenterId());
		shipment.setPaymentTermCode(sd.getPaymentTermCode());
		shipment.setGlClass(sd.getGlClass());
		
		shipment.setDiscountCode(sd.getDiscountCode());
		shipment.setDiscountRate(sd.getDiscountRate());
		shipment.setUnitDiscountCode(sd.getUnitDiscountCode());
		shipment.setUnitDiscountRate(sd.getUnitDiscountRate());
		
		shipment.setInvoiceNumber(0);
		shipment.setInvoiceType("");
		shipment.setInvoiceSequence(0);
		shipment.setInvoiceDate(null);
		
		//TODO
		shipment.setLastStatus("560");
		shipment.setNextStatus("580");
		
		shipment.setSerialOrLotNumbers(shipment.getSerialOrLotNumbers());
		return shipment;
	}

	private void setQuantityAndCosts(SalesShipment shipment, SalesDetail sd) {
		if (shipment.getTransactionQuantity() == null || shipment.getTransactionQuantity().stripTrailingZeros().equals(BigDecimal.ZERO))
			throw new SalesShipmentException("Quantity must be larger than 0");
		
		BigDecimal ucf = uomServ.findConversionValue(shipment.getItemCode(), shipment.getUnitOfMeasure(), shipment.getPrimaryUnitOfMeasure());
		BigDecimal primaryQty = shipment.getTransactionQuantity().multiply(ucf);
		
		if (primaryQty.compareTo(sd.getPrimaryOrderQuantity()) > 0)
			throw new SalesShipmentException("Quantity cannot be larger than order quantity");
		
		shipment.setUnitConversionFactor(ucf);
		shipment.setPrimaryTransactionQuantity(primaryQty);
		
		
		BigDecimal shipPerOrder = primaryQty.setScale(5).divide(sd.getPrimaryOrderQuantity(), RoundingMode.HALF_UP);
		shipment.setUnitCost(sd.getUnitCost());
		shipment.setExtendedCost(sd.getExtendedCost().multiply(shipPerOrder));
		shipment.setUnitPrice(sd.getUnitPrice());
		shipment.setExtendedPrice(sd.getExtendedPrice().multiply(shipPerOrder));
		shipment.setTaxAmount(sd.getTaxAmount().multiply(shipPerOrder));
	}
	
	private void handleCreation(SalesShipmentHeader shipmentRequest) {
		for (SalesShipment ship: shipmentRequest.getDetails()) {
			observers.forEach(observer -> observer.handleCreation(ship));
		}
		
	}

	@Transactional
	public SalesShipmentHeader updateSalesShipment(SalesShipmentPK pk, SalesShipmentHeader patchRequest) {
		voidShipments(patchRequest);
		
		SalesShipmentPredicates pred = SalesShipmentPredicates.getInstance();
		List<SalesShipment> existingShipments = Lists.newArrayList(repo.findAll(pred.sameHeaderAs(pk)));
		List<SalesShipment> newShipments = patchRequest.getDetails();
		
		newShipments.removeIf(ship -> existingShipments.stream()
										.anyMatch(exShip -> exShip.getPk().equals(ship.getPk()))
							);
		if (newShipments.isEmpty())
			return new SalesShipmentHeader(existingShipments);
		
		int maxSeq = Math.max(
				existingShipments.stream().mapToInt(sh -> sh.getPk().getSequence()).max().orElse(0), 
				newShipments.stream().mapToInt(sh -> sh.getPk().getSequence()).max().orElse(0)
			);
		for (int i = 0; i < newShipments.size(); i++) {
			SalesShipmentPK oldPk = newShipments.get(i).getPk();
			int seq = 10 * (maxSeq/10 + i + 1);
			SalesShipmentPK newPk = new SalesShipmentPK(oldPk.getCompanyId(), oldPk.getDocumentNumber(), oldPk.getDocumentType(), seq);
			newShipments.get(i).setPk(newPk);
		}
		
		
		SalesShipmentHeader existingHead = new SalesShipmentHeader(existingShipments);
		SalesShipmentHeader newHead = new SalesShipmentHeader(
				existingHead.getCompanyId(),
				existingHead.getDocumentNumber(),
				existingHead.getDocumentType(),
				existingHead.getBusinessUnitId(),
				existingHead.getDocumentDate(),
				existingHead.getCustomerId(),
				existingHead.getReceiverId(),
				existingHead.getDescription(),
				newShipments
			);
		newHead.setBatchNumber(existingHead.getBatchNumber());
		newHead.setBatchType(existingHead.getBatchType());
		completeShipmentInfo(newHead);
		handleCreation(newHead);
		repo.saveAll(newHead.getDetails());
		return new SalesShipmentHeader(
				Lists.newArrayList(repo.findAll(pred.sameHeaderAs(pk)))
		);
	}

	private void voidShipments(SalesShipmentHeader patchRequest) {
		List<SalesShipmentPK> toBeVoidedPks = patchRequest.getDetails().stream()
											.filter(sh -> sh.setForVoid)
											.map(SalesShipment::getPk)
											.collect(Collectors.toList());
		if (toBeVoidedPks.isEmpty())
			return;
		
		List<SalesShipment> toBeVoideds = Lists.newArrayList(repo.findAllById(toBeVoidedPks));
		toBeVoideds.forEach(sh -> {
			sh.setLastStatus("980");
			sh.setNextStatus("999");
			observers.forEach(obs -> obs.handleVoidation(sh));
		});
		repo.saveAll(toBeVoideds);
		
		List<SalesShipment> voideds = toBeVoideds.stream()
										.map(sh -> {
											SalesShipmentPK pk = new SalesShipmentPK(
													sh.getPk().getCompanyId(),
													sh.getPk().getDocumentNumber(),
													sh.getPk().getDocumentType(),
													sh.getPk().getSequence() + 1);
											SalesShipment sh2 = new SalesShipment();
											sh2.setPk(pk);
											sh2.setOrderNumber(sh.getOrderNumber());
											sh2.setOrderType(sh.getOrderType());
											sh2.setOrderSequence(sh.getOrderSequence());
											sh2.setSerialOrLotNumbers(sh.getSerialOrLotNumbers());
											sh2.setTransactionQuantity(sh.getTransactionQuantity().negate());
											sh2.setPrimaryTransactionQuantity(sh.getPrimaryTransactionQuantity().negate());
											sh2.setExtendedCost(sh.getExtendedCost().negate());
											sh2.setExtendedPrice(sh.getExtendedPrice().negate());
											return sh2;
										})
										.collect(Collectors.toList());
		
		SalesShipment sample = toBeVoideds.get(0);
		SalesShipmentHeader voidhead = new SalesShipmentHeader(
				sample.getPk().getCompanyId(),
				sample.getPk().getDocumentNumber(),
				sample.getPk().getDocumentType(),
				sample.getBusinessUnitId(),
				sample.getTransactionDate(),
				sample.getCustomerId(),
				sample.getReceiverId(),
				sample.getDescription(),
				voideds);
		completeShipmentInfo(voidhead);
		voidhead.getDetails().forEach(sh -> {
			sh.setLastStatus("980");
			sh.setNextStatus("999");
		});
		
		repo.saveAll(voideds);
		
	}

	public Object getDefaultSalesShipment(String appParamCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", "11000");
		map.put("documentType", "SI");
		map.put("businessUnitId", "110");
		map.put("nextStatus", "580");
		map.put("lastStatus", "560");
		map.put("transactionDate", LocalDate.now());
		map.put("details", Collections.emptyList());
		return map;
	}
}
