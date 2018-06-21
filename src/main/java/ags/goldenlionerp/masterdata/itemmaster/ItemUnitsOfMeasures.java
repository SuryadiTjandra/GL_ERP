package ags.goldenlionerp.masterdata.itemmaster;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemUnitsOfMeasures {
	@Column(name="IMUOM1")
	private String primaryUnitOfMeasure = "";
	
	@Column(name="IMUOM2")
	private String secondaryUnitOfMeasure = "";
	
	@Column(name="IMUOM3")
	private String purchasingUnitOfMeasure = "";
	
	@Column(name="IMUOM4")
	private String salesUnitOfMeasure = "";
	
	@Column(name="IMUOM5")
	private String priceUnitOfMeasure = "";
	
	@Column(name="IMUOM6")
	private String shippingUnitOfMeasure = "";
	
	@Column(name="IMUOM8")
	private String productionUnitOfMeasure = "";
	
	@Column(name="IMUOM9")
	private String componentUnitOfMeasure = "";
	
	@Column(name="IMUOMV")
	private String volumeUnitOfMeasure = "";
	
	@Column(name="IMUOMS")
	private String stockedUnitOfMeasure = "";
	
	@Column(name="IMUOMT")
	private String timeUnitOfMeasure = "";
	
	@Column(name="IMUOMW")
	private String weightUnitOfMeasure = "";
	
	@Column(name="IMWCF", precision=19, scale=9)
	private BigDecimal weightConversionFactor = new BigDecimal(0);
	
	@Column(name="IMDLUM")
	private boolean dualQuantityUnitOfMeasure;
	
	@Column(name="IMDPPO")
	private boolean dualPickingProcessOption;
	
	@Column(name="IMDLUMT")
	private boolean dualQuantityUnitOfMeasureTolerance;
	
	@Column(name="IMDLUMP", precision=19, scale=15)
	private BigDecimal dualQuantityUnitOfMeasurePercentage = new BigDecimal(0);

	public String getPrimaryUnitOfMeasure() {
		return primaryUnitOfMeasure;
	}

	public String getSecondaryUnitOfMeasure() {
		return determineUom(secondaryUnitOfMeasure);
	}

	public String getPurchasingUnitOfMeasure() {
		return determineUom(purchasingUnitOfMeasure);
	}

	public String getSalesUnitOfMeasure() {
		return determineUom(salesUnitOfMeasure);
	}

	public String getPriceUnitOfMeasure() {
		return determineUom(priceUnitOfMeasure);
	}

	public String getShippingUnitOfMeasure() {
		return determineUom(shippingUnitOfMeasure);
	}

	public String getProductionUnitOfMeasure() {
		return determineUom(productionUnitOfMeasure);
	}

	public String getComponentUnitOfMeasure() {
		return determineUom(componentUnitOfMeasure);
	}

	public String getVolumeUnitOfMeasure() {
		return determineUom(volumeUnitOfMeasure);
	}

	public String getStockedUnitOfMeasure() {
		return determineUom(stockedUnitOfMeasure);
	}

	public String getTimeUnitOfMeasure() {
		return determineUom(timeUnitOfMeasure);
	}

	public String getWeightUnitOfMeasure() {
		return determineUom(weightUnitOfMeasure);
	}

	public BigDecimal getWeightConversionFactor() {
		return weightConversionFactor;
	}

	public boolean isDualQuantityUnitOfMeasure() {
		return dualQuantityUnitOfMeasure;
	}

	public boolean isDualPickingProcessOption() {
		return dualPickingProcessOption;
	}

	public boolean isDualQuantityUnitOfMeasureTolerance() {
		return dualQuantityUnitOfMeasureTolerance;
	}

	public BigDecimal getDualQuantityUnitOfMeasurePercentage() {
		return dualQuantityUnitOfMeasurePercentage;
	}

	void setPrimaryUnitOfMeasure(String primaryUnitOfMeasure) {
		this.primaryUnitOfMeasure = primaryUnitOfMeasure;
	}

	void setSecondaryUnitOfMeasure(String secondaryUnitOfMeasure) {
		this.secondaryUnitOfMeasure = secondaryUnitOfMeasure;
	}

	void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}

	void setSalesUnitOfMeasure(String salesUnitOfMeasure) {
		this.salesUnitOfMeasure = salesUnitOfMeasure;
	}

	void setPriceUnitOfMeasure(String priceUnitOfMeasure) {
		this.priceUnitOfMeasure = priceUnitOfMeasure;
	}

	void setShippingUnitOfMeasure(String shippingUnitOfMeasure) {
		this.shippingUnitOfMeasure = shippingUnitOfMeasure;
	}

	void setProductionUnitOfMeasure(String productionUnitOfMeasure) {
		this.productionUnitOfMeasure = productionUnitOfMeasure;
	}

	void setComponentUnitOfMeasure(String componentUnitOfMeasure) {
		this.componentUnitOfMeasure = componentUnitOfMeasure;
	}

	void setVolumeUnitOfMeasure(String volumeUnitOfMeasure) {
		this.volumeUnitOfMeasure = volumeUnitOfMeasure;
	}

	void setStockedUnitOfMeasure(String stockedUnitOfMeasure) {
		this.stockedUnitOfMeasure = stockedUnitOfMeasure;
	}

	void setTimeUnitOfMeasure(String timeUnitOfMeasure) {
		this.timeUnitOfMeasure = timeUnitOfMeasure;
	}

	void setWeightUnitOfMeasure(String weightUnitOfMeasure) {
		this.weightUnitOfMeasure = weightUnitOfMeasure;
	}

	void setWeightConversionFactor(BigDecimal weightConversionFactor) {
		this.weightConversionFactor = weightConversionFactor;
	}

	void setDualQuantityUnitOfMeasure(boolean dualQuantityUnitOfMeasure) {
		this.dualQuantityUnitOfMeasure = dualQuantityUnitOfMeasure;
	}

	void setDualPickingProcessOption(boolean dualPickingProcessOption) {
		this.dualPickingProcessOption = dualPickingProcessOption;
	}

	void setDualQuantityUnitOfMeasureTolerance(boolean dualQuantityUnitOfMeasureTolerance) {
		this.dualQuantityUnitOfMeasureTolerance = dualQuantityUnitOfMeasureTolerance;
	}

	void setDualQuantityUnitOfMeasurePercentage(BigDecimal dualQuantityUnitOfMeasurePercentage) {
		this.dualQuantityUnitOfMeasurePercentage = dualQuantityUnitOfMeasurePercentage;
	}

	//for JPA
	ItemUnitsOfMeasures() {init();}
	
	//for Jackson
	ItemUnitsOfMeasures(String primaryUnitOfMeasure, String secondaryUnitOfMeasure, String purchasingUnitOfMeasure,
			String salesUnitOfMeasure, String priceUnitOfMeasure, String shippingUnitOfMeasure,
			String productionUnitOfMeasure, String componentUnitOfMeasure, String volumeUnitOfMeasure,
			String stockedUnitOfMeasure, String timeUnitOfMeasure, String weightUnitOfMeasure,
			BigDecimal weightConversionFactor, boolean dualQuantityUnitOfMeasure, boolean dualPickingProcessOption,
			boolean dualQuantityUnitOfMeasureTolerance, BigDecimal dualQuantityUnitOfMeasurePercentage) {
		super();
		this.primaryUnitOfMeasure = primaryUnitOfMeasure;
		this.secondaryUnitOfMeasure = secondaryUnitOfMeasure;
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
		this.salesUnitOfMeasure = salesUnitOfMeasure;
		this.priceUnitOfMeasure = priceUnitOfMeasure;
		this.shippingUnitOfMeasure = shippingUnitOfMeasure;
		this.productionUnitOfMeasure = productionUnitOfMeasure;
		this.componentUnitOfMeasure = componentUnitOfMeasure;
		this.volumeUnitOfMeasure = volumeUnitOfMeasure;
		this.stockedUnitOfMeasure = stockedUnitOfMeasure;
		this.timeUnitOfMeasure = timeUnitOfMeasure;
		this.weightUnitOfMeasure = weightUnitOfMeasure;
		this.weightConversionFactor = weightConversionFactor;
		this.dualQuantityUnitOfMeasure = dualQuantityUnitOfMeasure;
		this.dualPickingProcessOption = dualPickingProcessOption;
		this.dualQuantityUnitOfMeasureTolerance = dualQuantityUnitOfMeasureTolerance;
		this.dualQuantityUnitOfMeasurePercentage = dualQuantityUnitOfMeasurePercentage;
		init();
	}

	private String determineUom(String unitOfMeasure) {
		if (this.primaryUnitOfMeasure == null ||this.primaryUnitOfMeasure.isEmpty())
			throw new IllegalStateException();
		
		return (unitOfMeasure == null || unitOfMeasure.isEmpty()) ? 
				primaryUnitOfMeasure : unitOfMeasure;
	}
	
	private void init() {
		if (this.primaryUnitOfMeasure == null || this.primaryUnitOfMeasure.isEmpty())
			return;
		
		this.secondaryUnitOfMeasure = determineUom(secondaryUnitOfMeasure);
		this.purchasingUnitOfMeasure = determineUom(purchasingUnitOfMeasure);
		this.salesUnitOfMeasure = determineUom(salesUnitOfMeasure);
		this.priceUnitOfMeasure = determineUom(priceUnitOfMeasure);
		this.shippingUnitOfMeasure = determineUom(shippingUnitOfMeasure);
		this.productionUnitOfMeasure = determineUom(productionUnitOfMeasure);
		this.componentUnitOfMeasure = determineUom(componentUnitOfMeasure);
		this.volumeUnitOfMeasure = determineUom(volumeUnitOfMeasure);
		this.stockedUnitOfMeasure = determineUom(stockedUnitOfMeasure);
		this.timeUnitOfMeasure = determineUom(timeUnitOfMeasure);
		this.weightUnitOfMeasure = determineUom(weightUnitOfMeasure);
	}
}
