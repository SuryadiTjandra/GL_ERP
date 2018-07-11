package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.itemLocation.ItemLocationPK;
import ags.goldenlionerp.util.WebIdUtil;

public class ItemLocationApiTest extends ApiTestBase<ItemLocationPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("itemCode", newId().getItemCode());
		map.put("businessUnitId", newId().getBusinessUnitId());
		map.put("locationId", newId().getLocationId());
		map.put("serialLotNo", newId().getSerialLotNo());
		map.put("lotStatusCode", "A");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/itemLocations/";
	}

	@Override
	ItemLocationPK existingId() {
		return new ItemLocationPK("ACC.MOUSE - B100\t\t\t", "110", "GDU", "");
	}

	@Override
	ItemLocationPK newId() {
		return new ItemLocationPK("TEST.TEST", "123", "ABC.1.2.3", "test");
	}

	
	@Override @Test
	public void getTestSingle() throws Exception {
		String urlId = WebIdUtil.toWebId(existingId.toString());
		
		mockMvc.perform(get(baseUrl + urlId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		
	}

	@Override@Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl + newId);
		//System.out.println(mapper.writeValueAsString(requestObject));
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
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
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
		
	}

	@Override@Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(put(baseUrl + newId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		String url = baseUrl + WebIdUtil.toWebId(existingId.toString());
		assumeExists(url);
		
		mockMvc.perform(delete(url))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	
		mockMvc.perform(get(url))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

	@Override@Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
				.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
				.andExpect(jsonPath("$.serialLotNo").value(existingId.getSerialLotNo()))
				.andExpect(jsonPath("$.locationId").value(existingId.getLocationId()))
				.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
				.andExpect(jsonPath("$.locationStatus").value((String) JsonPath.read(beforePatch, "$.locationStatus")))
				.andExpect(jsonPath("$.quantities.quantityOnPurchaseOrder").value((Double) JsonPath.read(beforePatch, "$.quantities.quantityOnPurchaseOrder")))
				.andExpect(jsonPath("$.quantities.quantityOnSoftCommit").value((Double) JsonPath.read(beforePatch, "$.quantities.quantityOnSoftCommit")))
				.andExpect(jsonPath("$.quantities.unitOfMeasure").value((String) JsonPath.read(beforePatch, "$.quantities.unitOfMeasure")))
				.andExpect(jsonPath("$.expiredDate").value((String) JsonPath.read(beforePatch, "$.expiredDate")))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}

	@Override@Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	
}
