package ags.goldenlionerp.application.system.holiday;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class HolidayIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return HolidayMaster.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		DateTimeFormatter formatter = WebIdUtil.getWebIdDateFormatter();
		//return Timestamp.valueOf(LocalDate.parse(id, formatter).atStartOfDay());
		return LocalDate.parse(id, formatter);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		DateTimeFormatter formatter = WebIdUtil.getWebIdDateFormatter();
		
		//Timestamp pk = (Timestamp) id;
		//String s = formatter.format(pk.toLocalDateTime());
		LocalDate pk = (LocalDate) id;
		
		return formatter.format(pk);
	}

}
