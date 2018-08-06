package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.masterdata.lotmaster.LotMasterPK;

public class LotMasterApiTest extends ApiTestBase<LotMasterPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
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
	protected String baseUrl() {
		return "/api/lots/";
	}

	@Override
	protected LotMasterPK existingId() {
		return new LotMasterPK("123", "TEST.TEST", "test");
	}

	@Override
	protected LotMasterPK newId() {
		return new LotMasterPK("100", "ACC.CD-CDBLANK", "testNew");
	}


	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("lotEffectiveDate");
		
		super.updateTestWithPatch();
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
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

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.itemCode").value(newId.getItemCode()))
		.andExpect(jsonPath("$.businessUnitId").value(newId.getBusinessUnitId()))
		.andExpect(jsonPath("$.serialLotNo").value(newId.getSerialLotNo()))
		.andExpect(jsonPath("$.lotDescription").value(requestObject.get("lotDescription")))
		.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
		.andExpect(jsonPath("$.expiredDate").value(((LocalDateTime)requestObject.get("expiredDate")).format(DateTimeFormatter.ISO_DATE_TIME)))
		.andExpect(jsonPath("$.lotEffectiveDate").value(((LocalDateTime)requestObject.get("lotEffectiveDate")).format(DateTimeFormatter.ISO_DATE_TIME)))
		.andExpect(jsonPath("$.closedDate").value(Matchers.nullValue()))
		.andExpect(jsonPath("$.memoLot1").value(""));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.itemCode").value(existingId.getItemCode()))
		.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
		.andExpect(jsonPath("$.serialLotNo").value(existingId.getSerialLotNo()))
		.andExpect(jsonPath("$.lotDescription").value(requestObject.get("lotDescription")))
		.andExpect(jsonPath("$.lotStatusCode").value(requestObject.get("lotStatusCode")))
		.andExpect(jsonPath("$.expiredDate").value(((LocalDateTime)requestObject.get("expiredDate")).format(DateTimeFormatter.ISO_DATE_TIME)))
		.andExpect(jsonPath("$.lotEffectiveDate").value((String)JsonPath.read(beforeUpdateJson, "$.lotEffectiveDate")))
		.andExpect(jsonPath("$.closedDate").value((String)JsonPath.read(beforeUpdateJson, "$.closedDate")))
		.andExpect(jsonPath("$.memoLot1").value((String)JsonPath.read(beforeUpdateJson, "$.memoLot1")))
		;
		
	}

	
}
