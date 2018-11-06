package ags.goldenlionerp.application.itemstock.itemtransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.ar.invoice.VoidedAttributeConverter;
import ags.goldenlionerp.documents.DocumentDetailEntity;
import ags.goldenlionerp.entities.DatabaseEntityUtil;
import ags.goldenlionerp.entities.Voidable;

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
public class ItemTransaction extends DocumentDetailEntity<ItemTransactionPK> implements Voidable{

	@EmbeddedId @JsonUnwrapped
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
	private int documentYear;
	
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
	@Convert(converter=VoidedAttributeConverter.class)
	private Boolean voided = false;
	
	@Column(name="ITOBID")
	private String objectId;
	
	@Column(name="ITRECID")
	private String recordId;

	private ItemTransaction() {}
	
	private ItemTransaction(Builder builder) {
		this.pk = builder.pk;
		this.businessUnitId = builder.businessUnitId;
		this.batchNumber = builder.batchNumber;
		this.batchType = builder.batchType;
		setDocumentDate(builder.documentDateTime.toLocalDate());
		this.documentTime = DatabaseEntityUtil.toTimeString(builder.documentDateTime);
		this.glDate = builder.glDate;
		this.itemCode = builder.itemCode;
		this.locationId = builder.locationId;
		this.serialLotNo = builder.serialLotNo;
		this.itemDescription = builder.itemDescription;
		this.description = builder.description;
		this.quantity = builder.quantity;
		this.unitOfMeasure = builder.unitOfMeasure;
		this.unitConversionFactor = builder.unitConversionFactor;
		this.primaryTransactionQuantity = builder.primaryTransactionQuantity;
		this.primaryUnitOfMeasure = builder.primaryUnitOfMeasure;
		this.secondaryTransactionQuantity = builder.secondaryTransactionQuantity;
		this.secondaryUnitOfMeasure = builder.secondaryUnitOfMeasure;
		this.unitCost = builder.unitCost;
		this.extendedCost = builder.extendedCost;
		this.fromOrTo = builder.fromOrTo;
		this.businessPartnerId = builder.businessPartnerId;
		this.businessPartnerSearchType = builder.businessPartnerSearchType;
		this.batchPostingStatus = builder.batchPostingStatus;
		this.glClass = builder.glClass;
		this.expiredDate = builder.expiredDate;
		this.orderNumber = builder.orderNumber;
		this.orderType = builder.orderType;
		this.orderSequence = builder.orderSequence;
		this.relatedDocumentNumber = builder.relatedDocumentNumber;
		this.relatedDocumentType = builder.relatedDocumentType;
		this.relatedDocumentSequence = builder.relatedDocumentSequence;
		this.inventoryProcessingCode = builder.inventoryProcessingCode;
		this.inventoryTransactionType = builder.inventoryTransactionType;
		this.objectId = builder.objectId;
		this.recordId = builder.recordId;
	}
	
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

