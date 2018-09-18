package com.chuangyu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 操作日期工具
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午11:21:41   
 *
 */
public class DateUtil {
    
	public static final String FORMATDAYTOCHINATIME24H = "yyyy年MM月dd日";
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATETIME_FORMAT_CH = "yyyy年MM月dd日 HH时mm分ss秒";
	public static String DATETIME_FORMAT_STAMP = "yyyyMMddHHmmss";
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATE_FORMAT_SLASH = "yyyy/MM/dd";
	public static String TIME_FORMAT = "HH:mm:ss";
	public static String FILE_TIME_FORMAT = "yyyyMM";
	public static String YEAS_FORMAT = "yyyy";
	public static String DATE_FORMAT_CODE = "yyyyMMdd";
	public static String DATE_FORMAT_CODE_DETAIL = "yyyyMMddHH";
	public static String code = "yyyy-MM";

	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int ONE_THOUSAND_AND_NINE_HUNDRED = 1900;
	

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Parse date like "yyyy-MM-dd".
	 */
	public static Date parseDate(String d) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(d);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Parse date like "yyyy-MM-dd HH:mm:ss".
	 */
	public static Date parseDateTime(String d) {
		try {
			return new SimpleDateFormat(DATETIME_FORMAT).parse(d);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Parse date like "HH:mm:ss".
	 */
	public static Date parseTime(String d) {
		try {
			return new SimpleDateFormat(TIME_FORMAT).parse(d);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthEnd(String strdate) {
		Date date = parseDate(getMonthBegin(strdate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}
	
	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthBegin(String strdate) {
		Date date = parseDate(strdate);
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}
	/**
	 * 获取给定年的第一天
	 *
	 * @param date
	 * @return 2018-01-01
	 */
	public static Date getFirstdayOfYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		return  calendar.getTime();
	}

	/**
	 * 获取给定月的最后一天
	 *
	 * @param date
	 * @return 2018-08-31
	 */
	public static Date getLastdayOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.roll(Calendar.DATE, -1);
		// 第一天
		return ca.getTime();
	}


	/**
	 * 获取给定月的第一天
	 *
	 * @param date
	 * @return 2018-08-01
	 */
	public static Date getFirstdayOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		// 第一天
		return ca.getTime();
	}
	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public String formatDate(Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}
	
	/**
	 * @根据当前日期计算n天后的日期
	 * @return String
	 */
	public static Date afterNDay(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, n);
		Date destDay = c.getTime();
		return destDay;
	}
	/**
	 * @根据当前日期计算n月后的日期
	 * @return String
	 */
	public  static Date afterNMonth(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, n);
		Date destDay = c.getTime();
		return destDay;
	}
	/**
	 * @根据当前日期计算n年后的日期
	 * @return String
	 */
	public  static Date afterNYear(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, n);
		Date destDay = c.getTime();
		return destDay;
	}

	/**
	 * 两个日期间的天数
	 */
	public static int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}
	/**
	 * 得到day的起始时间点。
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到day的终止时间点.
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
    
    public static boolean  verifyDate(String birthday){
    	String [] array = birthday.split("-");
    	if(null == array || array.length != THREE || array[0].length() != FOUR || array[1].length() != TWO || array[TWO].length() != TWO){
    		return false;
    	} 
    		
    	int curyear = DateUtil.getYear(new Date());
    	try {
			int studyear = Integer.parseInt(array[0]);
			if(studyear <= ONE_THOUSAND_AND_NINE_HUNDRED || studyear > curyear){
				return false;
			}
			String curDateStr = formatDateByFormat(new Date(), DATE_FORMAT);
			if(birthday.compareTo(curDateStr) >= 0){
				return false;
			} 
			return checkDate(birthday);
		} catch (NumberFormatException e) {
			return false;
		}
    }
    
    private static boolean checkDate(String date) {
		// System.out.println(date);
		// 判断年月日的正则表达式，接受输入格式为2010-12-24，可接受平年闰年的日期
		String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcherObj = pattern.matcher(date);
		return matcherObj.matches();
	}
    
    /**
     * 获取当前时间Date格式
     * @return
     */
    public static Date getNowDate(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
    	Date date=new Date();
    	sdf.format(date);
    	return date;
    }
    
    public static void main(String [] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018,7,1);
		//calendar.add(Calendar.DATE,-15);
		System.out.println(calendar.getTime());
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2018,7,15);
		//calendar2.add(Calendar.DATE,-15);
		System.out.println(calendar2.getTime());
		System.out.println(getIntervalDays(calendar.getTime(),calendar2.getTime()));

		//System.out.println(formatDateByFormat(afterNMonth(new Date(),-1),DATE_FORMAT));
    }
	
}
