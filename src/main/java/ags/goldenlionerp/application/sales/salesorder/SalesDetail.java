package ags.goldenlionerp.application.sales.salesorder;

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

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.purchase.IntegratedReferences;
import ags.goldenlionerp.application.purchase.References;
import ags.goldenlionerp.application.sales.SalesOptions;
import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T4211")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="SDUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="SDDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="SDTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="SDUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="SDDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="SDTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="SDCID")),
})
public class SalesDetail extends DatabaseEntity<SalesDetailPK>{

	@EmbeddedId @JsonUnwrapped
	private SalesDetailPK pk;
	
	@Column(name="SDBUID")
	private String businessUnitId;
	
	@Column(name="SDCSID")
	private String customerId;
	
	@Column(name="SDSTID")
	private String receiverId;
	
	@Column(name="SDEXID")
	private String expeditionId;
	
	@Column(name="SDPYID")
	private String payerId;
	
	@Column(name="SDSLID")
	private String salesmanId;
	
	@Column(name="SDPRID")
	private String partnerRepresentativeId;
	
	@Column(name="SDINUM")
	private String itemCode;
	
	@Column(name="SDLOCID")
	private String locationId;
	
	@Column(name="SDSNLOT")
	private String serialLotNo;
	
	@Column(name="SDDESB1")
	private String description;
	
	@Column(name="SDLNTY")
	private String lineType;
	
	@Column(name="SDQTY", precision=19, scale=5)
	private BigDecimal quantity;
	
	@Column(name="SDQTYX", precision=19, scale=5)
	private BigDecimal extendedQuantity;
	
	@Column(name="SDQTYSH", precision=19, scale=5)
	private BigDecimal shippedQuantity;
	
	@Column(name="SDQTYCL", precision=19, scale=5)
	private BigDecimal cancelledQuantity;
	
	@Column(name="SDQTYRN", precision=19, scale=5)
	private BigDecimal returnedQuantity;
	
	@Column(name="SDQTYOP", precision=19, scale=5)
	private BigDecimal openQuantity;
	
	@Column(name="SDUOM")
	private String unitOfMeasure;
	
	@Column(name="SDUOMX")
	private String extendedUnitOfMeasure;
	
	@Column(name="SDUCF", precision=19, scale=9)
	private BigDecimal unitConversionFactor;
	
	@Column(name="SDUCFX", precision=19, scale=9)
	private BigDecimal extendedUnitConversionFactor;
	
	@Column(name="SDPQOR", precision=19, scale=5)
	private BigDecimal primaryOrderQuantity;
	
	@Column(name="SDUOM1")
	private String primaryUnitOfMeasure;
	
	@Column(name="SDSQOR", precision=19, scale=5)
	private BigDecimal secondaryOrderQuantity;
	
	@Column(name="SDUOM2")
	private String secondaryUnitOfMeasure;
	
	@Column(name="SDCRCB")
	private String baseCurrency;
	
	@Column(name="SDCRCT")
	private String transactionCurrency;

