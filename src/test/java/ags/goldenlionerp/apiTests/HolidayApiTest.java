package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.WebIdUtil;

public class HolidayApiTest extends ApiTestBase<LocalDate> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("offDate", newId());
		map.put("offDateType", "I");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/holidays/";
	}

	@Override
	LocalDate existingId() {
		return LocalDate.of(2018, 8, 17);
	}

	@Override
	LocalDate newId() {
		return LocalDate.of(2018, 12, 25);
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		String s = WebIdUtil.getWebIdDateFormatter().format(existingId);
		mockMvc.perform(get(baseUrl + s).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.offDate").value(existingId.toString()))
				.andExpect(jsonPath("$.offDateType").value("N"))
				.andExpect(jsonPath("$.description").value("Hari Kemerdekaan Indonesia"));
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.holidays").exists());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		String s = WebIdUtil.getWebIdDateFormatter().format(newId);
		assumeNotExists(baseUrl+s);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + s))
				.andExpect(jsonPath("$.offDate").value(newId.toString()))
				.andExpect(jsonPath("$.offDateType").value(requestObject.getOrDefault("offDateType", "")))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
		
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		String s = WebIdUtil.getWebIdDateFormatter().format(existingId);
		assumeExists(baseUrl + s);
		
		String beforePatch = mockMvc.perform(get(baseUrl + s))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("description");
		
		mockMvc.perform(patch(baseUrl + s)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + s))
				.andExpect(jsonPath("$.offDate").value(existingId.toString()))
				.andExpect(jsonPath("$.offDateType").value(requestObject.getOrDefault("offDateType", (String) JsonPath.read(beforePatch, "$.offDateType"))))
				.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforePatch, "$.description"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}



	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		String s = WebIdUtil.getWebIdDateFormatter().format(existingId);
		assumeExists(baseUrl + s);
		
		mockMvc.perform(delete(baseUrl + s))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	
		mockMvc.perform(get(baseUrl + s))
			.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		String s = WebIdUtil.getWebIdDateFormatter().format(existingId);
		assumeExists(baseUrl+s);
		
		mockMvc.perform(put(baseUrl + s)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
	
	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {	
		String s = WebIdUtil.getWebIdDateFormatter().format(newId);
		assumeNotExists(baseUrl+s);
		
		mockMvc.perform(put(baseUrl + s)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
}
