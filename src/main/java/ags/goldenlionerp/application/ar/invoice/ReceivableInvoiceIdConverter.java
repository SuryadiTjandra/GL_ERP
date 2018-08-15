package ags.goldenlionerp.application.ar.invoice;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class ReceivableInvoiceIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return ReceivableInvoice.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new ReceivableInvoicePK(
				ids[0], 
				Integer.parseInt(ids[1]), 
				ids[2], 
				Integer.parseInt(ids[3]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		ReceivableInvoicePK pk = (ReceivableInvoicePK) id;
		return String.join("_", 
				pk.getCompanyId(),
				String.valueOf(pk.getInvoiceNumber()),
				pk.getInvoiceType(),
				String.valueOf(pk.getInvoiceSequence())
			);
	}

}
