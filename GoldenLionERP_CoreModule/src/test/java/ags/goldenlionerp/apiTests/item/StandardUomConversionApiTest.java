package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.item.uomconversion.standard.StandardUomConversionPK;

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
		return "/api/stduomconvs/";
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
			.andExpect(jsonPath("$._embedded.standardUomConversions").exists());
		
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
		action
			.andExpect(jsonPath("$.uomFrom").value(existingId.getUomFrom()))
			.andExpect(jsonPath("$.uomTo").value(existingId.getUomTo()))
			.andExpect(jsonPath("$.conversionValue").value(requestObject.get("conversionValue")));
		
	}
	
	@Test @Rollback
	public void createTestWithPost_ExistingConversion() throws Exception {
		StandardUomConversionPK pk = new StandardUomConversionPK("CM", "KM");
		assumeNotExists(baseUrl + pk);
		
		requestObject.put("uomFrom", pk.getUomFrom());
		requestObject.put("uomTo", pk.getUomTo());
		requestObject.put("conversionValue", 12345678);
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(status().is4xxClientError());
		
		requestObject.put("conversionValue", 0.00001);
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(status().is2xxSuccessful());
	}

}
