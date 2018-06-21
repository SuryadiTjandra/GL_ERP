package ags.goldenlionerp.masterdata.itemmaster;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemRankingsAndParameters {
	@Column(name="IMUDF", precision=19, scale=15)
	private BigDecimal discountFactor = new BigDecimal(0);
	
	@Column(name="IMPMMF")
	private BigDecimal profitMarginMinimumFactor = new BigDecimal(0);
	
	@Column(name="IMGSCRT")
	private BigDecimal serviceChargeRate = new BigDecimal(0);
	
	@Column(name="IMTAXSC")
	private String taxOnSalesCode = "";
	
	@Column(name="IMTAXPC")
	private String taxOnPurchaseCode = "";
	
	@Column(name="IMBPID")
	private String branchPlantId = "";
	
	@Column(name="IMCSID")
	private String customerId = "";
	
	@Column(name="IMVNID")
	private String supplierId = "";
	
	@Column(name="IMRBIS")
	private String rankByInventorySales = "";
	
	@Column(name="IMRBIM")
	private String rankByInventoryMargin = "";
	
	@Column(name="IMRBII")
	private String rankByInventoryInvestement = "";
	
	@Column(name="IMDAQC")
	private boolean disableAutoQtyCombine;

	@Column(name="IMSIOPS")
	private boolean showItemOnPosScreen;

	public BigDecimal getDiscountFactor() {
		return discountFactor;
	}

	public BigDecimal getProfitMarginMinimumFactor() {
		return profitMarginMinimumFactor;
	}

	public BigDecimal getServiceChargeRate() {
		return serviceChargeRate;
	}

	public String getTaxOnSalesCode() {
		return taxOnSalesCode;
	}

	public String getTaxOnPurchaseCode() {
		return taxOnPurchaseCode;
	}

	public String getBranchPlantId() {
		return branchPlantId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public String getRankByInventorySales() {
		return rankByInventorySales;
	}

	public String getRankByInventoryMargin() {
		return rankByInventoryMargin;
	}

	public String getRankByInventoryInvestement() {
		return rankByInventoryInvestement;
	}

	public boolean isDisableAutoQtyCombine() {
		return disableAutoQtyCombine;
	}

	public boolean isShowItemOnPosScreen() {
		return showItemOnPosScreen;
	}

	void setDiscountFactor(BigDecimal discountFactor) {
		this.discountFactor = discountFactor;
	}

	void setProfitMarginMinimumFactor(BigDecimal profitMarginMinimumFactor) {
		this.profitMarginMinimumFactor = profitMarginMinimumFactor;
	}

	void setServiceChargeRate(BigDecimal serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}

	void setTaxOnSalesCode(String taxOnSalesCode) {
		this.taxOnSalesCode = taxOnSalesCode;
	}

	void setTaxOnPurchaseCode(String taxOnPurchaseCode) {
		this.taxOnPurchaseCode = taxOnPurchaseCode;
	}

	void setBranchPlantId(String branchPlantId) {
		this.branchPlantId = branchPlantId;
	}

	void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	void setRankByInventorySales(String rankByInventorySales) {
		this.rankByInventorySales = rankByInventorySales;
	}

	void setRankByInventoryMargin(String rankByInventoryMargin) {
		this.rankByInventoryMargin = rankByInventoryMargin;
	}

	void setRankByInventoryInvestement(String rankByInventoryInvestement) {
		this.rankByInventoryInvestement = rankByInventoryInvestement;
	}

	void setDisableAutoQtyCombine(boolean disableAutoQtyCombine) {
		this.disableAutoQtyCombine = disableAutoQtyCombine;
	}

	void setShowItemOnPosScreen(boolean showItemOnPosScreen) {
		this.showItemOnPosScreen = showItemOnPosScreen;
	}

	//for JPA
	ItemRankingsAndParameters() {}
	
	//for Jackson
	ItemRankingsAndParameters(BigDecimal discountFactor, BigDecimal profitMarginMinimumFactor,
			BigDecimal serviceChargeRate, String taxOnSalesCode, String taxOnPurchaseCode, String branchPlantId,
			String customerId, String supplierId, String rankByInventorySales, String rankByInventoryMargin,
			String rankByInventoryInvestement, boolean disableAutoQtyCombine, boolean showItemOnPosScreen) {
		super();
		this.discountFactor = discountFactor;
		this.profitMarginMinimumFactor = profitMarginMinimumFactor;
		this.serviceChargeRate = serviceChargeRate;
		this.taxOnSalesCode = taxOnSalesCode;
		this.taxOnPurchaseCode = taxOnPurchaseCode;
		this.branchPlantId = branchPlantId;
		this.customerId = customerId;
		this.supplierId = supplierId;
		this.rankByInventorySales = rankByInventorySales;
		this.rankByInventoryMargin = rankByInventoryMargin;
		this.rankByInventoryInvestement = rankByInventoryInvestement;
		this.disableAutoQtyCombine = disableAutoQtyCombine;
		this.showItemOnPosScreen = showItemOnPosScreen;
	}
	
	
}
