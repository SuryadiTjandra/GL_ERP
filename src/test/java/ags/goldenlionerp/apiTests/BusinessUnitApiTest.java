package ags.goldenlionerp.apiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
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
import org.springframework.http.MediaType;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BusinessUnitApiTest implements ApiTest {

	@Autowired
	WebApplicationContext wac;
	
	MockMvc mockMvc;
	ObjectMapper mapper;
	Map<String, String> requestObject;
	String url;
	String existingId;
	String newId;
	Matcher<String> dateTimeMatcher;
	
	@Before
	public void setUp() throws Exception {
		url = "/api/businessUnits/";
		existingId = "100";
		newId = "125";
		
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mapper = new ObjectMapper()
					.registerModule(new Jackson2HalModule())
					.registerModule(new JavaTimeModule());
		
		requestObject = new HashMap<>();
		requestObject.put("businessUnitId", newId);
		requestObject.put("description", "TESTTEST");
		requestObject.put("businessUnitType", "BS");
		requestObject.put("idNumber", "");
		requestObject.put("company", "/api/companies/00000");
		requestObject.put("relatedBusinessUnit", "/api/businessUnit/100");
		requestObject.put("modelOrConsolidated", "");
		requestObject.put("computerId", "YOOO");
		
		dateTimeMatcher = Matchers.allOf(
							new DateMatcher(),
							new TimeDifferenceLessThanOneHourMatcher()
						  );
	}
	
	@Override
	@Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(url + existingId).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.businessUnitId").value(existingId))
			.andExpect(jsonPath("$.description").value("AMTEK"));

	}

	@Override
	@Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
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
		mockMvc.perform(post(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String getResult = mockMvc.perform(get(url + newId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(jsonPath("$.businessUnitId").value(newId))
				.andExpect(jsonPath("$.inputUserId").value("login not yet"))
				.andExpect(jsonPath("$.inputDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(
				(String) JsonPath.read(getResult, "$.lastUpdateDateTime"),
				(String) JsonPath.read(getResult, "$.inputDateTime")
		);
		
		String companyUrl = JsonPath.read(getResult, "$._links.company.href");
		mockMvc.perform(get(companyUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("company"))));
		String relatedUrl = JsonPath.read(getResult, "$._links.relatedBusinessUnit.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("relatedBusinessUnit"))));
			
	}

	@Test
	@Rollback
	public void createTestWithPostWithoutRelatedBU() throws Exception {
		requestObject.remove("relatedBusinessUnit");
		mockMvc.perform(post(url)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		String getResult = mockMvc.perform(get(url + newId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andReturn().getResponse().getContentAsString();

		String relatedUrl = JsonPath.read(getResult, "$._links.relatedBusinessUnit.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
			
	}
	
	@Override
	@Test
	@Rollback
	public void createTestWithPut() throws Exception {
		requestObject.remove("businessUnitId");
		mockMvc.perform(put(url + newId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Override
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(url + existingId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		mockMvc.perform(get(url + existingId))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Override
	@Test
	@Rollback
	public void updateTestWithPatch() throws Exception {
		mockMvc.perform(patch(url + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		EntityManager manager = wac.getBean(EntityManager.class);
		manager.flush();
		
		String getResult = mockMvc.perform(get(url + existingId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(jsonPath("$.businessUnitId").value(existingId))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andReturn().getResponse().getContentAsString();
		
		assertNotEquals(
				JsonPath.read(getResult, "$.lastUpdateDateTime"),
				JsonPath.read(getResult, "$.inputDateTime")
		);
		
		String companyUrl = JsonPath.read(getResult, "$._links.company.href");
		mockMvc.perform(get(companyUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("company"))));
		String relatedUrl = JsonPath.read(getResult, "$._links.relatedBusinessUnit.href");
		mockMvc.perform(get(relatedUrl))
				.andExpect(jsonPath("$._links.self.href", Matchers.endsWith(requestObject.get("relatedBusinessUnit"))));
			
	}

	@Override
	@Test
	@Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(url + existingId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}
	

}
