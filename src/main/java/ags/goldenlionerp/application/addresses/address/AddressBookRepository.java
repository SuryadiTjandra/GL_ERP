package ags.goldenlionerp.application.addresses.address;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.QuerydslUsingBaseRepository;

@RepositoryRestResource(collectionResourceRel="addresses", path="addresses")
public interface AddressBookRepository extends 
		PagingAndSortingRepository<AddressBookMaster, String>,
		AddressCustomRepository,
		QuerydslUsingBaseRepository<AddressBookMaster, QAddressBookMaster>
{


}
