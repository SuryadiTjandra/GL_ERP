package ags.goldenlionerp.apiTests;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.location.LocationMaster;
import ags.goldenlionerp.masterdata.location.LocationMasterPK;

public class LocationApiTest extends ApiTestBase<LocationMasterPK>{

	@Override
	Map<String, String> requestObject() {
		Map<String, String> map = new HashMap<>();
		map.put("businessUnitId", "110");
		map.put("locationId", "QWERTY");
		map.put("warehouseCode", "XYZ");
		map.put("aisle", "9");
		map.put("row", "3");
		map.put("column", "1");
		map.put("inputUserId", "Meong");
		map.put("description", "Test Location");
		return map;
	}

	@Override
	String baseUrl() {
		return "/api/locations/";
	}

	@Override
	LocationMasterPK existingId() {
		return new LocationMasterPK("120", "GDT");
	}

	@Override
	LocationMasterPK newId() {
		return new LocationMasterPK("110", locationId(requestObject()));
	}
	
	@Test
	@Override
	public void getTestSingle() throws Exception {
		mockMvc.perform(get(baseUrl + existingId).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
				.andExpect(jsonPath("$.locationId").value(existingId.getLocationId()))
				.andExpect(jsonPath("$.description").value("Gudang THR"));
		
	}

	@Test
	@Override
	public void getTestCollection() throws Exception {
		mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
				//.andDo(res -> System.out.println(res.getResponse().getContentAsString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..locations[0].businessUnitId").value("110"))
				.andExpect(jsonPath("$..locations[0].description").value("TEST"))
				.andExpect(jsonPath("$..locations.length()").value(3))
				.andReturn().getResponse().getContentAsString();
	}

	@Override
	@Test
	@Rollback
	public void createTestWithPost() throws Exception {
		assumeNotExists(baseUrl + newId);
		
		HttpServletResponse response = mockMvc.perform(post(baseUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse();
		
		String newLocation = response.getHeader(HttpHeaders.LOCATION);
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(newLocation))
							.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(jsonPath("$.businessUnitId").value(newId.getBusinessUnitId()))
							.andExpect(jsonPath("$.locationId").value(locationId(requestObject)))
							.andExpect(jsonPath("$.description").value(requestObject.get("description")))
							.andExpect(jsonPath("$.warehouseCode").value(requestObject.get("warehouseCode")))
							.andExpect(jsonPath("$.aisle").value(requestObject.get("aisle")))
							.andReturn().getResponse().getContentAsString();
		
		assertCreationInfo(getResult);
		
	}
	
	private String locationId(Map<String, String> requestObject) {
		return LocationMaster.locationId(
				requestObject.get("warehouseCode"),
				requestObject.get("aisle"),
				requestObject.get("row"),
				requestObject.get("column")
		);
	}

	@Override
	@Test
	@Rollback
	public void createTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + newId)
						.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Override
	@Test
	@Rollback
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(get(baseUrl + existingId))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

	@Override
	@Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String beforePatch = mockMvc.perform(get(baseUrl + existingId))
								.andReturn().getResponse().getContentAsString();
		
		
		mockMvc.perform(patch(baseUrl + existingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(requestObject)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		String getResult = mockMvc.perform(get(baseUrl + existingId))
							.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
							.andExpect(jsonPath("$.businessUnitId").value(existingId.getBusinessUnitId()))
							.andExpect(jsonPath("$.locationId").value(existingId.getLocationId()))
							.andExpect(jsonPath("$.warehouseCode").value((String) JsonPath.read(beforePatch, "$.warehouseCode")))
							.andExpect(jsonPath("$.aisle").value((String) JsonPath.read(beforePatch, "$.aisle")))
							.andExpect(jsonPath("$.description").value(requestObject.get("description")))
							.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(getResult, beforePatch);
			
	}

	@Override
	@Test @Rollback
	public void updateTestWithPut() throws Exception {
		mockMvc.perform(put(baseUrl + existingId)
				.content(mapper.writeValueAsString(requestObject)))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}
	
	@Test
	public void updatePutCollectionOnAssociation() throws Exception{
		fail();
	}

	
}
