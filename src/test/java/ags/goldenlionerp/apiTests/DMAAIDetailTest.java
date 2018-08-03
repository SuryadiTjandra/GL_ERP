package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.application.setups.dmaai.DMAAIDetailPK;

public class DMAAIDetailTest extends ApiTestBaseMassUpdate<DMAAIDetailPK> {


	@Override
	protected Map<String, Object> requestObject() throws Exception {
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
	protected String baseUrl() {
		return "/api/dmaaiDetails/";
	}

	@Override
	protected DMAAIDetailPK existingId() {
		return new DMAAIDetailPK(3120, "00000", "IC", "", "*****", "");
	}

	@Override
	protected DMAAIDetailPK newId() {
		return new DMAAIDetailPK(3120, "11000", "II", "WO", "INFG", "WF01");
	}
	
	@Override
	protected Map<String, Object> requestObject2() throws Exception {
		Map<String, Object> map = new HashMap<>(requestObject());
		map.put("dmaaiNo", existingId().getDmaaiNo() + 1);
		map.put("companyId", existingId().getCompanyId());
		map.put("documentType", existingId().getDocumentType());
		map.put("documentOrderType", existingId().getDocumentOrderType());
		map.put("glClass", existingId().getGlClass());
		map.put("manufacturingCostType", existingId().getManufacturingCostType());
		return map;
	}

	@Override
	protected String associationLinkUrl() throws Exception {
		String headerUrl = new DMAAIHeaderTest().baseUrl();
		String headerResp = performer.performGet(headerUrl + existingId().getDmaaiNo())
								.andReturn().getResponse().getContentAsString();
		String linkUrl = JsonPath.read(headerResp, "$._links.details.href");
		return linkUrl;
	}


	@Override @Test
	public void getTestCollection() throws Exception {
		performer.performGet(baseUrl)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl+existingId);
		
		performer.performPatch(baseUrl+existingId, requestObject)
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
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
			.andExpect(jsonPath("$.description4").value(""));
	}

	@Override
	public void assertMassUpdateNewResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.dmaaiNo").value(newId.getDmaaiNo()))
			.andExpect(jsonPath("$.companyId").value(newId.getCompanyId()))
			.andExpect(jsonPath("$.documentType").value(newId.getDocumentType()))
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.objectAccountCode").value(requestObject.get("objectAccountCode")))
			.andExpect(jsonPath("$.description2").value(requestObject.get("description2")))
			.andExpect(jsonPath("$.description3").value(""));	
	}

	@Override
	public void assertMassUpdateExistingResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.dmaaiNo").value(existingId.getDmaaiNo()))
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.documentType").value(existingId.getDocumentType()))
			.andExpect(jsonPath("$.businessUnitId").value(requestObject2.get("businessUnitId")))
			.andExpect(jsonPath("$.objectAccountCode").value(requestObject2.get("objectAccountCode")))
			.andExpect(jsonPath("$.description2").value(requestObject2.get("description2")))
			.andExpect(jsonPath("$.description3").value((String) JsonPath.read(beforeUpdateJson, "$.description3")))
			.andReturn().getResponse().getContentAsString();
		
	}
	
	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
	}	//do nothing		
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
	}	//do nothing	
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
	}	//do nothing	

}
