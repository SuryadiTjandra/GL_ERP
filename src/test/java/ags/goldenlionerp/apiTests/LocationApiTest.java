package ags.goldenlionerp.apiTests;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.masterdata.location.LocationMaster;
import ags.goldenlionerp.masterdata.location.LocationMasterPK;

public class LocationApiTest extends ApiTestBase<LocationMasterPK>{

	@Override
	Map<String, Object> requestObject() {
		Map<String, Object> map = new HashMap<>();
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
	
	private String locationId(Map<String, Object> requestObject) {
		return LocationMaster.locationId(
				(String) requestObject.get("warehouseCode"),
				(String) requestObject.get("aisle"),
				(String) requestObject.get("row"),
				(String) requestObject.get("column")
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
	@Rollback
	public void updatePutCollectionOnAssociation() throws Exception{
		String buAssociationUrl = "/api/businessUnits/{id}/locations".replace("{id}", existingId.getBusinessUnitId());
		
		LocationMaster loc = em.find(LocationMaster.class, existingId);
		Map<String, Object> requestObject1 = new HashMap<>();
		requestObject1.put("warehouseCode", loc.getWarehouseCode());
		requestObject1.put("aisle", loc.getAisle());
		requestObject1.put("row", loc.getRow());
		requestObject1.put("column", loc.getColumn());
		requestObject1.put("description", "TEST1");
		requestObject1.put("computerId", "GREEN");
		
		Map<String, Object> requestObject2 = new HashMap<>();
		requestObject2.put("warehouseCode", "whc");
		requestObject2.put("aisle", "ais");
		requestObject2.put("row", "row");
		requestObject2.put("column", "col");
		requestObject2.put("description", "TEST2");
		requestObject2.put("computerId", "RED");
		
		Map<String, Object> requestObject3 = new HashMap<>();
		requestObject3.put("warehouseCode", "ABC");
		requestObject3.put("aisle", "DEF");
		requestObject3.put("column", "JKL");
		requestObject3.put("description", "TEST3");
		requestObject3.put("businessUnitId", "140");
		requestObject3.put("lastUpdateTime", "12:34:56");
		requestObject3.put("computerId", "BLUE");
		
		Collection<Map<String, Object>> requests = Arrays.asList(requestObject1, requestObject2, requestObject3);
		
		mockMvc.perform(put(buAssociationUrl)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requests)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		em.flush();
		em.clear();
		
		mockMvc.perform(get(buAssociationUrl))
				.andExpect(jsonPath("$..locations.length()").value(3));
		
		LocationMasterPK pk1 = new LocationMasterPK(loc.getBusinessUnitId(), loc.getLocationId());
		String res1 = mockMvc.perform(get(baseUrl + pk1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.businessUnitId").value(loc.getBusinessUnitId()))
				.andExpect(jsonPath("$.locationId").value(loc.getLocationId()))
				.andExpect(jsonPath("$.warehouseCode").value(loc.getWarehouseCode()))
				.andExpect(jsonPath("$.description").value(requestObject1.get("description")))
				.andReturn().getResponse().getContentAsString();
		assertUpdateInfo(res1);
		
		LocationMasterPK pk2 = new LocationMasterPK(existingId.getBusinessUnitId(), locationId(requestObject2));
		String res2 = mockMvc.perform(get(baseUrl + pk2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.businessUnitId").value(loc.getBusinessUnitId()))
				.andExpect(jsonPath("$.locationId").value(pk2.getLocationId()))
				.andExpect(jsonPath("$.warehouseCode").value(requestObject2.get("warehouseCode")))
				.andExpect(jsonPath("$.description").value(requestObject2.get("description")))
				.andReturn().getResponse().getContentAsString();
		assertCreationInfo(res2);
		
		LocationMasterPK pk3 = new LocationMasterPK(existingId.getBusinessUnitId(), locationId(requestObject3));
		String res3 = mockMvc.perform(get(baseUrl + pk3))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.businessUnitId").value(loc.getBusinessUnitId()))
				.andExpect(jsonPath("$.locationId").value(pk3.getLocationId()))
				.andExpect(jsonPath("$.warehouseCode").value(requestObject3.get("warehouseCode")))
				.andExpect(jsonPath("$.description").value(requestObject3.get("description")))
				.andReturn().getResponse().getContentAsString();
		assertCreationInfo(res3);
	}

	
}
