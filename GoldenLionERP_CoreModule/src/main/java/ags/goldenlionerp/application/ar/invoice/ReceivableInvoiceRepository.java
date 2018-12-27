package ags.goldenlionerp.application.ar.invoice;

import java.util.Collection;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ags.goldenlionerp.entities.UndeleteableRepository;
import ags.goldenlionerp.entities.VoidableRepository;

@RepositoryRestResource(collectionResourceRel="invoices", path="invoices")
public interface ReceivableInvoiceRepository extends 
	VoidableRepository<ReceivableInvoice, ReceivableInvoicePK>, 
	UndeleteableRepository<ReceivableInvoice, ReceivableInvoicePK> {

	
	Collection<ReceivableInvoice> findByPkCompanyIdAndPkInvoiceNumber(String companyId, int invoiceNo);
	

}
