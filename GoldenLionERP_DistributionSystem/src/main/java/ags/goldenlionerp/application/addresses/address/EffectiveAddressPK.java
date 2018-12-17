package ags.goldenlionerp.application.addresses.address;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EffectiveAddressPK implements Serializable{

	private static final long serialVersionUID = -9191234949136186273L;

	@Column(name="EAANUM")
	private String addressNumber;
	
	@Column(name="EARECID")
	private String recordId;
	
	@SuppressWarnings("unused")
	private EffectiveAddressPK () {}

	public EffectiveAddressPK(String addressNumber, String recordId) {
		super();
		this.addressNumber = addressNumber;
		this.recordId = recordId;
	}
	
	public EffectiveAddressPK(String addressNumber) {
		this(addressNumber, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public String getRecordId() {
		return recordId;
	}

	@Override
	public String toString() {
		return addressNumber + "_" + recordId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
		result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
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
		EffectiveAddressPK other = (EffectiveAddressPK) obj;
		if (addressNumber == null) {
			if (other.addressNumber != null)
				return false;
		} else if (!addressNumber.equals(other.addressNumber))
			return false;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		return true;
	}
	
	
}
