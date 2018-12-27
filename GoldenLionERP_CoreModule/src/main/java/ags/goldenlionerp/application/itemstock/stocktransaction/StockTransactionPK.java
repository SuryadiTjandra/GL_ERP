package ags.goldenlionerp.application.itemstock.stocktransaction;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.documents.DocumentDetailId;

@Embeddable
public class StockTransactionPK implements DocumentDetailId{

	private static final long serialVersionUID = -6837408006366743522L;

	@Column(name="ITCOID")
	private String companyId;
	
	@Column(name="ITDOCNO")
	private int documentNumber;
	
	@Column(name="ITDOCTY")
	private String documentType;
	
	@Column(name="ITDOCSQ")
	private int sequence;

	@SuppressWarnings("unused")
	private StockTransactionPK() {
	}
	
	public StockTransactionPK(String companyId, int documentNumber, String documentType, int sequence) {
		super();
		this.companyId = companyId;
		this.documentNumber = documentNumber;
		this.documentType = documentType;
		this.sequence = sequence;
	}
	
	@Override
	public String toString() {
		return new StockTransactionIdConverter().toRequestId(this, StockTransaction.class);
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

	public int getSequence() {
		return sequence;
	}
	
	
}
