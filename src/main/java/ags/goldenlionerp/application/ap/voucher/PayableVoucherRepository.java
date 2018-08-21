package ags.goldenlionerp.application.ap.voucher;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path="vouchers", collectionResourceRel="vouchers")
public interface PayableVoucherRepository extends PagingAndSortingRepository<PayableVoucher, PayableVoucherPK> {

	Collection<PayableVoucher> findByPkCompanyIdAndPkVoucherNumber(String companyId, int voucherNo);
	
	@Query("SELECT vou FROM PayableVoucher AS vou")
	Page<PayableVoucher> findAllIncludeVoided(Pageable pageable);
	
	@Query("SELECT vou FROM PayableVoucher AS vou WHERE vou.pk = :id")
	Optional<PayableVoucher> findIncludeVoided(@Param("id") PayableVoucherPK id);
	
	@Override
	@Query("SELECT vou FROM PayableVoucher AS vou WHERE vou.voided = false")
	Page<PayableVoucher> findAll(Pageable pageable);
	
	@Override
	@Query("SELECT vou FROM PayableVoucher AS vou WHERE vou.voided = false AND vou.pk = :id")
	Optional<PayableVoucher> findById(@Param("id") PayableVoucherPK id);
		
	
	@Override @RestResource(exported=false)
	default void delete(PayableVoucher entity) {
		throw new UnsupportedOperationException("Vouchers must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteById(PayableVoucherPK id) {
		throw new UnsupportedOperationException("Vouchers must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteAll() {
		throw new UnsupportedOperationException("Vouchers must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteAll(Iterable<? extends PayableVoucher> entities) {
		throw new UnsupportedOperationException("Vouchers must not be deleted!");
	}
}
