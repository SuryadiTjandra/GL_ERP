package ags.goldenlionerp.apiTests.purchase;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderPK;

public class PurchaseOrderApiTest extends ApiTestBase<PurchaseOrderPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/purchaseOrders/";
	}

	@Override
	protected PurchaseOrderPK existingId() {
		return new PurchaseOrderPK("11000", 180400043, "OP");
	}

	@Override
	protected PurchaseOrderPK newId() {
		return new PurchaseOrderPK("11000", 123456789, "OP");
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.purchaseOrderNumber").value(existingId.getPurchaseOrderNumber()))
			.andExpect(jsonPath("$.purchaseOrderType").value(existingId.getPurchaseOrderType()))
			
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.orderDate").value(LocalDate.of(2018, 4, 13).toString()))
			.andExpect(jsonPath("$.promisedDeliveryDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.vendorId").value("2810"))
			.andExpect(jsonPath("$.receiverId").value("2810"))
			.andExpect(jsonPath("$.transactionCurrency").value("IDR"))
			.andExpect(jsonPath("$.exchangeRate").value(1.00))
			.andExpect(jsonPath("$.paymentTermCode").value("N45"))
			.andExpect(jsonPath("$.taxCode").value("INCL"))
			.andExpect(jsonPath("$.description").value(""))
			.andExpect(jsonPath("$.customerId").value(""))
			
			.andExpect(jsonPath("$.details.length()").value(2))
			.andExpect(jsonPath("$.details[0].itemNumber").value("BRT.LASER-MFCL2740DW"))
			.andExpect(jsonPath("$.details[1].itemNumber").value("BRT.LASER-DCP1616NW"))
			.andExpect(jsonPath("$.details[0].locationId").value("GDU"))
			.andExpect(jsonPath("$.details[1].locationId").value("GDU"))
			.andExpect(jsonPath("$.details[0].orderQuantity").value(3))
			.andExpect(jsonPath("$.details[1].orderQuantity").value(5))
		;
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.purchaseOrders").exists())
			.andExpect(jsonPath("$._embedded.purchaseOrders[0].details").exists())
		;
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		fail();
	}
	
	
	@Override
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		performer.performPatch(baseUrl + existingId, requestObject)
				.andExpect(status().isMethodNotAllowed());
	}
	
	@Override
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		performer.performDelete(baseUrl + existingId)
				.andExpect(status().isMethodNotAllowed());
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		// not applicable
	}

}
