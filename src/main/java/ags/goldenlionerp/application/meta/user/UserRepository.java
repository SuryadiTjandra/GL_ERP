package ags.goldenlionerp.application.meta.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="users")
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
