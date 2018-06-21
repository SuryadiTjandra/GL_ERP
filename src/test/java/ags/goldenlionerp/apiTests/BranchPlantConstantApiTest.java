package ags.goldenlionerp.apiTests;

import static org.junit.Assert.assertEquals;
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
public class BranchPlantConstantApiTest extends ApiTestBase<String>{

	@Override
	Map<String, Object> requestObject() {
		Map<String, Object> map = new HashMap<>();
		map.put("branchAddressCode", "");
		map.put("purchaseCostMethod", "02");
		map.put("salesInventoryCostMethod", "01");
		map.put("interfaceToGL", "");
		map.put("inventoryLotCreation", "Y");
		map.put("locationControl", "Y");
		map.put("warehouseControl", "");
		map.put("inputUserId", "1234567890");
		map.put("branchCode", newId);
		return map;
	}

	@Override String baseUrl() { return "/api/branchPlantConstants/{id}"; }
	@Override String existingId() { return "110"; }
	@Override String newId() { return "100"; }

	String buAssociationUrl = "/api/businessUnits/{id}/configuration";
	
	@Test
	@Override
	public void getTestSingle() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		String getResult1 = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.branchCode").value(existingId))
				.andExpect(jsonPath("$.purchaseCostMethod").value("02"))
				.andExpect(jsonPath("$.salesInventoryCostMethod").value("02"))
				.andReturn().getResponse().getContentAsString();
		
		String url2 = this.buAssociationUrl.replace("{id}", existingId);
		String getResult2 = mockMvc.perform(get(url2))
							.andReturn().getResponse().getContentAsString();
		
		assertEquals(getResult1, getResult2);
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
		
		String collectionUrl = this.baseUrl.replace("{id}", "");
		mockMvc.perform(post(collectionUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
		
		String url2 = this.buAssociationUrl.replace("{id}", newId);
		String getResult2 = mockMvc.perform(get(url2))
				.andReturn().getResponse().getContentAsString();
		assertEquals(getResult, getResult2);
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
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
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
		String url = this.buAssociationUrl.replace("{id}", newId);
		assumeNotExists(url);
		
		mockMvc.perform(put(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(newId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
		
		String url2 = this.baseUrl.replace("{id}", newId);
		String getResult2 = mockMvc.perform(get(url2))
				.andReturn().getResponse().getContentAsString();
		assertEquals(getResult, getResult2);
	}

	@Override
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		assumeExists(url);
		
		mockMvc.perform(delete(url))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		mockMvc.perform(get(url))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		String url2 = this.buAssociationUrl.replace("{id}", existingId);
		mockMvc.perform(get(url2))
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
		
		requestObject.remove("salesInventoryCostMethod");
		requestObject.remove("warehouseControl");
		
		mockMvc.perform(patch(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(existingId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andExpect(jsonPath("$.salesInventoryCostMethod").value((String) JsonPath.read(beforePatch, "$.salesInventoryCostMethod")))
				.andExpect(jsonPath("$.warehouseControl").value((String) JsonPath.read(beforePatch, "$.warehouseControl")))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}

	@Test
	@Override
	@Rollback
	public void updateTestWithPut() throws Exception {
		String url = this.baseUrl.replace("{id}", existingId);
		assumeExists(url);
		
		requestObject.remove("salesInventoryCostMethod");
		requestObject.remove("warehouseControl");
		
		mockMvc.perform(put(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.branchCode").value(existingId))
				.andExpect(jsonPath("$.purchaseCostMethod").value(requestObject.get("purchaseCostMethod")))
				.andExpect(jsonPath("$.inventoryLotCreation").value(requestObject.get("inventoryLotCreation")))
				.andExpect(jsonPath("$.salesInventoryCostMethod").value(""))
				.andExpect(jsonPath("$.warehouseControl").value(""))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult);
	}
}
