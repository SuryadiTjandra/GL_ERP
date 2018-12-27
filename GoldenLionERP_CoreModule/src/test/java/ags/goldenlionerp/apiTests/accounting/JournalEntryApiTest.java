package ags.goldenlionerp.apiTests.accounting;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.accounting.accountledger.JournalEntryPK;
import ags.goldenlionerp.application.setups.nextnumber.NextNumber;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;

public class JournalEntryApiTest extends ApiTestBase<JournalEntryPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", newId().getCompanyId());
		map.put("documentNumber", newId().getDocumentNumber());
		map.put("documentType", newId().getDocumentType());
		
		map.put("businessUnitId", "100");
		map.put("documentDate", LocalDate.now().plusDays(2));
		map.put("cashflowType", "O");
		//map.put("accountId", "100.111321");
		map.put("businessPartnerId", "1020");
		map.put("transactionCurrency", "USD");
		map.put("exchangeRate", 100000);
		map.put("description", "testestest");
		map.put("batchType", "G");
		
		Map<String, Object> entry0 = new HashMap<>();
		entry0.put("accountId", "100.111321");
		entry0.put("amount", 800);
		
		Map<String, Object> entry1 = new HashMap<>();
		entry1.put("accountId", "100.211000");
		entry1.put("amount", -500);
		
		Map<String, Object> entry2 = new HashMap<>();
		entry2.put("accountId", "100.212900");
		entry2.put("amount", -300);
		
		map.put("entries", Arrays.asList(entry1, entry2));
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
		return new JournalEntryPK("11000", 987654321, "BM", "CA");
	}

	@Autowired
	private NextNumberService nnServ;
	
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
	
	@Override @Test @Rollback
	public void updateTestWithPatch() throws Exception {
		performer.performPatch(baseUrl + existingId, requestObject)
				.andExpect(status().isMethodNotAllowed());
	}
	
	@Override @Test @Rollback
	public void deleteTest() throws Exception {
		super.deleteTest();
		
		performer.performGet(baseUrl + existingId + "?includeVoided=true")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.voided").value(true));
	}
	
	@Test @Rollback
	public void updateTestWithPost() throws Exception {
		assumeExists(baseUrl + existingId);
		
		requestObject.put("companyId", existingId.getCompanyId());
		requestObject.put("documentNumber", existingId.getDocumentNumber());
		requestObject.put("documentType", existingId.getDocumentType());
		requestObject.put("ledgerType", existingId.getLedgerType());
		//requestObject.remove("entries");

		
		performer.performPost(baseUrl, requestObject)
					.andExpect(status().isConflict());
	}

	@Override @Ignore
	public void assertCreateWithPostResult(ResultActions action) throws Exception {
		NextNumber nn = nnServ.peekAtNextNumber(newId().getCompanyId(), (String) requestObject.get("batchType"), YearMonth.now());
		
		action
			.andExpect(jsonPath("$.companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.documentNumber").value(newId().getDocumentNumber()))
			.andExpect(jsonPath("$.documentType").value(newId().getDocumentType()))
			.andExpect(jsonPath("$.ledgerType").value(newId().getLedgerType()))
			
			.andExpect(jsonPath("$.documentDate").value(requestObject.getOrDefault("documentDate", LocalDate.now()).toString()))
			.andExpect(jsonPath("$.batchNumber").value(nn.getNextSequence()))
			.andExpect(jsonPath("$.transactionCurrency").value(requestObject.get("transactionCurrency")))
			.andExpect(jsonPath("$.exchangeRate").value(requestObject.get("exchangeRate")))
			.andExpect(jsonPath("$.description").value(requestObject.getOrDefault("description", "")))
			.andExpect(jsonPath("$.businessUnitId").value(requestObject.get("businessUnitId")))
			.andExpect(jsonPath("$.businessPartnerId").value(requestObject.getOrDefault("transactionCurrency", "")))
		
			.andExpect(jsonPath("$.entries[0].companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.entries[1].companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.entries[2].companyId").value(newId().getCompanyId()))
			.andExpect(jsonPath("$.entries[0].documentNumber").value(newId().getDocumentNumber()))
			.andExpect(jsonPath("$.entries[1].documentNumber").value(newId().getDocumentNumber()))
			.andExpect(jsonPath("$.entries[2].documentNumber").value(newId().getDocumentNumber()))
			.andExpect(jsonPath("$.entries[0].documentType").value(newId().getDocumentType()))
			.andExpect(jsonPath("$.entries[1].documentType").value(newId().getDocumentType()))
			.andExpect(jsonPath("$.entries[2].documentType").value(newId().getDocumentType()))
			.andExpect(jsonPath("$.entries[0].ledgerType").value(newId().getLedgerType()))
			.andExpect(jsonPath("$.entries[1].ledgerType").value(newId().getLedgerType()))
			.andExpect(jsonPath("$.entries[2].ledgerType").value(newId().getLedgerType()))
			.andExpect(jsonPath("$.entries[0].documentSequence").value(10))
			.andExpect(jsonPath("$.entries[1].documentSequence").value(20))
			.andExpect(jsonPath("$.entries[2].documentSequence").value(30))
			
			;
		
		//fail("More later");
	}

	
	@Override
	public void assertUpdateWithPatchResult(ResultActions action, String beforeUpdateJson) throws Exception {
		// Not applicable		
	}
	
	@Override
	public void getTestCollection() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void createTestWithPost() throws Exception {
		// TODO Auto-generated method stub
	}

}
