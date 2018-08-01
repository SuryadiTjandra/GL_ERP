package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import ags.goldenlionerp.application.setups.taxcode.TaxRulePK;

public class TaxRuleApiTest extends ApiTestBase<TaxRulePK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("taxCode", newId().getTaxCode());
		map.put("effectiveDate", newId().getEffectiveDate());
		map.put("expiredDate", LocalDate.of(2025, 5, 16));
		map.put("description", "Test Tax");
		map.put("taxAuthority1", "");
		map.put("taxClass1", "MBR");
		map.put("taxPercentage1", 20.25);
		map.put("taxClass2", "INFG");
		map.put("taxPercentage2", 12.33);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/taxRules/";
	}

	@Override
	TaxRulePK existingId() {
		return new TaxRulePK("INCL", LocalDate.of(2009, 1, 1));
	}

	@Override
	TaxRulePK newId() {
		return new TaxRulePK("NOTAX", LocalDate.of(2018, 7, 30));
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.taxCode").value(existingId.getTaxCode()))
				.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
				.andExpect(jsonPath("$.expiredDate").value(LocalDate.of(2025, 4, 1).toString()))
				.andExpect(jsonPath("$.description").value("Harga Include PPn"))
				.andExpect(jsonPath("$.taxAuthority1").value(""))
				.andExpect(jsonPath("$.taxClass1").value(""))
				.andExpect(jsonPath("$.taxPercentage1").value(10.00))
				.andExpect(jsonPath("$.taxAuthority2").value(""))
				.andExpect(jsonPath("$.taxClass2").value(""))
				.andExpect(jsonPath("$.taxPercentage2").value(0.00))
				.andExpect(jsonPath("$.taxAllowance").value(true))
				.andExpect(jsonPath("$.documentType").value(""));
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.taxRules").exists());
		
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
				.andExpect(jsonPath("$.taxCode").value(newId.getTaxCode()))
				.andExpect(jsonPath("$.effectiveDate").value(newId.getEffectiveDate().toString()))
				.andExpect(jsonPath("$.expiredDate").value(requestObject.get("expiredDate").toString()))
				.andExpect(jsonPath("$.taxAuthority1").value(requestObject.getOrDefault("taxAuthority1", "")))
				.andExpect(jsonPath("$.taxClass1").value(requestObject.getOrDefault("taxClass1", "")))
				.andExpect(jsonPath("$.taxPercentage1").value(requestObject.getOrDefault("taxPercentage1", 0)))
				.andExpect(jsonPath("$.taxAuthority2").value(requestObject.getOrDefault("taxAuthority2", "")))
				.andExpect(jsonPath("$.taxClass2").value(requestObject.getOrDefault("taxClass2", "")))
				.andExpect(jsonPath("$.taxPercentage2").value(requestObject.getOrDefault("taxPercentage2", 0)))
				.andExpect(jsonPath("$.taxAllowance").value(requestObject.getOrDefault("taxAllowance", false)))
				.andExpect(jsonPath("$.documentType").value(requestObject.getOrDefault("documentType", "")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
		
	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(put(baseUrl + newId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		mockMvc.perform(delete(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	
		mockMvc.perform(get(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.taxCode").value(existingId.getTaxCode()))
				.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
				.andExpect(jsonPath("$.expiredDate").value(requestObject.getOrDefault("expiredDate", JsonPath.read(beforePatch, "$.expiredDate")).toString()))
				.andExpect(jsonPath("$.taxAuthority1").value(requestObject.getOrDefault("taxAuthority1", JsonPath.read(beforePatch, "$.taxAuthority1"))))
				.andExpect(jsonPath("$.taxClass1").value(requestObject.getOrDefault("taxClass1", JsonPath.read(beforePatch, "$.taxClass1"))))
				.andExpect(jsonPath("$.taxPercentage1").value(requestObject.getOrDefault("taxPercentage1", JsonPath.read(beforePatch, "$.taxPercentage1"))))
				.andExpect(jsonPath("$.taxAuthority2").value(requestObject.getOrDefault("taxAuthority2", JsonPath.read(beforePatch, "$.taxAuthority2"))))
				.andExpect(jsonPath("$.taxClass2").value(requestObject.getOrDefault("taxClass2", JsonPath.read(beforePatch, "$.taxClass2"))))
				.andExpect(jsonPath("$.taxPercentage2").value(requestObject.getOrDefault("taxPercentage2", JsonPath.read(beforePatch, "$.taxPercentage2"))))
				.andExpect(jsonPath("$.taxAllowance").value(requestObject.getOrDefault("taxAllowance", JsonPath.read(beforePatch, "$.taxAllowance"))))
				.andExpect(jsonPath("$.documentType").value(requestObject.getOrDefault("documentType", JsonPath.read(beforePatch, "$.documentType"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);

	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	

}
