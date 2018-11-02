package ags.goldenlionerp.application.item.location;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="locations", path="locations")
public interface LocationRepository extends CrudRepository<LocationMaster, LocationMasterPK>{

	public Collection<LocationMaster> findByPk_BusinessUnitId(String businessUnitId);
}
