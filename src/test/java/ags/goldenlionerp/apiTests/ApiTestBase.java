package ags.goldenlionerp.apiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import ags.goldenlionerp.util.UpdateResultAssertion;
import ags.goldenlionerp.util.ResultAssertion;
import ags.goldenlionerp.util.WebIdUtil;
import ags.goldenlionerp.util.mockmvcperformer.MockMvcPerformer;
import ags.goldenlionerp.util.refresh.JpaRefresher;

/**
 * A new base class for API tests. Contain the boilerplates for the standard test as specified by {@link ApiTest} interface.
 * Subclasses only need to implement the result checking parts.
 * @author user
 *
 * @param <ID>
 */
@Import(ApiTestBase.TestConfig.class)
@TestPropertySource("classpath:test.properties")
public abstract class ApiTestBase<ID extends Serializable> extends ApiTestBaseOld<ID>{
	
	protected MockMvcPerformer performer;
	
	protected abstract Map<String, Object> requestObject() throws Exception;
	protected abstract String baseUrl();
	protected abstract ID existingId();
	protected abstract ID newId();
	
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
	
	public void refreshData() throws Exception {
		JpaRefresher ref = new JpaRefresher(performer);
		ref.refresh();
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
		assertCreationInfoWithUser(defaultUsername(), entityJson);
	}
	
	protected void assertCreationInfoWithUser(String username, String entityJson) {
		assertEquals(username, JsonPath.read(entityJson, "$.inputUserId"));
		assertThat(JsonPath.read(entityJson, "$.inputDateTime"), dateTimeMatcher);
		assertEquals(username, JsonPath.read(entityJson, "$.lastUpdateUserId"));
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
		assertUpdateInfoWithUser(defaultUsername(), entityJson);
	}
	
	protected void assertUpdateInfoWithUser(String username, String entityJson) {
		assertEquals(username, JsonPath.read(entityJson, "$.lastUpdateUserId"));
		assertThat(JsonPath.read(entityJson, "$.lastUpdateDateTime"), dateTimeMatcher);
		
		assertNotEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
	}
	
	protected void assertUpdateInfo(String entityJson, String entityJsonBefore) {
		assertUpdateInfoWithUser(defaultUsername(), entityJson, entityJsonBefore);
	}
	
	protected void assertUpdateInfoWithUser(String username, String entityJson, String entityJsonBefore) {
		assertUpdateInfoWithUser(username, entityJson);
		
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
	
	protected void assertLinks(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._links").exists())
			.andExpect(jsonPath("$._links.self").exists())
			.andExpect(jsonPath("$._links.self.href").exists());
		
		String res1 = action.andReturn().getResponse().getContentAsString();
		
		String selfLink = JsonPath.read(res1, "$._links.self.href");
		String res2 = performer.performGet(selfLink)
					.andExpect(status().isOk())
					.andReturn().getResponse().getContentAsString();
		assertEquals(res1, res2);
	}
	
	protected void assertLinksCollection(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.._links").exists())
			.andExpect(jsonPath("$._embedded.._links.self").exists())
			.andExpect(jsonPath("$._embedded.._links.self.href").exists())
			.andExpect(jsonPath("$._links.self.href").exists());
			
		//if "page" path doesn't exist, then stop assertion
		try {
			String res = action.andReturn().getResponse().getContentAsString();
			JsonPath.read(res, "$.page");
		} catch (PathNotFoundException ex) {
			return;
		}
		
		action
			.andExpect(jsonPath("$.page.size").exists())
			.andExpect(jsonPath("$.page.totalElements").exists())
			.andExpect(jsonPath("$.page.totalPages").exists())
			.andExpect(jsonPath("$.page.number").exists());
	}
	
	@Override @Test
	public void getTestSingle() throws Exception {
		String existingIdStr = WebIdUtil.toWebId(existingId);
		ResultActions action = performer.performGet(baseUrl() + existingIdStr)
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		assertGetSingleResult(action);
		assertLinks(action);
	}
	
	public abstract void assertGetSingleResult(ResultActions action) throws Exception;

	@Override @Test
	public void getTestCollection() throws Exception {
		ResultActions action = performer.performGet(baseUrl())
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
			
		assertGetCollectionResult(action);	
		assertLinksCollection(action);
	}
	
	public abstract void assertGetCollectionResult(ResultActions action) throws Exception;

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		createTestWithPost(this::assertCreateWithPostResultOuter);
	}
	
	public void createTestWithPost(ResultAssertion assertMethod) throws Exception{
		String newIdStr = WebIdUtil.toWebId(newId);
		assumeNotExists(baseUrl()+newIdStr);
		
		performer.performPost(baseUrl(), requestObject)
			.andExpect(status().isCreated());
		
		refreshData();
		
		ResultActions action = performer.performGet(baseUrl() + newIdStr);
		action.andDo(print());
		//assertCreateWithPostResultOuter(action);
		assertMethod.assertResult(action);
	}

	public void assertCreateWithPostResultOuter(ResultActions action) throws Exception {
		assertCreateWithPostResult(action);
		assertLinks(action);
		String getRes = action.andReturn().getResponse().getContentAsString();
		assertCreationInfo(getRes);
	}
	public abstract void assertCreateWithPostResult(ResultActions action) throws Exception;

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		updateTestWithPatch(this::assertUpdateWithPatchResultOuter);
	}
	
	public void updateTestWithPatch(UpdateResultAssertion assertMethod) throws Exception {
		String existingIdStr = WebIdUtil.toWebId(existingId);
		assumeExists(baseUrl() + existingIdStr);
		
		String beforePatch = performer.performGet(baseUrl() + existingIdStr)
								.andReturn().getResponse().getContentAsString();
		
		performer.performPatch(baseUrl() + existingIdStr, requestObject)
			.andExpect(status().is2xxSuccessful());
	
		refreshData();
		
		ResultActions action = performer.performGet(baseUrl() + existingIdStr);
		assertMethod.assertResult(action, beforePatch);
	}
	
	public void assertUpdateWithPatchResultOuter(ResultActions action, String beforeUpdateJson) throws Exception {
		assertUpdateWithPatchResult(action, beforeUpdateJson);
		assertLinks(action);
		String getRes = action.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getRes, beforeUpdateJson);
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
	
	@TestConfiguration
	@ComponentScan("ags.goldenlionerp")
	public static class TestConfig {

	}
}
