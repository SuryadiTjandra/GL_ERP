package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.application.addresses.contact.ContactPersonPK;

public class ContactApiTest extends ApiTestBase<ContactPersonPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId.getAddressNumber());
		map.put("sequence", newId.getSequence());
		map.put("mailingName", "Meong super imut");
		map.put("description", "a bwelly kyutie kitti");
		map.put("firstName", "Meong");
		map.put("middleName", "Kucing");
		map.put("lastName", "Imut");
		map.put("salutation", "Nona");
		map.put("gender", "P");
		map.put("type", "C");
		map.put("religion", "05");
		map.put("dateOfBirth", LocalDate.of(1994, 01, 26).toString());
		map.put("placeOfBirth", "Cutietown");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/contacts/";
	}

	@Override
	ContactPersonPK existingId() {
		return new ContactPersonPK("1015", 0);
	}

	@Override
	ContactPersonPK newId() {
		return new ContactPersonPK("1015", 1);
	}
	
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.name").value("Absolute"))
				.andExpect(jsonPath("$.mailingName").value("Absolute"))
				.andExpect(jsonPath("$.description").value(""))
				.andExpect(jsonPath("$.firstName").value(""))
				.andExpect(jsonPath("$.lastName").value(""))
				.andExpect(jsonPath("$.salutation").value(""))
				.andExpect(jsonPath("$.gender").value(""))
				.andExpect(jsonPath("$.religion").value(""))
				.andExpect(jsonPath("$.socialSecurityNumber").value(""))
				.andExpect(jsonPath("$.dateOfBirth").value(Matchers.nullValue()))
				.andExpect(jsonPath("$.type").value("C"))
				;
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.contacts").exists());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
				.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", "")))
				.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.firstName").value(requestObject.getOrDefault("firstName", "")))
				.andExpect(jsonPath("$.middleName").value(requestObject.getOrDefault("middleName", "")))
				.andExpect(jsonPath("$.lastName").value(requestObject.getOrDefault("lastName", "")))
				.andExpect(jsonPath("$.nickname").value(requestObject.getOrDefault("nickname", "")))
				.andExpect(jsonPath("$.salutation").value(requestObject.getOrDefault("salutation", "")))
				.andExpect(jsonPath("$.gender").value(requestObject.getOrDefault("gender", "")))
				.andExpect(jsonPath("$.religion").value(requestObject.getOrDefault("religion", "")))
				.andExpect(jsonPath("$.type").value(requestObject.getOrDefault("type", "")))
				.andExpect(jsonPath("$.dateOfBirth").value(requestObject.getOrDefault("dateOfBirth", null)))
				.andExpect(jsonPath("$.placeOfBirth").value(requestObject.getOrDefault("placeOfBirth", "")))
				.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject.getOrDefault("socialSecurityNumber", "")))
				.andExpect(jsonPath("$.socialSecurityDate").value(requestObject.getOrDefault("socialSecurityDate", null)))
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
				.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", (String) JsonPath.read(beforePatch, "$.name"))))
				.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", (String) JsonPath.read(beforePatch, "$.mailingName"))))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andExpect(jsonPath("$.firstName").value(requestObject.getOrDefault("firstName", (String) JsonPath.read(beforePatch, "$.firstName"))))
				.andExpect(jsonPath("$.middleName").value(requestObject.getOrDefault("middleName", (String) JsonPath.read(beforePatch, "$.middleName"))))
				.andExpect(jsonPath("$.lastName").value(requestObject.getOrDefault("lastName", (String) JsonPath.read(beforePatch, "$.lastName"))))
				.andExpect(jsonPath("$.nickname").value(requestObject.getOrDefault("nickname", (String) JsonPath.read(beforePatch, "$.nickname"))))
				.andExpect(jsonPath("$.salutation").value(requestObject.getOrDefault("salutation", (String) JsonPath.read(beforePatch, "$.salutation"))))
				.andExpect(jsonPath("$.gender").value(requestObject.getOrDefault("gender", (String) JsonPath.read(beforePatch, "$.gender"))))
				.andExpect(jsonPath("$.religion").value(requestObject.getOrDefault("religion", (String) JsonPath.read(beforePatch, "$.religion"))))
				.andExpect(jsonPath("$.type").value(requestObject.getOrDefault("type", (String) JsonPath.read(beforePatch, "$.type"))))
				.andExpect(jsonPath("$.dateOfBirth").value(requestObject.getOrDefault("dateOfBirth", (String) JsonPath.read(beforePatch, "$.dateOfBirth"))))
				.andExpect(jsonPath("$.placeOfBirth").value(requestObject.getOrDefault("placeOfBirth", (String) JsonPath.read(beforePatch, "$.placeOfBirth"))))
				.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject.getOrDefault("socialSecurityNumber", (String) JsonPath.read(beforePatch, "$.socialSecurityNumber"))))
				.andExpect(jsonPath("$.socialSecurityDate").value(requestObject.getOrDefault("socialSecurityDate", (String) JsonPath.read(beforePatch, "$.socialSecurityDate"))))
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
		String linkUrl = JsonPath.read(headerResp, "$._links.contacts.href");

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
				.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", "")))
				.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.firstName").value(requestObject.getOrDefault("firstName", "")))
				.andExpect(jsonPath("$.middleName").value(requestObject.getOrDefault("middleName", "")))
				.andExpect(jsonPath("$.lastName").value(requestObject.getOrDefault("lastName", "")))
				.andExpect(jsonPath("$.nickname").value(requestObject.getOrDefault("nickname", "")))
				.andExpect(jsonPath("$.salutation").value(requestObject.getOrDefault("salutation", "")))
				.andExpect(jsonPath("$.gender").value(requestObject.getOrDefault("gender", "")))
				.andExpect(jsonPath("$.religion").value(requestObject.getOrDefault("religion", "")))
				.andExpect(jsonPath("$.type").value(requestObject.getOrDefault("type", "")))
				.andExpect(jsonPath("$.dateOfBirth").value(requestObject.getOrDefault("dateOfBirth", null)))
				.andExpect(jsonPath("$.placeOfBirth").value(requestObject.getOrDefault("placeOfBirth", "")))
				.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject.getOrDefault("socialSecurityNumber", "")))
				.andExpect(jsonPath("$.socialSecurityDate").value(requestObject.getOrDefault("socialSecurityDate", null)))
				.andReturn().getResponse().getContentAsString();
		assertCreationInfo(newRes);
		
		//check content of existing object
		String updResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.name").value(requestObject2.getOrDefault("name", (String) JsonPath.read(beforeUpdate, "$.name"))))
				.andExpect(jsonPath("$.mailingName").value(requestObject2.getOrDefault("mailingName", (String) JsonPath.read(beforeUpdate, "$.mailingName"))))
				.andExpect(jsonPath("$.description").value(requestObject2.getOrDefault("description", (String) JsonPath.read(beforeUpdate, "$.description"))))
				.andExpect(jsonPath("$.firstName").value(requestObject2.getOrDefault("firstName", (String) JsonPath.read(beforeUpdate, "$.firstName"))))
				.andExpect(jsonPath("$.middleName").value(requestObject2.getOrDefault("middleName", (String) JsonPath.read(beforeUpdate, "$.middleName"))))
				.andExpect(jsonPath("$.lastName").value(requestObject2.getOrDefault("lastName", (String) JsonPath.read(beforeUpdate, "$.lastName"))))
				.andExpect(jsonPath("$.nickname").value(requestObject2.getOrDefault("nickname", (String) JsonPath.read(beforeUpdate, "$.nickname"))))
				.andExpect(jsonPath("$.salutation").value(requestObject2.getOrDefault("salutation", (String) JsonPath.read(beforeUpdate, "$.salutation"))))
				.andExpect(jsonPath("$.gender").value(requestObject2.getOrDefault("gender", (String) JsonPath.read(beforeUpdate, "$.gender"))))
				.andExpect(jsonPath("$.religion").value(requestObject2.getOrDefault("religion", (String) JsonPath.read(beforeUpdate, "$.religion"))))
				.andExpect(jsonPath("$.type").value(requestObject2.getOrDefault("type", (String) JsonPath.read(beforeUpdate, "$.type"))))
				.andExpect(jsonPath("$.dateOfBirth").value(requestObject2.getOrDefault("dateOfBirth", (String) JsonPath.read(beforeUpdate, "$.dateOfBirth"))))
				.andExpect(jsonPath("$.placeOfBirth").value(requestObject2.getOrDefault("placeOfBirth", (String) JsonPath.read(beforeUpdate, "$.placeOfBirth"))))
				.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject2.getOrDefault("socialSecurityNumber", (String) JsonPath.read(beforeUpdate, "$.socialSecurityNumber"))))
				.andExpect(jsonPath("$.socialSecurityDate").value(requestObject2.getOrDefault("socialSecurityDate", (String) JsonPath.read(beforeUpdate, "$.socialSecurityDate"))))
				.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(updResult, beforeUpdate);
	}


}
