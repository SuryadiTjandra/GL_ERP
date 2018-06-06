package ags.goldenlionerp.masterdata.location;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="locations", path="locations")
public interface LocationRepository extends CrudRepository<LocationMaster, LocationMasterPK>{

}
