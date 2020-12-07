package com.app.toolboxlibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @author: tianyingzhong
 */
public class DateUtil {


    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(date);
        return str;
    }


    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToString(Date date) {
        String str = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            str = format.format(date);
        }

        return str;
    }

    /**
     * 日期转换成字符串
     *
     * @return str
     */
    public static String DateToString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToShortStr(Date date) {
//		"yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    public static String DateToStrForICO(Date date) {
//		"yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = null;
        try {
            if (str != null) {
                date = format.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换成日期,没有时间只有日期
     *
     * @param str
     * @return date
     */
    public static Date ShortStrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param dateStr
     * @param format
     * @return Date
     * @Title: parseDate
     * @Description: 把字符串解析为日期
     */
    public static Date parseDate(String dateStr, String format) {

        Date date = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            date = df.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param dateStr
     * @return Date
     * @Title: parseDate
     * @Description: 把字符串解析为日期
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * @param date
     * @param format
     * @return String
     * @Title: format
     * @Description: 把日期格式化输出为字符串
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * @param date
     * @return
     * @Title: formatDate
     * @Description: 把日期解析为字符串
     */
    public static String formatDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * @param date
     * @return
     * @Title: getYear
     * @Description: 返回当前年
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * @param date
     * @return
     * @Title: getMonth
     * @Description: 返回当前月
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1 ;
    }

    /**
     * @param date
     * @return
     * @Title: getDay
     * @Description:返回当前日
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param date
     * @return
     * @Title: getHour
     * @Description: 返回当前小时
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @param date
     * @return
     * @Title: getMinute
     * @Description: 返回当前分钟
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * @param date
     * @return
     * @Title: getSecond
     * @Description: 返回当前秒
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * @param date
     * @return
     * @Title: getMillis
     * @Description: 返回当前毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * @param date
     * @return
     * @Title: getTime
     * @Description: 返回当前时分秒
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * @param date
     * @return
     * @Title: getDateTime
     * @Description: 返回当前年月日 时分秒
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param date
     * @param day
     * @return
     * @Title: addDate
     * @Description: 增加 几天后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * @param dateStart
     * @param dateEnd
     * @return
     * @Title: diffDate
     * @Description: 两个日期相差几天   前者 减 后者
     */
    public static int diffDate(Date dateStart, Date dateEnd) {
        return (int) ((getMillis(dateStart) - getMillis(dateEnd)) / (24 * 3600 * 1000));
    }

    /**
     * @param strdate
     * @return
     * @Title: getMonthBegin
     * @Description: 返回某天所属月份的第一天
     */
    public static String getMonthBegin(String strdate) {
        Date date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    public static int getMonthBegin(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK)-1 ;
    }

    /**
     * @param strdate
     * @return
     * @Title: getMonthEnd
     * @Description: 返回某天所属月份的最后一天
     */
    public static String getMonthEnd(String strdate) {
        Date date = parseDate(getMonthBegin(strdate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * @return
     * @Title: getGMT8Time
     * @Description: 获取东八区时间
     */
    public static Date getGMT8Time() {
        Date gmt8 = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),
                    Locale.CHINESE);
            Calendar day = Calendar.getInstance();
            day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            day.set(Calendar.DATE, cal.get(Calendar.DATE));
            day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
            day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
            gmt8 = day.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            gmt8 = null;
        }
        return gmt8;
    }

    /**
     * @param month 传入需要返回月份最后一天的月份
     * @return 返回月份最后一天
     */
    public static int getMonthEndDay(int year,int month) {
        int str = 0;
        if (month > 0) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    str = 31;
                    break;
                case 2:
                    if (isLeapyear(year)) {
                        str = 29;
                    } else {
                        str = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    str = 30;
                    break;
            }
        }
        return str;
    }

    /**
     * @param year 传入当前年份，判断是否是闰年
     * @return 返回booolean值
     */
    public static boolean isLeapyear(int year) {
        boolean bo = false;
        if (year > -1) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    bo = true;
                }
            } else {
                if (year % 4 == 0) {
                    bo = true;
                }
            }
        }
        return bo;
    }

    /**
     * 月份或号数补零
     */

    public static String dateTofillero(int month) {
        String string_date = "00";
        if (month > 0) {
            if (month < 10) {
                string_date = "0" + month;
            } else {
                string_date = month + "";
            }
        }
        return string_date;
    }
    public static String dateTofillero(long month) {
        String string_date = "00";
        if (month > 0) {
            if (month < 10) {
                string_date = "0" + month;
            } else {
                string_date = month + "";
            }
        }
        return string_date;
    }

    /**
     * 获取日期数据中的天
     *
     * @param dateStr
     * @return
     */
    public static int getDay(String dateStr) {
        int day = getDay(new Date());
        if (null != dateStr && dateStr.length() >= 8) {
            try {
                day = Integer.valueOf(dateStr.substring(6, 8));
            } catch (Exception ex) {
            }
        }
        return day;
    }

    /**
     * 获取日期数据中的月
     *
     * @param dateStr
     * @return
     */
    public static int getMonth(String dateStr) {
        int month = getMonth(new Date());
        if (null != dateStr && dateStr.length() >= 6) {
            try {
                month = Integer.valueOf(dateStr.substring(4, 6));
            } catch (Exception ex) {
            }
        }
        return month;
    }

    /**
     * 获取日期数据中的月
     *
     * @param dateStr
     * @return
     */
    public static int getYear(String dateStr) {
        int year = getYear(new Date());
        if (null != dateStr && dateStr.length() >= 4) {
            try {
                year = Integer.valueOf(dateStr.substring(0, 4));
            } catch (Exception ex) {
            }
        }
        return year;
    }

    public static String getDigitsOnlyString(String numString) {
        StringBuilder sb = new StringBuilder();
        for (char c : numString.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Date getDateForString(String dateString) {
        String digitsOnly = getDigitsOnlyString(dateString);
        SimpleDateFormat validDate = getDateFormatForLength(digitsOnly.length());
        if (validDate != null)
            try {
                validDate.setLenient(false);
                Date date = validDate.parse(digitsOnly);
                return date;
            } catch (ParseException pe) {
                return null;
            }
        return null;
    }

    public static SimpleDateFormat getDateFormatForLength(int len) {
        if (len == 4) {
            return new SimpleDateFormat("MMyy");
        } else if (len == 6) {
            return new SimpleDateFormat("MMyyyy");
        } else
            return null;
    }


    /**
     * 获取当前时间 并格式化为 20180103
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }



    /**
     * 将秒数转换为日时分秒，
     * @param second
     * @return
     */
    public static String secondToTime(long second){
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        if(days>0){
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        }else if(hours>0){
            return hours + "小时" + minutes + "分" + second + "秒";
        }else if(minutes>0){
            return minutes + "分" + second + "秒";
        }
        return second + "秒";
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static List<String> backPastTime(int intervals ) {
        List<String> pastDaysList = new ArrayList<>();
        for (int i = 1 ; i <intervals+1; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 计算两个日期之间的时间差
     * @param beginDateStr yyyy-MM-dd
     * @param endDateStr yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String beginDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse(beginDateStr);
        Date endDate = sdf.parse(endDateStr);
        int days = differentDays(beginDate, endDate);
        return days;
    }

    //计算时间差
    private static int differentDays(Date beginDate, Date endDate) {
        Calendar beginDateCalendar = Calendar.getInstance();
        beginDateCalendar.setTime(beginDate);
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTime(endDate);

        int beginDateDay = beginDateCalendar.get(Calendar.DAY_OF_YEAR);
        int endDateDay = endDateCalendar.get(Calendar.DAY_OF_YEAR);

        int beginDateMonth = beginDateCalendar.get(Calendar.MONTH);

        int beginDateYear = beginDateCalendar.get(Calendar.YEAR);
        int endDateYear = endDateCalendar.get(Calendar.YEAR);

        if (beginDateYear != endDateYear) {
            //不同年
            int timeDistance = 0;
            for (int i = beginDateYear; i < endDateYear; i++) { //闰年
                if (i % 4 == 0 && i % 100 != 0 ||
                        i % 400 == 0) {
                    timeDistance += 366;
                } else { // 不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (endDateDay - beginDateDay);
        } else {// 同年
            return (endDateDay - beginDateDay)>0? endDateDay - beginDateDay :
                    endDateDay - beginDateDay + DateUtil.getMonthEndDay(beginDateYear,beginDateMonth) ;
        }
    }


    public static String formatNetDate(String string) {
        if(null != string && string.length()>10){
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
            String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
            String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
            if("1".equals(mWay)){
                mWay ="日";
            }else if("2".equals(mWay)){
                mWay ="一";
            }else if("3".equals(mWay)){
                mWay ="二";
            }else if("4".equals(mWay)){
                mWay ="三";
            }else if("5".equals(mWay)){
                mWay ="四";
            }else if("6".equals(mWay)){
                mWay ="五";
            }else if("7".equals(mWay)){
                mWay ="六";
            }
            String s = string.substring(0,10);
            s = s.substring(0,4)+"年"+s.substring(5,7)+"月"+s.substring(8)+"日"+"  星期"+mWay ;
            return s ;
        }
        return "" ;
    }

    public static String backStatus(String startTime,String endTime,String thisTime){
        String status = "-1";
        try {
            int startNum =  daysBetween(thisTime,startTime);
            int endNum =  daysBetween(thisTime,endTime);
            LogUtil.showLog("startNum",""+startNum);
            LogUtil.showLog("endNum",""+endNum);
            if( startNum >= 0 ){
                status = "0";
            }else if(startNum>=0 && endNum>=0){
                status = "1";
            }else if(endNum<0){
                status = "2";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return status ;
    }
}