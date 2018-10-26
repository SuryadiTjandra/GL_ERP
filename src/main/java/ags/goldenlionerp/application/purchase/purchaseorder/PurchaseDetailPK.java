package ags.goldenlionerp.application.purchase.purchaseorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.entities.DocumentDetailId;

@Embeddable
public class PurchaseDetailPK implements Serializable, DocumentDetailId{

	private static final long serialVersionUID = -2198201452613072525L;

	@Column(name="ODCOID")
	private String companyId;
	
	@Column(name="ODDOCONO")
	private int purchaseOrderNumber;
	
	@Column(name="ODDOCOTY")
	private String purchaseOrderType;
	
	@Column(name="ODDOCOSQ")
	private int purchaseOrderSequence;

	
	@SuppressWarnings("unused")
	private PurchaseDetailPK() {}
	
	public PurchaseDetailPK(String companyId, int purchaseOrderNumber, String purchaseOrderType,
			int purchaseOrderSequence) {
		super();
		this.companyId = companyId;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.purchaseOrderType = purchaseOrderType;
		this.purchaseOrderSequence = purchaseOrderSequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public String getPurchaseOrderType() {
		return purchaseOrderType;
	}

	public int getPurchaseOrderSequence() {
		return purchaseOrderSequence;
	}

	@Override
	public String toString() {
		return String.join("_", companyId, String.valueOf(purchaseOrderNumber), purchaseOrderType, String.valueOf(purchaseOrderSequence));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + purchaseOrderNumber;
		result = prime * result + purchaseOrderSequence;
		result = prime * result + ((purchaseOrderType == null) ? 0 : purchaseOrderType.hashCode());
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
		PurchaseDetailPK other = (PurchaseDetailPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (purchaseOrderNumber != other.purchaseOrderNumber)
			return false;
		if (purchaseOrderSequence != other.purchaseOrderSequence)
			return false;
		if (purchaseOrderType == null) {
			if (other.purchaseOrderType != null)
				return false;
		} else if (!purchaseOrderType.equals(other.purchaseOrderType))
			return false;
		return true;
	}

	@Override
	public int getSequence() {
		return getPurchaseOrderSequence();
	}

	@Override
	public int getDocumentNumber() {
		return getPurchaseOrderNumber();
	}

	@Override
	public String getDocumentType() {
		return getPurchaseOrderType();
	}
	
	
}
