package ags.goldenlionerp.application.item.itemtransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DocumentDetailEntity;

@Entity
@Table(name="T4111")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="ITUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="ITDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="ITTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="ITUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="ITDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="ITTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="ITCID")),
})
public class ItemTransaction extends DocumentDetailEntity<ItemTransactionPK> {

	@EmbeddedId
	private ItemTransactionPK pk;
	
	@Column(name="ITBUID")
	private String businessUnitId;
	
	@Column(name="ITICU")
	private int batchNumber;
	
	@Column(name="ITICUT")
	private String batchType;
	
	@Column(name="ITDOCMO")
	private Month documentMonth;
	
	@Column(name="ITDOCYR")
	private String documentYear;
	
	@Column(name="ITDOCDT")
	private LocalDate documentDate;
	
	@Column(name="ITDOCTM")
	private String documentTime;
	
	@Column(name="ITGLDT")
	private LocalDate glDate;
	
	@Column(name="ITINUM")
	private String itemCode;
	
	@Column(name="ITLOCID")
	private String locationId;
	
	@Column(name="ITSNLOT")
	private String serialLotNo;
	
	@Column(name="ITDESB1")
	private String itemDescription;
	
	@Column(name="ITDESB2")
	private String description;
	
	@Column(name="ITQTY", precision=19, scale=5)
	private BigDecimal quantity;
	
	@Column(name="ITUOM")
	private String unitOfMeasure;
	
	@Column(name="ITUCF", precision=19, scale=9)
	private BigDecimal unitConversionFactor;
	
	@Column(name="ITPQOT", precision=19, scale=5)
	private BigDecimal primaryTransactionQuantity;
	
	@Column(name="ITUOM1")
	private String primaryUnitOfMeasure;
	
	@Column(name="ITSQOT", precision=19, scale=5)
	private BigDecimal secondaryTransactionQuantity;
	
	@Column(name="ITUOM2")
	private String secondaryUnitOfMeasure;
	
	@Column(name="ITUNCOST", precision=19, scale=7)
	private BigDecimal unitCost;
	
	@Column(name="ITEXCOST", precision=19, scale=5)
	private BigDecimal extendedCost;
	
	@Column(name="ITFT")
	private String fromOrTo;
	
	@Column(name="ITANUM")
	private String businessPartnerId;
	
	@Column(name="ITST")
	private String businessPartnerSearchType;
	
	@Column(name="ITPOST")
	private String batchPostingStatus;
	
	@Column(name="ITGLCLS")
	private String glClass;
	
	@Column(name="ITEXPDT")
	private LocalDate expiredDate;
	
	@Column(name="ITDOCONO")
	private int orderNumber;
	
	@Column(name="ITDOCOTY")
	private String orderType;
	
	@Column(name="ITDOCOSQ")
	private int orderSequence;
	
	@Column(name="ITRLDOCNO")
	private int relatedDocumentNumber;
	
	@Column(name="ITRLDOCTY")
	private String relatedDocumentType;
	
	@Column(name="ITRLDOCSQ")
	private int relatedDocumentSequence;
	
	@Column(name="ITIPCD")
	private String inventoryProcessingCode;
	
	@Column(name="ITITT")
	private String inventoryTransactionType;
	
	@Column(name="ITDVS")
	private String documentVoidStatus;
	
	@Column(name="ITOBID")
	private String objectId;
	
	@Column(name="ITRECID")
	private String recordId;
	
	@Override
	public ItemTransactionPK getId() {
		return getPk();
	}

	public ItemTransactionPK getPk() {
		return pk;
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

	public Month getDocumentMonth() {
		return documentMonth;
	}

	public String getDocumentYear() {
		return documentYear;
	}

	public LocalDate getDocumentDate() {
		return documentDate;
	}

	public String getDocumentTime() {
		return documentTime;
	}

	public LocalDate getGlDate() {
		return glDate;
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

	public String getItemDescription() {
		return itemDescription;
	}

	public String getDescription() {
		return description;
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

	public String getFromOrTo() {
		return fromOrTo;
	}

	public String getBusinessPartnerId() {
		return businessPartnerId;
	}

	public String getBusinessPartnerSearchType() {
		return businessPartnerSearchType;
	}

	public String getBatchPostingStatus() {
		return batchPostingStatus;
	}

	public String getGlClass() {
		return glClass;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
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

	public int getRelatedDocumentNumber() {
		return relatedDocumentNumber;
	}

	public String getRelatedDocumentType() {
		return relatedDocumentType;
	}

	public int getRelatedDocumentSequence() {
		return relatedDocumentSequence;
	}

	public String getInventoryProcessingCode() {
		return inventoryProcessingCode;
	}

	public String getInventoryTransactionType() {
		return inventoryTransactionType;
	}

	public String getDocumentVoidStatus() {
		return documentVoidStatus;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getRecordId() {
		return recordId;
	}

	void setPk(ItemTransactionPK pk) {
		this.pk = pk;
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

	void setDocumentMonth(Month documentMonth) {
		this.documentMonth = documentMonth;
	}

	void setDocumentYear(String documentYear) {
		this.documentYear = documentYear;
	}

	void setDocumentDate(LocalDate documentDate) {
		this.documentDate = documentDate;
	}

	void setDocumentTime(String documentTime) {
		this.documentTime = documentTime;
	}

	void setGlDate(LocalDate glDate) {
		this.glDate = glDate;
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

	void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	void setDescription(String description) {
		this.description = description;
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

	void setFromOrTo(String fromOrTo) {
		this.fromOrTo = fromOrTo;
	}

	void setBusinessPartnerId(String businessPartnerId) {
		this.businessPartnerId = businessPartnerId;
	}

	void setBusinessPartnerSearchType(String businessPartnerSearchType) {
		this.businessPartnerSearchType = businessPartnerSearchType;
	}

	void setBatchPostingStatus(String batchPostingStatus) {
		this.batchPostingStatus = batchPostingStatus;
	}

	void setGlClass(String glClass) {
		this.glClass = glClass;
	}

	void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
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

	void setRelatedDocumentNumber(int relatedDocumentNumber) {
		this.relatedDocumentNumber = relatedDocumentNumber;
	}

	void setRelatedDocumentType(String relatedDocumentType) {
		this.relatedDocumentType = relatedDocumentType;
	}

	void setRelatedDocumentSequence(int relatedDocumentSequence) {
		this.relatedDocumentSequence = relatedDocumentSequence;
	}

	void setInventoryProcessingCode(String inventoryProcessingCode) {
		this.inventoryProcessingCode = inventoryProcessingCode;
	}

	void setInventoryTransactionType(String inventoryTransactionType) {
		this.inventoryTransactionType = inventoryTransactionType;
	}

	void setDocumentVoidStatus(String documentVoidStatus) {
		this.documentVoidStatus = documentVoidStatus;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	

}
