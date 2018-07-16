package ags.goldenlionerp.application.addresses.contact;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ags.goldenlionerp.basecomponents.ChildEntityRepository;

@RepositoryRestResource(collectionResourceRel="contacts", path="contacts")
public interface ContactPersonRepository extends CrudRepository<ContactPerson, ContactPersonPK>, 
	ChildEntityRepository<ContactPerson, String> {

	@Override
	default Collection<ContactPerson> findChildrenByParentId(String addressNumber){
		return findByPk_AddressNumber(addressNumber);
	}
	
	Collection<ContactPerson> findByPk_AddressNumber(String addressNumber);
}
