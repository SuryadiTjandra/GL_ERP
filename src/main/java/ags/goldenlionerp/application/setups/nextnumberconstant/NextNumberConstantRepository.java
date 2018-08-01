package ags.goldenlionerp.application.setups.nextnumberconstant;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NextNumberConstantRepository extends PagingAndSortingRepository<NextNumberConstant, String>{

}
