package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

@Transactional
public class ItemMasterApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws JsonProcessingException {
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
	String baseUrl() {
		return "/api/items/";
	}

	@Override
	String existingId() {
		return "ACC.RIBBON - LABEL";
	}

	@Override
	String newId() {
		return "TESTITEM";
	}
	
	@Override
	@Test @Rollback
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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
	@Test @Rollback
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$._embedded.items[1].itemCodeShort").value(82))
				.andExpect(jsonPath("$._embedded.items.length()").value(20))
				.andExpect(jsonPath("$.page.totalElements").value(1403));

	}

	@SuppressWarnings("unchecked")
	@Override @Test @Rollback
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
		
		Map<String, String> mapUom = (Map<String, String>) requestObject.get("unitsOfMeasure");
		Map<String, String> mapPar = (Map<String, String>) requestObject.get("parameters");
		Map<String, String> mapCat = (Map<String, String>) requestObject.get("dataGroups");
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.itemCode").value(requestObject.get("itemCode")))
				.andExpect(jsonPath("$.itemCodeShort").value(Integer.valueOf(requestObject.get("itemCodeShort").toString())))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
				.andExpect(jsonPath("$.unitsOfMeasure.primaryUnitOfMeasure").value(mapUom.get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.unitsOfMeasure.secondaryUnitOfMeasure").value(mapUom.get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.unitsOfMeasure.salesUnitOfMeasure").value(mapUom.get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.dataGroups.brandCode").value(mapCat.get("brandCode")))
				.andExpect(jsonPath("$.parameters.discountFactor").value(mapPar.get("discountFactor")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
				
		
	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(get(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@SuppressWarnings("unchecked")
	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("barcode");
		
		Map<String, String> mapUom = (Map<String, String>) requestObject.get("unitsOfMeasure");		
		Map<String, String> mapPar = (Map<String, String>) requestObject.get("parameters");
		Map<String, String> mapCat = (Map<String, String>) requestObject.get("dataGroups");
		
		mockMvc.perform(patch(baseUrl + existingId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
							.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
							.andExpect(jsonPath("$.itemCode").value(existingId))
							.andExpect(jsonPath("$.itemCodeShort").value((Integer) JsonPath.read(beforePatch, "$.itemCodeShort")))
							.andExpect(jsonPath("$.barcode").value((String) JsonPath.read(beforePatch, "$.barcode")))
							.andExpect(jsonPath("$.description").value(requestObject.get("description")))
							.andExpect(jsonPath("$.descriptionLong").value(requestObject.get("descriptionLong")))
							.andExpect(jsonPath("$.unitsOfMeasure.primaryUnitOfMeasure").value(mapUom.get("primaryUnitOfMeasure")))
							.andExpect(jsonPath("$.unitsOfMeasure.secondaryUnitOfMeasure").value(mapUom.get("secondaryUnitOfMeasure")))
							.andExpect(jsonPath("$.dataGroups.brandCode").value(mapCat.get("brandCode")))
							.andExpect(jsonPath("$.parameters.discountFactor").value(mapPar.get("discountFactor")))
							.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);
	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + existingId)
				.content(mapper.writeValueAsString(requestObject)))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

}
