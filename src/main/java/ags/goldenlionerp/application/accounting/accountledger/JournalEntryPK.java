package ags.goldenlionerp.application.accounting.accountledger;

import java.io.Serializable;

public class JournalEntryPK implements Serializable{

	private static final long serialVersionUID = 9040760369880149839L;

	private String companyId;
	
	private int documentNumber;
	
	private String documentType;
	
	private String ledgerType;

	public JournalEntryPK(String companyId, int documentNumber, String documentType, String ledgerType) {
		super();
		this.companyId = companyId;
		this.documentNumber = documentNumber;
		this.documentType = documentType;
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
	
	public String getLedgerType() {
		return ledgerType;
	}
	
	@Override
	public String toString() {
		return new JournalEntryIdConverter().toRequestId(this, JournalEntryPK.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + documentNumber;
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
		JournalEntryPK other = (JournalEntryPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (documentNumber != other.documentNumber)
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
