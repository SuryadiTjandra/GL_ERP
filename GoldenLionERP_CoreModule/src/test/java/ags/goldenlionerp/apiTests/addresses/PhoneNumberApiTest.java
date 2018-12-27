package ags.goldenlionerp.apiTests.addresses;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseMassUpdate;
import ags.goldenlionerp.application.addresses.phone.PhoneNumberPK;

public class PhoneNumberApiTest extends ApiTestBaseMassUpdate<PhoneNumberPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId().getAddressNumber());
		map.put("sequence", newId().getSequence());
		map.put("countryCode", "+62");
		map.put("areaCode", "31");
		map.put("phoneNumber", "3920553");
		map.put("phoneType", "RUMAH");
		map.put("description", "Nomor telpon rumah");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/phoneNumbers/";
	}

	@Override
	protected PhoneNumberPK existingId() {
		return new PhoneNumberPK("1015", 1);
	}

	@Override
	protected PhoneNumberPK newId() {
		return new PhoneNumberPK("1015", 5);
	}

	@Override
	protected Map<String, Object> requestObject2() throws Exception {
		Map<String, Object> requestObject2 = new HashMap<>(requestObject());
		requestObject2.put("addressNumber", existingId().getAddressNumber() + 1);
		requestObject2.put("sequence", existingId().getSequence());
		requestObject2.put("description", "hjedfbkwebgkjrwb");
		return requestObject2;
	}

	@Override
	protected String associationLinkUrl() throws Exception {
		String headerUrl = new AddressApiTest().baseUrl();
		String headerResp = performer.performGet(headerUrl + existingId().getAddressNumber())
								.andReturn().getResponse().getContentAsString();
		String linkUrl = JsonPath.read(headerResp, "$._links.phoneNumbers.href");
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
		.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
		.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", "")))
		.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", "")))
		.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", "")))
		.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", "")))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		;
		
	}

	@Override
	public void assertMassUpdateExistingResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
		.andExpect(jsonPath("$.countryCode").value(requestObject2.getOrDefault("countryCode", (String) JsonPath.read(beforeUpdateJson, "$.countryCode"))))
		.andExpect(jsonPath("$.areaCode").value(requestObject2.getOrDefault("areaCode", (String) JsonPath.read(beforeUpdateJson, "$.areaCode"))))
		.andExpect(jsonPath("$.phoneNumber").value(requestObject2.getOrDefault("phoneNumber", (String) JsonPath.read(beforeUpdateJson, "$.phoneNumber"))))
		.andExpect(jsonPath("$.phoneType").value(requestObject2.getOrDefault("phoneType", (String) JsonPath.read(beforeUpdateJson, "$.phoneType"))))
		.andExpect(jsonPath("$.description").value(requestObject2.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		;
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
		.andExpect(jsonPath("$.countryCode").value("+62"))
		.andExpect(jsonPath("$.areaCode").value(""))
		.andExpect(jsonPath("$.phoneNumber").value("031-5470614"))
		.andExpect(jsonPath("$.phoneType").value("KANTOR"))
		.andExpect(jsonPath("$.description").value("Kantor"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.phoneNumbers").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
		.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", "")))
		.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", "")))
		.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", "")))
		.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", "")))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		;
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
		.andExpect(jsonPath("$.countryCode").value(requestObject.getOrDefault("countryCode", (String) JsonPath.read(beforeUpdateJson, "$.countryCode"))))
		.andExpect(jsonPath("$.areaCode").value(requestObject.getOrDefault("areaCode", (String) JsonPath.read(beforeUpdateJson, "$.areaCode"))))
		.andExpect(jsonPath("$.phoneNumber").value(requestObject.getOrDefault("phoneNumber", (String) JsonPath.read(beforeUpdateJson, "$.phoneNumber"))))
		.andExpect(jsonPath("$.phoneType").value(requestObject.getOrDefault("phoneType", (String) JsonPath.read(beforeUpdateJson, "$.phoneType"))))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		;
		
	}

}
