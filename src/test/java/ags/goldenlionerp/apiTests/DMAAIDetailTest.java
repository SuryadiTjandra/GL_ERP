package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.system.dmaai.DMAAIDetailPK;

public class DMAAIDetailTest extends ApiTestBase<DMAAIDetailPK> {


	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("dmaaiNo", newId().getDmaaiNo());
		map.put("companyId", newId().getCompanyId());
		map.put("documentType", newId().getDocumentType());
		map.put("documentOrderType", newId().getDocumentOrderType());
		map.put("glClass", newId().getGlClass());
		map.put("manufacturingCostType", newId().getManufacturingCostType());
		map.put("businessUnitId", "110");
		map.put("objectAccountCode", "616003");
		map.put("subsidiaryCode", "");
		map.put("description1", "Test");
		map.put("description2", "TEEESSSSTT");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/dmaaiDetails/";
	}

	@Override
	DMAAIDetailPK existingId() {
		return new DMAAIDetailPK(3120, "00000", "IC", "", "*****", "");
	}

	@Override
	DMAAIDetailPK newId() {
		return new DMAAIDetailPK(3120, "11000", "II", "WO", "INFG", "WF01");
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.dmaaiNo").value(existingId.getDmaaiNo()))
				.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
				.andExpect(jsonPath("$.documentType").value(existingId.getDocumentType()))
				.andExpect(jsonPath("$.documentOrderType").value(existingId.getDocumentOrderType()))
				.andExpect(jsonPath("$.glClass").value(existingId.getGlClass()))
				.andExpect(jsonPath("$.manufacturingCostType").value(existingId.getManufacturingCostType()))
				.andExpect(jsonPath("$.businessUnitId").value(""))
				.andExpect(jsonPath("$.objectAccountCode").value("1420"))
				.andExpect(jsonPath("$.subsidiaryCode").value(""))
				.andExpect(jsonPath("$.description1").value("Persediaan W.I.P"))
				.andExpect(jsonPath("$.description2").value("Inventory Completion"))
				.andExpect(jsonPath("$.description3").value(""))
				.andExpect(jsonPath("$.description4").value(""))
				.andReturn().getResponse().getContentAsString();
		
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(put(baseUrl + newId)
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

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(patch(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Test @Rollback
	public void createOrEditManyToAssociation() throws Exception {
		//get existing entity data before post request
		String beforeUpdate = mockMvc.perform(get(baseUrl + existingId))
				.andReturn().getResponse().getContentAsString();
		
		//get url for post request
		String headerUrl = new DMAAIHeaderTest().baseUrl();
		String headerResp = mockMvc.perform(get(headerUrl + existingId().getDmaaiNo()))
								.andReturn().getResponse().getContentAsString();
		String linkUrl = JsonPath.read(headerResp, "$._links.details.href");
		
		//construct an additional request object for post request
		Map<String, Object> requestObject2 = new HashMap<>(requestObject);
		requestObject2.put("dmaaiNo", existingId().getDmaaiNo() + 1);
		requestObject2.put("companyId", existingId().getCompanyId());
		requestObject2.put("documentType", existingId().getDocumentType());
		requestObject2.put("documentOrderType", existingId().getDocumentOrderType());
		requestObject2.put("glClass", existingId().getGlClass());
		requestObject2.put("manufacturingCostType", existingId().getManufacturingCostType());
		
		List<Map<String, Object>> list = Arrays.asList(requestObject, requestObject2);
		
		//post request
		mockMvc.perform(post(linkUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(list)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		em.flush(); em.clear();
		
		//check content of new entity
		String newResp = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.dmaaiNo").value(newId.getDmaaiNo()))
				.andExpect(jsonPath("$.companyId").value(newId.getCompanyId()))
				.andExpect(jsonPath("$.documentType").value(newId.getDocumentType()))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.objectAccountCode").value(requestObject.get("objectAccountCode")))
				.andExpect(jsonPath("$.description2").value(requestObject.get("description2")))
				.andExpect(jsonPath("$.description3").value(""))
				.andReturn().getResponse().getContentAsString();
		assertCreationInfo(newResp);
		
		//check content of existing entity
		String existResp = mockMvc.perform(get(baseUrl + existingId))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.dmaaiNo").value(existingId.getDmaaiNo()))
				.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
				.andExpect(jsonPath("$.documentType").value(existingId.getDocumentType()))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject2.get("businessUnitId")))
				.andExpect(jsonPath("$.objectAccountCode").value(requestObject2.get("objectAccountCode")))
				.andExpect(jsonPath("$.description2").value(requestObject2.get("description2")))
				.andExpect(jsonPath("$.description3").value((String) JsonPath.read(beforeUpdate, "$.description3")))
				.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(existResp, beforeUpdate);
	}
}
