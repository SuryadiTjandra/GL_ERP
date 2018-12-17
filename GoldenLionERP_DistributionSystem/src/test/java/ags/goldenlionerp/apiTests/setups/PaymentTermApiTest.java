package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;

public class PaymentTermApiTest extends ApiTestBase<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("paymentTermCode", newId());
		map.put("description", "Test Payment Term");
		map.put("discountPercentage", 25.0);
		map.put("discountDays", 7);
		map.put("netDaysToPay", 5);
		map.put("interfaceToAccountReceivable", true);
		map.put("dueMonth", 5);
		map.put("dueDay", 12);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/paymentTerms/";
	}

	@Override
	protected String existingId() {
		return "COD";
	}

	@Override
	protected String newId() {
		return "TES";
	}
	
	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.paymentTermCode").value(existingId))
		.andExpect(jsonPath("$.discountPercentage").value(0))
		.andExpect(jsonPath("$.effectiveInterestRate").value(0.0))
		.andExpect(jsonPath("$.discountDays").value(1))
		.andExpect(jsonPath("$.netDaysToPay").value(1))
		.andExpect(jsonPath("$.dueDay").value(0.0))
		.andExpect(jsonPath("$.dueMonth").value(0.0))
		.andExpect(jsonPath("$.interfaceToAccountReceivable").value(true))
		.andExpect(jsonPath("$.interfaceToAccountPayable").value(true));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.paymentTerms").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.paymentTermCode").value(newId))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		.andExpect(jsonPath("$.discountPercentage").value(requestObject.getOrDefault("discountPercentage", 0.0)))
		.andExpect(jsonPath("$.effectiveInterestRate").value(requestObject.getOrDefault("effectiveInterestRate", 0)))
		.andExpect(jsonPath("$.discountDays").value(requestObject.getOrDefault("discountDays", 0)))
		.andExpect(jsonPath("$.netDaysToPay").value(requestObject.getOrDefault("netDaysToPay", 0)))
		.andExpect(jsonPath("$.dueDay").value(requestObject.getOrDefault("dueDay", 0)))
		.andExpect(jsonPath("$.dueMonth").value(requestObject.getOrDefault("dueMonth", 0)))
		.andExpect(jsonPath("$.interfaceToAccountReceivable").value(requestObject.getOrDefault("interfaceToAccountReceivable", false)))
		.andExpect(jsonPath("$.interfaceToAccountPayable").value(requestObject.getOrDefault("interfaceToAccountPayable", false)));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.paymentTermCode").value(existingId))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		.andExpect(jsonPath("$.discountPercentage").value(requestObject.getOrDefault("discountPercentage", (Double) JsonPath.read(beforeUpdateJson, "$.discountPercentage"))))
		.andExpect(jsonPath("$.effectiveInterestRate").value(requestObject.getOrDefault("effectiveInterestRate", (Double) JsonPath.read(beforeUpdateJson, "$.effectiveInterestRate"))))
		.andExpect(jsonPath("$.discountDays").value(requestObject.getOrDefault("discountDays", (Integer) JsonPath.read(beforeUpdateJson, "$.discountDays"))))
		.andExpect(jsonPath("$.netDaysToPay").value(requestObject.getOrDefault("netDaysToPay", (Integer) JsonPath.read(beforeUpdateJson, "$.netDaysToPay"))))
		.andExpect(jsonPath("$.dueDay").value(requestObject.getOrDefault("dueDay", (Integer) JsonPath.read(beforeUpdateJson, "$.dueDay"))))
		.andExpect(jsonPath("$.dueMonth").value(requestObject.getOrDefault("dueMonth", (Integer) JsonPath.read(beforeUpdateJson, "$.dueMonth"))))
		.andExpect(jsonPath("$.interfaceToAccountReceivable").value(requestObject.getOrDefault("interfaceToAccountReceivable", (Boolean) JsonPath.read(beforeUpdateJson, "$.interfaceToAccountReceivable"))))
		.andExpect(jsonPath("$.interfaceToAccountPayable").value(requestObject.getOrDefault("interfaceToAccountPayable", (Boolean) JsonPath.read(beforeUpdateJson, "$.interfaceToAccountPayable"))));
		
	}

	

}
