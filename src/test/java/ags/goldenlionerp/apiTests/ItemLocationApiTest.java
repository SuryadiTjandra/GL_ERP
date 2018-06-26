package ags.goldenlionerp.apiTests;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
		fail();
		
	}

	@Override@Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(put(baseUrl + existingId)
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
		fail();
		
	}

	@Override@Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	
}
