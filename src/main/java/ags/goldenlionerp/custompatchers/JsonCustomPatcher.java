package ags.goldenlionerp.custompatchers;

import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class JsonCustomPatcher extends CustomPatcher {
	
	private ObjectMapper mapper;
	
	@Autowired 
	public JsonCustomPatcher(@Qualifier("halObjectMapper") ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T patch(T objectToPatch, Map<String, Object> patchRequest) {
		ObjectNode node = mapper.valueToTree(objectToPatch);
		ObjectNode patchedNode = patchJson(node, patchRequest);
		try {
			return (T) mapper.treeToValue(patchedNode, objectToPatch.getClass());
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
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
