package ags.goldenlionerp.application.sales.salesorder;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return SalesOrder.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String ids[] = id.split("_");
		return new SalesOrderPK(
				ids[0],
				Integer.valueOf(ids[1]),
				ids[2]
			);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		SalesOrderPK pk = (SalesOrderPK) id;
		return String.join("_", 
				pk.getCompanyId(),
				String.valueOf(pk.getSalesOrderNumber()),
				pk.getSalesOrderType());
	}

}
