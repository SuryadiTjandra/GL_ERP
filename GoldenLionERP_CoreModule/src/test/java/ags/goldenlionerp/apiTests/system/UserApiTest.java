package ags.goldenlionerp.apiTests.system;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.util.UpdateResultAssertion;

public class UserApiTest extends ApiTestBase<String> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("userSecurityId", newId());
		map.put("userSecurityCode", newPassword());
		map.put("userSecurityCodeRe", newPassword());
		map.put("userSecurityLevel", 5);
		map.put("userSecurityType", "U");
		map.put("businessUnitId", "110");
		map.put("menuToExecuteBOS", "MH10");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/users/";
	}

	@Override
	protected String existingId() {
		return "QSA";
	}

	@Override
	protected String newId() {
		return "SALMON";
	}
	
	protected String existingPassword() {
		return "";
	}
	
	protected String newPassword() {
		return "password";
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		super.createTestWithPost();
		assertTrue(testCanLogin(newId, newPassword()));
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.put("userSecurityType", "G");
		super.updateTestWithPatch();

		//assert password isn't changed
		assertTrue(testCanLogin(existingId, existingPassword()));
		assertFalse(testCanLogin(existingId, newPassword()));
	}
	
	@Test @Rollback
	public void updateTestWithPatch_HaveUserGroup() throws Exception {
		existingId = "SALES";
		
		String beforePatch = performer.performGet(baseUrl + existingId)
								.andReturn().getResponse().getContentAsString();
		String groupLink = JsonPath.read(beforePatch, "$._links.userSecurityGroup.href");
		MockHttpServletResponse groupResponse = performer.performGet(groupLink)
							.andReturn().getResponse();
		assumeTrue(groupResponse.getStatus() == 200);
		
		String groupInfo = groupResponse.getContentAsString();
		
		UpdateResultAssertion assertMethod   = (action, before) -> {
			action
				.andExpect(jsonPath("$.userSecurityId").value(existingId))
				//.andExpect(jsonPath("$.userSecurityCode").value((String) JsonPath.read(beforePatch, "$.userSecurityCode")))	//password shouldn't change with this
				.andExpect(jsonPath("$.userSecurityLevel").value((Integer) JsonPath.read(groupInfo, "$.userSecurityLevel")))
				.andExpect(jsonPath("$.userSecurityType").value("U"))
				.andExpect(jsonPath("$.addressNumber").value(requestObject.getOrDefault("addressNumber", (String) JsonPath.read(before, "$.addressNumber"))))
				.andExpect(jsonPath("$.menuToExecutePOS").value((String) JsonPath.read(groupInfo, "$.menuToExecutePOS")))
				.andExpect(jsonPath("$.menuToExecuteBOS").value((String) JsonPath.read(groupInfo, "$.menuToExecuteBOS")))
				.andExpect(jsonPath("$.menuToExecuteHOS").value((String) JsonPath.read(groupInfo, "$.menuToExecuteHOS")))
				.andExpect(jsonPath("$.tagId").value(requestObject.getOrDefault("tagId", (String) JsonPath.read(before, "$.tagId"))))
				.andExpect(jsonPath("$.approvalRouteCode").value(requestObject.getOrDefault("approvalRouteCode", (String) JsonPath.read(before, "$.approvalRouteCode"))))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId",  (String) JsonPath.read(before, "$.businessUnitId"))))
				;
		};
		
		super.updateTestWithPatch(assertMethod);

		//assert password isn't changed
		assertTrue(testCanLogin(existingId, existingPassword()));
		assertFalse(testCanLogin(existingId, newPassword()));
	}
	
	@Test @Rollback
	public void updatePasswordTest() throws Exception {
		assumeTrue(testCanLogin(existingId, existingPassword()));
		assumeExists(baseUrl + existingId);
		
		performer.performPatch(baseUrl + existingId + "/password", requestObject)
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		refreshData();
		
		assertTrue(testCanLogin(existingId, newPassword()));
	}

	@Test @Rollback
	public void updatePasswordTestFail() throws Exception {
		assumeExists(baseUrl + existingId);
		
		requestObject.put("userSecurityCode", "invalidPassword");
		
		performer.performPatch(baseUrl + existingId + "/password", requestObject)
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
		assertTrue(testCanLogin(existingId, existingPassword()));
		assertFalse(testCanLogin(existingId, (String) requestObject.get("userSecurityCode")));
	}
	
	@Test @Rollback
	public void createTestWithPost_NoPasswordConfirm() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		requestObject.remove("userSecurityCodeRe");
		
		performer.performPost(baseUrl, requestObject)
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	private boolean testCanLogin(String userId, String password) throws Exception {
		MvcResult loginRes = mockMvc.perform(formLogin().user(userId).password(password))
				.andDo(print())
				.andReturn();
		
		return !loginRes.getResponse().getRedirectedUrl().endsWith("error");
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.userSecurityId").value(existingId))
		//.andExpect(jsonPath("$.userSecurityCode").value()
		.andExpect(jsonPath("$.userSecurityLevel").value(9))
		.andExpect(jsonPath("$.userSecurityGroupId").value(""))
		.andExpect(jsonPath("$.userSecurityType").value("U"))
		.andExpect(jsonPath("$.addressNumber").value("0000"))
		.andExpect(jsonPath("$.menuToExecutePOS").value("PX00"))
		.andExpect(jsonPath("$.menuToExecuteBOS").value("MH00"))
		.andExpect(jsonPath("$.menuToExecuteHOS").value("MX00"))
		.andExpect(jsonPath("$.tagId").value(""))
		.andExpect(jsonPath("$.approvalRouteCode").value("APR_PO"))
		.andExpect(jsonPath("$.businessUnitId").value("110"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.users").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.userSecurityId").value(newId))
		//.andExpect(jsonPath("$.userSecurityCode").value()
		.andExpect(jsonPath("$.userSecurityLevel").value(requestObject.get("userSecurityLevel")))
		.andExpect(jsonPath("$.userSecurityGroupId").value(requestObject.getOrDefault("userSecurityGroupId", "")))
		.andExpect(jsonPath("$.userSecurityType").value("U"))
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
		.andExpect(jsonPath("$.userSecurityId").value(existingId))
		//.andExpect(jsonPath("$.userSecurityCode").value((String) JsonPath.read(beforeUpdateJson, "$.userSecurityCode")))	//password shouldn't change with this
		.andExpect(jsonPath("$.userSecurityLevel").value(requestObject.getOrDefault("userSecurityLevel", (Integer) JsonPath.read(beforeUpdateJson, "$.userSecurityLevel"))))
		.andExpect(jsonPath("$.userSecurityGroupId").value(requestObject.getOrDefault("userSecurityGroupId", (String) JsonPath.read(beforeUpdateJson, "$.userSecurityGroupId"))))
		.andExpect(jsonPath("$.userSecurityType").value("U"))
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
