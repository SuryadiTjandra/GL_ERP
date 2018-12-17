package ags.goldenlionerp.application.addresses.address;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

public class AddressCustomRepositoryImpl implements AddressCustomRepository {

	@Autowired
	private EntityManager em;
	
	@Override
	public <S extends AddressBookMaster> S save(S entity) {
		return em.find(AddressBookMaster.class, entity.getId()) != null? 
				handleUpdate(entity) : 
				handleCreate(entity);
	}

	private <S extends AddressBookMaster> S handleCreate(S entity) {
		EffectiveAddress ea = entity.getCurrentAddress();
		if (ea == null) {
			ea = new EffectiveAddress();
			entity.setCurrentAddress(ea);
		} 
		EffectiveAddressPK pk = new EffectiveAddressPK(entity.getAddressNumber());
		ea.setPk(pk);
		
		em.persist(entity);
		return entity;
	}
	
	private <S extends AddressBookMaster> S handleUpdate(S entity) {
		EffectiveAddress ea = entity.getCurrentAddress();
		if (!em.contains(ea)) {
			EffectiveAddressPK pk = new EffectiveAddressPK(entity.getAddressNumber());
			ea.setPk(pk);
		}
		
		
		return em.merge(entity);
	}
}
