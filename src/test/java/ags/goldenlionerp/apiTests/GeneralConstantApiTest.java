package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.application.setups.generalconstant.GeneralConstant;

public class GeneralConstantApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("code", newId());
		map.put("currencyConversionMethod", "D");
		map.put("ageOfDataMaster", 10);
		map.put("interfaceToAccountReceivable", false);
		map.put("interfaceToManufacturer", true);
		
		Map<String, Object> mapBase = new HashMap<>();
		mapBase.put("accountReceivables", 30);
		mapBase.put("deposits", 21.25252525252);
		map.put("baseCurrencyRoundings", mapBase);

		Map<String, Object> mapFor = new HashMap<>();
		mapFor.put("accountPayables", 20);
		mapFor.put("internalPayables", 35);
		map.put("foreignCurrencyRoundings", mapFor);
		
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/generalConstant/";
	}

	@Override
	String existingId() {
		return GeneralConstant.DEFAULT_CODE;
	}

	@Override
	String newId() {
		return "02";
	}

	
	@Override
	@Test
	public void getTestSingle() throws Exception {
		
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(existingId))
				.andExpect(jsonPath("$.currencyConversionMethod").value("M"))
				.andExpect(jsonPath("$.ageOfDataMaster").value(2))
				.andExpect(jsonPath("$.interfaceToGL").value(true))
				.andExpect(jsonPath("$.interfaceToAccountReceivable").value(true))
				.andExpect(jsonPath("$.interfaceToAccountPayable").value(true))
				.andExpect(jsonPath("$.interfaceToPayroll").value(false))
				.andExpect(jsonPath("$.baseCurrencyRoundings.accountReceivables").value(1))
				.andExpect(jsonPath("$.baseCurrencyRoundings.internalPayables").value(4))
				.andExpect(jsonPath("$.foreignCurrencyRoundings.internalReceivables").value(7.5))
				.andExpect(jsonPath("$.foreignCurrencyRoundings.deposits").value(10));

	}

	@Override
	public void getTestCollection() throws Exception {
		// Not Applicable

	}

	@Override
	@Test @Rollback
	public void createTestWithPost() throws Exception {
		mockMvc.perform(post(baseUrl)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + newId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(baseUrl)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
		mockMvc.perform(delete(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@SuppressWarnings("unchecked")
	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl);
		
		String beforePatch = mockMvc.perform(get(baseUrl))
								.andReturn().getResponse().getContentAsString();
		
		Map<String, Object> mapBase = (Map<String, Object>) requestObject.get("baseCurrencyRoundings");		
		Map<String, Object> mapFor = (Map<String, Object>) requestObject.get("foreignCurrencyRoundings");
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		em.flush();
		em.clear();
		BigDecimal deposit = new BigDecimal((Double)mapBase.get("deposits")).setScale(5, RoundingMode.HALF_UP);
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
							.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
							.andExpect(jsonPath("$.code").value(existingId))
							.andExpect(jsonPath("$.currencyConversionMethod").value(requestObject.get("currencyConversionMethod")))
							.andExpect(jsonPath("$.ageOfDataMaster").value(requestObject.get("ageOfDataMaster")))
							.andExpect(jsonPath("$.interfaceToGL").value((Boolean) JsonPath.read(beforePatch, "$.interfaceToGL")))
							.andExpect(jsonPath("$.interfaceToAccountReceivable").value(requestObject.get("interfaceToAccountReceivable")))
							.andExpect(jsonPath("$.interfaceToAccountPayable").value((Boolean) JsonPath.read(beforePatch, "$.interfaceToAccountPayable")))
							.andExpect(jsonPath("$.interfaceToManufacturer").value(requestObject.get("interfaceToManufacturer")))
							.andExpect(jsonPath("$.baseCurrencyRoundings.accountReceivables").value(mapBase.get("accountReceivables")))
							.andExpect(jsonPath("$.baseCurrencyRoundings.deposits").value(deposit))
							.andExpect(jsonPath("$.baseCurrencyRoundings.accountPayables").value((Double) JsonPath.read(beforePatch, "$.baseCurrencyRoundings.accountPayables")))
							.andExpect(jsonPath("$.foreignCurrencyRoundings.accountPayables").value(mapFor.get("accountPayables")))
							.andExpect(jsonPath("$.foreignCurrencyRoundings.internalPayables").value(mapFor.get("internalPayables")))
							.andExpect(jsonPath("$.foreignCurrencyRoundings.accountReceivables").value((Double) JsonPath.read(beforePatch, "$.foreignCurrencyRoundings.accountReceivables")))
							.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);
	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	
}
