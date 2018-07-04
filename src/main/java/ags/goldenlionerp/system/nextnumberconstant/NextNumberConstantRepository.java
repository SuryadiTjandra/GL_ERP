package ags.goldenlionerp.system.nextnumberconstant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NextNumberConstantRepository extends CrudRepository<NextNumberConstant, String>{

}
