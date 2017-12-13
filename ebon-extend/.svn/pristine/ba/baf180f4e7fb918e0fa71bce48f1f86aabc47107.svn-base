package com.ebon.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 封装日期管理的工具
 * @author EBon-WH
 *
 */
public class DateUtil {
	
	/**
	 * 格式：年－月－日 <br>
	 * "yyyy-MM-dd"
	 */
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 格式：年－月－日 小时：分钟：秒 <br>
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String LONG_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 格式：小时：分钟：秒 <br>
	 * "HH:mm:ss"
	 */
	public static final String LONG_TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * 格式：月－日<br>
	 * "MM-dd"
	 */
	public static final String SHORT_DATE_FORMAT = "MM-dd";
	
	private static final DateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Log log = LogFactory.getLog(DateUtil.class);
	
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String addDate(Date startDate, int count, int field, String format) {
		int year = getYearOfDate(startDate);
		int month = getMonthOfDate(startDate) - 1;
		int day = getDayOfDate(startDate);
		int hour = getHourOfDate(startDate);
		int minute = getMinuteOfDate(startDate);
		int second = getSecondOfDate(startDate);
		Calendar calendar = new GregorianCalendar(year, month, day, hour, minute, second);
		calendar.add(field, count);
		return date2StrByFormat(calendar.getTime(), format);
	}
	/**
	 * 比较两个时间，如果左侧小于(<)右侧返回true,否则(>=)返回false
	 * @param leftDateStr
	 * @param rightDateStr
	 * @return boolean
	 */
	public static boolean compare(String leftDateStr, String rightDateStr) {
		Date leftDate = DateUtil.toDate(leftDateStr);
		Date rightDate = DateUtil.toDate(rightDateStr);
		return leftDate.before(rightDate);
	}
	/**
	 * 返回当前日期,小时，分钟，秒数,毫秒数都为0
	 * 
	 * @return Date
	 */
	public static Date currentDate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * get the current time, like now() function in vb.
	 * 
	 * @return Date
	 */
	public static Date currentTime() {
		return new Date();
	}
	public static String date2StrByFormat(Date date, String format) {
		String l_result = "";
		if (date != null) {
			DateFormat sdf = new SimpleDateFormat(format);
			l_result = sdf.format(date);
		}
		return l_result;
	}
	/**
	 * 格式化日期,根据时分秒是否为空自动选择使用格式 yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return null;
		String d = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		try {
			if (c.get(Calendar.HOUR) > 0 || c.get(Calendar.MINUTE) > 0 || c.get(Calendar.SECOND) > 0) {
				d = ldf.format(date);
			} else {
				d = sdf.format(date);
			}
		} catch (Exception ex) {
			throw new RuntimeException("无法格式化的日期 : " + date);
		}
		return d;
	}
	/**
	 * 采用yyyy-MM-dd格式返回当前日期
	 * @return String
	 */
	public static String getCurrentDate() {
		Date date = new Date();
		return toString(date, sdf);
	}
	
	/**
	 * 采用定义格式返回当前日期
	 * @param formatStr	定义格式
	 * @return String
	 */
	public static String getCurrentDate(String formatStr) {
		Date date = new Date();
		return toString(date, formatStr);
	}
	
	/**
	 * 采用yyyy-MM-dd HH:mm:ss格式返回当前时间
	 * @return String
	 */
	public static String getCurrentDatetime() {
		Date date = new Date();
		return toString(date, ldf);
	}
	
	/**
	 * 采用定义格式格式返回当前时间
	 * @return String
	 */	
	public static String getCurrentDatetime(String formatStr) {
		Date date = new Date();
		return toString(date, formatStr);
	}
	
	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 取下几天的日期
	 * 
	 * @param d
	 * @param day
	 * @return Date
	 */
	public static Date getDateOfNexts(Date d, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	/**
	 * 取下几天的日期
	 * 
	 * @param day
	 * @return Date
	 */
	public static Date getDateOfNexts(int day) {
		return getDateOfNexts(new Date(), day);
	}
	
	/**
	 * 取上一天的日期
	 * 
	 * @return Date
	 */
	public static Date getDateOfPrevDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getZeroTimeToday());
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	public static int getDayOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取某年某月的天数
	 * 
	 * @param year
	 *        int 年
	 * @param month
	 *        int 月份[1-12]
	 * @return int 天数
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 返回两个日期之间的时间间隔
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 * @Author wanghui
	 * @createDate 2017年1月22日 下午5:35:28
	 */
	public static int getDaysOfTowDiffDate(String startDate, String endDate) throws Exception {
		Date l_startDate = str2DateByFormat(startDate, "yyyy-MM-dd");
		Date l_endDate = str2DateByFormat(endDate, "yyyy-MM-dd");
		double l_startTime = getMillisOfDate(l_startDate);
		double l_endTime = getMillisOfDate(l_endDate);
		int betweenDays = (int) ((l_endTime - l_startTime) / 86400000.0D);
		return betweenDays;
	}
	
