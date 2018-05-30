package ags.goldenlionerp.apiTests;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ags.goldenlionerp.masterdata.company.Company;

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
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath(expression, matcher))
		
		String getResult = mockMvc.perform(get(url + newId)).andReturn().getResponse().getContentAsString();
		
		
		Company comp = mapper.readValue(getResult, Company.class);

		assertEquals(newId, comp.getCompanyId());
		assertEquals(LocalDate.now(), comp.getInputDateTime().toLocalDate());
		Duration inputDur = Duration.between(comp.getInputDateTime(), LocalDateTime.now());
		assertTrue(inputDur.toHours() <= 1);
		assertEquals("login not yet", comp.getInputUserId());
		assertEquals(comp.getInputDateTime(), comp.getLastUpdateDateTime());
		assertEquals(comp.getInputUserId(), comp.getLastUpdateUserId());
		
		assertEquals(requestObject.get("description"), comp.getDescription());
		assertEquals(requestObject.get("fiscalDatePattern"), comp.getFiscalDatePattern());
		assertEquals(requestObject.get("currentReceivablePeriod"), String.valueOf(comp.getCurrentReceivablePeriod()));
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
				.andExpect(MockMvcResultMatchers.status().isNoContent());
				
		String getResult = mockMvc.perform(get(url)).andReturn().getResponse().getContentAsString();
		Company comp = mapper.readValue(getResult, Company.class);

		assertEquals("00000", comp.getCompanyId());
		assertEquals(LocalDate.now(), comp.getLastUpdateDateTime().toLocalDate());
		Duration dur = Duration.between(comp.getLastUpdateDateTime(), LocalDateTime.now());
		assertTrue(dur.toHours() <= 1);
		assertEquals("login not yet", comp.getLastUpdateUserId());
		assertNotEquals(comp.getInputDateTime(), comp.getLastUpdateDateTime());
		
		assertEquals(requestObject.get("description"), comp.getDescription());
		assertEquals(requestObject.get("fiscalDatePattern"), comp.getFiscalDatePattern());
		assertEquals(requestObject.get("currentReceivablePeriod"), String.valueOf(comp.getCurrentReceivablePeriod()));
	}
}
