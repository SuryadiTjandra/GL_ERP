package ags.goldenlionerp.entities;

import java.io.Serializable;

public interface DocumentId extends Serializable{

	public String getCompanyId();
	
	public int getDocumentNumber();
	
	public String getDocumentType();
}
