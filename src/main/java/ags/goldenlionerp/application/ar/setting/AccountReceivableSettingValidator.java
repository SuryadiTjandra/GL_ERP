package ags.goldenlionerp.application.ar.setting;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;

@Component("beforeSaveAccountReceivableSettingValidator")
public class AccountReceivableSettingValidator implements Validator {

	@Autowired
	private AddressBookRepository adRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AccountReceivableSetting.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "addressNumber", "addressNumber.required");
		AccountReceivableSetting setting = (AccountReceivableSetting) target;
		
		Optional<AddressBookMaster> master = adRepo.findById(setting.getAddressNumber());
		if (!master.isPresent())
			errors.rejectValue("addressNumber", "does.not.exist");
		if (master.get().getAccountReceivable() == false)
			errors.rejectValue("addressNumber", "setting.does.not.exist");
		
	}

}
