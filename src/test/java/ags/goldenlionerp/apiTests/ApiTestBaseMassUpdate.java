package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.util.WebIdUtil;

public abstract class ApiTestBaseMassUpdate<ID extends Serializable> extends ApiTestBaseNew<ID> {

	protected abstract Map<String, Object> requestObject2() throws Exception;
	protected abstract String associationLinkUrl() throws Exception;
	
	protected Map<String, Object> requestObject2;
	protected String associationLinkUrl;
	
	@Override @Before
	public void setUp() throws Exception {
		super.setUp();
		this.requestObject2 = requestObject2();
		this.associationLinkUrl = associationLinkUrl();
	}
	
	@Test @Rollback
	public void massUpdateTest() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		String newId = WebIdUtil.toWebId(newId());
		
		//get existing data before update
		String beforeUpdate = performer.performGet(baseUrl + existingId)
								.andReturn().getResponse().getContentAsString();
		
		//post update, consisting of a new object request and an existing object request
		List<Map<String, Object>> list = Arrays.asList(requestObject, requestObject2);
		performer.performPost(associationLinkUrl, list)
					.andExpect(status().is2xxSuccessful());
		
		refreshData();
		
		//check if new object is create properly
		ResultActions actionCreate = performer.performGet(baseUrl() + newId);
		assertMassUpdateNewResultOuter(actionCreate);
		
		//check if existing object is updated properly
		ResultActions actionUpdate = performer.performGet(baseUrl() + existingId);
		assertMassUpdateExistingResultOuter(actionUpdate, beforeUpdate);
	}
	
	public void assertMassUpdateNewResultOuter(ResultActions action) throws Exception {
		assertMassUpdateNewResult(action);
		String getRes = action.andReturn().getResponse().getContentAsString();
		assertCreationInfo(getRes);
	}
	
	public void assertMassUpdateExistingResultOuter(ResultActions action, String beforeUpdateJson) throws Exception {
		assertMassUpdateExistingResult(action, beforeUpdateJson);
		String getRes = action.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getRes, beforeUpdateJson);
	}
	public abstract void assertMassUpdateNewResult(ResultActions action) throws Exception;
	public abstract void assertMassUpdateExistingResult(ResultActions action, String beforeUpdateJson) throws Exception;
}
