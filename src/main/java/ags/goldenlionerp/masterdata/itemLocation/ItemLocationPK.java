package ags.goldenlionerp.masterdata.itemLocation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemLocationPK implements Serializable {

	private static final long serialVersionUID = -6950720710391350287L;

	@Column(name="ILINUM")
	private String itemCode;
	
	@Column(name="ILBUID")
	private String businessUnitId;
	
	@Column(name="ILLOCID")
	private String locationId;
	
	@Column(name="ILSNLOT")
	private String serialLotNo;
	
	@SuppressWarnings("unused")
	private ItemLocationPK() {}

	public ItemLocationPK(String itemCode, String businessUnitId, String locationId, String serialLotNo) {
		super();
		this.itemCode = itemCode;
		this.businessUnitId = businessUnitId;
		this.locationId = locationId;
		this.serialLotNo = serialLotNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getSerialLotNo() {
		return serialLotNo;
	}
	
	@Override
	public String toString() {
		return String.join("_", itemCode, businessUnitId, locationId, serialLotNo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessUnitId == null) ? 0 : businessUnitId.hashCode());
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((serialLotNo == null) ? 0 : serialLotNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemLocationPK other = (ItemLocationPK) obj;
		if (businessUnitId == null) {
			if (other.businessUnitId != null)
				return false;
		} else if (!businessUnitId.equals(other.businessUnitId))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (serialLotNo == null) {
			if (other.serialLotNo != null)
				return false;
		} else if (!serialLotNo.equals(other.serialLotNo))
			return false;
		return true;
	}
	
	
	
}
