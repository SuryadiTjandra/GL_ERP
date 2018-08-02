package ags.goldenlionerp.apiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.WebIdUtil;
import ags.goldenlionerp.util.mockmvcperformer.MockMvcPerformer;

/**
 * A new base class for API tests. Contain the boilerplates for the standard test as specified by {@link ApiTest} interface.
 * Subclasses only need to implement the result checking parts.
 * @author user
 *
 * @param <ID>
 */
public abstract class ApiTestBaseNew<ID extends Serializable> extends ApiTestBase<ID>{
	
	protected MockMvcPerformer performer;
	
	@Before	@Override
	public void setUp() throws Exception {
		super.setUp();
		performer = new MockMvcPerformer(this.mockMvc, this.mapper, defaultUser());
	}
	
	public UserDetails defaultUser() {
		return User.builder()
			.username(defaultUsername())
			.password(defaultPassword())
			.authorities(defaultAuthorities())
			.build();
	}
	
	public String defaultUsername() {
		return "username";
	}
	
	public String defaultPassword() {
		return "password";
	}
	
	public Collection<GrantedAuthority> defaultAuthorities() {
		return Collections.emptyList();
	}
	
	public void refreshData() {
		em.flush();
		em.clear();
	}
	
	
	protected void assumeExists(String url) throws Exception {
		performer.performGet(url)
				.andDo(res -> assumeTrue("Status is " + res.getResponse().getStatus(),
										res.getResponse().getStatus() == 200));
	}
	
	protected void assumeNotExists(String url) throws Exception {
		performer.performGet(url)
				.andDo(res -> assumeTrue("Status is " + res.getResponse().getStatus(),
										res.getResponse().getStatus() == 404));
	}
	
	protected void assertCreationInfo(String entityJson) {
		assertEquals("login not yet",JsonPath.read(entityJson, "$.inputUserId"));
		assertThat(JsonPath.read(entityJson, "$.inputDateTime"), dateTimeMatcher);
		assertEquals("login not yet", JsonPath.read(entityJson, "$.lastUpdateUserId"));
		assertThat(JsonPath.read(entityJson, "$.lastUpdateDateTime"), dateTimeMatcher);
		
		assertEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
		assertEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateUserId"),
				(String) JsonPath.read(entityJson, "$.inputUserId")
		);
	}
	
	protected void assertUpdateInfo(String entityJson) {
		assertEquals("login not yet", JsonPath.read(entityJson, "$.lastUpdateUserId"));
		assertThat(JsonPath.read(entityJson, "$.lastUpdateDateTime"), dateTimeMatcher);
		
		assertNotEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
	}
	
	protected void assertUpdateInfo(String entityJson, String entityJsonBefore) {
		assertUpdateInfo(entityJson);
		
		assertEquals(
				(String) JsonPath.read(entityJsonBefore, "$.inputUserId"),
				(String) JsonPath.read(entityJson, "$.inputUserId")
		);
		assertEquals(
				(String) JsonPath.read(entityJsonBefore, "$.inputDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
		assertNotEquals(
				(String) JsonPath.read(entityJsonBefore, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime")
		);
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		ResultActions action = performer.performGet(baseUrl() + existingId)
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		assertGetSingleResult(action);
	}
	
	public abstract void assertGetSingleResult(ResultActions action) throws Exception;

	@Override @Test
	public void getTestCollection() throws Exception {
		ResultActions action = performer.performGet(baseUrl())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
			
		assertGetCollectionResult(action);	
	}
	
	public abstract void assertGetCollectionResult(ResultActions action) throws Exception;

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		String newId = WebIdUtil.toWebId(newId());
		assumeNotExists(baseUrl()+newId);
		
		performer.performPost(baseUrl(), requestObject)
			.andExpect(status().isCreated());
		
		refreshData();
		
		ResultActions action = performer.performGet(baseUrl() + newId);
		action.andDo(print());
		assertCreateWithPostResult(action);
		
		String getRes = action.andReturn().getResponse().getContentAsString();
		assertCreationInfo(getRes);
		
	}

	public abstract void assertCreateWithPostResult(ResultActions action) throws Exception;

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		assumeExists(baseUrl() + existingId);
		
		String beforePatch = performer.performGet(baseUrl() + existingId)
								.andReturn().getResponse().getContentAsString();
		
		performer.performPatch(baseUrl() + existingId, requestObject)
			.andExpect(status().is2xxSuccessful());
	
		refreshData();
		
		ResultActions action = performer.performGet(baseUrl() + existingId);
		assertUpdateWithPatchResult(action, beforePatch);
		
		String getResult = action.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);

	}
	
	public abstract void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception;

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		assumeExists(baseUrl()+existingId);
		
		performer.performPut(baseUrl() + existingId, requestObject)
			.andExpect(status().isMethodNotAllowed());
		
	}
	
	@Override @Test @Rollback
	public void createTestWithPut() throws Exception {
		String newId = WebIdUtil.toWebId(newId());
		assumeNotExists(baseUrl()+newId);
		
		performer.performPut(baseUrl() + newId, requestObject)
			.andExpect(status().isMethodNotAllowed());
		
	}

	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		String existingId = WebIdUtil.toWebId(existingId());
		assumeExists(baseUrl()+existingId);
		
		performer.performDelete(baseUrl() + existingId)
			.andExpect(status().isNoContent());
		
		performer.performGet(baseUrl() + existingId)
			.andExpect(status().isNotFound());
			
	}

}
