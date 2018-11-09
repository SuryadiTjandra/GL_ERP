package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.purchase.PurchaseOptions;
import ags.goldenlionerp.application.purchase.References;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseDetail;
import ags.goldenlionerp.documents.DocumentDetailEntity;

@Entity
@Table(name="T4312")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="OVUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="OVDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="OVTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="OVUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="OVDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="OVTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="OVCID")),
})
public class PurchaseReceipt extends DocumentDetailEntity<PurchaseReceiptPK>{

	@EmbeddedId @JsonUnwrapped
	private PurchaseReceiptPK pk;
	
	@Column(name="OVDOCDT", nullable=false)
	private LocalDate documentDate;
	
	@Column(name="OVMATC")
	private String matchCode;
	
	@Column(name="OVBUID", updatable=false, nullable=false)
	private String businessUnitId;
	
	@Column(name="OVICU", updatable=false)
	private int batchNumber;
	
	@Column(name="OVICUT", updatable=false)
	private String batchType;
	
	@Column(name="OVVNID", updatable = false, nullable=false)
	private String vendorId;
	
	@Column(name="OVCRCB", updatable = false)
	private String baseCurrency;
	
	@Column(name="OVCRCT", updatable = false)
	private String transactionCurrency;
	
	@Column(name="OVEXCRT", precision=19, scale=9, updatable=false)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="OVINUM", updatable=false, nullable=false)
	private String itemCode;
	@JoinColumn(name="OVINUM", insertable=false, updatable=false, nullable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private ItemMaster item;
	
	@Column(name="OVLOCID")
	private String locationId;
	
	@Column(name="OVSNLOT")
	private String serialLotNo;
	
	@Column(name="OVDESB1")
	private String itemDescription;
	
	@Column(name="OVLNTY")
	private String lineType;
	
	@Column(name="OVQTY", precision=19, scale=5)
	private BigDecimal quantity;
	
	@Column(name="OVUOM")
	private String unitOfMeasure;
	
	@Column(name="OVUCF", precision=19, scale=9)
	private BigDecimal unitConversionFactor;
	
	@Column(name="OVPQOT", precision=19, scale=5)
	private BigDecimal primaryTransactionQuantity;
	
	@Column(name="OVUOM1")
	private String primaryUnitOfMeasure;
	
	@Column(name="OVSQOT", precision=19, scale=5)
	private BigDecimal secondaryTransactionQuantity;
	
	@Column(name="OVUOM2")
	private String secondaryUnitOfMeasure;
	
	@Column(name="OVUNCOST", precision=19, scale=7)
	private BigDecimal unitCost;
	
	@Column(name="OVEXCOST", precision=19, scale=5)
	private BigDecimal extendedCost;
	
	@Column(name="OVTAXAB", precision=19, scale=5)
	private BigDecimal taxBase;
	
	@Column(name="OVTAXAM", precision=19, scale=5)
	private BigDecimal taxAmount;
	
	@Column(name="OVFUC", precision=19, scale=7)
	private BigDecimal foreignUnitCost;
	
	@Column(name="OVFEC", precision=19, scale=5)
	private BigDecimal foreignExtendedCost;

	@Column(name="OVFTB", precision=19, scale=5)
	private BigDecimal foreignTaxBase;
	
	@Column(name="OVFTA", precision=19, scale=5)
	private BigDecimal foreignTaxAmount;
	
	@Column(name="OVACID")
	private String accountId;
	
	@Column(name="OVGLCLS")
	private String glClass;
	
	@Column(name="OVLST")
	private String lastStatus;
	
	@Column(name="OVNST")
	private String nextStatus;
	
	@Column(name="OVRCDT")
	private LocalDate receiptDate;
	
	@Column(name="OVPTC")
	private String paymentTermCode;
	
	@Column(name="OVTAXCD")
	private String taxCode;
	
	@Column(name="OVTAXAL")
	private Boolean taxAllowance;
	
	@Column(name="OVTAXRT", precision=19, scale=15)
	private BigDecimal taxRate  = BigDecimal.ZERO;
	
	@Column(name="OVDCCD")
	private String discountCode;
	
	@Column(name="OVDCRT", precision=19, scale=15)
	private BigDecimal discountRate = BigDecimal.ZERO;
	
	@Column(name="OVUDC")
	private String unitDiscountCode;
	
	@Column(name="OVUDF", precision=19, scale=15)
	private BigDecimal unitDiscountRate  = BigDecimal.ZERO;
	
	@Column(name="OVPJID")
	private String projectId;
	
	@Column(name="OVDOCINO")
	private int invoiceNumber;
	
	@Column(name="OVDOCITY")
	private String invoiceType;
	
	@Column(name="OVDOCISQ")
	private int invoiceSequence;
	
	@Column(name="OVDOCIDT")
	private LocalDate invoiceDate;
	
	@Column(name="OVDOCONO", nullable=false)
	private int purchaseOrderNumber;
	
	@Column(name="OVDOCOTY", nullable=false)
	private String purchaseOrderType;	
	
	@Column(name="OVDOCOSQ", nullable=false)
	private int purchaseOrderSequence;
	
	@JoinColumns({
		@JoinColumn(name="OVCOID", referencedColumnName="ODCOID", insertable=false, updatable=false),
		@JoinColumn(name="OVDOCONO", referencedColumnName="ODDOCONO", insertable=false, updatable=false),
		@JoinColumn(name="OVDOCOTY", referencedColumnName="ODDOCOTY", insertable=false, updatable=false),
		@JoinColumn(name="OVDOCOSQ", referencedColumnName="ODDOCOSQ", insertable=false, updatable=false),
	})
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private PurchaseDetail purchaseDetail;
	
	@Column(name="OVORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="OVORDOCTY")
	private String originalDocumentType;
	
	@Column(name="OVORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="OVCSDOCNO", nullable=false)
	private String customerOrderNumber;
	
	@Column(name="OVCSDOCDT")
	private LocalDate customerOrderDate;
	
	@Column(name="OVINVNO")
	private String vendorInvoiceNumber;
	
	@Column(name="OVINVDT")
	private LocalDate vendorInvoiceDate;
	
	@Column(name="OVTAXINO")
	private String taxInvoiceNumber;
	
	@Column(name="OVTAXIDT")
	private LocalDate taxInvoiceDate;
	
	@Column(name="OVEXPDT")
	private LocalDate expiredDate;
	
	@Column(name="OVITT")
	private String inventoryTransactionType;
	
	@Column(name="OVLCRT")
	private String landedCostRecordType;
	
	@Column(name="OVRTGC")
	private Boolean routingProcess;
	
	@Column(name="OVLVLA")
	private String levelCost;
	
	@Column(name="OVITC01")
	private String categoryCode;
	
	@Column(name="OVITC02")
	private String brandCode;
	
	@Column(name="OVITC03")
	private String typeCode;
	
	//@Column(name="OVITC04")
	//private String categoryCode4;
	
	@Column(name="OVITC05")
	private String landedCostRule;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="jobNo", column=@Column(name="OVVR01")),
		@AttributeOverride(name="shipmentId", column=@Column(name="OVVR02")),
		@AttributeOverride(name="packinglistId", column=@Column(name="OVVR03")),
		@AttributeOverride(name="deliveryOrderNumber", column=@Column(name="OVVR04")),
		@AttributeOverride(name="sealNumber", column=@Column(name="OVVR05"))
	})
	@JsonUnwrapped
	private References references = References.builder().build();
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="containerSize", column=@Column(name="OVPO01")),
		@AttributeOverride(name="stuffingType", column=@Column(name="OVPO02")),
		@AttributeOverride(name="containerLoadType", column=@Column(name="OVPO03")),
		//@AttributeOverride(name="purchaseOption4", column=@Column(name="OVPO04")),
		//@AttributeOverride(name="purchaseOption5", column=@Column(name="OVPO05")), etc
	})
	@JsonUnwrapped
	private PurchaseOptions purchaseOptions = PurchaseOptions.builder().build();
	
	@Column(name="OVCOT")
	private String conditionOfTransport;
	
	@Column(name="OVCNID")
	private String containerId;
	
	@Column(name="OVPOD")
	private String portOfDepartureId;
	
	@Column(name="OVPOA")
	private String portOfArrivalId;
	
	@Column(name="OVIDN")
	private String importDeclarationNumber;
	
	@Column(name="OVIDD")
	private LocalDate importDeclarationDate;
	
	@Column(name="OVURTX")
	private String description;
	
	@Column(name="OVOBID")
	private String objectId;
	
	@Column(name="OVRECID")
	private String recordId;
	
	@Transient
	private Set<String> serialNumbers = new HashSet<>();
	
	@Override
	public PurchaseReceiptPK getId() {
		return getPk();
	}

	public PurchaseReceiptPK getPk() {
		return pk;
	}

	public LocalDate getDocumentDate() {
		return documentDate;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public String getBatchType() {
		return batchType;
	}

	public String getVendorId() {
		return vendorId;
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

	public String getItemCode() {
		return itemCode;
	}

	public ItemMaster getItem() {
		return item;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getSerialLotNo() {
		return serialLotNo;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public String getLineType() {
		return lineType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public BigDecimal getUnitConversionFactor() {
		return unitConversionFactor;
	}

	public BigDecimal getPrimaryTransactionQuantity() {
		return primaryTransactionQuantity;
	}

	public String getPrimaryUnitOfMeasure() {
		return primaryUnitOfMeasure;
	}

	public BigDecimal getSecondaryTransactionQuantity() {
		return secondaryTransactionQuantity;
	}

	public String getSecondaryUnitOfMeasure() {
		return secondaryUnitOfMeasure;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
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

	public LocalDate getReceiptDate() {
		return receiptDate;
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

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public int getInvoiceSequence() {
		return invoiceSequence;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public int getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public String getPurchaseOrderType() {
		return purchaseOrderType;
	}

	public int getPurchaseOrderSequence() {
		return purchaseOrderSequence;
	}

	public PurchaseDetail getPurchaseDetail() {
		return purchaseDetail;
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

	public String getCustomerOrderNumber() {
		return customerOrderNumber;
	}

	public LocalDate getCustomerOrderDate() {
		return customerOrderDate;
	}

	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}

	public LocalDate getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}

	public String getTaxInvoiceNumber() {
		return taxInvoiceNumber;
	}

	public LocalDate getTaxInvoiceDate() {
		return taxInvoiceDate;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}

	public String getInventoryTransactionType() {
		return inventoryTransactionType;
	}

	public String getLandedCostRecordType() {
		return landedCostRecordType;
	}

	public Boolean getRoutingProcess() {
		return routingProcess;
	}

	public String getLevelCost() {
		return levelCost;
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

	public PurchaseOptions getPurchaseOptions() {
		return purchaseOptions;
	}

	public String getConditionOfTransport() {
		return conditionOfTransport;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getPortOfDepartureId() {
		return portOfDepartureId;
	}

	public String getPortOfArrivalId() {
		return portOfArrivalId;
	}

	public String getImportDeclarationNumber() {
		return importDeclarationNumber;
	}

	public LocalDate getImportDeclarationDate() {
		return importDeclarationDate;
	}

	public String getDescription() {
		return description;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getRecordId() {
		return recordId;
	}

	void setPk(PurchaseReceiptPK pk) {
		this.pk = pk;
	}

	void setDocumentDate(LocalDate documentDate) {
		this.documentDate = documentDate;
	}

	void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	void setVendorId(String vendorId) {
		this.vendorId = vendorId;
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

	void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	void setItem(ItemMaster item) {
		this.item = item;
	}

	void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	void setSerialLotNo(String serialLotNo) {
		this.serialLotNo = serialLotNo;
	}

	void setItemDescription(String description) {
		this.itemDescription = description;
	}

	void setLineType(String lineType) {
		this.lineType = lineType;
	}

	void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	void setUnitConversionFactor(BigDecimal unitConversionFactor) {
		this.unitConversionFactor = unitConversionFactor;
	}

	void setPrimaryTransactionQuantity(BigDecimal primaryTransactionQuantity) {
		this.primaryTransactionQuantity = primaryTransactionQuantity;
	}

	void setPrimaryUnitOfMeasure(String primaryUnitOfMeasure) {
		this.primaryUnitOfMeasure = primaryUnitOfMeasure;
	}

	void setSecondaryTransactionQuantity(BigDecimal secondaryTransactionQuantity) {
		this.secondaryTransactionQuantity = secondaryTransactionQuantity;
	}

	void setSecondaryUnitOfMeasure(String secondaryUnitOfMeasure) {
		this.secondaryUnitOfMeasure = secondaryUnitOfMeasure;
	}

	void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
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

	void setReceiptDate(LocalDate receiptDate) {
		this.receiptDate = receiptDate;
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

	void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	void setInvoiceSequence(int invoiceSequence) {
		this.invoiceSequence = invoiceSequence;
	}

	void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	void setPurchaseOrderNumber(int purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	void setPurchaseOrderType(String purchaseOrderType) {
		this.purchaseOrderType = purchaseOrderType;
	}

	void setPurchaseOrderSequence(int purchaseOrderSequence) {
		this.purchaseOrderSequence = purchaseOrderSequence;
	}

	void setPurchaseDetail(PurchaseDetail purchaseDetail) {
		this.purchaseDetail = purchaseDetail;
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

	void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}

	void setCustomerOrderDate(LocalDate customerOrderDate) {
		this.customerOrderDate = customerOrderDate;
	}

	void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}

	void setVendorInvoiceDate(LocalDate vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}

	void setTaxInvoiceNumber(String taxInvoiceNumber) {
		this.taxInvoiceNumber = taxInvoiceNumber;
	}

	void setTaxInvoiceDate(LocalDate taxInvoiceDate) {
		this.taxInvoiceDate = taxInvoiceDate;
	}

	void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}

	void setInventoryTransactionType(String inventoryTransactionType) {
		this.inventoryTransactionType = inventoryTransactionType;
	}

	void setLandedCostRecordType(String landedCostRecordType) {
		this.landedCostRecordType = landedCostRecordType;
	}

	void setRoutingProcess(Boolean routingProcess) {
		this.routingProcess = routingProcess;
	}

	void setLevelCost(String levelCost) {
		this.levelCost = levelCost;
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

	void setPurchaseOptions(PurchaseOptions purchaseOptions) {
		this.purchaseOptions = purchaseOptions;
	}

	void setConditionOfTransport(String conditionOfTransport) {
		this.conditionOfTransport = conditionOfTransport;
	}

	void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	void setPortOfDepartureId(String portOfDepartureId) {
		this.portOfDepartureId = portOfDepartureId;
	}

	void setPortOfArrivalId(String portOfArrivalId) {
		this.portOfArrivalId = portOfArrivalId;
	}

	void setImportDeclarationNumber(String importDeclarationNumber) {
		this.importDeclarationNumber = importDeclarationNumber;
	}

	void setImportDeclarationDate(LocalDate importDeclarationDate) {
		this.importDeclarationDate = importDeclarationDate;
	}

	void setDescription(String userReservedText) {
		this.description = userReservedText;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	Set<String> getSerialNumbers() {
		return serialNumbers;
	}

	void setSerialNumbers(Set<String> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
	
	@JsonSetter("voided") @Transient
	private boolean toBeVoided;
	
	public boolean isToBeVoided() {
		return toBeVoided;
	}
	
	@JsonGetter("voided")
	public boolean isVoided() {
		return lastStatus.equals("999");
	}
}
