package ags.goldenlionerp.apiTests.setups;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;

@Transactional
public class BranchPlantConstantApiTest extends ApiTestBaseNew<String>{

	@Override
	protected Map<String, Object> requestObject() {
		Map<String, Object> map = new HashMap<>();
		map.put("branchAddressCode", "");
		map.put("purchaseCostMethod", "02");
		map.put("salesInventoryCostMethod", "01");
		map.put("interfaceToGL", false);
		map.put("inventoryLotCreation", true);
		map.put("locationControl", Boolean.TRUE);
		map.put("warehouseControl", Boolean.FALSE);
		map.put("inputUserId", "1234567890");
		map.put("branchCode", newId);
		return map;
	}

	@Override protected String baseUrl() { return "/api/branchPlantConstants/"; }
	@Override protected String existingId() { return "110"; }
	@Override protected String newId() { return "100"; }

	String buAssociationUrl;
	
	@Override @Before
	public void setUp() throws Exception {
		super.setUp();
		String getRes = performer.performGet(new BusinessUnitApiTest().baseUrl() + existingId())
						.andReturn().getResponse().getContentAsString();
		buAssociationUrl = JsonPath.read(getRes, "$._links.configuration.href");
		buAssociationUrl = buAssociationUrl.replace(existingId(), "{id}");
	}
	
	@Test
	public void testBaseAndAssociationUrlSameResult() throws Exception {
		assumeExists(baseUrl + existingId);
		assumeExists(buAssociationUrl.replace("{id}", existingId));
		
		String getResult1 = performer.performGet(baseUrl + existingId)
								.andReturn().getResponse().getContentAsString();
		String getResult2 = performer.performGet(buAssociationUrl.replace("{id}", existingId))
								.andReturn().getResponse().getContentAsString();
		
		assertEquals(getResult1, getResult2);
	}

	@Test
	@Override
	@Rollback
	public void createTestWithPut() throws Exception {		
		assumeNotExists(baseUrl + newId);
		
		performer.performPut(baseUrl + newId, requestObject)
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		refreshData();
		
		String getResult = performer.performGet(baseUrl + newId)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
	}
	
	@Test
	@Rollback
	public void createTestWithPutOnAssociation() throws Exception {	
		String url = buAssociationUrl.replace("{id}", newId);
		assumeNotExists(url);
		
		performer.performPut(url, requestObject)
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		refreshData();
		
		String getResult = performer.performGet(url)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
		
		String getResult2 = performer.performGet(baseUrl + newId)
				.andReturn().getResponse().getContentAsString();
		assertEquals(getResult, getResult2);
	}

	@Test
	@Override
	@Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("salesInventoryCostMethod");
		requestObject.remove("warehouseControl");
		
		super.updateTestWithPatch();
	}

	@Test
	@Override
	@Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl + existingId);
		
		requestObject.remove("salesInventoryCostMethod");
		requestObject.remove("warehouseControl");
		
		performer.performPut(baseUrl + existingId, requestObject)
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		refreshData();
		
		String getResult = performer.performGet(baseUrl + existingId)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(existingId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andExpect(jsonPath("$.salesInventoryCostMethod").value(""))
				.andExpect(jsonPath("$.warehouseControl").value(false))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.branchCode").value(existingId))
		.andExpect(jsonPath("$.purchaseCostMethod").value("02"))
		.andExpect(jsonPath("$.salesInventoryCostMethod").value("02"));
	}
	
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.branchCode").value(newId))
		.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
		.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
		;
	}



	

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.branchCode").value(existingId))
		.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
		.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
		.andExpect(jsonPath("$.salesInventoryCostMethod").value((String) JsonPath.read(beforeUpdateJson, "$.salesInventoryCostMethod")))
		.andExpect(jsonPath("$.warehouseControl").value((Boolean) JsonPath.read(beforeUpdateJson, "$.warehouseControl")))
		;
	}
	
	@Test
	@Override
	public void getTestCollection() throws Exception {
		//no need to test this
	}
	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		// N/A
	}


}
