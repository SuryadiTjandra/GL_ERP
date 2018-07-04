package ags.goldenlionerp.system.paymentterm;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PaymentTermRepository extends PagingAndSortingRepository<PaymentTerm, String>{

}
