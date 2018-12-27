package ags.goldenlionerp.application.sales.salesorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentDetailId;

@Embeddable
public class SalesDetailPK implements Serializable, DocumentDetailId {

	private static final long serialVersionUID = 4164982552219444939L;

	@Column(name="SDCOID")
	private String companyId;
	
	@Column(name="SDDOCONO")
	private int salesOrderNumber;
	
	@Column(name="SDDOCOTY")
	private String salesOrderType;
	
	@Column(name="SDDOCOSQ")
	private int salesOrderSequence;

	@SuppressWarnings("unused")
	private SalesDetailPK() {}
	
	public SalesDetailPK(String companyId, int salesOrderNumber, String salesOrderType, int salesOrderSequence) {
		super();
		this.companyId = companyId;
		this.salesOrderNumber = salesOrderNumber;
		this.salesOrderType = salesOrderType;
		this.salesOrderSequence = salesOrderSequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public String getSalesOrderType() {
		return salesOrderType;
	}

	public int getSalesOrderSequence() {
		return salesOrderSequence;
	}
	
	@Override
	public String toString() {
		return String.join("_", companyId, String.valueOf(salesOrderNumber), salesOrderType, String.valueOf(salesOrderSequence));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + salesOrderNumber;
		result = prime * result + salesOrderSequence;
		result = prime * result + ((salesOrderType == null) ? 0 : salesOrderType.hashCode());
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
		SalesDetailPK other = (SalesDetailPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (salesOrderNumber != other.salesOrderNumber)
			return false;
		if (salesOrderSequence != other.salesOrderSequence)
			return false;
		if (salesOrderType == null) {
			if (other.salesOrderType != null)
				return false;
		} else if (!salesOrderType.equals(other.salesOrderType))
			return false;
		return true;
	}

	@Override
	public int getSequence() {
		return getSalesOrderSequence();
	}

	@Override
	public int getDocumentNumber() {
		return getSalesOrderNumber();
	}

	@Override
	public String getDocumentType() {
		return getSalesOrderType();
	}
	
	
}
