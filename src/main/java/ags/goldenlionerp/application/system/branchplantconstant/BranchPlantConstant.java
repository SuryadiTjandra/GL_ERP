package ags.goldenlionerp.application.system.branchplantconstant;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ags.goldenlionerp.application.system.businessunit.BusinessUnit;
import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0022")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="BCUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="BCDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="BCTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="BCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="BCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="BCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="BCCID")),
})
public class BranchPlantConstant extends DatabaseEntity<String> {

	@Id
	@Column(name="BCBUID")
	private String branchCode = "";
	
	@OneToOne(optional=false) //should be one-to-one but fake many-to-one to allow lazy load
	//@PrimaryKeyJoinColumn(name="BCBUID", referencedColumnName="BNBUID")
	@JoinColumn(name="BCBUID", insertable=false, updatable=false)
	private BusinessUnit branch;
	
	@Column(name="BCANUM")
	private String branchAddressCode = "";
	
	@Column(name="BCPUCM")
	private String purchaseCostMethod = "";
	
	@Column(name="BCSICM")
	private String salesInventoryCostMethod = "";
	
	@Column(name="BCITGL")
	private Boolean interfaceToGL;
	
	@Column(name="BCCTM")
	private String commitmentMethod = "";
	
	@Column(name="BCILC")
	private Boolean inventoryLotCreation;
	
	@Column(name="BCLCTL")
	private Boolean locationControl;
	
	@Column(name="BCWCTL")
	private Boolean warehouseControl;
	
	@Column(name="BCXRT")
	private String itemCrossReferenceType = "";
	
	@Column(name="BCXRT2")
	private String itemCrossReferenceType2 = "";

	public String getBranchCode() {
		return branchCode;
	}

	public BusinessUnit getBranch() {
		return branch;
	}

	public String getBranchAddressCode() {
		return branchAddressCode;
	}

	public String getPurchaseCostMethod() {
		return purchaseCostMethod;
	}

	public String getSalesInventoryCostMethod() {
		return salesInventoryCostMethod;
	}

	public Boolean getInterfaceToGL() {
		return interfaceToGL;
	}

	public String getCommitmentMethod() {
		return commitmentMethod;
	}

	public Boolean getInventoryLotCreation() {
		return inventoryLotCreation;
	}

	public Boolean getLocationControl() {
		return locationControl;
	}

	public Boolean getWarehouseControl() {
		return warehouseControl;
	}

	public String getItemCrossReferenceType() {
		return itemCrossReferenceType;
	}

	public String getItemCrossReferenceType2() {
		return itemCrossReferenceType2;
	}

	void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	void setBranch(BusinessUnit branch) {
		this.branch = branch;
	}

	void setBranchAddressCode(String branchAddressCode) {
		this.branchAddressCode = branchAddressCode;
	}

	void setPurchaseCostMethod(String purchaseCostMethod) {
		this.purchaseCostMethod = purchaseCostMethod;
	}

	void setSalesInventoryCostMethod(String salesInventoryCostMethod) {
		this.salesInventoryCostMethod = salesInventoryCostMethod;
	}

	void setInterfaceToGL(Boolean interfaceToGL) {
		this.interfaceToGL = interfaceToGL;
	}

	void setCommitmentMethod(String commitmentMethod) {
		this.commitmentMethod = commitmentMethod;
	}

	void setInventoryLotCreation(Boolean inventoryLotCreation) {
		this.inventoryLotCreation = inventoryLotCreation;
	}

	void setLocationControl(Boolean locationControl) {
		this.locationControl = locationControl;
	}

	void setWarehouseControl(Boolean warehouseControl) {
		this.warehouseControl = warehouseControl;
	}

	void setItemCrossReferenceType(String itemCrossReferenceType) {
		this.itemCrossReferenceType = itemCrossReferenceType;
	}

	void setItemCrossReferenceType2(String itemCrossReferenceType2) {
		this.itemCrossReferenceType2 = itemCrossReferenceType2;
	}

	@Override
	public String getId() {
		return getBranchCode();
	}


}
