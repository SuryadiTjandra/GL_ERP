package ags.goldenlionerp.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DocumentEntity<ID extends DocumentId> extends DatabaseEntity<ID>{

	public abstract ID getPk();
}
