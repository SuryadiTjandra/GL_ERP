package ags.goldenlionerp.application.purchase.purchaseorder;

import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.EnumPath;

import ags.goldenlionerp.application.purchase.OrderStatus;
import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource(collectionResourceRel="purchaseOrders", path="purchaseOrders")
public interface PurchaseOrderRepository extends 
	PagingAndSortingRepository<PurchaseOrder, PurchaseOrderPK>,
	QuerydslUsingBaseRepository<PurchaseOrder, QPurchaseOrder>{

	@Override
	default void customize(QuerydslBindings bindings, QPurchaseOrder root) {
		QuerydslUsingBaseRepository.super.customize(bindings, root);
		
		bindings.bind(OrderStatus.class)
				.first((EnumPath<OrderStatus> path, OrderStatus value) -> {
					switch (value) {
						case OPEN: return root.details.any().openQuantity.gt(0);
						case CLOSED: return root.details.any().openQuantity.gt(0).not();
						//TODO
						default: return null;
					}
					
				});
	}
}
