package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.ResultAssertion;


@Transactional
public class BusinessUnitApiTest extends ApiTestBaseNew<String> {

	@Override
	protected Map<String, Object> requestObject() {
		Map<String, Object> map = new HashMap<>();
		map.put("businessUnitId", newId);
		map.put("description", "TESTTEST");
		map.put("businessUnitType", "BS");
		map.put("idNumber", "");
		//map.put("company", "/api/companies/00000");
		//map.put("relatedBusinessUnit", "/api/businessUnits/110");
		map.put("companyId", "00000");
		map.put("relatedBusinessUnitId", "110");
		map.put("modelOrConsolidated", "");
		map.put("computerId", "YOOO");
		map.put("inputUserId", "abcdefghijklmnopqrstuvwxyz");
		return map;
	}
	@Override
	protected String baseUrl() {
		return "/api/businessUnits/";
	}
	@Override
	protected String existingId() {
		return "100";
	}
	@Override
	protected String newId() {
		return "125";
	}

	@Test
	@Rollback
	//@Commit
	public void createTestWithPostWithoutRelatedBU() throws Exception {
		requestObject.remove("relatedBusinessUnitId");

		ResultAssertion assertMethod = action -> {
			String getResult = action.andReturn().getResponse().getContentAsString();
			String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
			performer.performGet(relatedUrl)
					.andExpect(MockMvcResultMatchers.status().isNotFound());
		};
		
		super.createTestWithPost(assertMethod);
	}
	
	@Test
	@Rollback
	public void createTestAlreadyExist() throws Exception{
		requestObject.put("businessUnitId", existingId);
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(MockMvcResultMatchers.status().isConflict());
		EntityManager em = wac.getBean(EntityManager.class);
		em.flush();
	}
	
	@Test
	@Rollback
	public void updateTestWithPatchRemoveRelatedBusinessUnit() throws Exception {
		
		String id = "123";
		requestObject.put("relatedBusinessUnitId", "");
		
		//check beforehand that the entity has a non-null relatedBusinessUnit association
		String beforePatch = performer.performGet(baseUrl + id)
				.andReturn().getResponse().getContentAsString();
		String beforeRelated = JsonPath.read(beforePatch, "$._links.related.href");
		assumeExists(beforeRelated);
		
		//do a PATCH to remove the association
		performer.performPatch(baseUrl + id, requestObject)
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		refreshData();
		
		//do a GET to check the result
		String getResult = performer.performGet(baseUrl + id)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		performer.performGet(relatedUrl)
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.businessUnitId").value(existingId))
		.andExpect(jsonPath("$.description").value("AMTEK"));
		
	}
	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$..businessUnits.length()").value(5))
		.andExpect(jsonPath("$..businessUnits[0].businessUnitId").value("100"))
		.andExpect(jsonPath("$..businessUnits[0].description").value("AMTEK"));
		
	}
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.businessUnitId").value(newId))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.relatedPreview.businessUnitId").value("110"));
		
		String getResult = action.andReturn().getResponse().getContentAsString();
		String companyUrl = JsonPath.read(getResult, "$._links.company.href");
		performer.performGet(companyUrl)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith("/api/companies/00000")));
		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		performer.performGet(relatedUrl)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith("/api/businessUnits/110")));
			
	}
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.businessUnitId").value(existingId))
		.andExpect(jsonPath("$.description").value(requestObject.get("description")))
		.andExpect(jsonPath("$.businessUnitType").value((String) JsonPath.read(beforeUpdateJson, "$.businessUnitType")));
		
		String getResult = action.andReturn().getResponse().getContentAsString();
		String companyUrl = JsonPath.read(getResult, "$._links.company.href");
		performer.performGet(companyUrl)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith("/api/companies/00000")));
		String relatedUrl = JsonPath.read(getResult, "$._links.related.href");
		performer.performGet(relatedUrl)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith("/api/businessUnits/110")));
			
	}

	

}
