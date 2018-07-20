package ags.goldenlionerp.application.ar.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;

@Service
public class AccountReceivableSettingService {

	@Autowired
	private AccountReceivableSettingRepository repo;
	
	public AccountReceivableSetting createNewSettingForAddress(AddressBookMaster master) {
		AccountReceivableSetting setting = new AccountReceivableSetting();
		setting.setAddressNumber(master.getAddressNumber());
		return setting;
	}
	
	public void deleteSettingOfAddress(AddressBookMaster master) {
		repo.deleteById(master.getAddressNumber());
	}
}
