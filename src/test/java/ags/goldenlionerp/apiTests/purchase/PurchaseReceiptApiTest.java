package ags.goldenlionerp.apiTests.purchase;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.ObjectUtils;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.purchase.purchasereceipt.PurchaseReceiptPK;

public class PurchaseReceiptApiTest extends ApiTestBase<PurchaseReceiptPK> {

	Map<String, Object> po;
	List<Map<String, Object>> poDetails;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		PurchaseOrderApiTest poTest = new PurchaseOrderApiTest();
		
		String createdPoUrl = performer.performPost(poTest.baseUrl(), poTest.requestObject())
				.andExpect(status().is2xxSuccessful())
				.andReturn().getResponse().getHeader("Location");
		refreshData();
		
		String createdPoStr = performer.performGet(createdPoUrl)
				.andReturn().getResponse().getContentAsString();
		
		po = (Map<String, Object>) mapper.readValue(createdPoStr, Map.class);
		poDetails = (List<Map<String, Object>>) po.get("details");
		
		List<Map<String, Object>> details = new ArrayList<>();
		Map<String, Object> detail0 = new HashMap<>();
		detail0.put("purchaseOrderNumber", poDetails.get(0).get("purchaseOrderNumber"));
		detail0.put("purchaseOrderType", poDetails.get(0).get("purchaseOrderType"));
		detail0.put("purchaseOrderSequence", poDetails.get(0).get("purchaseOrderSequence"));
		detail0.put("quantity", Double.parseDouble(poDetails.get(0).get("quantity").toString()) - 3);
		details.add(detail0);
		
