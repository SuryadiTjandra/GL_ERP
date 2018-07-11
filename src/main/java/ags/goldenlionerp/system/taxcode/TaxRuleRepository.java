package ags.goldenlionerp.system.taxcode;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TaxRuleRepository extends CrudRepository<TaxRule, TaxRulePK> {

}
