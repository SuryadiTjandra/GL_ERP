package ags.goldenlionerp.application.system.dmaai;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(exported=true, collectionResourceRel="dmaaiDetails", path="dmaaiDetails")
public interface DMAAIDetailRepository extends CrudRepository<DMAAIDetail, DMAAIDetailPK>{

	@RestResource(exported=false)
	Iterable<DMAAIDetail> findAll();
	
	@RestResource(exported=false)
	<S extends DMAAIDetail> S save(DMAAIDetail entity);
}
