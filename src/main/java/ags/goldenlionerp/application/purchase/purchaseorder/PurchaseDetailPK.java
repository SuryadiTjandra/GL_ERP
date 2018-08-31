package ags.goldenlionerp.application.purchase.purchaseorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PurchaseDetailPK implements Serializable{

	private static final long serialVersionUID = -2198201452613072525L;

	@Column(name="ODCOID")
	private String companyId;
	
	@Column(name="ODDOCONO")
	private int purchaseOrderNumber;
	
	@Column(name="ODDOCOTY")
	private String purchaseOrderType;
	
	@Column(name="ODDOCOSQ")
	private String purchaseOrderSequence;

	
	@SuppressWarnings("unused")
	private PurchaseDetailPK() {}
	
	public PurchaseDetailPK(String companyId, int purchaseOrderNumber, String purchaseOrderType,
			String purchaseOrderSequence) {
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

	public String getPurchaseOrderSequence() {
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
		result = prime * result + ((purchaseOrderSequence == null) ? 0 : purchaseOrderSequence.hashCode());
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
		if (purchaseOrderSequence == null) {
			if (other.purchaseOrderSequence != null)
				return false;
		} else if (!purchaseOrderSequence.equals(other.purchaseOrderSequence))
			return false;
		if (purchaseOrderType == null) {
			if (other.purchaseOrderType != null)
				return false;
		} else if (!purchaseOrderType.equals(other.purchaseOrderType))
			return false;
		return true;
	}
	
	
}
