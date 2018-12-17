package ags.goldenlionerp.application.setups.nextnumber;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class NextNumberIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return NextNumber.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new NextNumberPK(ids[0], ids[1], Integer.parseInt(ids[2]), Integer.parseInt(ids[3]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		NextNumberPK pk = (NextNumberPK) id;
		return String.join("_", 
				pk.getCompanyId(), 
				pk.getDocumentOrBatchType(), 
				String.valueOf(pk.getYear()),
				String.valueOf(pk.getMonth()));
	}

}
