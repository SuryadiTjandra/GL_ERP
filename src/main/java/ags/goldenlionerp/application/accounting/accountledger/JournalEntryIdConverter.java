package ags.goldenlionerp.application.accounting.accountledger;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return JournalEntryImpl.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new JournalEntryPK(ids[0], Integer.parseInt(ids[1]), ids[2], ids[3]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		JournalEntryPK pk = (JournalEntryPK) id;
		return String.join("_", 
				pk.getCompanyId(), 
				String.valueOf(pk.getDocumentNumber()), 
				pk.getDocumentType(),
				pk.getLedgerType());
	}

}
