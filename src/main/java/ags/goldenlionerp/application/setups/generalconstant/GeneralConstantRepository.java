package ags.goldenlionerp.application.setups.generalconstant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel="generalConstant", path="generalConstant")
public interface GeneralConstantRepository extends CrudRepository<GeneralConstant, String>{
	
	@RestResource(exported=false)
	default GeneralConstant find() {
		return findById(GeneralConstant.DEFAULT_CODE).get();
	}
	
	@RestResource(exported=false)
	void deleteById(String id);
	@RestResource(exported=false)
	void delete(GeneralConstant entity);
	@RestResource(exported=false)
	void deleteAll(Iterable<? extends GeneralConstant> entities);
	@RestResource(exported=false)
	void deleteAll();

}
