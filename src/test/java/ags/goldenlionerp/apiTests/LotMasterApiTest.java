package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.lotmaster.LotMasterPK;
import ags.goldenlionerp.util.WebIdUtil;

public class LotMasterApiTest extends ApiTestBase<LotMasterPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("businessUnitId", newId().getBusinessUnitId());
		map.put("itemCode", newId.getItemCode());
		map.put("serialLotNo", newId.getSerialLotNo());
		map.put("lotDescription", "Test New Lot");
		map.put("lotStatusCode", "A");
		map.put("expiredDate", LocalDateTime.of(2019, Month.FEBRUARY, 14, 13, 5, 0));
		map.put("lotEffectiveDate", LocalDateTime.of(2018, Month.JULY, 15, 13, 5, 0));
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/lots/";
	}

	@Override
	LotMasterPK existingId() {
		return new LotMasterPK("123", "TEST.TEST", "test");
	}

	@Override
	LotMasterPK newId() {
		return new LotMasterPK("100", "ACC.CD-CDBLANK", "testNew");
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
				.andExpect(jsonPath("$.lotDescription").value("Test lot"))
				.andExpect(jsonPath("$.lotStatusCode").value("H"))
				.andExpect(jsonPath("$.serialNumber").value("test"))
				.andExpect(jsonPath("$.expiredDate").value(LocalDateTime.of(2019, Month.JUNE, 29, 0, 0, 0).format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.lotEffectiveDate").value(LocalDateTime.of(2018, Month.JUNE, 26, 0, 0).format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.closedDate").value(LocalDateTime.of(2019, Month.JUNE, 30, 0, 0).format(DateTimeFormatter.ISO_DATE_TIME)));
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

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
		
		String getRes = mockMvc.perform(get(baseUrl + WebIdUtil.toWebId(newId.toString())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.itemCode").value(newId.getItemCode()))
				.andExpect(jsonPath("$.businessUnitId").value(newId.getBusinessUnitId()))
				.andExpect(jsonPath("$.serialLotNo").value(newId.getSerialLotNo()))
				.andExpect(jsonPath("$.lotDescription").value(requestObject.get("lotDescription")))
				.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
				.andExpect(jsonPath("$.expiredDate").value(((LocalDateTime)requestObject.get("expiredDate")).format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.lotEffectiveDate").value(((LocalDateTime)requestObject.get("lotEffectiveDate")).format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.closedDate").value(Matchers.nullValue()))
				.andExpect(jsonPath("$.memoLot1").value(""))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

	}

	@Override @Test @Rollback
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

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("lotEffectiveDate");
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
				.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
				.andExpect(jsonPath("$.serialLotNo").value(existingId.getSerialLotNo()))
				.andExpect(jsonPath("$.lotDescription").value(requestObject.get("lotDescription")))
				.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
				.andExpect(jsonPath("$.expiredDate").value(((LocalDateTime)requestObject.get("expiredDate")).format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.lotEffectiveDate").value((String)JsonPath.read(beforePatch, "$.lotEffectiveDate")))
				.andExpect(jsonPath("$.closedDate").value((String)JsonPath.read(beforePatch, "$.closedDate")))
				.andExpect(jsonPath("$.memoLot1").value((String)JsonPath.read(beforePatch, "$.memoLot1")))
							.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);

	}

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	
}
