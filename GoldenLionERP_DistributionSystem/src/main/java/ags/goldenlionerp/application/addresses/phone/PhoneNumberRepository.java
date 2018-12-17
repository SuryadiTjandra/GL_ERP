package ags.goldenlionerp.application.addresses.phone;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import ags.goldenlionerp.basecomponents.ChildrenEntityRepository;

@RepositoryRestController
public interface PhoneNumberRepository
		extends CrudRepository<PhoneNumber, PhoneNumberPK>, ChildrenEntityRepository<PhoneNumber, String> {

	@Override
	default Collection<PhoneNumber> findChildrenByParentId(String parentId) {
		return findByPk_AddressNumber(parentId);
	}
	
	Collection<PhoneNumber> findByPk_AddressNumber(String addressNumber);
}
