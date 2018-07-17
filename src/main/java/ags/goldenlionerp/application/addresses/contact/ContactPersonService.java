package ags.goldenlionerp.application.addresses.contact;

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
public class ContactPersonService extends ParentChildService<AddressBookMaster, ContactPerson, String, ContactPersonPK>{

	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	
	@Autowired
	public ContactPersonService(AddressBookRepository addressRepo, ContactPersonRepository contactRepo) {
		super(addressRepo, contactRepo);
	}
	
	public Collection<ContactPerson> saveContactsForAddress(String addressNumber, Collection<Map<String, Object>> patchRequests) {
		return super.saveChildrenToParent(addressNumber, patchRequests);
	}
	
	@Override
	protected void setIdToMatchParent(String addressNumber, Map<String, Object> childRequest) {
		childRequest.put("addressNumber", addressNumber);
	}

	@Override
	protected ContactPersonPK constructChildId(Map<String, Object> childRequest) {
		return new ContactPersonPK(
				childRequest.get("addressNumber").toString(),
				Integer.parseInt(childRequest.get("sequence").toString())
			);
	}

	@Override
	protected ContactPerson constructChild(Map<String, Object> childRequest) {
		
		return mapper.convertValue(childRequest, ContactPerson.class);
	}

	
}
