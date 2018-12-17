package ags.goldenlionerp.application.ar.setting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface AccountReceivableSettingRepository extends CrudRepository<AccountReceivableSetting, String> {

	@RestResource(exported=false)
	Iterable<AccountReceivableSetting> findAll();
}
