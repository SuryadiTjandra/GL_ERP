package ags.goldenlionerp.application.item.lotmaster;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;
import ags.goldenlionerp.application.item.lotmaster.QLotMaster;

@RepositoryRestResource(collectionResourceRel="lots", path="lots")
public interface LotMasterRepository extends PagingAndSortingRepository<LotMaster, LotMasterPK>, QuerydslUsingBaseRepository<LotMaster, QLotMaster> {

}
