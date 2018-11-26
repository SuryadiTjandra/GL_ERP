package ags.goldenlionerp.documents;

public interface ItemTransactionObserver {

	public void handleCreation(ItemTransaction createdTransaction);
	
	//public void handleUpdated(ItemTransactionDocument updatedTransaction);
	
	//public void handleDeleted(ItemTransactionDocument deletedTransaction);
	
	public void handleVoidation(ItemTransaction voidedTransaction);
}
