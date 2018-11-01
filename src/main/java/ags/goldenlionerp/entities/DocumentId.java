package ags.goldenlionerp.entities;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface DocumentId extends Serializable{

	public String getCompanyId();
	
	public int getDocumentNumber();
	
	public String getDocumentType();
}
