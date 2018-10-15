package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.item.uomconversion.StandardUomConversionPK;

public class StandardUomConversionApiTest extends ApiTestBase<StandardUomConversionPK>{

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("uomFrom", newId().getUomFrom());
		map.put("uomTo", newId().getUomTo());
		map.put("conversionValue", 1234.5678);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/uomconversions/";
	}

	@Override
	protected StandardUomConversionPK existingId() {
		return new StandardUomConversionPK("CM", "MM");
	}

	@Override
	protected StandardUomConversionPK newId() {
		return new StandardUomConversionPK("CM", "KG"); //this makes no sense, but it's only for testing purposes
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.uomFrom").value(existingId.getUomFrom()))
			.andExpect(jsonPath("$.uomTo").value(existingId.getUomTo()))
			.andExpect(jsonPath("$.conversionValue").value(10));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.uomconversions").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.uomFrom").value(newId.getUomFrom()))
			.andExpect(jsonPath("$.uomTo").value(newId.getUomTo()))
			.andExpect(jsonPath("$.conversionValue").value(requestObject.get("conversionValue")));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
