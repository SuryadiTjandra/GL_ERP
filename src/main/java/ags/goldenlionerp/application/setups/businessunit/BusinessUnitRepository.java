package ags.goldenlionerp.application.setups.businessunit;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource(collectionResourceRel="businessUnits", path="businessUnits")
public interface BusinessUnitRepository extends PagingAndSortingRepository<BusinessUnit, String>, QuerydslUsingBaseRepository<BusinessUnit, QBusinessUnit>/*, BusinessUnitCustomRepository*/ {

}
