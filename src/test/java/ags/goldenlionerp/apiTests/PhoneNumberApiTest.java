package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.application.addresses.phone.PhoneNumberPK;

public class PhoneNumberApiTest extends ApiTestBase<PhoneNumberPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId().getAddressNumber());
		map.put("sequence", newId().getSequence());
		map.put("countryCode", "+62");
		map.put("areaCode", "31");
		map.put("phoneNumber", "3920553");
		map.put("phoneType", "RUMAH");
		map.put("description", "Nomor telpon rumah");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/phoneNumbers/";
	}

	@Override
	PhoneNumberPK existingId() {
		return new PhoneNumberPK("1015", 1);
	}

	@Override
	PhoneNumberPK newId() {
		return new PhoneNumberPK("1015", 5);
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.countryCode").value("+62"))
				.andExpect(jsonPath("$.areaCode").value(""))
				.andExpect(jsonPath("$.phoneNumber").value("031-5470614"))
				.andExpect(jsonPath("$.phoneType").value("KANTOR"))
				.andExpect(jsonPath("$.description").value("Kantor"))
		;
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$._embedded.phoneNumbers").exists());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
//				/.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
				.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", "")))
				.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", "")))
				.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", "")))
				.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
		
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("description");
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", (String) JsonPath.read(beforePatch, "$.countryCode"))))
				.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", (String) JsonPath.read(beforePatch, "$.areaCode"))))
				.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", (String) JsonPath.read(beforePatch, "$.phoneNumber"))))
				.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", (String) JsonPath.read(beforePatch, "$.phoneType"))))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}

	@Test @Rollback
	public void massCreateAndUpdateTest() throws Exception {
		//get existing entity data before post request
		String beforeUpdate = mockMvc.perform(get(baseUrl + existingId))
				.andReturn().getResponse().getContentAsString();
		
		//get url for post request
		String headerUrl = new AddressApiTest().baseUrl();
		String headerResp = mockMvc.perform(get(headerUrl + existingId().getAddressNumber()))
								.andReturn().getResponse().getContentAsString();
		String linkUrl = JsonPath.read(headerResp, "$._links.phoneNumbers.href");

		//construct an additional request object for post request
		Map<String, Object> requestObject2 = new HashMap<>(requestObject);
		requestObject2.put("addressNumber", existingId().getAddressNumber() + 1);
		requestObject2.put("sequence", existingId().getSequence());
		
		List<Map<String, Object>> list = Arrays.asList(requestObject, requestObject2);
		
		//post request
		mockMvc.perform(post(linkUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(list)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		em.flush(); em.clear();
		
		//check content of new entity
		String newRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
				.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", "")))
				.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", "")))
				.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", "")))
				.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andReturn().getResponse().getContentAsString();
		assertCreationInfo(newRes);
		
		//check content of existing object
		String updResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", (String) JsonPath.read(beforeUpdate, "$.countryCode"))))
				.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", (String) JsonPath.read(beforeUpdate, "$.areaCode"))))
				.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", (String) JsonPath.read(beforeUpdate, "$.phoneNumber"))))
				.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", (String) JsonPath.read(beforeUpdate, "$.phoneType"))))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdate, "$.description"))))
				.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(updResult, beforeUpdate);
	}

}
