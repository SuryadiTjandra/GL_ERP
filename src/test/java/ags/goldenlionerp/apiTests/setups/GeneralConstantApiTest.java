package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.setups.generalconstant.GeneralConstant;

public class GeneralConstantApiTest extends ApiTestBase<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
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
	protected String baseUrl() {
		return "/api/generalConstant/";
	}

	@Override
	protected String existingId() {
		return GeneralConstant.DEFAULT_CODE;
	}

	@Override
	protected String newId() {
		return "02";
	}

	@Override
	@Test @Rollback
	public void createTestWithPost() throws Exception {
		performer.performPost(baseUrl, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
	
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		//check nothing		
	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		performer.performPut(baseUrl + newId, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		performer.performDelete(baseUrl)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
		performer.performDelete(baseUrl + existingId) 
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		performer.performPut(baseUrl, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
		performer.performPut(baseUrl + existingId, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
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
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		//because there's only one general constants, so getting the collection is the same as getting the single result
		assertGetSingleResult(action);
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		Map<String, Object> mapBase = (Map<String, Object>) requestObject.get("baseCurrencyRoundings");		
		Map<String, Object> mapFor = (Map<String, Object>) requestObject.get("foreignCurrencyRoundings");
		BigDecimal deposit = new BigDecimal((Double)mapBase.get("deposits")).setScale(5, RoundingMode.HALF_UP);
		
		action
		.andExpect(jsonPath("$.code").value(existingId))
		.andExpect(jsonPath("$.currencyConversionMethod").value(requestObject.get("currencyConversionMethod")))
		.andExpect(jsonPath("$.ageOfDataMaster").value(requestObject.get("ageOfDataMaster")))
		.andExpect(jsonPath("$.interfaceToGL").value((Boolean) JsonPath.read(beforeUpdateJson, "$.interfaceToGL")))
		.andExpect(jsonPath("$.interfaceToAccountReceivable").value(requestObject.get("interfaceToAccountReceivable")))
		.andExpect(jsonPath("$.interfaceToAccountPayable").value((Boolean) JsonPath.read(beforeUpdateJson, "$.interfaceToAccountPayable")))
		.andExpect(jsonPath("$.interfaceToManufacturer").value(requestObject.get("interfaceToManufacturer")))
		.andExpect(jsonPath("$.baseCurrencyRoundings.accountReceivables").value(mapBase.get("accountReceivables")))
		.andExpect(jsonPath("$.baseCurrencyRoundings.deposits").value(deposit))
		.andExpect(jsonPath("$.baseCurrencyRoundings.accountPayables").value((Double) JsonPath.read(beforeUpdateJson, "$.baseCurrencyRoundings.accountPayables")))
		.andExpect(jsonPath("$.foreignCurrencyRoundings.accountPayables").value(mapFor.get("accountPayables")))
		.andExpect(jsonPath("$.foreignCurrencyRoundings.internalPayables").value(mapFor.get("internalPayables")))
		.andExpect(jsonPath("$.foreignCurrencyRoundings.accountReceivables").value((Double) JsonPath.read(beforeUpdateJson, "$.foreignCurrencyRoundings.accountReceivables")));
	}

	
}
