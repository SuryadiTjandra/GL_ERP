package ags.goldenlionerp.documents;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import ags.goldenlionerp.entities.QDocumentDetailEntity;
import ags.goldenlionerp.entities.QDocumentDetailId;

public abstract class DocumentDetailPredicates<T extends DocumentDetailEntity<ID>, ID extends DocumentDetailId>{
	
	protected Path<T> qPath;
	
	protected DocumentDetailPredicates(Path<T> qPath) {
		this.qPath = qPath;
	}
	
	public Predicate sameHeaderAs(T document) {
		return sameHeaderAs(document.getPk());
	}
	
	public Predicate sameHeaderAs(ID documentId) {
		QDocumentDetailId qDocId = new QDocumentDetailId(new QDocumentDetailEntity(qPath).pk);
		
		return qDocId.companyId.eq(documentId.getCompanyId())
				.and(qDocId.documentNumber.eq(documentId.getDocumentNumber()))
				.and(qDocId.documentType.eq(documentId.getDocumentType()));
	}
}
