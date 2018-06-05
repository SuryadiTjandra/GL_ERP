package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

@Transactional
public class BranchPlantConstantApiTest extends ApiTestBase{

	@Override
	Map<String, String> populateRequestObject() {
		Map<String, String> map = new HashMap<>();
		map.put("branchAddressCode", "");
		map.put("purchaseCostMethod", "02");
		map.put("salesInventoryCostMethod", "01");
		map.put("interfaceToGL", "");
		map.put("inventoryLotCreation", "Y");
		map.put("locationControl", "Y");
		map.put("warehouseControl", "");
		map.put("inputUserId", "1234567890");
		return map;
	}

	@Override String baseUrl() { return "/api/businessUnits/{id}/configuration"; }
	@Override String existingId() { return "110"; }
	@Override String newId() { return "100"; }


	@Test
	@Override
	public void getTestSingle() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.branchCode").value(existingId))
				.andExpect(jsonPath("$.purchaseCostMethod").value("02"))
				.andExpect(jsonPath("$.salesInventoryCostMethod").value("02"));
	}

	@Test
	@Override
	public void getTestCollection() throws Exception {
		//no need to test this
	}

	@Test
	@Override
	@Rollback
	public void createTestWithPost() throws Exception {
		String url = this.baseUrl.replace("{id}", newId);
		assumeNotExists(url);
		
		mockMvc.perform(post(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(existingId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
	}

	@Test
	@Override
	@Rollback
	public void createTestWithPut() throws Exception {		
		String url = this.baseUrl.replace("{id}", newId);
		assumeNotExists(url);
		
		mockMvc.perform(put(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
	}

	@Override
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		assumeExists(url);
		
		mockMvc.perform(delete(url))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		mockMvc.perform(get(url))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

	@Test
	@Override
	@Rollback
	public void updateTestWithPatch() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		assumeExists(url);
		
		String beforePatch = mockMvc.perform(get(url))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("salesInventoryPurchaseMethod");
		requestObject.remove("warehouseControl");
		
		mockMvc.perform(patch(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andExpect(jsonPath("$.salesInventoryPurchaseMethod").value(JsonPath.read(beforePatch, "$.salesInventoryPurchaseMethod")))
				.andExpect(jsonPath("$.warehouseControl").value(JsonPath.read(beforePatch, "$.warehouseControl")))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}

	@Test
	@Override
	@Rollback
	public void updateTestWithPut() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		assumeExists(url);
		
		requestObject.remove("salesInventoryPurchaseMethod");
		requestObject.remove("warehouseControl");
		
		mockMvc.perform(put(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andExpect(jsonPath("$.salesInventoryPurchaseMethod").value(""))
				.andExpect(jsonPath("$.warehouseControl").value(""))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult);
	}
}
