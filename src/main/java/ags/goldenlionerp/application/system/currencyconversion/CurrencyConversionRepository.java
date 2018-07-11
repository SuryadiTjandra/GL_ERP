package ags.goldenlionerp.application.system.currencyconversion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CurrencyConversionRepository extends CrudRepository<CurrencyConversion, CurrencyConversionPK> {

}
