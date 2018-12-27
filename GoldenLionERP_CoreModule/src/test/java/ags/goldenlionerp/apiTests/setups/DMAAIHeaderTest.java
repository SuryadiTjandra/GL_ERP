package ags.goldenlionerp.apiTests.setups;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;

public class DMAAIHeaderTest extends ApiTestBase<Integer> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
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
	protected String baseUrl() {
		return "/api/dmaai/";
	}

	@Override
	protected Integer existingId() {
		return 3220;
	}

	@Override
	protected Integer newId() {
		return 3399;
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		assumeExists(baseUrl+existingId);
		
		String getRes = performer.performGet(baseUrl + existingId)
						.andReturn().getResponse().getContentAsString();
		String detailsLink = JsonPath.read(getRes, "$._links.details.href");
		String linkRes = performer.performGet(detailsLink)
						.andReturn().getResponse().getContentAsString();
		String detailLink = JsonPath.read(linkRes, "$._embedded.dmaaiDetails[0]._links.self.href");
		assumeExists(detailLink);
		
		performer.performDelete(baseUrl + existingId)
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		refreshData();

		performer.performGet(baseUrl + existingId)
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		performer.performGet(detailLink)
			.andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		String getRes = action
			.andExpect(jsonPath("$.dmaaiNo").value(existingId))
			.andExpect(jsonPath("$.description1").value("Work In Proccess"))
			.andExpect(jsonPath("$.description2").value(""))
			.andExpect(jsonPath("$.hasPostingEditCode").value(true))
			.andExpect(jsonPath("$.levelOfDetail").value(3))
			.andExpect(jsonPath("$.multiplyFactor").value(1))
			.andReturn().getResponse().getContentAsString();

		String detailLink = JsonPath.read(getRes, "$._links.details.href");
		String linkRes = performer.performGet(detailLink)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Collection<Integer> dmaaiNos = JsonPath.read(linkRes, "$._embedded.dmaaiDetails[*].dmaaiNo");
		dmaaiNos.forEach(no -> assertEquals(existingId, no));
				
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.dmaai").exists());
		
	}

	@Override
	public void assertCreateWithPostResultOuter(ResultActions action) throws Exception {
		assertCreateWithPostResult(action);
	}
	
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.dmaaiNo").value(newId))
			.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", "")))
			.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", "")))
			.andExpect(jsonPath("$.hasPostingEditCode").value(requestObject.getOrDefault("hasPostingEditCode", false)))
			.andExpect(jsonPath("$.levelOfDetail").value(requestObject.getOrDefault("levelOfDetail", 0)))
			.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", 0)));
		
	}

	@Override
	public void assertUpdateWithPatchResultOuter(ResultActions action, String beforeUpdateJson) throws Exception {
		assertUpdateWithPatchResult(action, beforeUpdateJson);
	}
	
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.dmaaiNo").value(existingId))
			.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", (String) JsonPath.read(beforeUpdateJson, "$.description1"))))
			.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", (String) JsonPath.read(beforeUpdateJson, "$.description2"))))
			.andExpect(jsonPath("$.hasPostingEditCode").value(requestObject.getOrDefault("hasPostingEditCode", (Boolean) JsonPath.read(beforeUpdateJson, "$.hasPostingEditCode"))))
			.andExpect(jsonPath("$.levelOfDetail").value(requestObject.getOrDefault("levelOfDetail", (Integer) JsonPath.read(beforeUpdateJson, "$.levelOfDetail"))))
			.andExpect(jsonPath("$.multiplyFactor").value(requestObject.getOrDefault("multiplyFactor", (Double) JsonPath.read(beforeUpdateJson, "$.multiplyFactor"))));
		
	}

	

}
