package ags.goldenlionerp.apiTests.accounting;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;

public class ChartOfAccountApiTest extends ApiTestBase<String>{

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("objectAccountCode", newId());
		map.put("description", "Test");
		map.put("descriptionLong", "Teeeeesssttt");
		map.put("levelOfDetail", 5);
		map.put("postingEditCode", true);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/chartOfAccounts/";
	}

	@Override
	protected String existingId() {
		return "111300";
	}

	@Override
	protected String newId() {
		return "123456";
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("description");
		super.updateTestWithPatch();		
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.objectAccountCode").value(existingId))
		.andExpect(jsonPath("$.description").value("Bank IDR"))
		.andExpect(jsonPath("$.levelOfDetail").value(6))
		.andExpect(jsonPath("$.postingEditCode").value(false));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.objectAccountCode").value(newId))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
		.andExpect(jsonPath("$.levelOfDetail").value(requestObject.get("levelOfDetail")))
		.andExpect(jsonPath("$.postingEditCode").value(requestObject.get("postingEditCode")))
		;
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.objectAccountCode").value(existingId))
		.andExpect(jsonPath("$.description").value((String) JsonPath.read(beforeUpdateJson, "$.description")))
		.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
		.andExpect(jsonPath("$.levelOfDetail").value(requestObject.get("levelOfDetail")))
		.andExpect(jsonPath("$.postingEditCode").value(requestObject.get("postingEditCode")))
		;
	}

}
