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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class PaymentTermApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
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
	String baseUrl() {
		return "/api/paymentTerms/";
	}

	@Override
	String existingId() {
		return "COD";
	}

	@Override
	String newId() {
		return "TES";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.paymentTerms").exists());

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
				.andExpect(jsonPath("$.paymentTermCode").value(newId))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.discountPercentage").value(requestObject.getOrDefault("discountPercentage", 0.0)))
				.andExpect(jsonPath("$.effectiveInterestRate").value(requestObject.getOrDefault("effectiveInterestRate", 0)))
				.andExpect(jsonPath("$.discountDays").value(requestObject.getOrDefault("discountDays", 0)))
				.andExpect(jsonPath("$.netDaysToPay").value(requestObject.getOrDefault("netDaysToPay", 0)))
				.andExpect(jsonPath("$.dueDay").value(requestObject.getOrDefault("dueDay", 0)))
				.andExpect(jsonPath("$.dueMonth").value(requestObject.getOrDefault("dueMonth", 0)))
				.andExpect(jsonPath("$.interfaceToAccountReceivable").value(requestObject.getOrDefault("interfaceToAccountReceivable", false)))
				.andExpect(jsonPath("$.interfaceToAccountPayable").value(requestObject.getOrDefault("interfaceToAccountPayable", false)))
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
				.andExpect(jsonPath("$.paymentTermCode").value(existingId))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andExpect(jsonPath("$.discountPercentage").value(requestObject.getOrDefault("discountPercentage", (Double) JsonPath.read(beforePatch, "$.discountPercentage"))))
				.andExpect(jsonPath("$.effectiveInterestRate").value(requestObject.getOrDefault("effectiveInterestRate", (Double) JsonPath.read(beforePatch, "$.effectiveInterestRate"))))
				.andExpect(jsonPath("$.discountDays").value(requestObject.getOrDefault("discountDays", (Integer) JsonPath.read(beforePatch, "$.discountDays"))))
				.andExpect(jsonPath("$.netDaysToPay").value(requestObject.getOrDefault("netDaysToPay", (Integer) JsonPath.read(beforePatch, "$.netDaysToPay"))))
				.andExpect(jsonPath("$.dueDay").value(requestObject.getOrDefault("dueDay", (Integer) JsonPath.read(beforePatch, "$.dueDay"))))
				.andExpect(jsonPath("$.dueMonth").value(requestObject.getOrDefault("dueMonth", (Integer) JsonPath.read(beforePatch, "$.dueMonth"))))
				.andExpect(jsonPath("$.interfaceToAccountReceivable").value(requestObject.getOrDefault("interfaceToAccountReceivable", (Boolean) JsonPath.read(beforePatch, "$.interfaceToAccountReceivable"))))
				.andExpect(jsonPath("$.interfaceToAccountPayable").value(requestObject.getOrDefault("interfaceToAccountPayable", (Boolean) JsonPath.read(beforePatch, "$.interfaceToAccountPayable"))))
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
