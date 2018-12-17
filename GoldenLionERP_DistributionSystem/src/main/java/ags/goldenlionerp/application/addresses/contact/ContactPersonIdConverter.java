package ags.goldenlionerp.application.addresses.contact;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class ContactPersonIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return ContactPerson.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null)
			return null;
		
		String[] ids = id.split("_");
		return new ContactPersonPK(ids[0], Integer.parseInt(ids[1]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		ContactPersonPK pk = (ContactPersonPK) id;
		return pk.getAddressNumber() + "_" + pk.getSequence();
	}

}
