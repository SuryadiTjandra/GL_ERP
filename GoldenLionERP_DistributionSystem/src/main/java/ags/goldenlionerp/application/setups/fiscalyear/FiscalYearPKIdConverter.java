package ags.goldenlionerp.application.setups.fiscalyear;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class FiscalYearPKIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return FiscalYear.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String ids[] = id.split("_");
		return new FiscalYearPK(ids[0], Integer.parseInt(ids[1]));
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return id.toString();
	}

}
