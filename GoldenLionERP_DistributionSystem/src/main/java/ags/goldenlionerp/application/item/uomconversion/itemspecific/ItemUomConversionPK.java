package ags.goldenlionerp.application.item.uomconversion.itemspecific;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemUomConversionPK implements Serializable{

	private static final long serialVersionUID = 3576804944889646495L;

	@Column(name="UMINUM")
	private String itemCode;
	
	@Column(name="UMUOM")
	private String uomFrom;
	
	@Column(name="UMUOMR")
	private String uomTo;

	@SuppressWarnings("unused")
	private ItemUomConversionPK() {}
	
	public ItemUomConversionPK(String itemCode, String uomFrom, String uomTo) {
		super();
		this.itemCode = itemCode;
		this.uomFrom = uomFrom;
		this.uomTo = uomTo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getUomFrom() {
		return uomFrom;
	}

	public String getUomTo() {
		return uomTo;
	}
	
	@Override
	public String toString() {
		return new ItemUomConversionIdConverter().toRequestId(this, ItemUomConversion.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
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
		ItemUomConversionPK other = (ItemUomConversionPK) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
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
