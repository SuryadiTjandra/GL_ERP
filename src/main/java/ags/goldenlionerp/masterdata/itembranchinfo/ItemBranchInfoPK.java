package ags.goldenlionerp.masterdata.itembranchinfo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemBranchInfoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8219943186278332792L;

	@Column(name="IBBUID")
	private String branchCode;
	@Column(name="IBINUM")
	private String itemCode;


	public String getBranchCode() {
		return branchCode;
	}
	public String getItemCode() {
		return itemCode;
	}


	public ItemBranchInfoPK(String branchCode, String itemCode) {
		super();
		this.branchCode = branchCode;
		this.itemCode = itemCode;
	}

	@SuppressWarnings("unused")
	private ItemBranchInfoPK() {
	}

	@Override
	public String toString() {
		return this.branchCode + "_" + this.itemCode;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
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
		ItemBranchInfoPK other = (ItemBranchInfoPK) obj;
		if (branchCode == null) {
			if (other.branchCode != null)
				return false;
		} else if (!branchCode.equals(other.branchCode))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		return true;
	}


	
	
}
