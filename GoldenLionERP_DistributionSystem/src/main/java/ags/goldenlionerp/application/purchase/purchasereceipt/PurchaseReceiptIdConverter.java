package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReceiptIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return PurchaseReceipt.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new PurchaseReceiptPK(
				ids[0], 
				Integer.parseInt(ids[1]), 
				ids[2], 
				Integer.parseInt(ids[3]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		PurchaseReceiptPK pk = (PurchaseReceiptPK) id;
		return String.join("_", 
				pk.getCompanyId(), 
				String.valueOf(pk.getPurchaseReceiptNumber()),
				pk.getPurchaseReceiptType(),
				String.valueOf(pk.getSequence()));
	}

}
