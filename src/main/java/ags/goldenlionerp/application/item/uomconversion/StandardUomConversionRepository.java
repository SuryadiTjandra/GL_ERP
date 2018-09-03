package ags.goldenlionerp.application.item.uomconversion;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StandardUomConversionRepository extends PagingAndSortingRepository<StandardUomConversion, StandardUomConversionPK> {

	Collection<StandardUomConversion> findByUomFrom(String uomFrom);
	
	Collection<StandardUomConversion> findByUomTo(String uomTo);
}
