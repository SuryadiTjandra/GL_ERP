package ags.goldenlionerp.application.purchase.purchasereceipt;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource
public interface PurchaseReceiptRepository extends
	PagingAndSortingRepository<PurchaseReceipt, PurchaseReceiptPK>,
	QuerydslUsingBaseRepository<QPurchaseReceipt>{

}
