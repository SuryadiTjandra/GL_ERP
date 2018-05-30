package ags.goldenlionerp.masterdata.businessunit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="businessUnit", path="businessUnit")
public interface BusinessUnitRepository extends CrudRepository<BusinessUnit, String> {

}
