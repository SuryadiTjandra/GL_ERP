package ags.goldenlionerp.application.addresses.bankaccount;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.util.BeanFinder;

@Embeddable
public class BankAccountPK implements Serializable{

	private static final long serialVersionUID = 1586409804519148181L;

	@Column(name="BAANUM")
	private String addressNumber;
	
	@Column(name="BABANKID")
	private String bankId;
	
	@Column(name="BABANKAC")
	private String bankAccountNumber;

	@SuppressWarnings("unused")
	private BankAccountPK() {}
	
	public BankAccountPK(String addressNumber, String bankId, String bankAccountNumber) {
		super();
		this.addressNumber = addressNumber;
		this.bankId = bankId;
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public String getBankId() {
		return bankId;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	@Override
	public String toString() {
		BankAccountIdConverter conv = BeanFinder.findBean(BankAccountIdConverter.class);
		return conv.toRequestId(this, BankAccountPK.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
		result = prime * result + ((bankAccountNumber == null) ? 0 : bankAccountNumber.hashCode());
		result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
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
		BankAccountPK other = (BankAccountPK) obj;
		if (addressNumber == null) {
			if (other.addressNumber != null)
				return false;
		} else if (!addressNumber.equals(other.addressNumber))
			return false;
		if (bankAccountNumber == null) {
			if (other.bankAccountNumber != null)
				return false;
		} else if (!bankAccountNumber.equals(other.bankAccountNumber))
			return false;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		return true;
	}
	
	
	
}
