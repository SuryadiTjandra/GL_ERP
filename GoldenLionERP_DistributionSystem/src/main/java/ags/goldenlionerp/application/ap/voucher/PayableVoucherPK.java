package ags.goldenlionerp.application.ap.voucher;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PayableVoucherPK implements Serializable {

	private static final long serialVersionUID = 6221847723171122352L;

	@Column(name="PVCOID")
	private String companyId;
	
	@Column(name="PVDOCINO")
	private int voucherNumber;
	
	@Column(name="PVDOCITY")
	private String voucherType;
	
	@Column(name="PVDOCISQ")
	private int voucherSequence;

	@SuppressWarnings("unused")
	private PayableVoucherPK() {}
	
	public PayableVoucherPK(String companyId, int voucherNumber, String voucherType, int voucherSequence) {
		super();
		this.companyId = companyId;
		this.voucherNumber = voucherNumber;
		this.voucherType = voucherType;
		this.voucherSequence = voucherSequence;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getVoucherNumber() {
		return voucherNumber;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public int getVoucherSequence() {
		return voucherSequence;
	}
	
	public String toString() {
		return new PayableVoucherIDConverter().toRequestId(this, this.getClass());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + voucherNumber;
		result = prime * result + voucherSequence;
		result = prime * result + ((voucherType == null) ? 0 : voucherType.hashCode());
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
		PayableVoucherPK other = (PayableVoucherPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (voucherNumber != other.voucherNumber)
			return false;
		if (voucherSequence != other.voucherSequence)
			return false;
		if (voucherType == null) {
			if (other.voucherType != null)
				return false;
		} else if (!voucherType.equals(other.voucherType))
			return false;
		return true;
	}
	
	
}
