package ags.goldenlionerp.documents;

public interface ItemTransactionObserver {

	public void handleCreated(ItemTransaction createdTransaction);
	
	//public void handleUpdated(ItemTransactionDocument updatedTransaction);
	
	//public void handleDeleted(ItemTransactionDocument deletedTransaction);
}
