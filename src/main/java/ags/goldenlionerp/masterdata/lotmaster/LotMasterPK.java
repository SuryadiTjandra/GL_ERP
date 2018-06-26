package ags.goldenlionerp.masterdata.lotmaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LotMasterPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="LTBUID")
	private String businessUnitId;
	
	@Column(name="LTINUM")
	private String itemCode;
	
	@Column(name="LTSNLOT")
	private String serialLotNo;
	
	@SuppressWarnings("unused")
	private LotMasterPK() {}
	
	public LotMasterPK(String businessUnitId, String itemCode, String serialLotNo) {
		super();
		this.itemCode = itemCode;
		this.businessUnitId = businessUnitId;
		this.serialLotNo = serialLotNo;
	}


	public String getBusinessUnitId() {
		return businessUnitId;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public String getSerialLotNo() {
		return serialLotNo;
	}

	void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setSerialLotNo(String serialLotNo) {
		this.serialLotNo = serialLotNo;
	}

	@Override
	public String toString() {
		return businessUnitId + "_" + itemCode + "_" + serialLotNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessUnitId == null) ? 0 : businessUnitId.hashCode());
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
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
		LotMasterPK other = (LotMasterPK) obj;
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
		if (serialLotNo == null) {
			if (other.serialLotNo != null)
				return false;
		} else if (!serialLotNo.equals(other.serialLotNo))
			return false;
		return true;
	}
	
	
}
