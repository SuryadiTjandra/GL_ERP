package ags.goldenlionerp.application.sales.salesorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.purchase.IntegratedReferences;
import ags.goldenlionerp.application.purchase.OrderStatus;
import ags.goldenlionerp.application.purchase.References;
import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T4201")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="SHUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="SHDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="SHTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="SHUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="SHDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="SHTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="SHCID")),
})
public class SalesOrder extends DatabaseEntity<SalesOrderPK>{

	@EmbeddedId @JsonUnwrapped
	private SalesOrderPK pk;
	
	@Column(name="SHBUID")
	private String businessUnitId;
	
	@Column(name="SHCSID")
	private String customerId;
	
	@Column(name="SHSTID")
	private String receiverId;
	
	@Column(name="SHEXID")
	private String expeditionId;
	
	@Column(name="SHPYID")
	private String payerId;
	
	@Column(name="SHSLID")
	private String salesmanId;
	
	@Column(name="SHCRCB")
	private String baseCurrency;
	
	@Column(name="SHCRCT")
	private String transactionCurrency;
	
	@Column(name="SHEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="SHORDT")
	private LocalDate orderDate;
	
	@Column(name="SHRQDT")
	private LocalDate requestDate;
	
	@Column(name="SHPDDT")
	private LocalDate promisedDeliveryDate;
	
	@Column(name="SHDLDT")
	private LocalDate deliveryDate;
	
	@Column(name="SHCLDT")
	private LocalDate closedDate;

	@Column(name="SHGLDT")
	private LocalDate glDate;
	
	@Column(name="SHETD")
	private LocalDate estimatedTimeOfDeparture;
	
	@Column(name="SHETA")
	private LocalDate estimatedTimeOfArrival;
	
	@Column(name="SHATD")
	private LocalDate actualTimeOfDeparture;
	
	@Column(name="SHATA")
	private LocalDate actualTimeOfArrival;
	
	@Column(name="SHDOL")
	private LocalDate dateOfLoading;
	
	@Column(name="SHDOU")
	private LocalDate dateOfUnloading;
	
	@Column(name="SHDOD")
	private LocalDate dateOfDocking;
	
	@Column(name="SHDESB1")
	private String description;
	
	@Column(name="SHPTC")
	private String paymentTermCode;
	
	@Column(name="SHTAXCD")
	private String taxCode;
	
	@Column(name="SHTAXAL")
	private Boolean taxAllowance;
	
	@Column(name="SHTAXRT", precision=19, scale=15)
	private BigDecimal taxRate;
	
	@Column(name="SHGSCRT", precision=19, scale=15)
	private BigDecimal guestServiceChargeRate;
	
	@Column(name="SHDCCD")
	private String discountCode;
	
	@Column(name="SHDCRT", precision=19, scale=15)
	private BigDecimal discountRate;
	
	@Column(name="SHLST")
	private String lastStatus;
	
	@Column(name="SHNST")
	private String nextStatus;
	
	@Column(name="SHCSDOCNO")
	private String customerOrderNumber;
	
	@Column(name="SHCSDOCDT")
	private LocalDate customerOrderDate;
	
	@Column(name="SHORDOCNO")
	private int originalOrderNumber;
	
	@Column(name="SHORDOCDT")
	private LocalDate originalOrderDate;
	
	@Column(name="SHPJID")
	private String projectId;
	
	@Column(name="SHTAGID")
	private String tagId;
	
	@Column(name="SHRBUID")
	private String profitCenterId;
	
	@Column(name="SHHCOD")
	private String holdCode;
	
	@Column(name="SHPOD")
	private String portOfDepartureId;
	
	@Column(name="SHPOA")
	private String portOfArrivalId;
	
	@Column(name="SHFHC")
	private String freightHandlingCode;
	
	@Column(name="SHCOT")
	private String conditionOfTransport;
	
	@Column(name="SHSHCN")
	private String shipmentCondition;
	
	@Column(name="SHSHMT")
	private String shippingMethod;
	
	@Column(name="SHEDN")
	private String exportDeclarationNumber;
	
	@Column(name="SHEDD")
	private LocalDate exportDeclarationDate;
	
	@Column(name="SHFBN")
	private String freightBillingNumber;
	
	@Column(name="SHFBD")
	private LocalDate freightBillingDate;
	
	@Column(name="SHEAN")
	private String exportApprovalNumber;
	
	@Column(name="SHEAD")
	private LocalDate exportApprovalDate;
	
	@Column(name="SHPRID")
	private String partnerRepresentativeId;
	
	@Column(name="SHURDT1")
	private LocalDate userReservedDate1;
	
	@Column(name="SHURDT2")
	private LocalDate userReservedDate2;
	
	@Column(name="SHOBID")
	private String objectId;
	
	@OneToMany(mappedBy="order", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<SalesDetail> details;
	
	@SuppressWarnings("unused")
	private SalesOrder() {
		this.details = new ArrayList<>();
	}
	
	public SalesOrder(List<SalesDetail> details) {
		if (details == null) details = new ArrayList<>();
		this.details = details;
	}
	
	@Override
	public SalesOrderPK getId() {
		return getPk();
	}

	public SalesOrderPK getPk() {
		return pk;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String getExpeditionId() {
		return expeditionId;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getSalesmanId() {
		return salesmanId;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public LocalDate getPromisedDeliveryDate() {
		return promisedDeliveryDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public LocalDate getGlDate() {
		return glDate;
	}

	public LocalDate getEstimatedTimeOfDeparture() {
		return estimatedTimeOfDeparture;
	}

	public LocalDate getEstimatedTimeOfArrival() {
		return estimatedTimeOfArrival;
	}

	public LocalDate getActualTimeOfDeparture() {
		return actualTimeOfDeparture;
	}

	public LocalDate getActualTimeOfArrival() {
		return actualTimeOfArrival;
	}

	public LocalDate getDateOfLoading() {
		return dateOfLoading;
	}

	public LocalDate getDateOfUnloading() {
		return dateOfUnloading;
	}

	public LocalDate getDateOfDocking() {
		return dateOfDocking;
	}

	public String getDescription() {
		return description;
	}

	public String getPaymentTermCode() {
		return paymentTermCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public Boolean getTaxAllowance() {
		return taxAllowance;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public BigDecimal getGuestServiceChargeRate() {
		return guestServiceChargeRate;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public String getNextStatus() {
		return nextStatus;
	}

	public String getCustomerOrderNumber() {
		return customerOrderNumber;
	}

	public LocalDate getCustomerOrderDate() {
		return customerOrderDate;
	}

	public int getOriginalOrderNumber() {
		return originalOrderNumber;
	}

	public LocalDate getOriginalOrderDate() {
		return originalOrderDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getTagId() {
		return tagId;
	}

	public String getProfitCenterId() {
		return profitCenterId;
	}

	public String getHoldCode() {
		return holdCode;
	}

	public String getPortOfDepartureId() {
		return portOfDepartureId;
	}

	public String getPortOfArrivalId() {
		return portOfArrivalId;
	}

	public String getFreightHandlingCode() {
		return freightHandlingCode;
	}

	public String getConditionOfTransport() {
		return conditionOfTransport;
	}

	public String getShipmentCondition() {
		return shipmentCondition;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public String getExportDeclarationNumber() {
		return exportDeclarationNumber;
	}

	public LocalDate getExportDeclarationDate() {
		return exportDeclarationDate;
	}

	public String getFreightBillingNumber() {
		return freightBillingNumber;
	}

	public LocalDate getFreightBillingDate() {
		return freightBillingDate;
	}

	public String getExportApprovalNumber() {
		return exportApprovalNumber;
	}

	public LocalDate getExportApprovalDate() {
		return exportApprovalDate;
	}

	public String getPartnerRepresentativeId() {
		return partnerRepresentativeId;
	}

	public LocalDate getUserReservedDate1() {
		return userReservedDate1;
	}

	public LocalDate getUserReservedDate2() {
		return userReservedDate2;
	}

	public String getObjectId() {
		return objectId;
	}

	public List<SalesDetail> getDetails() {
		return details;
	}

	void setPk(SalesOrderPK pk) {
		this.pk = pk;
		for (int i = 0; i < this.details.size(); i++) {
			SalesDetailPK detPk = new SalesDetailPK(
					pk.getCompanyId(), 
					pk.getSalesOrderNumber(),
					pk.getSalesOrderType(), 
					(i+1) * 10
				);
			this.details.get(i).setPk(detPk);
		}
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
		this.details.forEach(det -> det.setBusinessUnitId(businessUnitId));
	}

	void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
		this.details.forEach(det -> det.setReceiverId(receiverId));
	}

	void setCustomerId(String customerId) {
		this.customerId = customerId;
		this.details.forEach(det -> det.setCustomerId(customerId));
	}
	
	void setExpeditionId(String expeditionId) {
		this.expeditionId = expeditionId;
		this.details.forEach(det -> det.setExpeditionId(expeditionId));
	}
	
	void setPayerId(String payerId) {
		this.payerId = payerId;
		this.details.forEach(det -> det.setPayerId(payerId));
	}
	
	void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
		this.details.forEach(det -> det.setSalesmanId(salesmanId));
	}

	void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
		this.details.forEach(det -> det.setBaseCurrency(baseCurrency));
	}

	void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
		this.details.forEach(det -> det.setTransactionCurrency(transactionCurrency));
	}

	void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
		this.details.forEach(det -> det.setExchangeRate(exchangeRate));
	}

	void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
		this.details.forEach(det -> det.setOrderDate(orderDate));
	}

	void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
		this.details.forEach(det -> det.setRequestDate(requestDate));
	}

	void setPromisedDeliveryDate(LocalDate promisedDeliveryDate) {
		this.promisedDeliveryDate = promisedDeliveryDate;
		this.details.forEach(det -> det.setPromisedDeliveryDate(promisedDeliveryDate));
	}

	void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
		this.details.forEach(det-> det.setDeliveryDate(deliveryDate));
	}

	void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
		this.details.forEach(det -> det.setClosedDate(closedDate));
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
		this.details.forEach(det -> det.setGlDate(glDate));
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
		this.details.forEach(det -> det.setPaymentTermCode(paymentTermCode));
	}

	void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
		this.details.forEach(det -> det.setTaxCode(taxCode));
	}

	void setTaxAllowance(boolean taxAllowance) {
		this.taxAllowance = taxAllowance;
		this.details.forEach(det -> det.setTaxAllowance(taxAllowance));
	}

	void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
		this.details.forEach(det -> det.setTaxRate(taxRate));
	}

