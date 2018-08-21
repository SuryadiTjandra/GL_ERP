package ags.goldenlionerp.application.accounting.accountmaster;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class AccountMasterIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return AccountMaster.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("\\.", 4);
		return new AccountMasterPK(ids[0], ids[1], ids[2], ids[3]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		String str = ((AccountMasterPK) id).toString();
		return str;
	}

}
