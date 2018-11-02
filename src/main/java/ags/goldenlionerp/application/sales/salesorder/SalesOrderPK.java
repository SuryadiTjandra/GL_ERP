package ags.goldenlionerp.application.sales.salesorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentId;

@Embeddable
public class SalesOrderPK implements Serializable, DocumentId{

	private static final long serialVersionUID = -7914577149475922263L;

	@Column(name="SHCOID")
	private String companyId;
	
	@Column(name="SHDOCONO")
	private int salesOrderNumber;
	
	@Column(name="SHDOCOTY")
	private String salesOrderType;
	
	@SuppressWarnings("unused")
	private SalesOrderPK() {}

	public SalesOrderPK(String companyId, int salesOrderNumber, String salesOrderType) {
		super();
		this.companyId = companyId;
		this.salesOrderNumber = salesOrderNumber;
		this.salesOrderType = salesOrderType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(int salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public String getSalesOrderType() {
		return salesOrderType;
	}

	public void setSalesOrderType(String salesOrderType) {
		this.salesOrderType = salesOrderType;
	}

	@Override
	public String toString() {
		return new SalesOrderIdConverter().toRequestId(this, SalesOrderPK.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + salesOrderNumber;
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
		SalesOrderPK other = (SalesOrderPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (salesOrderNumber != other.salesOrderNumber)
			return false;
		if (salesOrderType == null) {
			if (other.salesOrderType != null)
				return false;
		} else if (!salesOrderType.equals(other.salesOrderType))
			return false;
		return true;
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
