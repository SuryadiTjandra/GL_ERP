package ags.goldenlionerp.application.setups.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="companies", path="companies")
interface CompanyRepository extends CrudRepository<Company, String>{
	
}
