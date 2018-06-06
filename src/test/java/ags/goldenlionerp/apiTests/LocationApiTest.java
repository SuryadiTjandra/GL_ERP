package ags.goldenlionerp.apiTests;

import java.util.Map;

import ags.goldenlionerp.masterdata.location.LocationMasterPK;

public class LocationApiTest extends ApiTestBase<LocationMasterPK>{

	@Override
	Map<String, String> populateRequestObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String baseUrl() {
		return "/api/locations/";
	}

	@Override
	LocationMasterPK existingId() {
		return new LocationMasterPK("120", "GDU");
	}

	@Override
	LocationMasterPK newId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void getTestSingle() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTestCollection() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTestWithPost() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTestWithPut() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTestWithPatch() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTestWithPut() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
