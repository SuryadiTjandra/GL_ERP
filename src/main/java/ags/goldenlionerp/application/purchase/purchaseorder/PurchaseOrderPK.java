package ags.goldenlionerp.application.purchase.purchaseorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentId;

@Embeddable
public class PurchaseOrderPK implements Serializable, DocumentId{

	private static final long serialVersionUID = 2160958370852181468L;

	@Column(name="OHCOID")
	private String companyId;
	
	@Column(name="OHDOCONO")
	private int purchaseOrderNumber;
	
	@Column(name="OHDOCOTY")
	private String purchaseOrderType;
	
	@SuppressWarnings("unused")
	private PurchaseOrderPK() {}
	
	public PurchaseOrderPK(String companyId, int purchaseOrderNumber, String purchaseOrderType) {
		super();
		this.companyId = companyId;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.purchaseOrderType = purchaseOrderType;
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
	
	public String toString() {
		return new PurchaseOrderIdConverter().toRequestId(this, PurchaseOrderPK.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + purchaseOrderNumber;
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
		PurchaseOrderPK other = (PurchaseOrderPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (purchaseOrderNumber != other.purchaseOrderNumber)
			return false;
		if (purchaseOrderType == null) {
			if (other.purchaseOrderType != null)
				return false;
		} else if (!purchaseOrderType.equals(other.purchaseOrderType))
			return false;
		return true;
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
