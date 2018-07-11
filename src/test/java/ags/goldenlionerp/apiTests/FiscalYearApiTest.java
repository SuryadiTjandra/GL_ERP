package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.system.fiscalyear.FiscalYearPK;

public class FiscalYearApiTest extends ApiTestBase<FiscalYearPK> {

	@Override
	Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("fiscalDatePattern", newId().getFiscalDatePattern());
		map.put("fiscalYear", newId().getFiscalYear());
		map.put("startDate", LocalDate.of(2024, 6, 15));
		map.put("endDateOfPeriod5", LocalDate.of(2024, 11, 23));
		map.put("endDateOfPeriod9", LocalDate.of(2025, 4, 12));
		map.put("endDateOfPeriod10", LocalDate.of(2025, 6, 12));
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/fiscalYears/";
	}

	@Override
	FiscalYearPK existingId() {
		return new FiscalYearPK("R", 2024);
	}

	@Override
	FiscalYearPK newId() {
		return new FiscalYearPK("F", 2024);
	}

	
	@Override @Test
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.fiscalDatePattern").value(existingId.getFiscalDatePattern()))
				.andExpect(jsonPath("$.fiscalYear").value(existingId.getFiscalYear()))
				.andExpect(jsonPath("$.startDate").value(LocalDate.of(2024, 1, 1).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod1").value(LocalDate.of(2024, 1, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod3").value(LocalDate.of(2024, 3, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod8").value(LocalDate.of(2024, 8, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod12").value(LocalDate.of(2024, 12, 31).toString()))
				.andExpect(jsonPath("$._links.extend.href").exists());
	}

	@Override @Test
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$._embedded.fiscalYears").exists());
		
	}

	@Override @Test @Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl+newId);
		
		mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		em.flush(); em.clear();
		
		String getRes = mockMvc.perform(get(baseUrl + newId))
				.andExpect(jsonPath("$.fiscalDatePattern").value(newId.getFiscalDatePattern()))
				.andExpect(jsonPath("$.fiscalYear").value(newId.getFiscalYear()))
				.andExpect(jsonPath("$.startDate").value(LocalDate.of(2024, 6, 15).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod1").value(LocalDate.of(2024, 6, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod2").value(LocalDate.of(2024, 7, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod3").value(LocalDate.of(2024, 8, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod4").value(LocalDate.of(2024, 9, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod5").value(LocalDate.of(2024, 11, 23).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod6").value(LocalDate.of(2024, 11, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod7").value(LocalDate.of(2024, 12, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod8").value(LocalDate.of(2025, 1, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod9").value(LocalDate.of(2025, 4, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod10").value(LocalDate.of(2025, 6, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod11").value(LocalDate.of(2025, 6, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod12").value(LocalDate.of(2025, 6, 14).toString()))
				.andReturn().getResponse().getContentAsString();
				
		assertCreationInfo(getRes);
		
	}

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
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		requestObject.remove("startDate");
		requestObject.put("endDateOfPeriod5", LocalDate.of(2024, 5, 23));
		requestObject.put("endDateOfPeriod9", LocalDate.of(2024, 9, 12));
		requestObject.put("endDateOfPeriod10", LocalDate.of(2024, 10, 12));
		
		mockMvc.perform(patch(baseUrl + existingId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
		em.flush();	em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
				.andExpect(jsonPath("$.fiscalDatePattern").value(existingId.getFiscalDatePattern()))
				.andExpect(jsonPath("$.fiscalYear").value(existingId.getFiscalYear()))
				.andExpect(jsonPath("$.startDate").value(LocalDate.of(2024, 1, 1).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod1").value(LocalDate.of(2024, 1, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod2").value(LocalDate.of(2024, 2, 29).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod3").value(LocalDate.of(2024, 3, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod4").value(LocalDate.of(2024, 4, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod5").value(LocalDate.of(2024, 5, 23).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod6").value(LocalDate.of(2024, 6, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod7").value(LocalDate.of(2024, 7, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod8").value(LocalDate.of(2024, 8, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod9").value(LocalDate.of(2024, 9, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod10").value(LocalDate.of(2024, 10, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod11").value(LocalDate.of(2024, 11, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod12").value(LocalDate.of(2024, 12, 31).toString()))
				.andReturn().getResponse().getContentAsString();
		
		assertUpdateInfo(getResult, beforePatch);
		
	}
	
	@Test(expected=IllegalStateException.class) @Rollback
	public void updateTestWithPatchException() throws Exception {
		assumeExists(baseUrl + existingId);
		
		mockMvc.perform(patch(baseUrl + existingId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestObject)))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();	em.clear();
	}
	
	

	@Override @Test @Rollback
	public void updateTestWithPut() throws Exception {
		assumeExists(baseUrl+existingId);
		
		mockMvc.perform(put(baseUrl + existingId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		
	}

	@Test @Rollback
	public void extendTest() throws Exception {
		createTestWithPost();
		assumeExists(baseUrl+newId);
		
		String res = mockMvc.perform(get(baseUrl + newId)).andReturn().getResponse().getContentAsString();
		String extendLink = JsonPath.read(res, "$._links.extend.href");
		
		String location = mockMvc.perform(post(extendLink))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getHeader("Location");
		
		
		String extendRes = mockMvc.perform(get(location).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.fiscalDatePattern").value(newId.getFiscalDatePattern()))
				.andExpect(jsonPath("$.fiscalYear").value(newId.getFiscalYear() + 1))
				.andExpect(jsonPath("$.startDate").value(LocalDate.of(2025, 6, 15).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod1").value(LocalDate.of(2025, 6, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod2").value(LocalDate.of(2025, 7, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod3").value(LocalDate.of(2025, 8, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod4").value(LocalDate.of(2025, 9, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod5").value(LocalDate.of(2025, 11, 23).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod6").value(LocalDate.of(2025, 11, 30).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod7").value(LocalDate.of(2025, 12, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod8").value(LocalDate.of(2026, 1, 31).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod9").value(LocalDate.of(2026, 4, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod10").value(LocalDate.of(2026, 6, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod11").value(LocalDate.of(2026, 6, 12).toString()))
				.andExpect(jsonPath("$.endDateOfPeriod12").value(LocalDate.of(2026, 6, 14).toString()))
				.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(extendRes);
	}
	
	@Rollback @Test
	public void extendTestWithException() throws Exception {
		FiscalYearPK pk = new FiscalYearPK(existingId.getFiscalDatePattern(), existingId.getFiscalYear());
		assumeExists(baseUrl+pk);
		
		String res = mockMvc.perform(get(baseUrl + pk)).andReturn().getResponse().getContentAsString();
		String extendLink = JsonPath.read(res, "$._links.extend.href");
		
		mockMvc.perform(post(extendLink))
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}
}
