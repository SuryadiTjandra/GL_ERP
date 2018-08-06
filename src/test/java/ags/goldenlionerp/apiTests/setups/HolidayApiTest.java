package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;

public class HolidayApiTest extends ApiTestBaseNew<LocalDate> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("offDate", newId());
		map.put("offDateType", "I");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/holidays/";
	}

	@Override
	protected LocalDate existingId() {
		return LocalDate.of(2018, 8, 17);
	}

	@Override
	protected LocalDate newId() {
		return LocalDate.of(2018, 12, 25);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.offDate").value(existingId.toString()))
			.andExpect(jsonPath("$.offDateType").value("N"))
			.andExpect(jsonPath("$.description").value("Hari Kemerdekaan Indonesia"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.holidays").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.offDate").value(newId.toString()))
			.andExpect(jsonPath("$.offDateType").value(requestObject.getOrDefault("offDateType", "")))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
			;
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.offDate").value(existingId.toString()))
			.andExpect(jsonPath("$.offDateType").value(requestObject.getOrDefault("offDateType", (String) JsonPath.read(beforeUpdateJson, "$.offDateType"))))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
			;
	}
}
