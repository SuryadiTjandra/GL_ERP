package ags.goldenlionerp.application.item.itemmaster;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;
import ags.goldenlionerp.masterdata.itemmaster.QItemMaster;

@RepositoryRestResource(collectionResourceRel="items", path="items")
public interface ItemMasterRepository extends PagingAndSortingRepository<ItemMaster, String>, QuerydslUsingBaseRepository<ItemMaster, QItemMaster> {

}
