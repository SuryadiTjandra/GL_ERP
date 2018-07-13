package ags.goldenlionerp.application.addresses.address;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

public class AddressCustomRepositoryImpl implements AddressCustomRepository {

	@Autowired
	private EntityManager em;
	@Autowired
	private EffectiveAddressRepository eaRepo;
	
	@Override
	public AddressBookMaster save(AddressBookMaster entity) {
		return em.find(AddressBookMaster.class, entity.getId()) != null? 
				handleUpdate(entity) : 
				handleCreate(entity);
	}

	private AddressBookMaster handleCreate(AddressBookMaster entity) {
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
	
	private AddressBookMaster handleUpdate(AddressBookMaster entity) {
		EffectiveAddress ea = entity.getCurrentAddress();
		if (!em.contains(ea)) {
			EffectiveAddressPK pk = new EffectiveAddressPK(entity.getAddressNumber());
			ea.setPk(pk);
		}
		
		
		return em.merge(entity);
	}
}
