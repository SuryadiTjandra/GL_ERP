package ags.goldenlionerp.system.taxcode;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class TaxRuleIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return TaxRule.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		LocalDate efDate = LocalDate.parse(ids[1], WebIdUtil.getWebIdDateFormatter());
		return new TaxRulePK(ids[0], efDate);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		TaxRulePK pk = (TaxRulePK) id;
		return pk.getTaxCode() + "_" 
				+ WebIdUtil.getWebIdDateFormatter().format(pk.getEffectiveDate()); 
	}

}
