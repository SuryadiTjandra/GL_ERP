package ags.goldenlionerp.masterdata.businessunit;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0022")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="BCUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="BCDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="BCTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="BCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="BCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="BCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="BCCID")),
})
public class BranchPlantConstant extends DatabaseEntity {

	@Id
	@Column(name="BCBUID")
	private String branchCode;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name="BCBUID", referencedColumnName="BNBUID")
	private BusinessUnit branch;
	
	@Column(name="BCANUM")
	private String branchAddressCode;
	
	@Column(name="BCPUCM")
	private String purchaseCostMethod;
	
	@Column(name="BCSICM")
	private String salesInventoryCostMethod;
	
	@Column(name="BCITGL")
	private String interfaceToGL;
	
	@Column(name="BCCTM")
	private String commitmentMethod;
	
	@Column(name="BCILC")
	private String inventoryLotCreation;
	
	@Column(name="BCLCTL")
	private String locationControl;
	
	@Column(name="BCWCTL")
	private String warehouseControl;
	
	@Column(name="BCXRT")
	private String itemCrossReferenceType;
	
	@Column(name="BCXRT2")
	private String itemCrossReferenceType2;

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

	public String getInterfaceToGL() {
		return interfaceToGL;
	}

	public String getCommitmentMethod() {
		return commitmentMethod;
	}

	public String getInventoryLotCreation() {
		return inventoryLotCreation;
	}

	public String getLocationControl() {
		return locationControl;
	}

	public String getWarehouseControl() {
		return warehouseControl;
	}

	public String getItemCrossReferenceType() {
		return itemCrossReferenceType;
	}

	public String getItemCrossReferenceType2() {
		return itemCrossReferenceType2;
	}
	
	
}
