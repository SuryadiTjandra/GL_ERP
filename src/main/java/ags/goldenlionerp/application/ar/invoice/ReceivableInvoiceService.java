package ags.goldenlionerp.application.ar.invoice;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReceivableInvoiceService {

	@Autowired private ReceivableInvoiceRepository repo;
	
	
	public ReceivableInvoice create(ReceivableInvoice newInvoiceRequest) {
		throw new UnsupportedOperationException("Not yet implemented");
		//return newInvoiceRequest;
	}


	public ReceivableInvoice voidInvoice(ReceivableInvoicePK pk) {
		ReceivableInvoice invoice = repo.findIncludeVoided(pk)
										.orElseThrow(() -> new ResourceNotFoundException("Could not find invoice with number: " + pk.getInvoiceNumber()));
		if (invoice.isVoided())
			throw new AlreadyVoidedException("Invoice with number " + pk.getInvoiceNumber() + " is already voided!");
		
		invoice.voidInvoice();
		invoice.setClosedDate(LocalDate.now());
		return repo.save(invoice);
	}
}
