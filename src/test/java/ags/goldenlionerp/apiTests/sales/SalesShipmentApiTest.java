package ags.goldenlionerp.apiTests.sales;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.sales.salesshipment.SalesShipmentPK;

public class SalesShipmentApiTest extends ApiTestBase<SalesShipmentPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/salesShipments/";
	}

	@Override
	protected SalesShipmentPK existingId() {
		return new SalesShipmentPK("11000", 180400002, "SI", 20);
	}

	@Override
	protected SalesShipmentPK newId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId().getCompanyId()))
			.andExpect(jsonPath("$.documentNumber").value(existingId().getDocumentNumber()))
			.andExpect(jsonPath("$.documentType").value(existingId().getDocumentType()))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.documentDate").value(LocalDate.of(2018, 4, 9).toString()))
			.andExpect(jsonPath("$.description").value("DO/ATG/2018/IV/4898"))
			.andExpect(jsonPath("$.customerId").value("1479"))
			.andExpect(jsonPath("$.receiverId").value("1479"))
			
			.andExpect(jsonPath("$.details[1].sequence").value(existingId().getSequence()))
			.andExpect(jsonPath("$.details[1].itemCode").value("HP.INKJET - Z4B04A			"))
			.andExpect(jsonPath("$.details[1].itemDescription").value("HP Inktank 315 AIO					"))
			.andExpect(jsonPath("$.details[1].locationId").value("GDU"))
			.andExpect(jsonPath("$.details[1].serialLotNo").isEmpty())
			.andExpect(jsonPath("$.details[1].quantity").value(2))
			.andExpect(jsonPath("$.details[1].unitOfMeasure").value("UNT"))
			.andExpect(jsonPath("$.details[1]._links.self.href").exists());
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
