package ags.goldenlionerp.application.addresses.phone;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.basecomponents.ParentChildService;

@Service
public class PhoneNumberService extends ParentChildService<AddressBookMaster, PhoneNumber, String, PhoneNumberPK>{

	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	
	public Collection<PhoneNumber> savePhoneNumbersForAddress(String addressNumber, Collection<Map<String, Object>> childRequests){
		return super.saveChildrenToParent(addressNumber, childRequests);
	}
	
	@Autowired
	protected PhoneNumberService(AddressBookRepository parentRepo, PhoneNumberRepository childRepo) {
		super(parentRepo, childRepo);
	}

	@Override
	protected void setIdToMatchParent(String parentId, Map<String, Object> childRequest) {
		childRequest.put("addressNumber", parentId);
	}

	@Override
	protected PhoneNumberPK constructChildId(Map<String, Object> childRequest) {
		return new PhoneNumberPK(
				(String) childRequest.get("addressNumber"), 
				(Integer) childRequest.get("sequence"));
	}

	@Override
	protected PhoneNumber constructChild(Map<String, Object> childRequest) {
		return mapper.convertValue(childRequest, PhoneNumber.class);
	}

}
