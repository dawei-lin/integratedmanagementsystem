package com.infinova.imscommon.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * 日期助手
 * 
 * @author mx
 * 
 */
public class DateUtil {

	private static Calendar calendar = new GregorianCalendar();

	public static Date getUtcTime() {
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTime();
	}

	public static String getTDateStr(Date tDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			return sdf.format(tDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath()
	{
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 
	 * @param -字符串 yyyy-MM-ddTHH:mm:ss转换为Date
	 * @return 注：不会转为本地时间
	 */
	public static Date parseTDateStr(String tDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			return sdf.parse(tDateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param 字符串 yyyy-MM-ddTHH:mm:ss.SSSZ转换为Date
	 * @return 注：不会转为本地时间
	 */
	public static Date parseTDateStr2(String tDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		try {
			return sdf.parse(tDateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 本地时间转GMT(UTC时间)
	 * 
	 * @param localDate 本地时间
	 * @return yyyy-MM-dd'T'HH:mm:ss
	 */
	public static String getGMTTime(Date localDate) {
		// mothed 2
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		format.setTimeZone(gmtTime);
//		System.out.println("GMT Time: " + format.format(localDate));
		////////////////
		String dateString = format.format(localDate);
		return dateString;
//		Date currentTime_2 = parseTDateStr(dateString);

//		// method 2
//		Calendar calendar1 = Calendar.getInstance();
//		calendar1.setTimeZone(gmtTime);
//		// System.out.println(calendar1.getTime()); //时区无效
//		// System.out.println(calendar1.getTimeInMillis()); //时区无效
//		System.out.println("GMT hour = " + calendar1.get(Calendar.HOUR));
	}

	/**
	 * 转换为"yyyy-MM-dd'T'HH:mm:ss"字符串格式时间
	 * 
	 * @param localDate
	 * @return 注:不会自动转换为UTC时间
	 */
	public static String parseTTime(Date localDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateString = format.format(localDate);
		return dateString;
	}

	/**
	 * 本地时间转utc时间
	 * 
	 * @param localDate
	 * @return
	 */
	public static Date parseUTCTime(Date localDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateString = format.format(localDate);
		return parseTDateStr(dateString);
	}

	public static Date AddSecond(Date date, Integer seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static Date AddMinute(Date date, Integer minutes) {
		return AddSecond(date, minutes * 60);
	}

	public static Date AddHour(Date date, Integer hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	public static Date AddDays(Date date, Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	public static Date AddMonths(Date date, Integer Months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, Months);
		return calendar.getTime();
	}

	public static Date AddYears(Date date, Integer Years) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, Years);
		return calendar.getTime();
	}

	/**
	 * 获取年月日零时零分零秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date GetDate00_00_00(Date date) {
		if (null == date)
			return GetCurrentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar calendar = Calendar.getInstance();
		calendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 获取年月日23时59分59秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date GetDate23_59_59(Date date) {
		if (null == date)
			return GetCurrentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar calendar = Calendar.getInstance();
		calendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date GetCurrentDate() {
		Calendar clCalendar = Calendar.getInstance();
		Date dtDate = clCalendar.getTime();
		return dtDate;
	}

	public static Long GetTimeSpanYears(Date start, Date end) {

		Long RetVal = GetYear(end).longValue() - GetYear(start).longValue();

		return RetVal;
	}

	public static Long GetTimeSpanMonths(Date start, Date end) {
		Long RetVal = GetMonth(end).longValue() - GetMonth(start).longValue() + 12 * (GetYear(end) - GetYear(start));

		return RetVal;
	}

	public static Long GetTimeSpanDays(Date start, Date end) {
		final Integer DayMilliSecond = (1000 * 60 * 60 * 24);

		Long RetVal = end.getTime() - start.getTime();
		RetVal /= (DayMilliSecond);

		return RetVal;
	}

	public static Long GetTimeSpanHours(Date start, Date end) {
		final Integer HourMilliSecond = (1000 * 60 * 60);

		Long RetVal = end.getTime() - start.getTime();
		RetVal /= (HourMilliSecond);

		return RetVal;
	}

	public static Long GetTimeSpanMinutes(Date start, Date end) {
		final Integer MinuteMilliSecond = (1000 * 60);

		Long RetVal = end.getTime() - start.getTime();
		RetVal /= (MinuteMilliSecond);

		return RetVal;
	}

	public static Long GetTimeSpanSeconds(Date start, Date end) {
		final Integer SecondMilliSecond = (1000);

		Long RetVal = end.getTime() - start.getTime();
		RetVal /= (SecondMilliSecond);

		return RetVal;
	}

	public static Long GetTimeSpanMilliSeconds(Date start, Date end) {
		Long RetVal = end.getTime() - start.getTime();

		return RetVal;
	}

	public static Date GetDate() {
		Calendar clCalendar = Calendar.getInstance();
		Date dtDate = clCalendar.getTime();
		return dtDate;
	}

	public static Integer GetDay() {
		return GetDay(GetDate());
	}

	public static Integer GetDay(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static Integer GetHour() {
		return GetHour(GetDate());
	}

	public static Integer GetHour(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static Integer GetMinute() {
		return GetMinute(GetDate());
	}

	public static Integer GetMinute(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	public static Integer GetMonth() {
		return GetMonth(GetDate());
	}

	public static Integer GetMonth(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static Integer GetSecond() {
		return GetSecond(GetDate());
	}

	public static Integer GetSecond(Date date) {
		if (null == date)
			return 0;
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	public static Integer GetWeek() {
		return GetWeek(GetDate());
	}

	public static Integer GetWeek(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static String GetDayOfWeek(Date date) {
		int week = GetWeek(date);
		String result = "Sunday";
		switch (week) {
		case 0:
			result = "Sunday";
			break;
		case 1:
			result = "Monday";
			break;
		case 2:
			result = "Tuesday";
			break;
		case 3:
			result = "Wednesday";
			break;
		case 4:
			result = "Thursday";
			break;
		case 5:
			result = "Friday";
			break;
		case 6:
			result = "Saturday";
			break;
		default:
			break;
		}
		return result;
	}

	public static String GetDayOfWeek() {
		return GetDayOfWeek(new Date());
	}

	public static Integer GetYear() {
		return GetYear(GetDate());
	}

	public static Integer GetYear(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 是否是最小的年
	 * 
	 * @param date
	 * @return
	 */
	public static boolean IsMinValue(Date date) {
		if (date == null) {
			return false;
		}
		calendar.setTime(date);
		if (calendar.get(Calendar.YEAR) == calendar.getMinimum(Calendar.YEAR)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 Date
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		return parseDate(currentTime);
	}

	/**
	 * 时间转换
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date parseDate(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(date);
			Date time = formatter.parse(dateString);
			return time;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串yyyy-MM-dd HH:mm:ss转换为时间 Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		if (strDate == null || strDate.length() == 0)
			return GetCurrentDate();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * YYYY-MM-DD HH24:MI:SS.SSS
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		return String.format("%1$tY-%1$tm-%1$te %1$tH:%1$tM:%1$tS.%1$tL", date);
	}

	/**
	 * 获取当前时间 YYYY-MM-DD HH24:MI:SS.SSS
	 * 
	 * @return
	 */
	public static String getDateString() {
		Calendar clCalendar = Calendar.getInstance();
		Date dtDate = clCalendar.getTime();
		return getDateString(dtDate);
	}

	/**
	 * YYYYMMDDHH24MISSSSS
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStringEx(Date date) {
		return String.format("%1$tY%1$tm%1$te%1$tH%1$tM%1$tS%1$tL", date);
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 获取两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return 0:同一天， <0:date1<date2的天数， >0:date1>date2的天数
	 */
	public static long getDays(Date date1, Date date2) {
		Date date = null;
		Date mydate = null;
		try {
			date = parseDate(date1);
			mydate = parseDate(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";
		// 得到这个月的1号是星期几
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = DateUtil.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k 表示是取几位随机数，可以自己定
	 */
	public static String getNo(int k) {
		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static Calendar DateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Date CalendarToDate(Calendar cal) {
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 返回最小日期：0001-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date GetMinDate() {
		String strDate = "0001-01-01 00:00:00", strDateFormat = "yyyy-MM-dd HH:mm:ss";
		Date minDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		try {
			minDate = dateFormat.parse(strDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return minDate;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return 1--date1大于date2；-1--date1小于date2；0--date1等于date2
	 */
	public static int CompareDate(Date date1, Date date2) {
		if (null == date1 && null == date2)
			return 0;
		if (null == date1)
			return -1;
		if (null == date2)
			return 1;
		Date dt1 = date1;
		Date dt2 = date2;
		if (dt1.getTime() > dt2.getTime()) {// 比较long型的毫秒数
			return 1;
		} else if (dt1.getTime() < dt2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * long型转为格式化的String
	 * 
	 * @param time   ,例:1446364800000L
	 * @param format ,例:"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String Long2String(long time, String format) {
		if (time > 0l) {
			if (null == format || format.isEmpty())
				format = "yyyy-MM-dd HH:mm:ss";

			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);

			return sf.format(date);
		}
		return "";
	}

	/**
	 * 
	 * @param tDateStr 字符串类型的utc时间，示例:"2011-03-10T11:54:30.000Z"
	 * @return Date
	 */
	public static Date tDate2Date(String tDateStr) {
		try {
			SimpleDateFormat formatter, FORMATTER;
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));// 设置时区:GMT、GMT+8
			Date date = formatter.parse(tDateStr.substring(0, 24));
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param tDateStr 字符串类型的utc时间，示例:"2011-03-10T11:54:30.000Z"
	 * @return "dd-MMM-yyyy HH:mm:ss.SSS" 示例：22-九月-2016 10:00:01.124
	 */
	public static String tDate2StrDate(String tDateStr) {
		try {
			SimpleDateFormat formatter, FORMATTER;
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));// 设置时区:GMT、GMT+8
			Date date = formatter.parse(tDateStr.substring(0, 24));
			FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
//			System.out.println("OldDate-->"+tDateStr);
			return FORMATTER.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(GetCurrentDate());
	}

}
