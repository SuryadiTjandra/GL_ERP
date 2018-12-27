package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentDetailId;

@Embeddable
@Access(AccessType.FIELD)
public class PurchaseReceiptPK implements Serializable, DocumentDetailId{

	private static final long serialVersionUID = -6658403012584106513L;

	@Column(name="OVCOID")
	private String companyId;
	
	@Column(name="OVDOCNO")
	private int documentNumber;
	
	@Column(name="OVDOCTY")
	private String documentType;
	
	@Column(name="OVDOCSQ")
	private int sequence;

	@SuppressWarnings("unused")
	private PurchaseReceiptPK() {}
	
	public PurchaseReceiptPK(String companyId, int purchaseReceiptNumber, String purchaseReceiptType, int sequence) {
		super();
		this.companyId = companyId;
		this.documentNumber = purchaseReceiptNumber;
		this.documentType = purchaseReceiptType;
		this.sequence = sequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getPurchaseReceiptNumber() {
		return documentNumber;
	}

	public String getPurchaseReceiptType() {
		return documentType;
	}

	public int getSequence() {
		return sequence;
	}
	
	public String toString() {
		return new PurchaseReceiptIdConverter().toRequestId(this, PurchaseReceiptPK.class);
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
		PurchaseReceiptPK other = (PurchaseReceiptPK) obj;
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
	public int getDocumentNumber() {
		return getPurchaseReceiptNumber();
	}

	@Override
	public String getDocumentType() {
		return getPurchaseReceiptType();
	}	
	
}
