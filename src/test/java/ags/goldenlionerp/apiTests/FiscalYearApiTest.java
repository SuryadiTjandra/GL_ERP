package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.application.setups.fiscalyear.FiscalYearPK;

public class FiscalYearApiTest extends ApiTestBaseNew<FiscalYearPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
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
	protected String baseUrl() {
		return "/api/fiscalYears/";
	}

	@Override
	protected FiscalYearPK existingId() {
		return new FiscalYearPK("R", 2024);
	}

	@Override
	protected FiscalYearPK newId() {
		return new FiscalYearPK("F", 2024);
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("startDate");
		requestObject.put("endDateOfPeriod5", LocalDate.of(2024, 5, 23));
		requestObject.put("endDateOfPeriod9", LocalDate.of(2024, 9, 12));
		requestObject.put("endDateOfPeriod10", LocalDate.of(2024, 10, 12));
		
		super.updateTestWithPatch();
	}
	
	@Test @Rollback 
	public void updateTestWithPatchException() throws Exception {
		assumeExists(baseUrl + existingId);
		
		performer.performPatch(baseUrl + existingId, requestObject)
					.andExpect(status().isUnprocessableEntity());
	}
	
	@Test @Rollback
	public void extendTest() throws Exception {
		createTestWithPost();
		assumeExists(baseUrl+newId);
		
		String res = performer.performGet(baseUrl + newId).andReturn().getResponse().getContentAsString();
		String extendLink = JsonPath.read(res, "$._links.extend.href");
		
		String location = performer.performPost(extendLink, null)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andDo(print())
				.andReturn().getResponse().getHeader("Location");
		
		
		String extendRes = performer.performGet(location)
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
		
		String res = performer.performGet(baseUrl + pk).andReturn().getResponse().getContentAsString();
		String extendLink = JsonPath.read(res, "$._links.extend.href");
		
		performer.performPost(extendLink, null)
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.fiscalDatePattern").value(existingId.getFiscalDatePattern()))
			.andExpect(jsonPath("$.fiscalYear").value(existingId.getFiscalYear()))
			.andExpect(jsonPath("$.startDate").value(LocalDate.of(2024, 1, 1).toString()))
			.andExpect(jsonPath("$.endDateOfPeriod1").value(LocalDate.of(2024, 1, 31).toString()))
			.andExpect(jsonPath("$.endDateOfPeriod3").value(LocalDate.of(2024, 3, 31).toString()))
			.andExpect(jsonPath("$.endDateOfPeriod8").value(LocalDate.of(2024, 8, 31).toString()))
			.andExpect(jsonPath("$.endDateOfPeriod12").value(LocalDate.of(2024, 12, 31).toString()))
			.andExpect(jsonPath("$._links.extend.href").exists());
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.fiscalYears").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
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
			;
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
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
			;
	}
}
