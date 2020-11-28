package ags.goldenlionerp.apiTests;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.DateMatcher;
import ags.goldenlionerp.util.TimeDifferenceLessThanOneHourMatcher;

/**
 * The old base class for API Tests. Don't subclass this anymore 
 * @author user
 *
 * @param <ID>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public abstract class ApiTestBaseOld<ID extends Serializable> implements ApiTest{

	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	protected EntityManager em;
	protected ObjectMapper mapper;
	protected Map<String, Object> requestObject;
	protected String baseUrl;
	protected ID existingId;
	protected ID newId;
	protected Matcher<String> dateTimeMatcher;
	
	@Before
	public void setUp() throws Exception{
		baseUrl = baseUrl();
		existingId = existingId();
		newId = newId();
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(wac)
					.apply(SecurityMockMvcConfigurers.springSecurity())
					.build();
		em = wac.getBean(EntityManager.class);
		
		mapper = new ObjectMapper()
					.registerModule(new Jackson2HalModule())
					.registerModule(new JavaTimeModule());
		
	
		requestObject = requestObject();
		//add random metadata to request object. These shouldn't affect the request result.
		requestObject.put("computerId", "YOOO");
		requestObject.put("inputUserId", "NO");
		requestObject.put("lastUpdateUserId", "NAY");
		requestObject.put("inputDateTime", LocalDateTime.of(1994, 1, 26, 8, 15, 30));
		requestObject.put("lastUpdateDateTime",  LocalDateTime.of(2015, 2, 28, 20, 45, 0));
		
		dateTimeMatcher = Matchers.allOf(
							new DateMatcher(),
							new TimeDifferenceLessThanOneHourMatcher()
						  );
	}
	
	protected abstract Map<String, Object> requestObject() throws Exception;
	protected abstract String baseUrl();
	protected abstract ID existingId();
	protected abstract ID newId();
	
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
		
		em.flush();em.clear();
	
		mockMvc.perform(get(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Override @Test @Rollback @WithMockUser
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
	
	void assertCreationInfo(String entityJson) {
		assertEquals("login not yet",JsonPath.read(entityJson, "$.inputUserId"));
		assertThat(JsonPath.read(entityJson, "$.inputDateTime"), dateTimeMatcher);
		assertEquals("login not yet", JsonPath.read(entityJson, "$.lastUpdateUserId"));
		assertThat(JsonPath.read(entityJson, "$.lastUpdateDateTime"), dateTimeMatcher);
		
		assertEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime"),
				"Last update time and input date time are not equal"
		);
		assertEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateUserId"),
				(String) JsonPath.read(entityJson, "$.inputUserId"),
				"Last update and input user are not equal"
		);
	}
	
	void assertUpdateInfo(String entityJson) {
		assertEquals("login not yet", JsonPath.read(entityJson, "$.lastUpdateUserId"));
		assertThat(JsonPath.read(entityJson, "$.lastUpdateDateTime"), dateTimeMatcher);
		
		assertNotEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
	}
	
	void assertUpdateInfo(String entityJson, String entityJsonBefore) {
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
	
	protected void assumeExists(String url) throws Exception {
		mockMvc.perform(get(url))
				.andDo(res -> assumeTrue(res.getResponse().getStatus()==200));
	}
	
	protected void assumeNotExists(String url) throws Exception {
		mockMvc.perform(get(url))
				.andDo(res -> assumeTrue(res.getResponse().getStatus()==404));
	}
}
