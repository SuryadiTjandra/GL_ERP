package ags.goldenlionerp.application.setups.paymentterm;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource
public interface PaymentTermRepository extends PagingAndSortingRepository<PaymentTerm, String>, QuerydslUsingBaseRepository<QPaymentTerm>{

}
