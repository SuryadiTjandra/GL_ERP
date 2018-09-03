package ags.goldenlionerp.apiTests.purchase;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderPK;
import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.taxcode.TaxRule;
import ags.goldenlionerp.application.setups.taxcode.TaxRuleRepository;
import ags.goldenlionerp.masterdata.itemLocation.ItemLocation;
import ags.goldenlionerp.masterdata.itemmaster.ItemMaster;
import ags.goldenlionerp.masterdata.itemmaster.ItemMasterRepository;

public class PurchaseOrderApiTest extends ApiTestBase<PurchaseOrderPK> {

	
	@Autowired private CompanyRepository compRepo;
	@Autowired private AddressBookRepository addrRepo;
	@Autowired private TaxRuleRepository taxRepo;
	@Autowired private ItemMasterRepository itemRepo;
	
	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId().getCompanyId());
		map.put("purchaseOrderNumber", newId().getPurchaseOrderNumber());
		map.put("purchaseOrderType", newId().getPurchaseOrderType());
		
		map.put("businessUnitId", "100");
		map.put("orderDate", LocalDate.now().plusDays(3).toString());
		map.put("promisedDeliveryDate", LocalDate.now().plusDays(5).toString());
		map.put("vendorId", "1015");
		map.put("receiverId", "1015");
		map.put("exchangeRate", 1);
		map.put("taxCode", "NOTAX");
		map.put("description", "testtest");
		
		Map<String, Object> det0 = new HashMap<>();
		det0.put("itemNumber", "ACC.BATERAI - CMOS			");
		det0.put("locationId", "ABC.1.2.3");
		det0.put("orderQuantity", 10);
		//det0.put("extendedUnitOfMeasure", "CM");
		det0.put("unitCost", BigDecimal.valueOf(50.0));
		//det0.put("extendedUnitCost", BigDecimal.valueOf(30));
		//det0.put("unitDiscountCode", "D00");
		//det0.put("jobNo")
		//det0.put("landedCostRule", "LOC");
		//det0.put("containerSize", 20);
		//det0.put("stuffingType", "D");
		//det0.put("containerLoadType", "LCL");
		
		Map<String, Object> det1 = new HashMap<>();
		det1.put("itemNumber", "TEST2");
		det1.put("orderQuantity", 10);
		det1.put("unitOfMeasure", "IN");
		det1.put("extendedUnitOfMeasure", "CM");
		det1.put("unitCost", BigDecimal.valueOf(30));
		det1.put("extendedUnitCost", BigDecimal.valueOf(50));
		det1.put("unitDiscountCode", "D00");
		//det1.put("jobNo")
		det1.put("landedCostRule", "LOC");
		det1.put("containerSize", 20);
		det1.put("stuffingType", "D");
		det1.put("containerLoadType", "LCL");
		
		List<Map<String, Object>> details = Arrays.asList(det0, det1);
		map.put("details", details);
		
		map.put("discountCode", "R1000");
		map.put("importDeclarationNumber", "111111");
		map.put("importDeclarationDate", LocalDate.now().plusDays(3).toString());
		map.put("vehicleRegistrationNumber", "222222");
		map.put("vehicleDescription", "vehicledesc");
		map.put("vehicleDescription2", "vehicledesc2");
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
		Company comp = compRepo.findById(newId().getCompanyId()).get();
		AddressBookMaster addr = addrRepo.findById(requestObject.get("vendorId").toString()).get();
		Optional<TaxRule> tax = taxRepo.findActiveTaxRuleAt(
				(String) requestObject.getOrDefault("taxCode", ""),
				LocalDate.parse((String) requestObject.get("orderDate"))
		);
		
		action
			.andExpect(jsonPath("$.companyId").value(newId.getCompanyId()))
			.andExpect(jsonPath("$.purchaseOrderNumber").value(newId.getPurchaseOrderNumber()))
			.andExpect(jsonPath("$.purchaseOrderType").value(newId.getPurchaseOrderType()))
			
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.vendorId").value(requestObject.get("vendorId")))
			.andExpect(jsonPath("$.receiverId").value(requestObject.getOrDefault("receiverId", requestObject.get("vendorId"))))
			.andExpect(jsonPath("$.customerId").value(requestObject.getOrDefault("customerId", "")))
			.andExpect(jsonPath("$.baseCurrency").value(comp.getCurrencyCodeBase()))
			.andExpect(jsonPath("$.transactionCurrency").value(requestObject.getOrDefault("transactionCurrency", addr.getApSetting().get().getCurrencyCodeTransaction())))
			.andExpect(jsonPath("$.exchangeRate").value(requestObject.getOrDefault("exchangeRate", 1.00)))
			.andExpect(jsonPath("$.orderDate").value(requestObject.get("orderDate")))
			.andExpect(jsonPath("$.requestDate").value(requestObject.getOrDefault("requestDate", requestObject.get("orderDate"))))
			.andExpect(jsonPath("$.promisedDeliveryDate").value(requestObject.get("promisedDeliveryDate")))
			.andExpect(jsonPath("$.receiptDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.closedDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.glDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
			.andExpect(jsonPath("$.paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", addr.getApSetting().get().getPaymentTermCode())))
			.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
			.andExpect(jsonPath("$.taxAllowance").value(tax.map(TaxRule::getTaxAllowance).orElse(false)))
			//.andExpect(jsonPath("$.taxPercentage").value(taxRate))
			.andExpect(jsonPath("$.discountCode").value(requestObject.getOrDefault("discountCode", "")))
			//.andExpect(jsonPath("$.discountRate").value(requestObject.getOrDefault("discountRate", calculateDiscount())))
			.andExpect(jsonPath("$.lastStatus").value("220")) //from where?
			.andExpect(jsonPath("$.nextStatus").value("400")) //from whrre?
			.andExpect(jsonPath("$.importDeclarationNumber").value(requestObject.getOrDefault("importDeclarationNumber", "")))
			.andExpect(jsonPath("$.importDeclarationDate").value(requestObject.getOrDefault("importDeclarationDate", LocalDate.MIN)))
			.andExpect(jsonPath("$.portOfDeparture").value(requestObject.getOrDefault("portOfDeparture", "")))
			.andExpect(jsonPath("$.portOfArrival").value(requestObject.getOrDefault("portOfArrival", "")))
			.andExpect(jsonPath("$.vehicleRegistrationNumber").value(requestObject.getOrDefault("vehicleRegistrationType", "")))
			.andExpect(jsonPath("$.vehicleType").value(requestObject.getOrDefault("vehicleType", "")))
			.andExpect(jsonPath("$.vehicleDescription").value(requestObject.getOrDefault("vehicleDescription", "")))
			.andExpect(jsonPath("$.vehicleDescription2").value(requestObject.getOrDefault("vehicleDescription2", "")));
			
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) requestObject.get("details");
		Map<String, Object> det0 = list.get(0);
		Map<String, Object> det1 = list.get(1);
		ItemMaster item0 = itemRepo.findById((String) det0.get("itemNumber")).get();
		ItemMaster item1 = itemRepo.findById((String) det1.get("itemNumber")).get();
		ItemLocation primLoc0 = item0.getItemLocations().stream()
								.filter(il -> il.getPk().getBusinessUnitId().equals(requestObject.get("businessUnit")))
								.filter(il -> il.getLocationStatus() == "P")
								.findAny().get();
		ItemLocation primLoc1 = item1.getItemLocations().stream()
								.filter(il -> il.getPk().getBusinessUnitId().equals(requestObject.get("businessUnit")))
								.filter(il -> il.getLocationStatus() == "P")
								.findAny().get();
		
		action
			.andExpect(jsonPath("$.details[0].companyId").value(newId.getCompanyId()))
			.andExpect(jsonPath("$.details[1].companyId").value(newId.getCompanyId()))
			.andExpect(jsonPath("$.details[0].purchaseOrderNumber").value(newId.getPurchaseOrderNumber()))
			.andExpect(jsonPath("$.details[1].purchaseOrderNumber").value(newId.getPurchaseOrderNumber()))
			.andExpect(jsonPath("$.details[0].purchaseOrderType").value(newId.getPurchaseOrderType()))
			.andExpect(jsonPath("$.details[1].purchaseOrderType").value(newId.getPurchaseOrderType()))
			.andExpect(jsonPath("$.details[0].purchaseOrderSequence").value(10))
			.andExpect(jsonPath("$.details[1].purchaseOrderSequence").value(20))
			
			.andExpect(jsonPath("$.details[0].businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.details[0].vendorId").value(requestObject.get("vendorId")))
			.andExpect(jsonPath("$.details[0].receiverId").value(requestObject.getOrDefault("receiverId", requestObject.get("vendorId"))))
			.andExpect(jsonPath("$.details[0].customerId").value(requestObject.getOrDefault("customerId", "")))
			.andExpect(jsonPath("$.details[1].businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.details[1].vendorId").value(requestObject.get("vendorId")))
			.andExpect(jsonPath("$.details[1].receiverId").value(requestObject.getOrDefault("receiverId", requestObject.get("vendorId"))))
			.andExpect(jsonPath("$.details[1].customerId").value(requestObject.getOrDefault("customerId", "")))
			
			.andExpect(jsonPath("$.details[0].itemNumber").value(det0.get("itemNumber")))
			.andExpect(jsonPath("$.details[0].locationId").value(det0.getOrDefault("locationId", primLoc0.getPk().getLocationId())))
			.andExpect(jsonPath("$.details[0].serialLotNumber").value(det0.getOrDefault("serialLotNumber", primLoc0.getPk().getSerialLotNo())))
			.andExpect(jsonPath("$.details[0].description").value(item0.getDescription()))
			.andExpect(jsonPath("$.details[0].lineType").value(item0.getTransactionType()))
			.andExpect(jsonPath("$.details[1].itemNumber").value(det1.get("itemNumber")))
			.andExpect(jsonPath("$.details[1].locationId").value(det1.getOrDefault("locationId", primLoc1.getPk().getLocationId())))
			.andExpect(jsonPath("$.details[1].serialLotNumber").value(det1.getOrDefault("serialLotNumber", primLoc1.getPk().getSerialLotNo())))
			.andExpect(jsonPath("$.details[1].description").value(item1.getDescription()))
			.andExpect(jsonPath("$.details[1].lineType").value(item1.getTransactionType()))
			
			.andExpect(jsonPath("$.details[0].quantity").value(det0.get("quantity")))
			.andExpect(jsonPath("$.details[0].unitOfMeasure").value(det0.getOrDefault("unitOfMeasure", item0.getUnitsOfMeasure().getPrimaryUnitOfMeasure())))
			.andExpect(jsonPath("$.details[0].extendedQuantity").value(det0.getOrDefault("extendedQuantity", 0)))
			.andExpect(jsonPath("$.details[0].extendedUnitOfMeasure").value(det0.getOrDefault("extendedUnitOfMeasure", "")))
			.andExpect(jsonPath("$.details[0].orderQuantity").value(det0.get("quantity")))
			.andExpect(jsonPath("$.details[0].receivedQuantity").value(0))
			.andExpect(jsonPath("$.details[0].cancelledQuantity").value(0))
			.andExpect(jsonPath("$.details[0].returnedQuantity").value(0))
			.andExpect(jsonPath("$.details[0].openQuantity").value(det0.get("quantity")))
			.andExpect(jsonPath("$.details[0].unitConversionFactor").value(1))
			.andExpect(jsonPath("$.details[0].extendedUnitConversionFactor").value(0))
			.andExpect(jsonPath("$.details[0].primaryOrderQuantity").value(det0.get("quantity")))
			.andExpect(jsonPath("$.details[0].primaryUnitOfMeasure").value(item0.getUnitsOfMeasure().getPrimaryUnitOfMeasure()))
			//.andExpect(jsonPath("$.details[0].secondaryOrderQuantity").value(matcher))
			.andExpect(jsonPath("$.details[0].secondaryUnitOfMeasure").value(item0.getUnitsOfMeasure().getSecondaryUnitOfMeasure()))
			
			.andExpect(jsonPath("$.details[1].quantity").value(det1.get("quantity")))
			.andExpect(jsonPath("$.details[1].unitOfMeasure").value(det1.getOrDefault("unitOfMeasure", item0.getUnitsOfMeasure().getPrimaryUnitOfMeasure())))
			.andExpect(jsonPath("$.details[1].extendedQuantity").value(25.4/*det1.getOrDefault("extendedQuantity", 0)*/))
			.andExpect(jsonPath("$.details[1].extendedUnitOfMeasure").value(det1.getOrDefault("extendedUnitOfMeasure", "")))
			.andExpect(jsonPath("$.details[1].orderQuantity").value(det1.get("quantity")))
			.andExpect(jsonPath("$.details[1].receivedQuantity").value(0))
			.andExpect(jsonPath("$.details[1].cancelledQuantity").value(0))
			.andExpect(jsonPath("$.details[1].returnedQuantity").value(0))
			.andExpect(jsonPath("$.details[1].openQuantity").value(det1.get("quantity")))
			.andExpect(jsonPath("$.details[1].unitConversionFactor").value(0.083333333))
			.andExpect(jsonPath("$.details[1].extendedUnitConversionFactor").value(0.032808398))
			.andExpect(jsonPath("$.details[1].primaryOrderQuantity").value(0.83333/*det1.get("quantity")*/))
			.andExpect(jsonPath("$.details[1].primaryUnitOfMeasure").value(item1.getUnitsOfMeasure().getPrimaryUnitOfMeasure()))
			//.andExpect(jsonPath("$.details[1].secondaryOrderQuantity").value(matcher))
			.andExpect(jsonPath("$.details[1].secondaryUnitOfMeasure").value(item1.getUnitsOfMeasure().getSecondaryUnitOfMeasure()))
			
			.andExpect(jsonPath("$.details[0].unitCost").value(det0.get("unitCost")))
			.andExpect(jsonPath("$.details[0].extendedUnitCost").value(0))
			.andExpect(jsonPath("$.details[0].extendedCost").value(500.00))
			.andExpect(jsonPath("$.details[1].unitCost").value(127))
			.andExpect(jsonPath("$.details[1].extendedUnitCost").value(det0.get("extendedUnitCost")))
			.andExpect(jsonPath("$.details[1].extendedCost").value(1270.00))
			
			.andExpect(jsonPath("$.details[0].baseCurrency").value(comp.getCurrencyCodeBase()))
			.andExpect(jsonPath("$.details[0].transactionCurrency").value(requestObject.getOrDefault("transactionCurrency", addr.getApSetting().get().getCurrencyCodeTransaction())))
			.andExpect(jsonPath("$.details[0].exchangeRate").value(requestObject.getOrDefault("exchangeRate", 1.00)))
			.andExpect(jsonPath("$.details[1].baseCurrency").value(comp.getCurrencyCodeBase()))
			.andExpect(jsonPath("$.details[1].transactionCurrency").value(requestObject.getOrDefault("transactionCurrency", addr.getApSetting().get().getCurrencyCodeTransaction())))
			.andExpect(jsonPath("$.details[1].exchangeRate").value(requestObject.getOrDefault("exchangeRate", 1.00)))
			
			.andExpect(jsonPath("$.details[0].taxableAmount").value(0))
			.andExpect(jsonPath("$.details[1].taxableAmount").value(0))
			
			.andExpect(jsonPath("$.details[0].glClass").value(item0.getGlClass()))
			.andExpect(jsonPath("$.details[1].glClass").value(item0.getGlClass()))
			.andExpect(jsonPath("$.details[0].lastStatus").value("220"))
			.andExpect(jsonPath("$.details[1].lastStatus").value("220"))
			.andExpect(jsonPath("$.details[0].nextStatus").value("400"))
			.andExpect(jsonPath("$.details[1].nextStatus").value("400"))
			
			.andExpect(jsonPath("$.details[0].orderDate").value(requestObject.get("orderDate")))
			.andExpect(jsonPath("$.details[0].requestDate").value(requestObject.getOrDefault("requestDate", requestObject.get("orderDate"))))
			.andExpect(jsonPath("$.details[0].promisedDeliveryDate").value(requestObject.get("promisedDeliveryDate")))
			.andExpect(jsonPath("$.details[0].receiptDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.details[0].closedDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.details[0].glDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.details[1].orderDate").value(requestObject.get("orderDate")))
			.andExpect(jsonPath("$.details[1].requestDate").value(requestObject.getOrDefault("requestDate", requestObject.get("orderDate"))))
			.andExpect(jsonPath("$.details[1].promisedDeliveryDate").value(requestObject.get("promisedDeliveryDate")))
			.andExpect(jsonPath("$.details[1].receiptDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.details[1].closedDate", Matchers.nullValue()))
			.andExpect(jsonPath("$.details[1].glDate", Matchers.nullValue()))
			
			.andExpect(jsonPath("$.details[0].paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", addr.getApSetting().get().getPaymentTermCode())))
			.andExpect(jsonPath("$.details[0].taxCode").value(requestObject.getOrDefault("taxCode", "")))
			.andExpect(jsonPath("$.details[0].taxAllowance").value(tax.map(TaxRule::getTaxAllowance).orElse(false)))
			//.andExpect(jsonPath("$.details[0].taxPercentage").value(taxRate))
			.andExpect(jsonPath("$.details[0].$discountCode").value(requestObject.getOrDefault("discountCode", "")))
			//.andExpect(jsonPath("$.details[0].discountRate").value(requestObject.getOrDefault("discountRate", calculateDiscount())))
			.andExpect(jsonPath("$.details[0].$unitDiscountCode").value(det0.getOrDefault("unitDiscountCode", "")))
			//.andExpect(jsonPath("$.details[0].$unitDiscountRate").value(requestObject.getOrDefault("discountCode", "")))
			
			.andExpect(jsonPath("$.details[1].paymentTermCode").value(requestObject.getOrDefault("paymentTermCode", addr.getApSetting().get().getPaymentTermCode())))
			.andExpect(jsonPath("$.details[1].taxCode").value(requestObject.getOrDefault("taxCode", "")))
			.andExpect(jsonPath("$.details[1].taxAllowance").value(tax.map(TaxRule::getTaxAllowance).orElse(false)))
			//.andExpect(jsonPath("$.details[1].taxPercentage").value(taxRate))
			.andExpect(jsonPath("$.details[1].discountCode").value(requestObject.getOrDefault("discountCode", "")))
			//.andExpect(jsonPath("$.details[1].discountRate").value(requestObject.getOrDefault("discountRate", calculateDiscount())))
			.andExpect(jsonPath("$.details[1].$unitDiscountCode").value(det1.getOrDefault("unitDiscountCode", "")))
			//.andExpect(jsonPath("$.details[1].$unitDiscountRate").value(requestObject.getOrDefault("discountCode", "")))		
			
			.andExpect(jsonPath("$.details[0].landedCostRule").value(det0.getOrDefault("landedCostRule" ,"")))
			.andExpect(jsonPath("$.details[0].containerSize").value(det0.getOrDefault("containerSize", "")))
			.andExpect(jsonPath("$.details[0].stuffingType").value(det0.getOrDefault("stuffingType", "")))
			.andExpect(jsonPath("$.details[0].containerLoadType").value(det0.getOrDefault("containerLoadType", "")))
			.andExpect(jsonPath("$.details[1].landedCostRule").value(det1.getOrDefault("landedCostRule" ,"")))
			.andExpect(jsonPath("$.details[1].containerSize").value(det1.getOrDefault("containerSize", "")))
			.andExpect(jsonPath("$.details[1].stuffingType").value(det1.getOrDefault("stuffingType", "")))
			.andExpect(jsonPath("$.details[1].containerLoadType").value(det1.getOrDefault("containerLoadType", "")))
			;
			
			String json = action.andReturn().getResponse().getContentAsString();
			assertCreationInfo(JsonPath.read(json, "$.details[0]"));
			assertCreationInfo(JsonPath.read(json, "$.details[1]"));
			
	}
	
	
	private Object calculateDiscount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		assumeExists(baseUrl + existingId);
		performer.performPatch(baseUrl + existingId, requestObject)
				.andExpect(status().isMethodNotAllowed());
	}
	
	@Test @Rollback
	public void updateTestWithPost() throws Exception {
		assumeExists(baseUrl + existingId);
		
		requestObject.put("companyId", existingId.getCompanyId());
		requestObject.put("purchaseOrderNumber", existingId().getPurchaseOrderNumber());
		requestObject.put("purchaseOrderType", existingId().getPurchaseOrderType());
		
		performer.performPost(baseUrl, requestObject)
				.andExpect(status().isConflict());
	}
	
	@Override @Test @Rollback
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
