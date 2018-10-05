package ags.goldenlionerp.application.purchase.purchaseorder;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

@RepositoryRestResource(collectionResourceRel="purchaseOrders", path="purchaseOrders")
public interface PurchaseOrderRepository extends 
	PagingAndSortingRepository<PurchaseOrder, PurchaseOrderPK>,
	QuerydslPredicateExecutor<QPurchaseOrder>,
	QuerydslBinderCustomizer<QPurchaseOrder>{

	@Override
	default void customize(QuerydslBindings bindings, QPurchaseOrder root) {
		bindings.bind(String.class)
			.first((StringPath path, String value) -> path.containsIgnoreCase(value));
		
	}
}
