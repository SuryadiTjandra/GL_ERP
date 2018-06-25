package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.itembranchinfo.ItemBranchInfo;
import ags.goldenlionerp.masterdata.itembranchinfo.ItemBranchInfoPK;
import ags.goldenlionerp.util.WebIdUtil;

public class ItemBranchInfoApiTest extends ApiTestBase<ItemBranchInfoPK> {


	@Override
	Map<String, Object> requestObject() throws Exception {
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
	String baseUrl() {
		return "/api/itembranches/";
	}

	@Override
	ItemBranchInfoPK existingId() {
		return new ItemBranchInfoPK("110", "ACC.BATERAI - CMOS\t\t\t");
	}

	@Override
	ItemBranchInfoPK newId() {
		return new ItemBranchInfoPK("123", "TEST.TEST");
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		String urlId = WebIdUtil.toWebId(existingId.toString());
		
		mockMvc.perform(get(baseUrl + urlId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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
	@Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.itembranches[1].branchCode").value("110"))
				.andExpect(jsonPath("$._embedded.itembranches.length()").value(20))
				.andExpect(jsonPath("$.page.totalElements").value(3008));

	}

	@SuppressWarnings("unchecked")
	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl + WebIdUtil.toWebId(newId.toString()));
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		Map<String, Object> mapPar = (Map<String, Object>) requestObject.get("parameters");

		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.itemCode").value(newId.getItemCode()))
				.andExpect(jsonPath("$.branchCode").value(newId.getBranchCode()))
				.andExpect(jsonPath("$.stockingType").value(requestObject.get("stockingType")))
				.andExpect(jsonPath("$.reorderQty").value(requestObject.get("reorderQty")))
				.andExpect(jsonPath("$.serialNumberRequired").value(requestObject.get("serialNumberRequired")))
				.andExpect(jsonPath("$.minimumReorderQty").value(0))
				.andExpect(jsonPath("$.parameters.profitMarginMinimumFactor").value(mapPar.get("profitMarginMinimumFactor")))
				.andExpect(jsonPath("$.dataGroups.brandCode").value(""))
				.andExpect(jsonPath("$.parameters.discountFactor").value(0))
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
		assumeExists(baseUrl + WebIdUtil.toWebId(existingId.toString()));
		
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
		
		requestObject.remove("stockingType");
		
		Map<String, Object> mapPar = (Map<String, Object>) requestObject.get("parameters");
		
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
							.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
							.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
							.andExpect(jsonPath("$.branchCode").value(existingId.getBranchCode()))
							.andExpect(jsonPath("$.stockingType").value((String) JsonPath.read(beforePatch, "$.stockingType")))
							.andExpect(jsonPath("$.reorderQty").value(requestObject.get("reorderQty")))
							.andExpect(jsonPath("$.serialNumberRequired").value(requestObject.get("serialNumberRequired")))
							.andExpect(jsonPath("$.minimumReorderQty").value((Double) JsonPath.read(beforePatch, "$.minimumReorderQty")))
							.andExpect(jsonPath("$.parameters.profitMarginMinimumFactor").value(mapPar.get("profitMarginMinimumFactor")))
							.andExpect(jsonPath("$.dataGroups.brandCode").value((String) JsonPath.read(beforePatch, "$.dataGroups.brandCode")))
							.andExpect(jsonPath("$.parameters.discountFactor").value((Double) JsonPath.read(beforePatch, "$.parameters.discountFactor")))
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
