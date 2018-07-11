package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class DiscountApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("discountCode", newId());
		map.put("description", "Test Discount");
		map.put("discountPercentage1", 25);
		map.put("discountAmount2", 10000);
		map.put("discountPercentage3", 12.123);
		map.put("discountAmount4", 5500.25);
		map.put("discountPercentage5", 8);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/discounts/";
	}

	@Override
	String existingId() {
		return "D00";
	}

	@Override
	String newId() {
		return "TEST";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.discountCode").value(existingId))
				.andExpect(jsonPath("$.description").value("Discount 10%"))
				.andExpect(jsonPath("$.discountPercentage1").value(10))
				.andExpect(jsonPath("$.discountPercentage2").value(0))
				.andExpect(jsonPath("$.discountPercentage3").value(0))
				.andExpect(jsonPath("$.discountPercentage4").value(0))
				.andExpect(jsonPath("$.discountPercentage5").value(0))
				.andExpect(jsonPath("$.discountAmount1").value(0))
				.andExpect(jsonPath("$.discountAmount2").value(0))
				.andExpect(jsonPath("$.discountAmount3").value(0))
				.andExpect(jsonPath("$.discountAmount4").value(0))
				.andExpect(jsonPath("$.discountAmount5").value(0));

	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.discounts").exists());

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
				.andExpect(jsonPath("$.discountCode").value(newId))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andExpect(jsonPath("$.discountPercentage1").value(requestObject.getOrDefault("discountPercentage1", 0)))
				.andExpect(jsonPath("$.discountPercentage2").value(requestObject.getOrDefault("discountPercentage2", 0)))
				.andExpect(jsonPath("$.discountPercentage3").value(requestObject.getOrDefault("discountPercentage3", 0)))
				.andExpect(jsonPath("$.discountPercentage4").value(requestObject.getOrDefault("discountPercentage4", 0)))
				.andExpect(jsonPath("$.discountPercentage5").value(requestObject.getOrDefault("discountPercentage5", 0)))
				.andExpect(jsonPath("$.discountAmount1").value(requestObject.getOrDefault("discountAmount1", 0)))
				.andExpect(jsonPath("$.discountAmount2").value(requestObject.getOrDefault("discountAmount2", 0)))
				.andExpect(jsonPath("$.discountAmount3").value(requestObject.getOrDefault("discountAmount3", 0)))
				.andExpect(jsonPath("$.discountAmount4").value(requestObject.getOrDefault("discountAmount4", 0)))
				.andExpect(jsonPath("$.discountAmount5").value(requestObject.getOrDefault("discountAmount5", 0)))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

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
				.andExpect(jsonPath("$.discountCode").value(existingId))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andExpect(jsonPath("$.discountPercentage1").value(requestObject.getOrDefault("discountPercentage1", (Double) JsonPath.read(beforePatch, "$.discountPercentage1"))))
				.andExpect(jsonPath("$.discountPercentage2").value(requestObject.getOrDefault("discountPercentage2", (Double) JsonPath.read(beforePatch, "$.discountPercentage2"))))
				.andExpect(jsonPath("$.discountPercentage3").value(requestObject.getOrDefault("discountPercentage3", (Double) JsonPath.read(beforePatch, "$.discountPercentage3"))))
				.andExpect(jsonPath("$.discountPercentage4").value(requestObject.getOrDefault("discountPercentage4", (Double) JsonPath.read(beforePatch, "$.discountPercentage4"))))
				.andExpect(jsonPath("$.discountPercentage5").value(requestObject.getOrDefault("discountPercentage5", (Double) JsonPath.read(beforePatch, "$.discountPercentage5"))))
				.andExpect(jsonPath("$.discountAmount1").value(requestObject.getOrDefault("discountAmount1", (Double) JsonPath.read(beforePatch, "$.discountAmount1"))))
				.andExpect(jsonPath("$.discountAmount2").value(requestObject.getOrDefault("discountAmount2", (Double) JsonPath.read(beforePatch, "$.discountAmount2"))))
				.andExpect(jsonPath("$.discountAmount3").value(requestObject.getOrDefault("discountAmount3", (Double) JsonPath.read(beforePatch, "$.discountAmount3"))))
				.andExpect(jsonPath("$.discountAmount4").value(requestObject.getOrDefault("discountAmount4", (Double) JsonPath.read(beforePatch, "$.discountAmount4"))))
				.andExpect(jsonPath("$.discountAmount5").value(requestObject.getOrDefault("discountAmount5", (Double) JsonPath.read(beforePatch, "$.discountAmount5"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);

	}


	

}
