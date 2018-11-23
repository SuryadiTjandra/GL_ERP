package ags.goldenlionerp.apiTests.itemstock;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.itemstock.stocktransaction.StockTransactionPK;

public class StockTransactionApiTest extends ApiTestBase<StockTransactionPK>{

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/stockTransactions/";
	}

	@Override
	protected StockTransactionPK existingId() {
		return new StockTransactionPK("11000", 180400001, "I0", 40);
	}

	@Override
	protected StockTransactionPK newId() {
		return new StockTransactionPK("11000", 9999999, "IA", 10);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.documentNumber").value(existingId.getDocumentNumber()))
			.andExpect(jsonPath("$.documentType").value(existingId.getDocumentType()))
			.andExpect(jsonPath("$.details[3].sequence").value(existingId.getSequence()))
			.andExpect(jsonPath("$.documentDate").value(LocalDate.of(2018, Month.APRIL, 8).toString()))
			.andExpect(jsonPath("$.glDate").value(LocalDate.of(2018, Month.APRIL, 8).toString()))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			
			.andExpect(jsonPath("$.details[3].description").value("SALDO AWAL INVENTORY"))
			.andExpect(jsonPath("$.details[3].itemCode").value(Matchers.equalToIgnoringWhiteSpace("ATK.AMPLOP - SAMSON D")))
			.andExpect(jsonPath("$.details[3].locationId").value("GDU"))
			.andExpect(jsonPath("$.details[3].serialLotNo").isEmpty())
			.andExpect(jsonPath("$.details[3].itemDescription").value(Matchers.equalToIgnoringWhiteSpace("ATK Amplop Coklat Samson D")))
			.andExpect(jsonPath("$.details[3].quantity").value(300.00))
			.andExpect(jsonPath("$.details[3].unitOfMeasure").value("UNT"))
			.andExpect(jsonPath("$.details[3].unitCost").value(325))
			.andExpect(jsonPath("$.details[3].extendedCost").value(97500))
			.andExpect(jsonPath("$.details[3].glClass").value("INFG"))
			.andExpect(jsonPath("$.details[3].expiredDate").isEmpty())
			;
		
	}

	@Override
	public void assertGetCollectionResult(ResultActions action) throws Exception {
		// TODO Auto-generated method stub
		
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
