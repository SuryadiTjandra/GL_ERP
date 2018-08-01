package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.WebIdUtil;

public class UserGroupApiTest extends ApiTestBase<String> {

	@Override
	Map<String, Object> requestObject() throws Exception {
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
	String baseUrl() {
		return "/api/userGroups/";
	}

	@Override
	String existingId() {
		return "ADMIN_PENJUALAN";
	}

	@Override
	String newId() {
		return "SALMONGROUP";
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

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$._embedded.userGroups").exists());
		
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
				.andExpect(jsonPath("$.userSecurityGroupId").value(""))
				.andExpect(jsonPath("$.userSecurityType").value("G"))
				.andExpect(jsonPath("$.addressNumber").value(requestObject.getOrDefault("addressNumber", "")))
				.andExpect(jsonPath("$.menuToExecutePOS").value(requestObject.getOrDefault("menuToExecutePOS", "")))
				.andExpect(jsonPath("$.menuToExecuteBOS").value(requestObject.getOrDefault("menuToExecuteBOS", "")))
				.andExpect(jsonPath("$.menuToExecuteHOS").value(requestObject.getOrDefault("menuToExecuteHOS", "")))
				.andExpect(jsonPath("$.tagId").value(requestObject.getOrDefault("tagId", "")))
				.andExpect(jsonPath("$.approvalRouteCode").value(requestObject.getOrDefault("approvalRouteCode", "")))
				.andExpect(jsonPath("$.businessUnitId").value(requestObject.getOrDefault("businessUnitId", "")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

		//assertTrue(testCanLogin(existingId, newPassword()));
		
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		requestObject.put("userSecurityType", "U");
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.userSecurityId").value(WebIdUtil.toEntityId(existingId)))
				//.andExpect(jsonPath("$.userSecurityCode").value((String) JsonPath.read(beforePatch, "$.userSecurityCode")))	//password shouldn't change with this
				.andExpect(jsonPath("$.userSecurityLevel").value(requestObject.getOrDefault("userSecurityLevel", (Integer) JsonPath.read(beforePatch, "$.userSecurityLevel"))))
				.andExpect(jsonPath("$.userSecurityGroupId").value(""))
				.andExpect(jsonPath("$.userSecurityType").value("G"))
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
		//assertTrue(testCanLogin(existingId, existingPassword()));
		//assertFalse(testCanLogin(existingId, newPassword()));
		
	}

}
