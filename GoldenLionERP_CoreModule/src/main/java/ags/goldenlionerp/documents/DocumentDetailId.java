package ags.goldenlionerp.documents;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface DocumentDetailId extends DocumentId{
	
	int getSequence();
}
