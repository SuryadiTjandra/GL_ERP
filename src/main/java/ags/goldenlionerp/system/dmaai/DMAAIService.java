package ags.goldenlionerp.system.dmaai;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.lang.reflect.Field;

@Service
public class DMAAIService {

	@Autowired private DMAAIHeaderRepository headRepo;
	@Autowired private DMAAIDetailRepository detailRepo;

	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	
	public void saveDetailsToHeader(int headerId, List<Map<String, Object>> requests) {
		//check if the header exists first
		DMAAIHeader header = headRepo.findById(headerId)
				.orElseThrow(() -> new ResourceNotFoundException("The indicated DMAAI Header does not exist"));
		
		//set the dmaaiNo of the details to the indicated header
		requests.forEach(request -> request.put("dmaaiNo", headerId));
		
		//separate the details into new and existing
		Map<Boolean, Collection<Map<String, Object>>> map = separateRequestIntoNewAndExisting(header.getDetails(), requests);
		Collection<Map<String, Object>> news = map.get(true);
		Collection<Map<String, Object>> existings = map.get(false);
		
		//the new ones can be immediately saved
		detailRepo.saveAll(news.stream().map(m -> construct(m)).collect(toSet()));
		
		//patch the existing ones
		Collection<DMAAIDetail> updated = new HashSet<>();
		for (Map<String, Object> existing: existings) {
			DMAAIDetail current = header.getDetails().stream()
									.filter(cur -> cur.getPk().equals(constructPk(existing)))
									.findFirst().get();
			updated.add(patch(current, existing));
		}
		detailRepo.saveAll(updated);
	}
	
	private Map<Boolean, Collection<Map<String, Object>>> separateRequestIntoNewAndExisting(Collection<DMAAIDetail> currents, Collection<Map<String, Object>> requests){
		Set<DMAAIDetailPK> currentPks = currents.stream().map(DMAAIDetail::getPk).collect(toSet());
		
		Set<Map<String, Object>> newRequest = requests.stream()
										.filter(request -> !currentPks.contains(constructPk(request)))
										.collect(toSet());
		Set<Map<String, Object>> existingRequest = requests.stream()
										.filter(request -> currentPks.contains(constructPk(request)))
										.collect(toSet());
		Map<Boolean, Collection<Map<String, Object>>> map = new HashMap<>();
		map.put(true, newRequest);
		map.put(false, existingRequest);
		return map;
	}
	
	private DMAAIDetailPK constructPk(Map<String, Object> request) {
		return new DMAAIDetailPK(
				(Integer) request.get("dmaaiNo"),
				(String) request.get("companyId"),
				(String) request.get("documentType"),
				(String) request.get("documentOrderType"),
				(String) request.get("glClass"),
				(String) request.get("manufacturingCostType")
		);
	}
	
	private DMAAIDetail construct(Map<String, Object> request) {
		
		try {
			String reqJson = mapper.writeValueAsString(request);
			return mapper.readValue(reqJson, DMAAIDetail.class);
		} catch (IOException e) {
			//should never happen
			e.printStackTrace();
			return null;
		}
	}
	
	private DMAAIDetail patch(DMAAIDetail current, Map<String, Object>  request) {
		//BeanWrapper detail = new BeanWrapperImpl(current);
		//detail.setPropertyValues(new MutablePropertyValues(request), true, true);
		//return (DMAAIDetail) detail.getWrappedInstance();
		
		for (String key: request.keySet()) {
			try {
				Field field = DMAAIDetail.class.getDeclaredField(key);
				field.setAccessible(true);
				field.set(current, request.get(key));
			} catch (SecurityException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				//skip
			} 
		}
		
		/*if (request.containsKey("businessUnitId"))
			current.setBusinessUnitId((String) request.get("businessUnitId"));
		if (request.containsKey("objectAccountCode"))
			current.setObjectAccountCode((String) request.get("objectAccountCode"));
		if (request.containsKey("subsidiaryCode"))
			current.setSubsidiaryCode((String) request.get("subsidiaryCode"));
		if (request.containsKey("description1"))
			current.setDescription1((String) request.get("description1"));
		if (request.containsKey("description2"))
			current.setDescription2((String) request.get("description2"));
		if (request.containsKey("description3"))
			current.setDescription3((String) request.get("description3"));
		if (request.containsKey("description4"))
			current.setDescription4((String) request.get("description4"));*/
		
		return current;
	}
	
	public void deleteHeaderAndAllDetails(int headerId) {
		DMAAIHeader head = headRepo.findById(headerId).orElseThrow(() -> new ResourceNotFoundException());
		
		detailRepo.deleteAll(head.getDetails());
		headRepo.delete(head);
	}
}
