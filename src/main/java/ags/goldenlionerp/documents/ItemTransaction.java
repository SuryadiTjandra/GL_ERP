package ags.goldenlionerp.documents;

import java.math.BigDecimal;
import java.util.Collection;

public interface ItemTransaction {

	public DocumentDetailId getId();
	
	public String getBusinessUnitId();
	
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
	
	/**
	 * Check whether the transaction will increase or reduce the number of items in the stock, 
	 * if transaction quantity is positive
	 * @return 
	 */
	public boolean isAdditive();
}
