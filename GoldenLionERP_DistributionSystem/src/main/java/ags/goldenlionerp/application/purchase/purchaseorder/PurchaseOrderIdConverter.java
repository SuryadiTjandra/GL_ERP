package ags.goldenlionerp.application.purchase.purchaseorder;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return PurchaseOrder.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new PurchaseOrderPK(ids[0], Integer.parseInt(ids[1]), ids[2]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		PurchaseOrderPK pk = (PurchaseOrderPK) id;
		return String.join("_", 
				pk.getCompanyId(),
				String.valueOf(pk.getPurchaseOrderNumber()),
				pk.getPurchaseOrderType()
			);
	}

}
