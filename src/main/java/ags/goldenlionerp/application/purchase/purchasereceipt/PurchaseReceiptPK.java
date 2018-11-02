package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentDetailId;

@Embeddable
public class PurchaseReceiptPK implements Serializable, DocumentDetailId{

	private static final long serialVersionUID = -6658403012584106513L;

	@Column(name="OVCOID")
	private String companyId;
	
	@Column(name="OVDOCNO")
	private int purchaseReceiptNumber;
	
	@Column(name="OVDOCTY")
	private String purchaseReceiptType;
	
	@Column(name="OVDOCSQ")
	private int sequence;

	@SuppressWarnings("unused")
	private PurchaseReceiptPK() {}
	
	public PurchaseReceiptPK(String companyId, int purchaseReceiptNumber, String purchaseReceiptType, int sequence) {
		super();
		this.companyId = companyId;
		this.purchaseReceiptNumber = purchaseReceiptNumber;
		this.purchaseReceiptType = purchaseReceiptType;
		this.sequence = sequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getPurchaseReceiptNumber() {
		return purchaseReceiptNumber;
	}

	public String getPurchaseReceiptType() {
		return purchaseReceiptType;
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
		result = prime * result + purchaseReceiptNumber;
		result = prime * result + ((purchaseReceiptType == null) ? 0 : purchaseReceiptType.hashCode());
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
		if (purchaseReceiptNumber != other.purchaseReceiptNumber)
			return false;
		if (purchaseReceiptType == null) {
			if (other.purchaseReceiptType != null)
				return false;
		} else if (!purchaseReceiptType.equals(other.purchaseReceiptType))
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
