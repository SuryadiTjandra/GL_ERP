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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.system.currencyconversion.CurrencyConversionPK;

public class CurrencyConversionApiTest extends ApiTestBase<CurrencyConversionPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("effectiveDate", newId().getEffectiveDate());
		map.put("fromCurrency", newId().getFromCurrency());
		map.put("toCurrency", newId().getToCurrency());
		//map.put("description", "TEST");
		map.put("multiplyFactor", "12.123");
		map.put("divisionFactor", 32.321);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/currencyConversions/";
	}

	
	@Override
	CurrencyConversionPK existingId() {
		return new CurrencyConversionPK(LocalDate.of(2018, 7, 31), "IDR", "SGD");
	}

	@Override
	CurrencyConversionPK newId() {
		return new CurrencyConversionPK(LocalDate.of(2018, 7, 31), "USD", "CNY");
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
				.andExpect(jsonPath("$.fromCurrency").value(existingId.getFromCurrency()))
				.andExpect(jsonPath("$.toCurrency").value(existingId.getToCurrency()))
				.andExpect(jsonPath("$.multiplyFactor").value(1))
				.andExpect(jsonPath("$.divisionFactor").value(10000))
				.andExpect(jsonPath("$.description").value("Rupiah to SGD"));
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.currencyConversions").exists());
		
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
				.andExpect(jsonPath("$.effectiveDate").value(newId.getEffectiveDate().toString()))
				.andExpect(jsonPath("$.fromCurrency").value(newId.getFromCurrency()))
				.andExpect(jsonPath("$.toCurrency").value(newId.getToCurrency()))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", 0)))
				.andExpect(jsonPath("$.divisionFactor").value(requestObject.getOrDefault("divisionFactor", 0)))
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
				.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
				.andExpect(jsonPath("$.fromCurrency").value(existingId.getFromCurrency()))
				.andExpect(jsonPath("$.toCurrency").value(existingId.getToCurrency()))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", (Double) JsonPath.read(beforePatch, "$.multiplyFactor"))))
				.andExpect(jsonPath("$.divisionFactor").value(requestObject.getOrDefault("divisionFactor", (Double) JsonPath.read(beforePatch, "$.divisionFactor"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}

}
