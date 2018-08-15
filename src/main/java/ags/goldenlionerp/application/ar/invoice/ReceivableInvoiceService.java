package ags.goldenlionerp.application.ar.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivableInvoiceService {

	@Autowired private ReceivableInvoiceRepository repo;
	
	
	public ReceivableInvoice create(ReceivableInvoice newInvoiceRequest) {
		throw new UnsupportedOperationException("Not yet implemented");
		//return newInvoiceRequest;
	}
}
