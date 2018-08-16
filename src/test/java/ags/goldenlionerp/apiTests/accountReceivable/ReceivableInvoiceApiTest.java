package ags.goldenlionerp.apiTests.accountReceivable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.application.ar.invoice.ReceivableInvoicePK;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.application.setups.paymentterm.PaymentTerm;
import ags.goldenlionerp.application.setups.paymentterm.PaymentTermRepository;

public class ReceivableInvoiceApiTest extends ApiTestBase<ReceivableInvoicePK> {

	@Autowired NextNumberService nnServ;
	@Autowired PaymentTermRepository ptcRepo;
	@Autowired AddressBookRepository addrRepo;
	
	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId.getCompanyId());
		map.put("invoiceNumber", newId.getInvoiceNumber());
		map.put("invoiceType", newId.getInvoiceType());
		map.put("invoiceSequence", newId.getInvoiceSequence());
		
		map.put("invoiceDate", LocalDate.now().plusDays(5));
		map.put("customerId", "1015");
		map.put("payerId", "1016");
		map.put("businessUnitId", "110");
		map.put("netAmount", 2000.00);
		map.put("taxableAmount", 20);
		map.put("transactionCurrency", "USD");
		map.put("exchangeRate", 1);
		map.put("paymentTermCode", "D02");
		map.put("description", "APITEST");
		
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/invoices/";
	}

	@Override
	protected ReceivableInvoicePK existingId() {
		return new ReceivableInvoicePK("11000", 180800004, "RI", 10);
	}

	@Override
	protected ReceivableInvoicePK newId() {
		return new ReceivableInvoicePK("11000", 123456789, "RI", 10);
	}
	
	protected ReceivableInvoicePK voidedId() {
		return new ReceivableInvoicePK("11000", 180800003, "RI", 10);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.invoiceNumber").value(existingId.getInvoiceNumber()))
			.andExpect(jsonPath("$.invoiceType").value(existingId.getInvoiceType()))
			.andExpect(jsonPath("$.invoiceSequence").value(existingId.getInvoiceSequence()))
			.andExpect(jsonPath("$.invoiceDate").value(LocalDate.of(2018, 8, 10).toString()))
			.andExpect(jsonPath("$.customerId").value("1019"))
			.andExpect(jsonPath("$.payerId").value("1019"))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.netAmount").value(500))
			.andExpect(jsonPath("$.taxableAmount").value(0))
			.andExpect(jsonPath("$.transactionCurrency").value("IDR"))
			.andExpect(jsonPath("$.exchangeRate").value(1.2))
			.andExpect(jsonPath("$.paymentTermCode").value("DBC"))
			.andExpect(jsonPath("$.voided").value(false))
			.andExpect(jsonPath("$.accountId").value("100.112100"))
			.andExpect(jsonPath("$.description").value("Ace Colours~tes3"));
			
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.invoices").exists())
			.andExpect(jsonPath("$..invoices[?(@.voided =='true')]").isEmpty())
		;
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		int nextRNumber = nnServ.peekAtNextNumber(newId().getCompanyId(), "R", YearMonth.now()).getNextSequence();
		
		action
			//primary key
			.andExpect(jsonPath("$.companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.invoiceNumber").value(newId().getInvoiceNumber()))
			.andExpect(jsonPath("$.invoiceType").value("RI"))
			.andExpect(jsonPath("$.invoiceSequence").value(newId().getInvoiceSequence()))
			//fields visible in ERP
			.andExpect(jsonPath("$.invoiceDate").value(requestObject.get("invoiceDate").toString()))
			.andExpect(jsonPath("$.customerId").value(requestObject.get("customerId")))
			.andExpect(jsonPath("$.payerId").value(requestObject.getOrDefault("payerId", requestObject.get("customerId"))))
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.netAmount").value(requestObject.getOrDefault("netAmount", 0)))
			.andExpect(jsonPath("$.taxableAmount").value(requestObject.getOrDefault("taxableAmount", 0)))
			.andExpect(jsonPath("$.exchangeRate").value(requestObject.getOrDefault("exchangeRate", 1.0)))
			.andExpect(jsonPath("$.transactionCurrency").value(requestObject.get("transactionCurrency")))
			.andExpect(jsonPath("$.paymentTermCode").value(requestObject.get("paymentTermCode")))
			//.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
			//others
			.andExpect(jsonPath("$.batchNumber").value(nextRNumber - 1))
			.andExpect(jsonPath("$.batchType").value("R"))
			.andExpect(jsonPath("$.glDate").value(requestObject.getOrDefault("$.glDate", requestObject.get("invoiceDate")).toString()))
			.andExpect(jsonPath("$.salesmanId").isEmpty())
			.andExpect(jsonPath("$.closedDate").isEmpty())
			.andExpect(jsonPath("$.openAmount").value(requestObject.getOrDefault("netAmount", 0)))
			.andExpect(jsonPath("$.foreignExtendedAmount").value(requestObject.getOrDefault("foreignExtendedAmount", 0)))
			.andExpect(jsonPath("$.baseCurrency").value(requestObject.getOrDefault("baseCurrency", requestObject.get("transactionCurrency"))))
			.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
			.andExpect(jsonPath("$.documentStatusCode").value("A"))
			.andExpect(jsonPath("$.documentVoidStatus").isEmpty())
			.andExpect(jsonPath("$.voided").value(false))
			.andExpect(jsonPath("$.glClass").value(requestObject.getOrDefault("glClass", "")))
			.andExpect(jsonPath("$.accountId").value("100.112100")) //hardcoded?
			.andExpect(jsonPath("$.objectAccountCode").value("112100")) //hardcoded?
			.andExpect(jsonPath("$.subsidiaryCode").value(""))//hardcoded?
			.andExpect(jsonPath("$.subledger").value(""))//hardcoded?
			.andExpect(jsonPath("$.subledgerType").value(""))//hardcoded?
			;
			
		String result = action.andReturn().getResponse().getContentAsString();
		assertDueDates(result);
		assertAmounts(result);
		assertForeignAmounts(result);
		assertTaxes(result);
		assertVendorInvoiceInfo(result);
		assertDocumentOrderInfo(result);
		assertOriginalDocumentInfo(result);
		
		String currency = JsonPath.read(result, "$.transactionCurrency");
		assertEquals(
			currency.equals("IDR") ? "D" : "F",
			JsonPath.read(result, "$.domesticOrForeignTransaction")	
		);
		
		String customerId = JsonPath.read(result, "$.customerId");
		String customerName = addrRepo.findById(customerId)
									.map(AddressBookMaster::getName)
									.get();
		String description = customerName;
		if (requestObject.containsKey("description"))
			description = description + "~" + requestObject.get("description");
		assertEquals(description, JsonPath.read(result, "$.description"));
		
		assertCreatedJournal(result);
	}

	private void assertDueDates(String result) {
		String ptc = JsonPath.read(result, "$.paymentTermCode");
		PaymentTerm pt = ptcRepo.findById(ptc).get();
		
		LocalDate invoiceDate = LocalDate.parse(JsonPath.read(result, "$.invoiceDate"));
		
		LocalDate expectedDueDate = invoiceDate.plusDays(pt.getNetDaysToPay());
		assertEquals(
				expectedDueDate.toString(), 
				JsonPath.read(result, "$.dueDate").toString()
		);
		
		LocalDate expectedDiscountDueDate = invoiceDate.plusDays(pt.getDiscountDays());
		assertEquals(
				expectedDiscountDueDate.toString(), 
				JsonPath.read(result, "$.discountDueDate").toString()
		);
	}
	
	private void assertAmounts(String result) {
		BigDecimal netAmount = BigDecimal.valueOf(((Double) JsonPath.read(result, "$.netAmount")).doubleValue());
		BigDecimal openAmount = BigDecimal.valueOf(((Double) JsonPath.read(result, "$.openAmount")).doubleValue());
		assertEquals(netAmount, openAmount);
		
		String ptc = JsonPath.read(result, "$.paymentTermCode");
		PaymentTerm pt = ptcRepo.findById(ptc).get();
		BigDecimal discPerc = pt.getDiscountPercentage().divide(BigDecimal.valueOf(100)); //divide by 100 because it's percentage
		BigDecimal expectedDisc = netAmount.multiply(discPerc);
		
		BigDecimal disc = BigDecimal.valueOf(((Double) JsonPath.read(result, "$.discountAmountAvailable")).doubleValue());
		assertTrue(expectedDisc.compareTo(disc) == 0);
		
		BigDecimal takenDisc = BigDecimal.valueOf(((Double) JsonPath.read(result, "$.discountAmountTaken")).doubleValue());
		assertTrue(BigDecimal.ZERO.compareTo(takenDisc) == 0);
		
		BigDecimal taxAmt = BigDecimal.valueOf(((Double) JsonPath.read(result, "$.taxableAmount")).doubleValue());
		BigDecimal taxBase = BigDecimal.valueOf(((Double) JsonPath.read(result, "$.baseTaxableAmount")).doubleValue());
		assertTrue(netAmount.subtract(taxAmt).compareTo(taxBase) == 0);
	}
	
	private void assertForeignAmounts(String result) {
		// TODO Still don't know what to do
	}	
	
	private void assertTaxes(String result) {
		// TODO Still don't know what to do
	}

	private void assertVendorInvoiceInfo(String result) {
		// TODO Still don't know what to do
	}

	private void assertDocumentOrderInfo(String result) {
		// TODO Still don't know what to do
	}

	private void assertOriginalDocumentInfo(String result) {
		// TODO Still don't know what to do
	}

	private void assertCreatedJournal(String result) {
		// TODO wait until journal is finished
		fail();
	}

	@Test @Rollback
	public void createTestNoInvoiceNumber() throws Exception {
		requestObject.remove("invoiceNumber");
		String nextDocNo = nnServ.peekAtNextDocumentNumber(newId().getCompanyId(), "RI", YearMonth.now());
		
		ResultActions action = performer.performPost(baseUrl, requestObject)
					.andExpect(status().isCreated());
		String createdUrl = action.andReturn().getResponse().getHeader("Location");
		
		performer.performGet(createdUrl)
				.andExpect(jsonPath("$.companyId").value(newId().getCompanyId()))
				.andExpect(jsonPath("$.invoiceNumber").value(nextDocNo))
				.andExpect(jsonPath("$.invoiceType").value("RI"))
				.andExpect(jsonPath("$.invoiceSequence").value(newId().getInvoiceSequence()));
				
	}
	
	@Test
	public void getVoidedSingle() throws Exception {
		performer.performGet(baseUrl + voidedId())
	
				.andExpect(status().isNotFound());
		
		performer.performGet(baseUrl + voidedId() + "?includeVoided=true")
			
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.invoiceNumber").value(voidedId().getInvoiceNumber()));
	}
	
	@Test
	public void getVoidedCollection() throws Exception {		
		String normalRes = performer.performGet(baseUrl)
				.andDo(print())
				//.andExpect(jsonPath("$.page.totalElements").value(1440));
				.andReturn().getResponse().getContentAsString();
		int withoutVoidNumber = JsonPath.read(normalRes, "$.page.totalElements");
		
		String voidRes = performer.performGet(baseUrl + "?includeVoided=true")
				//.andDo(print())
				//.andExpect(jsonPath("$.page.totalElements").value(1441));
				.andReturn().getResponse().getContentAsString();
		int withVoidNumber = JsonPath.read(voidRes, "$.page.totalElements");
		assertTrue(withVoidNumber > withoutVoidNumber);
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
		requestObject.put("invoiceNumber", existingId.getInvoiceNumber());
		requestObject.put("invoiceType", existingId.getInvoiceType());
		requestObject.put("invoiceSequence", existingId.getInvoiceSequence());
		
		performer.performPost(baseUrl, requestObject)
					.andExpect(status().isConflict());
	}
	
	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		super.deleteTest();
		
		performer.performGet(baseUrl + existingId + "?includeVoided=true")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.closedDate").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.openAmount").value(0.0));
	}
	
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		//does nothing
	}

}
