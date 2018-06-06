package ags.goldenlionerp.apiTests;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.company.Company;

@Transactional
public class CompanyApiTest extends ApiTestBase<String> {
	
	@Override
	Map<String, String> requestObject() {
		Map<String, String> map = new HashMap<>();
		map.put("companyId", newId);
		map.put("description", "TESTTEST");
		map.put("currencyCodeBase", "IDR");
		map.put("businessPartnerId", "");
		map.put("fiscalDatePattern", "R");
		map.put("currentFiscalYear", "0");
		map.put("currentAccountingPeriod", "0");
		map.put("currentPayablePeriod", "0");
		map.put("currentReceivablePeriod", "0");
		map.put("currentInventoryPeriod", "0");
		map.put("computerId", "YOOO");
		return map;
	}
	@Override String baseUrl() { return "/api/companies/"; }
	@Override String existingId() { return "00000"; }
	@Override String newId() { return "12345"; }

	@Test
	public void getTestCollection() throws Exception {
		String jsonResult = mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Resources<Company> res = mapper.readValue(jsonResult, new TypeReference<Resources<Company>>() {});
		
		Collection<Company> list = res.getContent();
		assertEquals(2, list.size());
		assertTrue(list.stream().anyMatch(co -> co.getCompanyId().equals("00000")));
		assertTrue(list.stream().anyMatch(co -> co.getDescription().equals("AMTEK GROUP")));

	}
	
	@Test
	public void getTestSingle() throws Exception{
		String jsonResult = mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Company comp = mapper.readValue(jsonResult, Company.class);
		assertEquals(existingId, comp.getCompanyId());
		assertEquals("AMTEK GROUP", comp.getDescription());
	}
	
	
	@Test
	@Rollback
	public void createTestWithPost() throws Exception {		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String getResult = mockMvc.perform(get(baseUrl + newId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.companyId").value(newId))
				.andExpect(jsonPath("$.inputUserId").value("login not yet"))
				.andExpect(jsonPath("$.inputDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.fiscalDatePattern").value(requestObject.get("fiscalDatePattern")))
				.andExpect(jsonPath("$.currentReceivablePeriod").value(requestObject.get("currentReceivablePeriod")))
				.andReturn().getResponse().getContentAsString();

		assertEquals(
				(String) JsonPath.read(getResult, "$.lastUpdateDateTime"),
				(String) JsonPath.read(getResult, "$.inputDateTime")
		);
	}
	
	@Test
	@Rollback
	public void createTestWithPut() throws Exception {	
		mockMvc.perform(put(baseUrl + newId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Test
	@Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}
	
	@Test
	@Rollback
	public void updateTestWithPatch() throws Exception {
		mockMvc.perform(patch(baseUrl + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		EntityManager manager = wac.getBean(EntityManager.class);
		manager.flush();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.companyId").value(existingId))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.fiscalDatePattern").value(requestObject.get("fiscalDatePattern")))
				.andExpect(jsonPath("$.currentReceivablePeriod").value(requestObject.get("currentReceivablePeriod")))
				.andReturn().getResponse().getContentAsString();
		
		assertNotEquals(
				JsonPath.read(getResult, "$.lastUpdateDateTime"),
				JsonPath.read(getResult, "$.inputDateTime")
		);
	}
	
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(baseUrl + existingId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
				
		mockMvc.perform(get(baseUrl + existingId))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

}
