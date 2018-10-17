package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.item.uomconversion.itemspecific.ItemUomConversionPK;

public class ItemUomConversionApiTest extends ApiTestBase<ItemUomConversionPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("itemCode", newId().getItemCode());
		map.put("uomFrom", newId().getUomFrom());
		map.put("uomTo", newId().getUomTo());
		map.put("conversionValue", 500);
		map.put("uomStructure", "2");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/itemuomconvs/";
	}

	@Override
	protected ItemUomConversionPK existingId() {
		return new ItemUomConversionPK("TEST2", "FT", "CM");
	}

	@Override
	protected ItemUomConversionPK newId() {
		return new ItemUomConversionPK("ACC.BROTHER-TT4000", "BL", "UNT");
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
			.andExpect(jsonPath("$.uomFrom").value(existingId.getUomFrom()))
			.andExpect(jsonPath("$.uomTo").value(existingId.getUomTo()))
			.andExpect(jsonPath("$.uomFromDescription").value("Feet"))
			.andExpect(jsonPath("$.uomToDescription").value("Centimeters"))
			.andExpect(jsonPath("$.conversionValue").value(50.0))
			.andExpect(jsonPath("$.conversionValueToPrimary").value(1));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.itemUomConversions").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.itemCode").value(newId.getItemCode()))
			.andExpect(jsonPath("$.uomFrom").value(newId.getUomFrom()))
			.andExpect(jsonPath("$.uomTo").value(newId.getUomTo()))
			.andExpect(jsonPath("$.uomFromDescription").value("Barrels"))
			.andExpect(jsonPath("$.uomToDescription").value("Unit"))
			.andExpect(jsonPath("$.conversionValue").value(500.00))
			.andExpect(jsonPath("$.conversionValueToPrimary").value(500.0))
			.andExpect(jsonPath("$.uomStructure").value("2"));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
			.andExpect(jsonPath("$.uomFrom").value(existingId.getUomFrom()))
			.andExpect(jsonPath("$.uomTo").value(existingId.getUomTo()))
			.andExpect(jsonPath("$.uomFromDescription").value("Barrels"))
			.andExpect(jsonPath("$.uomToDescription").value("Unit"))
			.andExpect(jsonPath("$.conversionValue").value(500.00))
			.andExpect(jsonPath("$.conversionValueToPrimary").value(500.0))
			.andExpect(jsonPath("$.uomStructure").value("2"));
		
	}

}
