package ags.goldenlionerp.application.item.uomconversion.standard;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StandardUomConversionPK implements Serializable{

	private static final long serialVersionUID = -5527609522675152900L;

	@Column(name="UCUOM")
	private String uomFrom;
	
	@Column(name="UCUOMR")
	private String uomTo;
	
	@SuppressWarnings("unused")
	private StandardUomConversionPK() {}
	
	public StandardUomConversionPK(String uomFrom, String uomTo) {
		super();
		this.uomFrom = uomFrom;
		this.uomTo = uomTo;
	}

	public String getUomFrom() {
		return uomFrom;
	}

	public String getUomTo() {
		return uomTo;
	}
	
	@Override
	public String toString() {
		return new StandardUomConversionIdConverter().toRequestId(this, this.getClass());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uomFrom == null) ? 0 : uomFrom.hashCode());
		result = prime * result + ((uomTo == null) ? 0 : uomTo.hashCode());
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
		StandardUomConversionPK other = (StandardUomConversionPK) obj;
		if (uomFrom == null) {
			if (other.uomFrom != null)
				return false;
		} else if (!uomFrom.equals(other.uomFrom))
			return false;
		if (uomTo == null) {
			if (other.uomTo != null)
				return false;
		} else if (!uomTo.equals(other.uomTo))
			return false;
		return true;
	}
	
	
}
