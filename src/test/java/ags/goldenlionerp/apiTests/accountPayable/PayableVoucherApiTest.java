package ags.goldenlionerp.apiTests.accountPayable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import ags.goldenlionerp.application.ap.voucher.PayableVoucherPK;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.application.setups.paymentterm.PaymentTerm;
import ags.goldenlionerp.application.setups.paymentterm.PaymentTermRepository;

public class PayableVoucherApiTest extends ApiTestBase<PayableVoucherPK> {

	@Autowired private NextNumberService nnServ;
	@Autowired private CompanyRepository compRepo;
	@Autowired private AddressBookRepository addrRepo;
	@Autowired private PaymentTermRepository ptcRepo;
	
	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId().getCompanyId());
		map.put("voucherNumber", newId().getVoucherNumber());
		map.put("voucherType", newId().getVoucherType());
		map.put("voucherSequence", newId().getVoucherSequence());
		
		map.put("businessUnitId", "110");
		map.put("voucherDate", LocalDate.now().plusDays(3));
		map.put("vendorId", "1015");
		map.put("payerId", "1015");
		map.put("netAmount", 1000.50);
		map.put("taxableAmount", 100);
		map.put("transactionCurrency", "SGD");
		map.put("exchangeRate", 10000);
		map.put("paymentTermCode", "D02");
		map.put("description", "TESTESTEST");
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/vouchers/";
	}

	@Override
	protected PayableVoucherPK existingId() {
		return new PayableVoucherPK("11000", 180400152, "PV", 10);
	}

	@Override
	protected PayableVoucherPK newId() {
		return new PayableVoucherPK("11000", 123456789, "PV", 10);
	}
	
	protected PayableVoucherPK voidedId() {
		return new PayableVoucherPK("11000", 180800001, "PV", 10);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.voucherNumber").value(existingId.getVoucherNumber()))
			.andExpect(jsonPath("$.voucherType").value(existingId.getVoucherType()))
			.andExpect(jsonPath("$.voucherSequence").value(existingId.getVoucherSequence()))
			.andExpect(jsonPath("$.voucherDate").value(LocalDate.of(2018, 4, 25).toString()))
			.andExpect(jsonPath("$.vendorId").value("2864"))
			.andExpect(jsonPath("$.payerId").value("2864"))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.netAmount").value(1259999700))
			.andExpect(jsonPath("$.taxableAmount").value(114545427.27))
			.andExpect(jsonPath("$.transactionCurrency").value("IDR"))
			.andExpect(jsonPath("$.exchangeRate").value(1))
			.andExpect(jsonPath("$.paymentTermCode").value("N21"))
			.andExpect(jsonPath("$.voided").value(false))
			.andExpect(jsonPath("$.accountId").value("100.212100"))
			.andExpect(jsonPath("$.description").value("PT Primajaya Multi Technology~PMT0411-10579/PO/ATG/180400051"));
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.vouchers").exists())
			.andExpect(jsonPath("$..vouchers[?(@.voided =='true')]").isEmpty())
		;
		
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		int nextRNumber = nnServ.peekAtNextNumber(newId().getCompanyId(), "P", YearMonth.now()).getNextSequence();
		String compCur = compRepo.findById(newId().getCompanyId()).get().getCurrencyCodeBase();
		
		action
			//primary key
			.andExpect(jsonPath("$.companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.voucherNumber").value(newId().getVoucherNumber()))
			.andExpect(jsonPath("$.voucherType").value("PV"))
			.andExpect(jsonPath("$.voucherSequence").value(newId().getVoucherSequence()))
			//fields visible in ERP
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.voucherDate").value(requestObject.getOrDefault("voucherDate", LocalDate.now()).toString()))
			.andExpect(jsonPath("$.vendorId").value(requestObject.get("vendorId")))
			.andExpect(jsonPath("$.payerId").value(requestObject.getOrDefault("payerId", requestObject.get("vendorId"))))
			.andExpect(jsonPath("$.netAmount").value(requestObject.getOrDefault("netAmount", 0)))
			.andExpect(jsonPath("$.taxableAmount").value(requestObject.getOrDefault("taxableAmount", 0)))
			.andExpect(jsonPath("$.transactionCurrency").value(requestObject.getOrDefault("transactionCurrency", "IDR")))
			.andExpect(jsonPath("$.exchangeRate").value(requestObject.getOrDefault("exchangeRate", 1)))
			.andExpect(jsonPath("$.paymentTermCode").value(requestObject.get("paymentTermCode")))
			//others
			.andExpect(jsonPath("$.batchNumber").value(nextRNumber - 1))
			.andExpect(jsonPath("$.batchType").value("P"))
			.andExpect(jsonPath("$.glDate").value(requestObject.getOrDefault("$.glDate", requestObject.get("voucherDate")).toString()))
			.andExpect(jsonPath("$.closedDate").isEmpty())
			.andExpect(jsonPath("$.openAmount").value(requestObject.getOrDefault("netAmount", 0)))
			.andExpect(jsonPath("$.foreignExtendedAmount").value(requestObject.getOrDefault("foreignExtendedAmount", 0)))
			.andExpect(jsonPath("$.baseCurrency").value(requestObject.getOrDefault("baseCurrency", compCur)))
			.andExpect(jsonPath("$.taxCode").value(requestObject.getOrDefault("taxCode", "")))
			.andExpect(jsonPath("$.documentStatusCode").value("A"))
			.andExpect(jsonPath("$.documentVoidStatus").isEmpty())
			.andExpect(jsonPath("$.voided").value(false))
			.andExpect(jsonPath("$.glClass").value(requestObject.getOrDefault("glClass", "")))
			.andExpect(jsonPath("$.accountId").value("100.212100")) //hardcoded?
			.andExpect(jsonPath("$.objectAccountCode").value("212100")) //hardcoded?
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
		
		String currencyTr = JsonPath.read(result, "$.transactionCurrency");
		String currencyBs = JsonPath.read(result, "$.baseCurrency");
		assertEquals(
			currencyTr.equals(currencyBs) ? "D" : "F",
			JsonPath.read(result, "$.domesticOrForeignTransaction")	
		);
		
		String vendorId = JsonPath.read(result, "$.vendorId");
		String vendorName = addrRepo.findById(vendorId)
									.map(AddressBookMaster::getName)
									.get();
		String description = vendorName;
		if (requestObject.containsKey("description"))
			description = description + "~" + requestObject.get("description");
		assertEquals(description, JsonPath.read(result, "$.description"));
		
		assertCreatedJournal(result);
		
	}
	
	private void assertDueDates(String result) {
		String ptc = JsonPath.read(result, "$.paymentTermCode");
		PaymentTerm pt = ptcRepo.findById(ptc).get();
		
		LocalDate invoiceDate = LocalDate.parse(JsonPath.read(result, "$.voucherDate"));
		
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
		fail("To be implemented");
	}
	
	@Test @Rollback
	public void createTestNoInvoiceNumber() throws Exception {
		requestObject.remove("voucherNumber");
		int nextDocNo = nnServ.peekAtNextDocumentNumber(newId().getCompanyId(), "PV", YearMonth.now());
		
		ResultActions action = performer.performPost(baseUrl, requestObject)
					.andExpect(status().isCreated());
		String createdUrl = action.andReturn().getResponse().getHeader("Location");
		
		performer.performGet(createdUrl)
				.andExpect(jsonPath("$.companyId").value(newId().getCompanyId()))
				.andExpect(jsonPath("$.voucherNumber").value(nextDocNo))
				.andExpect(jsonPath("$.voucherType").value("PV"))
				.andExpect(jsonPath("$.voucherSequence").value(newId().getVoucherSequence()));
				
	}

	@Test
	public void getVoidedSingle() throws Exception {
		performer.performGet(baseUrl + voidedId())
	
				.andExpect(status().isNotFound());
		
		performer.performGet(baseUrl + voidedId() + "?includeVoided=true")
			
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.voucherNumber").value(voidedId().getVoucherNumber()));
	}
	
	@Test
	public void getVoidedCollection() throws Exception {		
		String normalRes = performer.performGet(baseUrl)
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		int withoutVoidNumber = JsonPath.read(normalRes, "$.page.totalElements");
		
		String voidRes = performer.performGet(baseUrl + "?includeVoided=true")
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		int withVoidNumber = JsonPath.read(voidRes, "$.page.totalElements");
		assertTrue(withVoidNumber > withoutVoidNumber);
	}
	
	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		super.deleteTest();
		
		performer.performGet(baseUrl + existingId + "?includeVoided=true")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.closedDate").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.openAmount").value(0.0));
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
		requestObject.put("voucherNumber", existingId.getVoucherNumber());
		requestObject.put("voucherType", existingId.getVoucherType());
		requestObject.put("voucherSequence", existingId.getVoucherSequence());
		
		performer.performPost(baseUrl, requestObject)
					.andExpect(status().isConflict());
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		//no need to do anything
	}

}
