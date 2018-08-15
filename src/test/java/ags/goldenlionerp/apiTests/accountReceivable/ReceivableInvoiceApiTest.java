package ags.goldenlionerp.apiTests.accountReceivable;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.ar.invoice.ReceivableInvoicePK;

public class ReceivableInvoiceApiTest extends ApiTestBase<ReceivableInvoicePK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId.getCompanyId());
		map.put("invoiceNumber", newId.getInvoiceNumber());
		map.put("invoiceType", newId.getInvoiceType());
		map.put("invoiceSequence", newId.getInvoiceSequence());
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
			.andExpect(jsonPath("$.customerId").value("1019"))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.netAmount").value(500))
			.andExpect(jsonPath("$.taxableAmount").value(0))
			.andExpect(jsonPath("$.transactionCurrency").value("IDR"))
			.andExpect(jsonPath("$.exchangeRate").value(1.2))
			.andExpect(jsonPath("$.paymentTermCode").value("DBC"))
			.andExpect(jsonPath("$.voided").value(false))
			.andExpect(jsonPath("$.accountId").value("100.112100"))
			.andExpect(jsonPath("$.description2").value("Ace Colours~tes3"));
			
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
		fail();
		
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
		performer.performGet(baseUrl)
		.andDo(print())
				.andExpect(jsonPath("$.page.totalElements").value(1440));
		
		performer.performGet(baseUrl + "?includeVoided=true")
		.andDo(print())
				.andExpect(jsonPath("$.page.totalElements").value(1441));
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
	
	@Override
	public void deleteTest() throws Exception {
		super.deleteTest();
		
		performer.performGet(baseUrl + voidedId() + "?includeVoided=true")
				.andExpect(status().isOk());
	}
	
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		//does nothing
	}

}
