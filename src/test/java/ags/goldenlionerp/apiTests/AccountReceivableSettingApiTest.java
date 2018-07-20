package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class AccountReceivableSettingApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
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
	String baseUrl() {
		return "/api/accountReceivableSettings/";
	}

	@Override
	String existingId() {
		return "0100";
	}

	@Override
	String newId() {
		return "2808";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
	
	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(put(baseUrl+newId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
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
		
		String beforeUpdate = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("description");
		
		mockMvc.perform(put(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + existingId))
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
				.andExpect(jsonPath("$.addressNumber").value(existingId))
				.andExpect(jsonPath("$.paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", (String) JsonPath.read(beforePatch, "$.paymentTermCode"))))
				.andExpect(jsonPath("$.paymentInstrument").value(requestObject.getOrDefault("paymentInstrument", (String) JsonPath.read(beforePatch, "$.paymentInstrument"))))
				.andExpect(jsonPath("$.currencyCodeTransaction").value(requestObject.getOrDefault("currencyCodeTransaction", (String) JsonPath.read(beforePatch, "$.currencyCodeTransaction"))))
				.andExpect(jsonPath("$.creditStatus").value(requestObject.getOrDefault("creditStatus", (String) JsonPath.read(beforePatch, "$.creditStatus"))))
				.andExpect(jsonPath("$.creditLimit").value(requestObject.getOrDefault("creditLimit", (Double) JsonPath.read(beforePatch, "$.creditLimit"))))
				.andExpect(jsonPath("$.rankBySales").value(requestObject.getOrDefault("rankBySales", (String) JsonPath.read(beforePatch, "$.rankBySales"))))
				.andExpect(jsonPath("$.rankByProfitMargin").value(requestObject.getOrDefault("rankByProfitMargin", (String) JsonPath.read(beforePatch, "$.rankByProfitMargin"))))
				.andExpect(jsonPath("$.rankByAverageDay").value(requestObject.getOrDefault("rankByAverageDay", (String) JsonPath.read(beforePatch, "$.rankByAverageDay"))))
				.andExpect(jsonPath("$.customerPriceGroup").value(requestObject.getOrDefault("customerPriceGroup", (String) JsonPath.read(beforePatch, "$.customerPriceGroup"))))
				.andExpect(jsonPath("$.tradeDiscountFactor").value(requestObject.getOrDefault("tradeDiscountFactor", (Double) JsonPath.read(beforePatch, "$.tradeDiscountFactor"))))
				.andExpect(jsonPath("$.billingAddressType").value(requestObject.getOrDefault("billingAddressType", (String) JsonPath.read(beforePatch, "$.billingAddressType"))))
				.andExpect(jsonPath("$.accountReceivableClass").value(requestObject.getOrDefault("accountReceivableClass", (String) JsonPath.read(beforePatch, "$.accountReceivableClass"))))
				.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", (String) JsonPath.read(beforePatch, "$.taxCode"))))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", (String) JsonPath.read(beforePatch, "$.taxId"))))
				.andExpect(jsonPath("$.salesmanAddressNumber").value(requestObject.getOrDefault("salesmanAddressNumber", (String) JsonPath.read(beforePatch, "$.salesmanAddressNumber"))))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andExpect(jsonPath("$.accountOpenedDate").value(requestObject.getOrDefault("accountOpenedDate", (String) JsonPath.read(beforePatch, "$.accountOpenedDate"))))
				.andExpect(jsonPath("$.expiredDate").value(requestObject.getOrDefault("expiredDate", (String) JsonPath.read(beforePatch, "$.expiredDate"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);

	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		mockMvc.perform(delete(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
	

}
