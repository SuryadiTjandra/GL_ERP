package ags.goldenlionerp.application.ap.voucher;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class PayableVoucherIDConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return PayableVoucher.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null)
			return null;
		
		String[] ids = id.split("_");
		return new PayableVoucherPK(
				ids[0], Integer.parseInt(ids[1]),
				ids[2], Integer.parseInt(ids[3])
			);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		PayableVoucherPK pk = (PayableVoucherPK) id;
		return String.join("_", 
				pk.getCompanyId(), 
				String.valueOf(pk.getVoucherNumber()),
				pk.getVoucherType(),
				String.valueOf(pk.getVoucherSequence())
			);
	}

}
