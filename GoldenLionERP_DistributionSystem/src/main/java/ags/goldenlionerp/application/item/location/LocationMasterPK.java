package ags.goldenlionerp.application.item.location;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LocationMasterPK implements Serializable {


	private static final long serialVersionUID = 5817968174553075780L;
	
	@Column(name="LMBUID")
	private String businessUnitId;
	@Column(name="LMLOCID")
	private String locationId;
	
	public LocationMasterPK(String businessUnitId, String locationId) {
		this.businessUnitId = businessUnitId;
		this.locationId = locationId;
	}
	
	@SuppressWarnings("unused")
	private LocationMasterPK() {}
	
	public String getBusinessUnitId() {
		return businessUnitId;
	}
	public String getLocationId() {
		return locationId;
	}
	
	@Override
	public String toString() {
		return businessUnitId + "_" + locationId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessUnitId == null) ? 0 : businessUnitId.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
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
		LocationMasterPK other = (LocationMasterPK) obj;
		if (businessUnitId == null) {
			if (other.businessUnitId != null)
				return false;
		} else if (!businessUnitId.equals(other.businessUnitId))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		return true;
	}
	
	
	
}
