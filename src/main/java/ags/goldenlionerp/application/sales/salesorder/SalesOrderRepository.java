package ags.goldenlionerp.application.sales.salesorder;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

@RepositoryRestResource(collectionResourceRel="salesOrders", path="salesOrders")
public interface SalesOrderRepository extends 
	PagingAndSortingRepository<SalesOrder, SalesOrderPK>,
	QuerydslPredicateExecutor<QSalesOrder>,
	QuerydslBinderCustomizer<QSalesOrder>{

	@Override
	default void customize(QuerydslBindings bindings, QSalesOrder root) {
		bindings.bind(String.class)
			.first((StringPath path, String value) -> path.containsIgnoreCase(value));
		
	}
}
