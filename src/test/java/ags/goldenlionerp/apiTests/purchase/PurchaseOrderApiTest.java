package ags.goldenlionerp.apiTests.purchase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.ResultActions;

import ags.goldenlionerp.apiTests.ApiTestBase;
import ags.goldenlionerp.application.purchase.purchaseorder.PurchaseOrderPK;

public class PurchaseOrderApiTest extends ApiTestBase<PurchaseOrderPK> {

	@Override
	protected Map<String, Object> requestObject() throws Exception {
		Map<String, Object> map = new HashMap<>();
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
		// TODO Auto-generated method stub
		
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
