package com.ori.learnsquare1.common.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 字符串转换日期
     *
     * @param dateStr
     * @param pattern 默认格式: yyyy-MM-dd
     * @return
     */
    public static Date strToDate(String dateStr, String pattern) {
        try {
            if (pattern == null || "".equals(pattern))
                pattern = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String formatDate(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * @param time
     * @return
     */
    public static String formatDate7(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time - 60 * 60 * 24 * 1000 * 7);
        return sf.format(d);
    }

    /**
     * 日期转换成字符串
     *
     * @param date 格式(yyyy-MM-dd HH:mm:ss)
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 日期转换成字符串
     *
     * @param date 格式(yyyy/MM/dd)
     * @return str
     */
    public static String DateToStr1(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 将字符串装日期 格式(yyyy/MM/dd)
     **/
    public static Date ConverToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.parse(strDate);
    }


    /**
     * lr 20150808
     * 日期转换为字符串
     *
     * @param date
     * @param dateformat 默认 yyyy-MM-dd
     * @return
     */
    public static String toDateTimeString(Date date, String dateformat) {
        try {
            if (date == null)
                return "";
            if (dateformat == null || "".equals(dateformat.trim()))
                dateformat = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
            return dateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 转换指定的时间
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public String convertTime(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date curDate = new Date(time);
        return formatter.format(curDate);
    }

    // 工具方法
    private static String getFormat(String format) {
        Date de = new Date();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(de);
    }

    public static String _getFormatTime(String format) {
        if (null == format || "".equals(format) || format.length() <= 0){
            return getFormat("yyyyMMddHHmmss");
        }
        return getFormat(format);
    }

    /**
     * 获取当前系统当前时间(格式:yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getCurTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取当前系统当前时间(格式:yyyy-MM-dd HH:mm)
     *
     * @return
     */
    public static String getCurTimeEndMin() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sDateFormat.format(new Date());
        return date;
    }


    // /根据系统时间返回yyyy-MM-dd HH:mm:ss格式日期
    public static String getFormatTime() {
        return getFormat("yyyy-MM-dd HH:mm:ss");
    }

    // 根据系统时间返回yyyy-MM-dd格式日期
    public static String getFormaTimeByYMD() {
        return getFormat("yyyy-MM-dd");
    }

    // 根据给定字段串日期转换成日期
    public static Date setDateByString(String date) {
        SimpleDateFormat simDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = simDateFormat.parse(date);
            return newDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;

    }

    // 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = nowdate == null ? new Date() : DateUtil.setDateByString(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间，格式为：YYYY-MM-DD
     *
     * @param nowdate 当前时间
     * @param delay   前移（负数）、后延（正数）
     * @return
     */
    public static String getNextDayByDate(String nowdate, int delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = DateUtil.setDateByString(nowdate);
            long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    private static SimpleDateFormat dateFormatStandard = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static SimpleDateFormat ADateFormatStandard = new SimpleDateFormat(
            "yyyyMMdd");

    public static Calendar getCalendar(String string, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat(format.trim());
            Date date = df.parse(string.trim());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Get the previous one day
     *
     * @param string , string
     * @return calendar
     */
    public static Calendar getPreviousCalendar(String string, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat(format.trim());
            Date date = df.parse(string.trim());
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Get the next one day
     *
     * @param string , string
     * @return calendar
     */
    public static Calendar getNextCalendar(String string, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat(format.trim());
            Date date = df.parse(string.trim());
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Get the Calendar after today
     *
     * @return nextCalendar
     */
    public static Calendar getCalendarAfterToday() {
        Calendar nextCalendar = Calendar.getInstance();
        nextCalendar.add(Calendar.DAY_OF_MONTH, 1);

        return nextCalendar;
    }

    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("finally")
    public static String transformDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return format.format(date);
        }
    }

    /**
     * Get the formate Date String like this: from 20121230 icon_back 2012-12-30
     *
     * @param dateString
     * @return formatDateString
     */
    public static String getFormatDateString(String dateString) {
        String formatDateString = "";
        if (dateString.trim().length() == 8) {
            formatDateString = dateString.substring(0, 4) + "-"
                    + dateString.substring(4, 6) + "-"
                    + dateString.substring(6, 8);
        } else
            formatDateString = dateString;
        return formatDateString;
    }

    /**
     * Get the formate time String like this: from 1800 icon_back 18:00
     *
     * @param time
     * @return formatDateString
     */
    public static String getStandardTimeString(String time) {

        String standardTime;

        if (time.trim().length() == 4) {
            standardTime = time.trim().substring(0, 2) + ":"
                    + time.trim().substring(2, 4);
        } else if (time.trim().length() == 6) {
            standardTime = time.trim().substring(0, 2) + ":"
                    + time.trim().substring(2, 4) + time.trim().substring(4, 6);
        } else
            standardTime = time.trim();

        return standardTime;
    }

    /**
     * Get the digit time like this: from 00:12+1 icon_back 1452
     *
     * @param time
     * @return minutes
     */
    public static long getDigitTime(String time) {
        long minutes = 0;
        time = time.trim().replace(":", "");
        if (time.length() == 4) {
            String timeHour = time.substring(0, 2);
            String timeMinute = time.substring(2, 4);

            minutes = Long.valueOf(timeHour) * 60 + Long.valueOf(timeMinute);
        } else if (time.length() == 6) {
            String timeHour = time.substring(0, 2);
            String timeMinute = time.substring(2, 4);
            String timeDay = time.substring(5, 6);

            minutes = Long.valueOf(timeDay) * 24 * 60 + Long.valueOf(timeHour)
                    * 60 + Long.valueOf(timeMinute);
        }

        return minutes;
    }

    /**
     * Calculate the number of days between two Calendar
     *
     * @param cal1
     * @param cal2
     * @return int
     */
    public static int daysBetween(Calendar cal1, Calendar cal2) {

        cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH),
                cal1.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH),
                cal2.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        long time1 = cal1.getTimeInMillis();

        long time2 = cal2.getTimeInMillis();

        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * Get the format of yyyy-MM-dd date string
     *
     * @param calendar
     * @return String
     */
    public static String getStandardDate(Calendar calendar) {
        return dateFormatStandard.format(calendar.getTime());
    }

    /**
     * Get the format of yyyyMMdd date string
     *
     * @param calendar
     * @return String
     */
    public static String getAStandardDate(Calendar calendar) {
        return ADateFormatStandard.format(calendar.getTime());
    }

    /**
     * Get the time of the day
     *
     * @param calendar
     * @return String
     */
    public static String getTheDayTime(Calendar calendar) {
        if ((calendar.get(Calendar.HOUR_OF_DAY)) < 12)
            return "早上";
        else if ((calendar.get(Calendar.HOUR_OF_DAY)) == 12)
            return "中午";
        else if ((calendar.get(Calendar.HOUR_OF_DAY)) <= 18)
            return "下午";
        else
            return "晚上";
    }

    /**
     * 获取对应的中文日期
     *
     * @param calendar
     * @return
     */
    public static String getDateOfWeek(Calendar calendar) {
        String result = "";
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                result = "星期日";
                break;
            case 2:
                result = "星期一";
                break;
            case 3:
                result = "星期二";
                break;
            case 4:
                result = "星期三";
                break;
            case 5:
                result = "星期四";
                break;
            case 6:
                result = "星期五";
                break;
            case 7:
                result = "星期六";
                break;
            default:
                break;
        }
        return result;
    }

    public static Long getDateCompare(String s1, String s2) throws Exception {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);

        //		Cfg.dbug(s1+" : "+s2+" ");

        //		int y=d1.getYear()-d2.getYear();
        //		int m=d1.getMonth()-d2.getMonth();
        //		int d=d1.getDate()-d2.getDate();
        //		int h=d1.getHours()-d2.getHours();
        //		int mu=d1.getMinutes()-d2.getMinutes();
        //		int s=d1.getSeconds()-d2.getSeconds();

        String ds = "";

        //比较
        long timeLong = d1.getTime() - d2.getTime();

        return timeLong;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * dd/MM格式
     *
     * @return
     */
    public static String[] getCurDateAfterWeekDateDM() {
        String[] dates = new String[7];
        for (int i = 1; i <= dates.length; i++) {//往后1-7天
            dates[i - 1] = afterNDayDM(i);
        }
        return dates;
    }

    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static String[] getCurDateAfterWeekDateYMD() {
        String[] dates = new String[7];
        for (int i = 1; i <= dates.length; i++) {//往后1-7天
            dates[i - 1] = afterNDayYMD(i);
        }
        return dates;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static int getWeekOfDateIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return intWeek;
    }

    /**
     * 当前日期前几天或者后几天的日期 dd/MM
     *
     * @param n
     * @return
     */
    public static String afterNDayDM(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, n);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormatStandard = new SimpleDateFormat("dd/MM");
        String s = dateFormatStandard.format(date);
        return s;
    }

    /**
     * 当前日期前几天或者后几天的日期 yyyy-MM-dd
     *
     * @param n
     * @return
     */
    public static String afterNDayYMD(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, n);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormatStandard = new SimpleDateFormat("yyyy-MM-dd");
        String s = dateFormatStandard.format(date);
        return s;
    }

    /**
     * 当前日期前几天或者后几天的日期
     *
     * @param n
     * @return
     */
    public static Date afterNDayDate(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, n);
        Date date = calendar.getTime();
        return date;
    }


    /**
     * 取得指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }


    /**
     * 取得当前日期所在周的第一天
     *
     * @return
     */
    public static Date getFirstDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }


    /**
     * 取得当前日期所在周的最后一天
     *
     * @return
     */
    public static Date getLastDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 取得当前日期所在月的第一天
     *
     * @return
     */
    public static Date getFirstdayofMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }


    /**
     * 取得当前日期所在月的第一天
     *
     * @return
     */
    public static Date getLastdayofMonth() {
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.setTime(getFirstdayofMonth());
        cal.add(Calendar.MONTH, +1);

        cal.setTime(cal.getTime());
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    /**
     * 取得当前日期所在年的第一天
     *
     * @return
     */
    public static Date getFirstdayofYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * 取得当前日期所在月的第一天
     *
     * @return
     */
    public static Date getLastdayofYear() {
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.setTime(getFirstdayofYear());
        cal.add(Calendar.YEAR, +1);

        cal.setTime(cal.getTime());
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    /**
     * 07/18 今天
     */
    public static String getToDay() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM");
        String date = sDateFormat.format(new Date());
        return date;
    }
}
