package ags.goldenlionerp.system.dmaai;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="dmaai", path="dmaai")
public interface DMAAIHeaderRepository extends PagingAndSortingRepository<DMAAIHeader, Integer> {

}
