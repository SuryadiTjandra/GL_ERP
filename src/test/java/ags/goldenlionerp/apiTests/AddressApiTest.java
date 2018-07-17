package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class AddressApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId());
		map.put("searchType", "I");
		map.put("longAddressNumber", "ABCDEFGH");
		map.put("name", "Abcd");
		map.put("accountReceivable", false);
		map.put("accountPayable", true);
		map.put("businessUnitId", "110");
		
		Map<String, Object> catMap = new HashMap<>();
		catMap.put("categoryCode01", "AO");
		map.put("categories", catMap);
		
		Map<String, Object> relMap = new HashMap<>();
		relMap.put("salesperson", "1010");
		relMap.put("relatedAddress2", "1000");
		map.put("relatedAddresses", relMap);
		
		Map<String, Object> eaMap = new HashMap<>();
		eaMap.put("addressNumber", newId);
		eaMap.put("address1", "Jalan Test no 3");
		eaMap.put("address2", "Ruko Testes");
		eaMap.put("country", "ID");
		map.put("currentAddress", eaMap);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/addresses/";
	}

	@Override
	String existingId() {
		return "1015";
	}

	@Override
	String newId() {
		return "ABCD";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.addressNumber").value(existingId))
				.andExpect(jsonPath("$.searchType").value("C"))
				.andExpect(jsonPath("$.longAddressNumber").value("Absolute"))
				.andExpect(jsonPath("$.name").value("Absolute"))
				.andExpect(jsonPath("$.accountReceivable").value(true))
				.andExpect(jsonPath("$.parentAddressNumber").value("1015"))
				.andExpect(jsonPath("$.businessUnitId").value("110"))
				.andExpect(jsonPath("$.relatedAddresses.salesperson").value(""))
				.andExpect(jsonPath("$.relatedAddresses.relatedAddress2").value(""))
				.andExpect(jsonPath("$.categories.categoryCode01").value(""))
				.andExpect(jsonPath("$.currentAddress.address1").value("Hitech Mall Lt.Dasar Blok E 26-27"))
				.andExpect(jsonPath("$.currentAddress.address3").value("Surabaya "))
				.andExpect(jsonPath("$.currentAddress.country").value(""))
				.andExpect(jsonPath("$.addressHistory").doesNotExist());
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.addresses").exists());

	}

	@SuppressWarnings("unchecked")
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
		
		Map<String, Object> catMap = (Map<String, Object>) requestObject.get("categories");
		Map<String, Object> relMap = (Map<String, Object>) requestObject.get("relatedAddresses");
		Map<String, Object> eaMap = (Map<String, Object>) requestObject.get("currentAddress");
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.addressNumber").value(newId))
				.andExpect(jsonPath("$.searchType").value(requestObject.getOrDefault("searchType", "")))
				.andExpect(jsonPath("$.longAddressNumber").value(requestObject.getOrDefault("longAddressNumber", "")))
				.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", "")))
				.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", "")))
				.andExpect(jsonPath("$.accountReceivable").value(requestObject.getOrDefault("accountReceivable", false)))
				.andExpect(jsonPath("$.accountPayable").value(requestObject.getOrDefault("accountPayable", false)))
				.andExpect(jsonPath("$.parentAddressNumber").value(requestObject.getOrDefault("parentAddressNumber", newId)))
				.andExpect(jsonPath("$.employee").value(requestObject.getOrDefault("employee", false)))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", "")))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId", "")))
				.andExpect(jsonPath("$.categories.categoryCode01").value(catMap.getOrDefault("categoryCode01", "")))
				.andExpect(jsonPath("$.categories.categoryCode02").value(catMap.getOrDefault("categoryCode02", "")))
				.andExpect(jsonPath("$.categories.categoryCode05").value(catMap.getOrDefault("categoryCode05", "")))
				.andExpect(jsonPath("$.relatedAddresses.salesperson").value(relMap.getOrDefault("salesperson", "")))
				.andExpect(jsonPath("$.relatedAddresses.relatedAddress5").value(relMap.getOrDefault("relatedAddress5", "")))
				.andExpect(jsonPath("$.relatedAddresses.relatedAddress9").value(relMap.getOrDefault("relatedAddress9", "")))
				.andExpect(jsonPath("$.currentAddress.addressNumber").value(newId))
				.andExpect(jsonPath("$.currentAddress.effectiveDate").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.currentAddress.address1").value(eaMap.getOrDefault("address1", "")))
				.andExpect(jsonPath("$.currentAddress.address2").value(eaMap.getOrDefault("address2", "")))
				.andExpect(jsonPath("$.currentAddress.address3").value(eaMap.getOrDefault("address3", "")))
				.andExpect(jsonPath("$.currentAddress.address4").value(eaMap.getOrDefault("address4", "")))
				.andExpect(jsonPath("$.currentAddress.country").value(eaMap.getOrDefault("country", "")))
				.andExpect(jsonPath("$.currentAddress.city").value(eaMap.getOrDefault("city", "")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

		//check contact is created
		checkContactCreation(getRes);
	}
	
	private void checkContactCreation(String createdAddressJson) throws Exception {
		String contactLink = JsonPath.read(createdAddressJson, "$._links.contacts.href");
		String name = JsonPath.read(createdAddressJson, "$.name");
		String mailingName = JsonPath.read(createdAddressJson, "$.mailingName");
		
		String getRes = mockMvc.perform(get(contactLink))
							.andDo(MockMvcResultHandlers.print())
							.andExpect(jsonPath("$._embedded.contacts").exists())
							.andExpect(jsonPath("$._embedded.contacts.length()").value(1))
							.andExpect(jsonPath("$._embedded.contacts[0].name").value(name))
							.andExpect(jsonPath("$._embedded.contacts[0].mailingName").value(mailingName))
							.andExpect(jsonPath("$._embedded.contacts[0].type").value("C"))
							.andReturn().getResponse().getContentAsString();
		
		String contactJSon = mapper.writeValueAsString(
								JsonPath.read(getRes, "$._embedded.contacts[0]")
							);
		assertCreationInfo(contactJSon);
	}

	@SuppressWarnings("unchecked")
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
		
		Map<String, Object> catMap = (Map<String, Object>) requestObject.get("categories");
		Map<String, Object> relMap = (Map<String, Object>) requestObject.get("relatedAddresses");
		Map<String, Object> eaMap = (Map<String, Object>) requestObject.get("currentAddress");
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.addressNumber").value(existingId))
				.andExpect(jsonPath("$.searchType").value(requestObject.getOrDefault("searchType", (String) JsonPath.read(beforePatch, "$.searchType"))))
				.andExpect(jsonPath("$.longAddressNumber").value(requestObject.getOrDefault("longAddressNumber", (String) JsonPath.read(beforePatch, "$.longAddressNumber"))))
				.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", (String) JsonPath.read(beforePatch, "$.name"))))
				.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", (String) JsonPath.read(beforePatch, "$.mailingName"))))
				.andExpect(jsonPath("$.accountReceivable").value(requestObject.getOrDefault("accountReceivable",(Boolean) JsonPath.read(beforePatch, "$.accountReceivable"))))
				.andExpect(jsonPath("$.accountPayable").value(requestObject.getOrDefault("accountPayable", (Boolean) JsonPath.read(beforePatch, "$.accountPayable"))))
				.andExpect(jsonPath("$.parentAddressNumber").value(requestObject.getOrDefault("parentAddressNumber", (String) JsonPath.read(beforePatch, "$.parentAddressNumber"))))
				.andExpect(jsonPath("$.employee").value(requestObject.getOrDefault("employee", (Boolean) JsonPath.read(beforePatch, "$.employee"))))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", (String) JsonPath.read(beforePatch, "$.taxId"))))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId", (String) JsonPath.read(beforePatch, "$.businessUnitId"))))
				.andExpect(jsonPath("$.categories.categoryCode01").value(catMap.getOrDefault("categoryCode01", (String) JsonPath.read(beforePatch, "$.categories.categoryCode01"))))
				.andExpect(jsonPath("$.categories.categoryCode02").value(catMap.getOrDefault("categoryCode02", (String) JsonPath.read(beforePatch, "$.categories.categoryCode02"))))
				.andExpect(jsonPath("$.categories.categoryCode05").value(catMap.getOrDefault("categoryCode05", (String) JsonPath.read(beforePatch, "$.categories.categoryCode05"))))
				.andExpect(jsonPath("$.relatedAddresses.salesperson").value(relMap.getOrDefault("salesperson", (String) JsonPath.read(beforePatch, "$.relatedAddresses.salesperson"))))
				.andExpect(jsonPath("$.relatedAddresses.relatedAddress5").value(relMap.getOrDefault("relatedAddress5", (String) JsonPath.read(beforePatch, "$.relatedAddresses.relatedAddress5"))))
				.andExpect(jsonPath("$.relatedAddresses.relatedAddress9").value(relMap.getOrDefault("relatedAddress9", (String) JsonPath.read(beforePatch, "$.relatedAddresses.relatedAddress9"))))
				.andExpect(jsonPath("$.currentAddress.addressNumber").value(existingId))
				.andExpect(jsonPath("$.currentAddress.effectiveDate").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.currentAddress.address1").value(eaMap.getOrDefault("address1", (String) JsonPath.read(beforePatch, "$.currentAddress.address1"))))
				.andExpect(jsonPath("$.currentAddress.address2").value(eaMap.getOrDefault("address2", (String) JsonPath.read(beforePatch, "$.currentAddress.address2"))))
				.andExpect(jsonPath("$.currentAddress.address3").value(eaMap.getOrDefault("address3", (String) JsonPath.read(beforePatch, "$.currentAddress.address3"))))
				.andExpect(jsonPath("$.currentAddress.address4").value(eaMap.getOrDefault("address4", (String) JsonPath.read(beforePatch, "$.currentAddress.address4"))))
				.andExpect(jsonPath("$.currentAddress.country").value(eaMap.getOrDefault("country", (String) JsonPath.read(beforePatch, "$.currentAddress.country"))))
				.andExpect(jsonPath("$.currentAddress.city").value(eaMap.getOrDefault("city", (String) JsonPath.read(beforePatch, "$.currentAddress.city"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);

	}

	

}
