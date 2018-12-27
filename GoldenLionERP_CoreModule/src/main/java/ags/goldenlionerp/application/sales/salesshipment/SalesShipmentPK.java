package ags.goldenlionerp.application.sales.salesshipment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentDetailId;

@Embeddable
public class SalesShipmentPK implements DocumentDetailId{

	private static final long serialVersionUID = 8881483145733255664L;

	@Column(name="SLCOID")
	private String companyId;
	
	@Column(name="SLDOCNO")
	private int documentNumber;
	
	@Column(name="SLDOCTY")
	private String documentType;
	
	@Column(name="SLDOCSQ")
	private int sequence;

	@SuppressWarnings("unused")
	private SalesShipmentPK() {}
	
	public SalesShipmentPK(String companyId, int documentNumber, String documentType, int sequence) {
		this.companyId = companyId;
		this.documentNumber = documentNumber;
		this.documentType = documentType;
		this.sequence = sequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getDocumentNumber() {
		return documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public int getSequence() {
		return sequence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + documentNumber;
		result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
		result = prime * result + sequence;
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
		SalesShipmentPK other = (SalesShipmentPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (documentNumber != other.documentNumber)
			return false;
		if (documentType == null) {
			if (other.documentType != null)
				return false;
		} else if (!documentType.equals(other.documentType))
			return false;
		if (sequence != other.sequence)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new SalesShipmentIdConverter().toRequestId(this, SalesShipment.class);
	}

}
