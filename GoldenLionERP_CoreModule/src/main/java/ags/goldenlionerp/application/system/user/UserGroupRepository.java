package ags.goldenlionerp.application.system.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="userGroups", collectionResourceRel="userGroups")
public interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, String> {

}
