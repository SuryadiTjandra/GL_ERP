package ags.goldenlionerp.application.purchase.purchaseorder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.purchase.IntegratedReferences;
import ags.goldenlionerp.application.purchase.OrderStatus;
import ags.goldenlionerp.application.purchase.PurchaseOptions;
import ags.goldenlionerp.application.purchase.References;
import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.entities.Voidable;

@Entity
@Table(name="T4311")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="ODUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="ODDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="ODTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="ODUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="ODDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="ODTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="ODCID")),
})
public class PurchaseDetail extends DatabaseEntity<PurchaseDetailPK> implements Voidable{

	@EmbeddedId @JsonUnwrapped
	private PurchaseDetailPK pk;
	
	@Column(name="ODBUID")
	private String businessUnitId;
	
	@Column(name="ODVNID")
	private String vendorId;
	
	@Column(name="ODSTID")
	private String receiverId;
	
	@Column(name="ODCSID")
	private String customerId;
	
	@Column(name="ODINUM")
	private String itemCode;
	
	@Column(name="ODLOCID")
	private String locationId;
	
	@Column(name="ODSNLOT")
	private String serialLotNo;
	
	@Column(name="ODDESB1")
	private String description;
	
	@Column(name="ODLNTY")
	private String lineType;
	
	@Column(name="ODQTY", precision=19, scale=5)
	private BigDecimal quantity;
	
	@Column(name="ODQTYX", precision=19, scale=5)
	private BigDecimal extendedQuantity;
	
	@Column(name="ODQTYOR", precision=19, scale=5)
	private BigDecimal orderQuantity;
	
	@Column(name="ODQTYRC", precision=19, scale=5)
	private BigDecimal receivedQuantity;
	
	@Column(name="ODQTYCL", precision=19, scale=5)
	private BigDecimal cancelledQuantity;
	
	@Column(name="ODQTYRN", precision=19, scale=5)
	private BigDecimal returnedQuantity;
	
	@Column(name="ODQTYOP", precision=19, scale=5)
	private BigDecimal openQuantity;
	
	@Column(name="ODUOM")
	private String unitOfMeasure;
	
	@Column(name="ODUOMX")
	private String extendedUnitOfMeasure;
	
	@Column(name="ODUCF", precision=19, scale=9)
	private BigDecimal unitConversionFactor;
	
	@Column(name="ODUCFX", precision=19, scale=9)
	private BigDecimal extendedUnitConversionFactor;
	
	@Column(name="ODPQOR", precision=19, scale=5)
	private BigDecimal primaryOrderQuantity;
	
	@Column(name="ODUOM1")
	private String primaryUnitOfMeasure;
	
	@Column(name="ODSQOR", precision=19, scale=5)
	private BigDecimal secondaryOrderQuantity;
	
	@Column(name="ODUOM2")
	private String secondaryUnitOfMeasure;
	
	@Column(name="ODCRCB")
	private String baseCurrency;
	
	@Column(name="ODCRCT")
	private String transactionCurrency;

