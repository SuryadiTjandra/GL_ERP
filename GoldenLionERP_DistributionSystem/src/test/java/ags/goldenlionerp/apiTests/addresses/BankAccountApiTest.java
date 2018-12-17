package ags.goldenlionerp.apiTests.addresses;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseMassUpdate;
import ags.goldenlionerp.application.addresses.bankaccount.BankAccountPK;

public class BankAccountApiTest extends ApiTestBaseMassUpdate<BankAccountPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId().getAddressNumber());
		map.put("bankId", newId().getBankId());
		map.put("bankAccountNumber", newId().getBankAccountNumber());
		map.put("description", "Test bank account");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/bankAccounts/";
	}

	@Override
	protected BankAccountPK existingId() {
		return new BankAccountPK("0100", "", "");
	}

	@Override
	protected BankAccountPK newId() {
		return new BankAccountPK("0100", "008", "123456789");
	}
	
	@Override
	protected Map<String, Object> requestObject2() throws Exception {
		Map<String, Object> requestObject2 = new HashMap<>(requestObject());
		requestObject2.put("addressNumber", existingId().getAddressNumber() + 1);
		requestObject2.put("bankId", existingId().getBankId());
		requestObject2.put("bankAccountNumber", existingId().getBankAccountNumber());
		requestObject2.put("description", "1234567890");
		return requestObject2;
	}

	@Override
	protected String associationLinkUrl() throws Exception {
		String headerUrl = new AddressApiTest().baseUrl();
		String headerResp = performer.performGet(headerUrl + existingId().getAddressNumber())
								.andReturn().getResponse().getContentAsString();
		String linkUrl = JsonPath.read(headerResp, "$._links.bankAccounts.href");
		return linkUrl;
	}
	
	
	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		requestObject.remove("description");
		super.updateTestWithPatch();		
	}

	@Override
	public void assertMassUpdateNewResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
		.andExpect(jsonPath("$.bankId").value(newId.getBankId()))
		.andExpect(jsonPath("$.bankAccountNumber").value(newId.getBankAccountNumber()))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		;
		
	}

	@Override
	public void assertMassUpdateExistingResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.bankId").value(existingId.getBankId()))
		.andExpect(jsonPath("$.bankAccountNumber").value(existingId.getBankAccountNumber()))
		.andExpect(jsonPath("$.description").value(requestObject2.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		;
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.bankId").value(existingId.getBankId()))
		.andExpect(jsonPath("$.bankAccountNumber").value(existingId.getBankAccountNumber()))
		.andExpect(jsonPath("$.description").value(""));
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.bankAccounts").exists());
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
		.andExpect(jsonPath("$.bankId").value(newId.getBankId()))
		.andExpect(jsonPath("$.bankAccountNumber").value(newId.getBankAccountNumber()))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")));
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.bankId").value(existingId.getBankId()))
		.andExpect(jsonPath("$.bankAccountNumber").value(existingId.getBankAccountNumber()))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		;
		
	}

	

}
