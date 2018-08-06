package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.masterdata.itembranchinfo.ItemBranchInfoPK;

public class ItemBranchInfoApiTest extends ApiTestBase<ItemBranchInfoPK> {


	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("branchCode", newId().getBranchCode());
		map.put("itemCode", newId().getItemCode());
		map.put("stockingType", "C");
		map.put("reorderQty", "100.15");
		map.put("serialNumberRequired", true);
		
		Map<String, Object> mapPar = new HashMap<>();
		mapPar.put("profitMarginMinimumFactor", 25.75);
		mapPar.put("rankByInventoryInvestment", "C");
		map.put("parameters", mapPar);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/itembranches/";
	}

	@Override
	protected ItemBranchInfoPK existingId() {
		return new ItemBranchInfoPK("110", "ACC.BATERAI - CMOS\t\t\t");
	}

	@Override
	protected ItemBranchInfoPK newId() {
		return new ItemBranchInfoPK("123", "TEST.TEST");
	}
	

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("stockingType");
		
		super.updateTestWithPatch();
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
		.andExpect(jsonPath("$.glClass").value("INFG"))
		.andExpect(jsonPath("$.reorderQty").value(0))
		.andExpect(jsonPath("$.inventoryLotCreation").value(false))
		.andExpect(jsonPath("$.lotStatusCode").value(""))
		.andExpect(jsonPath("$.parameters.discountFactor").value(0))
		.andExpect(jsonPath("$.dataGroups.brandCode").value(""))
		.andExpect(jsonPath("$.dataGroups.salesReportingCode5").value(""));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.itembranches[1].branchCode").value("110"))
		.andExpect(jsonPath("$._embedded.itembranches.length()").value(20))
		.andExpect(jsonPath("$.page.totalElements").value(3008));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		Map<String, Object> mapPar = (Map<String, Object>) requestObject.get("parameters");
		
		action
		.andExpect(jsonPath("$.itemCode").value(newId.getItemCode()))
		.andExpect(jsonPath("$.branchCode").value(newId.getBranchCode()))
		.andExpect(jsonPath("$.stockingType").value(requestObject.get("stockingType")))
		.andExpect(jsonPath("$.reorderQty").value(requestObject.get("reorderQty")))
		.andExpect(jsonPath("$.serialNumberRequired").value(requestObject.get("serialNumberRequired")))
		.andExpect(jsonPath("$.minimumReorderQty").value(0))
		.andExpect(jsonPath("$.parameters.profitMarginMinimumFactor").value(mapPar.get("profitMarginMinimumFactor")))
		.andExpect(jsonPath("$.dataGroups.brandCode").value(""))
		.andExpect(jsonPath("$.parameters.discountFactor").value(0));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		Map<String, Object> mapPar = (Map<String, Object>) requestObject.get("parameters");
		
		action
		.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
		.andExpect(jsonPath("$.branchCode").value(existingId.getBranchCode()))
		.andExpect(jsonPath("$.stockingType").value((String) JsonPath.read(beforeUpdateJson, "$.stockingType")))
		.andExpect(jsonPath("$.reorderQty").value(requestObject.get("reorderQty")))
		.andExpect(jsonPath("$.serialNumberRequired").value(requestObject.get("serialNumberRequired")))
		.andExpect(jsonPath("$.minimumReorderQty").value((Double) JsonPath.read(beforeUpdateJson, "$.minimumReorderQty")))
		.andExpect(jsonPath("$.parameters.profitMarginMinimumFactor").value(mapPar.get("profitMarginMinimumFactor")))
		.andExpect(jsonPath("$.dataGroups.brandCode").value((String) JsonPath.read(beforeUpdateJson, "$.dataGroups.brandCode")))
		.andExpect(jsonPath("$.parameters.discountFactor").value((Double) JsonPath.read(beforeUpdateJson, "$.parameters.discountFactor")))
		;
		
	}


}
