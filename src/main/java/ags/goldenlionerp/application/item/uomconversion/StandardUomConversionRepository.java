package ags.goldenlionerp.application.item.uomconversion;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="uomconversions", collectionResourceRel="uomconversions")
public interface StandardUomConversionRepository extends PagingAndSortingRepository<StandardUomConversion, StandardUomConversionPK> {

	@Query("SELECT c FROM StandardUomConversion c WHERE c.pk.uomFrom = ?1")
	Collection<StandardUomConversion> findByUomFrom(String uomFrom);
	
	@Query("SELECT c FROM StandardUomConversion c WHERE c.pk.uomTo = ?1")
	Collection<StandardUomConversion> findByUomTo(String uomTo);
}
