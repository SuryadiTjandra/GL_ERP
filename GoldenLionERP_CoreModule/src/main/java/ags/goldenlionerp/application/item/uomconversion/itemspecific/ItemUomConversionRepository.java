package ags.goldenlionerp.application.item.uomconversion.itemspecific;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="itemuomconvs", collectionResourceRel="itemUomConversions")
public interface ItemUomConversionRepository extends CrudRepository<ItemUomConversion, ItemUomConversionPK> {

	@Query("SELECT c FROM ItemUomConversion c WHERE c.pk.itemCode = ?1")
	Collection<ItemUomConversion> findByItemCode(String itemCode);
}
