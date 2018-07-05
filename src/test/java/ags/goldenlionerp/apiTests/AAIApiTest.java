package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.system.aai.AutomaticAccountingInstructionPK;

public class AAIApiTest extends ApiTestBase<AutomaticAccountingInstructionPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("sequence", newId().getSequence());
		map.put("aaiCode", newId().getAaiCode());
		map.put("description1", "Test description");
		map.put("description2", "Test description 2");
		map.put("companyId", "11000");
		map.put("businessUnitId", "100");
		map.put("objectAccountCode", "111110");
		map.put("subsidiaryCode", "");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/aai/";
	}

	@Override
	AutomaticAccountingInstructionPK existingId() {
		return new AutomaticAccountingInstructionPK(11500, "GLA200");
	}

	@Override
	AutomaticAccountingInstructionPK newId() {
		return new AutomaticAccountingInstructionPK(12345, "TESTTEST");
	}

	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.aaiCode").value(existingId.getAaiCode()))
				.andExpect(jsonPath("$.companyId").value("00000"))
				.andExpect(jsonPath("$.businessUnitId").value(""))
				.andExpect(jsonPath("$.objectAccountCode").value("120000"))
				.andExpect(jsonPath("$.subsidiaryCode").value(""))
				.andExpect(jsonPath("$.description1").value("Aktiva Tidak Lancar"))
				.andExpect(jsonPath("$.description2").value("Aktiva"))
				.andExpect(jsonPath("$.description3").value(""));
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.automaticAccountingInstructions").exists());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {	
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
				.andExpect(jsonPath("$.aaiCode").value(newId.getAaiCode()))
				.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", "")))
				.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", "")))
				.andExpect(jsonPath("$.description3").value(requestObject.getOrDefault("description3", "")))
				.andExpect(jsonPath("$.companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.objectAccountCode").value(requestObject.get("objectAccountCode")))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);

		String accountLink = JsonPath.read(getRes, "$._links.accountMaster.href");
		mockMvc.perform(get(accountLink))
				.andExpect(jsonPath("companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("objectAccountCode").value(requestObject.get("objectAccountCode")))
				.andExpect(jsonPath("subsidiaryCode").value(requestObject.get("subsidiaryCode")));
	}

	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(put(baseUrl + newId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		mockMvc.perform(delete(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	
		mockMvc.perform(get(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("description1");
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
				.andExpect(jsonPath("$.aaiCode").value(existingId.getAaiCode()))
				.andExpect(jsonPath("$.description1").value(requestObject.getOrDefault("description1", (String) JsonPath.read(beforePatch, "$.description1"))))
				.andExpect(jsonPath("$.description2").value(requestObject.getOrDefault("description2", (String) JsonPath.read(beforePatch, "$.description2"))))
				.andExpect(jsonPath("$.description3").value(requestObject.getOrDefault("description3", (String) JsonPath.read(beforePatch, "$.description3"))))
				.andExpect(jsonPath("$.companyId").value(requestObject.getOrDefault("companyId", (String) JsonPath.read(beforePatch, "$.companyId"))))
				.andExpect(jsonPath("$.objectAccountCode").value(requestObject.getOrDefault("objectAccountCode", (String) JsonPath.read(beforePatch, "$.objectAccountCode"))))
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
