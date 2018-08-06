package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class AccountPayableSettingApiTest extends ApiTestBaseNew<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addresNumber", newId());
		map.put("paymentTermCode", "CDC");
		map.put("paymentInstrument", "");
		map.put("creditStatus", "P");
		map.put("supplierPriceGroup", "UMUM");
		map.put("landedCostRule", "IMP");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/accountPayableSettings/";
	}

	@Override
	protected String existingId() {
		return "0100";
	}

	@Override
	protected String newId() {
		return "2807";
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		performer.performGet(baseUrl)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		performer.performPut(baseUrl + newId, requestObject)
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		refreshData();
		
		String getRes = performer.performGet(baseUrl + newId)
				.andExpect(jsonPath("$.addressNumber").value(newId))
				.andExpect(jsonPath("$.paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", "")))
				.andExpect(jsonPath("$.paymentInstrument").value(requestObject.getOrDefault("paymentInstrument", "")))
				.andExpect(jsonPath("$.currencyCodeTransaction").value(requestObject.getOrDefault("currencyCodeTransaction", "IDR")))
				.andExpect(jsonPath("$.creditStatus").value(requestObject.getOrDefault("creditStatus", "")))
				.andExpect(jsonPath("$.supplierPriceGroup").value(requestObject.getOrDefault("supplierPriceGroup", "")))
				.andExpect(jsonPath("$.accountPayableClass").value(requestObject.getOrDefault("accountPayableClass", "")))
				.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", "")))
				.andExpect(jsonPath("$.taxAuthorityNumber").value(requestObject.getOrDefault("taxAuthorityNumber", "")))
				.andExpect(jsonPath("$.landedCostRule").value(requestObject.getOrDefault("landedCostRule", "")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
	}
	
	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforeUpdate = performer.performGet(baseUrl + existingId)
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("description");
		
		performer.performPut(baseUrl + existingId, requestObject)
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		refreshData();
		
		String getRes = performer.performGet(baseUrl + existingId)
				.andExpect(jsonPath("$.addressNumber").value(existingId))
				.andExpect(jsonPath("$.paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", "")))
				.andExpect(jsonPath("$.paymentInstrument").value(requestObject.getOrDefault("paymentInstrument", "")))
				.andExpect(jsonPath("$.currencyCodeTransaction").value(requestObject.getOrDefault("currencyCodeTransaction", "IDR")))
				.andExpect(jsonPath("$.creditStatus").value(requestObject.getOrDefault("creditStatus", "")))
				.andExpect(jsonPath("$.supplierPriceGroup").value(requestObject.getOrDefault("supplierPriceGroup", "")))
				.andExpect(jsonPath("$.accountPayableClass").value(requestObject.getOrDefault("accountPayableClass", "")))
				.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", "")))
				.andExpect(jsonPath("$.taxAuthorityNumber").value(requestObject.getOrDefault("taxAuthorityNumber", "")))
				.andExpect(jsonPath("$.landedCostRule").value(requestObject.getOrDefault("landedCostRule", "")))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getRes, beforeUpdate);
	}
	
	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("description");
		
		super.updateTestWithPatch();
	}
	
	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		performer.performDelete(baseUrl + existingId) 
			.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId))
		.andExpect(jsonPath("$.paymentTermCode").value("COD"))
		.andExpect(jsonPath("$.paymentInstrument").value(""))
		.andExpect(jsonPath("$.currencyCodeTransaction").value("IDR"))
		.andExpect(jsonPath("$.creditStatus").value(""))
		.andExpect(jsonPath("$.landedCostRule").value(""))
		.andExpect(jsonPath("$.supplierPriceGroup").value(""))
		.andExpect(jsonPath("$.accountPayableClass").value("OTH1"))
		.andExpect(jsonPath("$.taxAuthorityNumber").value(""))
		.andExpect(jsonPath("$.taxId").value(""));
		
	}

	

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId))
		.andExpect(jsonPath("$.paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", (String) JsonPath.read(beforeUpdateJson, "$.paymentTermCode"))))
		.andExpect(jsonPath("$.paymentInstrument").value(requestObject.getOrDefault("paymentInstrument", (String) JsonPath.read(beforeUpdateJson, "$.paymentInstrument"))))
		.andExpect(jsonPath("$.currencyCodeTransaction").value(requestObject.getOrDefault("currencyCodeTransaction", (String) JsonPath.read(beforeUpdateJson, "$.currencyCodeTransaction"))))
		.andExpect(jsonPath("$.creditStatus").value(requestObject.getOrDefault("creditStatus", (String) JsonPath.read(beforeUpdateJson, "$.creditStatus"))))
		.andExpect(jsonPath("$.supplierPriceGroup").value(requestObject.getOrDefault("supplierPriceGroup", (String) JsonPath.read(beforeUpdateJson, "$.supplierPriceGroup"))))
		.andExpect(jsonPath("$.accountPayableClass").value(requestObject.getOrDefault("accountPayableClass", (String) JsonPath.read(beforeUpdateJson, "$.accountPayableClass"))))
		.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", (String) JsonPath.read(beforeUpdateJson, "$.taxCode"))))
		.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", (String) JsonPath.read(beforeUpdateJson, "$.taxId"))))
		.andExpect(jsonPath("$.taxAuthorityNumber").value(requestObject.getOrDefault("taxAuthorityNumber", (String) JsonPath.read(beforeUpdateJson, "$.taxAuthorityNumber"))))
		.andExpect(jsonPath("$.landedCostRule").value(requestObject.getOrDefault("landedCostRule", (String) JsonPath.read(beforeUpdateJson, "$.landedCostRule"))))
		;
	}

	
	@Override public void assertGetCollectionResult(ResultActions action) throws Exception {
		// N/A
	}

	@Override public void assertCreateWithPostResult(ResultActions action) throws Exception {
		// N/A
	}
}
