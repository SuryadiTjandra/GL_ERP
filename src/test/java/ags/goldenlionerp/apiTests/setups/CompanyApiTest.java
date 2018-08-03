package ags.goldenlionerp.apiTests.setups;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;
import ags.goldenlionerp.application.setups.company.Company;

@Transactional
public class CompanyApiTest extends ApiTestBaseNew<String> {
	
	@Override
	protected Map<String, Object> requestObject() {
		Map<String, Object> map = new HashMap<>();
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
	@Override protected String baseUrl() { return "/api/companies/"; }
	@Override protected String existingId() { return "00000"; }
	@Override protected String newId() { return "12345"; }

	@Test
	public void getTestCollection() throws Exception {
		String jsonResult = performer.performGet(baseUrl)
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
		String jsonResult = performer.performGet(baseUrl + existingId)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Company comp = mapper.readValue(jsonResult, Company.class);
		assertEquals(existingId, comp.getCompanyId());
		assertEquals("AMTEK GROUP", comp.getDescription());
	}
	
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.companyId").value(newId))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.fiscalDatePattern").value(requestObject.get("fiscalDatePattern")))
		.andExpect(jsonPath("$.currentReceivablePeriod").value(requestObject.get("currentReceivablePeriod")))
		;
	}
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.companyId").value(existingId))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.fiscalDatePattern").value(requestObject.get("fiscalDatePattern")))
		.andExpect(jsonPath("$.currentReceivablePeriod").value(requestObject.get("currentReceivablePeriod")));
		
	}
	
	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		//do nothing
	}
	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		// do nothing
	}

}
