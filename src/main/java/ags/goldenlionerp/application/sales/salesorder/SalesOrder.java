package ags.goldenlionerp.application.sales.salesorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	private BigDecimal exchangeRate;
	
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
	private LocalDateTime estimatedTimeOfDeparture;
	
	@Column(name="SHETA")
	private LocalDateTime estimatedTimeOfArrival;
	
	@Column(name="SHATD")
	private LocalDateTime actualTimeOfDeparture;
	
	@Column(name="SHATA")
	private LocalDateTime actualTimeOfArrival;
	
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
	private int customerOrderNumber;
	
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
	private String shipmentCodition;
	
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

	public LocalDateTime getEstimatedTimeOfDeparture() {
		return estimatedTimeOfDeparture;
	}

	public LocalDateTime getEstimatedTimeOfArrival() {
		return estimatedTimeOfArrival;
	}

	public LocalDateTime getActualTimeOfDeparture() {
		return actualTimeOfDeparture;
	}

	public LocalDateTime getActualTimeOfArrival() {
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

	public int getCustomerOrderNumber() {
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

	public String getShipmentCodition() {
		return shipmentCodition;
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
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	void setExpeditionId(String expeditionId) {
		this.expeditionId = expeditionId;
	}

	void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	void setPromisedDeliveryDate(LocalDate promisedDeliveryDate) {
		this.promisedDeliveryDate = promisedDeliveryDate;
	}

	void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
	}

	void setEstimatedTimeOfDeparture(LocalDateTime estimatedTimeOfDeparture) {
		this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
	}

	void setEstimatedTimeOfArrival(LocalDateTime estimatedTimeOfArrival) {
		this.estimatedTimeOfArrival = estimatedTimeOfArrival;
	}

	void setActualTimeOfDeparture(LocalDateTime actualTimeOfDeparture) {
		this.actualTimeOfDeparture = actualTimeOfDeparture;
	}

	void setActualTimeOfArrival(LocalDateTime actualTimeOfArrival) {
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

	void setDescription(String description) {
		this.description = description;
	}

	void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
	}

	void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	void setTaxAllowance(Boolean taxAllowance) {
		this.taxAllowance = taxAllowance;
	}

	void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	void setGuestServiceChargeRate(BigDecimal guestServiceChargeRate) {
		this.guestServiceChargeRate = guestServiceChargeRate;
	}

	void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}

	void setCustomerOrderNumber(int customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}

	void setCustomerOrderDate(LocalDate customerOrderDate) {
		this.customerOrderDate = customerOrderDate;
	}

	void setOriginalOrderNumber(int originalOrderNumber) {
		this.originalOrderNumber = originalOrderNumber;
	}

	void setOriginalOrderDate(LocalDate originalOrderDate) {
		this.originalOrderDate = originalOrderDate;
	}

	void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	void setTagId(String tagId) {
		this.tagId = tagId;
	}

	void setProfitCenterId(String profitCenterId) {
		this.profitCenterId = profitCenterId;
	}

	void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
	}

	void setPortOfDepartureId(String portOfDepartureId) {
		this.portOfDepartureId = portOfDepartureId;
	}

	void setPortOfArrivalId(String portOfArrivalId) {
		this.portOfArrivalId = portOfArrivalId;
	}

	void setFreightHandlingCode(String freightHandlingCode) {
		this.freightHandlingCode = freightHandlingCode;
	}

	void setConditionOfTransport(String conditionOfTransport) {
		this.conditionOfTransport = conditionOfTransport;
	}

	void setShipmentCodition(String shipmentCodition) {
		this.shipmentCodition = shipmentCodition;
	}

	void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	void setExportDeclarationNumber(String exportDeclarationNumber) {
		this.exportDeclarationNumber = exportDeclarationNumber;
	}

	void setExportDeclarationDate(LocalDate exportDeclarationDate) {
		this.exportDeclarationDate = exportDeclarationDate;
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
	}

	void setUserReservedDate1(LocalDate userReservedDate1) {
		this.userReservedDate1 = userReservedDate1;
	}

	void setUserReservedDate2(LocalDate userReservedDate2) {
		this.userReservedDate2 = userReservedDate2;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setDetails(List<SalesDetail> details) {
		this.details = details;
	}
	

	
	
	
}
