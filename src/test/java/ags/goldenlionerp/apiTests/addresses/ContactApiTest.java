package ags.goldenlionerp.apiTests.addresses;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBaseMassUpdate;
import ags.goldenlionerp.application.addresses.contact.ContactPersonPK;

public class ContactApiTest extends ApiTestBaseMassUpdate<ContactPersonPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("addressNumber", newId.getAddressNumber());
		map.put("sequence", newId.getSequence());
		map.put("mailingName", "Meong super imut");
		map.put("description", "a bwelly kyutie kitti");
		map.put("firstName", "Meong");
		map.put("middleName", "Kucing");
		map.put("lastName", "Imut");
		map.put("salutation", "Nona");
		map.put("gender", "P");
		map.put("type", "C");
		map.put("religion", "05");
		map.put("dateOfBirth", LocalDate.of(1994, 01, 26).toString());
		map.put("placeOfBirth", "Cutietown");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/contacts/";
	}
	
	@Override
	protected Map<String, Object> requestObject2() throws Exception {
		Map<String, Object> requestObject2 = new HashMap<>(requestObject());
		requestObject2.put("addressNumber", existingId().getAddressNumber() + 1);
		requestObject2.put("sequence", existingId().getSequence());
		requestObject2.put("mailingName", "alibaba");
		requestObject2.put("description", "aladin");
		return requestObject2;
	}

	@Override
	protected String associationLinkUrl() throws Exception {
		String headerUrl = new AddressApiTest().baseUrl();
		String headerResp = performer.performGet(headerUrl + existingId().getAddressNumber())
								.andReturn().getResponse().getContentAsString();
		String linkUrl = JsonPath.read(headerResp, "$._links.contacts.href");
		return linkUrl;
	}


	@Override
	protected ContactPersonPK existingId() {
		return new ContactPersonPK("1015", 0);
	}

	@Override
	protected ContactPersonPK newId() {
		return new ContactPersonPK("1015", 1);
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
		.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", "")))
		.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", "")))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		.andExpect(jsonPath("$.firstName").value(requestObject.getOrDefault("firstName", "")))
		.andExpect(jsonPath("$.middleName").value(requestObject.getOrDefault("middleName", "")))
		.andExpect(jsonPath("$.lastName").value(requestObject.getOrDefault("lastName", "")))
		.andExpect(jsonPath("$.nickname").value(requestObject.getOrDefault("nickname", "")))
		.andExpect(jsonPath("$.salutation").value(requestObject.getOrDefault("salutation", "")))
		.andExpect(jsonPath("$.gender").value(requestObject.getOrDefault("gender", "")))
		.andExpect(jsonPath("$.religion").value(requestObject.getOrDefault("religion", "")))
		.andExpect(jsonPath("$.type").value(requestObject.getOrDefault("type", "")))
		.andExpect(jsonPath("$.dateOfBirth").value(requestObject.getOrDefault("dateOfBirth", null)))
		.andExpect(jsonPath("$.placeOfBirth").value(requestObject.getOrDefault("placeOfBirth", "")))
		.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject.getOrDefault("socialSecurityNumber", "")))
		.andExpect(jsonPath("$.socialSecurityDate").value(requestObject.getOrDefault("socialSecurityDate", null)))
		;
		
	}

	@Override
	public void assertMassUpdateExistingResult(ResultActions action, String beforeUpdate) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
		.andExpect(jsonPath("$.name").value(requestObject2.getOrDefault("name", (String) JsonPath.read(beforeUpdate, "$.name"))))
		.andExpect(jsonPath("$.mailingName").value(requestObject2.getOrDefault("mailingName", (String) JsonPath.read(beforeUpdate, "$.mailingName"))))
		.andExpect(jsonPath("$.description").value(requestObject2.getOrDefault("description", (String) JsonPath.read(beforeUpdate, "$.description"))))
		.andExpect(jsonPath("$.firstName").value(requestObject2.getOrDefault("firstName", (String) JsonPath.read(beforeUpdate, "$.firstName"))))
		.andExpect(jsonPath("$.middleName").value(requestObject2.getOrDefault("middleName", (String) JsonPath.read(beforeUpdate, "$.middleName"))))
		.andExpect(jsonPath("$.lastName").value(requestObject2.getOrDefault("lastName", (String) JsonPath.read(beforeUpdate, "$.lastName"))))
		.andExpect(jsonPath("$.nickname").value(requestObject2.getOrDefault("nickname", (String) JsonPath.read(beforeUpdate, "$.nickname"))))
		.andExpect(jsonPath("$.salutation").value(requestObject2.getOrDefault("salutation", (String) JsonPath.read(beforeUpdate, "$.salutation"))))
		.andExpect(jsonPath("$.gender").value(requestObject2.getOrDefault("gender", (String) JsonPath.read(beforeUpdate, "$.gender"))))
		.andExpect(jsonPath("$.religion").value(requestObject2.getOrDefault("religion", (String) JsonPath.read(beforeUpdate, "$.religion"))))
		.andExpect(jsonPath("$.type").value(requestObject2.getOrDefault("type", (String) JsonPath.read(beforeUpdate, "$.type"))))
		.andExpect(jsonPath("$.dateOfBirth").value(requestObject2.getOrDefault("dateOfBirth", (String) JsonPath.read(beforeUpdate, "$.dateOfBirth"))))
		.andExpect(jsonPath("$.placeOfBirth").value(requestObject2.getOrDefault("placeOfBirth", (String) JsonPath.read(beforeUpdate, "$.placeOfBirth"))))
		.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject2.getOrDefault("socialSecurityNumber", (String) JsonPath.read(beforeUpdate, "$.socialSecurityNumber"))))
		.andExpect(jsonPath("$.socialSecurityDate").value(requestObject2.getOrDefault("socialSecurityDate", (String) JsonPath.read(beforeUpdate, "$.socialSecurityDate"))))
		;
		
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
		.andExpect(jsonPath("$.name").value("Absolute"))
		.andExpect(jsonPath("$.mailingName").value("Absolute"))
		.andExpect(jsonPath("$.description").value(""))
		.andExpect(jsonPath("$.firstName").value(""))
		.andExpect(jsonPath("$.lastName").value(""))
		.andExpect(jsonPath("$.salutation").value(""))
		.andExpect(jsonPath("$.gender").value(""))
		.andExpect(jsonPath("$.religion").value(""))
		.andExpect(jsonPath("$.socialSecurityNumber").value(""))
		.andExpect(jsonPath("$.dateOfBirth").value(Matchers.nullValue()))
		.andExpect(jsonPath("$.type").value("C"))
		;
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$._embedded.contacts").exists());
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(newId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(newId.getSequence()))
		.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", "")))
		.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", "")))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
		.andExpect(jsonPath("$.firstName").value(requestObject.getOrDefault("firstName", "")))
		.andExpect(jsonPath("$.middleName").value(requestObject.getOrDefault("middleName", "")))
		.andExpect(jsonPath("$.lastName").value(requestObject.getOrDefault("lastName", "")))
		.andExpect(jsonPath("$.nickname").value(requestObject.getOrDefault("nickname", "")))
		.andExpect(jsonPath("$.salutation").value(requestObject.getOrDefault("salutation", "")))
		.andExpect(jsonPath("$.gender").value(requestObject.getOrDefault("gender", "")))
		.andExpect(jsonPath("$.religion").value(requestObject.getOrDefault("religion", "")))
		.andExpect(jsonPath("$.type").value(requestObject.getOrDefault("type", "")))
		.andExpect(jsonPath("$.dateOfBirth").value(requestObject.getOrDefault("dateOfBirth", null)))
		.andExpect(jsonPath("$.placeOfBirth").value(requestObject.getOrDefault("placeOfBirth", "")))
		.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject.getOrDefault("socialSecurityNumber", "")))
		.andExpect(jsonPath("$.socialSecurityDate").value(requestObject.getOrDefault("socialSecurityDate", null)))
		;
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		action
		.andExpect(jsonPath("$.addressNumber").value(existingId.getAddressNumber()))
		.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
		.andExpect(jsonPath("$.name").value(requestObject.getOrDefault("name", (String) JsonPath.read(beforeUpdateJson, "$.name"))))
		.andExpect(jsonPath("$.mailingName").value(requestObject.getOrDefault("mailingName", (String) JsonPath.read(beforeUpdateJson, "$.mailingName"))))
		.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", (String) JsonPath.read(beforeUpdateJson, "$.description"))))
		.andExpect(jsonPath("$.firstName").value(requestObject.getOrDefault("firstName", (String) JsonPath.read(beforeUpdateJson, "$.firstName"))))
		.andExpect(jsonPath("$.middleName").value(requestObject.getOrDefault("middleName", (String) JsonPath.read(beforeUpdateJson, "$.middleName"))))
		.andExpect(jsonPath("$.lastName").value(requestObject.getOrDefault("lastName", (String) JsonPath.read(beforeUpdateJson, "$.lastName"))))
		.andExpect(jsonPath("$.nickname").value(requestObject.getOrDefault("nickname", (String) JsonPath.read(beforeUpdateJson, "$.nickname"))))
		.andExpect(jsonPath("$.salutation").value(requestObject.getOrDefault("salutation", (String) JsonPath.read(beforeUpdateJson, "$.salutation"))))
		.andExpect(jsonPath("$.gender").value(requestObject.getOrDefault("gender", (String) JsonPath.read(beforeUpdateJson, "$.gender"))))
		.andExpect(jsonPath("$.religion").value(requestObject.getOrDefault("religion", (String) JsonPath.read(beforeUpdateJson, "$.religion"))))
		.andExpect(jsonPath("$.type").value(requestObject.getOrDefault("type", (String) JsonPath.read(beforeUpdateJson, "$.type"))))
		.andExpect(jsonPath("$.dateOfBirth").value(requestObject.getOrDefault("dateOfBirth", (String) JsonPath.read(beforeUpdateJson, "$.dateOfBirth"))))
		.andExpect(jsonPath("$.placeOfBirth").value(requestObject.getOrDefault("placeOfBirth", (String) JsonPath.read(beforeUpdateJson, "$.placeOfBirth"))))
		.andExpect(jsonPath("$.socialSecurityNumber").value(requestObject.getOrDefault("socialSecurityNumber", (String) JsonPath.read(beforeUpdateJson, "$.socialSecurityNumber"))))
		.andExpect(jsonPath("$.socialSecurityDate").value(requestObject.getOrDefault("socialSecurityDate", (String) JsonPath.read(beforeUpdateJson, "$.socialSecurityDate"))))
		;
	}


}
