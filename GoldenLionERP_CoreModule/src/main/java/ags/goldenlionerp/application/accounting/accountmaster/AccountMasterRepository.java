package ags.goldenlionerp.application.accounting.accountmaster;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountMasterRepository extends PagingAndSortingRepository<AccountMaster, AccountMasterPK>{

}