	public int getDocumentYear() {
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

	public boolean isVoided1() {
		return voided;
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

	void setDocumentDate(LocalDate documentDate) {
		this.documentDate = documentDate;
		this.documentYear = documentDate.getYear();
		this.documentMonth = documentDate.getMonth();
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

	void setVoided(boolean voided) {
		this.voided = voided;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * Creates builder to build {@link ItemTransaction}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ItemTransaction}.
	 */
	public static final class Builder {
		private ItemTransactionPK pk;
		private String businessUnitId;
		private int batchNumber;
		private String batchType;
		private LocalDateTime documentDateTime;
		private LocalDate glDate;
		private String itemCode;
		private String locationId;
		private String serialLotNo;
		private String itemDescription;
		private String description;
		private BigDecimal quantity;
		private String unitOfMeasure;
		private BigDecimal unitConversionFactor;
		private BigDecimal primaryTransactionQuantity;
		private String primaryUnitOfMeasure;
		private BigDecimal secondaryTransactionQuantity;
		private String secondaryUnitOfMeasure;
		private BigDecimal unitCost;
		private BigDecimal extendedCost;
		private String fromOrTo;
		private String businessPartnerId;
		private String businessPartnerSearchType;
		private String batchPostingStatus;
		private String glClass;
		private LocalDate expiredDate;
		private int orderNumber;
		private String orderType;
		private int orderSequence;
		private int relatedDocumentNumber;
		private String relatedDocumentType;
		private int relatedDocumentSequence;
		private String inventoryProcessingCode;
		private String inventoryTransactionType;
		private String objectId;
		private String recordId;

		private Builder() {
		}

		public Builder(ItemTransactionPK pk) {
			this.pk = pk;
		}

		public Builder businessUnitId(String businessUnitId) {
			this.businessUnitId = businessUnitId;
			return this;
		}

		public Builder batchNumber(int batchNumber) {
			this.batchNumber = batchNumber;
			return this;
		}

		public Builder batchType(String batchType) {
			this.batchType = batchType;
			return this;
		}

		public Builder documentDateTime(LocalDateTime documentDate) {
			this.documentDateTime = documentDate;
			return this;
		}

		public Builder glDate(LocalDate glDate) {
			this.glDate = glDate;
			return this;
		}

		public Builder itemCode(String itemCode) {
			this.itemCode = itemCode;
			return this;
		}

		public Builder locationId(String locationId) {
			this.locationId = locationId;
			return this;
		}

		public Builder serialLotNo(String serialLotNo) {
			this.serialLotNo = serialLotNo;
			return this;
		}

		public Builder itemDescription(String itemDescription) {
			this.itemDescription = itemDescription;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder quantity(BigDecimal quantity) {
			this.quantity = quantity;
			return this;
		}

		public Builder unitOfMeasure(String unitOfMeasure) {
			this.unitOfMeasure = unitOfMeasure;
			return this;
		}

		public Builder unitConversionFactor(BigDecimal unitConversionFactor) {
			this.unitConversionFactor = unitConversionFactor;
			return this;
		}

		public Builder primaryTransactionQuantity(BigDecimal primaryTransactionQuantity) {
			this.primaryTransactionQuantity = primaryTransactionQuantity;
			return this;
		}

		public Builder primaryUnitOfMeasure(String primaryUnitOfMeasure) {
			this.primaryUnitOfMeasure = primaryUnitOfMeasure;
			return this;
		}

		public Builder secondaryTransactionQuantity(BigDecimal secondaryTransactionQuantity) {
			this.secondaryTransactionQuantity = secondaryTransactionQuantity;
			return this;
		}

		public Builder secondaryUnitOfMeasure(String secondaryUnitOfMeasure) {
			this.secondaryUnitOfMeasure = secondaryUnitOfMeasure;
			return this;
		}

		public Builder unitCost(BigDecimal unitCost) {
			this.unitCost = unitCost;
			return this;
		}

		public Builder extendedCost(BigDecimal extendedCost) {
			this.extendedCost = extendedCost;
			return this;
		}

		public Builder fromOrTo(String fromOrTo) {
			this.fromOrTo = fromOrTo;
			return this;
		}

		public Builder businessPartnerId(String businessPartnerId) {
			this.businessPartnerId = businessPartnerId;
			return this;
		}

		public Builder businessPartnerSearchType(String businessPartnerSearchType) {
			this.businessPartnerSearchType = businessPartnerSearchType;
			return this;
		}

		public Builder batchPostingStatus(String batchPostingStatus) {
			this.batchPostingStatus = batchPostingStatus;
			return this;
		}

		public Builder glClass(String glClass) {
			this.glClass = glClass;
			return this;
		}

		public Builder expiredDate(LocalDate expiredDate) {
			this.expiredDate = expiredDate;
			return this;
		}

		public Builder orderNumber(int orderNumber) {
			this.orderNumber = orderNumber;
			return this;
		}

		public Builder orderType(String orderType) {
			this.orderType = orderType;
			return this;
		}

		public Builder orderSequence(int orderSequence) {
			this.orderSequence = orderSequence;
			return this;
		}

		public Builder relatedDocumentNumber(int relatedDocumentNumber) {
			this.relatedDocumentNumber = relatedDocumentNumber;
			return this;
		}

		public Builder relatedDocumentType(String relatedDocumentType) {
			this.relatedDocumentType = relatedDocumentType;
			return this;
		}

		public Builder relatedDocumentSequence(int relatedDocumentSequence) {
			this.relatedDocumentSequence = relatedDocumentSequence;
			return this;
		}

		public Builder inventoryProcessingCode(String inventoryProcessingCode) {
			this.inventoryProcessingCode = inventoryProcessingCode;
			return this;
		}

		public Builder inventoryTransactionType(String inventoryTransactionType) {
			this.inventoryTransactionType = inventoryTransactionType;
			return this;
		}

		public Builder objectId(String objectId) {
			this.objectId = objectId;
			return this;
		}

		public Builder recordId(String recordId) {
			this.recordId = recordId;
			return this;
		}

		public ItemTransaction build() {
			return new ItemTransaction(this);
		}
	}

	@Override
	public boolean isVoided() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
