package ags.goldenlionerp.application.purchase.purchaseorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

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
	
	@Column(name="OHBUID")
	private String businessUnitId;
	
	@Column(name="OHVNID")
	private String vendorId;
	
	@Column(name="OHSTID")
	private String receiverId;
	
	@Column(name="OHCSID")
	private String customerId;
	
	@Column(name="OHCRCB")
	private String baseCurrency;
	
	@Column(name="OHCRCT")
	private String transactionCurrency;
	
	@Column(name="OHEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="OHORDT")
	private LocalDate orderDate;
	
	@Column(name="OHRQDT")
	private LocalDate requestDate;
	
	@Column(name="OHPDDT")
	private LocalDate promisedDeliveryDate;
	
	@Column(name="OHRCDT")
	private LocalDate receiptDate;
	
	@Column(name="OHCLDT")
	private LocalDate closedDate;
	
	@Column(name="OHGLDT")
	private LocalDate glDate;
	
	@Column(name="OHDESB1")
	private String description;
	
	@Column(name="OHPTC")
	private String paymentTermCode;
	
	@Column(name="OHTAXCD")
	private String taxCode;
	
	@Column(name="OHTAXAL")
	private Boolean taxAllowance;
	
	@Column(name="OHTAXRT", precision=19, scale=15)
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
	private String description1;
	
	@Column(name="OHDESC2")
	private String description2;
	
	@Column(name="OHOBID")
	private String objectId;
	
	@OneToMany(mappedBy="order")
	private List<PurchaseDetail> details;
	
	@Override
	public PurchaseOrderPK getId() {
		// TODO Auto-generated method stub
		return null;
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

	public String getDescription1() {
		return description1;
	}

	public String getDescription2() {
		return description2;
	}

	public String getObjectId() {
		return objectId;
	}

	public List<PurchaseDetail> getDetails() {
		return details;
	}

	void setPk(PurchaseOrderPK pk) {
		this.pk = pk;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	void setReceiptDate(LocalDate receiptDate) {
		this.receiptDate = receiptDate;
	}

	void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
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

	void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
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

	void setDescription1(String description1) {
		this.description1 = description1;
	}

	void setDescription2(String description2) {
		this.description2 = description2;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setDetails(List<PurchaseDetail> details) {
		this.details = details;
	}

}
