package ags.goldenlionerp.application.addresses.address;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ags.goldenlionerp.application.addresses.contact.ContactPersonService;
import ags.goldenlionerp.application.ap.setting.AccountPayableSettingService;
import ags.goldenlionerp.application.ar.setting.AccountReceivableSettingService;

@Service
public class AddressService {

	@Autowired
	private AddressBookRepository repo;
	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	@Autowired
	private ContactPersonService cpService;
	@Autowired
	private AccountReceivableSettingService arServ;
	@Autowired
	private AccountPayableSettingService apServ;
	
	@Transactional
	public AddressBookMaster post(AddressBookMaster entityToCreate) {
		AddressBookMaster created = repo.save(entityToCreate);
		cpService.createNewContactFor(created);
		handleAccountReceivable(null, created);
		handleAccountPayable(null, created);
		return created;
	}
	
	@Transactional
	public AddressBookMaster patch(String addressNumber, Map<String, Object> patchRequest) throws IOException {
		AddressBookMaster entity = repo.findById(addressNumber)
									.orElseThrow(() -> new ResourceNotFoundException());
		
		ObjectNode node = mapper.valueToTree(entity);
		ObjectNode patchedNode = patchJson(node, patchRequest);
		
		EffectiveAddress patchedCurAdd = mapper.treeToValue(patchedNode.get("currentAddress"), EffectiveAddress.class);
		patchedCurAdd.setEffectiveDate(LocalDate.now());
		patchedCurAdd.setPk(new EffectiveAddressPK(addressNumber));
		
		AddressBookMaster patchedEntity = mapper.treeToValue(patchedNode, AddressBookMaster.class);
		patchedEntity.setAddressNumber(addressNumber);
		patchedEntity.setAddressHistory(entity.getAddressHistory());
		patchedEntity.setCurrentAddress(patchedCurAdd);

		patchedEntity = repo.save(patchedEntity);
		handleAccountPayable(entity, patchedEntity);
		handleAccountReceivable(entity, patchedEntity);
		return patchedEntity;
	}
	
	private ObjectNode patchJson(JsonNode nodeToPatch, Map<String, Object> patchRequest) {
		ObjectNode node;
		if (nodeToPatch == null || !(nodeToPatch instanceof ObjectNode))
			node = JsonNodeFactory.instance.objectNode();
		else
			node = ((ObjectNode) nodeToPatch).deepCopy();
		
		for(Entry<String, Object> entry: patchRequest.entrySet()) {
			if (entry.getValue() instanceof Map) {
				JsonNode childNode = node.get(entry.getKey());
				
				@SuppressWarnings("unchecked")
				JsonNode patchedChildNode = patchJson(childNode, (Map<String, Object>) entry.getValue());
				node.set(entry.getKey(), patchedChildNode);
			} else {
				JsonNode valueNode = mapper.valueToTree(entry.getValue());
				node.set(entry.getKey(), valueNode);
			}
		}
		return node;
	}
	
	private void handleAccountReceivable(AddressBookMaster oldEnt, AddressBookMaster newEnt) {
		boolean oldAr = oldEnt == null ? false : oldEnt.getAccountReceivable();
		boolean newAr = newEnt.getAccountReceivable();
		
		if (oldAr == newAr) return;
		
		if (!oldAr && newAr) {
			arServ.createNewSettingForAddress(newEnt);
		}
		if (oldAr && !newAr) {
			arServ.deleteSettingOfAddress(oldEnt);
		}
	}
	
	private void handleAccountPayable(AddressBookMaster oldEnt, AddressBookMaster newEnt) {
		boolean oldAr = oldEnt == null ? false : oldEnt.getAccountPayable();
		boolean newAr = newEnt.getAccountPayable();
		
		if (oldAr == newAr) return;
		
		if (!oldAr && newAr) {
			apServ.createNewSettingForAddress(newEnt);
		}
		if (oldAr && !newAr) {
			apServ.deleteSettingOfAddress(oldEnt);
		}
	}
}
