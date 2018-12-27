package ags.goldenlionerp.application.datacode;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DataCodePK implements Serializable{

	private static final long serialVersionUID = -88610523637871993L;

	@Column(name="DTPC")
	private String productCode;
	
	@Column(name="DTSC")
	private String systemCode;
	
	@Column(name="DTDC")
	private String dataCode;

	@SuppressWarnings("unused")
	private DataCodePK() {}
	
	public DataCodePK(String productCode, String systemCode, String dataCode) {
		super();
		this.productCode = productCode;
		this.systemCode = systemCode;
		this.dataCode = dataCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public String getDataCode() {
		return dataCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCode == null) ? 0 : dataCode.hashCode());
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((systemCode == null) ? 0 : systemCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataCodePK other = (DataCodePK) obj;
		if (dataCode == null) {
			if (other.dataCode != null)
				return false;
		} else if (!dataCode.equals(other.dataCode))
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (systemCode == null) {
			if (other.systemCode != null)
				return false;
		} else if (!systemCode.equals(other.systemCode))
			return false;
		return true;
	}
	
	
	
	
}
