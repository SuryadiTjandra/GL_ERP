package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;

@Transactional
public class ItemMasterApiTest extends ApiTestBase<String> {

	@Override
	protected Map<String, Object> requestObject() throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		map.put("itemCode", newId());
		map.put("itemCodeShort", "123456");
		map.put("barcode", "TESTBARCODE");
		map.put("description", "test");
		map.put("descriptionLong", "teeeeeeeeeesssssssttttt");
		map.put("inventoryLotCreation", "true");
		
		Map<String, String> mapUom = new HashMap<>();
		mapUom.put("primaryUnitOfMeasure", "CM");
		mapUom.put("secondaryUnitOfMeasure", "MT");
		map.put("unitsOfMeasure", mapUom);
		
		Map<String, String> mapCat = new HashMap<>();
		mapCat.put("categoryCode", "FD");
		mapCat.put("brandCode", "EPSON");
		map.put("dataGroups", mapCat);
		
		Map<String, String> mapPar = new HashMap<>();
		mapPar.put("discountFactor", "12.34567");
		map.put("parameters", mapPar);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/items/";
	}

	@Override
	protected String existingId() {
		return "ACC.RIBBON - LABEL";
	}

	@Override
	protected String newId() {
		return "TESTITEM";
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("barcode");
		
		super.updateTestWithPatch();
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.itemCode").value(existingId))
		.andExpect(jsonPath("$.itemCodeShort").value(23))
		.andExpect(jsonPath("$.unitsOfMeasure.primaryUnitOfMeasure").value("UNT"))
		.andExpect(jsonPath("$.inventoryCostLevel").value("2"))
		.andExpect(jsonPath("$.unitsOfMeasure.dualQuantityUnitOfMeasure").value(false))
		.andExpect(jsonPath("$.parameters.discountFactor").value(0))
		.andExpect(jsonPath("$.dataGroups.brandCode").value("EPSON"))
		.andExpect(jsonPath("$.dataGroups.salesReportingCode5").value(""));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.items[1].itemCodeShort").value(82))
		.andExpect(jsonPath("$._embedded.items.length()").value(20))
		.andExpect(jsonPath("$.page.totalElements").value(1403));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		Map<String, String> mapUom = (Map<String, String>) requestObject.get("unitsOfMeasure");
		Map<String, String> mapPar = (Map<String, String>) requestObject.get("parameters");
		Map<String, String> mapCat = (Map<String, String>) requestObject.get("dataGroups");
		
		action
		.andExpect(jsonPath("$.itemCode").value(requestObject.get("itemCode")))
		.andExpect(jsonPath("$.itemCodeShort").value(Integer.valueOf(requestObject.get("itemCodeShort").toString())))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
		.andExpect(jsonPath("$.unitsOfMeasure.primaryUnitOfMeasure").value(mapUom.get("primaryUnitOfMeasure")))
		.andExpect(jsonPath("$.unitsOfMeasure.secondaryUnitOfMeasure").value(mapUom.get("secondaryUnitOfMeasure")))
		.andExpect(jsonPath("$.unitsOfMeasure.salesUnitOfMeasure").value(mapUom.get("primaryUnitOfMeasure")))
		.andExpect(jsonPath("$.dataGroups.brandCode").value(mapCat.get("brandCode")))
		.andExpect(jsonPath("$.parameters.discountFactor").value(mapPar.get("discountFactor")))
		.andExpect(jsonPath("$.lastTransactionDate").value(Matchers.nullValue()))
		.andExpect(jsonPath("$.lastSynchronizedDate").value(Matchers.nullValue()))
		;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		Map<String, String> mapUom = (Map<String, String>) requestObject.get("unitsOfMeasure");		
		Map<String, String> mapPar = (Map<String, String>) requestObject.get("parameters");
		Map<String, String> mapCat = (Map<String, String>) requestObject.get("dataGroups");
		
		action
		.andExpect(jsonPath("$.itemCode").value(existingId))
		.andExpect(jsonPath("$.itemCodeShort").value((Integer) JsonPath.read(beforeUpdateJson, "$.itemCodeShort")))
		.andExpect(jsonPath("$.barcode").value((String) JsonPath.read(beforeUpdateJson, "$.barcode")))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
		.andExpect(jsonPath("$.unitsOfMeasure.primaryUnitOfMeasure").value(mapUom.get("primaryUnitOfMeasure")))
		.andExpect(jsonPath("$.unitsOfMeasure.secondaryUnitOfMeasure").value(mapUom.get("secondaryUnitOfMeasure")))
		.andExpect(jsonPath("$.dataGroups.brandCode").value(mapCat.get("brandCode")))
		.andExpect(jsonPath("$.parameters.discountFactor").value(mapPar.get("discountFactor")))
		;
		
	}

}
