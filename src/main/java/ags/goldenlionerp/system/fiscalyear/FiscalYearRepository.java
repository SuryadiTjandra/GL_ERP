package ags.goldenlionerp.system.fiscalyear;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FiscalYearRepository extends PagingAndSortingRepository<FiscalYear, FiscalYearPK> {

}
