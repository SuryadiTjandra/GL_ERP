package ags.goldenlionerp.apiTests;

import static org.junit.Assert.*;

import java.util.Collection;
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
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.company.Company;
import ags.goldenlionerp.util.BeanFinder;
import ags.goldenlionerp.util.DateMatcher;
import ags.goldenlionerp.util.TimeDifferenceLessThanOneHourMatcher;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CompanyApiTest {

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
		url = "/api/companies/";
		existingId = "00000";
		newId = "12345";
		
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mapper = new ObjectMapper()
					.registerModule(new Jackson2HalModule())
					.registerModule(new JavaTimeModule());
		
		requestObject = new HashMap<>();
		requestObject.put("companyId", newId);
		requestObject.put("description", "TESTTEST");
		requestObject.put("currencyCodeBase", "IDR");
		requestObject.put("businessPartnerId", "");
		requestObject.put("fiscalDatePattern", "R");
		requestObject.put("currentFiscalYear", "0");
		requestObject.put("currentAccountingPeriod", "0");
		requestObject.put("currentPayablePeriod", "0");
		requestObject.put("currentReceivablePeriod", "0");
		requestObject.put("currentInventoryPeriod", "0");
		requestObject.put("computerId", "YOOO");
		
		dateTimeMatcher = Matchers.allOf(
							new DateMatcher(),
							new TimeDifferenceLessThanOneHourMatcher()
						  );
	}

	@Test
	public void getTestCollection() throws Exception {
		String jsonResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Resources<Company> res = mapper.readValue(jsonResult, new TypeReference<Resources<Company>>() {});
		
		Collection<Company> list = res.getContent();
		assertEquals(2, list.size());
		assertTrue(list.stream().anyMatch(co -> co.getCompanyId().equals("00000")));
		assertTrue(list.stream().anyMatch(co -> co.getDescription().equals("AMTEK GROUP")));

	}
	
	@Test
	public void getTestSingle() throws Exception{
		String jsonResult = mockMvc.perform(get(url + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		Company comp = mapper.readValue(jsonResult, Company.class);
		assertEquals(existingId, comp.getCompanyId());
		assertEquals("AMTEK GROUP", comp.getDescription());
	}
	
	
	@Test
	@Rollback
	public void createTestWithPost() throws Exception {		
		mockMvc.perform(post(url)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String getResult = mockMvc.perform(get(url + newId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.companyId").value(newId))
				.andExpect(jsonPath("$.inputUserId").value("login not yet"))
				.andExpect(jsonPath("$.inputDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.fiscalDatePattern").value(requestObject.get("fiscalDatePattern")))
				.andExpect(jsonPath("$.currentReceivablePeriod").value(requestObject.get("currentReceivablePeriod")))
				.andReturn().getResponse().getContentAsString();

		assertEquals(
				(String) JsonPath.read(getResult, "$.lastUpdateDateTime"),
				(String) JsonPath.read(getResult, "$.inputDateTime")
		);
	}
	
	@Test
	@Rollback
	public void createTestWithPut() throws Exception {	
		mockMvc.perform(put(url + newId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Test
	@Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(url + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}
	
	@Test
	@Rollback
	public void updateTestWithPatch() throws Exception {
		mockMvc.perform(patch(url + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		EntityManager manager = BeanFinder.findBean(EntityManager.class);
		manager.flush();
		
		String getResult = mockMvc.perform(get(url + existingId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.companyId").value(existingId))
				.andExpect(jsonPath("$.lastUpdateUserId").value("login not yet"))
				.andExpect(jsonPath("$.lastUpdateDateTime", dateTimeMatcher))
				.andExpect(jsonPath("$.description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.fiscalDatePattern").value(requestObject.get("fiscalDatePattern")))
				.andExpect(jsonPath("$.currentReceivablePeriod").value(requestObject.get("currentReceivablePeriod")))
				.andReturn().getResponse().getContentAsString();
		
		assertNotEquals(
				JsonPath.read(getResult, "$.lastUpdateDateTime"),
				JsonPath.read(getResult, "$.inputDateTime")
		);
	}
	
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(url + existingId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
				
		mockMvc.perform(get(url + existingId))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		
	}
}
