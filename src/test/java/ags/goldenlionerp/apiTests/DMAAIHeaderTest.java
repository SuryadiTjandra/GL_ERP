package ags.goldenlionerp.apiTests;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class DMAAIHeaderTest extends ApiTestBase<Integer> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("dmaaiNo", newId());
		map.put("description1", "Test");
		map.put("description2", "TEEEEEESSSSST");
		map.put("hasPostingEditCode", true);
		map.put("levelOfDetail", 3);
		map.put("multiplyFactor", -1);
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/dmaai/";
	}

	@Override
	Integer existingId() {
		return 3220;
	}

	@Override
	Integer newId() {
		return 3399;
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		String getRes = mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.dmaaiNo").value(existingId))
				.andExpect(jsonPath("$.description1").value("Work In Proccess"))
				.andExpect(jsonPath("$.description2").value(""))
				.andExpect(jsonPath("$.hasPostingEditCode").value(true))
				.andExpect(jsonPath("$.levelOfDetail").value(3))
				.andExpect(jsonPath("$.multiplyFactor").value(1))
				.andReturn().getResponse().getContentAsString();

		String detailLink = JsonPath.read(getRes, "$._links.details.href");
		String linkRes = mockMvc.perform(get(detailLink).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Collection<Integer> dmaaiNos = JsonPath.read(linkRes, "$._embedded.dmaaiDetails[*].dmaaiNo");
		dmaaiNos.forEach(no -> assertEquals(existingId, no));

	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$._embedded.dmaai").exists());

	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.dmaaiNo").value(newId))
				.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", "")))
				.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", "")))
				.andExpect(jsonPath("$.hasPostingEditCode").value(requestObject.getOrDefault("hasPostingEditCode", false)))
				.andExpect(jsonPath("$.levelOfDetail").value(requestObject.getOrDefault("levelOfDetail", 0)))
				.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", 0)));

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
		assumeExists(baseUrl+existingId);
		
		String getRes = mockMvc.perform(get(baseUrl + existingId))
						.andReturn().getResponse().getContentAsString();
		String detailsLink = JsonPath.read(getRes, "$._links.details.href");
		String linkRes = mockMvc.perform(get(detailsLink))
						.andReturn().getResponse().getContentAsString();
		String detailLink = JsonPath.read(linkRes, "$._embedded.dmaaiDetails[0]._links.self.href");
		assumeExists(detailLink);
		
		mockMvc.perform(delete(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(get(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		mockMvc.perform(get(detailLink))
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
		
		mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.dmaaiNo").value(existingId))
				.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", (String) JsonPath.read(beforePatch, "$.description1"))))
				.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", (String) JsonPath.read(beforePatch, "$.description2"))))
				.andExpect(jsonPath("$.hasPostingEditCode").value(requestObject.getOrDefault("hasPostingEditCode", (Boolean) JsonPath.read(beforePatch, "$.hasPostingEditCode"))))
				.andExpect(jsonPath("$.levelOfDetail").value(requestObject.getOrDefault("levelOfDetail", (Integer) JsonPath.read(beforePatch, "$.levelOfDetail"))))
				.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", (Double) JsonPath.read(beforePatch, "$.multiplyFactor"))));
	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	

}
