package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

public class NextNumberConstantApiTest extends ApiTestBaseNew<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("documentType", newId());
		map.put("resetNumber", 2);
		map.put("resetMethod", "CY");
		map.put("includeMonthInNextNumber", false);
		map.put("includeYearInNextNumber", true);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/nextNumberConstants/";
	}

	@Override
	protected String existingId() {
		return "FD";
	}

	@Override
	protected String newId() {
		return "FM";
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		performer.performDelete(baseUrl() + newId());
		super.createTestWithPost();
	}
	
	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		performer.performDelete(baseUrl() + newId());
		super.createTestWithPut();
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("resetNumber");
		super.updateTestWithPatch();
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.documentType").value(existingId))
		.andExpect(jsonPath("$.resetNumber").value(0))
		.andExpect(jsonPath("$.resetMethod").value("CY"))
		.andExpect(jsonPath("$.includeMonthInNextNumber").value(true))
		.andExpect(jsonPath("$.includeYearInNextNumber").value(true));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action.andExpect(jsonPath("$._embedded.nextNumberConstants").exists());
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.documentType").value(requestObject.get("documentType")))
			.andExpect(jsonPath("$.resetNumber").value(requestObject.get("resetNumber")))
			.andExpect(jsonPath("$.resetMethod").value(requestObject.get("resetMethod")))
			.andExpect(jsonPath("$.includeMonthInNextNumber").value(requestObject.get("includeMonthInNextNumber")))
			.andExpect(jsonPath("$.includeYearInNextNumber").value(requestObject.get("includeYearInNextNumber")));
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.documentType").value(existingId))
			.andExpect(jsonPath("$.resetNumber").value((Integer) JsonPath.read(beforeUpdateJson, "$.resetNumber")))
			.andExpect(jsonPath("$.resetMethod").value(requestObject.get("resetMethod")))
			.andExpect(jsonPath("$.includeMonthInNextNumber").value(requestObject.get("includeMonthInNextNumber")))
			.andExpect(jsonPath("$.includeYearInNextNumber").value(requestObject.get("includeYearInNextNumber")));	
	}

	

}
