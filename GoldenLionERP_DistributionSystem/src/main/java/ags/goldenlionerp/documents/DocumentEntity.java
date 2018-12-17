package ags.goldenlionerp.documents;

import javax.persistence.MappedSuperclass;

import ags.goldenlionerp.entities.DatabaseEntity;

@MappedSuperclass
public abstract class DocumentEntity<ID extends DocumentId> extends DatabaseEntity<ID>{

	public abstract ID getPk();
}
