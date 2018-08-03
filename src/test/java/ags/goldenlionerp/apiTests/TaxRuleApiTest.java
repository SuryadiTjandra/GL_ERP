package ags.goldenlionerp.apiTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.application.setups.taxcode.TaxRulePK;

public class TaxRuleApiTest extends ApiTestBaseNew<TaxRulePK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("taxCode", newId().getTaxCode());
		map.put("effectiveDate", newId().getEffectiveDate());
		map.put("expiredDate", LocalDate.of(2025, 5, 16));
		map.put("description", "Test Tax");
		map.put("taxAuthority1", "");
		map.put("taxClass1", "MBR");
		map.put("taxPercentage1", 20.25);
		map.put("taxClass2", "INFG");
		map.put("taxPercentage2", 12.33);
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/taxRules/";
	}

	@Override
	protected TaxRulePK existingId() {
		return new TaxRulePK("INCL", LocalDate.of(2009, 1, 1));
	}

	@Override
	protected TaxRulePK newId() {
		return new TaxRulePK("NOTAX", LocalDate.of(2018, 7, 30));
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.taxCode").value(existingId.getTaxCode()))
			.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
			.andExpect(jsonPath("$.expiredDate").value(LocalDate.of(2025, 4, 1).toString()))
			.andExpect(jsonPath("$.description").value("Harga Include PPn"))
			.andExpect(jsonPath("$.taxAuthority1").value(""))
			.andExpect(jsonPath("$.taxClass1").value(""))
			.andExpect(jsonPath("$.taxPercentage1").value(10.00))
			.andExpect(jsonPath("$.taxAuthority2").value(""))
			.andExpect(jsonPath("$.taxClass2").value(""))
			.andExpect(jsonPath("$.taxPercentage2").value(0.00))
			.andExpect(jsonPath("$.taxAllowance").value(true))
			.andExpect(jsonPath("$.documentType").value(""));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.taxRules").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.taxCode").value(newId.getTaxCode()))
			.andExpect(jsonPath("$.effectiveDate").value(newId.getEffectiveDate().toString()))
			.andExpect(jsonPath("$.expiredDate").value(requestObject.get("expiredDate").toString()))
			.andExpect(jsonPath("$.taxAuthority1").value(requestObject.getOrDefault("taxAuthority1", "")))
			.andExpect(jsonPath("$.taxClass1").value(requestObject.getOrDefault("taxClass1", "")))
			.andExpect(jsonPath("$.taxPercentage1").value(requestObject.getOrDefault("taxPercentage1", 0)))
			.andExpect(jsonPath("$.taxAuthority2").value(requestObject.getOrDefault("taxAuthority2", "")))
			.andExpect(jsonPath("$.taxClass2").value(requestObject.getOrDefault("taxClass2", "")))
			.andExpect(jsonPath("$.taxPercentage2").value(requestObject.getOrDefault("taxPercentage2", 0)))
			.andExpect(jsonPath("$.taxAllowance").value(requestObject.getOrDefault("taxAllowance", false)))
			.andExpect(jsonPath("$.documentType").value(requestObject.getOrDefault("documentType", "")));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
			.andExpect(jsonPath("$.taxCode").value(existingId.getTaxCode()))
			.andExpect(jsonPath("$.effectiveDate").value(existingId.getEffectiveDate().toString()))
			.andExpect(jsonPath("$.expiredDate").value(requestObject.getOrDefault("expiredDate", JsonPath.read(beforeUpdateJson, "$.expiredDate")).toString()))
			.andExpect(jsonPath("$.taxAuthority1").value(requestObject.getOrDefault("taxAuthority1", JsonPath.read(beforeUpdateJson, "$.taxAuthority1"))))
			.andExpect(jsonPath("$.taxClass1").value(requestObject.getOrDefault("taxClass1", JsonPath.read(beforeUpdateJson, "$.taxClass1"))))
			.andExpect(jsonPath("$.taxPercentage1").value(requestObject.getOrDefault("taxPercentage1", JsonPath.read(beforeUpdateJson, "$.taxPercentage1"))))
			.andExpect(jsonPath("$.taxAuthority2").value(requestObject.getOrDefault("taxAuthority2", JsonPath.read(beforeUpdateJson, "$.taxAuthority2"))))
			.andExpect(jsonPath("$.taxClass2").value(requestObject.getOrDefault("taxClass2", JsonPath.read(beforeUpdateJson, "$.taxClass2"))))
			.andExpect(jsonPath("$.taxPercentage2").value(requestObject.getOrDefault("taxPercentage2", JsonPath.read(beforeUpdateJson, "$.taxPercentage2"))))
			.andExpect(jsonPath("$.taxAllowance").value(requestObject.getOrDefault("taxAllowance", JsonPath.read(beforeUpdateJson, "$.taxAllowance"))))
			.andExpect(jsonPath("$.documentType").value(requestObject.getOrDefault("documentType", JsonPath.read(beforeUpdateJson, "$.documentType"))));
			
	}

	

}
