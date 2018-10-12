package ags.goldenlionerp.apiTests.purchase;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptPK;

public class PurchaseReceiptApiTest extends ApiTestBase<PurchaseReceiptPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/purchaseReceipts/";
	}

	@Override
	protected PurchaseReceiptPK existingId() {
		return new PurchaseReceiptPK("11000", 180400187, "OV", 60);
	}

	@Override
	protected PurchaseReceiptPK newId() {
		return new PurchaseReceiptPK("11000", 99999999, "OV", 10);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.purchaseReceiptNumber").value(existingId.getPurchaseReceiptNumber()))
			.andExpect(jsonPath("$.purchaseReceiptType").value(existingId.getPurchaseReceiptType()))
			.andExpect(jsonPath("$.sequence").value(existingId.getSequence()))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.batchNumber").value(133))
			.andExpect(jsonPath("$.vendorId").value("2814"))
			.andExpect(jsonPath("$.customerOrderNumber").value("4522094772"))
			.andExpect(jsonPath("$.itemCode").value("HP.LAPTOP-1XE24PA#AR6"))
			.andExpect(jsonPath("$.quantity").value(15.0))
			.andExpect(jsonPath("$.unitOfMeasure").value("UNT"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.purchaseReceipts").exists())
			.andExpect(jsonPath("$._embedded.purchaseReceipts[0]._links.sameReceipt.href").exists())
		;
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Test @Rollback
	public void updateTestWithPost() throws Exception {
		assumeExists(baseUrl + existingId);
		
		requestObject.put("companyId", existingId.getCompanyId());
		requestObject.put("purchaseReceiptNumber", existingId().getPurchaseReceiptNumber());
		requestObject.put("purchaseReceiptType", existingId().getPurchaseReceiptType());
		requestObject.put("sequence", existingId().getSequence());
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(status().isConflict());
	}
	
	@Override
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		performer.performDelete(baseUrl + existingId())
				.andExpect(status().isMethodNotAllowed());
	}

	
}
