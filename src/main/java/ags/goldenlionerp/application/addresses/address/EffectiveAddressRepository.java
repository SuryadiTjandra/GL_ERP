package ags.goldenlionerp.application.addresses.address;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface EffectiveAddressRepository extends CrudRepository<EffectiveAddress, EffectiveAddressPK> {

	
	default EffectiveAddress findCurrentAddress(String addressNumber) {
		return findAddressBefore(addressNumber, LocalDate.now()).get(0);
	}
	
	@Query("SELECT ea from EffectiveAddress ea WHERE ea.pk.addressNumber = ?1")
	List<EffectiveAddress> findAddressBefore(String addressNumber, LocalDate date);
}
