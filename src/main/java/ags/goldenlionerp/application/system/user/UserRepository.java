package ags.goldenlionerp.application.system.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="users", collectionResourceRel="users")
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
