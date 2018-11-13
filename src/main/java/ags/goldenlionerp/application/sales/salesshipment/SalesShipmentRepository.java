package ags.goldenlionerp.application.sales.salesshipment;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource
public interface SalesShipmentRepository extends 
	PagingAndSortingRepository<SalesShipment, SalesShipmentPK>,
	QuerydslUsingBaseRepository<SalesShipment, QSalesShipment>{

}
