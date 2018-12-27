package ags.goldenlionerp.application.sales.salesorder;

import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.EnumPath;
import ags.goldenlionerp.application.purchase.OrderStatus;
import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource(collectionResourceRel="salesOrders", path="salesOrders")
public interface SalesOrderRepository extends 
	PagingAndSortingRepository<SalesOrder, SalesOrderPK>,
	QuerydslUsingBaseRepository<SalesOrder, QSalesOrder>{

	@Override
	default void customize(QuerydslBindings bindings, QSalesOrder root) {
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
