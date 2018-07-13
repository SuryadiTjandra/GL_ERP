package ags.goldenlionerp.masterdata.location;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import ags.goldenlionerp.application.system.businessunit.BusinessUnit;
import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T4100")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="LMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="LMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="LMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="LMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="LMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="LMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="LMCID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="LMDTLS"))
})
public class LocationMaster extends SynchronizedDatabaseEntityImpl<LocationMasterPK> {

	@EmbeddedId
	private LocationMasterPK pk;
	
	@Column(name="LMWHC", updatable=false)
	private String warehouseCode = "";
	
	@Column(name="LMAISLE", updatable=false)
	private String aisle = "";
	
	@Column(name="LMROW", updatable=false)
	private String row = "";
	
	@Column(name="LMCOL", updatable=false)
	private String column = "";
	
	@Column(name="LMDESA1")
	private String description = "";
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="LMBUID", insertable=false, updatable=false)
	private BusinessUnit businessUnit;
	
	//a field to temporarily store the buId before persisting
	@Transient
	private String tempBuId;
	
	private boolean isPersisted() {
		return pk != null;
	}
	
	@PrePersist
	void finalizePk() {
		//PK is instantiated only when persisting, and can't be changed
		this.pk = new LocationMasterPK(
					tempBuId, 
					locationId(warehouseCode, aisle, row, column)
				);
	}
	
	public LocationMasterPK getPk() {
		return pk;
	}
	
	public String getBusinessUnitId() {
		return isPersisted() ? pk.getBusinessUnitId() : tempBuId;
	}

	public String getLocationId() {
		return isPersisted() ? pk.getLocationId() : locationId(warehouseCode, aisle, row, column);
	}
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public String getAisle() {
		return aisle;
	}
	public String getRow() {
		return row;
	}
	public String getColumn() {
		return column;
	}
	public String getDescription() {
		return description;
	}
	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}
	
	
	void setPk(LocationMasterPK pk) {
		this.pk = pk;
	}
	
	void setBusinessUnitId(String businessUnitId) {
		this.tempBuId = businessUnitId;
	}
	
	void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	void setAisle(String aisle) {
		this.aisle = aisle;
	}

	void setRow(String row) {
		this.row = row;
	}

	void setColumn(String column) {
		this.column = column;
	}

	void setDescription(String description) {
		this.description = description;
	}
	
	public static String locationId(String warehouseCode, String aisle, String row, String column) {
		StringBuilder sb = new StringBuilder(warehouseCode);
		
		if (aisle == null || aisle.isEmpty()) return sb.toString();		
		sb.append(".").append(aisle);
		
		if (row == null || row.isEmpty()) return sb.toString();		
		sb.append(".").append(row);
		
		if (column == null || column.isEmpty()) return sb.toString();		
		sb.append(column);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "ID: " + (isPersisted() ? pk.toString() : getBusinessUnitId() + "_" + getLocationId());
	}

	@Override
	public LocationMasterPK getId() {
		return getPk();
	}
	
}
