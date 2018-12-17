package ags.goldenlionerp.application.addresses.phone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class PhoneNumberPK implements Serializable {

	private static final long serialVersionUID = 1556673387407776886L;

	@Column(name="CMANUM")
	private String addressNumber;
	
	//this column isn't actually used??
	@Column(name="CMSEQ1")
	private int sequence1;
	
	@Column(name="CMSEQ2")
	private int sequence2;
	
	@SuppressWarnings("unused")
	private PhoneNumberPK() {}

	PhoneNumberPK(String addressNumber, int sequence1, int sequence2) {
		super();
		this.addressNumber = addressNumber;
		this.sequence1 = sequence1;
		this.sequence2 = sequence2;
	}
	
	@JsonCreator
	public PhoneNumberPK(
			@JsonProperty("addressNumber") String addressNumber, 
			@JsonProperty("sequence") int sequence) {
		this(addressNumber, 0, sequence);
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	int getSequence1() {
		return sequence1;
	}
	
	int getSequence2() {
		return sequence2;
	}

	public int getSequence() {
		return sequence2;
	}
	
	@Override
	public String toString() {
		return addressNumber + "_" + sequence1 + "_" + sequence2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
		result = prime * result + sequence1;
		result = prime * result + sequence2;
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
		PhoneNumberPK other = (PhoneNumberPK) obj;
		if (addressNumber == null) {
			if (other.addressNumber != null)
				return false;
		} else if (!addressNumber.equals(other.addressNumber))
			return false;
		if (sequence1 != other.sequence1)
			return false;
		if (sequence2 != other.sequence2)
			return false;
		return true;
	}
	
	
}
