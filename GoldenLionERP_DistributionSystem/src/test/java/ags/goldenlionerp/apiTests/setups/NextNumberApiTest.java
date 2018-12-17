package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberPK;

public class NextNumberApiTest extends ApiTestBase<NextNumberPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId().getCompanyId());
		map.put("documentOrBatchType", newId().getDocumentOrBatchType());
		map.put("year", newId().getYear());
		map.put("month", newId().getMonth());
		map.put("nextSequence", 13);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/nextNumbers/";
	}

	@Override
	protected NextNumberPK existingId() {
		return new NextNumberPK("11000", "RI", 2018, 4);
	}

	@Override
	protected NextNumberPK newId() {
		return new NextNumberPK("11000", "RI", 2018, 5);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.documentOrBatchType").value(existingId.getDocumentOrBatchType()))
			.andExpect(jsonPath("$.year").value(existingId.getYear()))
			.andExpect(jsonPath("$.month").value(existingId.getMonth()))
			.andExpect(jsonPath("$.nextSequence").value(790));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("_embedded.nextNumbers").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(newId.getCompanyId()))
			.andExpect(jsonPath("$.documentOrBatchType").value(newId.getDocumentOrBatchType()))
			.andExpect(jsonPath("$.year").value(newId.getYear()))
			.andExpect(jsonPath("$.month").value(newId.getMonth()))
			.andExpect(jsonPath("$.nextSequence").value(requestObject.get("nextSequence")));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.documentOrBatchType").value(existingId.getDocumentOrBatchType()))
			.andExpect(jsonPath("$.year").value(existingId.getYear()))
			.andExpect(jsonPath("$.month").value(existingId.getMonth()))
			.andExpect(jsonPath("$.nextSequence").value(requestObject.get("nextSequence")));
		
	}

}
