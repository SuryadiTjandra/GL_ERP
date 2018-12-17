package ags.goldenlionerp.apiTests.system;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.util.WebIdUtil;

public class UserGroupApiTest extends ApiTestBase<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("userSecurityId", newId());
		map.put("userSecurityCode", newPassword());
		map.put("userSecurityCodeRe", newPassword());
		map.put("userSecurityLevel", 5);
		map.put("userSecurityType", "G");
		map.put("userSecurityGroupId", "ADM AR");
		map.put("businessUnitId", "110");
		map.put("menuToExecuteBOS", "MH10");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/userGroups/";
	}

	@Override
	protected String existingId() {
		return "ADMIN_PENJUALAN";
	}

	@Override
	protected String newId() {
		return "SALMONGROUP";
	}
	
	String existingPassword() {
		return "";
	}
	
	String newPassword() {
		return "password";
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.userSecurityId").value(WebIdUtil.toEntityId(existingId)))
		//.andExpect(jsonPath("$.userSecurityCode").value()
		.andExpect(jsonPath("$.userSecurityLevel").value(4))
		.andExpect(jsonPath("$.userSecurityGroupId").value(""))
		.andExpect(jsonPath("$.userSecurityType").value("G"))
		.andExpect(jsonPath("$.addressNumber").value(""))
		.andExpect(jsonPath("$.menuToExecutePOS").value(""))
		.andExpect(jsonPath("$.menuToExecuteBOS").value("MH00"))
		.andExpect(jsonPath("$.menuToExecuteHOS").value(""))
		.andExpect(jsonPath("$.tagId").value(""))
		.andExpect(jsonPath("$.approvalRouteCode").value(""))
		.andExpect(jsonPath("$.businessUnitId").value("110"));
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.userGroups").exists());
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.userSecurityId").value(newId))
		//.andExpect(jsonPath("$.userSecurityCode").value()
		.andExpect(jsonPath("$.userSecurityLevel").value(requestObject.get("userSecurityLevel")))
		.andExpect(jsonPath("$.userSecurityGroupId").value(""))
		.andExpect(jsonPath("$.userSecurityType").value("G"))
		.andExpect(jsonPath("$.addressNumber").value(requestObject.getOrDefault("addressNumber", "")))
		.andExpect(jsonPath("$.menuToExecutePOS").value(requestObject.getOrDefault("menuToExecutePOS", "")))
		.andExpect(jsonPath("$.menuToExecuteBOS").value(requestObject.getOrDefault("menuToExecuteBOS", "")))
		.andExpect(jsonPath("$.menuToExecuteHOS").value(requestObject.getOrDefault("menuToExecuteHOS", "")))
		.andExpect(jsonPath("$.tagId").value(requestObject.getOrDefault("tagId", "")))
		.andExpect(jsonPath("$.approvalRouteCode").value(requestObject.getOrDefault("approvalRouteCode", "")))
		.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId", "")))
		;
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.userSecurityId").value(WebIdUtil.toEntityId(existingId)))
		//.andExpect(jsonPath("$.userSecurityCode").value((String) JsonPath.read(beforeUpdateJson, "$.userSecurityCode")))	//password shouldn't change with this
		.andExpect(jsonPath("$.userSecurityLevel").value(requestObject.getOrDefault("userSecurityLevel", (Integer) JsonPath.read(beforeUpdateJson, "$.userSecurityLevel"))))
		.andExpect(jsonPath("$.userSecurityGroupId").value(""))
		.andExpect(jsonPath("$.userSecurityType").value("G"))
		.andExpect(jsonPath("$.addressNumber").value(requestObject.getOrDefault("addressNumber", (String) JsonPath.read(beforeUpdateJson, "$.addressNumber"))))
		.andExpect(jsonPath("$.menuToExecutePOS").value(requestObject.getOrDefault("menuToExecutePOS", (String) JsonPath.read(beforeUpdateJson, "$.menuToExecutePOS"))))
		.andExpect(jsonPath("$.menuToExecuteBOS").value(requestObject.getOrDefault("menuToExecuteBOS", (String) JsonPath.read(beforeUpdateJson, "$.menuToExecuteBOS"))))
		.andExpect(jsonPath("$.menuToExecuteHOS").value(requestObject.getOrDefault("menuToExecuteHOS", (String) JsonPath.read(beforeUpdateJson, "$.menuToExecuteHOS"))))
		.andExpect(jsonPath("$.tagId").value(requestObject.getOrDefault("tagId", (String) JsonPath.read(beforeUpdateJson, "$.tagId"))))
		.andExpect(jsonPath("$.approvalRouteCode").value(requestObject.getOrDefault("approvalRouteCode", (String) JsonPath.read(beforeUpdateJson, "$.approvalRouteCode"))))
		.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId",  (String) JsonPath.read(beforeUpdateJson, "$.businessUnitId"))))
		;
		
	}

}
