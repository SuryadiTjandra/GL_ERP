package ags.goldenlionerp.application.addresses.contact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContactPersonPK implements Serializable {

	private static final long serialVersionUID = 2439597064756479826L;

	@Column(name="CIANUM")
	private String addressNumber;
	
	@Column(name="CISEQ1")
	private int sequence;
	
	@SuppressWarnings("unused")
	private ContactPersonPK() {}
	
	public ContactPersonPK(String addressNumber, int sequence) {
		super();
		this.addressNumber = addressNumber;
		this.sequence = sequence;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public int getSequence() {
		return sequence;
	}

	@Override
	public String toString() {
		return addressNumber + "_" + sequence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
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
		ContactPersonPK other = (ContactPersonPK) obj;
		if (addressNumber == null) {
			if (other.addressNumber != null)
				return false;
		} else if (!addressNumber.equals(other.addressNumber))
			return false;
		if (sequence != other.sequence)
			return false;
		return true;
	}
	
	
}
