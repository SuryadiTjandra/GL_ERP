package ags.goldenlionerp.entities.embeddables;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class RankingsAndParameters {
	@Column(name="UDF", precision=19, scale=15)
	private BigDecimal discountFactor = new BigDecimal(0);
	
	@Column(name="PMMF", precision=9, scale=5)
	private BigDecimal profitMarginMinimumFactor = new BigDecimal(0);
	
	@Column(name="TAXSC")
	private String taxOnSalesCode = "";
	
	@Column(name="TAXPC")
	private String taxOnPurchaseCode = "";
	
	@Column(name="BPID")
	private String branchPlantId = "";
	
	@Column(name="CSID")
	private String customerId = "";
	
	@Column(name="VNID")
	private String supplierId = "";
	
	@Column(name="RBIS")
	private String rankByInventorySales = "";
	
	@Column(name="RBIM")
	private String rankByInventoryMargin = "";
	
	@Column(name="RBII")
	private String rankByInventoryInvestment = "";

	public BigDecimal getDiscountFactor() {
		return discountFactor;
	}

	public BigDecimal getProfitMarginMinimumFactor() {
		return profitMarginMinimumFactor;
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

	public String getRankByInventoryInvestment() {
		return rankByInventoryInvestment;
	}

	protected void setDiscountFactor(BigDecimal discountFactor) {
		this.discountFactor = discountFactor;
	}

	protected void setProfitMarginMinimumFactor(BigDecimal profitMarginMinimumFactor) {
		this.profitMarginMinimumFactor = profitMarginMinimumFactor;
	}

	protected void setTaxOnSalesCode(String taxOnSalesCode) {
		this.taxOnSalesCode = taxOnSalesCode;
	}

	protected void setTaxOnPurchaseCode(String taxOnPurchaseCode) {
		this.taxOnPurchaseCode = taxOnPurchaseCode;
	}

	protected void setBranchPlantId(String branchPlantId) {
		this.branchPlantId = branchPlantId;
	}

	protected void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	protected void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	protected void setRankByInventorySales(String rankByInventorySales) {
		this.rankByInventorySales = rankByInventorySales;
	}

	protected void setRankByInventoryMargin(String rankByInventoryMargin) {
		this.rankByInventoryMargin = rankByInventoryMargin;
	}

	protected void setRankByInventoryInvestment(String rankByInventoryInvestment) {
		this.rankByInventoryInvestment = rankByInventoryInvestment;
	}

	
}
