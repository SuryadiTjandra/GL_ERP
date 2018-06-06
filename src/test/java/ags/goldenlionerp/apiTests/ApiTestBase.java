package ags.goldenlionerp.apiTests;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
	Map<String, String> requestObject;
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
		
		requestObject = populateRequestObject();
		requestObject.put("computerId", "YOOO");
		
		dateTimeMatcher = Matchers.allOf(
							new DateMatcher(),
							new TimeDifferenceLessThanOneHourMatcher()
						  );
	}
	
	abstract Map<String, String> populateRequestObject();
	abstract String baseUrl();
	abstract ID existingId();
	abstract ID newId();
	
	void assertCreationInfo(String entityJson) {
		assertEquals(JsonPath.read(entityJson, "$.inputUserId"), "login not yet");
		assertThat(JsonPath.read(entityJson, "$.inputDateTime"), dateTimeMatcher);
		assertEquals(JsonPath.read(entityJson, "$.lastUpdateUserId"), "login not yet");
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
		assertEquals(JsonPath.read(entityJson, "$.lastUpdateUserId"), "login not yet");
		assertThat(JsonPath.read(entityJson, "$.lastUpdateDateTime"), dateTimeMatcher);
		
		assertNotEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJson, "$.inputDateTime")
		);
	}
	
	void assertUpdateInfo(String entityJson, String entityJsonBefore) {
		assertUpdateInfo(entityJson);
		
		assertEquals(
				(String) JsonPath.read(entityJson, "$.inputUserId"),
				(String) JsonPath.read(entityJsonBefore, "$.inputUserId")
		);
		assertEquals(
				(String) JsonPath.read(entityJson, "$.inputDateTime"),
				(String) JsonPath.read(entityJsonBefore, "$.inputDateTime")
		);
		assertNotEquals(
				(String) JsonPath.read(entityJson, "$.lastUpdateDateTime"),
				(String) JsonPath.read(entityJsonBefore, "$.lastUpdateDateTime")
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