	void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
		this.details.forEach(det -> det.setDiscountCode(discountCode));
	}

	void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
		this.details.forEach(det -> det.setDiscountRate(discountRate));
	}

	void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
		this.details.forEach(det -> det.setLastStatus(lastStatus));
	}

	void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
		this.details.forEach(det -> det.setNextStatus(nextStatus));
	}

	void setProjectId(String projectId) {
		this.projectId = projectId;
		this.details.forEach(det -> det.setProjectId(projectId));
	}

	void setExportDeclarationNumber(String exportDeclarationNumber) {
		this.exportDeclarationNumber = exportDeclarationNumber;
	}

	void setExportDeclarationDate(LocalDate exportDeclarationDate) {
		this.exportDeclarationDate = exportDeclarationDate;
	}

	void setConditionOfTransport(String conditionOfTransport) {
		this.conditionOfTransport = conditionOfTransport;
	}

	void setPortOfDepartureId(String portOfDepartureId) {
		this.portOfDepartureId = portOfDepartureId;
	}

	void setPortOfArrivalId(String portOfArrivalId) {
		this.portOfArrivalId = portOfArrivalId;
	}

	void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.details.forEach(det -> det.setVehicleRegistrationNumber(vehicleRegistrationNumber));
	}

	void setVehicleType(String vehicleType) {
		this.details.forEach(det -> det.setVehicleType(vehicleType));
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
		this.details.forEach(det -> det.setObjectId(objectId));
	}

	void setDetails(List<SalesDetail> details) {
		this.details = details;
		syncDetails();
	}

	void setEstimatedTimeOfDeparture(LocalDate estimatedTimeOfDeparture) {
		this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
	}

	void setEstimatedTimeOfArrival(LocalDate estimatedTimeOfArrival) {
		this.estimatedTimeOfArrival = estimatedTimeOfArrival;
	}

	void setActualTimeOfDeparture(LocalDate actualTimeOfDeparture) {
		this.actualTimeOfDeparture = actualTimeOfDeparture;
	}

	void setActualTimeOfArrival(LocalDate actualTimeOfArrival) {
		this.actualTimeOfArrival = actualTimeOfArrival;
	}

	void setDateOfLoading(LocalDate dateOfLoading) {
		this.dateOfLoading = dateOfLoading;
	}

	void setDateOfUnloading(LocalDate dateOfUnloading) {
		this.dateOfUnloading = dateOfUnloading;
	}

	void setDateOfDocking(LocalDate dateOfDocking) {
		this.dateOfDocking = dateOfDocking;
	}

	void setGuestServiceChargeRate(BigDecimal guestServiceChargeRate) {
		this.guestServiceChargeRate = guestServiceChargeRate;
		this.details.forEach(det -> det.setGuestServiceChargeRate(guestServiceChargeRate));
	}

	void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}

	void setCustomerOrderDate(LocalDate customerOrderDate) {
		this.customerOrderDate = customerOrderDate;
	}

	void setOriginalOrderNumber(int originalOrderNumber) {
		this.originalOrderNumber = originalOrderNumber;
		this.details.forEach(det -> det.setOriginalDocumentNumber(originalOrderNumber));
	}

	void setOriginalOrderDate(LocalDate originalOrderDate) {
		this.originalOrderDate = originalOrderDate;
		this.details.forEach(det -> det.setOriginalDocumentDate(originalOrderDate));
	}

	void setTagId(String tagId) {
		this.tagId = tagId;
	}

	void setProfitCenterId(String profitCenterId) {
		this.profitCenterId = profitCenterId;
		this.details.forEach(det -> det.setProfitCenterId(profitCenterId));
	}

	void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
		this.details.forEach(det -> det.setHoldCode(holdCode));
	}

	void setFreightHandlingCode(String freightHandlingCode) {
		this.freightHandlingCode = freightHandlingCode;
	}

	void setShipmentCondition(String shipmentCodition) {
		this.shipmentCondition = shipmentCodition;
	}

	void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	void setFreightBillingNumber(String freightBillingNumber) {
		this.freightBillingNumber = freightBillingNumber;
	}

	void setFreightBillingDate(LocalDate freightBillingDate) {
		this.freightBillingDate = freightBillingDate;
	}

	void setExportApprovalNumber(String exportApprovalNumber) {
		this.exportApprovalNumber = exportApprovalNumber;
	}

	void setExportApprovalDate(LocalDate exportApprovalDate) {
		this.exportApprovalDate = exportApprovalDate;
	}

	void setPartnerRepresentativeId(String partnerRepresentativeId) {
		this.partnerRepresentativeId = partnerRepresentativeId;
		this.details.forEach(det -> det.setPartnerRepresentativeId(partnerRepresentativeId));
	}

	void setUserReservedDate1(LocalDate userReservedDate1) {
		this.userReservedDate1 = userReservedDate1;
	}

	void setUserReservedDate2(LocalDate userReservedDate2) {
		this.userReservedDate2 = userReservedDate2;
	}
	
	@JsonUnwrapped
	public References getReferences() {
		if (this.details.isEmpty()) return References.builder().build();
		return this.details.get(0).getReferences();
	}
	
	@JsonUnwrapped
	public IntegratedReferences getIntegratedReferences() {
		if (this.details.isEmpty()) return IntegratedReferences.builder().build();
		return this.details.get(0).getIntegratedReferences();
	}
	
	public BigDecimal getGrossPrice() {
		return this.details.stream()
				.map(SalesDetail::getExtendedPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public BigDecimal getTotalUnitDiscountAmount() {
		return this.details.stream()
				.map(SalesDetail::getUnitDiscountAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public BigDecimal getPriceAfterUnitDiscount() {
		return this.getGrossPrice().subtract(this.getTotalUnitDiscountAmount());
	}
	
	public void syncDetails() {
		//setPk(pk);
		setBusinessUnitId(businessUnitId);
		setReceiverId(receiverId);
		setCustomerId(customerId);
		setExpeditionId(expeditionId);
		setPayerId(payerId);
		setSalesmanId(salesmanId);
		setBaseCurrency(baseCurrency);
		setTransactionCurrency(transactionCurrency);
		setExchangeRate(exchangeRate);
		setOrderDate(orderDate);
		setRequestDate(requestDate);
		setPromisedDeliveryDate(promisedDeliveryDate);
		setDeliveryDate(deliveryDate);
		setClosedDate(closedDate);
		setGlDate(glDate);
		setPaymentTermCode(paymentTermCode);
		setTaxCode(taxCode);
		setTaxAllowance(taxAllowance);
		setTaxRate(taxRate);
		setDiscountCode(discountCode);
		setDiscountRate(discountRate);
		setLastStatus(lastStatus);
		setNextStatus(nextStatus);
		setProjectId(projectId);
		//setVehicleRegistrationNumber(vehicleRegistrationNumber);
		//setVehicleType(vehicleType);
		setObjectId(objectId);
		setGuestServiceChargeRate(guestServiceChargeRate);
		setOriginalOrderNumber(originalOrderNumber);
		setOriginalOrderDate(originalOrderDate);
		setProfitCenterId(profitCenterId);
		setHoldCode(holdCode);
		setPartnerRepresentativeId(partnerRepresentativeId);
	}
	
	public OrderStatus getStatus() {
		//TODO
		if (details.stream().map(det -> det.getStatus()).allMatch(st -> st.equals(OrderStatus.CANCELLED)))
			return OrderStatus.CANCELLED;
		else
			return OrderStatus.OPEN;
	}
}
