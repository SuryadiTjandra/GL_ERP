package ags.goldenlionerp.application.item.itemtransaction;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class ItemTransactionIdConverter implements BackendIdConverter{

	@Override
	public boolean supports(Class<?> delimiter) {
		return ItemTransaction.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		String[] ids = id.split("_");
		return new ItemTransactionPK(ids[0], Integer.parseInt(ids[1]), ids[2], Integer.parseInt(ids[3]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		ItemTransactionPK pk = (ItemTransactionPK) id;
		return String.join("_",
				pk.getCompanyId(),
				String.valueOf(pk.getDocumentNumber()),
				pk.getDocumentType(),
				String.valueOf(pk.getSequence()));
	}

}
