package ags.goldenlionerp.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.AuditorAware;

import ags.goldenlionerp.util.BeanFinder;

public class DatabaseEntityUtil {
	
	private DatabaseEntityUtil() {}
	
	static void setCreationInfo(DatabaseEntity e) {
		e.setInputUserId(getCurrentUserId());
		e.setInputDateTime(LocalDateTime.now());
		setUpdateInfo(e);
	}
	
	static void setUpdateInfo(DatabaseEntity e) {
		e.setLastUpdateUserId(getCurrentUserId());
		e.setLastUpdateDateTime(LocalDateTime.now());
	}
	
	@SuppressWarnings("unchecked")
	private static String getCurrentUserId() {
		AuditorAware<String> auditorAware = BeanFinder.findBean(AuditorAware.class);
		return auditorAware.getCurrentAuditor().orElse("");
	}
	
	public static LocalDateTime toLocalDateTime(Timestamp date, String time) {
		if (date == null) return null;
		
		LocalDateTime datetime = date.toLocalDateTime();
		if (time == null || time.isEmpty())
			return datetime;
		
		try {
			DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_TIME;
			LocalTime localTime = LocalTime.parse(time, format);
			return LocalDateTime.of(datetime.toLocalDate(), localTime);
		} catch (DateTimeParseException e) {
			return datetime;
		}
	}
	
	public static Timestamp toDate(LocalDateTime datetime) {
		return Timestamp.valueOf(datetime.truncatedTo(ChronoUnit.DAYS));
	}
	public static String toTimeString(LocalDateTime datetime) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
		return datetime.toLocalTime().format(format);
	}
}
