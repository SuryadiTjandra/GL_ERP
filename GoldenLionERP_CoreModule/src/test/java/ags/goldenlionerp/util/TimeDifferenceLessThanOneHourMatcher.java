package ags.goldenlionerp.util;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class TimeDifferenceLessThanOneHourMatcher extends BaseMatcher<String>{

	@Override
	public boolean matches(Object item) {
		LocalTime time = LocalTime.parse((String) item, DateTimeFormatter.ISO_DATE_TIME);
		Duration dur = Duration.between(time, LocalTime.now()).abs();
		return dur.getSeconds() <= 3600;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

}
