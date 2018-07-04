package ags.goldenlionerp.system.generalconstant;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class CalculationRounding {

	private BigDecimal accountReceivables;
	
	private BigDecimal internalReceivables;
	
	private BigDecimal accountPayables;
	
	private BigDecimal internalPayables;
	
	private BigDecimal deposits;
	
	@SuppressWarnings("unused")
	private CalculationRounding() {}

	public CalculationRounding(BigDecimal accountReceivables, BigDecimal internalReceivables,
			BigDecimal accountPayables, BigDecimal internalPayables, BigDecimal deposits) {
		super();
		this.accountReceivables = accountReceivables;
		this.internalReceivables = internalReceivables;
		this.accountPayables = accountPayables;
		this.internalPayables = internalPayables;
		this.deposits = deposits;
	}

	public BigDecimal getAccountReceivables() {
		return accountReceivables;
	}

	public BigDecimal getInternalReceivables() {
		return internalReceivables;
	}

	public BigDecimal getAccountPayables() {
		return accountPayables;
	}

	public BigDecimal getInternalPayables() {
		return internalPayables;
	}

	public BigDecimal getDeposits() {
		return deposits;
	}

	void setAccountReceivables(BigDecimal accountReceivables) {
		this.accountReceivables = accountReceivables;
	}

	void setInternalReceivables(BigDecimal internalReceivables) {
		this.internalReceivables = internalReceivables;
	}

	void setAccountPayables(BigDecimal accountPayables) {
		this.accountPayables = accountPayables;
	}

	void setInternalPayables(BigDecimal internalPayables) {
		this.internalPayables = internalPayables;
	}

	void setDeposits(BigDecimal deposits) {
		this.deposits = deposits;
	}
	
	
}
