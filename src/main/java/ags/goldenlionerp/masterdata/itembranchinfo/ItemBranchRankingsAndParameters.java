package ags.goldenlionerp.masterdata.itembranchinfo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.entities.embeddables.RankingsAndParameters;

@Embeddable
@AttributeOverrides({
	@AttributeOverride(name="discountFactor", column=@Column(name="IBUDF")),
	@AttributeOverride(name="profitMarginMinimumFactor", column=@Column(name="IBPMMF")),
	@AttributeOverride(name="serviceChargeRate", column=@Column(name="IBUDF")),
	@AttributeOverride(name="taxOnSalesCode", column=@Column(name="IBTAXSC")),
	@AttributeOverride(name="taxOnPurchaseCode", column=@Column(name="IBTAXPC")),
	@AttributeOverride(name="branchPlantId", column=@Column(name="IBBPID")),
	@AttributeOverride(name="customerId", column=@Column(name="IBCSID")),
	@AttributeOverride(name="supplierId", column=@Column(name="IBVNID")),
	@AttributeOverride(name="rankByInventorySales", column=@Column(name="IBRBIS")),
	@AttributeOverride(name="rankByInventoryMargin", column=@Column(name="IBRBIM")),
	@AttributeOverride(name="rankByInventoryInvestment", column=@Column(name="IBRBII"))
})
public class ItemBranchRankingsAndParameters extends RankingsAndParameters {
	
	
	ItemBranchRankingsAndParameters() {
	}
}
