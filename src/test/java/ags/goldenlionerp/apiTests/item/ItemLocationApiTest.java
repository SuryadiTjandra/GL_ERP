package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;
import ags.goldenlionerp.masterdata.itemLocation.ItemLocationPK;

public class ItemLocationApiTest extends ApiTestBaseNew<ItemLocationPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("itemCode", newId().getItemCode());
		map.put("businessUnitId", newId().getBusinessUnitId());
		map.put("locationId", newId().getLocationId());
		map.put("serialLotNo", newId().getSerialLotNo());
		map.put("lotStatusCode", "A");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/itemLocations/";
	}

	@Override
	protected ItemLocationPK existingId() {
		return new ItemLocationPK("ACC.MOUSE - B100\t\t\t", "110", "GDU", "");
	}

	@Override
	protected ItemLocationPK newId() {
		return new ItemLocationPK("TEST.TEST", "123", "ABC.1.2.3", "test");
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
		.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
		.andExpect(jsonPath("$.serialLotNo").value(existingId.getSerialLotNo()))
		.andExpect(jsonPath("$.locationId").value(existingId.getLocationId()))
		.andExpect(jsonPath("$.locationStatus").value("P"))
		.andExpect(jsonPath("$.quantities.quantityOnPurchaseOrder").value(6))
		.andExpect(jsonPath("$.quantities.quantityOnSoftCommit").value(0))
		.andExpect(jsonPath("$.quantities.unitOfMeasure").value(""))
		.andExpect(jsonPath("$.expiredDate").value(Matchers.nullValue()));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.businessUnitId").value(newId.getBusinessUnitId()))
		.andExpect(jsonPath("$.itemCode").value(newId.getItemCode()))
		.andExpect(jsonPath("$.serialLotNo").value(newId.getSerialLotNo()))
		.andExpect(jsonPath("$.locationId").value(newId.getLocationId()))
		.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
		.andExpect(jsonPath("$.locationStatus").value(""))
		.andExpect(jsonPath("$.quantities.quantityOnPurchaseOrder").value(0))
		.andExpect(jsonPath("$.quantities.quantityOnSoftCommit").value(0))
		.andExpect(jsonPath("$.quantities.unitOfMeasure").value(""))
		.andExpect(jsonPath("$.expiredDate").value(Matchers.nullValue()))
		;
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
		.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
		.andExpect(jsonPath("$.serialLotNo").value(existingId.getSerialLotNo()))
		.andExpect(jsonPath("$.locationId").value(existingId.getLocationId()))
		.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
		.andExpect(jsonPath("$.locationStatus").value((String) JsonPath.read(beforeUpdateJson, "$.locationStatus")))
		.andExpect(jsonPath("$.quantities.quantityOnPurchaseOrder").value((Double) JsonPath.read(beforeUpdateJson, "$.quantities.quantityOnPurchaseOrder")))
		.andExpect(jsonPath("$.quantities.quantityOnSoftCommit").value((Double) JsonPath.read(beforeUpdateJson, "$.quantities.quantityOnSoftCommit")))
		.andExpect(jsonPath("$.quantities.unitOfMeasure").value((String) JsonPath.read(beforeUpdateJson, "$.quantities.unitOfMeasure")))
		.andExpect(jsonPath("$.expiredDate").value((String) JsonPath.read(beforeUpdateJson, "$.expiredDate")))
		;
		
	}

	
}
