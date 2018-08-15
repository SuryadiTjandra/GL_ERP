package ags.goldenlionerp.application.ar.invoice;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel="invoices", path="invoices")
public interface ReceivableInvoiceRepository extends PagingAndSortingRepository<ReceivableInvoice, ReceivableInvoicePK> {

	@Query("SELECT inv FROM ReceivableInvoice AS inv")
	Page<ReceivableInvoice> findAllIncludeVoided(Pageable pageable);
	
	@Query("SELECT inv FROM ReceivableInvoice AS inv WHERE inv.pk = :id")
	Optional<ReceivableInvoice> findIncludeVoided(@Param("id") ReceivableInvoicePK id);
	
	@Override
	@Query("SELECT inv FROM ReceivableInvoice AS inv WHERE inv.voided = false")
	Page<ReceivableInvoice> findAll(Pageable pageable);
	
	@Override
	@Query("SELECT inv FROM ReceivableInvoice AS inv WHERE inv.voided = false AND inv.pk = :id")
	Optional<ReceivableInvoice> findById(@Param("id") ReceivableInvoicePK id);
		
	
	@Override @RestResource(exported=false)
	default void delete(ReceivableInvoice entity) {
		throw new UnsupportedOperationException("Invoices must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteById(ReceivableInvoicePK id) {
		throw new UnsupportedOperationException("Invoices must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteAll() {
		throw new UnsupportedOperationException("Invoices must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteAll(Iterable<? extends ReceivableInvoice> entities) {
		throw new UnsupportedOperationException("Invoices must not be deleted!");
	}
}
