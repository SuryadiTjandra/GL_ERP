package ags.goldenlionerp.application.sales.salesshipment;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class SalesShipmentIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return SalesShipment.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		String[] ids = id.split("_");
		
		return new SalesShipmentPK(ids[0], 
				Integer.parseInt(ids[1]), 
				ids[2], 
				Integer.parseInt(ids[3]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		SalesShipmentPK pk = (SalesShipmentPK) id;
		return String.join("_", 
				pk.getCompanyId(), 
				String.valueOf(pk.getDocumentNumber()),
				pk.getDocumentType(),
				String.valueOf(pk.getSequence()));
	}

}
