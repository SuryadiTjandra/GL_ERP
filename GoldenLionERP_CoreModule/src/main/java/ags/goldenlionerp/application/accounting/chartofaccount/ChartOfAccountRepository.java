package ags.goldenlionerp.application.accounting.chartofaccount;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="chartOfAccounts", path="chartOfAccounts")
public interface ChartOfAccountRepository extends PagingAndSortingRepository<ChartOfAccount, String>{

}
