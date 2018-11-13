package ags.goldenlionerp.application.sales.salesshipment;

import java.math.BigDecimal;
import java.time.LocalDate;

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

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.purchase.IntegratedReferences;
import ags.goldenlionerp.application.purchase.References;
import ags.goldenlionerp.application.sales.SalesOptions;
import ags.goldenlionerp.application.sales.salesorder.SalesDetail;
import ags.goldenlionerp.documents.DocumentDetailEntity;

@Entity
@Table(name="T4212")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="SLUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="SLDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="SLTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="SLUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="SLDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="SLTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="SLCID")),
})
public class SalesShipment extends DocumentDetailEntity<SalesShipmentPK> {

	@EmbeddedId @JsonUnwrapped
	private SalesShipmentPK pk;
	
	@Column(name="SLDOCDT")
	private LocalDate documentDate;
	
	@Column(name="SLBUID", updatable=false, nullable=false)
	private String businessUnitId;
	
	@Column(name="SLICU", updatable=false)
	private int batchNumber;
	
	@Column(name="SLICUT", updatable=false)
	private String batchType;
	
	@Column(name="SLCSID", updatable=false)
	private String customerId;
	
	@Column(name="SLSTID", updatable=false)
	private String receiverId;
	
	@Column(name="SLEXID")
	private String expeditionId;
	
	@Column(name="SLPYID")
	private String payerId;
	
	@Column(name="SLSLID")
	private String salesmanId;
	
	@Column(name="SLCRCB", updatable=false)
	private String baseCurrency;
	
	@Column(name="SLCRCT", updatable=false)
	private String transactionCurrency;
	
	@Column(name="SLEXCRT", precision=19, scale=9, updatable=false)
	private BigDecimal exchangeRate = BigDecimal.ONE;
	
