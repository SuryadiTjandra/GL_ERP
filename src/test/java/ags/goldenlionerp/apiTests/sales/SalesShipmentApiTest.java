package ags.goldenlionerp.apiTests.sales;

import static org.junit.Assume.assumeTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.sales.salesshipment.SalesShipmentPK;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;

public class SalesShipmentApiTest extends ApiTestBase<SalesShipmentPK> {

	List<Map<String, Object>> soDetails;
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		super.setUp();
		String soStr = performer.performGet("/api/salesOrders/" + "11000_181100001_SO")
				.andReturn().getResponse().getContentAsString();
		Map<String, Object> so = mapper.readValue(soStr, Map.class);
		soDetails = (List<Map<String, Object>>) so.get("details");
	}
	
	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId().getCompanyId());
		map.put("documentNumber", newId().getDocumentNumber());
		map.put("documentType", newId().getDocumentType());
		map.put("businessUnitId", "110");
		//map.put("documentDate", LocalDate.of(2018, month, dayOfMonth))
		map.put("customerId", "1479");
		map.put("description", "TEST");
		
		List<Map<String, Object>> details = new ArrayList<>();
		Map<String, Object> det0 = new HashMap<>();
		det0.put("orderNumber", 181100001);
		det0.put("orderType", "SO");
		det0.put("orderSequence", 10);
		det0.put("quantity", 10.0);
		details.add(det0);
		
		Map<String, Object> det1 = new HashMap<>();
		det1.put("orderNumber", 181100001);
		det1.put("orderType", "SO");
		det1.put("orderSequence", 20);
		det1.put("quantity", 0.5);
		det1.put("unitOfMeasure", "MT");
		details.add(det1);
		
		Map<String, Object> det2 = new HashMap<>();
		det2.put("orderNumber", 181100001);
		det2.put("orderType", "SO");
		det2.put("orderSequence", 30);
		det2.put("quantity", 3.0);
		det2.put("serialOrLotNumbers", Arrays.asList("serialNoA", "serialNoB", "serialNoC"));
		details.add(det2);
		
		map.put("details", details);
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
		return new SalesShipmentPK("11000", 99999999, "SI", 10);
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

	@Autowired NextNumberService nnServ;
	@SuppressWarnings("unchecked")
	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		int batchNo = nnServ.peekAtNextNumber(newId().getCompanyId(), "S", YearMonth.from((LocalDate) requestObject.getOrDefault("documentDate", LocalDate.now())))
							.getNextSequence() - 1; //minus one because it has already been incremented when creating
		
		action
			.andExpect(jsonPath("$.companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.documentNumber").value(newId().getDocumentNumber()))
			.andExpect(jsonPath("$.documentType").value(newId().getDocumentType()))
			.andExpect(jsonPath("$.documentDate").value(requestObject.getOrDefault("documentDate", LocalDate.now()).toString()))
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.customerId").value(requestObject.get("customerId")))
			.andExpect(jsonPath("$.receiverId").value(requestObject.getOrDefault("receiverId", requestObject.get("customerId"))))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
			.andExpect(jsonPath("$.batchType").value("S"))
			.andExpect(jsonPath("$.batchNumber").value(batchNo));
		
		List<Map<String, Object>> requestDetails = (List<Map<String, Object>>) requestObject.get("details");
		action
			.andExpect(jsonPath("$.details[0].sequence").value("10"))
			.andExpect(jsonPath("$.details[0].itemCode").value(soDetails.get(0).get("itemCode")))
			.andExpect(jsonPath("$.details[0].itemDescription").value(soDetails.get(0).get("description")))
			.andExpect(jsonPath("$.details[0].locationId").value(requestDetails.get(0).getOrDefault("locationId", soDetails.get(0).get("locationId"))))
			.andExpect(jsonPath("$.details[0].serialLotNo").value(requestDetails.get(0).getOrDefault("serialLotNo", soDetails.get(0).get("serialLotNo"))))
			.andExpect(jsonPath("$.details[0].quantity").value(requestDetails.get(0).get("quantity")))
			.andExpect(jsonPath("$.details[0].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", soDetails.get(0).get("unitOfMeasure"))))
			.andExpect(jsonPath("$.details[0].unitConversionFactor").value(1))
			.andExpect(jsonPath("$.details[0].primaryTransactionQuantity").value(requestDetails.get(0).get("quantity")))
			.andExpect(jsonPath("$.details[0].primaryUnitOfMeasure").value(soDetails.get(0).get("primaryUnitOfMeasure")))
			//.andExpect(jsonPath("$.details[0].secondaryTransactionQuantity").value(requestDetails.get(0).get("quantity")))
			//.andExpect(jsonPath("$.details[0].secondaryUnitOfMeasure").value(soDetails.get(0).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[0].orderNumber").value(requestDetails.get(0).get("orderNumber")))
			.andExpect(jsonPath("$.details[0].orderType").value(requestDetails.get(0).get("orderType")))
			.andExpect(jsonPath("$.details[0].orderSequence").value(requestDetails.get(0).get("orderSequence")))
			.andExpect(jsonPath("$.details[0].baseCurrency").value(soDetails.get(0).get("baseCurrency")))
			.andExpect(jsonPath("$.details[0].transactionCurrency").value(soDetails.get(0).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[0].exchangeRate").value(soDetails.get(0).get("exchangeRate")))
			.andExpect(jsonPath("$.details[0].lineType").value(soDetails.get(0).get("lineType")))
			.andExpect(jsonPath("$.details[0].unitPrice").value(soDetails.get(0).get("unitPrice")))
			.andExpect(jsonPath("$.details[0].extendedPrice").value(soDetails.get(0).get("extendedPrice")))
			.andExpect(jsonPath("$.details[0].unitCost").value(soDetails.get(0).get("unitCost")))
			.andExpect(jsonPath("$.details[0].extendedCost").value(soDetails.get(0).get("extendedCost")))
			.andExpect(jsonPath("$.details[0].taxBase").value(soDetails.get(0).get("taxBase")))
			.andExpect(jsonPath("$.details[0].taxAmount").value(soDetails.get(0).get("taxAmount")))
			.andExpect(jsonPath("$.details[0].profitCenterId").value(soDetails.get(0).get("profitCenterId")))
			.andExpect(jsonPath("$.details[0].paymentTermCode").value(soDetails.get(0).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[0].taxCode").value(soDetails.get(0).get("taxCode")))
			.andExpect(jsonPath("$.details[0].taxAllowance").value(soDetails.get(0).get("taxAllowance")))
			.andExpect(jsonPath("$.details[0].taxRate").value(soDetails.get(0).get("taxRate")))
			.andExpect(jsonPath("$.details[0].guestServiceChargeRate").value(soDetails.get(0).get("guestServiceChargeRate")))
			.andExpect(jsonPath("$.details[0].discountCode").value(soDetails.get(0).get("discountCode")))
			.andExpect(jsonPath("$.details[0].discountRate").value(soDetails.get(0).get("discountRate")))
			.andExpect(jsonPath("$.details[0].unitDiscountCode").value(soDetails.get(0).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[0].unitDiscountRate").value(soDetails.get(0).get("unitDiscountRate")))
			.andExpect(jsonPath("$.details[0].lastStatus").value("560"))
			.andExpect(jsonPath("$.details[0].nextStatus").value("580"))
			.andExpect(jsonPath("$.details[0].invoiceNumber").value(0))
			.andExpect(jsonPath("$.details[0].invoiceType").isEmpty())
			.andExpect(jsonPath("$.details[0].invoiceSequence").value(0))
			.andExpect(jsonPath("$.details[0].invoiceDate").isEmpty())
			//.andExpect(jsonPath("$.details[0].originalDocumentNumber").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].originalDocumentType").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].originalDocumentSequence").value(poDetails.get(0).get("exchangeRate")))
			//.andExpect(jsonPath("$.details[0].customerOrderNumber").value(requestObject.get("customerOrderNumber")))
			//.andExpect(jsonPath("$.details[0].customerOrderDate").value(requestObject.get("customerOrderDate")))
			//.andExpect(jsonPath("$.details[0].vendorInvoiceNumber").value(poDetails.get(0).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[0].vendorInvoiceDate").value(poDetails.get(0).get("serialLotNo")))
			.andExpect(jsonPath("$.details[0].taxInvoiceNumber").isEmpty())
			.andExpect(jsonPath("$.details[0].taxInvoiceDate").isEmpty());
		//others later
		
		action
			.andExpect(jsonPath("$.details[1].sequence").value("20"))
			.andExpect(jsonPath("$.details[1].itemCode").value(soDetails.get(1).get("itemCode")))
			.andExpect(jsonPath("$.details[1].itemDescription").value(soDetails.get(1).get("description")))
			.andExpect(jsonPath("$.details[1].locationId").value(requestDetails.get(1).getOrDefault("locationId", soDetails.get(1).get("locationId"))))
			.andExpect(jsonPath("$.details[1].serialLotNo").value(requestDetails.get(1).getOrDefault("serialLotNo", soDetails.get(1).get("serialLotNo"))))
			.andExpect(jsonPath("$.details[1].quantity").value(requestDetails.get(1).get("quantity")))
			.andExpect(jsonPath("$.details[1].unitOfMeasure").value(requestDetails.get(1).getOrDefault("unitOfMeasure", soDetails.get(1).get("unitOfMeasure"))))
			.andExpect(jsonPath("$.details[1].unitConversionFactor").value(Matchers.closeTo(3.2808, 0.01)))
			.andExpect(jsonPath("$.details[1].primaryTransactionQuantity").value(Matchers.closeTo(1.6404, 0.01)))
			.andExpect(jsonPath("$.details[1].primaryUnitOfMeasure").value(soDetails.get(1).get("primaryUnitOfMeasure")))
			//.andExpect(jsonPath("$.details[1].secondaryTransactionQuantity").value(requestDetails.get(1).get("quantity")))
			//.andExpect(jsonPath("$.details[1].secondaryUnitOfMeasure").value(soDetails.get(1).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[1].orderNumber").value(requestDetails.get(1).get("orderNumber")))
			.andExpect(jsonPath("$.details[1].orderType").value(requestDetails.get(1).get("orderType")))
			.andExpect(jsonPath("$.details[1].orderSequence").value(requestDetails.get(1).get("orderSequence")))
			.andExpect(jsonPath("$.details[1].baseCurrency").value(soDetails.get(1).get("baseCurrency")))
			.andExpect(jsonPath("$.details[1].transactionCurrency").value(soDetails.get(1).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[1].exchangeRate").value(soDetails.get(1).get("exchangeRate")))
			.andExpect(jsonPath("$.details[1].lineType").value(soDetails.get(1).get("lineType")))
			.andExpect(jsonPath("$.details[1].unitPrice").value(soDetails.get(1).get("unitPrice")))
			.andExpect(jsonPath("$.details[1].extendedPrice").value(Matchers.closeTo(1.6404 / (double)soDetails.get(1).get("primaryOrderQuantity") * (double)soDetails.get(1).get("extendedPrice"), 0.01)))
			.andExpect(jsonPath("$.details[1].unitCost").value(soDetails.get(1).get("unitCost")))
			.andExpect(jsonPath("$.details[1].extendedCost").value(Matchers.closeTo(1.6404 / (double)soDetails.get(1).get("primaryOrderQuantity") * (double)soDetails.get(1).get("extendedCost"), 0.01)))
			//.andExpect(jsonPath("$.details[1].taxBase").value(soDetails.get(1).get("taxBase")))
			//.andExpect(jsonPath("$.details[1].taxAmount").value(soDetails.get(1).get("taxAmount")))
			.andExpect(jsonPath("$.details[1].profitCenterId").value(soDetails.get(1).get("profitCenterId")))
			.andExpect(jsonPath("$.details[1].paymentTermCode").value(soDetails.get(1).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[1].taxCode").value(soDetails.get(1).get("taxCode")))
			.andExpect(jsonPath("$.details[1].taxAllowance").value(soDetails.get(1).get("taxAllowance")))
			.andExpect(jsonPath("$.details[1].taxRate").value(soDetails.get(1).get("taxRate")))
			.andExpect(jsonPath("$.details[1].guestServiceChargeRate").value(soDetails.get(1).get("guestServiceChargeRate")))
			.andExpect(jsonPath("$.details[1].discountCode").value(soDetails.get(1).get("discountCode")))
			.andExpect(jsonPath("$.details[1].discountRate").value(soDetails.get(1).get("discountRate")))
			.andExpect(jsonPath("$.details[1].unitDiscountCode").value(soDetails.get(1).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[1].unitDiscountRate").value(soDetails.get(1).get("unitDiscountRate")))
			.andExpect(jsonPath("$.details[1].lastStatus").value("560"))
			.andExpect(jsonPath("$.details[1].nextStatus").value("580"))
			.andExpect(jsonPath("$.details[1].invoiceNumber").value(0))
			.andExpect(jsonPath("$.details[1].invoiceType").isEmpty())
			.andExpect(jsonPath("$.details[1].invoiceSequence").value(0))
			.andExpect(jsonPath("$.details[1].invoiceDate").isEmpty())
			//.andExpect(jsonPath("$.details[1].originalDocumentNumber").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].originalDocumentType").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].originalDocumentSequence").value(poDetails.get(1).get("exchangeRate")))
			//.andExpect(jsonPath("$.details[1].customerOrderNumber").value(requestObject.get("customerOrderNumber")))
			//.andExpect(jsonPath("$.details[1].customerOrderDate").value(requestObject.get("customerOrderDate")))
			//.andExpect(jsonPath("$.details[1].vendorInvoiceNumber").value(poDetails.get(1).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[1].vendorInvoiceDate").value(poDetails.get(1).get("serialLotNo")))
			.andExpect(jsonPath("$.details[1].taxInvoiceNumber").isEmpty())
			.andExpect(jsonPath("$.details[1].taxInvoiceDate").isEmpty());
		
		action
			.andExpect(jsonPath("$.details[2].sequence").value("30"))
			.andExpect(jsonPath("$.details[2].itemCode").value(soDetails.get(2).get("itemCode")))
			.andExpect(jsonPath("$.details[2].itemDescription").value(soDetails.get(2).get("description")))
			.andExpect(jsonPath("$.details[2].locationId").value(requestDetails.get(2).getOrDefault("locationId", soDetails.get(2).get("locationId"))))
			.andExpect(jsonPath("$.details[2].serialLotNo").value(requestDetails.get(2).getOrDefault("serialLotNo", soDetails.get(2).get("serialLotNo"))))
			.andExpect(jsonPath("$.details[2].quantity").value(requestDetails.get(2).get("quantity")))
			.andExpect(jsonPath("$.details[2].unitOfMeasure").value(requestDetails.get(2).getOrDefault("unitOfMeasure", soDetails.get(2).get("unitOfMeasure"))))
			.andExpect(jsonPath("$.details[2].unitConversionFactor").value(1))
			.andExpect(jsonPath("$.details[2].primaryTransactionQuantity").value(requestDetails.get(2).get("quantity")))
			.andExpect(jsonPath("$.details[2].primaryUnitOfMeasure").value(soDetails.get(2).get("primaryUnitOfMeasure")))
			//.andExpect(jsonPath("$.details[2].secondaryTransactionQuantity").value(requestDetails.get(2).get("quantity")))
			//.andExpect(jsonPath("$.details[2].secondaryUnitOfMeasure").value(soDetails.get(2).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[2].orderNumber").value(requestDetails.get(2).get("orderNumber")))
			.andExpect(jsonPath("$.details[2].orderType").value(requestDetails.get(2).get("orderType")))
			.andExpect(jsonPath("$.details[2].orderSequence").value(requestDetails.get(2).get("orderSequence")))
			.andExpect(jsonPath("$.details[2].baseCurrency").value(soDetails.get(2).get("baseCurrency")))
			.andExpect(jsonPath("$.details[2].transactionCurrency").value(soDetails.get(2).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[2].exchangeRate").value(soDetails.get(2).get("exchangeRate")))
			.andExpect(jsonPath("$.details[2].lineType").value(soDetails.get(2).get("lineType")))
			.andExpect(jsonPath("$.details[2].unitPrice").value(soDetails.get(2).get("unitPrice")))
			.andExpect(jsonPath("$.details[2].extendedPrice").value(Matchers.closeTo(3 / (double)soDetails.get(2).get("primaryOrderQuantity") * (double)soDetails.get(2).get("extendedPrice"), 0.01)))
			.andExpect(jsonPath("$.details[2].unitCost").value(soDetails.get(2).get("unitCost")))
			.andExpect(jsonPath("$.details[2].extendedCost").value(Matchers.closeTo(3 / (double)soDetails.get(2).get("primaryOrderQuantity") * (double)soDetails.get(2).get("extendedCost"), 0.01)))
			//.andExpect(jsonPath("$.details[2].taxBase").value(soDetails.get(2).get("taxBase")))
			//.andExpect(jsonPath("$.details[2].taxAmount").value(soDetails.get(2).get("taxAmount")))
			.andExpect(jsonPath("$.details[2].profitCenterId").value(soDetails.get(2).get("profitCenterId")))
			.andExpect(jsonPath("$.details[2].paymentTermCode").value(soDetails.get(2).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[2].taxCode").value(soDetails.get(2).get("taxCode")))
			.andExpect(jsonPath("$.details[2].taxAllowance").value(soDetails.get(2).get("taxAllowance")))
			.andExpect(jsonPath("$.details[2].taxRate").value(soDetails.get(2).get("taxRate")))
			.andExpect(jsonPath("$.details[2].guestServiceChargeRate").value(soDetails.get(2).get("guestServiceChargeRate")))
			.andExpect(jsonPath("$.details[2].discountCode").value(soDetails.get(2).get("discountCode")))
			.andExpect(jsonPath("$.details[2].discountRate").value(soDetails.get(2).get("discountRate")))
			.andExpect(jsonPath("$.details[2].unitDiscountCode").value(soDetails.get(2).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[2].unitDiscountRate").value(soDetails.get(2).get("unitDiscountRate")))
			.andExpect(jsonPath("$.details[2].lastStatus").value("560"))
			.andExpect(jsonPath("$.details[2].nextStatus").value("580"))
			.andExpect(jsonPath("$.details[2].invoiceNumber").value(0))
			.andExpect(jsonPath("$.details[2].invoiceType").isEmpty())
			.andExpect(jsonPath("$.details[2].invoiceSequence").value(0))
			.andExpect(jsonPath("$.details[2].invoiceDate").isEmpty())
			//.andExpect(jsonPath("$.details[2].originalDocumentNumber").value(poDetails.get(2).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[2].originalDocumentType").value(poDetails.get(2).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[2].originalDocumentSequence").value(poDetails.get(2).get("exchangeRate")))
			//.andExpect(jsonPath("$.details[2].customerOrderNumber").value(requestObject.get("customerOrderNumber")))
			//.andExpect(jsonPath("$.details[2].customerOrderDate").value(requestObject.get("customerOrderDate")))
			//.andExpect(jsonPath("$.details[2].vendorInvoiceNumber").value(poDetails.get(2).get("serialLotNo")))
			//.andExpect(jsonPath("$.details[2].vendorInvoiceDate").value(poDetails.get(2).get("serialLotNo")))
			.andExpect(jsonPath("$.details[2].taxInvoiceNumber").isEmpty())
			.andExpect(jsonPath("$.details[2].taxInvoiceDate").isEmpty());
		
		//assert that the quantities on the sales orders have been changed
		performer.performGet(((Map<String, Map<String, Object>>) soDetails.get(0).get("_links")).get("order").get("href").toString())
				.andExpect(jsonPath("$.details[0].quantity").value(soDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].shippedQuantity").value(requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].openQuantity").value((double)soDetails.get(0).get("quantity") - (double)requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[1].quantity").value(soDetails.get(1).get("quantity")))
				.andExpect(jsonPath("$.details[1].shippedQuantity").value(Matchers.closeTo(39.37 * (double)requestDetails.get(1).get("quantity"), 0.001)))
				.andExpect(jsonPath("$.details[1].openQuantity").value(Matchers.closeTo((double)soDetails.get(1).get("quantity") - 39.37 * (double)requestDetails.get(1).get("quantity"), 0.001)));
	
		//assert the shipped serial numbers have their statuses changed
		String lotUrl = "/api/lots?pk.businessUnitId=" + requestObject.get("businessUnitId") + "&pk.itemCode=" + soDetails.get(2).get("itemCode");
		//String[] serialNumbers = ((List<String>) requestDetails.get(2).get("serialOrLotNumbers")).toArray(new String[3]);
		performer.performGet(lotUrl)
				//.andDo(print())
				.andExpect(jsonPath("$._embedded.lots[?(@.serialNumber in ['serialNoA', 'serialNoB', 'serialNoC'])].lotStatusCode").value(Matchers.contains("S", "S", "S")))
				//.andExpect(jsonPath("$._embedded.lots.length()").value(3))
				//.andExpect(jsonPath("$._embedded.lots[0].itemCode").value(soDetails.get(2).get("itemCode")))
		;
		
		//assert new item transactions are created for items with type stock
		String itemtransUrl = "/api/stockTransactions/";
		performer.performGet(itemtransUrl + newId())
				.andDo(print())
				.andExpect(jsonPath("$.details.length()").value(2))
				.andExpect(jsonPath("$.details[0].companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.details[0].documentNumber").value(requestObject.get("documentNumber")))
				.andExpect(jsonPath("$.details[0].documentType").value(requestObject.get("documentType")))
				.andExpect(jsonPath("$.details[0].businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.details[0].transactionDate").value(requestObject.getOrDefault("documentDate", LocalDate.now()).toString()))
				.andExpect(jsonPath("$.details[0].glDate").value(requestObject.getOrDefault("documentDate", LocalDate.now()).toString()))
				//.andExpect(jsonPath("$.details[0].description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.details[0].sequence").value(10))
				.andExpect(jsonPath("$.details[0].quantity").value(requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].itemCode").value(soDetails.get(0).get("itemCode")))
				.andExpect(jsonPath("$.details[0].locationId").value(soDetails.get(0).get("locationId")))
				.andExpect(jsonPath("$.details[0].serialLotNo").value(soDetails.get(0).get("serialLotNo")))
				.andExpect(jsonPath("$.details[0].itemDescription").value(soDetails.get(0).get("description")))
				.andExpect(jsonPath("$.details[0].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", soDetails.get(0).get("unitOfMeasure"))))
				.andExpect(jsonPath("$.details[0].primaryUnitOfMeasure").value(soDetails.get(0).get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[0].secondaryTransactionQuantity").value(requestDetails.get(0).getOrDefault("secondaryTransactionQuantity", 0.0)))
				.andExpect(jsonPath("$.details[0].secondaryUnitOfMeasure").value(soDetails.get(0).get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[0].unitCost").value(soDetails.get(0).get("unitCost")))
				.andExpect(jsonPath("$.details[0].extendedCost").value(((double)soDetails.get(0).get("unitCost")) * ((double) requestDetails.get(0).get("quantity"))))
				.andExpect(jsonPath("$.details[0].businessPartnerId").value(requestObject.get("customerId")))
				.andExpect(jsonPath("$.details[0].fromOrTo").value("T"))
				.andExpect(jsonPath("$.details[0].orderNumber").value(soDetails.get(0).get("salesOrderNumber")))
				.andExpect(jsonPath("$.details[0].orderType").value(soDetails.get(0).get("salesOrderType")))
				.andExpect(jsonPath("$.details[0].orderSequence").value(soDetails.get(0).get("salesOrderSequence")))
				
				.andExpect(jsonPath("$.details[1].companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.details[1].documentNumber").value(requestObject.get("documentNumber")))
				.andExpect(jsonPath("$.details[1].documentType").value(requestObject.get("documentType")))
				.andExpect(jsonPath("$.details[1].businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.details[1].transactionDate").value(requestObject.getOrDefault("documentDate", LocalDate.now()).toString()))
				.andExpect(jsonPath("$.details[1].glDate").value(requestObject.getOrDefault("documentDate", LocalDate.now()).toString()))
				//.andExpect(jsonPath("$.details[1].description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.details[1].sequence").value(30))
				.andExpect(jsonPath("$.details[1].quantity").value(requestDetails.get(2).get("quantity")))
				.andExpect(jsonPath("$.details[1].itemCode").value(soDetails.get(2).get("itemCode")))
				.andExpect(jsonPath("$.details[1].locationId").value(soDetails.get(2).get("locationId")))
				.andExpect(jsonPath("$.details[1].serialLotNo").value(soDetails.get(2).get("serialLotNo")))
				.andExpect(jsonPath("$.details[1].itemDescription").value(soDetails.get(2).get("description")))
				.andExpect(jsonPath("$.details[1].unitOfMeasure").value(requestDetails.get(2).getOrDefault("unitOfMeasure", soDetails.get(2).get("unitOfMeasure"))))
				.andExpect(jsonPath("$.details[1].primaryUnitOfMeasure").value(soDetails.get(2).get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[1].secondaryTransactionQuantity").value(requestDetails.get(2).getOrDefault("secondaryTransactionQuantity", 0.0)))
				.andExpect(jsonPath("$.details[1].secondaryUnitOfMeasure").value(soDetails.get(2).get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[1].unitCost").value(soDetails.get(2).get("unitCost")))
				.andExpect(jsonPath("$.details[1].extendedCost").value(
						((double)soDetails.get(2).get("unitCost")) 
						* 
						((double) requestDetails.get(2).get("quantity"))))
				.andExpect(jsonPath("$.details[1].businessPartnerId").value(requestObject.get("customerId")))
				.andExpect(jsonPath("$.details[1].fromOrTo").value("T"))
				.andExpect(jsonPath("$.details[1].orderNumber").value(soDetails.get(2).get("salesOrderNumber")))
				.andExpect(jsonPath("$.details[1].orderType").value(soDetails.get(2).get("salesOrderType")))
				.andExpect(jsonPath("$.details[1].orderSequence").value(soDetails.get(2).get("salesOrderSequence")))
				;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		//header and existing details are unchanged
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
		
		List<Map<String, Object>> requestDetails = (List<Map<String, Object>>) requestObject.get("details");
		//new requests are added at the bottom of the details list
		action
			.andExpect(jsonPath("$.details[3].sequence").value("40"))
			.andExpect(jsonPath("$.details[3].itemCode").value(soDetails.get(0).get("itemCode")))
			.andExpect(jsonPath("$.details[3].itemDescription").value(soDetails.get(0).get("description")))
			.andExpect(jsonPath("$.details[3].locationId").value(requestDetails.get(0).getOrDefault("locationId", soDetails.get(0).get("locationId"))))
			.andExpect(jsonPath("$.details[3].serialLotNo").value(requestDetails.get(0).getOrDefault("serialLotNo", soDetails.get(0).get("serialLotNo"))))
			.andExpect(jsonPath("$.details[3].quantity").value(requestDetails.get(0).get("quantity")))
			.andExpect(jsonPath("$.details[3].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", soDetails.get(0).get("unitOfMeasure"))))
			.andExpect(jsonPath("$.details[3].unitConversionFactor").value(1))
			.andExpect(jsonPath("$.details[3].primaryTransactionQuantity").value(requestDetails.get(0).get("quantity")))
			.andExpect(jsonPath("$.details[3].primaryUnitOfMeasure").value(soDetails.get(0).get("primaryUnitOfMeasure")))
			//.andExpect(jsonPath("$.details[3].secondaryTransactionQuantity").value(requestDetails.get(0).get("quantity")))
			//.andExpect(jsonPath("$.details[3].secondaryUnitOfMeasure").value(soDetails.get(0).get("primaryUnitOfMeasure")))
			.andExpect(jsonPath("$.details[3].orderNumber").value(requestDetails.get(0).get("orderNumber")))
			.andExpect(jsonPath("$.details[3].orderType").value(requestDetails.get(0).get("orderType")))
			.andExpect(jsonPath("$.details[3].orderSequence").value(requestDetails.get(0).get("orderSequence")))
			.andExpect(jsonPath("$.details[3].baseCurrency").value(soDetails.get(0).get("baseCurrency")))
			.andExpect(jsonPath("$.details[3].transactionCurrency").value(soDetails.get(0).get("transactionCurrency")))
			.andExpect(jsonPath("$.details[3].exchangeRate").value(soDetails.get(0).get("exchangeRate")))
			.andExpect(jsonPath("$.details[3].lineType").value(soDetails.get(0).get("lineType")))
			.andExpect(jsonPath("$.details[3].unitPrice").value(soDetails.get(0).get("unitPrice")))
			.andExpect(jsonPath("$.details[3].extendedPrice").value(soDetails.get(0).get("extendedPrice")))
			.andExpect(jsonPath("$.details[3].unitCost").value(soDetails.get(0).get("unitCost")))
			.andExpect(jsonPath("$.details[3].extendedCost").value(soDetails.get(0).get("extendedCost")))
			.andExpect(jsonPath("$.details[3].taxBase").value(soDetails.get(0).get("taxBase")))
			.andExpect(jsonPath("$.details[3].taxAmount").value(soDetails.get(0).get("taxAmount")))
			.andExpect(jsonPath("$.details[3].profitCenterId").value(soDetails.get(0).get("profitCenterId")))
			.andExpect(jsonPath("$.details[3].paymentTermCode").value(soDetails.get(0).get("paymentTermCode")))
			.andExpect(jsonPath("$.details[3].taxCode").value(soDetails.get(0).get("taxCode")))
			.andExpect(jsonPath("$.details[3].taxAllowance").value(soDetails.get(0).get("taxAllowance")))
			.andExpect(jsonPath("$.details[3].taxRate").value(soDetails.get(0).get("taxRate")))
			.andExpect(jsonPath("$.details[3].guestServiceChargeRate").value(soDetails.get(0).get("guestServiceChargeRate")))
			.andExpect(jsonPath("$.details[3].discountCode").value(soDetails.get(0).get("discountCode")))
			.andExpect(jsonPath("$.details[3].discountRate").value(soDetails.get(0).get("discountRate")))
			.andExpect(jsonPath("$.details[3].unitDiscountCode").value(soDetails.get(0).get("unitDiscountCode")))
			.andExpect(jsonPath("$.details[3].unitDiscountRate").value(soDetails.get(0).get("unitDiscountRate")))
			.andExpect(jsonPath("$.details[3].lastStatus").value("560"))
			.andExpect(jsonPath("$.details[3].nextStatus").value("580"))
			.andExpect(jsonPath("$.details[3].invoiceNumber").value(0))
			.andExpect(jsonPath("$.details[3].invoiceType").isEmpty())
			.andExpect(jsonPath("$.details[3].invoiceSequence").value(0))
			.andExpect(jsonPath("$.details[3].invoiceDate").isEmpty());
	}
	
	@Override @Test
	public void deleteTest() throws Exception {
		assumeExists(baseUrl + existingId);
		
		performer.performDelete(baseUrl + existingId)
				.andExpect(status().isMethodNotAllowed());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void voidTest() throws Exception{
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
				.andExpect(jsonPath("$.details[3].purchaseOrderNumber").value(soDetails.get(0).get("purchaseOrderNumber")))
				.andExpect(jsonPath("$.details[3].purchaseOrderType").value(soDetails.get(0).get("purchaseOrderType")))
				.andExpect(jsonPath("$.details[3].purchaseOrderSequence").value(soDetails.get(0).get("purchaseOrderSequence")))
				.andExpect(jsonPath("$.details[3].quantity").value(Matchers.closeTo(-1 * (double)requestDetails.get(0).get("quantity"), 0.0001)))
				.andExpect(jsonPath("$.details[3].itemCode").value(soDetails.get(0).get("itemCode")))
				.andExpect(jsonPath("$.details[3].baseCurrency").value(soDetails.get(0).get("baseCurrency")))
				.andExpect(jsonPath("$.details[3].transactionCurrency").value(soDetails.get(0).get("transactionCurrency")))
				.andExpect(jsonPath("$.details[3].exchangeRate").value(soDetails.get(0).get("exchangeRate")))
				.andExpect(jsonPath("$.details[3].locationId").value(soDetails.get(0).get("locationId")))
				.andExpect(jsonPath("$.details[3].serialLotNo").value(soDetails.get(0).get("serialLotNo")))
				.andExpect(jsonPath("$.details[3].itemDescription").value(soDetails.get(0).get("description")))
				.andExpect(jsonPath("$.details[3].lineType").value(soDetails.get(0).get("lineType")))
				.andExpect(jsonPath("$.details[3].unitOfMeasure").value(requestDetails.get(0).getOrDefault("unitOfMeasure", soDetails.get(0).get("unitOfMeasure"))))
				//.andExpect(jsonPath("$.details[3].unitConversionFactor").value(soDetails.get(0).get("unitConversionFactor")))
				//.andExpect(jsonPath("$.details[3].primaryTransactionQuantity").value(-1 * (double)soDetails.get(0).get("primaryTransactionQuantity")))
				.andExpect(jsonPath("$.details[3].primaryUnitOfMeasure").value(soDetails.get(0).get("primaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[3].secondaryTransactionQuantity").value(Matchers.closeTo(-1 * (double)requestDetails.get(0).getOrDefault("secondaryTransactionQuantity", 0.0), 0.0001)))
				.andExpect(jsonPath("$.details[3].secondaryUnitOfMeasure").value(soDetails.get(0).get("secondaryUnitOfMeasure")))
				.andExpect(jsonPath("$.details[3].unitCost").value(soDetails.get(0).get("unitCost")))
				.andExpect(jsonPath("$.details[3].extendedCost").value(Matchers.closeTo(-1 * ((double)soDetails.get(0).get("unitCost")) * ((double) requestDetails.get(0).get("quantity")), 0.001)))
				.andExpect(jsonPath("$.details[3].taxBase").value(Matchers.closeTo(-1 * ((double)requestDetails.get(0).get("quantity")) / ((double)soDetails.get(0).get("quantity")) * ((double)soDetails.get(0).get("taxBase")), 0.001 )))
				.andExpect(jsonPath("$.details[3].taxAmount").value(Matchers.closeTo(-1 * ((double)requestDetails.get(0).get("quantity")) / ((double)soDetails.get(0).get("quantity")) * ((double)soDetails.get(0).get("taxAmount")), 0.001 )))
				.andExpect(jsonPath("$.details[3].lastStatus").value("499"))
				.andExpect(jsonPath("$.details[3].nextStatus").value("999"))
				.andExpect(jsonPath("$.details[3].receiptDate").value(requestObject.computeIfPresent("documentDate", (k,v) -> v.toString())))
				.andExpect(jsonPath("$.details[3].paymentTermCode").value(soDetails.get(0).get("paymentTermCode")))
				.andExpect(jsonPath("$.details[3].taxCode").value(soDetails.get(0).get("taxCode")))
				.andExpect(jsonPath("$.details[3].taxAllowance").value(soDetails.get(0).get("taxAllowance")))
				.andExpect(jsonPath("$.details[3].taxRate").value(soDetails.get(0).get("taxRate")))
				.andExpect(jsonPath("$.details[3].discountCode").value(soDetails.get(0).get("discountCode")))
				.andExpect(jsonPath("$.details[3].discountRate").value(soDetails.get(0).get("discountRate")))
				.andExpect(jsonPath("$.details[3].unitDiscountCode").value(soDetails.get(0).get("unitDiscountCode")))
				.andExpect(jsonPath("$.details[3].unitDiscountRate").value(soDetails.get(0).get("unitDiscountRate")))
			//assert the voided receipt has its status changed
				.andExpect(jsonPath("$.details[0].lastStatus").value("499"))
				.andExpect(jsonPath("$.details[0].nextStatus").value("999"));
		
		//assert that the quantities on the purchase orders have been returned to its original quantities
		performer.performGet(((Map<String, Map<String, Object>>) soDetails.get(0).get("_links")).get("order").get("href").toString())
				.andExpect(jsonPath("$.details[0].quantity").value(soDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[0].receivedQuantity").value(0.0))
				.andExpect(jsonPath("$.details[0].openQuantity").value(soDetails.get(0).get("quantity")));
		
		//assert new item transactions with negative quantity has been created
		String itemtransUrl = "/api/stockTransactions/";
		performer.performGet(itemtransUrl + newId())
				//.andDo(print())
				.andExpect(jsonPath("$.details[2].companyId").value(requestObject.get("companyId")))
				.andExpect(jsonPath("$.details[2].documentNumber").value(requestObject.get("purchaseReceiptNumber")))
				.andExpect(jsonPath("$.details[2].documentType").value(requestObject.get("purchaseReceiptType")))
				.andExpect(jsonPath("$.details[2].businessUnitId").value(requestObject.get("businessUnitId")))
				.andExpect(jsonPath("$.details[2].documentDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[2].glDate").value(requestObject.get("documentDate").toString()))
				.andExpect(jsonPath("$.details[2].description").value(requestObject.get("description")))
				.andExpect(jsonPath("$.details[2].sequence").value((requestDetails.size()+1) * 10))
				.andExpect(jsonPath("$.details[2].quantity").value(-1 * (double)requestDetails.get(0).get("quantity")))
				.andExpect(jsonPath("$.details[2].itemCode").value(soDetails.get(0).get("itemCode")))
				.andExpect(jsonPath("$.details[2].locationId").value(soDetails.get(0).get("locationId")))
				.andExpect(jsonPath("$.details[2].serialLotNo").value(soDetails.get(0).get("serialLotNo")))
				.andExpect(jsonPath("$.details[2].itemDescription").value(soDetails.get(0).get("description")));
	}

}
