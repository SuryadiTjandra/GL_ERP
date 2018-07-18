package ags.goldenlionerp.application.addresses.bankaccount;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class BankAccountIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return BankAccount.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_", 3);
		return new BankAccountPK(ids[0], ids[1], ids[2]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		BankAccountPK pk = (BankAccountPK) id;
		return String.join("_", 
				pk.getAddressNumber(),
				pk.getBankId(),
				pk.getBankAccountNumber()
			);
	}

}
