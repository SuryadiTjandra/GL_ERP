package ags.goldenlionerp.apiTests.item;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.item.itemtransaction.ItemTransactionPK;

public class ItemTransactionApiTest extends ApiTestBase<ItemTransactionPK>{

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	@Override
	protected String baseUrl() {
		return "/api/itemTransactions/";
	}

	@Override
	protected ItemTransactionPK existingId() {
		return new ItemTransactionPK("11000", 180400001, "I0", 40);
	}

	@Override
	protected ItemTransactionPK newId() {
		return new ItemTransactionPK("11000", 9999999, "IA", 10);
	}

	@Override
	public void assertGetSingleResult(ResultActions action) throws Exception {
		action
			.andExpect(jsonPath("$.pk.companyId").value(existingId.getCompanyId()))
			.andExpect(jsonPath("$.pk.documentNumber").value(existingId.getDocumentNumber()))
			.andExpect(jsonPath("$.pk.documentType").value(existingId.getDocumentType()))
			.andExpect(jsonPath("$.pk.sequence").value(existingId.getSequence()))
			.andExpect(jsonPath("$.documentDate").value(LocalDate.of(2018, Month.APRIL, 8).toString()))
			.andExpect(jsonPath("$.glDate").value(LocalDate.of(2018, Month.APRIL, 8).toString()))
			.andExpect(jsonPath("$.businessUnitId").value("110"))
			.andExpect(jsonPath("$.description").value("SALDO AWAL INVENTORY"))
			.andExpect(jsonPath("$.itemCode").value(Matchers.equalToIgnoringWhiteSpace("ATK.AMPLOP - SAMSON D")))
			.andExpect(jsonPath("$.locationId").value("GDU"))
			.andExpect(jsonPath("$.serialLotNo").isEmpty())
			.andExpect(jsonPath("$.itemDescription").value(Matchers.equalToIgnoringWhiteSpace("ATK Amplop Coklat Samson D")))
			.andExpect(jsonPath("$.quantity").value(300.00))
			.andExpect(jsonPath("$.unitOfMeasure").value("UNT"))
			.andExpect(jsonPath("$.unitCost").value(325))
			.andExpect(jsonPath("$.extendedCost").value(97500))
			.andExpect(jsonPath("$.glClass").value("INFG"))
			.andExpect(jsonPath("$.expiredDate").isEmpty())
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
