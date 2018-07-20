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

public class AccountPayableSettingApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
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
	String baseUrl() {
		return "/api/accountPayableSettings/";
	}

	@Override
	String existingId() {
		return "0100";
	}

	@Override
	String newId() {
		return "2807";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.addressNumber").value(existingId))
				.andExpect(jsonPath("$.paymentTermCode").value("COD"))
				.andExpect(jsonPath("$.paymentInstrument").value(""))
				.andExpect(jsonPath("$.currencyCodeTransaction").value("IDR"))
				.andExpect(jsonPath("$.creditStatus").value(""))
				.andExpect(jsonPath("$.landedCostRule").value(""))
				.andExpect(jsonPath("$.supplierPriceGroup").value(""))
				.andExpect(jsonPath("$.accountPayableClass").value("OTH1"))
				.andExpect(jsonPath("$.taxAuthorityNumber").value(""))
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
				.andExpect(jsonPath("$.supplierPriceGroup").value(requestObject.getOrDefault("supplierPriceGroup", (String) JsonPath.read(beforePatch, "$.supplierPriceGroup"))))
				.andExpect(jsonPath("$.accountPayableClass").value(requestObject.getOrDefault("accountPayableClass", (String) JsonPath.read(beforePatch, "$.accountPayableClass"))))
				.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", (String) JsonPath.read(beforePatch, "$.taxCode"))))
				.andExpect(jsonPath("$.taxId").value(requestObject.getOrDefault("taxId", (String) JsonPath.read(beforePatch, "$.taxId"))))
				.andExpect(jsonPath("$.taxAuthorityNumber").value(requestObject.getOrDefault("taxAuthorityNumber", (String) JsonPath.read(beforePatch, "$.taxAuthorityNumber"))))
				.andExpect(jsonPath("$.landedCostRule").value(requestObject.getOrDefault("landedCostRule", (String) JsonPath.read(beforePatch, "$.landedCostRule"))))
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
