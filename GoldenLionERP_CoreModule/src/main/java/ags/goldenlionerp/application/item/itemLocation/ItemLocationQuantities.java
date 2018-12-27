package ags.goldenlionerp.application.item.itemLocation;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemLocationQuantities {
	@Column(name="ILPQOH", precision=19, scale=5)
	private BigDecimal primaryQuantityOnHand = new BigDecimal(0);
	
	@Column(name="ILSQOH", precision=19, scale=5)
	private BigDecimal secondaryQuantityOnHand = new BigDecimal(0);
	
	@Column(name="ILQOPO", precision=19, scale=5)
	private BigDecimal quantityOnPurchaseOrder = new BigDecimal(0);
	
	@Column(name="ILQOSO", precision=19, scale=5)
	private BigDecimal quantityOnSalesOrder = new BigDecimal(0);
	
	@Column(name="ILQOBO", precision=19, scale=5)
	private BigDecimal quantityOnBackOrder = new BigDecimal(0);
	
	@Column(name="ILQTTR", precision=19, scale=5)
	private BigDecimal quantityInTransit = new BigDecimal(0);
	
	@Column(name="ILQTIN", precision=19, scale=5)
	private BigDecimal quantityInInspection = new BigDecimal(0);
	
	@Column(name="ILQOT1", precision=19, scale=5)
	private BigDecimal quantityOther1 = new BigDecimal(0);
	
	@Column(name="ILQOT2", precision=19, scale=5)
	private BigDecimal quantityOther2 = new BigDecimal(0);
	
	@Column(name="ILQOT3", precision=19, scale=5)
	private BigDecimal quantityOther3 = new BigDecimal(0);
	
	@Column(name="ILQTIB", precision=19, scale=5)
	private BigDecimal quantityInBound = new BigDecimal(0);
	
	@Column(name="ILQTOB", precision=19, scale=5)
	private BigDecimal quantityOutBound = new BigDecimal(0);
	
	@Column(name="ILQOSC", precision=19, scale=5)
	private BigDecimal quantityOnSoftCommit = new BigDecimal(0);
	
	@Column(name="ILQOHC", precision=19, scale=5)
	private BigDecimal quantityOnHardCommit = new BigDecimal(0);
	
	@Column(name="ILQOWO", precision=19, scale=5)
	private BigDecimal quantityOnWO = new BigDecimal(0);
	
	@Column(name="ILQOWS", precision=19, scale=5)
	private BigDecimal quantityOnWOSoftCommit = new BigDecimal(0);
	
	@Column(name="ILQOWH", precision=19, scale=5)
	private BigDecimal quantityOnWOHardCommit = new BigDecimal(0);
	
	@Column(name="ILSQPO", precision=19, scale=5)
	private BigDecimal secondaryQuantityOnPO = new BigDecimal(0);
	
	@Column(name="ILSQSO", precision=19, scale=5)
	private BigDecimal secondaryQuantityOnSO = new BigDecimal(0);
	
	@Column(name="ILSQWO", precision=19, scale=5)
	private BigDecimal secondaryQuantityOnWO = new BigDecimal(0);
	
	@Column(name="ILUOM")
	private String unitOfMeasure = "";
	
	@Column(name="ILUOM1")
	private String unitOfMeasure1 = "";
	
	@Column(name="ILUOM2")
	private String unitOfMeasure2 = "";

	public BigDecimal getPrimaryQuantityOnHand() {
		return primaryQuantityOnHand;
	}

	public BigDecimal getSecondaryQuantityOnHand() {
		return secondaryQuantityOnHand;
	}

	public BigDecimal getQuantityOnPurchaseOrder() {
		return quantityOnPurchaseOrder;
	}

	public BigDecimal getQuantityOnSalesOrder() {
		return quantityOnSalesOrder;
	}

	public BigDecimal getQuantityOnBackOrder() {
		return quantityOnBackOrder;
	}

	public BigDecimal getQuantityInTransit() {
		return quantityInTransit;
	}

	public BigDecimal getQuantityInInspection() {
		return quantityInInspection;
	}

	public BigDecimal getQuantityOther1() {
		return quantityOther1;
	}

	public BigDecimal getQuantityOther2() {
		return quantityOther2;
	}

	public BigDecimal getQuantityOther3() {
		return quantityOther3;
	}

	public BigDecimal getQuantityInBound() {
		return quantityInBound;
	}

	public BigDecimal getQuantityOutBound() {
		return quantityOutBound;
	}

	public BigDecimal getQuantityOnSoftCommit() {
		return quantityOnSoftCommit;
	}

	public BigDecimal getQuantityOnHardCommit() {
		return quantityOnHardCommit;
	}

	public BigDecimal getQuantityOnWO() {
		return quantityOnWO;
	}

	public BigDecimal getQuantityOnWOSoftCommit() {
		return quantityOnWOSoftCommit;
	}

	public BigDecimal getQuantityOnWOHardCommit() {
		return quantityOnWOHardCommit;
	}

	public BigDecimal getSecondaryQuantityOnPO() {
		return secondaryQuantityOnPO;
	}

	public BigDecimal getSecondaryQuantityOnSO() {
		return secondaryQuantityOnSO;
	}

	public BigDecimal getSecondaryQuantityOnWO() {
		return secondaryQuantityOnWO;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public String getUnitOfMeasure1() {
		return unitOfMeasure1;
	}

	public String getUnitOfMeasure2() {
		return unitOfMeasure2;
	}
	
	ItemLocationQuantities() {}
}
