package ags.goldenlionerp.apiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import com.jayway.jsonpath.JsonPath;


@Transactional
public class BusinessUnitApiTest extends ApiTestBase/*implements ApiTest*/ {

	@Override
	Map<String, String> populateRequestObject() {
		Map<String, String> map = new HashMap<>();
		map.put("businessUnitId", newId);
		map.put("description", "TESTTEST");
		map.put("businessUnitType", "BS");
		map.put("idNumber", "");
		map.put("company", "/api/companies/00000");
		map.put("relatedBusinessUnit", "/api/businessUnits/110");
		map.put("modelOrConsolidated", "");
		map.put("computerId", "YOOO");
		map.put("inputUserId", "abcdefghijklmnopqrstuvwxyz");
		return map;
	}
	@Override
	String baseUrl() {
		return "/api/businessUnits/";
	}
	@Override
	String existingId() {
		return "100";
	}
	@Override
	String newId() {
		return "125";
	}
	
	@Override
	@Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
			//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.businessUnitId").value(existingId))
			.andExpect(jsonPath("$.description").value("AMTEK"));

	}

	@Override
	@Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..businessUnits.length()").value(5))
				.andExpect(jsonPath("$..businessUnits[0].businessUnitId").value("100"))
				.andExpect(jsonPath("$..businessUnits[0].description").value("AMTEK"));

	}

	@Override
	@Test
	@Rollback
	public void createTestWithPost() throws Exception {
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String getResult = mockMvc.perform(get(baseUrl + newId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.businessUnitId").value(newId))
				.andExpect(jsonPath("$.inputUserId").value("login not yet"))
				.andExpect(jsonPath("$.inputDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.relatedPreview.businessUnitId").value("110"))
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(
				(String) JsonPath.read(getResult, "$.lastUpdateDateTime"),
				(String) JsonPath.read(getResult, "$.inputDateTime")
		);
		
		String companyUrl = JsonPath.read(getResult, "$._links.company.href");
		mockMvc.perform(get(companyUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("company"))));
		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("relatedBusinessUnit"))));
			
	}

	@Test
	@Rollback
	//@Commit
	public void createTestWithPostWithoutRelatedBU() throws Exception {
		requestObject.remove("relatedBusinessUnit");

		mockMvc.perform(post(baseUrl)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		String getResult = mockMvc.perform(get(baseUrl + newId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				//.andDo(res -> System.out.println(res.getResponse().getErrorMessage()))
				.andReturn().getResponse().getContentAsString();

		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
			
	}
	
	@Override
	@Test
	@Rollback
	public void createTestWithPut() throws Exception {
		requestObject.remove("businessUnitId");
		mockMvc.perform(put(baseUrl + newId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}
	
	@Test
	@Rollback
	public void createTestAlreadyExist() throws Exception{
		requestObject.put("businessUnitId", existingId);
		
		mockMvc.perform(post(baseUrl)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)));
				//.andExpect(MockMvcResultMatchers.status().isConflict());
		EntityManager em = wac.getBean(EntityManager.class);
		em.flush();
	}

	@Override
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(baseUrl + existingId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		mockMvc.perform(get(baseUrl + existingId))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Override
	@Test
	@Rollback
	public void updateTestWithPatch() throws Exception {
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
				
		requestObject.remove("businessUnitType");
		requestObject.remove("computerId");
		
		mockMvc.perform(patch(baseUrl + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		EntityManager manager = wac.getBean(EntityManager.class);
		manager.flush();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.businessUnitId").value(existingId))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.businessUnitType").value((String) JsonPath.read(beforePatch, "$.businessUnitType")))
				.andExpect(jsonPath("$.computerId").value((String) JsonPath.read(beforePatch, "$.computerId")))
				.andExpect(jsonPath("$.inputDateTime").value((String) JsonPath.read(beforePatch, "$.inputDateTime")))
				.andExpect(jsonPath("$.inputUserId").value((String) JsonPath.read(beforePatch, "$.inputUserId")))
				.andReturn().getResponse().getContentAsString();
		
		assertNotEquals(
				JsonPath.read(getResult, "$.lastUpdateDateTime"),
				JsonPath.read(getResult, "$.inputDateTime")
		);
		
		String companyUrl = JsonPath.read(getResult, "$._links.company.href");
		mockMvc.perform(get(companyUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("company"))));
		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("relatedBusinessUnit"))));
			
	}
	
	@Test
	@Rollback
	public void updateTestWithPatchRemoveRelatedBusinessUnit() throws Exception {
		
		String id = "100";
		requestObject.put("relatedBusinessUnit", null);
		
		//check beforehand that the entity has a non-null relatedBusinessUnit association
		String beforePatch = mockMvc.perform(get(baseUrl + id).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		String beforeRelated = JsonPath.read(beforePatch, "$._links.related.href");
		mockMvc.perform(get(beforeRelated))
				.andDo(res -> assumeTrue(res.getResponse().getStatus() == 200));
		
		//do a PATCH to remove the association
		mockMvc.perform(patch(baseUrl + id)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		EntityManager manager = wac.getBean(EntityManager.class);
		manager.flush();
		
		//do a GET to check the result
		String getResult = mockMvc.perform(get(baseUrl + id).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Override
	@Test
	@Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	

}
