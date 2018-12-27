package ags.goldenlionerp.application.itemstock.stocktransaction;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class StockTransactionIdConverter implements BackendIdConverter{

	@Override
	public boolean supports(Class<?> delimiter) {
		return StockTransaction.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		String[] ids = id.split("_");
		return new StockTransactionPK(ids[0], Integer.parseInt(ids[1]), ids[2], Integer.parseInt(ids[3]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		StockTransactionPK pk = (StockTransactionPK) id;
		return String.join("_",
				pk.getCompanyId(),
				String.valueOf(pk.getDocumentNumber()),
				pk.getDocumentType(),
				String.valueOf(pk.getSequence()));
	}

}
