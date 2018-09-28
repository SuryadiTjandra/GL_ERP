package ags.goldenlionerp.application.purchase.purchaseorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.purchase.OrderStatus;
import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T4301")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="OHUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="OHDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="OHTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="OHUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="OHDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="OHTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="OHCID")),
})
public class PurchaseOrder extends DatabaseEntity<PurchaseOrderPK> {

	@EmbeddedId @JsonUnwrapped
	private PurchaseOrderPK pk;
	
	@Column(name="OHBUID", updatable = false)
	private String businessUnitId;
	
	@Column(name="OHVNID", updatable = false)
	private String vendorId;
	
	@Column(name="OHSTID", updatable = false)
	private String receiverId;
	
	@Column(name="OHCSID", updatable = false)
	private String customerId;
	
	@Column(name="OHCRCB", updatable = false)
	private String baseCurrency;
	
	@Column(name="OHCRCT", updatable = false)
	private String transactionCurrency;
	
	@Column(name="OHEXCRT", precision=19, scale=9, updatable=false)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="OHORDT", updatable=false)
	private LocalDate orderDate;
	
	@Column(name="OHRQDT")
	private LocalDate requestDate;
	
	@Column(name="OHPDDT", updatable = false)
	private LocalDate promisedDeliveryDate;
	
	@Column(name="OHRCDT")
	private LocalDate receiptDate;
	
	@Column(name="OHCLDT")
	private LocalDate closedDate;
	
	@Column(name="OHGLDT")
	private LocalDate glDate;
	
	@Column(name="OHDESB1")
	private String description;
	
	@Column(name="OHPTC", updatable =false)
	private String paymentTermCode;
	
	@Column(name="OHTAXCD", updatable = false)
	private String taxCode;
	
	@Column(name="OHTAXAL", updatable = false)
	private Boolean taxAllowance;
	
	@Column(name="OHTAXRT", precision=19, scale=15, updatable = false)
	private BigDecimal taxRate;
	
	@Column(name="OHDCCD")
	private String discountCode;
	
	@Column(name="OHDCRT", precision=19, scale=15)
	private BigDecimal discountRate;
	
	@Column(name="OHLST")
	private String lastStatus;
	
	@Column(name="OHNST")
	private String nextStatus;
	
	@Column(name="OHPJID")
	private String projectId;
	
	@Column(name="OHAPRCD")
	private String approvalCode;
	
	@Column(name="OHIDN")
	private String importDeclarationNumber;
	
	@Column(name="OHIDD")
	private LocalDate importDeclarationDate;
	
	@Column(name="OHCOT")
	private String conditionOfTransport;
	
	@Column(name="OHPOD")
	private String portOfDepartureId;
	
	@Column(name="OHPOA")
	private String portOfArrivalId;
	
	@Column(name="OHVHRN")
	private String vehicleRegistrationNumber;
	
	@Column(name="OHVHTY")
	private String vehicleType;
	
	@Column(name="OHDESC1")
	private String vehicleDescription;
	
	@Column(name="OHDESC2")
	private String vehicleDescription2;
	
	@Column(name="OHOBID")
	private String objectId;
	
	@OneToMany(mappedBy="order", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<PurchaseDetail> details;
	
	@SuppressWarnings("unused")
	private PurchaseOrder() {
		details = new ArrayList<>();
	}
	
	//@JsonCreator //this ensures a PurchaseOrder always have list of details
	public PurchaseOrder(/*@JsonProperty("details")*/ List<PurchaseDetail> details) {
		if (details == null) details = new ArrayList<>();
		this.details = details;
	}
	
	@Override
	public PurchaseOrderPK getId() {
		return getPk();
	}

	public PurchaseOrderPK getPk() {
		return pk;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String getCustomerId() {
		return customerId;
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

	public LocalDate getReceiptDate() {
		return receiptDate;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public LocalDate getGlDate() {
		return glDate;
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

	public String getProjectId() {
		return projectId;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public String getImportDeclarationNumber() {
		return importDeclarationNumber;
	}

	public LocalDate getImportDeclarationDate() {
		return importDeclarationDate;
	}

	public String getConditionOfTransport() {
		return conditionOfTransport;
	}

	public String getPortOfDepartureId() {
		return portOfDepartureId;
	}

	public String getPortOfArrivalId() {
		return portOfArrivalId;
	}

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public String getObjectId() {
		return objectId;
	}

	public List<PurchaseDetail> getDetails() {
		return details;
	}

	void setPk(PurchaseOrderPK pk) {
		this.pk = pk;
		for (int i = 0; i < this.details.size(); i++) {
			PurchaseDetailPK detPk = new PurchaseDetailPK(
					pk.getCompanyId(), 
					pk.getPurchaseOrderNumber(),
					pk.getPurchaseOrderType(), 
					(i+1) * 10
				);
			this.details.get(i).setPk(detPk);
		}
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
		this.details.forEach(det -> det.setBusinessUnitId(businessUnitId));
	}

	void setVendorId(String vendorId) {
		this.vendorId = vendorId;
		this.details.forEach(det -> det.setVendorId(vendorId));
	}

	void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
		this.details.forEach(det -> det.setReceiverId(receiverId));
	}

	void setCustomerId(String customerId) {
		this.customerId = customerId;
		this.details.forEach(det -> det.setCustomerId(customerId));
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

	void setReceiptDate(LocalDate receiptDate) {
		this.receiptDate = receiptDate;
		this.details.forEach(det-> det.setReceiptDate(receiptDate));
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

	void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
		this.details.forEach(det -> det.setApprovalCode(approvalCode));
	}

	void setImportDeclarationNumber(String importDeclarationNumber) {
		this.importDeclarationNumber = importDeclarationNumber;
	}

	void setImportDeclarationDate(LocalDate importDeclarationDate) {
		this.importDeclarationDate = importDeclarationDate;
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
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
		this.details.forEach(det -> det.setObjectId(objectId));
	}
	
	public String getVehicleDescription() {
		return vehicleDescription;
	}

	public String getVehicleDescription2() {
		return vehicleDescription2;
	}

	void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}

	void setVehicleDescription2(String vehicleDescription2) {
		this.vehicleDescription2 = vehicleDescription2;
	}

	void setDetails(List<PurchaseDetail> details) {
		this.details = details;
		syncDetails();
	}
	
	public BigDecimal getGrossCost() {
		return this.details.stream()
				.map(PurchaseDetail::getExtendedCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public BigDecimal getTotalUnitDiscountAmount() {
		return this.details.stream()
				.map(PurchaseDetail::getUnitDiscountAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public BigDecimal getCostAfterUnitDiscount() {
		return this.getGrossCost().subtract(this.getTotalUnitDiscountAmount());
	}

	public OrderStatus getStatus() {
		//TODO
		if (details.stream().map(det -> det.getStatus()).allMatch(st -> st.equals(OrderStatus.CANCELLED)))
			return OrderStatus.CANCELLED;
		else
			return OrderStatus.OPEN;
	}
	
	void syncDetails() {
		setBusinessUnitId(businessUnitId);
		setVendorId(vendorId);
		setReceiverId(receiverId);
		setCustomerId(customerId);
		setTransactionCurrency(transactionCurrency);
		setBaseCurrency(baseCurrency);
		setExchangeRate(exchangeRate);
		setOrderDate(orderDate);
		setReceiptDate(receiptDate);
		setPromisedDeliveryDate(promisedDeliveryDate);
		setRequestDate(requestDate);
		setClosedDate(closedDate);
		setGlDate(glDate);
		setPaymentTermCode(paymentTermCode);
		setTaxCode(taxCode);
		setTaxAllowance(Optional.ofNullable(taxAllowance).orElse(false));
		setTaxRate(taxRate);
		setDiscountCode(discountCode);
		setDiscountRate(discountRate);
		setNextStatus(nextStatus);
		setLastStatus(lastStatus);
		setProjectId(projectId);
		setApprovalCode(approvalCode);
		setObjectId(objectId);
	}
}
