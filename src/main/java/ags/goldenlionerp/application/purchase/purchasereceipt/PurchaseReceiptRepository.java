package ags.goldenlionerp.application.purchase.purchasereceipt;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource
public interface PurchaseReceiptRepository extends
	PagingAndSortingRepository<PurchaseReceipt, PurchaseReceiptPK>,
	QuerydslPredicateExecutor<PurchaseReceipt>,
	QuerydslBinderCustomizer<QPurchaseReceipt>{
	//QuerydslUsingBaseRepository<QPurchaseReceipt>{

	@Override
	default void customize(QuerydslBindings bindings, QPurchaseReceipt root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));		
	}
}