	@Column(name="SDEXCRT", precision=19, scale=9)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="SDUNCOST", precision=19, scale=7)
	private BigDecimal unitCost;
	
	@Column(name="SDEXCOST", precision=19, scale=5)
	private BigDecimal extendedCost;
	
	@Column(name="SDUNPRICE", precision=19, scale=7)
	private BigDecimal unitPrice;
	
	@Column(name="SDUNPRICEX", precision=19, scale=7)
	private BigDecimal extendedUnitPrice;
	
	@Column(name="SDEXPRICE", precision=19, scale=5)
	private BigDecimal extendedPrice;
	
	@Column(name="SDTAXAB", precision=19, scale=5)
	private BigDecimal taxBase;
	
	@Column(name="SDTAXAM", precision=19, scale=5)
	private BigDecimal taxAmount;
	
	@Column(name="SDFUC", precision=19, scale=7)
	private BigDecimal foreignUnitCost;
	
	@Column(name="SDFEC", precision=19, scale=5)
	private BigDecimal foreignExtendedCost;

	@Column(name="SDFTB", precision=19, scale=5)
	private BigDecimal foreignTaxBase;
	
	@Column(name="SDFTA", precision=19, scale=5)
	private BigDecimal foreignTaxAmount;
	
	@Column(name="SDPJID")
	private String projectId;
	
	@Column(name="SDRBUID")
	private String profitCenterId;
	
	@Column(name="SDACID")
	private String accountId;
	
	@Column(name="SDPTC")
	private String paymentTermCode;
	
	@Column(name="SDHCOD")
	private String holdCode;
	
	@Column(name="SDTAXCD")
	private String taxCode;
	
	@Column(name="SDTAXAL")
	private Boolean taxAllowance;
	
	@Column(name="SDTAXRT", precision=19, scale=15)
	private BigDecimal taxRate;
	
	@Column(name="SDGSCRT", precision=19, scale=15)
	private BigDecimal guestServiceChargeRate;
	
	@Column(name="SDDCCD")
	private String discountCode;
	
	@Column(name="SDDCRT", precision=19, scale=15)
	private BigDecimal discountRate = BigDecimal.ZERO;
	
	@Column(name="SDUDC")
	private String unitDiscountCode;
	
	@Column(name="SDUDF", precision=19, scale=15)
	private BigDecimal unitDiscountRate  = BigDecimal.ZERO;
	
	@Column(name="SDLST")
	private String lastStatus;
	
	@Column(name="SDNST")
	private String nextStatus;
	
	@Column(name="SDORDT")
	private LocalDate orderDate;
	
	@Column(name="SDRQDT")
	private LocalDate requestDate;
	
	@Column(name="SDPDDT")
	private LocalDate promisedDeliveryDate;
	
	@Column(name="SDDLDT")
	private LocalDate deliveryDate;
	
	@Column(name="SDCLDT")
	private LocalDate closedDate;

	@Column(name="SDGLDT")
	private LocalDate glDate;
	
	@Column(name="SDGLCLS")
	private String glClass;
	
	@Column(name="SDORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="SDORDOCTY")
	private String originalDocumentType;
	
	@Column(name="SDORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="SDORDOCDT")
	private LocalDate originalDocumentDate;
	
	@Column(name="SDRLDOCNO")
	private int relatedDocumentNumber;
	
	@Column(name="SDRLDOCTY")
	private String relatedDocumentType;
	
	@Column(name="SDRLDOCSQ")
	private int relatedDocumentSequence;
	
	@Column(name="ODSLRC1")
	private String serverOperatorCode;
	
	//@Column(name="ODSLRC2")
	//private String salesReportingCode;
	
	//@Column(name="ODSLRC3")
	//private String salesReportingCode;
	
	//@Column(name="ODSLRC4")
	//private String salesReportingCode;
	
	//@Column(name="ODSLRC5")
	//private String salesReportingCode;
	
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
		@AttributeOverride(name="containerSize", column=@Column(name="SDPO01")),
		@AttributeOverride(name="stuffingType", column=@Column(name="SDPO02")),
		@AttributeOverride(name="containerLoadType", column=@Column(name="SDPO03")),
		//@AttributeOverride(name="purchaseOption4", column=@Column(name="SDPO04")),
		//@AttributeOverride(name="purchaseOption5", column=@Column(name="SDPO05")), etc
	})
	@JsonUnwrapped
	private SalesOptions salesOptions = SalesOptions.builder().build();
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="jobNo", column=@Column(name="SDVR01")),
		@AttributeOverride(name="shipmentId", column=@Column(name="SDVR02")),
		@AttributeOverride(name="packinglistId", column=@Column(name="SDVR03")),
		@AttributeOverride(name="deliveryOrderNumber", column=@Column(name="SDVR04")),
		@AttributeOverride(name="sealNumber", column=@Column(name="SDVR05"))
	})
	@JsonUnwrapped
	private References references = References.builder().build();
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="feederVessel", column=@Column(name="SDIR01")),
		@AttributeOverride(name="connectingVessel", column=@Column(name="SDIR02")),
		@AttributeOverride(name="shippedBy", column=@Column(name="SDIR03")),
		//@AttributeOverride(name="integratedReference4", column=@Column(name="SDIR04")),
		//@AttributeOverride(name="integratedReference5", column=@Column(name="SDIR05")),
	})
	@JsonUnwrapped
	private IntegratedReferences integratedReferences = IntegratedReferences.builder().build();
	
	@Column(name="SDCNID")
	private String containerId;
	
	@Column(name="SDVHRN")
	private String vehicleRegistrationNumber;
	
	@Column(name="SDVHTY")
	private String vehicleType;
	
	@Column(name="SDQTYWY", precision=19, scale=5)
	private BigDecimal weightQuantity;
	
	@Column(name="SDUOMW")
	private String weightUnitOfMeasure;
	
	@Column(name="SDQTYVL", precision=19, scale=5)
	private BigDecimal volumeQuantity;
	
	@Column(name="SDUOMV")
	private String volumeUnitOfMeasure;
	
	@Column(name="SDURN1")
	private int userReservedNumber1;
	
	@Column(name="SDURN2")
	private int userReservedNumber2;
	
	@Column(name="SDOTT")
	private String orderTransactionType;
	
	@Column(name="SDOBID")
	private String objectId;
	
	@JoinColumns({
		@JoinColumn(name="SDCOID", referencedColumnName="SHCOID", updatable=false, insertable=false),
		@JoinColumn(name="SDDOCONO", referencedColumnName="SHDOCONO", updatable=false, insertable=false),
		@JoinColumn(name="SDDOCOTY", referencedColumnName="SHDOCOTY", updatable=false, insertable=false)
	})
	@ManyToOne(optional=false)
	private SalesOrder order;
	
	@Override
	public SalesDetailPK getId() {
		return getPk();
	}

	public SalesDetailPK getPk() {
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

	public String getPartnerRepresentativeId() {
		return partnerRepresentativeId;
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

	public BigDecimal getShippedQuantity() {
		return shippedQuantity;
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

	public BigDecimal getExtendedCost() {
		return extendedCost;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getExtendedUnitPrice() {
		return extendedUnitPrice;
	}

	public BigDecimal getExtendedPrice() {
		return extendedPrice;
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

	public String getProjectId() {
		return projectId;
	}

	public String getProfitCenterId() {
		return profitCenterId;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getPaymentTermCode() {
		return paymentTermCode;
	}

	public String getHoldCode() {
		return holdCode;
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

	public String getUnitDiscountCode() {
		return unitDiscountCode;
	}

	public BigDecimal getUnitDiscountRate() {
		return unitDiscountRate;
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

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public LocalDate getGlDate() {
		return glDate;
	}

	public String getGlClass() {
		return glClass;
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

	public String getServerOperatorCode() {
		return serverOperatorCode;
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

	public SalesOptions getSalesOptions() {
		return salesOptions;
	}

	public References getReferences() {
		return references;
	}

	public IntegratedReferences getIntegratedReferences() {
		return integratedReferences;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public BigDecimal getWeightQuantity() {
		return weightQuantity;
	}

	public String getWeightUnitOfMeasure() {
		return weightUnitOfMeasure;
	}

	public BigDecimal getVolumeQuantity() {
		return volumeQuantity;
	}

	public String getVolumeUnitOfMeasure() {
		return volumeUnitOfMeasure;
	}

	public int getUserReservedNumber1() {
		return userReservedNumber1;
	}

	public int getUserReservedNumber2() {
		return userReservedNumber2;
	}

	public String getOrderTransactionType() {
		return orderTransactionType;
	}

	public String getObjectId() {
		return objectId;
	}

	public SalesOrder getOrder() {
		return order;
	}
	
	public boolean isExtended() {
		return this.extendedUnitOfMeasure != null && !this.extendedUnitOfMeasure.isEmpty();
	}

	void setPk(SalesDetailPK pk) {
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

	void setPartnerRepresentativeId(String partnerRepresentativeId) {
		this.partnerRepresentativeId = partnerRepresentativeId;
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

	void setShippedQuantity(BigDecimal shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
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

	void setExtendedCost(BigDecimal extendedCost) {
		this.extendedCost = extendedCost;
	}

	void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	void setExtendedUnitPrice(BigDecimal extendedUnitPrice) {
		this.extendedUnitPrice = extendedUnitPrice;
	}

	void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
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

	void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	void setProfitCenterId(String profitCenterId) {
		this.profitCenterId = profitCenterId;
	}

	void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
	}

	void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
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

	void setUnitDiscountCode(String unitDiscountCode) {
		this.unitDiscountCode = unitDiscountCode;
	}

	void setUnitDiscountRate(BigDecimal unitDiscountRate) {
		this.unitDiscountRate = unitDiscountRate;
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

	void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
	}

	void setGlClass(String glClass) {
		this.glClass = glClass;
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

	void setServerOperatorCode(String serverOperatorCode) {
		this.serverOperatorCode = serverOperatorCode;
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

	void setSalesOptions(SalesOptions salesOptions) {
		this.salesOptions = salesOptions;
	}

	void setReferences(References references) {
		this.references = references;
	}

	void setIntegratedReferences(IntegratedReferences integratedReferences) {
		this.integratedReferences = integratedReferences;
	}

	void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	void setWeightQuantity(BigDecimal weightQuantity) {
		this.weightQuantity = weightQuantity;
	}

	void setWeightUnitOfMeasure(String weightUnitOfMeasure) {
		this.weightUnitOfMeasure = weightUnitOfMeasure;
	}

	void setVolumeQuantity(BigDecimal volumeQuantity) {
		this.volumeQuantity = volumeQuantity;
	}

	void setVolumeUnitOfMeasure(String volumeUnitOfMeasure) {
		this.volumeUnitOfMeasure = volumeUnitOfMeasure;
	}

	void setUserReservedNumber1(int userReservedNumber1) {
		this.userReservedNumber1 = userReservedNumber1;
	}

	void setUserReservedNumber2(int userReservedNumber2) {
		this.userReservedNumber2 = userReservedNumber2;
	}

	void setOrderTransactionType(String orderTransactionType) {
		this.orderTransactionType = orderTransactionType;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setOrder(SalesOrder order) {
		this.order = order;
	}

	public BigDecimal getUnitDiscountAmount() {
		return this.getExtendedPrice()
				.multiply(this.getUnitDiscountRate())
				.divide(BigDecimal.valueOf(100))
				.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getPriceAfterUnitDiscount() {
		return this.getExtendedPrice().subtract(this.getUnitDiscountAmount());
	}
}