	@Column(name="ODEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="ODUNCOST", precision=19, scale=7)
	private BigDecimal unitCost;
	
	@Column(name="ODUNCOSTX", precision=19, scale=7)
	private BigDecimal extendedUnitCost;
	
	@Column(name="ODEXCOST", precision=19, scale=5)
	private BigDecimal extendedCost;
	
	@Column(name="ODTAXAB", precision=19, scale=5)
	private BigDecimal taxBase;
	
	@Column(name="ODTAXAM", precision=19, scale=5)
	private BigDecimal taxAmount;
	
	@Column(name="ODFUC", precision=19, scale=7)
	private BigDecimal foreignUnitCost;
	
	@Column(name="ODFEC", precision=19, scale=5)
	private BigDecimal foreignExtendedCost;

	@Column(name="ODFTB", precision=19, scale=5)
	private BigDecimal foreignTaxBase;
	
	@Column(name="ODFTA", precision=19, scale=5)
	private BigDecimal foreignTaxAmount;
	
	@Column(name="ODACID")
	private String accountId;
	
	@Column(name="ODGLCLS")
	private String glClass;
	
	@Column(name="ODLST")
	private String lastStatus;
	
	@Column(name="ODNST")
	private String nextStatus;
	
	@Column(name="ODORDT")
	private LocalDate orderDate;
	
	@Column(name="ODRQDT")
	private LocalDate requestDate;
	
	@Column(name="ODPDDT")
	private LocalDate promisedDeliveryDate;
	
	@Column(name="ODRCDT")
	private LocalDate receiptDate;
	
	@Column(name="ODCLDT")
	private LocalDate closedDate;
	
	@Column(name="ODGLDT")
	private LocalDate glDate;
	
	@Column(name="ODPTC")
	private String paymentTermCode;
	
	@Column(name="ODTAXCD")
	private String taxCode;
	
	@Column(name="ODTAXAL")
	private Boolean taxAllowance;
	
	@Column(name="ODTAXRT", precision=19, scale=15)
	private BigDecimal taxRate  = BigDecimal.ZERO;
	
	@Column(name="ODDCCD")
	private String discountCode;
	
	@Column(name="ODDCRT", precision=19, scale=15)
	private BigDecimal discountRate = BigDecimal.ZERO;
	
	@Column(name="ODUDC")
	private String unitDiscountCode;
	
	@Column(name="ODUDF", precision=19, scale=15)
	private BigDecimal unitDiscountRate  = BigDecimal.ZERO;
	
	@Column(name="ODPJID")
	private String projectId;
	
	@Column(name="ODAPRCD")
	private String approvalCode;
	
	@Column(name="ODOBID")
	private String objectId;
	
	@Column(name="ODORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="ODORDOCTY")
	private String originalDocumentType;
	
	@Column(name="ODORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="ODORDOCDT")
	private LocalDate originalDocumentDate;
	
	@Column(name="ODRLDOCNO")
	private int relatedDocumentNumber;
	
	@Column(name="ODRLDOCTY")
	private String relatedDocumentType;
	
	@Column(name="ODRLDOCSQ")
	private int relatedDocumentSequence;
	
	@Column(name="ODITC01")
	private String categoryCode;
	
	@Column(name="ODITC02")
	private String brandCode;
	
	@Column(name="ODITC03")
	private String typeCode;
	
	//@Column(name="ODITC04")
	//private String categoryCode4;
	
	@Column(name="ODITC05")
	private String landedCostRule;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="jobNo", column=@Column(name="ODVR01")),
		@AttributeOverride(name="shipmentId", column=@Column(name="ODVR02")),
		@AttributeOverride(name="packinglistId", column=@Column(name="ODVR03")),
		@AttributeOverride(name="deliveryOrderNumber", column=@Column(name="ODVR04")),
		@AttributeOverride(name="sealNumber", column=@Column(name="ODVR05"))
	})
	@JsonUnwrapped
	private References references = References.builder().build();
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="feederVessel", column=@Column(name="ODIR01")),
		@AttributeOverride(name="connectingVessel", column=@Column(name="ODIR02")),
		@AttributeOverride(name="shippedBy", column=@Column(name="ODIR03")),
		//@AttributeOverride(name="integratedReference4", column=@Column(name="ODIR04")),
		//@AttributeOverride(name="integratedReference5", column=@Column(name="ODIR05")),
	})
	@JsonUnwrapped
	private IntegratedReferences integratedReferences = IntegratedReferences.builder().build();
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="containerSize", column=@Column(name="ODPO01")),
		@AttributeOverride(name="stuffingType", column=@Column(name="ODPO02")),
		@AttributeOverride(name="containerLoadType", column=@Column(name="ODPO03")),
		//@AttributeOverride(name="purchaseOption4", column=@Column(name="ODPO04")),
		//@AttributeOverride(name="purchaseOption5", column=@Column(name="ODPO05")), etc
	})
	@JsonUnwrapped
	private PurchaseOptions purchaseOptions = PurchaseOptions.builder().build();
	
	@Column(name="ODOTT")
	private String orderTransactionType;
	
	@JoinColumns({
		@JoinColumn(name="ODCOID", referencedColumnName="OHCOID", updatable=false, insertable=false),
		@JoinColumn(name="ODDOCONO", referencedColumnName="OHDOCONO", updatable=false, insertable=false),
		@JoinColumn(name="ODDOCOTY", referencedColumnName="OHDOCOTY", updatable=false, insertable=false)
	})
	@ManyToOne(optional=false)
	private PurchaseOrder order;
	
	@Override
	public PurchaseDetailPK getId() {
		return getPk();
	}

	public PurchaseDetailPK getPk() {
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

	public String getItemCode() {
		return itemCode;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getSerialLotNo() {
		return serialLotNo;
	}

	public String getDescription() {
		return description;
	}

	public String getLineType() {
		return lineType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getExtendedQuantity() {
		return extendedQuantity;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public BigDecimal getReceivedQuantity() {
		return receivedQuantity;
	}

	public BigDecimal getCancelledQuantity() {
		return cancelledQuantity;
	}

	public BigDecimal getReturnedQuantity() {
		return returnedQuantity;
	}

	public BigDecimal getOpenQuantity() {
		return openQuantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public String getExtendedUnitOfMeasure() {
		return extendedUnitOfMeasure;
	}

	public BigDecimal getUnitConversionFactor() {
		return unitConversionFactor;
	}

	public BigDecimal getExtendedUnitConversionFactor() {
		return extendedUnitConversionFactor;
	}

	public BigDecimal getPrimaryOrderQuantity() {
		return primaryOrderQuantity;
	}

	public String getPrimaryUnitOfMeasure() {
		return primaryUnitOfMeasure;
	}

	public BigDecimal getSecondaryOrderQuantity() {
		return secondaryOrderQuantity;
	}

	public String getSecondaryUnitOfMeasure() {
		return secondaryUnitOfMeasure;
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

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public BigDecimal getExtendedUnitCost() {
		return extendedUnitCost;
	}

	public BigDecimal getExtendedCost() {
		return extendedCost;
	}

	public BigDecimal getTaxBase() {
		return taxBase;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public BigDecimal getForeignUnitCost() {
		return foreignUnitCost;
	}

	public BigDecimal getForeignExtendedCost() {
		return foreignExtendedCost;
	}

	public BigDecimal getForeignTaxBase() {
		return foreignTaxBase;
	}

	public BigDecimal getForeignTaxAmount() {
		return foreignTaxAmount;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getGlClass() {
		return glClass;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public String getNextStatus() {
		return nextStatus;
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

	public String getUnitDiscountCode() {
		return unitDiscountCode;
	}

	public BigDecimal getUnitDiscountRate() {
		return unitDiscountRate;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public String getObjectId() {
		return objectId;
	}

	public int getOriginalDocumentNumber() {
		return originalDocumentNumber;
	}

	public String getOriginalDocumentType() {
		return originalDocumentType;
	}

	public int getOriginalDocumentSequence() {
		return originalDocumentSequence;
	}

	public LocalDate getOriginalDocumentDate() {
		return originalDocumentDate;
	}

	public int getRelatedDocumentNumber() {
		return relatedDocumentNumber;
	}

	public String getRelatedDocumentType() {
		return relatedDocumentType;
	}

	public int getRelatedDocumentSequence() {
		return relatedDocumentSequence;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getLandedCostRule() {
		return landedCostRule;
	}

	public References getReferences() {
		return references;
	}

	public IntegratedReferences getIntegratedReferences() {
		return integratedReferences;
	}

	public PurchaseOptions getPurchaseOptions() {
		return purchaseOptions;
	}

	public String getOrderTransactionType() {
		return orderTransactionType;
	}

	public PurchaseOrder getOrder() {
		return order;
	}
	
	public boolean isExtended() {
		return this.extendedUnitOfMeasure != null && !this.extendedUnitOfMeasure.isEmpty();
	}

	void setPk(PurchaseDetailPK pk) {
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

	void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	void setSerialLotNo(String serialLotNo) {
		this.serialLotNo = serialLotNo;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setLineType(String lineType) {
		this.lineType = lineType;
	}

	void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	void setExtendedQuantity(BigDecimal extendedQuantity) {
		this.extendedQuantity = extendedQuantity;
	}

	void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	void setReceivedQuantity(BigDecimal receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	void setCancelledQuantity(BigDecimal cancelledQuantity) {
		this.cancelledQuantity = cancelledQuantity;
	}

	void setReturnedQuantity(BigDecimal returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	void setOpenQuantity(BigDecimal openQuantity) {
		this.openQuantity = openQuantity;
	}

	void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	void setExtendedUnitOfMeasure(String extendedUnitOfMeasure) {
		this.extendedUnitOfMeasure = extendedUnitOfMeasure;
	}

	void setUnitConversionFactor(BigDecimal unitConversionFactor) {
		this.unitConversionFactor = unitConversionFactor;
	}

	void setExtendedUnitConversionFactor(BigDecimal extendedUnitConversionFactor) {
		this.extendedUnitConversionFactor = extendedUnitConversionFactor;
	}

	void setPrimaryOrderQuantity(BigDecimal primaryOrderQuantity) {
		this.primaryOrderQuantity = primaryOrderQuantity;
	}

	void setPrimaryUnitOfMeasure(String primaryUnitOfMeasure) {
		this.primaryUnitOfMeasure = primaryUnitOfMeasure;
	}

	void setSecondaryOrderQuantity(BigDecimal secondaryOrderQuantity) {
		this.secondaryOrderQuantity = secondaryOrderQuantity;
	}

	void setSecondaryUnitOfMeasure(String secondaryUnitOfMeasure) {
		this.secondaryUnitOfMeasure = secondaryUnitOfMeasure;
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

	void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	void setExtendedUnitCost(BigDecimal extendedUnitCost) {
		this.extendedUnitCost = extendedUnitCost;
	}

	void setExtendedCost(BigDecimal extendedCost) {
		this.extendedCost = extendedCost;
	}

	void setTaxBase(BigDecimal taxBase) {
		this.taxBase = taxBase;
	}

	void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	void setForeignUnitCost(BigDecimal foreignUnitCost) {
		this.foreignUnitCost = foreignUnitCost;
	}

	void setForeignExtendedCost(BigDecimal foreignExtendedCost) {
		this.foreignExtendedCost = foreignExtendedCost;
	}

	void setForeignTaxBase(BigDecimal foreignTaxBase) {
		this.foreignTaxBase = foreignTaxBase;
	}

	void setForeignTaxAmount(BigDecimal foreignTaxAmount) {
		this.foreignTaxAmount = foreignTaxAmount;
	}

	void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	void setGlClass(String glClass) {
		this.glClass = glClass;
	}

	void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
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

	void setUnitDiscountCode(String unitDiscountCode) {
		this.unitDiscountCode = unitDiscountCode;
	}

	void setUnitDiscountRate(BigDecimal unitDiscountRate) {
		this.unitDiscountRate = unitDiscountRate;
	}

	void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setOriginalDocumentNumber(int originalDocumentNumber) {
		this.originalDocumentNumber = originalDocumentNumber;
	}

	void setOriginalDocumentType(String originalDocumentType) {
		this.originalDocumentType = originalDocumentType;
	}

	void setOriginalDocumentSequence(int originalDocumentSequence) {
		this.originalDocumentSequence = originalDocumentSequence;
	}

	void setOriginalDocumentDate(LocalDate originalDocumentDate) {
		this.originalDocumentDate = originalDocumentDate;
	}

	void setRelatedDocumentNumber(int relatedDocumentNumber) {
		this.relatedDocumentNumber = relatedDocumentNumber;
	}

	void setRelatedDocumentType(String relatedDocumentType) {
		this.relatedDocumentType = relatedDocumentType;
	}

	void setRelatedDocumentSequence(int relatedDocumentSequence) {
		this.relatedDocumentSequence = relatedDocumentSequence;
	}

	void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	void setLandedCostRule(String landedCostRule) {
		this.landedCostRule = landedCostRule;
	}

	void setReferences(References references) {
		this.references = references;
	}

	void setIntegratedReferences(IntegratedReferences integratedReferences) {
		this.integratedReferences = integratedReferences;
	}

	void setPurchaseOptions(PurchaseOptions purchaseOptions) {
		this.purchaseOptions = purchaseOptions;
	}

	void setOrderTransactionType(String orderTransactionType) {
		this.orderTransactionType = orderTransactionType;
	}

	public void setOrder(PurchaseOrder order) {
		this.order = order;
	}
	
	public BigDecimal getUnitDiscountAmount() {
		return this.getExtendedCost()
				.multiply(this.getUnitDiscountRate())
				.divide(BigDecimal.valueOf(100))
				.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getCostAfterUnitDiscount() {
		return this.getExtendedCost().subtract(this.getUnitDiscountAmount());
	}
	
	public OrderStatus getStatus() {
		if (cancelledQuantity.doubleValue() > 0)
			return OrderStatus.CANCELLED;
		if (openQuantity.compareTo(quantity) == 0)
			return OrderStatus.OPEN;
		if (openQuantity.compareTo(BigDecimal.ZERO) == 0)
			return OrderStatus.CLOSED;
		
		return OrderStatus.ETC;
	}

	@JsonSetter("voided") @Transient
	private boolean setForVoid;
	
	public boolean isSetForVoid() {
		return setForVoid;
	}
	
	@JsonGetter("voided")
	public boolean isVoided() {
		return this.getStatus().equals(OrderStatus.CANCELLED);
	}
	
	void voidDocument() {
		if (!this.getStatus().equals(OrderStatus.OPEN))
			throw new IllegalStateException();
		
		this.cancelledQuantity = this.openQuantity;
		this.openQuantity = BigDecimal.ZERO;
	}
}
