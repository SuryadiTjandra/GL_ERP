package ags.goldenlionerp.apiTests.finance;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseNew;

public class AccountMasterApiTest extends ApiTestBaseNew<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", "00000");
		map.put("businessUnitId", "123");
		map.put("objectAccountCode", "100000");
		map.put("subsidiaryCode", "123");
		map.put("description", "Test account");
		map.put("levelOfDetail", "3");
		map.put("currencyCodeTransaction", "IDR");
		map.put("category01", "GLA200");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/accountMasters/";
	}

	@Override
	protected String existingId() {
		return "11000.100.100000.";
	}

	@Override
	protected String newId() {
		return "00000.123.100000.123";
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("description");
		
		super.updateTestWithPatch();
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.accountId").value("100.100000"))
		.andExpect(jsonPath("$.companyId").value("11000"))
		.andExpect(jsonPath("$.businessUnitId").value("100"))
		.andExpect(jsonPath("$.objectAccountCode").value("100000"))
		.andExpect(jsonPath("$.subsidiaryCode").value(""))
		.andExpect(jsonPath("$.description").value("AKTIVA"))
		.andExpect(jsonPath("$.levelOfDetail").value(3))
		.andExpect(jsonPath("$.postingEditCode").value(false))
		.andExpect(jsonPath("$.modelAccount").value(""))
		.andExpect(jsonPath("$.category01").value("GLA100"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.accountId").value("123.100000.123"))
		.andExpect(jsonPath("$.companyId").value(requestObject.get("companyId")))
		.andExpect(jsonPath("$.subsidiaryCode").value(requestObject.get("subsidiaryCode")))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.levelOfDetail").value(requestObject.get("levelOfDetail")))
		.andExpect(jsonPath("$.currencyCodeTransaction").value(requestObject.get("currencyCodeTransaction")))
		.andExpect(jsonPath("$.category01").value(requestObject.get("category01")))
		;
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.accountId").value("100.100000"))
		.andExpect(jsonPath("$.companyId").value((String) JsonPath.read(beforeUpdateJson, "$.companyId")))
		.andExpect(jsonPath("$.businessUnitId").value((String) JsonPath.read(beforeUpdateJson, "$.businessUnitId")))
		.andExpect(jsonPath("$.objectAccountCode").value((String) JsonPath.read(beforeUpdateJson, "$.objectAccountCode")))
		.andExpect(jsonPath("$.subsidiaryCode").value((String) JsonPath.read(beforeUpdateJson, "$.subsidiaryCode")))
		.andExpect(jsonPath("$.description").value((String) JsonPath.read(beforeUpdateJson, "$.description")))
		.andExpect(jsonPath("$.levelOfDetail").value(requestObject.get("levelOfDetail")))
		.andExpect(jsonPath("$.category01").value(requestObject.get("category01")))
		;
		
	}

}
