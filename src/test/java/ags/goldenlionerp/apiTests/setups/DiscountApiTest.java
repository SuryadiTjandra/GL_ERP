package ags.goldenlionerp.apiTests.setups;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;

public class DiscountApiTest extends ApiTestBaseNew<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("discountCode", newId());
		map.put("description", "Test Discount");
		map.put("discountPercentage1", 25);
		map.put("discountAmount2", 10000);
		map.put("discountPercentage3", 12.123);
		map.put("discountAmount4", 5500.25);
		map.put("discountPercentage5", 8);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/discounts/";
	}

	@Override
	protected String existingId() {
		return "D00";
	}

	@Override
	protected String newId() {
		return "TEST";
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.discountCode").value(existingId))
			.andExpect(jsonPath("$.description").value("Discount 10%"))
			.andExpect(jsonPath("$.discountPercentage1").value(10))
			.andExpect(jsonPath("$.discountPercentage2").value(0))
			.andExpect(jsonPath("$.discountPercentage3").value(0))
			.andExpect(jsonPath("$.discountPercentage4").value(0))
			.andExpect(jsonPath("$.discountPercentage5").value(0))
			.andExpect(jsonPath("$.discountAmount1").value(0))
			.andExpect(jsonPath("$.discountAmount2").value(0))
			.andExpect(jsonPath("$.discountAmount3").value(0))
			.andExpect(jsonPath("$.discountAmount4").value(0))
			.andExpect(jsonPath("$.discountAmount5").value(0));
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.discounts").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.discountCode").value(newId))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
			.andExpect(jsonPath("$.discountPercentage1").value(requestObject.getOrDefault("discountPercentage1", 0)))
			.andExpect(jsonPath("$.discountPercentage2").value(requestObject.getOrDefault("discountPercentage2", 0)))
			.andExpect(jsonPath("$.discountPercentage3").value(requestObject.getOrDefault("discountPercentage3", 0)))
			.andExpect(jsonPath("$.discountPercentage4").value(requestObject.getOrDefault("discountPercentage4", 0)))
			.andExpect(jsonPath("$.discountPercentage5").value(requestObject.getOrDefault("discountPercentage5", 0)))
			.andExpect(jsonPath("$.discountAmount1").value(requestObject.getOrDefault("discountAmount1", 0)))
			.andExpect(jsonPath("$.discountAmount2").value(requestObject.getOrDefault("discountAmount2", 0)))
			.andExpect(jsonPath("$.discountAmount3").value(requestObject.getOrDefault("discountAmount3", 0)))
			.andExpect(jsonPath("$.discountAmount4").value(requestObject.getOrDefault("discountAmount4", 0)))
			.andExpect(jsonPath("$.discountAmount5").value(requestObject.getOrDefault("discountAmount5", 0)))
			;
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.discountCode").value(existingId))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
			.andExpect(jsonPath("$.discountPercentage1").value(requestObject.getOrDefault("discountPercentage1", (Double) JsonPath.read(beforeUpdateJson, "$.discountPercentage1"))))
			.andExpect(jsonPath("$.discountPercentage2").value(requestObject.getOrDefault("discountPercentage2", (Double) JsonPath.read(beforeUpdateJson, "$.discountPercentage2"))))
			.andExpect(jsonPath("$.discountPercentage3").value(requestObject.getOrDefault("discountPercentage3", (Double) JsonPath.read(beforeUpdateJson, "$.discountPercentage3"))))
			.andExpect(jsonPath("$.discountPercentage4").value(requestObject.getOrDefault("discountPercentage4", (Double) JsonPath.read(beforeUpdateJson, "$.discountPercentage4"))))
			.andExpect(jsonPath("$.discountPercentage5").value(requestObject.getOrDefault("discountPercentage5", (Double) JsonPath.read(beforeUpdateJson, "$.discountPercentage5"))))
			.andExpect(jsonPath("$.discountAmount1").value(requestObject.getOrDefault("discountAmount1", (Double) JsonPath.read(beforeUpdateJson, "$.discountAmount1"))))
			.andExpect(jsonPath("$.discountAmount2").value(requestObject.getOrDefault("discountAmount2", (Double) JsonPath.read(beforeUpdateJson, "$.discountAmount2"))))
			.andExpect(jsonPath("$.discountAmount3").value(requestObject.getOrDefault("discountAmount3", (Double) JsonPath.read(beforeUpdateJson, "$.discountAmount3"))))
			.andExpect(jsonPath("$.discountAmount4").value(requestObject.getOrDefault("discountAmount4", (Double) JsonPath.read(beforeUpdateJson, "$.discountAmount4"))))
			.andExpect(jsonPath("$.discountAmount5").value(requestObject.getOrDefault("discountAmount5", (Double) JsonPath.read(beforeUpdateJson, "$.discountAmount5"))))
			;
	}


	

}
