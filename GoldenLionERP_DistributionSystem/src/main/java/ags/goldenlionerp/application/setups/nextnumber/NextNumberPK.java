package ags.goldenlionerp.application.setups.nextnumber;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NextNumberPK implements Serializable{

	private static final long serialVersionUID = 9101638779496677283L;

	@Column(name="NNCOID")
	private String companyId;
	
	@Column(name="NNDOCBTY")
	private String documentOrBatchType;
	
	@Column(name="NNYR")
	private int year;
	
	@Column(name="NNMO")
	private int month;
	
	@SuppressWarnings("unused") //for Jackson and JPA construction
	private NextNumberPK() {}

	public NextNumberPK(String companyId, String documentOrBatchType, int year, int month) {
		super();
		this.companyId = companyId;
		this.documentOrBatchType = documentOrBatchType;
		this.year = year;
		this.month = month;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDocumentOrBatchType() {
		return documentOrBatchType;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}
	
	@Override
	public String toString() {
		return new NextNumberIdConverter().toRequestId(this, NextNumberPK.class);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((documentOrBatchType == null) ? 0 : documentOrBatchType.hashCode());
		result = prime * result + month;
		result = prime * result + year;
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
		NextNumberPK other = (NextNumberPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (documentOrBatchType == null) {
			if (other.documentOrBatchType != null)
				return false;
		} else if (!documentOrBatchType.equals(other.documentOrBatchType))
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	
}
