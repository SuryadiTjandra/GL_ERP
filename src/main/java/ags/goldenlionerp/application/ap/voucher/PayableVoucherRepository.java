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

import ags.goldenlionerp.entities.UndeleteableRepository;
import ags.goldenlionerp.entities.VoidableRepository;

@RepositoryRestResource(path="vouchers", collectionResourceRel="vouchers")
public interface PayableVoucherRepository extends 
	VoidableRepository<PayableVoucher, PayableVoucherPK>,
	UndeleteableRepository<PayableVoucher, PayableVoucherPK> {

	Collection<PayableVoucher> findByPkCompanyIdAndPkVoucherNumber(String companyId, int voucherNo);
	
}
