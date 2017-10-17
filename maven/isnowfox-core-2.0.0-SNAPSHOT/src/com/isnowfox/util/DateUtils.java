// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   DateUtils.java

package com.isnowfox.util;

import java.sql.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public final class DateUtils {

	public DateUtils() {
	}

	public static final long getDateStartTime(java.util.Date d) {
		Date d2 = new Date(d.getYear(), d.getMonth(), d.getDate());
		return d2.getTime();
	}

	public static final long getYearStartTime(java.util.Date d) {
		Date d2 = new Date(d.getYear(), 0, 1);
		return d2.getTime();
	}

	public static final int getUtcHours(java.util.Date data) {
		int hours = (data.getHours() * 60 + data.getMinutes() + data.getTimezoneOffset()) / 60;
		return hours;
	}

	public static final int getUtcMinutes(java.util.Date data) {
		int minutes = (data.getHours() * 60 + data.getMinutes() + data.getTimezoneOffset()) % 60;
		return minutes;
	}

	public static long parseTime(String startTime) {
		int index = startTime.indexOf(" ");
		int day = NumberUtils.toInt(startTime.substring(0, index), -1);
		if (day == -1) {
			throw new RuntimeException((new StringBuilder()).append("´íÎóµÄÈÕÆÚ").append(startTime).toString());
		} else {
			long time = day * 24 * 60 * 60 * 1000;
			time += parseMillisOfDay(startTime.substring(index + 1));
			return time;
		}
	}

	public static boolean isTheSameDay(long time1, long time2) {
		DateTime t1 = new DateTime(time1);
		DateTime t2 = new DateTime(time2);
		return t1.getDayOfYear() + t1.getYear() * 1000 == t2.getDayOfYear() + t2.getYear() * 1000;
	}

	public static int parseMillisOfDay(String startTime) {
		if (StringUtils.isNotEmpty(startTime)) {
			org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
			return LocalTime.parse(startTime, fmt).getMillisOfDay();
		} else {
			return 0;
		}
	}
}
