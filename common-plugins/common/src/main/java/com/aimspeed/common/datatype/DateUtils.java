package com.aimspeed.common.datatype;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;  
  
/**
 * 日期处理常用类 
 * @author AimSpeed
 */
public class DateUtils {  
	
	/**
	 * 日期格式化对象（年月日[yyyy-MM-dd]）
	 */
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	
	/**
	 * 日期格式化对象（年月[yyyy-MM]）
	 */
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");  
	
	/**
	 * 日期格式化对象，数据库使用的日期格式  （年月日[yyyyMMdd]）
	 */
    public static SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 日期格式化对象（年月日 时分秒[yyyy-MM-dd HH:mm:ss]）
	 */
    public static SimpleDateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    
    /**
     *  日期格式化常量（年月日[yyyy-MM-dd]）
     */
    public static final String Y_M_D = "yyyy-MM-dd";  
    
    /**
     *  日期格式化常量（年月日 时分[yyyy-MM-dd HH:mm]）
     */
    public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";  
    
    /**
     *  日期格式化常量（年月日 时分秒[yyyy-MM-dd HH:mm:ss]）
     */
    public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";    
    
    /**
     *  日期格式化常量（年月日[yyyyMMdd]）
     */
    public static final String YMD = "yyyyMMdd";     
    
    /**
     *  日期格式化常量（年月日 时分[yyyyMMddHHmm]）
     */
    public static final String YMDHM = "yyyyMMddHHmm";  
    
    /**
     *  日期格式化常量（年月日 时分秒[yyyyMMddHHmmss]）
     */
    public static final String YMDHMS = "yyyyMMddHHmmss";   
    
	private static Calendar gregorianCalendar = null;

	static{
		gregorianCalendar = new GregorianCalendar();
	}
 	
	/**
	 * 两时间相减 
	 * @author AimSpeed
	 * @param startTime 开始时间(yyyy-MM-dd HH:mm:ss)
	 * @param endTime 结束时间 (yyyy-MM-dd HH:mm:ss)
	 * @return String 返回相减后的 天时分(days+"-"+hours+"-"+minutes)
	 */
    public static String getSubTwoTime(String startTime,String endTime){  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
       
        try{  
              
            Date d1 = df.parse(startTime);  
            Date d2 = df.parse(endTime);  
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
            long days = diff / (1000 * 60 * 60 * 24);  
              
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);  
//    System.out.println(""+days+"天"+hours+"小时"+minutes+"分");  
            if(hours <0){  
                hours = new BigDecimal(hours).abs().intValue();  
            }  
            if(minutes <0){  
                minutes = new BigDecimal(minutes).abs().intValue();  
            }  
            return days+"-"+hours+"-"+minutes;  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return "";  
    }  

