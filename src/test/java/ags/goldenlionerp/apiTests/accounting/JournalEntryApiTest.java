package ags.goldenlionerp.apiTests.accounting;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.accounting.accountledger.JournalEntryPK;

public class JournalEntryApiTest extends ApiTestBase<JournalEntryPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/journalEntries/";
	}

	@Override
	protected JournalEntryPK existingId() {
		return new JournalEntryPK("11000", 180800002, "JE", "CA");
	}

	@Override
	protected JournalEntryPK newId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
		.andDo(print())
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.documentType").value(existingId.getDocumentType()))
			.andExpect(jsonPath("$.documentNumber").value(existingId.getDocumentNumber()))
			.andExpect(jsonPath("$.ledgerType").value(existingId.getLedgerType()))
			
			.andExpect(jsonPath("$.documentDate").value(LocalDate.of(2018, 8, 25).toString()))
			.andExpect(jsonPath("$.batchNumber").value(64))
			.andExpect(jsonPath("$.transactionCurrency").value("AUD"))
			.andExpect(jsonPath("$.exchangeRate").value(100.00))
			.andExpect(jsonPath("$.description").value("tes jurnal"))
			.andExpect(jsonPath("$.businessUnitId").value("100"))
			.andExpect(jsonPath("$.businessPartnerId").value(""))
		
			.andExpect(jsonPath("$.entries[0].accountId").value("100.111110"))
			.andExpect(jsonPath("$.entries[0].amount").value(200))
			.andExpect(jsonPath("$.entries[0].accountDescription").value("Kas Penjualan"))
			.andExpect(jsonPath("$.entries[0].description").value("tes jurnal"))
			.andExpect(jsonPath("$.entries[0].subledger").value(""))
			.andExpect(jsonPath("$.entries[0].cashflowType").value("O"))
			.andExpect(jsonPath("$.entries[0].postStatus").value("A"))
		
			.andExpect(jsonPath("$.entries[1].accountId").value("100.111120"))
			.andExpect(jsonPath("$.entries[1].amount").value(-100))
			.andExpect(jsonPath("$.entries[1].accountDescription").value("Kas Kecil"))
			.andExpect(jsonPath("$.entries[1].description").value("tes jurnal"))
			.andExpect(jsonPath("$.entries[1].subledger").value(""))
			.andExpect(jsonPath("$.entries[1].cashflowType").value("O"))
			.andExpect(jsonPath("$.entries[1].postStatus").value("A"))
			
			.andExpect(jsonPath("$.entries[2].accountId").value("100.111311"))
			.andExpect(jsonPath("$.entries[2].amount").value(-100))
			.andExpect(jsonPath("$.entries[2].accountDescription").value("BCA Suryono"))
			.andExpect(jsonPath("$.entries[2].description").value("tes jurnal"))
			.andExpect(jsonPath("$.entries[2].subledger").value(""))
			.andExpect(jsonPath("$.entries[2].cashflowType").value("O"))
			.andExpect(jsonPath("$.entries[2].postStatus").value("A"))
			;
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$._embedded.journalEntries").exists())
			.andExpect(jsonPath("$..journalEntries[?(@.voided =='true')]").isEmpty())
			;
		
	}
	
	@Override
	public void updateTestWithPatch() throws Exception {
		performer.performPatch(baseUrl + existingId, requestObject)
				.andExpect(status().isMethodNotAllowed());
	}

	@Override
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
