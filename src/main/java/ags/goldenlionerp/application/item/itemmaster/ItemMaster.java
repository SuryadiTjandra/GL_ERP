package ags.goldenlionerp.application.item.itemmaster;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ags.goldenlionerp.application.item.itemLocation.ItemLocation;
import ags.goldenlionerp.application.item.itembranchinfo.ItemBranchInfo;
import ags.goldenlionerp.application.item.lotmaster.LotMaster;
import ags.goldenlionerp.entities.TransactionSynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T4101")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="IMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="IMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="IMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="IMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="IMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="IMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="IMCID")),
	@AttributeOverride(name="lastTransactionDate", column=@Column(name="IMDTLT")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="IMDTLS")),
})
public class ItemMaster extends TransactionSynchronizedDatabaseEntityImpl<String>{

	@Id
	@Column(name="IMINUM")
	private String itemCode = "";
	
	@Column(name="IMINUMB")
	private String barcode = "";
	
	@Column(name="IMINUMS", updatable=false)
	private int itemCodeShort;
	
	@Column(name="IMDESB1")
	private String description = "";
	
	@Column(name="IMDESD1")
	private String descriptionLong = "";
	
	@Column(name="IMSKTY")
	private String stockingType = "";
	
	@Column(name="IMGLCLS")
	private String glClass = "";
	
	@Column(name="IMLNTY")
	private String transactionType = "";
	
	@Column(name="IMICL")
	private String inventoryCostLevel = "";
	
	@Column(name="IMSPL")
	private String salesPriceLevel = "";
	
	@Column(name="IMPPL")
	private String purchasingPriceLevel = "";
	
	@Column(name="IMCTM")
	private String commitmentMethod = "";
	
	@Column(name="IMILC")
	private Boolean inventoryLotCreation = false;
	
	@Column(name="IMSNR")
	private Boolean serialNumberRequired = false;
	
	@Column(name="IMLSC")
	private String lotStatusCode = "";
	
	@Column(name="IMIPG")
	private String itemPriceGroup = "";
	
	@Column(name="IMPLG")
	private String printerLocationGroup = "";
	
	@Column(name="IMVICT")
	private String variantItemControlType = "";
	
	@Column(name="IMINUMP")
	private String parentItemNumber = "";
	
	@Embedded
	private ItemUnitsOfMeasures unitsOfMeasure= new ItemUnitsOfMeasures();
	
	@Embedded
	private ItemRankingsAndParameters parameters = new ItemRankingsAndParameters();
	
	@Embedded
	private ItemDataGroups dataGroups = new ItemDataGroups();
	
	@OneToMany(mappedBy="itemMaster", fetch=FetchType.LAZY)
	private List<ItemBranchInfo> itemBranchInfos;
	
	@OneToMany(mappedBy="item", fetch=FetchType.LAZY)
	private List<ItemLocation> itemLocations;
	
	@OneToMany(mappedBy="item", fetch=FetchType.LAZY)
	private List<LotMaster> lots;

	public String getItemCode() {
		return itemCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public int getItemCodeShort() {
		return itemCodeShort;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionLong() {
		return descriptionLong;
	}

	public String getStockingType() {
		return stockingType;
	}

	public String getGlClass() {
		return glClass;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getInventoryCostLevel() {
		return inventoryCostLevel;
	}

	public String getSalesPriceLevel() {
		return salesPriceLevel;
	}

	public String getPurchasingPriceLevel() {
		return purchasingPriceLevel;
	}

	public String getCommitmentMethod() {
		return commitmentMethod;
	}

	public boolean isInventoryLotCreation() {
		return inventoryLotCreation;
	}

	public boolean isSerialNumberRequired() {
		return serialNumberRequired;
	}

	public String getLotStatusCode() {
		return lotStatusCode;
	}

	public String getItemPriceGroup() {
		return itemPriceGroup;
	}

	public String getPrinterLocationGroup() {
		return printerLocationGroup;
	}

	public String getVariantItemControlType() {
		return variantItemControlType;
	}

	public String getParentItemNumber() {
		return parentItemNumber;
	}

	public ItemUnitsOfMeasures getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public ItemRankingsAndParameters getParameters() {
		return parameters;
	}

	public ItemDataGroups getDataGroups() {
		return dataGroups;
	}

	void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	void setItemCodeShort(int itemCodeShort) {
		this.itemCodeShort = itemCodeShort;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
	}

	void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}

	void setGlClass(String glClass) {
		this.glClass = glClass;
	}

	void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	void setInventoryCostLevel(String inventoryCostLevel) {
		this.inventoryCostLevel = inventoryCostLevel;
	}

	void setSalesPriceLevel(String salesPriceLevel) {
		this.salesPriceLevel = salesPriceLevel;
	}

	void setPurchasingPriceLevel(String purchasingPriceLevel) {
		this.purchasingPriceLevel = purchasingPriceLevel;
	}

	void setCommitmentMethod(String commitmentMethod) {
		this.commitmentMethod = commitmentMethod;
	}

	void setInventoryLotCreation(boolean inventoryLotCreation) {
		this.inventoryLotCreation = inventoryLotCreation;
	}

	void setSerialNumberRequired(boolean serialNumberRequired) {
		this.serialNumberRequired = serialNumberRequired;
	}

	void setLotStatusCode(String lotStatusCode) {
		this.lotStatusCode = lotStatusCode;
	}

	void setItemPriceGroup(String itemPriceGroup) {
		this.itemPriceGroup = itemPriceGroup;
	}

	void setPrinterLocationGroup(String printerLocationGroup) {
		this.printerLocationGroup = printerLocationGroup;
	}

	void setVariantItemControlType(String variantItemControlType) {
		this.variantItemControlType = variantItemControlType;
	}

	void setParentItemNumber(String parentItemNumber) {
		this.parentItemNumber = parentItemNumber;
	}

	void setUnitsOfMeasure(ItemUnitsOfMeasures unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	void setParameters(ItemRankingsAndParameters parameters) {
		this.parameters = parameters;
	}

	void setDataGroups(ItemDataGroups dataGroups) {
		this.dataGroups = dataGroups;
	}

	public List<ItemBranchInfo> getItemBranchInfos() {
		return itemBranchInfos;
	}
	
	public List<ItemLocation> getItemLocations() {
		return itemLocations;
	}
	
	public List<LotMaster> getLots(){
		return lots;
	}

	@Override
	public String getId() {
		return getItemCode();
	}

}
