package ags.goldenlionerp.application.ap.voucher;

import java.util.Collection;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ags.goldenlionerp.entities.UndeleteableRepository;
import ags.goldenlionerp.entities.VoidableRepository;

@RepositoryRestResource(path="vouchers", collectionResourceRel="vouchers")
public interface PayableVoucherRepository extends 
	VoidableRepository<PayableVoucher, PayableVoucherPK>,
	UndeleteableRepository<PayableVoucher, PayableVoucherPK> {

	Collection<PayableVoucher> findByPkCompanyIdAndPkVoucherNumber(String companyId, int voucherNo);
	
}
