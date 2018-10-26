package ags.goldenlionerp.masterdata.lotmaster;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource(collectionResourceRel="lots", path="lots")
public interface LotMasterRepository extends PagingAndSortingRepository<LotMaster, LotMasterPK>, QuerydslUsingBaseRepository<LotMaster, QLotMaster> {

	Collection<LotMaster> saveAll(Collection<LotMaster> entities);
}
