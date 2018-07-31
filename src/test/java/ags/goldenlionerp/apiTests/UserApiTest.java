package ags.goldenlionerp.apiTests;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

public class UserApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
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
	String baseUrl() {
		return "/api/users/";
	}

	@Override
	String existingId() {
		return "QSA";
	}

	@Override
	String newId() {
		return "SALMON";
	}
	
	String existingPassword() {
		return "";
	}
	
	String newPassword() {
		return "password";
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.users").exists());

	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
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
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

		assertTrue(testCanLogin(existingId, newPassword()));
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		requestObject.put("userSecurityType", "G");
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.userSecurityId").value(existingId))
				//.andExpect(jsonPath("$.userSecurityCode").value((String) JsonPath.read(beforePatch, "$.userSecurityCode")))	//password shouldn't change with this
				.andExpect(jsonPath("$.userSecurityLevel").value(requestObject.getOrDefault("userSecurityLevel", (Integer) JsonPath.read(beforePatch, "$.userSecurityLevel"))))
				.andExpect(jsonPath("$.userSecurityGroupId").value(requestObject.getOrDefault("userSecurityGroupId", (String) JsonPath.read(beforePatch, "$.userSecurityGroupId"))))
				.andExpect(jsonPath("$.userSecurityType").value("U"))
				.andExpect(jsonPath("$.addressNumber").value(requestObject.getOrDefault("addressNumber", (String) JsonPath.read(beforePatch, "$.addressNumber"))))
				.andExpect(jsonPath("$.menuToExecutePOS").value(requestObject.getOrDefault("menuToExecutePOS", (String) JsonPath.read(beforePatch, "$.menuToExecutePOS"))))
				.andExpect(jsonPath("$.menuToExecuteBOS").value(requestObject.getOrDefault("menuToExecuteBOS", (String) JsonPath.read(beforePatch, "$.menuToExecuteBOS"))))
				.andExpect(jsonPath("$.menuToExecuteHOS").value(requestObject.getOrDefault("menuToExecuteHOS", (String) JsonPath.read(beforePatch, "$.menuToExecuteHOS"))))
				.andExpect(jsonPath("$.tagId").value(requestObject.getOrDefault("tagId", (String) JsonPath.read(beforePatch, "$.tagId"))))
				.andExpect(jsonPath("$.approvalRouteCode").value(requestObject.getOrDefault("approvalRouteCode", (String) JsonPath.read(beforePatch, "$.approvalRouteCode"))))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId",  (String) JsonPath.read(beforePatch, "$.businessUnitId"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);

		//assert password isn't changed
		assertTrue(testCanLogin(existingId, existingPassword()));
		assertFalse(testCanLogin(existingId, newPassword()));
	}
	
	@Test @Rollback
	public void updateTestWithPatch_HaveUserGroup() throws Exception {
		existingId = "SALES";
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		String groupLink = JsonPath.read(beforePatch, "$._links.userSecurityGroup.href");
		MockHttpServletResponse groupResponse = mockMvc.perform(get(groupLink))
							.andReturn().getResponse();
		assumeTrue(groupResponse.getStatus() == 200);
		
		String groupInfo = groupResponse.getContentAsString();
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.userSecurityId").value(existingId))
				//.andExpect(jsonPath("$.userSecurityCode").value((String) JsonPath.read(beforePatch, "$.userSecurityCode")))	//password shouldn't change with this
				.andExpect(jsonPath("$.userSecurityLevel").value((Integer) JsonPath.read(groupInfo, "$.userSecurityLevel")))
				.andExpect(jsonPath("$.userSecurityType").value("U"))
				.andExpect(jsonPath("$.addressNumber").value(requestObject.getOrDefault("addressNumber", (String) JsonPath.read(beforePatch, "$.addressNumber"))))
				.andExpect(jsonPath("$.menuToExecutePOS").value((String) JsonPath.read(groupInfo, "$.menuToExecutePOS")))
				.andExpect(jsonPath("$.menuToExecuteBOS").value((String) JsonPath.read(groupInfo, "$.menuToExecuteBOS")))
				.andExpect(jsonPath("$.menuToExecuteHOS").value((String) JsonPath.read(groupInfo, "$.menuToExecuteHOS")))
				.andExpect(jsonPath("$.tagId").value(requestObject.getOrDefault("tagId", (String) JsonPath.read(beforePatch, "$.tagId"))))
				.andExpect(jsonPath("$.approvalRouteCode").value(requestObject.getOrDefault("approvalRouteCode", (String) JsonPath.read(beforePatch, "$.approvalRouteCode"))))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId",  (String) JsonPath.read(beforePatch, "$.businessUnitId"))))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);

		//assert password isn't changed
		assertTrue(testCanLogin(existingId, existingPassword()));
		assertFalse(testCanLogin(existingId, newPassword()));
	}
	
	@Test @Rollback
	public void updatePasswordTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		mockMvc.perform(patch(baseUrl + existingId + "/password")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		em.flush();	em.clear();
		
		mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.userSecurityCode").value(requestObject.get("userSecurityCode")));
		
		assertTrue(testCanLogin(existingId, newPassword()));
	}

	@Test @Rollback
	public void updatePasswordTestFail() throws Exception {
		assumeExists(baseUrl + existingId);
		
		requestObject.put("userSecurityCode", "invalidPassword");
		
		mockMvc.perform(patch(baseUrl + existingId + "/password")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test @Rollback
	public void createTestWithPost_NoPasswordConfirm() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		requestObject.remove("userSecurityCodeRe");
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	private boolean testCanLogin(String userId, String password) {
		//TODO test login
		return true;
	}
}
