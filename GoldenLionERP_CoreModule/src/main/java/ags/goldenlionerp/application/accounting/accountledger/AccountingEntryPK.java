package ags.goldenlionerp.application.accounting.accountledger;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountingEntryPK implements Serializable{

	private static final long serialVersionUID = -3442666377908845506L;

	@Column(name="ALCOID")
	private String companyId;
	
	@Column(name="ALDOCNO")
	private int documentNumber;
	
	@Column(name="ALDOCTY")
	private String documentType;
	
	@Column(name="ALDOCSQ")
	private int documentSequence;
	
	@Column(name="ALLT")
	private String ledgerType;
	
	@SuppressWarnings("unused")
	private AccountingEntryPK() {}

	public AccountingEntryPK(String companyId, int documentNumber, String documentType, int documentSequence,
			String ledgerType) {
		super();
		this.companyId = companyId;
		this.documentNumber = documentNumber;
		this.documentType = documentType;
		this.documentSequence = documentSequence;
		this.ledgerType = ledgerType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public int getDocumentNumber() {
		return documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public int getDocumentSequence() {
		return documentSequence;
	}

	public String getLedgerType() {
		return ledgerType;
	}
	
	public String toString() {
		return new AccountingEntryIdConverter().toRequestId(this, AccountingEntryPK.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + documentNumber;
		result = prime * result + documentSequence;
		result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
		result = prime * result + ((ledgerType == null) ? 0 : ledgerType.hashCode());
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
		AccountingEntryPK other = (AccountingEntryPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (documentNumber != other.documentNumber)
			return false;
		if (documentSequence != other.documentSequence)
			return false;
		if (documentType == null) {
			if (other.documentType != null)
				return false;
		} else if (!documentType.equals(other.documentType))
			return false;
		if (ledgerType == null) {
			if (other.ledgerType != null)
				return false;
		} else if (!ledgerType.equals(other.ledgerType))
			return false;
		return true;
	}
	
	
}
