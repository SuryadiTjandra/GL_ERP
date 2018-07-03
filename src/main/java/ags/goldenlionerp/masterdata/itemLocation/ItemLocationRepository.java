package ags.goldenlionerp.masterdata.itemLocation;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ItemLocationRepository extends PagingAndSortingRepository<ItemLocation, ItemLocationPK> {

	/*@RestResource(path="businessUnit")
	public Collection<ItemLocation> findByPkBusinessUnitId(String businessUnitId);
	
	@RestResource(path="businessUnitAndItem")
	public Collection<ItemLocation> findByPkBusinessUnitIdAndPkItemCode(String buId, String itemCode);*/
}
