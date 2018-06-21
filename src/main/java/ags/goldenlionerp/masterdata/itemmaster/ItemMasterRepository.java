package ags.goldenlionerp.masterdata.itemmaster;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="items", path="items")
public interface ItemMasterRepository extends PagingAndSortingRepository<ItemMaster, String>/*, ItemMasterCustomRepository*/ {

}
