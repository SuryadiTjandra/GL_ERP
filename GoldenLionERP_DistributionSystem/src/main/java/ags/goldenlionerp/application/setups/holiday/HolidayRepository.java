package ags.goldenlionerp.application.setups.holiday;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="holidays", path="holidays")
public interface HolidayRepository extends CrudRepository<HolidayMaster, LocalDate> {

}
