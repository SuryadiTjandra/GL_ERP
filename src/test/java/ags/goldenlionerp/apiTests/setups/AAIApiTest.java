package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;
import ags.goldenlionerp.application.setups.aai.AutomaticAccountingInstructionPK;

public class AAIApiTest extends ApiTestBaseNew<AutomaticAccountingInstructionPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("sequence", newId().getSequence());
		map.put("aaiCode", newId().getAaiCode());
		map.put("description1", "Test description");
		map.put("description2", "Test description 2");
		map.put("companyId", "11000");
		map.put("businessUnitId", "100");
		map.put("objectAccountCode", "111110");
		map.put("subsidiaryCode", "");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/aai/";
	}

	@Override
	protected AutomaticAccountingInstructionPK existingId() {
		return new AutomaticAccountingInstructionPK(11500, "GLA200");
	}

	@Override
	protected AutomaticAccountingInstructionPK newId() {
		return new AutomaticAccountingInstructionPK(12345, "TESTTEST");
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("description1");
		super.updateTestWithPatch();
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
			.andExpect(jsonPath("$.aaiCode").value(existingId.getAaiCode()))
			.andExpect(jsonPath("$.companyId").value("00000"))
			.andExpect(jsonPath("$.businessUnitId").value(""))
			.andExpect(jsonPath("$.objectAccountCode").value("120000"))
			.andExpect(jsonPath("$.subsidiaryCode").value(""))
			.andExpect(jsonPath("$.description1").value("Aktiva Tidak Lancar"))
			.andExpect(jsonPath("$.description2").value("Aktiva"))
			.andExpect(jsonPath("$.description3").value(""));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.automaticAccountingInstructions").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
			.andExpect(jsonPath("$.aaiCode").value(newId.getAaiCode()))
			.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", "")))
			.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", "")))
			.andExpect(jsonPath("$.description3").value(requestObject.getOrDefault("description3", "")))
			.andExpect(jsonPath("$.companyId").value(requestObject.get("companyId")))
			.andExpect(jsonPath("$.objectAccountCode").value(requestObject.get("objectAccountCode")));
		
		String getRes = action.andReturn().getResponse().getContentAsString();
		String accountLink = JsonPath.read(getRes, "$._links.accountMaster.href");
		performer.performGet(accountLink)
				.andExpect(jsonPath("companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("objectAccountCode").value(requestObject.get("objectAccountCode")))
				.andExpect(jsonPath("subsidiaryCode").value(requestObject.get("subsidiaryCode")));
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
			.andExpect(jsonPath("$.aaiCode").value(existingId.getAaiCode()))
			.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", (String) JsonPath.read(beforeUpdateJson, "$.description1"))))
			.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", (String) JsonPath.read(beforeUpdateJson, "$.description2"))))
			.andExpect(jsonPath("$.description3").value(requestObject.getOrDefault("description3", (String) JsonPath.read(beforeUpdateJson, "$.description3"))))
			.andExpect(jsonPath("$.companyId").value(requestObject.getOrDefault("companyId", (String) JsonPath.read(beforeUpdateJson, "$.companyId"))))
			.andExpect(jsonPath("$.objectAccountCode").value(requestObject.getOrDefault("objectAccountCode", (String) JsonPath.read(beforeUpdateJson, "$.objectAccountCode"))));
			
	}

}
