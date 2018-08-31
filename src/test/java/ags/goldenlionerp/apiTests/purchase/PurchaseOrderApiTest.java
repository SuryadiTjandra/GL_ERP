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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderPK;
import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.taxcode.TaxRule;
import ags.goldenlionerp.application.setups.taxcode.TaxRuleRepository;

public class PurchaseOrderApiTest extends ApiTestBase<PurchaseOrderPK> {

	
	@Autowired private CompanyRepository compRepo;
	@Autowired private AddressBookRepository addrRepo;
	@Autowired private TaxRuleRepository taxRepo;
	
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
		det1.put("unitCost", BigDecimal.valueOf(50.0));
		det1.put("extendedUnitCost", BigDecimal.valueOf(30));
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
			
			.andExpect(jsonPath("$.orderDate").value(requestObject.get("orderDate").toString()))
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
			.andExpect(jsonPath("$.$discountCode").value(requestObject.getOrDefault("discountCode", "")))
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
			.andExpect(jsonPath("$.vehicleDescription2").value(requestObject.getOrDefault("vehicleDescription2", "")))
			
			;
	}
	
	
	private Object calculateDiscount() {
		// TODO Auto-generated method stub
		return null;
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
