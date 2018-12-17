package ags.goldenlionerp.application.item.itemLocation;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemLocationRepository extends PagingAndSortingRepository<ItemLocation, ItemLocationPK> {

	/*@RestResource(path="businessUnit")
	public Collection<ItemLocation> findByPkBusinessUnitId(String businessUnitId);
	
	@RestResource(path="businessUnitAndItem")
	public Collection<ItemLocation> findByPkBusinessUnitIdAndPkItemCode(String buId, String itemCode);*/
}
