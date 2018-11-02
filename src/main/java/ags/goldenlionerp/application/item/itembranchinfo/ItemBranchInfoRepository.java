package ags.goldenlionerp.application.item.itembranchinfo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="itembranches", path="itembranches")
public interface ItemBranchInfoRepository extends PagingAndSortingRepository<ItemBranchInfo, ItemBranchInfoPK>{

}
