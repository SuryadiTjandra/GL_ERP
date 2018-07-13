package ags.goldenlionerp.application.addresses.address;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AddressService {

	@Autowired
	private AddressBookRepository repo;
	@Autowired
	private EffectiveAddressRepository eaRepo;
	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	
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

		return repo.save(patchedEntity);
		
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
}
