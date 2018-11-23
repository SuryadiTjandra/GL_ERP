package ags.goldenlionerp.apiTests.purchase;

import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.ObjectUtils;

import com.jayway.jsonpath.JsonPath;

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
		Map<String, Object> poReq = poTest.requestObject();
		poReq.put("vendorId", "2814");
		
		List<Map<String, Object>> poDets = new ArrayList<>((List<Map<String, Object>>) poReq.get("details"));
		Map<String, Object> serialPoDet = new HashMap<>();
		serialPoDet.put("itemCode", "ACC.BROTHER-LT6505");
		serialPoDet.put("quantity", 5);
		serialPoDet.put("unitCost", 100);
		poDets.add(serialPoDet);
		poReq.put("details", poDets);
		
		String createdPoUrl = performer.performPost(poTest.baseUrl(), poReq)
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
		
		Map<String, Object> detail2 = new HashMap<>();
		detail2.put("purchaseOrderNumber", poDetails.get(2).get("purchaseOrderNumber"));
		detail2.put("purchaseOrderType", poDetails.get(2).get("purchaseOrderType"));
		detail2.put("purchaseOrderSequence", poDetails.get(2).get("purchaseOrderSequence"));
		detail2.put("quantity", 3.0);
		detail2.put("serialNumbers", Arrays.asList("SERNO1", "SERNO2", "SERNO3"));
		details.add(detail2);
		
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
		map.put("vendorId", "2814");
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
			.andExpect(jsonPath("$.documentDate").value(LocalDate.of(2018, 4, 27).toString()))
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
		
		//assert that the quantities on the purchase orders have been changed
		performer.performGet(((Map<String, Map<String, Object>>) poDetails.get(0).get("_links")).get("order").get("href").toString())
				.andExpect(jsonPath("$.details[0].quantity").value(poDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].receivedQuantity").value(requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].openQuantity").value((double)poDetails.get(0).get("quantity") - (double)requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[1].quantity").value(poDetails.get(1).get("quantity")))
				.andExpect(jsonPath("$.details[1].receivedQuantity").value(requestDetails.get(1).get("quantity")))
				.andExpect(jsonPath("$.details[1].openQuantity").value((double)poDetails.get(1).get("quantity") - (double)requestDetails.get(1).get("quantity")));
		
		//assert new lot masters are created
		String lotUrl = JsonPath.read(action.andReturn().getResponse().getContentAsString(), "$.details[3]._links.serialNumbers");
				//"/api/lots?pk.businessUnitId=" + requestObject.get("businessUnitId") + "&pk.itemCode=" + poDetails.get(2).get("itemCode");
		String[] serialNumbers = ((List<String>) requestDetails.get(2).get("serialNumbers")).toArray(new String[3]);
		performer.performGet(lotUrl)
				.andDo(print())
				.andExpect(jsonPath("$._embedded.lots[*].serialLotNo").value(Matchers.hasItems(serialNumbers)))
				.andExpect(jsonPath("$._embedded.lots.length()").value(3))
				.andExpect(jsonPath("$._embedded.lots[0].itemCode").value(poDetails.get(2).get("itemCode")))
		;	
		
		//assert new item transactions are created for items with type stock
		String itemtransUrl = "/api/itemTransactions/";
		performer.performGet(itemtransUrl + newId())
				.andDo(print())
				.andExpect(jsonPath("$.details.length()").value(2))
				.andExpect(jsonPath("$.details[0].companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.details[0].documentNumber").value(requestObject.get("purchaseReceiptNumber")))
				.andExpect(jsonPath("$.details[0].documentType").value(requestObject.get("purchaseReceiptType")))
				.andExpect(jsonPath("$.details[0].businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.details[0].documentDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[0].glDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[0].description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.details[0].sequence").value(10))
				.andExpect(jsonPath("$.details[0].quantity").value(requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].itemCode").value(poDetails.get(0).get("itemCode")))
				.andExpect(jsonPath("$.details[0].locationId").value(poDetails.get(0).get("locationId")))
				.andExpect(jsonPath("$.details[0].serialLotNo").value(poDetails.get(0).get("serialLotNo")))
				.andExpect(jsonPath("$.details[0].itemDescription").value(poDetails.get(0).get("description")))
				.andExpect(jsonPath("$.details[0].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", poDetails.get(0).get("unitOfMeasure"))))
				.andExpect(jsonPath("$.details[0].primaryUnitOfMeasure").value(poDetails.get(0).get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[0].secondaryTransactionQuantity").value(requestDetails.get(0).getOrDefault("secondaryTransactionQuantity", 0.0)))
				.andExpect(jsonPath("$.details[0].secondaryUnitOfMeasure").value(poDetails.get(0).get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[0].unitCost").value(poDetails.get(0).get("unitCost")))
				.andExpect(jsonPath("$.details[0].extendedCost").value(((double)poDetails.get(0).get("unitCost")) * ((double) requestDetails.get(0).get("quantity"))))
				.andExpect(jsonPath("$.details[0].businessPartnerId").value(requestObject.get("vendorId")))
				.andExpect(jsonPath("$.details[0].fromOrTo").value("F"))
				.andExpect(jsonPath("$.details[0].orderNumber").value(poDetails.get(0).get("purchaseOrderNumber")))
				.andExpect(jsonPath("$.details[0].orderType").value(poDetails.get(0).get("purchaseOrderType")))
				.andExpect(jsonPath("$.details[0].orderSequence").value(poDetails.get(0).get("purchaseOrderSequence")))
				
				.andExpect(jsonPath("$.details[1].companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.details[1].documentNumber").value(requestObject.get("purchaseReceiptNumber")))
				.andExpect(jsonPath("$.details[1].documentType").value(requestObject.get("purchaseReceiptType")))
				.andExpect(jsonPath("$.details[1].businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.details[1].documentDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[1].glDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[1].description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.details[1].sequence").value(30))
				.andExpect(jsonPath("$.details[1].quantity").value(requestDetails.get(2).get("quantity")))
				.andExpect(jsonPath("$.details[1].itemCode").value(poDetails.get(2).get("itemCode")))
				.andExpect(jsonPath("$.details[1].locationId").value(poDetails.get(2).get("locationId")))
				.andExpect(jsonPath("$.details[1].serialLotNo").value(poDetails.get(2).get("serialLotNo")))
				.andExpect(jsonPath("$.details[1].itemDescription").value(poDetails.get(2).get("description")))
				.andExpect(jsonPath("$.details[1].unitOfMeasure").value(requestDetails.get(2).getOrDefault("unitOfMeasure", poDetails.get(2).get("unitOfMeasure"))))
				.andExpect(jsonPath("$.details[1].primaryUnitOfMeasure").value(poDetails.get(2).get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[1].secondaryTransactionQuantity").value(requestDetails.get(2).getOrDefault("secondaryTransactionQuantity", 0.0)))
				.andExpect(jsonPath("$.details[1].secondaryUnitOfMeasure").value(poDetails.get(2).get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[1].unitCost").value(poDetails.get(2).get("unitCost")))
				.andExpect(jsonPath("$.details[1].extendedCost").value(
						((double)poDetails.get(2).get("unitCost")) 
						* 
						((double) requestDetails.get(2).get("quantity"))))
				.andExpect(jsonPath("$.details[1].businessPartnerId").value(requestObject.get("vendorId")))
				.andExpect(jsonPath("$.details[1].fromOrTo").value("F"))
				.andExpect(jsonPath("$.details[1].orderNumber").value(poDetails.get(2).get("purchaseOrderNumber")))
				.andExpect(jsonPath("$.details[1].orderType").value(poDetails.get(2).get("purchaseOrderType")))
				.andExpect(jsonPath("$.details[1].orderSequence").value(poDetails.get(2).get("purchaseOrderSequence")))
				;
		//fail();// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		//header and existing details are unchanged
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
			.andExpect(jsonPath("$.documentDate").value(LocalDate.of(2018, 4, 27).toString()))
			.andExpect(jsonPath("$.details[5].itemCode").value("HP.LAPTOP-1XE24PA#AR6"))
			.andExpect(jsonPath("$.details[5].quantity").value(15.0))
			.andExpect(jsonPath("$.details[5].unitOfMeasure").value("UNT"))
			.andExpect(jsonPath("$.details[5]._links.self.href").exists());
		
		List<Map<String, Object>> requestDetails = (List<Map<String, Object>>) requestObject.get("details");
		//requested details are added to the bottom of the detail list, with requested header info changed to match the existing header info
		action
			.andExpect(jsonPath("$.details.length()").value(11))
			.andExpect(jsonPath("$.details[8].companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.details[8].purchaseReceiptNumber").value(existingId.getPurchaseReceiptNumber()))
			.andExpect(jsonPath("$.details[8].purchaseReceiptType").value(existingId.getPurchaseReceiptType()))
			.andExpect(jsonPath("$.details[8].sequence").value(90))
			.andExpect(jsonPath("$.details[8].businessUnitId").value("110"))
			.andExpect(jsonPath("$.details[8].batchNumber").value(133))
			.andExpect(jsonPath("$.details[8].vendorId").value("2814"))
			.andExpect(jsonPath("$.details[8].customerOrderNumber").value("4522094772"))
		//other details match the request (+ completed fields)
			.andExpect(jsonPath("$.details[8].purchaseOrderNumber").value(poDetails.get(0).get("purchaseOrderNumber")))
			.andExpect(jsonPath("$.details[8].purchaseOrderType").value(poDetails.get(0).get("purchaseOrderType")))
			.andExpect(jsonPath("$.details[8].purchaseOrderSequence").value(poDetails.get(0).get("purchaseOrderSequence")))
			.andExpect(jsonPath("$.details[8].quantity").value(requestDetails.get(0).get("quantity")))
			.andExpect(jsonPath("$.details[8].itemCode").value(poDetails.get(0).get("itemCode")))
			.andExpect(jsonPath("$.details[8].baseCurrency").value(poDetails.get(0).get("baseCurrency")))
			.andExpect(jsonPath("$.details[8].transactionCurrency").value(poDetails.get(0).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[8].exchangeRate").value(poDetails.get(0).get("exchangeRate")))
			.andExpect(jsonPath("$.details[8].locationId").value(poDetails.get(0).get("locationId")))
			.andExpect(jsonPath("$.details[8].serialLotNo").value(poDetails.get(0).get("serialLotNo")))
			.andExpect(jsonPath("$.details[8].itemDescription").value(poDetails.get(0).get("description")))
			.andExpect(jsonPath("$.details[8].lineType").value(poDetails.get(0).get("lineType")))
			.andExpect(jsonPath("$.details[8].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", poDetails.get(0).get("unitOfMeasure"))))
			//.andExpect(jsonPath("$.detail[8].unitConversionFactor").value(poDetails.get(0).get("unitConversionFactor")))
			//.andExpect(jsonPath("$.details[8].primaryTransactionQuantity").value(poDetails.get(0).get("primaryTransactionQuantity")))
			.andExpect(jsonPath("$.details[8].primaryUnitOfMeasure").value(poDetails.get(0).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[8].secondaryTransactionQuantity").value(requestDetails.get(0).getOrDefault("secondaryTransactionQuantity", 0.0)))
			.andExpect(jsonPath("$.details[8].secondaryUnitOfMeasure").value(poDetails.get(0).get("secondaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[8].unitCost").value(poDetails.get(0).get("unitCost")))
			.andExpect(jsonPath("$.details[8].extendedCost").value(((double)poDetails.get(0).get("unitCost")) * ((double) requestDetails.get(0).get("quantity"))))
			.andExpect(jsonPath("$.details[8].taxBase").value( ((double)requestDetails.get(0).get("quantity")) / ((double)poDetails.get(0).get("quantity")) * ((double)poDetails.get(0).get("taxBase")) ))
			.andExpect(jsonPath("$.details[8].taxAmount").value( ((double)requestDetails.get(0).get("quantity")) / ((double)poDetails.get(0).get("quantity")) * ((double)poDetails.get(0).get("taxAmount")) ))
			.andExpect(jsonPath("$.details[8].lastStatus").value("400"))
			.andExpect(jsonPath("$.details[8].nextStatus").value(ObjectUtils.isEmpty(poDetails.get(0).get("landedCostRule")) ? "440" : "425" ))
			//.andExpect(jsonPath("$.details[8].receiptDate").value(requestObject.computeIfPresent("documentDate", (k,v) -> v.toString())))
			.andExpect(jsonPath("$.details[8].paymentTermCode").value(poDetails.get(0).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[8].taxCode").value(poDetails.get(0).get("taxCode")))
			.andExpect(jsonPath("$.details[8].taxAllowance").value(poDetails.get(0).get("taxAllowance")))
			.andExpect(jsonPath("$.details[8].taxRate").value(poDetails.get(0).get("taxRate")))
			.andExpect(jsonPath("$.details[8].discountCode").value(poDetails.get(0).get("discountCode")))
			.andExpect(jsonPath("$.details[8].discountRate").value(poDetails.get(0).get("discountRate")))
			.andExpect(jsonPath("$.details[8].unitDiscountCode").value(poDetails.get(0).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[8].unitDiscountRate").value(poDetails.get(0).get("unitDiscountRate")))
		;
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

	@SuppressWarnings("unchecked")
	@Test
	public void voidReceiptTest() throws Exception {
		//post new receipts
		String location = performer.performPost(baseUrl, requestObject)
							.andDo(res -> assumeTrue(res.getResponse().getStatus() >= 200 && res.getResponse().getStatus() < 300))
							.andReturn().getResponse().getHeader("Location");
		
		refreshData();
		
		//void one of the receipt
		List<Map<String, Object>> requestDetails = (List<Map<String, Object>>) requestObject.get("details");
		requestDetails.get(0).put("sequence", 10);
		requestDetails.get(0).put("voided", true);
		requestDetails.get(1).put("sequence", 20);
		requestDetails.get(2).put("sequence", 30);
		
		
		performer.performPatch(location, requestObject)
					.andExpect(status().is2xxSuccessful());
		
		refreshData();
		
		//assert creation of a new receipt, which is identical to the voided one, but has the negative amount
		performer.performGet(location)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.details.length()").value(4))
				.andExpect(jsonPath("$.details[3].sequence").value(40))
				.andExpect(jsonPath("$.details[3].purchaseOrderNumber").value(poDetails.get(0).get("purchaseOrderNumber")))
				.andExpect(jsonPath("$.details[3].purchaseOrderType").value(poDetails.get(0).get("purchaseOrderType")))
				.andExpect(jsonPath("$.details[3].purchaseOrderSequence").value(poDetails.get(0).get("purchaseOrderSequence")))
				.andExpect(jsonPath("$.details[3].quantity").value(Matchers.closeTo(-1 * (double)requestDetails.get(0).get("quantity"), 0.0001)))
				.andExpect(jsonPath("$.details[3].itemCode").value(poDetails.get(0).get("itemCode")))
				.andExpect(jsonPath("$.details[3].baseCurrency").value(poDetails.get(0).get("baseCurrency")))
				.andExpect(jsonPath("$.details[3].transactionCurrency").value(poDetails.get(0).get("transactionCurrency")))
				.andExpect(jsonPath("$.details[3].exchangeRate").value(poDetails.get(0).get("exchangeRate")))
				.andExpect(jsonPath("$.details[3].locationId").value(poDetails.get(0).get("locationId")))
				.andExpect(jsonPath("$.details[3].serialLotNo").value(poDetails.get(0).get("serialLotNo")))
				.andExpect(jsonPath("$.details[3].itemDescription").value(poDetails.get(0).get("description")))
				.andExpect(jsonPath("$.details[3].lineType").value(poDetails.get(0).get("lineType")))
				.andExpect(jsonPath("$.details[3].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", poDetails.get(0).get("unitOfMeasure"))))
				//.andExpect(jsonPath("$.details[3].unitConversionFactor").value(poDetails.get(0).get("unitConversionFactor")))
				//.andExpect(jsonPath("$.details[3].primaryTransactionQuantity").value(-1 * (double)poDetails.get(0).get("primaryTransactionQuantity")))
				.andExpect(jsonPath("$.details[3].primaryUnitOfMeasure").value(poDetails.get(0).get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[3].secondaryTransactionQuantity").value(Matchers.closeTo(-1 * (double)requestDetails.get(0).getOrDefault("secondaryTransactionQuantity", 0.0), 0.0001)))
				.andExpect(jsonPath("$.details[3].secondaryUnitOfMeasure").value(poDetails.get(0).get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[3].unitCost").value(poDetails.get(0).get("unitCost")))
				.andExpect(jsonPath("$.details[3].extendedCost").value(Matchers.closeTo(-1 * ((double)poDetails.get(0).get("unitCost")) * ((double) requestDetails.get(0).get("quantity")), 0.001)))
				.andExpect(jsonPath("$.details[3].taxBase").value(Matchers.closeTo(-1 * ((double)requestDetails.get(0).get("quantity")) / ((double)poDetails.get(0).get("quantity")) * ((double)poDetails.get(0).get("taxBase")), 0.001 )))
				.andExpect(jsonPath("$.details[3].taxAmount").value(Matchers.closeTo(-1 * ((double)requestDetails.get(0).get("quantity")) / ((double)poDetails.get(0).get("quantity")) * ((double)poDetails.get(0).get("taxAmount")), 0.001 )))
				.andExpect(jsonPath("$.details[3].lastStatus").value("499"))
				.andExpect(jsonPath("$.details[3].nextStatus").value("999"))
				.andExpect(jsonPath("$.details[3].receiptDate").value(requestObject.computeIfPresent("documentDate", (k,v) -> v.toString())))
				.andExpect(jsonPath("$.details[3].paymentTermCode").value(poDetails.get(0).get("paymentTermCode")))
				.andExpect(jsonPath("$.details[3].taxCode").value(poDetails.get(0).get("taxCode")))
				.andExpect(jsonPath("$.details[3].taxAllowance").value(poDetails.get(0).get("taxAllowance")))
				.andExpect(jsonPath("$.details[3].taxRate").value(poDetails.get(0).get("taxRate")))
				.andExpect(jsonPath("$.details[3].discountCode").value(poDetails.get(0).get("discountCode")))
				.andExpect(jsonPath("$.details[3].discountRate").value(poDetails.get(0).get("discountRate")))
				.andExpect(jsonPath("$.details[3].unitDiscountCode").value(poDetails.get(0).get("unitDiscountCode")))
				.andExpect(jsonPath("$.details[3].unitDiscountRate").value(poDetails.get(0).get("unitDiscountRate")))
			//assert the voided receipt has its status changed
				.andExpect(jsonPath("$.details[0].lastStatus").value("499"))
				.andExpect(jsonPath("$.details[0].nextStatus").value("999"));
		
		//assert that the quantities on the purchase orders have been returned to its original quantities
		performer.performGet(((Map<String, Map<String, Object>>) poDetails.get(0).get("_links")).get("order").get("href").toString())
				.andExpect(jsonPath("$.details[0].quantity").value(poDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].receivedQuantity").value(0.0))
				.andExpect(jsonPath("$.details[0].openQuantity").value(poDetails.get(0).get("quantity")));
		
		//assert new item transactions with negative quantity has been created
		String itemtransUrl = "/api/stockTransactions/";
		performer.performGet(itemtransUrl + newId())
				.andDo(print())
				.andExpect(jsonPath("$.details[2].companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.details[2].documentNumber").value(requestObject.get("purchaseReceiptNumber")))
				.andExpect(jsonPath("$.details[2].documentType").value(requestObject.get("purchaseReceiptType")))
				.andExpect(jsonPath("$.details[2].businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.details[2].transactionDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[2].glDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[2].description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.details[2].sequence").value((requestDetails.size()+1) * 10))
				.andExpect(jsonPath("$.details[2].quantity").value(-1 * (double)requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[2].itemCode").value(poDetails.get(0).get("itemCode")))
				.andExpect(jsonPath("$.details[2].locationId").value(poDetails.get(0).get("locationId")))
				.andExpect(jsonPath("$.details[2].serialLotNo").value(poDetails.get(0).get("serialLotNo")))
				.andExpect(jsonPath("$.details[2].itemDescription").value(poDetails.get(0).get("description")));
	}
	
}
