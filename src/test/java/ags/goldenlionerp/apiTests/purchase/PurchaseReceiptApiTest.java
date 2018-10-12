package ags.goldenlionerp.apiTests.purchase;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.beans.BeanDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

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
	
	@Test
	public void getSameReceiptTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		String receiptJson = performer.performGet(baseUrl + existingId)
								.andReturn().getResponse().getContentAsString();
		ReadContext ctx = JsonPath.parse(receiptJson);
		String url = ctx.read("$._links.sameReceipt.href");
		String sameBatchReceipts = performer.performGet(url)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.purchaseReceipts.length()").value(8))
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.companyId != %s)]", (String) ctx.read("$.companyId") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.purchaseReceiptNumber != %d)]", (Integer) ctx.read("$.purchaseReceiptNumber") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.purchaseReceiptType != '%s')]", (String) ctx.read("$.purchaseReceiptType") ).isEmpty())
				//.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.sequence == %d)]", (Integer) ctx.read("$.sequence") ).value()))
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.batchNumber != %d)]", (Integer) ctx.read("$.batchNumber") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.businessUnitId != '%s')]", (String) ctx.read("$.businessUnitId") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.documentDate != '%s')]", (String) ctx.read("$.documentDate") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.vendorId != '%s')]", (String) ctx.read("$.vendorId") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.customerOrderNumber != '%s')]", (String) ctx.read("$.customerOrderNumber") ).isEmpty())
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.userReservedText != '%s')]", (String) ctx.read("$.userReservedText") ).isEmpty())
				.andReturn().getResponse().getContentAsString();
		
		String x = JsonPath.read(sameBatchReceipts, String.format("$._embedded.purchaseReceipts[?(@.sequence == %d)]", (Integer) ctx.read("$.sequence"))).toString();
		assertTrue((Integer) JsonPath.read(x, "$.length()") == 1);
		
		
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
