package ags.goldenlionerp.application.system.discount;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="discounts", path="discounts")
public interface DiscountRepository extends CrudRepository<DiscountMaster, String> {

}
