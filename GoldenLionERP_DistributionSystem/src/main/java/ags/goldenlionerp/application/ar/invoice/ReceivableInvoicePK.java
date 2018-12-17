package ags.goldenlionerp.application.ar.invoice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReceivableInvoicePK implements Serializable {
	
	private static final long serialVersionUID = -8472735524287882692L;

	@Column(name="RICOID")
	private String companyId;
	
	@Column(name="RIDOCINO")
	private int invoiceNumber;
	
	@Column(name="RIDOCITY")
	private String invoiceType;
	
	@Column(name="RIDOCISQ")
	private int invoiceSequence;
	
	@SuppressWarnings("unused")
	private ReceivableInvoicePK() {}

	public ReceivableInvoicePK(String companyId, int invoiceNumber, String invoiceType, int invoiceSequence) {
		super();
		this.companyId = companyId;
		this.invoiceNumber = invoiceNumber;
		this.invoiceType = invoiceType;
		this.invoiceSequence = invoiceSequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public int getInvoiceSequence() {
		return invoiceSequence;
	}
	
	@Override
	public String toString() {
		return new ReceivableInvoiceIdConverter().toRequestId(this, this.getClass());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + invoiceNumber;
		result = prime * result + invoiceSequence;
		result = prime * result + ((invoiceType == null) ? 0 : invoiceType.hashCode());
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
		ReceivableInvoicePK other = (ReceivableInvoicePK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (invoiceNumber != other.invoiceNumber)
			return false;
		if (invoiceSequence != other.invoiceSequence)
			return false;
		if (invoiceType == null) {
			if (other.invoiceType != null)
				return false;
		} else if (!invoiceType.equals(other.invoiceType))
			return false;
		return true;
	}

	
	
	
}
