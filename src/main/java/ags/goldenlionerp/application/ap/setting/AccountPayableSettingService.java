package ags.goldenlionerp.application.ap.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;

@Service
public class AccountPayableSettingService {
	@Autowired
	private AccountPayableSettingRepository repo;
	
	public AccountPayableSetting createNewSettingForAddress(AddressBookMaster master) {
		AccountPayableSetting setting = new AccountPayableSetting();
		setting.setAddressNumber(master.getAddressNumber());
		return setting;
	}
	
	public void deleteSettingOfAddress(AddressBookMaster master) {
		repo.deleteById(master.getAddressNumber());
	}
}
