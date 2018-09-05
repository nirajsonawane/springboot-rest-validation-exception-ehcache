package com.niraj.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

	private DateUtils()
	{
		
	}
	private static ChronoUnit chronoUnitseconds = ChronoUnit.SECONDS;

	public static Boolean isOlderSecondsThanCurrentUTC(ZonedDateTime sourceDate, Long seconds) {

		long between = chronoUnitseconds.between(getCurrentZonedDateInUtc().toInstant(), sourceDate.toInstant());
		return between <= Math.negateExact(seconds) ? true : false;
	}

	public static ZonedDateTime getCurrentZonedDateInUtc() {
		return ZonedDateTime.now(ZoneOffset.UTC);
	}

	public static Boolean isGreaterThanCurrentUtc(ZonedDateTime zonedDateTime) {
		long between = chronoUnitseconds.between(getCurrentZonedDateInUtc().toInstant(), zonedDateTime.toInstant());
		return between > 0 ? true : false;
	}

	public static Boolean isWithInSeconds(ZonedDateTime currentDate, ZonedDateTime sourceDate, Long seconds) {
		long between = chronoUnitseconds.between(currentDate.toInstant(), sourceDate.toInstant());
		return between > Math.negateExact(seconds) ? true : false;
	}

}
