package ags.goldenlionerp.apiTests.addresses;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;

public class AccountReceivableSettingApiTest extends ApiTestBaseNew<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId());
		map.put("paymentTermCode", "N21");
		map.put("paymentInstrument", "D");
		map.put("creditLimit", 100000);
		map.put("tradeDiscountFactor", 2.1);
		map.put("customerPriceGroup", "AGEN");
		map.put("taxCode", "INCL");
		map.put("billingAddresstype", "X");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/accountReceivableSettings/";
	}

	@Override
	protected String existingId() {
		return "0100";
	}

	@Override
	protected String newId() {
		return "2808";
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
				.andExpect(jsonPath("$.creditLimit").value(requestObject.getOrDefault("creditLimit", 0)))
				.andExpect(jsonPath("$.rankBySales").value(requestObject.getOrDefault("rankBySales", "")))
				.andExpect(jsonPath("$.rankByProfitMargin").value(requestObject.getOrDefault("rankByProfitMargin", "")))
				.andExpect(jsonPath("$.rankByAverageDay").value(requestObject.getOrDefault("rankByAverageDay", "")))
				.andExpect(jsonPath("$.customerPriceGroup").value(requestObject.getOrDefault("customerPriceGroup", "")))
				.andExpect(jsonPath("$.tradeDiscountFactor").value(requestObject.getOrDefault("tradeDiscountFactor", 0)))
				.andExpect(jsonPath("$.billingAddressType").value(requestObject.getOrDefault("billingAddressType", "")))
				.andExpect(jsonPath("$.accountReceivableClass").value(requestObject.getOrDefault("accountReceivableClass", "")))
				.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", "")))
				.andExpect(jsonPath("$.salesmanAddressNumber").value(requestObject.getOrDefault("salesmanAddressNumber", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.accountOpenedDate").value(requestObject.getOrDefault("accountOpenedDate", null)))
				.andExpect(jsonPath("$.expiredDate").value(requestObject.getOrDefault("expiredDate", null)))
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
				.andExpect(jsonPath("$.creditLimit").value(requestObject.getOrDefault("creditLimit", 0)))
				.andExpect(jsonPath("$.rankBySales").value(requestObject.getOrDefault("rankBySales", "")))
				.andExpect(jsonPath("$.rankByProfitMargin").value(requestObject.getOrDefault("rankByProfitMargin", "")))
				.andExpect(jsonPath("$.rankByAverageDay").value(requestObject.getOrDefault("rankByAverageDay", "")))
				.andExpect(jsonPath("$.customerPriceGroup").value(requestObject.getOrDefault("customerPriceGroup", "")))
				.andExpect(jsonPath("$.tradeDiscountFactor").value(requestObject.getOrDefault("tradeDiscountFactor", 0)))
				.andExpect(jsonPath("$.billingAddressType").value(requestObject.getOrDefault("billingAddressType", "")))
				.andExpect(jsonPath("$.accountReceivableClass").value(requestObject.getOrDefault("accountReceivableClass", "")))
				.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", "")))
				.andExpect(jsonPath("$.salesmanAddressNumber").value(requestObject.getOrDefault("salesmanAddressNumber", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.accountOpenedDate").value(requestObject.getOrDefault("accountOpenedDate", null)))
				.andExpect(jsonPath("$.expiredDate").value(requestObject.getOrDefault("expiredDate", null)))
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
		.andExpect(jsonPath("$.paymentTermCode").value("N30"))
		.andExpect(jsonPath("$.paymentInstrument").value(""))
		.andExpect(jsonPath("$.currencyCodeTransaction").value("IDR"))
		.andExpect(jsonPath("$.creditStatus").value(""))
		.andExpect(jsonPath("$.creditLimit").value(0))
		.andExpect(jsonPath("$.billingAddressType").value(""))
		.andExpect(jsonPath("$.customerPriceGroup").value(""))
		.andExpect(jsonPath("$.tradeDiscountFactor").value(0))
		.andExpect(jsonPath("$.accountReceivableClass").value("OTH"))
		.andExpect(jsonPath("$.salesmanAddressNumber").value(""))
		.andExpect(jsonPath("$.taxId").value(""))
		;
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId))
		.andExpect(jsonPath("$.paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", (String) JsonPath.read(beforeUpdateJson, "$.paymentTermCode"))))
		.andExpect(jsonPath("$.paymentInstrument").value(requestObject.getOrDefault("paymentInstrument", (String) JsonPath.read(beforeUpdateJson, "$.paymentInstrument"))))
		.andExpect(jsonPath("$.currencyCodeTransaction").value(requestObject.getOrDefault("currencyCodeTransaction", (String) JsonPath.read(beforeUpdateJson, "$.currencyCodeTransaction"))))
		.andExpect(jsonPath("$.creditStatus").value(requestObject.getOrDefault("creditStatus", (String) JsonPath.read(beforeUpdateJson, "$.creditStatus"))))
		.andExpect(jsonPath("$.creditLimit").value(requestObject.getOrDefault("creditLimit", (Double) JsonPath.read(beforeUpdateJson, "$.creditLimit"))))
		.andExpect(jsonPath("$.rankBySales").value(requestObject.getOrDefault("rankBySales", (String) JsonPath.read(beforeUpdateJson, "$.rankBySales"))))
		.andExpect(jsonPath("$.rankByProfitMargin").value(requestObject.getOrDefault("rankByProfitMargin", (String) JsonPath.read(beforeUpdateJson, "$.rankByProfitMargin"))))
		.andExpect(jsonPath("$.rankByAverageDay").value(requestObject.getOrDefault("rankByAverageDay", (String) JsonPath.read(beforeUpdateJson, "$.rankByAverageDay"))))
		.andExpect(jsonPath("$.customerPriceGroup").value(requestObject.getOrDefault("customerPriceGroup", (String) JsonPath.read(beforeUpdateJson, "$.customerPriceGroup"))))
		.andExpect(jsonPath("$.tradeDiscountFactor").value(requestObject.getOrDefault("tradeDiscountFactor", (Double) JsonPath.read(beforeUpdateJson, "$.tradeDiscountFactor"))))
		.andExpect(jsonPath("$.billingAddressType").value(requestObject.getOrDefault("billingAddressType", (String) JsonPath.read(beforeUpdateJson, "$.billingAddressType"))))
		.andExpect(jsonPath("$.accountReceivableClass").value(requestObject.getOrDefault("accountReceivableClass", (String) JsonPath.read(beforeUpdateJson, "$.accountReceivableClass"))))
		.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", (String) JsonPath.read(beforeUpdateJson, "$.taxCode"))))
		.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", (String) JsonPath.read(beforeUpdateJson, "$.taxId"))))
		.andExpect(jsonPath("$.salesmanAddressNumber").value(requestObject.getOrDefault("salesmanAddressNumber", (String) JsonPath.read(beforeUpdateJson, "$.salesmanAddressNumber"))))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		.andExpect(jsonPath("$.accountOpenedDate").value(requestObject.getOrDefault("accountOpenedDate", (String) JsonPath.read(beforeUpdateJson, "$.accountOpenedDate"))))
		.andExpect(jsonPath("$.expiredDate").value(requestObject.getOrDefault("expiredDate", (String) JsonPath.read(beforeUpdateJson, "$.expiredDate"))))
		;
		
	}
	

}
