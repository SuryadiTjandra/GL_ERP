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
import javax.transaction.Transactional;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.util.DateMatcher;
import ags.goldenlionerp.util.TimeDifferenceLessThanOneHourMatcher;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public abstract class ApiTestBase<ID extends Serializable> implements ApiTest{

	@Autowired
	WebApplicationContext wac;
	
	MockMvc mockMvc;
	EntityManager em;
	ObjectMapper mapper;
	Map<String, Object> requestObject;
	String baseUrl;
	ID existingId;
	ID newId;
	Matcher<String> dateTimeMatcher;
	
	@Before
	public void setUp() throws Exception{
		baseUrl = baseUrl();
		existingId = existingId();
		newId = newId();
		
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		em = wac.getBean(EntityManager.class);
		
		mapper = new ObjectMapper()
					.registerModule(new Jackson2HalModule())
					.registerModule(new JavaTimeModule());
		
		requestObject = requestObject();
		requestObject.put("computerId", "YOOO");
		requestObject.put("inputUserId", "NO");
		requestObject.put("lastUpdateUserId", "NAY");
		requestObject.put("inputUserDateTime", LocalDateTime.of(1994, 1, 26, 8, 15, 30));
		requestObject.put("lastUpdateDateTime",  LocalDateTime.of(2015, 2, 28, 20, 45, 0));
		
		dateTimeMatcher = Matchers.allOf(
							new DateMatcher(),
							new TimeDifferenceLessThanOneHourMatcher()
						  );
	}
	
	abstract Map<String, Object> requestObject() throws Exception;
	abstract String baseUrl();
	abstract ID existingId();
	abstract ID newId();
	
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
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
		assertEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateUserId"),
				(String) JsonPath.read(entityJson, "$.inputUserId")
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
	
	void assumeExists(String url) throws Exception {
		mockMvc.perform(get(url))
				.andDo(res -> assumeTrue(res.getResponse().getStatus()==200));
	}
	
	void assumeNotExists(String url) throws Exception {
		mockMvc.perform(get(url))
				.andDo(res -> assumeTrue(res.getResponse().getStatus()==404));
	}
}
