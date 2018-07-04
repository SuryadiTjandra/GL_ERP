package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.WebIdUtil;

public class NextNumberConstantApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("documentType", newId());
		map.put("resetNumber", 2);
		map.put("resetMethod", "CY");
		map.put("includeMonthInNextNumber", false);
		map.put("includeYearInNextNumber", true);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/nextNumberConstants/";
	}

	@Override
	String existingId() {
		return "FD";
	}

	@Override
	String newId() {
		return "FM";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.documentType").value(existingId))
				.andExpect(jsonPath("$.resetNumber").value(0))
				.andExpect(jsonPath("$.resetMethod").value("CY"))
				.andExpect(jsonPath("$.includeMonthInNextNumber").value(true))
				.andExpect(jsonPath("$.includeYearInNextNumber").value(true));

	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.nextNumberConstants").exists());
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		mockMvc.perform(delete(baseUrl + newId));		
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.documentType").value(requestObject.get("documentType")))
				.andExpect(jsonPath("$.resetNumber").value(requestObject.get("resetNumber")))
				.andExpect(jsonPath("$.resetMethod").value(requestObject.get("resetMethod")))
				.andExpect(jsonPath("$.includeMonthInNextNumber").value(requestObject.get("includeMonthInNextNumber")))
				.andExpect(jsonPath("$.includeYearInNextNumber").value(requestObject.get("includeYearInNextNumber")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		mockMvc.perform(delete(baseUrl + newId));		
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
		
		requestObject.remove("resetNumber");
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.documentType").value(existingId))
				.andExpect(jsonPath("$.resetNumber").value((Integer) JsonPath.read(beforePatch, "$.resetNumber")))
				.andExpect(jsonPath("$.resetMethod").value(requestObject.get("resetMethod")))
				.andExpect(jsonPath("$.includeMonthInNextNumber").value(requestObject.get("includeMonthInNextNumber")))
				.andExpect(jsonPath("$.includeYearInNextNumber").value(requestObject.get("includeYearInNextNumber")))
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
