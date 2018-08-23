package ags.goldenlionerp.application.accounting.accountledger;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class AccountLedgerIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return AccountLedger.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new AccountLedgerPK(
				ids[0],
				Integer.parseInt(ids[1]),
				ids[2],
				Integer.parseInt(ids[3]),
				ids[4]
			);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		AccountLedgerPK pk = (AccountLedgerPK) id;
		return String.join("_", 
				pk.getCompanyId(),
				String.valueOf(pk.getDocumentNumber()),
				pk.getDocumentType(),
				String.valueOf(pk.getDocumentSequence()),
				pk.getLedgerType()
			);
	}

}
