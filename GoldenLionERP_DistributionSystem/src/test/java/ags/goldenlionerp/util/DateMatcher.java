package ags.goldenlionerp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class DateMatcher extends BaseMatcher<String>{

	@Override
	public boolean matches(Object item) {
		LocalDate date = LocalDate.parse((String)item, DateTimeFormatter.ISO_DATE_TIME);
		return date.equals(LocalDate.now());
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}


}
