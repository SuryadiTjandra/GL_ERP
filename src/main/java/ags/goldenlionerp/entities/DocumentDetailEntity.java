package ags.goldenlionerp.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DocumentDetailEntity<ID extends DocumentDetailId> extends DocumentEntity<ID> {

}
