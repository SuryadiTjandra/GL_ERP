package ags.goldenlionerp.documents;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public interface ItemTransaction {

	public DocumentDetailId getId();
	
	public String getBusinessUnitId();
	
	public String getBusinessPartnerId();
	
	public LocalDate getTransactionDate();
	
	public String getItemCode();
	
	public String getLocationId();
	
	public Collection<String> getSerialOrLotNumbers();
	
	/**
	 * A positive number means a new transaction. A negative number means the cancellation of a previous transaction.
	 * @return
	 */
	public BigDecimal getTransactionQuantity();
	
	public String getUnitOfMeasure();
	
	public BigDecimal getPrimaryTransactionQuantity();
	
	public String getPrimaryUnitOfMeasure();
	
	public BigDecimal getUnitCost();
	
	default BigDecimal getExtendedCost() {
		return this.getUnitCost().multiply(this.getTransactionQuantity());
	}
	
	/**
	 * Check whether the transaction will increase or reduce the number of items in the stock, 
	 * if transaction quantity is positive
	 * @return 
	 */
	public boolean isAdditive();
	
	public String getDescription();
	
	public int getOrderNumber();
	
	public String getOrderType();
	
	public int getOrderSequence();
}
