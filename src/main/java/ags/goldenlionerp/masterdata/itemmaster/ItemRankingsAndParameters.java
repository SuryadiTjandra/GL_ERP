package ags.goldenlionerp.masterdata.itemmaster;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.entities.embeddables.RankingsAndParameters;

@Embeddable
@AttributeOverrides({
	@AttributeOverride(name="discountFactor", column=@Column(name="IMUDF")),
	@AttributeOverride(name="profitMarginMinimumFactor", column=@Column(name="IMPMMF")),
	@AttributeOverride(name="serviceChargeRate", column=@Column(name="IMGSCRT")),
	@AttributeOverride(name="taxOnSalesCode", column=@Column(name="IMTAXSC")),
	@AttributeOverride(name="taxOnPurchaseCode", column=@Column(name="IMTAXPC")),
	@AttributeOverride(name="branchPlantId", column=@Column(name="IMBPID")),
	@AttributeOverride(name="customerId", column=@Column(name="IMCSID")),
	@AttributeOverride(name="supplierId", column=@Column(name="IMVNID")),
	@AttributeOverride(name="rankByInventorySales", column=@Column(name="IMRBIS")),
	@AttributeOverride(name="rankByInventoryMargin", column=@Column(name="IMRBIM")),
	@AttributeOverride(name="rankByInventoryInvestment", column=@Column(name="IMRBII"))
})
public class ItemRankingsAndParameters extends RankingsAndParameters {
	
	@Column(name="IMGSCRT")
	private BigDecimal serviceChargeRate = new BigDecimal(0);
	
	@Column(name="IMDAQC")
	private boolean disableAutoQtyCombine;

	@Column(name="IMSIOPS")
	private boolean showItemOnPosScreen;

	public BigDecimal getServiceChargeRate() {
		return serviceChargeRate;
	}
	
	public boolean isDisableAutoQtyCombine() {
		return disableAutoQtyCombine;
	}

	public boolean isShowItemOnPosScreen() {
		return showItemOnPosScreen;
	}
	
	void setDisableAutoQtyCombine(boolean disableAutoQtyCombine) {
		this.disableAutoQtyCombine = disableAutoQtyCombine;
	}

	void setShowItemOnPosScreen(boolean showItemOnPosScreen) {
		this.showItemOnPosScreen = showItemOnPosScreen;
	}


	@Override
	protected void setDiscountFactor(BigDecimal discountFactor) {
		super.setDiscountFactor(discountFactor);
	}

	@Override
	protected void setProfitMarginMinimumFactor(BigDecimal profitMarginMinimumFactor) {
		super.setProfitMarginMinimumFactor(profitMarginMinimumFactor);
	}

	@Override
	protected void setTaxOnSalesCode(String taxOnSalesCode) {
		super.setTaxOnSalesCode(taxOnSalesCode);
	}

	@Override
	protected void setTaxOnPurchaseCode(String taxOnPurchaseCode) {
		super.setTaxOnPurchaseCode(taxOnPurchaseCode);
	}

	@Override
	protected void setBranchPlantId(String branchPlantId) {
		super.setBranchPlantId(branchPlantId);
	}

	@Override
	protected void setCustomerId(String customerId) {
		super.setCustomerId(customerId);
	}

	@Override
	protected void setSupplierId(String supplierId) {
		super.setSupplierId(supplierId);
	}

	@Override
	protected void setRankByInventorySales(String rankByInventorySales) {
		super.setRankByInventorySales(rankByInventorySales);
	}

	@Override
	protected void setRankByInventoryMargin(String rankByInventoryMargin) {
		super.setRankByInventoryMargin(rankByInventoryMargin);
	}

	@Override
	protected void setRankByInventoryInvestment(String rankByInventoryInvestment) {
		super.setRankByInventoryInvestment(rankByInventoryInvestment);
	}

	//for JPA
	ItemRankingsAndParameters() {}
	
	
}
