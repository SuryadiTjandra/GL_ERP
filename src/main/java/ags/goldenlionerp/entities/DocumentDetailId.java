package ags.goldenlionerp.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface DocumentDetailId extends DocumentId{
	
	int getSequence();
}
