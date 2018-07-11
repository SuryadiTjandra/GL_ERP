package ags.goldenlionerp.system.currencyconversion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;
@Component
public class CurrencyConversionIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return CurrencyConversion.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null)	return null;
		
		DateTimeFormatter format = WebIdUtil.getWebIdDateFormatter();
		String[] ids = id.split("_");
		return new CurrencyConversionPK(
				LocalDate.parse(ids[0], format), 
				ids[1], ids[2]
			);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		CurrencyConversionPK pk = (CurrencyConversionPK) id;
		DateTimeFormatter format = WebIdUtil.getWebIdDateFormatter();
		return String.join("_", 
				format.format(pk.getEffectiveDate()),
				pk.getFromCurrency(), pk.getToCurrency()
			);
	}

}
