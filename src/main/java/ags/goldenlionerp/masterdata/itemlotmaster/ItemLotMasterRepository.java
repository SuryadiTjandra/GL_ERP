package ags.goldenlionerp.masterdata.itemlotmaster;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemLotMasterRepository extends PagingAndSortingRepository<ItemLotMaster, ItemLotMasterPK>, QuerydslPredicateExecutor<T> {

}
