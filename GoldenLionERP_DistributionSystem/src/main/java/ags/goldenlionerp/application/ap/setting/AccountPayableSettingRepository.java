package ags.goldenlionerp.application.ap.setting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface AccountPayableSettingRepository extends CrudRepository<AccountPayableSetting, String> {

	@RestResource(exported=false)
	Iterable<AccountPayableSetting> findAll();

}
