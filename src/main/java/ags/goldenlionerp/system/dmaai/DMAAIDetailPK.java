package ags.goldenlionerp.system.dmaai;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DMAAIDetailPK implements Serializable {

	private static final long serialVersionUID = 4519388568030794134L;

	@Column(name="MLDMNO")
	private int dmaaiNo;
	
	@Column(name="MLCOID")
	private String companyId;
	
	@Column(name="MLDOCTY")
	private String documentType;
	
	@Column(name="MLDOCOTY")
	private String documentOrderType;
	
	@Column(name="MLGLCLS")
	private String glClass;
	
	@Column(name="MLCSTY")
	private String manufacturingCostType;

	@SuppressWarnings("unused")
	private DMAAIDetailPK() {}
	
	public DMAAIDetailPK(int dmaaiNo, String companyId, String documentType, String documentOrderType, String glClass,
			String manufacturingCostType) {
		super();
		this.dmaaiNo = dmaaiNo;
		this.companyId = companyId;
		this.documentType = documentType;
		this.documentOrderType = documentOrderType;
		this.glClass = glClass;
		this.manufacturingCostType = manufacturingCostType;
	}

	public int getDmaaiNo() {
		return dmaaiNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public String getDocumentOrderType() {
		return documentOrderType;
	}

	public String getGlClass() {
		return glClass;
	}

	public String getManufacturingCostType() {
		return manufacturingCostType;
	}

	@Override
	public String toString() {
		return String.join("_", 
				String.valueOf(dmaaiNo), 
				companyId, 
				documentType, 
				documentOrderType, 
				glClass, 
				manufacturingCostType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + dmaaiNo;
		result = prime * result + ((documentOrderType == null) ? 0 : documentOrderType.hashCode());
		result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
		result = prime * result + ((glClass == null) ? 0 : glClass.hashCode());
		result = prime * result + ((manufacturingCostType == null) ? 0 : manufacturingCostType.hashCode());
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
		DMAAIDetailPK other = (DMAAIDetailPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (dmaaiNo != other.dmaaiNo)
			return false;
		if (documentOrderType == null) {
			if (other.documentOrderType != null)
				return false;
		} else if (!documentOrderType.equals(other.documentOrderType))
			return false;
		if (documentType == null) {
			if (other.documentType != null)
				return false;
		} else if (!documentType.equals(other.documentType))
			return false;
		if (glClass == null) {
			if (other.glClass != null)
				return false;
		} else if (!glClass.equals(other.glClass))
			return false;
		if (manufacturingCostType == null) {
			if (other.manufacturingCostType != null)
				return false;
		} else if (!manufacturingCostType.equals(other.manufacturingCostType))
			return false;
		return true;
	}
	
	
}
