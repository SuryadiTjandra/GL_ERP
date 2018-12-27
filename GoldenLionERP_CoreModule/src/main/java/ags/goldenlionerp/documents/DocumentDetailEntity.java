package ags.goldenlionerp.documents;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DocumentDetailEntity<ID extends DocumentDetailId> extends DocumentEntity<ID> {

}
