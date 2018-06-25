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

public class ChartOfAccountApiTest extends ApiTestBase<String>{

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("objectAccountCode", newId());
		map.put("description", "Test");
		map.put("descriptionLong", "Teeeeesssttt");
		map.put("levelOfDetail", 5);
		map.put("postingEditCode", true);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/chartOfAccounts/";
	}

	@Override
	String existingId() {
		return "111300";
	}

	@Override
	String newId() {
		return "123456";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.objectAccountCode").value(existingId))
				.andExpect(jsonPath("$.description").value("Bank IDR"))
				.andExpect(jsonPath("$.levelOfDetail").value(6))
				.andExpect(jsonPath("$.postingEditCode").value(false));
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl + newId);
		//System.out.println(mapper.writeValueAsString(requestObject));
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.objectAccountCode").value(newId))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
				.andExpect(jsonPath("$.levelOfDetail").value(requestObject.get("levelOfDetail")))
				.andExpect(jsonPath("$.postingEditCode").value(requestObject.get("postingEditCode")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
				
		
	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeExists(baseUrl + existingId);
		mockMvc.perform(put(baseUrl + existingId)
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
		
		requestObject.remove("description");
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
							.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
							.andExpect(jsonPath("$.objectAccountCode").value(existingId))
							.andExpect(jsonPath("$.description").value((String) JsonPath.read(beforePatch, "$.description")))
							.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
							.andExpect(jsonPath("$.levelOfDetail").value(requestObject.get("levelOfDetail")))
							.andExpect(jsonPath("$.postingEditCode").value(requestObject.get("postingEditCode")))
							.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);
		
	}

	@Override@Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl + existingId);
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
		
	}

}
