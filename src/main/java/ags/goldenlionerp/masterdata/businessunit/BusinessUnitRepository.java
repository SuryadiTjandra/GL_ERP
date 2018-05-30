package ags.goldenlionerp.masterdata.businessunit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="businessUnits", path="businessUnits")
public interface BusinessUnitRepository extends CrudRepository<BusinessUnit, String> {

}
