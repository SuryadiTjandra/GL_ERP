package ags.goldenlionerp.application.sales.salesorder;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="salesOrders", path="salesOrders")
public interface SalesOrderRepository extends PagingAndSortingRepository<SalesOrder, SalesOrderPK> {

}
