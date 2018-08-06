package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;
import ags.goldenlionerp.application.setups.currencyconversion.CurrencyConversionPK;

public class CurrencyConversionApiTest extends ApiTestBaseNew<CurrencyConversionPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
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
	protected String baseUrl() {
		return "/api/currencyConversions/";
	}

	
	@Override
	protected CurrencyConversionPK existingId() {
		return new CurrencyConversionPK(LocalDate.of(2018, 7, 31), "IDR", "SGD");
	}

	@Override
	protected CurrencyConversionPK newId() {
		return new CurrencyConversionPK(LocalDate.of(2018, 7, 31), "USD", "CNY");
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
		.andExpect(jsonPath("$.fromCurrency").value(existingId.getFromCurrency()))
		.andExpect(jsonPath("$.toCurrency").value(existingId.getToCurrency()))
		.andExpect(jsonPath("$.multiplyFactor").value(1))
		.andExpect(jsonPath("$.divisionFactor").value(10000))
		.andExpect(jsonPath("$.description").value("Rupiah to SGD"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.currencyConversions").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.effectiveDate").value(newId.getEffectiveDate().toString()))
		.andExpect(jsonPath("$.fromCurrency").value(newId.getFromCurrency()))
		.andExpect(jsonPath("$.toCurrency").value(newId.getToCurrency()))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", 0)))
		.andExpect(jsonPath("$.divisionFactor").value(requestObject.getOrDefault("divisionFactor", 0)))
		;
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
		.andExpect(jsonPath("$.fromCurrency").value(existingId.getFromCurrency()))
		.andExpect(jsonPath("$.toCurrency").value(existingId.getToCurrency()))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", (Double) JsonPath.read(beforeUpdateJson, "$.multiplyFactor"))))
		.andExpect(jsonPath("$.divisionFactor").value(requestObject.getOrDefault("divisionFactor", (Double) JsonPath.read(beforeUpdateJson, "$.divisionFactor"))))
		;
		
	}

}
