package ags.goldenlionerp.masterdata.itembranchinfo;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;
import ags.goldenlionerp.masterdata.businessunit.BusinessUnit;
import ags.goldenlionerp.masterdata.itemmaster.ItemMaster;

@Entity
@Table(name="T41011")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="IBUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="IBDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="IBTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="IBUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="IBDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="IBTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="IBCID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="IBDTLS"))
})
public class ItemBranchInfo extends SynchronizedDatabaseEntityImpl {

	@EmbeddedId @JsonUnwrapped
	private ItemBranchInfoPK pk;
	
	@Column(name="IBSKTY")
	private String stockingType = "";
	
	@Column(name="IBGLCLS")
	private String glClass= "";
	
	@Column(name="IBLNTY")
	private String transactionType= "";
	
	@Column(name="IBROQ", precision=19, scale=5)
	private BigDecimal reorderQty = new BigDecimal(0);
	
	@Column(name="IBROP", precision=19, scale=5)
	private BigDecimal reorderPoint = new BigDecimal(0);
	
	@Column(name="IBMAXROQ", precision=19, scale=5)
	private BigDecimal maximumReorderQty = new BigDecimal(0);
	
	@Column(name="IBMINROQ", precision=19, scale=5)
	private BigDecimal minimumReorderQty = new BigDecimal(0);
	
	@Column(name="IBSFSTK", precision=19, scale=5)
	private BigDecimal safetyStock = new BigDecimal(0);
	
	@Column(name="IBCTM")
	private String commitmentMethod = "";
	
	@Column(name="IBILC")
	private Boolean inventoryLotCreation = false;
	
	@Column(name="IBSNR")
	private Boolean serialNumberRequired = false;
	
	@Column(name="IBLSC")
	private String lotStatusCode = "";
	
	@Column(name="IBIPG")
	private String itemPriceGroup = "";
	
	@Column(name="IBPLG")
	private String printerLocationGroup = "";
	
	@Embedded
	private ItemBranchRankingsAndParameters parameters = new ItemBranchRankingsAndParameters();
	
	@Embedded
	private ItemBranchDataGroups dataGroups = new ItemBranchDataGroups();
	
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="IBBUID", insertable=false, updatable=false)
	private BusinessUnit branch;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="IBINUM", insertable=false, updatable=false)
	private ItemMaster itemMaster;
	
	private ItemBranchInfo() {}

	public ItemBranchInfoPK getPk() {
		return pk;
	}

	public String getItemCode() {
		return pk.getItemCode();
	}
	
	public String getBranchCode() {
		return pk.getBranchCode();
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

	public BigDecimal getReorderQty() {
		return reorderQty;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getMaximumReorderQty() {
		return maximumReorderQty;
	}

	public BigDecimal getMinimumReorderQty() {
		return minimumReorderQty;
	}

	public BigDecimal getSafetyStock() {
		return safetyStock;
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

	public ItemBranchRankingsAndParameters getParameters() {
		return parameters;
	}

	public ItemBranchDataGroups getDataGroups() {
		return dataGroups;
	}

	public BusinessUnit getBranch() {
		return branch;
	}

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	void setPk(ItemBranchInfoPK pk) {
		this.pk = pk;
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

	void setReorderQty(BigDecimal reorderQty) {
		this.reorderQty = reorderQty;
	}

	void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	void setMaximumReorderQty(BigDecimal maximumReorderQty) {
		this.maximumReorderQty = maximumReorderQty;
	}

	void setMinimumReorderQty(BigDecimal minimumReorderQty) {
		this.minimumReorderQty = minimumReorderQty;
	}

	void setSafetyStock(BigDecimal safetyStock) {
		this.safetyStock = safetyStock;
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

	void setParameters(ItemBranchRankingsAndParameters parameters) {
		this.parameters = parameters;
	}

	void setDataGroups(ItemBranchDataGroups dataGroups) {
		this.dataGroups = dataGroups;
	}

	void setBranch(BusinessUnit branch) {
		this.branch = branch;
	}

	void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}
	

}
