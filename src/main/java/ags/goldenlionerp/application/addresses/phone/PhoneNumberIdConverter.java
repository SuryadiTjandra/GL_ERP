package ags.goldenlionerp.application.addresses.phone;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return PhoneNumber.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		String[] ids = id.split("_");
		return new PhoneNumberPK(ids[0], Integer.parseInt(ids[1]), Integer.parseInt(ids[2]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		PhoneNumberPK pk = (PhoneNumberPK) id;
		return pk.getAddressNumber() + "_" + pk.getSequence1() + "_" + pk.getSequence2();
	}

}