	/**
	 * 返回属于指定日期的最早的时间
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getEarliestTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期所在的月的第一天的日期
	 * 
	 * @param monthDate
	 *        月里任意一天的日期
	 * @return Date 第一天的日期
	 */
	public static Date getFirstDateOfMonth(Date monthDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date date = calendar.getTime();
		return getEarliestTime(date);
	}
	
	/**
	 * 获取指定日期所在的月的第一天的日期
	 * 
	 * @param monthDate
	 *        月里任意一天的日期
	 * @return Date 第一天的日期
	 */
	public static Date getFirstDateOfMonth(String monthDate) {
		Date date = toDate(monthDate);
		date = getFirstDateOfMonth(date);
		return date;
	}
	
	/**
	 * 获取指定日期的下一月第一天的日期
	 * 
	 * @param monthDate
	 *        月里任意一天的日期
	 * @return Date 下一月第一天的日期
	 */
	public static Date getFirstDateOfNextMonth(Date monthDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthDate);
		calendar.add(Calendar.MONTH, 1);
		Date date = calendar.getTime();
		return getEarliestTime(date);
	}
	
	/**
	 * 获取指定日期的下一周第一天的日期，星期天为第一天
	 * 
	 * @param weekDate
	 *        周里任意一天的日期
	 * @return Date 下一周第一天的日期
	 */
	public static Date getFirstDateOfNextWeek(Date weekDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weekDate);
		calendar.add(Calendar.DAY_OF_YEAR, 8 - calendar.get(Calendar.DAY_OF_WEEK));
		Date date = calendar.getTime();
		return getEarliestTime(date);
	}
	
	/**
	 * 获取指定日期所在的周的第一天的日期，星期天为第一天
	 * 
	 * @param weekDate
	 *        周里任意一天的日期
	 * @return Date 第一天的日期
	 */
	public static Date getFirstDateOfWeek(Date weekDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weekDate);
		calendar.add(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() - calendar.get(Calendar.DAY_OF_WEEK));
		Date date = calendar.getTime();
		return getEarliestTime(date);
	}
	
	public static int getHourOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取指定日期所在的月的第一天的日期
	 * 
	 * @param monthDate
	 *        月里任意一天的日期
	 * @return Date 第一天的日期
	 */
	public static Date getLastDateOfMonth(Date monthDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);
		Date date = calendar.getTime();
		return getEarliestTime(date);
	}
	
	/**
	 * 获取指定日期所在的月的第一天的日期
	 * 
	 * @param monthDate
	 *        月里任意一天的日期
	 * @return Date 第一天的日期
	 */
	public static Date getLastDateOfMonth(String monthDate) {
		Date date = toDate(monthDate);
		date = getLastDateOfMonth(date);
		return date;
	}
	
	/**
	 * 返回属于指定日期最晚的时间,如传入日期为2007-08-12 会返回一个时间 2007-08-12 23:59:59
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getLatestTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	public static double getMillisOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}
	
	public static int getMinuteOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}
	
	public static int getMonthOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * czqcs
	 * 
	 * @return 当前时间的小时和分钟（HH:mm）
	 */
	public static String getNowHourAndMiTime() {
		return DateUtil.toString(new Date(), "HH:mm");
	}
	
	/**
	 * 返回当前时间字符串（精确到秒"yyyy-MM-dd HH:mm:ss"）
	 * 
	 * @return String
	 */
	public static String getNowTimeString() {
		return DateUtil.toString(new Date(), DateUtil.LONG_DATETIME_FORMAT);
	}
	
	public static int getSecondOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}
	
	public static Date getStringToDate(String stringDate) throws Exception {
		Date date = sdf.parse(stringDate);
		return date;
	}
	
	public static Date getStringToLongDate(String stringDate) throws Exception {
		Date date = ldf.parse(stringDate);
		return date;
	}
	
	/**
	 * 返回今天的时间字符串（精确到天“yyyy-MM-dd”）
	 * 
	 * @return String
	 */
	public static String getTodayDateString() {
		return DateUtil.toString(new Date(), DateUtil.LONG_DATE_FORMAT);
	}
	
	public static String getYearFristDay() throws Exception {
		Date date = new Date();
		return getYearFristDay(date);
	}
	
	public static String getYearFristDay(Date date) throws Exception {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int year = cd.get(Calendar.YEAR);
		return year + "-01-01";
	}
	
	public static String getYearFristDay(String dateStr) throws Exception {
		Date date = getStringToDate(dateStr);
		return getYearFristDay(date);
	}
	
	public static String getYearLastDay() throws Exception {
		Date date = new Date();
		return getYearLastDay(date);
	}
	
	public static String getYearLastDay(Date date) throws Exception {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int year = cd.get(Calendar.YEAR);
		return year + "-12-31";
	}
	
	public static String getYearLastDay(String dateStr) throws Exception {
		Date date = getStringToDate(dateStr);
		return getYearLastDay(date);
	}
	
	public static int getYearOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 返回当天0点时刻
	 * 
	 * @return Date
	 */
	public static Date getZeroTimeToday() {
		return getEarliestTime(new Date());
	}
	
	/**
	 * 判断给定的字符串是否日期，日期格式为LONG_DATE_FORMAT
	 * 
	 * @param date
	 *        日期字符串
	 * @return 是日期返回true,否则返回false
	 */
	public static boolean isDate(String date) {
		return isDate(date, LONG_DATE_FORMAT);
	}
	
	/**
	 * 判断给定的字符串是否与指定的日期格式吻合
	 * 
	 * @param date
	 *        日期字符串
	 * @param format
	 *        日期格式
	 * @return 符合返回true，否则返回false
	 */
	public static boolean isDate(String date, String format) {
		if (null == date) {
			return false;
		}
		
		Date d = null;
		ParsePosition pos = new ParsePosition(0);
		d = toDate(date, format, pos);
		return (null == d ? false : true) && (pos.getIndex() == date.length());
	}
	
	public static Date str2DateByFormat(String strDate, String format) throws Exception {
		Date l_date = null;
		DateFormat df = new SimpleDateFormat(format);
		if ((strDate != null) && (!"".equals(strDate)) && (format != null) && (!"".equals(format))) {
			l_date = df.parse(strDate);
		}
		return l_date;
	}
	
	/**
	 * 作用不明确，可能是测试第一个参数是否与第二个格式相匹配，
	 * 如果匹配则会返回与第一个参数相同的串，否则返回null
	 * @param date
	 * @param format
	 * @return
	 */
	public static String stringDate2String(String date, String format) {
		String str = null;
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date d = df.parse(date);
			str = df.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 分别使用三种格式转换字符串为java.util.Date类型
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date toDate(String date) {
		Date d = toDate(date, LONG_DATE_FORMAT);
		if (null == d) {
			d = toDate(date, LONG_DATETIME_FORMAT);
		}
		if (null == d) {
			d = toDate(date, LONG_TIME_FORMAT);
		}
		return d;
	}
	
	/**
	 * 自动根据字符描述处理时间
	 * @param date
	 * @param mode 0表示0点，1表示从上班时刻，2表示下班时刻
	 * @return
	 * @Author wanghui
	 * @createDate 2016年8月13日 上午11:54:10
	 */
	public static Date toDate(String date, int mode){
		Date d = toDate(date, LONG_DATE_FORMAT);
		if (null == d) {
			if (1 == mode){
				date = date + " 08:00:00";
			}else if (2 == mode){
				date = date + " 17:00:00";
			}
			d = toDate(date, LONG_DATETIME_FORMAT);
		}
		if (null == d) {
			d = toDate(date, LONG_TIME_FORMAT);
		}
		return d;
		
	}
	
	/**
	 * 把符合日期格式的字符串转换为日期类型
	 * 
	 * @param date
	 *        日期字符串
	 * @param format
	 *        转换格式
	 * @return Date 日期类型,如果转换不成功返回null
	 */
	public static Date toDate(String date, String format) {
		ParsePosition pos = new ParsePosition(0);
		Date d = toDate(date, format, pos);
		if (null != d && pos.getIndex() != date.length()) {
			d = null;
		}
		return d;
	}
	
	/**
	 * 把符合日期格式的字符串转换为日期类型
	 * 
	 * @param date
	 *        日期字符串
	 * @param format
	 *        转换格式
	 * @param pos
	 * @see ParsePosition
	 * @return Date 日期类型,如果转换不成功返回null
	 */
	public static Date toDate(String date, String format, ParsePosition pos) {
		if (date == null) {
			return null;
		}
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(date, pos);
		} catch (Exception e) {
			log.error(e);
			d = null;
		}
		return d;
	}
	
	/**
	 * 采用定义格式返回指定时间
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String toString(Date date, DateFormat formater) {
		String dateStr = "";
		if (StringUtil.isNotNull(date)) {
			try {
				dateStr = formater.format(date);
			} catch (Exception e) {
				log.error(e);
			}
		}
		return dateStr;
	}
	
	/**
	 * 采用定义格式返回指定时间
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String toString(Date date, String formatStr) {
		SimpleDateFormat formater = new SimpleDateFormat(formatStr);
		return toString(date, formater);
	}

}