		Map<String, Object> detail1 = new HashMap<>();
		detail1.put("purchaseOrderNumber", poDetails.get(1).get("purchaseOrderNumber"));
		detail1.put("purchaseOrderType", poDetails.get(1).get("purchaseOrderType"));
		detail1.put("purchaseOrderSequence", poDetails.get(1).get("purchaseOrderSequence"));
		detail1.put("quantity", Double.parseDouble(poDetails.get(1).get("quantity").toString()));
		details.add(detail1);
		requestObject.put("details", details);
	}
	
	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		map.put("companyId", newId().getCompanyId());
		map.put("purchaseReceiptNumber", newId().getPurchaseReceiptNumber());
		map.put("purchaseReceiptType", newId().getPurchaseReceiptType());
		map.put("businessUnitId", "110");
		map.put("documentDate", LocalDate.now());
		map.put("vendorId", "1015");
		map.put("customerOrderNumber", "11223344");
		map.put("description", "TESTRECEIPT");
		
		
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
			.andExpect(jsonPath("$.details").exists())
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.purchaseReceiptNumber").value(existingId.getPurchaseReceiptNumber()))
			.andExpect(jsonPath("$.purchaseReceiptType").value(existingId.getPurchaseReceiptType()))
			.andExpect(jsonPath("$.details[5].sequence").value(existingId.getSequence()))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.batchNumber").value(133))
			.andExpect(jsonPath("$.vendorId").value("2814"))
			.andExpect(jsonPath("$.customerOrderNumber").value("4522094772"))
			.andExpect(jsonPath("$.details[5].itemCode").value("HP.LAPTOP-1XE24PA#AR6"))
			.andExpect(jsonPath("$.details[5].quantity").value(15.0))
			.andExpect(jsonPath("$.details[5].unitOfMeasure").value("UNT"))
			.andExpect(jsonPath("$.details[5]._links.self.href").exists());
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.purchaseReceipts").exists())
			.andExpect(jsonPath("$._embedded.purchaseReceipts[0]._links.sameReceipt.href").exists())
		;
		
	}
	
	/*@Test
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
				.andExpect(jsonPath("$._embedded.purchaseReceipts[?(@.description != '%s')]", (String) ctx.read("$.description") ).isEmpty())
				.andReturn().getResponse().getContentAsString();
		
		String x = JsonPath.read(sameBatchReceipts, String.format("$._embedded.purchaseReceipts[?(@.sequence == %d)]", (Integer) ctx.read("$.sequence"))).toString();
		assertTrue((Integer) JsonPath.read(x, "$.length()") == 1);
		
		
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		List<Map<String, Object>> requestDetails = (List<Map<String, Object>>) requestObject.get("details");
		
		action
			.andExpect(jsonPath("$.companyId").value(requestObject.get("companyId")))
			.andExpect(jsonPath("$.purchaseReceiptNumber").value(requestObject.get("purchaseReceiptNumber")))
			.andExpect(jsonPath("$.purchaseReceiptType").value(requestObject.get("purchaseReceiptType")))
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.vendorId").value(requestObject.get("vendorId")))
			.andExpect(jsonPath("$.documentDate").value(requestObject.get("documentDate").toString()))
			.andExpect(jsonPath("$.customerOrderNumber").value(requestObject.get("customerOrderNumber")))
			.andExpect(jsonPath("$.description").value(requestObject.get("description")))

			.andExpect(jsonPath("$.details[0].sequence").value(10))
			.andExpect(jsonPath("$.details[0].purchaseOrderNumber").value(poDetails.get(0).get("purchaseOrderNumber")))
			.andExpect(jsonPath("$.details[0].purchaseOrderType").value(poDetails.get(0).get("purchaseOrderType")))
			.andExpect(jsonPath("$.details[0].purchaseOrderSequence").value(poDetails.get(0).get("purchaseOrderSequence")))
			.andExpect(jsonPath("$.details[0].quantity").value(requestDetails.get(0).get("quantity")))
			.andExpect(jsonPath("$.details[0].itemCode").value(poDetails.get(0).get("itemCode")))
			.andExpect(jsonPath("$.details[0].baseCurrency").value(poDetails.get(0).get("baseCurrency")))
			.andExpect(jsonPath("$.details[0].transactionCurrency").value(poDetails.get(0).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[0].exchangeRate").value(poDetails.get(0).get("exchangeRate")))
			.andExpect(jsonPath("$.details[0].locationId").value(poDetails.get(0).get("locationId")))
			.andExpect(jsonPath("$.details[0].serialLotNo").value(poDetails.get(0).get("serialLotNo")))
			.andExpect(jsonPath("$.details[0].itemDescription").value(poDetails.get(0).get("description")))
			.andExpect(jsonPath("$.details[0].lineType").value(poDetails.get(0).get("lineType")))
			.andExpect(jsonPath("$.details[0].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", poDetails.get(0).get("unitOfMeasure"))))
			//.andExpect(jsonPath("$.details[0].unitConversionFactor").value(poDetails.get(0).get("unitConversionFactor")))
			//.andExpect(jsonPath("$.details[0].primaryTransactionQuantity").value(poDetails.get(0).get("primaryTransactionQuantity")))
			.andExpect(jsonPath("$.details[0].primaryUnitOfMeasure").value(poDetails.get(0).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[0].secondaryTransactionQuantity").value(requestDetails.get(0).getOrDefault("secondaryTransactionQuantity", 0.0)))
			.andExpect(jsonPath("$.details[0].secondaryUnitOfMeasure").value(poDetails.get(0).get("secondaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[0].unitCost").value(poDetails.get(0).get("unitCost")))
			.andExpect(jsonPath("$.details[0].extendedCost").value(((double)poDetails.get(0).get("unitCost")) * ((double) requestDetails.get(0).get("quantity"))))
			.andExpect(jsonPath("$.details[0].taxBase").value( ((double)requestDetails.get(0).get("quantity")) / ((double)poDetails.get(0).get("quantity")) * ((double)poDetails.get(0).get("taxBase")) ))
			.andExpect(jsonPath("$.details[0].taxAmount").value( ((double)requestDetails.get(0).get("quantity")) / ((double)poDetails.get(0).get("quantity")) * ((double)poDetails.get(0).get("taxAmount")) ))
			.andExpect(jsonPath("$.details[0].lastStatus").value("400"))
			.andExpect(jsonPath("$.details[0].nextStatus").value(ObjectUtils.isEmpty(poDetails.get(0).get("landedCostRule")) ? "440" : "425" ))
			.andExpect(jsonPath("$.details[0].receiptDate").value(requestObject.computeIfPresent("documentDate", (k,v) -> v.toString())))
			.andExpect(jsonPath("$.details[0].paymentTermCode").value(poDetails.get(0).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[0].taxCode").value(poDetails.get(0).get("taxCode")))
			.andExpect(jsonPath("$.details[0].taxAllowance").value(poDetails.get(0).get("taxAllowance")))
			.andExpect(jsonPath("$.details[0].taxRate").value(poDetails.get(0).get("taxRate")))
			.andExpect(jsonPath("$.details[0].discountCode").value(poDetails.get(0).get("discountCode")))
			.andExpect(jsonPath("$.details[0].discountRate").value(poDetails.get(0).get("discountRate")))
			.andExpect(jsonPath("$.details[0].unitDiscountCode").value(poDetails.get(0).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[0].unitDiscountRate").value(poDetails.get(0).get("unitDiscountRate")))
			.andExpect(jsonPath("$.details[0].invoiceNumber").value(0))
			.andExpect(jsonPath("$.details[0].invoiceType").isEmpty())
			.andExpect(jsonPath("$.details[0].invoiceSequence").value(0))
			.andExpect(jsonPath("$.details[0].invoiceDate").isEmpty())
			//.andExpect(jsonPath("$.details[0].originalDocumentNumber").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].originalDocumentType").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].originalDocumentSequence").value(poDetails.get(0).get("exchangeRate")))
			.andExpect(jsonPath("$.details[0].customerOrderNumber").value(requestObject.get("customerOrderNumber")))
			.andExpect(jsonPath("$.details[0].customerOrderDate").value(requestObject.get("customerOrderDate")))
			//.andExpect(jsonPath("$.details[0].vendorInvoiceNumber").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].vendorInvoiceDate").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].taxInvoiceNumber").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].taxInvoiceDate").value(poDetails.get(0).get("serialLotNo")))
			.andExpect(jsonPath("$.details[0].expiredDate").value(requestDetails.get(0).computeIfPresent("expiredDate", (k,v) -> v.toString())))
			//.andExpect(jsonPath("$.details[0].inventoryTransactionType").value(poDetails.get(0).get("exchangeRate")))
			.andExpect(jsonPath("$.details[0].landedCostRecordType").value(ObjectUtils.isEmpty(poDetails.get(0).get("landedCostRule")) ? "" : "1"))
			//.andExpect(jsonPath("$.details[0].routingProcess").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].levelCost").value(poDetails.get(0).get("serialLotNo")))
			.andExpect(jsonPath("$.details[0].categoryCode").value(poDetails.get(0).get("categoryCode")))
			.andExpect(jsonPath("$.details[0].brandCode").value(poDetails.get(0).get("brandCode")))
			.andExpect(jsonPath("$.details[0].typeCode").value(poDetails.get(0).get("typeCode")))
			.andExpect(jsonPath("$.details[0].landedCostRule").value(poDetails.get(0).get("landedCostRule")))
			//.andExpect(jsonPath("$.details[0].conditionOfTransport").value(poDetails.get(0).get("conditionOfTransport")))
			//.andExpect(jsonPath("$.details[0].containerId").value(poDetails.get(0).get("containerId")))
			//.andExpect(jsonPath("$.details[0].portOfDepartureId").value(poDetails.get(0).get("portOfDepartureId")))
			//.andExpect(jsonPath("$.details[0].portOfArrivalId").value(poDetails.get(0).get("portOfArrivalId")))
			//.andExpect(jsonPath("$.details[0].importDeclarationNumber").value(poDetails.get(0).get("importDeclarationNumber")))
			//.andExpect(jsonPath("$.details[0].importDeclarationDate").value(poDetails.get(0).get("importDeclarationDate")))
			
			.andExpect(jsonPath("$.details[1].sequence").value(20))
			.andExpect(jsonPath("$.details[1].purchaseOrderNumber").value(requestDetails.get(1).get("purchaseOrderNumber")))
			.andExpect(jsonPath("$.details[1].purchaseOrderType").value(requestDetails.get(1).get("purchaseOrderType")))
			.andExpect(jsonPath("$.details[1].purchaseOrderSequence").value(requestDetails.get(1).get("purchaseOrderSequence")))
			.andExpect(jsonPath("$.details[1].quantity").value(requestDetails.get(1).get("quantity")))
			.andExpect(jsonPath("$.details[1].itemCode").value(poDetails.get(1).get("itemCode")))
			.andExpect(jsonPath("$.details[1].baseCurrency").value(poDetails.get(1).get("baseCurrency")))
			.andExpect(jsonPath("$.details[1].transactionCurrency").value(poDetails.get(1).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[1].exchangeRate").value(poDetails.get(1).get("exchangeRate")))
			.andExpect(jsonPath("$.details[1].locationId").value(poDetails.get(1).get("locationId")))
			.andExpect(jsonPath("$.details[1].serialLotNo").value(poDetails.get(1).get("serialLotNo")))
			.andExpect(jsonPath("$.details[1].itemDescription").value(poDetails.get(1).get("description")))
			.andExpect(jsonPath("$.details[1].lineType").value(poDetails.get(1).get("lineType")))
			.andExpect(jsonPath("$.details[1].unitOfMeasure").value(requestDetails.get(1).getOrDefault("unitOfMeasure", poDetails.get(1).get("unitOfMeasure"))))
			//.andExpect(jsonPath("$.details[1].unitConversionFactor").value(poDetails.get(1).get("unitConversionFactor")))
			//.andExpect(jsonPath("$.details[1].primaryTransactionQuantity").value(poDetails.get(1).get("primaryTransactionQuantity")))
			.andExpect(jsonPath("$.details[1].primaryUnitOfMeasure").value(poDetails.get(1).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[1].secondaryTransactionQuantity").value(requestDetails.get(1).getOrDefault("secondaryTransactionQuantity", 0.0)))
			.andExpect(jsonPath("$.details[1].secondaryUnitOfMeasure").value(poDetails.get(1).get("secondaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[1].unitCost").value(poDetails.get(1).get("unitCost")))
			.andExpect(jsonPath("$.details[1].extendedCost").value(((double)poDetails.get(1).get("unitCost")) * ((double) requestDetails.get(1).get("quantity"))))
			.andExpect(jsonPath("$.details[1].taxBase").value( ((double)requestDetails.get(1).get("quantity")) / ((double)poDetails.get(1).get("quantity")) * ((double)poDetails.get(1).get("taxBase")) ))
			.andExpect(jsonPath("$.details[1].taxAmount").value( ((double)requestDetails.get(1).get("quantity")) / ((double)poDetails.get(1).get("quantity")) * ((double)poDetails.get(1).get("taxAmount")) ))
			.andExpect(jsonPath("$.details[1].lastStatus").value("400"))
			.andExpect(jsonPath("$.details[1].nextStatus").value(ObjectUtils.isEmpty(poDetails.get(1).get("landedCostRule")) ? "440" : "425"))
			.andExpect(jsonPath("$.details[1].receiptDate").value(requestObject.computeIfPresent("documentDate", (k,v) -> v.toString())))
			.andExpect(jsonPath("$.details[1].paymentTermCode").value(poDetails.get(1).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[1].taxCode").value(poDetails.get(1).get("taxCode")))
			.andExpect(jsonPath("$.details[1].taxAllowance").value(poDetails.get(1).get("taxAllowance")))
			.andExpect(jsonPath("$.details[1].taxRate").value(poDetails.get(1).get("taxRate")))
			.andExpect(jsonPath("$.details[1].discountCode").value(poDetails.get(1).get("discountCode")))
			.andExpect(jsonPath("$.details[1].discountRate").value(poDetails.get(1).get("discountRate")))
			.andExpect(jsonPath("$.details[1].unitDiscountCode").value(poDetails.get(1).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[1].unitDiscountRate").value(poDetails.get(1).get("unitDiscountRate")))
			.andExpect(jsonPath("$.details[1].invoiceNumber").value(0))
			.andExpect(jsonPath("$.details[1].invoiceType").isEmpty())
			.andExpect(jsonPath("$.details[1].invoiceSequence").value(0))
			.andExpect(jsonPath("$.details[1].invoiceDate").isEmpty())
			.andExpect(jsonPath("$.details[1].purchaseOrderNumber").value(poDetails.get(1).get("purchaseOrderNumber")))
			.andExpect(jsonPath("$.details[1].purchaseOrderType").value(poDetails.get(1).get("purchaseOrderType")))
			.andExpect(jsonPath("$.details[1].purchaseOrderSequence").value(poDetails.get(1).get("purchaseOrderSequence")))
			//.andExpect(jsonPath("$.details[1].originalDocumentNumber").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].originalDocumentType").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].originalDocumentSequence").value(poDetails.get(1).get("exchangeRate")))
			.andExpect(jsonPath("$.details[1].customerOrderNumber").value(requestObject.get("customerOrderNumber")))
			.andExpect(jsonPath("$.details[1].customerOrderDate").value(requestObject.get("customerOrderDate")))
			//.andExpect(jsonPath("$.details[1].vendorInvoiceNumber").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].vendorInvoiceDate").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].taxInvoiceNumber").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].taxInvoiceDate").value(poDetails.get(1).get("serialLotNo")))
			.andExpect(jsonPath("$.details[1].expiredDate").value(requestDetails.get(1).get("expiredDate")))
			//.andExpect(jsonPath("$.details[1].inventoryTransactionType").value(poDetails.get(1).get("exchangeRate")))
			.andExpect(jsonPath("$.details[1].landedCostRecordType").value(ObjectUtils.isEmpty(poDetails.get(1).get("landedCostRule")) ? "" : "1"))
			//.andExpect(jsonPath("$.details[1].routingProcess").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].levelCost").value(poDetails.get(1).get("serialLotNo")))
			.andExpect(jsonPath("$.details[1].categoryCode").value(poDetails.get(1).get("categoryCode")))
			.andExpect(jsonPath("$.details[1].brandCode").value(poDetails.get(1).get("brandCode")))
			.andExpect(jsonPath("$.details[1].typeCode").value(poDetails.get(1).get("typeCode")))
			.andExpect(jsonPath("$.details[1].landedCostRule").value(poDetails.get(1).get("landedCostRule")))
			//.andExpect(jsonPath("$.details[1].conditionOfTransport").value(poDetails.get(1).get("conditionOfTransport")))
			//.andExpect(jsonPath("$.details[1].containerId").value(poDetails.get(1).get("containerId")))
			//.andExpect(jsonPath("$.details[1].portOfDepartureId").value(poDetails.get(1).get("portOfDepartureId")))
			//.andExpect(jsonPath("$.details[1].portOfArrivalId").value(poDetails.get(1).get("portOfArrivalId")))
			//.andExpect(jsonPath("$.details[1].importDeclarationNumber").value(poDetails.get(1).get("importDeclarationNumber")))
			//.andExpect(jsonPath("$.details[1].importDeclarationDate").value(poDetails.get(1).get("importDeclarationDate")))
			
			;
		
		performer.performGet(((Map<String, Map<String, Object>>) poDetails.get(0).get("_links")).get("order").get("href").toString())
				.andExpect(jsonPath("$.details[0].quantity").value(poDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].receivedQuantity").value(requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].openQuantity").value((double)poDetails.get(0).get("quantity") - (double)requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[1].quantity").value(poDetails.get(1).get("quantity")))
				.andExpect(jsonPath("$.details[1].receivedQuantity").value(requestDetails.get(1).get("quantity")))
				.andExpect(jsonPath("$.details[1].openQuantity").value((double)poDetails.get(1).get("quantity") - (double)requestDetails.get(1).get("quantity")));
		
		fail();// TODO Auto-generated method stub
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		fail();// TODO Auto-generated method stub
		
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