    /**
     * 两时间相减
     * @author AimSpeed
     * @param startTime 开始时间(yyyy-MM-dd)
     * @param endTime 结束时间(yyyy-MM-dd)
     * @return String 天数
     */
    public static long getSubTwoTimeYY(String startTime,String endTime){  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
          
        try{  
            Date d1 = df.parse(startTime);  
            Date d2 = df.parse(endTime);  
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
            long days = diff / (1000 * 60 * 60 * 24);  
            return days;  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return 0;  
    }  

    /**
     * 创建一个"yyyy-MM-dd"日期的格式化对象 
     * @author AimSpeed
     * @return SimpleDateFormat "yyyy-MM-dd"日期的格式化对象 
     */
    private static SimpleDateFormat newLongYMDFormat() {  
        return new SimpleDateFormat("yyyy-MM-dd");  
    }  
  
    /**
     * 创建一个"yyyy-MM-dd HH:mm:ss"日期的格式化对象 
     * @author AimSpeed
     * @return SimpleDateFormat "yyyy-MM-dd HH:mm:ss"日期的格式化对象 
     */
    private static SimpleDateFormat newLongYMDHMSFormat() {  
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    }  
  
    /**
     * "yyyyMMddHHmmss"格式的日期转换为"yyyy-MM-dd HH:mm:ss"格式的日期 
     * @author AimSpeed
     * @param shortYMDHMS "yyyyMMddHHmmss"格式的日期 
     * @return String "yyyy-MM-dd HH:mm:ss"格式的日期 
     * @throws ParseException  
     */
    public static String toLongYMDHMS(String shortYMDHMS) throws ParseException {  
        return newLongYMDHMSFormat().format(newShortYMDHMSFormat().parse(shortYMDHMS));  
    }  
  
    /**
     * 获得"yyyy-MM-dd"格式的当前日期 
     * @author AimSpeed
     * @return String 返回"yyyy-MM-dd"格式的当前日期 
     */
    public static String getLongYMD() {  
        return newLongYMDFormat().format(new Date());  
    }  

    /**
     * 获得"X年X月X日"格式的当前日期 
     * @author AimSpeed
     * @return String 
     */
    public static String getLongYMDChina() {  
        String str = newLongYMDFormat().format(new Date());  
        return str.split("-")[0]+"年"+str.split("-")[1]+"月"+str.split("-")[2]+"日";  
    }  
  
    /**
     * 创建一个"yyyyMMdd"日期的格式化对象 
     * @author AimSpeed
     * @return SimpleDateFormat "yyyyMMdd"日期的格式化对象 
     */
    private static SimpleDateFormat newShortYMDFormat() {  
        return new SimpleDateFormat("yyyyMMdd");  
    }  
  
    /**
     * 创建一个"yyyyMMddHHmmss"日期的格式化对象 
     * @author AimSpeed
     * @return SimpleDateFormat "yyyyMMddHHmmss"日期的格式化对象 
     */
    private static SimpleDateFormat newShortYMDHMSFormat() {  
        return new SimpleDateFormat("yyyyMMddHHmmss");  
    }  
  
    /**
     * 获得"yyyyMMddHHmmss"格式的当前日期 
     * @author AimSpeed
     * @return String 返回"yyyyMMddHHmmss"格式的当前时间 
     */
    public static String getShortYMDHMS() {  
        return newShortYMDHMSFormat().format(new Date());  
    }  
  
    /**
     * "yyyyMMdd"格式的日期转换为"yyyy-MM-dd"格式的日期 
     * @author AimSpeed
     * @param shortYMD "yyyyMMdd"格式的日期 
     * @return String "yyyy-MM-dd"格式的日期 
     */
    public static String toLongYMD(String shortYMD) {  
        try {  
            return newLongYMDFormat().format(newShortYMDFormat().parse(shortYMD));  
        } catch (ParseException e) {  
            // Auto-generated catch block  
  
            e.printStackTrace();  
            return "";  
        }  
    }  
  
    /**
     * 生成日期yyyyMMdd 
     * @author AimSpeed
     * @return String 日期yyyyMMdd的字符串
     */
    public static String getDbDate() {  
        return dateFormatDB.format(new Date());  
    }  
  
    /**
     * 把日期yyyy-MM-dd格式转换成yyyyMMDD日期格式 
     * @author AimSpeed
     * @param dateStr 日期yyyy-MM-dd格式
     * @return String 返回yyyyMMDD日期格式 
     */
    public static String convertClientDateToDbDate(String dateStr) {  
        String dbDateStr = null;  
        try {  
            dbDateStr = dateFormatDB.format(dateFormat.parse(dateStr));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return dbDateStr;  
    }  
  
    /**
     * 功能：格式化日期字符串 例如：2008-8-8  转换为2008-08-08 
     * @author AimSpeed
     * @param dateStr 需要格式化的字符串
     * @return String 格式化后的字符串
     */
    public static String getDateStrFormat(String dateStr) {  
        try {  
            Date date = dateFormat.parse(dateStr);  
            return dateFormat.format(date);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    /**
     * 格式化日期字符串  例如：2008-8  转换为2008-08 
     * @author AimSpeed
     * @param dateStr 需要格式化的字符串
     * @return String 格式化后的字符串
     */
    public static String getDateStrFormat2(String dateStr) {  
        try {  
            Date date = dateFormat2.parse(dateStr);  
            return dateFormat2.format(date);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    /**
     * 格式化日期字符串  例如：2008-8-8 转 20080808  
     * @author AimSpeed
     * @param dateStr 需要格式化的字符串
     * @return String 格式化后的字符串
     */
    public static String getDateStrFormatyyyyMMdd(String dateStr) {  
        try {  
            Date date = dateFormat.parse(dateStr);  
            return dateFormatDB.format(date);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /**
     * 解析数据库中的时间字符串 格式:yyyy-MM-dd HH:mm:ss 
     * @author AimSpeed
     * @param dateTimeStr 需要格式化的字符串
     * @return Date 格式化后的字符串
     */
    public static Date parseDateTime(String dateTimeStr) {  
        Date date = null;  
        try {  
            date = dataTimeFormat.parse(dateTimeStr);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return date;  
    }  
  
    /**
     * 计算两个日期之间的天数
     * @author AimSpeed
     * @param startDate 开始时间 
     * @param endDate 结束时间 
     * @return int 
     */
    public static int calcDays(String startDate, String endDate) {  
        int days = 1;  
        try {  
            long start = dateFormat.parse(startDate).getTime();  
            long end = dateFormat.parse(endDate).getTime();  
            days = (int) ((end - start) / (24 * 60 * 60 * 1000));  
        } catch (ParseException e) {  
            e.printStackTrace();  
            return -1;  
        }  
        return days;  
    }  
  
    /**
     * 计算两个日期之间的天数 
     * @author AimSpeed
     * @param startDate 开始时间 
     * @param endDate 结束时间 
     * @return int 相差的天数
     */
    public static int calcDay(String startDate, String endDate) {  
        int days = 1;  
        try {  
            long start = dateFormatDB.parse(startDate).getTime();  
            long end = dateFormatDB.parse(endDate).getTime();  
            days = (int) ((end - start) / (24 * 60 * 60 * 1000));  
        } catch (ParseException e) {  
            e.printStackTrace();  
            return -1;  
        }  
        return days;  
    }  
  
    /**
     * 指定日期加上指定天数 
     * @author AimSpeed
     * @param date 日期 
     * @param day 天数 
     * @return Date 加上后的时间
     */
    public static Date addDate(Date date, int day) {  
        Calendar c = Calendar.getInstance();  
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);  
        return c.getTime();  
    }  
  
    /**
     * 指定日期加上指定分钟
     * @author AimSpeed
     * @param date 日期 
     * @param minute 分钟
     * @return Date 加上后的时间
     */
    public static Date addMinute(Date date, int minute) {  
        Calendar c = Calendar.getInstance();  
        c.setTimeInMillis(getMillis(date) + ((long) minute) * 60 * 1000);  
        return c.getTime();  
    }  
  
    /**
     * 指定日期加上指定妙
     * @author AimSpeed
     * @param date 日期 
     * @param second 秒
     * @return Date 加上后的时间
     */
    public static Date addSecond(Date date, int second) {  
        long s = date.getTime();  
        s = s + second * 1000;  
        return new Date(s);  
    }  

    /**
     * 分钟相差 minute的时间值 
     * @author AimSpeed
     * @param date 日期
     * @param minute 分钟
     * @return Date 相差后的时间
     */
    public static Date getDateByMinuteAdd(Date date, int minute) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.MINUTE, minute);  
        return calendar.getTime();  
    }  
  
    /**
     * :两个日期相隔天数 
     * @author AimSpeed
     * @param startDate 开始日期 
     * @param endDate 结束日期 
     * @return int 相差天数
     */
    public static int diffDate(Date startDate, Date endDate) {  
        long endMillis = endDate.getTime();  
        long startMillis = startDate.getTime();  
        long s = (endMillis - startMillis) / (24 * 3600 * 1000);  
        return (int) s;  
    }  
  
    /**
     * 传入时间按所需格式返回时间字符串 
     * @author AimSpeed
     * @param date 需要格式化的时间对象
     * @param format yyyy-MM-dd HH:mm:ss | yyyy年MM月dd日 HH时mm分ss秒 
     * @return String 格式化后的时间字符串
     */
    public static String format(Date date, String format) {  
        String result = "";  
        try {  
            if (date == null) {  
                date = new Date();// 如果时间为空,则默认为当前时间  
            }  
            if (null != format) {// 默认格式化形式  
                format = "yyyy-MM-dd";  
            }  
            DateFormat df = new SimpleDateFormat(format);  
            result = df.format(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    } 

    /**
     * 日期到当前相隔多少年 
     * @author AimSpeed
     * @param date 需要计算的日期
     * @return int 相差的年数
     */
    public static int getApartNowYear(Date date) {  
    	int now = getYear(new Date());
    	int pre = getYear(date);
        return now - pre;  
    }
    
    /**
     * 获取时间的年
     * @author AimSpeed
     * @param date 时间
     * @return int 年
     */
    public static int getYear(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.YEAR);  
  
    }  
  
    /**
     * 获取时间的月
     * @author AimSpeed
     * @param date 时间
     * @return int 月
     */
    public static int getMonth(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.MONTH) + 1;  
    }  
  
    /**
     * 获取时间的日
     * @author AimSpeed
     * @param date 时间
     * @return int 日
     */
    public static int getDay(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.DAY_OF_MONTH);  
    }  
  
    /**
     * 获取时间的小时
     * @author AimSpeed
     * @param date 时间
     * @return int 小时
     */
    public static int getHour(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.HOUR_OF_DAY);  
    }  
  
    /**
     * 获取时间的分
     * @author AimSpeed
     * @param date 时间
     * @return int 分
     */
    public static int getMinute(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.MINUTE);  
    }  
  
    /**
     * 获取时间的星期
     * @author AimSpeed
     * @param date 时间
     * @return int 星期
     */
    public static int getChinaWeek(Date date) {  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;  
        if (week == 0) {  
            return 7;  
        } else {  
            return week;  
        }  
    }  
  
    /**
     * 获取时间的秒
     * @author AimSpeed
     * @param date 时间
     * @return int 秒
     */
    public static int getSecond2(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.SECOND);  
    }  
  
    /**
     * 获取时间的毫秒
     * @author AimSpeed
     * @param date 时间
     * @return long 
     */
    public static long getMillis(Date date) {  
        if (date == null) {  
            date = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.getTimeInMillis();  
    }  
  
    /**
     * 获取当前月的第一天日期 
     * @author AimSpeed
     * @return Date 当前月的第一天日期 
     */
    public static Date getMonFirstDay() {  
        Date date = new Date();  
        Calendar c = Calendar.getInstance();  
        c.set(getYear(date), getMonth(date) - 1, 1);  
        return c.getTime();  
    }  
  
    /**
     * 获取当前月的最后一天日期 
     * @author AimSpeed
     * @return Date 当前月的最后一天日期 
     */
    public static Date getMonLastDay() {  
        Date date = new Date();  
        Calendar c = Calendar.getInstance();  
        c.set(getYear(date), getMonth(date), 1);  
        c.setTimeInMillis(c.getTimeInMillis() - (24 * 3600 * 1000));  
        return c.getTime();  
    }  

    /**
     * 获取上个月的最后一天日期 
     * @author AimSpeed
     * @return Date 上个月的最后一天日期 
     */
    public static Date getMonUpDay() {  
        Date date = new Date();  
        Calendar c = Calendar.getInstance();  
        c.set(getYear(date), getMonth(date)-1, 1);  
        c.setTimeInMillis(c.getTimeInMillis() - (24 * 3600 * 1000));  
        return c.getTime();  
    }  
      
    /**
     * 获得给定日期当月的天数
     * @author AimSpeed
     * @param cal 
     * @return int 
     */
    public static int getDays(Calendar cal) {  
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }  
      
      
    /**
     * 日期转日历
     * @author AimSpeed
     * @param date 日期
     * @return Calendar 转换后的日历
     */
    public static Calendar convertDateToCal(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        return cal;  
    }  
      
    /**
     * 获取当前日期 
     * @author AimSpeed
     * @return String 格式:2008-02-02 23:11:10 
     */
    public static String getCurrentDateTime() {  
        Date date = new Date();  
        return dataTimeFormat.format(date);  
    }  
  
    /**
     * 获取当前日期 
     * @author AimSpeed
     * @return String 格式:20101010 
     */
    public static String getCurrentDate() {  
        Date date = new Date();  
        return dateFormat.format(date);  
    }  
      
    /**
     * 创建一个"yyyyMM"日期的格式化对象 
     * @author AimSpeed
     * @return SimpleDateFormat "yyyyMM"日期的格式化对象 
     */
    private static SimpleDateFormat newShortYMFormat() {  
        return new SimpleDateFormat("yyyyMM");  
    }  
  
    /**
     * 获得距离输入月份的diffMonth月的日期 
     * @author AimSpeed
     * @param month "yyyyMM"格式的日期 
     * @param diffMonth 相差的月数 
     * @return String 格式的日期 
     */
    public static String getShortYMDiffMonth(String month, int diffMonth) {  
        SimpleDateFormat sdf = newShortYMFormat();  
        try {  
            sdf.parse(month);  
        } catch (ParseException e) {  
            // Auto-generated catch block  
            e.printStackTrace();  
        }  
        Calendar c = sdf.getCalendar();  
        c.add(Calendar.MONTH, diffMonth);  
        return sdf.format(c.getTime());  
    }  

    /**
     * 获得距离给定日期diffDay天的日期 
     * @author AimSpeed
     * @param shortYMD "yyyyMMdd"格式的日期 
     * @param diffDay 相差的天数
     * @return String "yyyyMMdd"格式的日期  
     */
    public static String getShortYMDDiffDay(String shortYMD, int diffDay) {  
        SimpleDateFormat sdf = newShortYMDFormat();  
        try {  
            sdf.parse(shortYMD);  
        } catch (ParseException e) {  
            // Auto-generated catch block  
            e.printStackTrace();  
        }  
        Calendar c = sdf.getCalendar();  
        c.add(Calendar.DATE, diffDay);  
        return sdf.format(c.getTime());  
    }  
  
    /**
     * 获取某月份的最后一天 
     * @author AimSpeed
     * @param shortYM 月份 
     * @return String 月份的最后一天 
     */
    public static String getEndDayOfMonth(String shortYM) {  
        String month = "";  
        try {  
            month = DateUtils.getShortYMDiffMonth(shortYM, 1);  
        } catch (Exception e) {  
            // Auto-generated catch block  
            e.printStackTrace();  
        }  
        return DateUtils.getShortYMDDiffDay(month + "01", -1);  
    }  
  
    /**
     * 获得两个日期之间的所有日期列表（包括起始日期和结束日期） 
     * @author AimSpeed
     * @param startShortYMD "yyyyMMdd"格式的起始日期 
     * @param endShortYMD "yyyyMMdd"格式的结束日期 
     * @return "yyyyMMdd"格式的日期列表 
     * @throws ParseException List 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public static List getShortYMDList(String startShortYMD, String endShortYMD) throws ParseException {  
        SimpleDateFormat startDateFormat = newShortYMDFormat();  
        startDateFormat.parse(startShortYMD);  
        Calendar startCal = startDateFormat.getCalendar();  
  
        SimpleDateFormat endDateFormat = newShortYMDFormat();  
        endDateFormat.parse(endShortYMD);  
        Calendar endCal = endDateFormat.getCalendar();  
  
        List dateList = new ArrayList();  
        while (startCal.before(endCal)) {  
            dateList.add(startDateFormat.format(startCal.getTime()));  
            startCal.add(Calendar.DATE, 1);  
        }  
  
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {  
            dateList.add(startDateFormat.format(endCal.getTime()));  
        }  
  
        return dateList;  
    }  
  
    /**
     * 获得两个日期之间的所有日期列表（包括起始日期和结束日期） 
     * @author AimSpeed
     * @param startShortYM "yyyyMM"格式的起始日期 
     * @param endShortYM "yyyyMM"格式的结束日期 
     * @return "yyyyMM"格式的日期列表 
     * @throws ParseException List 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public static List getShortYMList(String startShortYM, String endShortYM) throws ParseException {  
        SimpleDateFormat startDateFormat = newShortYMFormat();  
        startDateFormat.parse(startShortYM);  
        Calendar startCal = startDateFormat.getCalendar();  
  
        SimpleDateFormat endDateFormat = newShortYMFormat();  
        endDateFormat.parse(endShortYM);  
        Calendar endCal = endDateFormat.getCalendar();  
  
        List dateList = new ArrayList();  
        while (startCal.before(endCal)) {  
            dateList.add(startDateFormat.format(startCal.getTime()));  
            startCal.add(Calendar.MONTH, 1);  
        }  
  
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {  
            dateList.add(startDateFormat.format(endCal.getTime()));  
        }  
  
        return dateList;  
    }  
  
    /**
     * 验证输入的日期是否合法 
     * @author AimSpeed
     * @param expDate 日期
     * @return boolean false,true
     */
    public static boolean checkExpDate(String expDate) {  
        int year = Integer.parseInt(expDate.substring(0, 4));  
        int month = Integer.parseInt(expDate.substring(4, 6));  
        int day = Integer.parseInt(expDate.substring(6, 8));  
        if (month > 12 || month < 1) {  
            return false;  
        }  
  
        int[] monthLengths = new int[] { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };  
  
        if (isLeapYear(year)) {  
            monthLengths[2] = 29;  
        } else {  
            monthLengths[2] = 28;  
        }  
  
        int monthLength = monthLengths[month];  
        if (day < 1 || day > monthLength) {  
            return false;  
        }  
        return true;  
    }  
  
    /**
     * 是否是闰年  
     * @author AimSpeed
     * @param year 年
     * @return boolean false,true
     */
    private static boolean isLeapYear(int year) {  
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);  
    }  

    /**
     * 根据传过来的字符串型的date，转换成对应的日期 
     * @author AimSpeed
     * @param date 日期
     * @return Date 转换后的日期
     */
    public static Date str2Date(String date) {  
        Date ret = null;  
        if (date.length() == 10) {  
            try {  
                ret = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        if (date.length() == 16) {  
            try {  
                ret = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        if (date.length() == 19) {  
            try {  
                ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        if (date.length() == 13) {  
            try {  
                ret = new SimpleDateFormat("yyyy-MM-dd HH").parse(date);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        if (date.length() == 7) {  
            try {  
                ret = new SimpleDateFormat("yyyy-MM").parse(date);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        return ret;  
    }  
    
    /**
     * 获取到一个时间的月是上旬/中旬/下旬 
     * @author AimSpeed
     * @param date 日期
     * @return int 1 ：上旬  2： 中旬  3： 下旬 
     */
    public static int getEarlyMidLate(Date date) {  
        int day=getDay(date);  
        int earlyMidLate=0;  
        if(1<=day && day<= 10){  
            earlyMidLate=1;  
        }  
        if(11<=day && day<=20){  
            earlyMidLate=2;  
        }  
        if(20<day){  
            earlyMidLate=3;  
        }  
        return earlyMidLate;  
    }  
      
    /**
     * 获取到哪一年的第几天 
     * @author AimSpeed
     * @param date 日期
     * @return int 一年的第几天
     */
    public static int dateToJulian(Date date){  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int year = calendar.get(Calendar.YEAR)%100;  
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);  
        return year*1000 + dayOfYear;  
    }  
      
    /**
     * 时间减去 几小时 
     * @author AimSpeed
     * @param strDate 日期字符串
     * @param a 要减去的时间
     * @return String 返回时间字符串
     */
    public static String getSubTwoDate(String strDate,int a){  
        try{  
             SimpleDateFormat dft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
             Calendar   dar=Calendar.getInstance();      
             dar.setTime(dft.parse(strDate));      
             dar.add(java.util.Calendar.HOUR_OF_DAY, -a);       
             return dft.format(dar.getTime());  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return "";  
    }  

    /**
     * 判断两个时间是否相等 精确到分 
     * @author AimSpeed
     * @param strDate 字符串日期
     * @param date 日期
     * @return boolean false,true 
     */
    public static boolean getCurrentDate(String strDate,Date date) {  
        SimpleDateFormat dft=new SimpleDateFormat("yyyy-MM-dd HH:mm");   
        Calendar   dar=Calendar.getInstance();      
         try {  
            dar.setTime(dft.parse(strDate));  
        } catch (ParseException e) {  
            // TODO Auto-generated catch block  
                e.printStackTrace();  
            }     
            return dft.format(dar.getTime()).equals(dft.format(date));  
    } 
        
    /**
     * 获取当前日期星期日日期    
     * @author AimSpeed
     * @return Date 当前日期星期日日期   
     */
	public static Date getLastDayOfWeek() {
		gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		gregorianCalendar.setTime(new Date());
		gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取指定月的第一天
	 * @author AimSpeed
	 * @param date 日期
	 * @return Date 指定月的日期
	 */
	public static Date getFirstDayOfMonth(Date date) {
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期前一天
	 * @author AimSpeed
	 * @param date 日期
	 * @return Date 前一天的日期
	 */
	public static Date getDayBefore(Date date) {
		gregorianCalendar.setTime(date);
		int day = gregorianCalendar.get(Calendar.DATE);
		gregorianCalendar.set(Calendar.DATE, day - 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期后一天
	 * @author AimSpeed
	 * @param date 日期
	 * @return Date 后一天的日期
	 */
	public static Date getDayAfter(Date date) {
		gregorianCalendar.setTime(date);
		int day = gregorianCalendar.get(Calendar.DATE);
		gregorianCalendar.set(Calendar.DATE, day + 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取到当前年
	 * @author AimSpeed
	 * @return int 
	 */
	public static int getNowYear() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * @author AimSpeed
	 * @return int 
	 */
	public static int getNowMonth() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当月天
	 * @author AimSpeed
	 * @return int 
	 */
	public static int getNowMonthDay() {
		Calendar d = Calendar.getInstance();
		return d.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 获取当前时间提前多少个月
	 * @author AimSpeed
	 * @param monty 提前的月数
	 * @return Date 提前后的月时间
	 */
	public static Date getFirstMonth(int monty) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -monty);
		return c.getTime();
	}
	
	/**
	 * 获取前后日期 date开始算的时间，天数 负数往前几天，正数往后几天
	 * @author AimSpeed
	 * @param date 要计算的日期
	 * @param day 天数 负数往前几天，正数往后几天
	 * @return String 计算后的日期字符串
	 */
	public static String  getDateDiff(Date date,Integer day){
        // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 将字符串的日期转为Date类型
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, day);
        Date date1 = calendar.getTime();
        return sdf.format(date1);
    }
	
	
	//--------------------- 计算生日日期 - 生肖

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座",
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座" };

    /**
     * 通过生日日期计算星座
     * @author AimSpeed
     * @param birthday 日期
     * @return String 生肖
     */
    public static String getConstellation(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }
    
    /**
     * 通过生日日期计算属相
     * @author AimSpeed
     * @param birthday 日期
     * @return String 属相
     */
    public static String getYearToAnimals(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int year = calendar.get(Calendar.YEAR);
        int start = 1900;
        String[] years = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪" };
        return years[(year - start) % years.length];
    }
	
	
}
