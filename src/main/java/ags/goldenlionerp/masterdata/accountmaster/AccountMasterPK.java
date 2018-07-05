package ags.goldenlionerp.masterdata.accountmaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountMasterPK implements Serializable{
	
	private static final long serialVersionUID = -8987357397477346754L;

	@Column(name="AMCOID", updatable=false)
	private String companyId;
	
	@Column(name="AMBUID", updatable=false)
	private String businessUnitId;
	
	@Column(name="AMOBJ", updatable=false)
	private String objectAccountCode;
	
	@Column(name="AMSUB", updatable=false)
	private String subsidiaryCode = "";

	@SuppressWarnings("unused")
	private AccountMasterPK() {}
	
	public AccountMasterPK(String companyId, String businessUnitId, String objectAccountCode, String subsidiaryCode) {
		super();
		this.companyId = companyId;
		this.businessUnitId = businessUnitId;
		this.objectAccountCode = objectAccountCode;
		this.subsidiaryCode = subsidiaryCode;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public String getObjectAccountCode() {
		return objectAccountCode;
	}

	public String getSubsidiaryCode() {
		return subsidiaryCode;
	}
	
	@Override
	public String toString() {
		return companyId + "." + businessUnitId + "." + objectAccountCode + "." + subsidiaryCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessUnitId == null) ? 0 : businessUnitId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((objectAccountCode == null) ? 0 : objectAccountCode.hashCode());
		result = prime * result + ((subsidiaryCode == null) ? 0 : subsidiaryCode.hashCode());
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
		AccountMasterPK other = (AccountMasterPK) obj;
		if (businessUnitId == null) {
			if (other.businessUnitId != null)
				return false;
		} else if (!businessUnitId.equals(other.businessUnitId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (objectAccountCode == null) {
			if (other.objectAccountCode != null)
				return false;
		} else if (!objectAccountCode.equals(other.objectAccountCode))
			return false;
		if (subsidiaryCode == null) {
			if (other.subsidiaryCode != null)
				return false;
		} else if (!subsidiaryCode.equals(other.subsidiaryCode))
			return false;
		return true;
	}
	
	
}