	@Column(name="SLINUM", nullable=false)
	private String itemCode;
	@JoinColumn(name="SLINUM", insertable=false, updatable=false, nullable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	private ItemMaster item;
	
	@Column(name="SLLOCID")
	private String locationId;
	
	@Column(name="SLSNLOT")
	private String serialLotNo;
	
	@Column(name="SLDESB1")
	private String itemDescription;
	
	@Column(name="SLLNTY")
	private String lineType;
	
	@Column(name="SLQTY", precision=19, scale=5)
	private BigDecimal quantity;
	
	@Column(name="SLUOM")
	private String unitOfMeasure;
	
	@Column(name="SLUCF", precision=19, scale=9)
	private BigDecimal unitConversionFactor;
	
	@Column(name="SLPQOT", precision=19, scale=5)
	private BigDecimal primaryTransactionQuantity;
	
	@Column(name="SLUOM1")
	private String primaryUnitOfMeasure;
	
	@Column(name="SLSQOT", precision=19, scale=5)
	private BigDecimal secondaryTransactionQuantity;
	
	@Column(name="SLUOM2")
	private String secondaryUnitOfMeasure;
	
	@Column(name="SLUNPRICE", precision=19, scale=7)
	private BigDecimal unitPrice;
	
	@Column(name="SLEXPRICE", precision=19, scale=5)
	private BigDecimal extendedPrice;
	
	@Column(name="SLTAXAB", precision=19, scale=5)
	private BigDecimal taxBase;
	
	@Column(name="SLTAXAM", precision=19, scale=5)
	private BigDecimal taxAmount;
	
	@Column(name="SLUNCOST", precision=19, scale=7)
	private BigDecimal unitCost;
	
	@Column(name="SLEXCOST", precision=19, scale=5)
	private BigDecimal extendedCost;
	
	@Column(name="SLFUP", precision=19, scale=7)
	private BigDecimal foreignUnitPrice;
	
	@Column(name="SLFEP", precision=19, scale=5)
	private BigDecimal foreignExtendedPrice;

	@Column(name="SLFTB", precision=19, scale=5)
	private BigDecimal foreignTaxBase;
	
	@Column(name="SLFTA", precision=19, scale=5)
	private BigDecimal foreignTaxAmount;
	
	@Column(name="SLPJID")
	private String projectId;
	
	@Column(name="SLRBUID")
	private String profitCenterId;
	
	@Column(name="SLACID")
	private String accountId;
	
	@Column(name="SLPTC")
	private String paymentTermCode;
	
	@Column(name="SLTAXCD")
	private String taxCode;
	
	@Column(name="SLTAXAL")
	private Boolean taxAllowance;
	
	@Column(name="SLTAXRT", precision=19, scale=15)
	private BigDecimal taxRate;
	
	@Column(name="SLGSCRT", precision=19, scale=15)
	private BigDecimal guestServiceChargeRate;
	
	@Column(name="SLDCCD")
	private String discountCode;
	
	@Column(name="SLDCRT", precision=19, scale=15)
	private BigDecimal discountRate = BigDecimal.ZERO;
	
	@Column(name="SLUDC")
	private String unitDiscountCode;
	
	@Column(name="SLUDF", precision=19, scale=15)
	private BigDecimal unitDiscountRate  = BigDecimal.ZERO;
	
	@Column(name="SLLST")
	private String lastStatus;
	
	@Column(name="SLNST")
	private String nextStatus;
	
	@Column(name="SLGLCLS")
	private String glClass;
	
	@Column(name="SLDOCINO")
	private int invoiceNumber;
	
	@Column(name="SLDOCITY")
	private String invoiceType;
	
	@Column(name="SLDOCISQ")
	private int invoiceSequence;
	
	@Column(name="SLDOCONO")
	private int orderNumber;
	
	@Column(name="SLDOCOTY")
	private String orderType;
	
	@Column(name="SLDOCOSQ")
	private int orderSequence;
	
	@JoinColumns({
		@JoinColumn(name="SLCOID", referencedColumnName="SDCOID", insertable=false, updatable=false),
		@JoinColumn(name="SLDOCONO", referencedColumnName="SDDOCONO", insertable=false, updatable=false),
		@JoinColumn(name="SLDOCOTY", referencedColumnName="SDDOCOTY", insertable=false, updatable=false),
		@JoinColumn(name="SLDOCOSQ", referencedColumnName="SDDOCOSQ", insertable=false, updatable=false),
	})
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private SalesDetail salesDetail;
	
	@Column(name="SLORDOCNO")
	private int originalDocumentNumber;
	
	@Column(name="SLORDOCTY")
	private String originalDocumentType;
	
	@Column(name="SLORDOCSQ")
	private int originalDocumentSequence;
	
	@Column(name="SLORDOCDT")
	private LocalDate originalDocumentDate;
	
	@Column(name="SLCSDOCNO", nullable=false)
	private String customerOrderNumber;
	
	@Column(name="SLCSDOCDT")
	private LocalDate customerOrderDate;
	
	@Column(name="SLINVNO")
	private String vendorInvoiceNumber;
	
	@Column(name="SLINVDT")
	private LocalDate vendorInvoiceDate;
	
	@Column(name="SLTAXINO")
	private String taxInvoiceNumber;
	
	@Column(name="SLTAXIDT")
	private LocalDate taxInvoiceDate;
	
	@Column(name="SLINOP")
	private int timesInvoicePrinted;
	
	@Column(name="SLDNOP")
	private int timesDeliveryOrderPrinted;
	
	@Column(name="SLITT")
	private String inventoryTransactionType;
	
	@Column(name="SLDESC1")
	private String description;
	
	@Column(name="SLDESC2")
	private String description2;
	
	@Column(name="SLCNID")
	private String containerId;
	
	@Column(name="SLSHCN")
	private String shipmentCondition;
	
	@Column(name="SLPOD")
	private String portOfDepartureId;
	
	@Column(name="SLPOA")
	private String portOfArrivalId;
	
	@Column(name="SLETD")
	private LocalDate estimatedTimeOfDeparture;
	
	@Column(name="SLETA")
	private LocalDate estimatedTimeOfArrival;
	
	@Column(name="SLATD")
	private LocalDate actualTimeOfDeparture;
	
	@Column(name="SLATA")
	private LocalDate actualTimeOfArrival;
	
	@Column(name="SLDOL")
	private LocalDate dateOfLoading;
	
	@Column(name="SLDOU")
	private LocalDate dateOfUnloading;
	
	@Column(name="SLDOD")
	private LocalDate dateOfDocking;
	
	@Column(name="SLDLDT")
	private LocalDate deliveryDate;
	
	@Column(name="SLEDN")
	private String exportDeclarationNumber;
	
	@Column(name="SLEDD")
	private LocalDate exportDeclarationDate;
	
	@Column(name="SLFBN")
	private String freightBillingNumber;
	
	@Column(name="SLFBD")
	private LocalDate freightBillingDate;
	
	@Column(name="SLEAN")
	private String exportApprovalNumber;
	
	@Column(name="SLEAD")
	private LocalDate exportApprovalDate;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="jobNo", column=@Column(name="SLVR01")),
		@AttributeOverride(name="shipmentId", column=@Column(name="SLVR02")),
		@AttributeOverride(name="packinglistId", column=@Column(name="SLVR03")),
		@AttributeOverride(name="deliveryOrderNumber", column=@Column(name="SLVR04")),
		@AttributeOverride(name="sealNumber", column=@Column(name="SLVR05"))
	})
	@JsonUnwrapped
	private References references = References.builder().build();
	
	@Column(name="SLSLRC1")
	private String serverOperatorCode;
	
	//@Column(name="SLSLRC2")
	//private String salesReportingCode;
	
	//@Column(name="SLSLRC3")
	//private String salesReportingCode;
	
	//@Column(name="SLSLRC4")
	//private String salesReportingCode;
	
	//@Column(name="SLSLRC5")
	//private String salesReportingCode;
	
	@Column(name="SLITC01")
	private String categoryCode;
	
	@Column(name="SLITC02")
	private String brandCode;
	
	@Column(name="SLITC03")
	private String typeCode;
	
	//@Column(name="SLITC04")
	//private String categoryCode4;
	
	@Column(name="SLITC05")
	private String landedCostRule;
	

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="containerSize", column=@Column(name="SLSO01")),
		@AttributeOverride(name="stuffingType", column=@Column(name="SLSO02")),
		@AttributeOverride(name="containerLoadType", column=@Column(name="SLSO03")),
		//@AttributeOverride(name="purchaseOption4", column=@Column(name="SLSO04")),
		//@AttributeOverride(name="purchaseOption5", column=@Column(name="SLSO05")), etc
	})
	@JsonUnwrapped
	private SalesOptions salesOptions = SalesOptions.builder().build();
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="feederVessel", column=@Column(name="SLIR01")),
		@AttributeOverride(name="connectingVessel", column=@Column(name="SLIR02")),
		@AttributeOverride(name="shippedBy", column=@Column(name="SLIR03")),
		//@AttributeOverride(name="integratedReference4", column=@Column(name="SLIR04")),
		//@AttributeOverride(name="integratedReference5", column=@Column(name="SLIR05")),
	})
	@JsonUnwrapped
	private IntegratedReferences integratedReferences = IntegratedReferences.builder().build();
	
	@Column(name="SLPRID")
	private String partnerRepresentativeId;
	
	@Column(name="SLOBID")
	private String objectId;
	
	@Column(name="SLRECID")
	private String recordId;
	
	@Override
	public SalesShipmentPK getPk() {
		return pk;
	}

	@Override
	public SalesShipmentPK getId() {
		return getPk();
	}

	public LocalDate getDocumentDate() {
		return documentDate;
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

	public BigDecimal getUnitPrice() {
		return unitPrice;
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

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public BigDecimal getExtendedCost() {
		return extendedCost;
	}

	public BigDecimal getForeignUnitPrice() {
		return foreignUnitPrice;
	}

	public BigDecimal getForeignExtendedPrice() {
		return foreignExtendedPrice;
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

	public String getGlClass() {
		return glClass;
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

	public int getOrderNumber() {
		return orderNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public int getOrderSequence() {
		return orderSequence;
	}

	public SalesDetail getSalesDetail() {
		return salesDetail;
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

	public int getTimesInvoicePrinted() {
		return timesInvoicePrinted;
	}

	public int getTimesDeliveryOrderPrinted() {
		return timesDeliveryOrderPrinted;
	}

	public String getInventoryTransactionType() {
		return inventoryTransactionType;
	}

	public String getDescription() {
		return description;
	}

	public String getDescription2() {
		return description2;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getShipmentCondition() {
		return shipmentCondition;
	}

	public String getPortOfDepartureId() {
		return portOfDepartureId;
	}

	public String getPortOfArrivalId() {
		return portOfArrivalId;
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

	public LocalDate getDeliveryDate() {
		return deliveryDate;
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

	public References getReferences() {
		return references;
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

	public IntegratedReferences getIntegratedReferences() {
		return integratedReferences;
	}

	public String getPartnerRepresentativeId() {
		return partnerRepresentativeId;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getRecordId() {
		return recordId;
	}

	void setPk(SalesShipmentPK pk) {
		this.pk = pk;
	}

	void setDocumentDate(LocalDate documentDate) {
		this.documentDate = documentDate;
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

	void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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

	void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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

	void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	void setExtendedCost(BigDecimal extendedCost) {
		this.extendedCost = extendedCost;
	}

	void setForeignUnitPrice(BigDecimal foreignUnitPrice) {
		this.foreignUnitPrice = foreignUnitPrice;
	}

	void setForeignExtendedPrice(BigDecimal foreignExtendedPrice) {
		this.foreignExtendedPrice = foreignExtendedPrice;
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

	void setGlClass(String glClass) {
		this.glClass = glClass;
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

	void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	void setSalesDetail(SalesDetail salesDetail) {
		this.salesDetail = salesDetail;
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

	void setTimesInvoicePrinted(int timesInvoicePrinted) {
		this.timesInvoicePrinted = timesInvoicePrinted;
	}

	void setTimesDeliveryOrderPrinted(int timesDeliveryOrderPrinted) {
		this.timesDeliveryOrderPrinted = timesDeliveryOrderPrinted;
	}

	void setInventoryTransactionType(String inventoryTransactionType) {
		this.inventoryTransactionType = inventoryTransactionType;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDescription2(String description2) {
		this.description2 = description2;
	}

	void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	void setShipmentCondition(String shipmentCondition) {
		this.shipmentCondition = shipmentCondition;
	}

	void setPortOfDepartureId(String portOfDepartureId) {
		this.portOfDepartureId = portOfDepartureId;
	}

	void setPortOfArrivalId(String portOfArrivalId) {
		this.portOfArrivalId = portOfArrivalId;
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

	void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	void setReferences(References references) {
		this.references = references;
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

	void setIntegratedReferences(IntegratedReferences integratedReferences) {
		this.integratedReferences = integratedReferences;
	}

	void setPartnerRepresentativeId(String partnerRepresentativeId) {
		this.partnerRepresentativeId = partnerRepresentativeId;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	

}
