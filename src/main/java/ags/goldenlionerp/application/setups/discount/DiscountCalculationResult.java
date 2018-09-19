package ags.goldenlionerp.application.setups.discount;

import java.math.BigDecimal;

public class DiscountCalculationResult {

	private BigDecimal amount;
	private String discountCode;
	private BigDecimal discountRate;
	private BigDecimal discountAmount;
	
	public DiscountCalculationResult(BigDecimal amount, String discountCode, BigDecimal discountRate,
			BigDecimal discountAmount) {
		super();
		this.amount = amount;
		this.discountCode = discountCode;
		this.discountRate = discountRate;
		this.discountAmount = discountAmount;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public BigDecimal getDiscountRate() {
		return discountRate;
	}
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	
	
}
