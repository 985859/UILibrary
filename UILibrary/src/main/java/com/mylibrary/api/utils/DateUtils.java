package com.mylibrary.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/5/26.
 * 日期处理
 */

public class DateUtils {

    /**
     * @param
     * @return 单位毫秒  获取系统时间的10位的时间戳
     * @author: hukui
     * @date: 2019/9/9
     * @description 得到当前时间
     */
    public static long getLongTime() {
        return System.currentTimeMillis();
    }

    /**
     * 得到指定时间
     *
     * @param dateFormat 时间格式 yyyy-MM-dd HH:mm:ss
     * @return 默认 得到当前时间
     * @author: hukui
     * @date: 2019/9/9
     */
    public static String getTime(String dateFormat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getTime(int year, int month, int day, String dateFormat) {
        return getTime(year, month, day, 0, 0, 0, dateFormat);
    }

    public static String getTime(int year, int month, int day, int hourOfDay, int minute, String dateFormat) {
        return getTime(year, month, day, hourOfDay, minute, 0, dateFormat);
    }

    public static String getTime(int year, int month, int day, int hourOfDay, int minute, int second, String dateFormat) {
        Calendar calendar = Calendar.getInstance();//系统当前时间
        calendar.clear();
        calendar.set(year, month, day, hourOfDay, minute, second);
        Date currentTime = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static int getYear() {
        int year = 0;
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        return year;
    }

    public static int getMonth() {
        int month = 0;
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        return month;
    }

    public static int getDay() {
        int day = 0;
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getHour() {
        int hour = 0;//0-23
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static int getSecond() {
        int hour = 0;//0-23
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.SECOND);
        return hour;
    }

    /**
     * 时间字符串戳转换成置顶的格式字符串
     *
     * @param time       字符串型日期 符合时间格式 例如 2019/9/9
     * @param formatDate 要转化的时间格式 yyyy-MM-dd HH:mm:ss
     * @return 格式化的系统时间
     * @author: hukui
     */
    public static String formarString(String time, String formatDate) {
        if (StringUtil.isNotEmpty(time)) {
            if (StringUtil.isEmpty(formatDate)) {
                formatDate = "yyyy-MM-dd HH:mm:ss";
            }
            Date d = new Date(time);
            SimpleDateFormat sf = new SimpleDateFormat(formatDate);
            return sf.format(d);
        }
        return "";
    }

    /**
     * 时间戳转换成字符串
     *
     * @param dateFormat 时间格式 yyyy-MM-dd HH:mm:ss
     * @param time       时间戳
     * @return 格式化的系统时间
     * @author: hukui
     */
    public static String formarLongToString(long time, String dateFormat) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateForma 时间格式 yyyy-MM-dd HH:mm:ss
     * @param time      字符串型日期 例如 2019/9/9
     * @return 转换后的10位时间戳
     * @author: hukui
     */
    public static long formarStringToLong(String time, String dateForma) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateForma);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 将字符串型日期转换成日期
     *
     * @param time       字符串型日期
     * @param dateFormat 日期格式
     * @return 日期
     * @author: hukui
     */
    public static Date formarStringToDate(String time, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期转字符串
     *
     * @param date       日期
     * @param dateFormat 时间格式 yyyy-MM-dd HH:mm:ss
     * @return 日期
     * @author: hukui
     */
    public static String formarDateToString(Date date, String dateFormat) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 日期转字符串
     *
     * @param date 日期
     * @return 时间戳
     * @author: hukui
     */
    public static long formarDateToLong(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * 日期转字符串
     *
     * @param l 时间戳
     * @return 日期
     * @author: hukui
     */
    public static Date formarlongToDate(long l) {
        Date date = new Date(l);
        return date;
    }

    /**
     * @return 返回年月日 时分秒的数组
     */

    public static int[] get() {
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0; // 0-23
        int minute = 0;
        int second = 0;
        Calendar c = Calendar.getInstance();
        //取得系统日期:
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        //取得系统时间：
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);
        int[] t = {year, month, day, hour, minute, second};
        return t;
    }


    /**
     * 两个时间点的间隔时长（分钟）
     *
     * @param before 开始时间
     * @param after  结束时间
     * @return 两个时间点的间隔时长
     */
    public static long compareDateInterval(Date before, Date after) {
        if (before == null || after == null) {
            return 0l;
        }
        long dif = 0;
        if (after.getTime() >= before.getTime()) {
            dif = after.getTime() - before.getTime();
        } else if (after.getTime() < before.getTime()) {
            dif = after.getTime() + 86400000 - before.getTime();
        }
        dif = Math.abs(dif);
        return dif / 60000;
    }

    /**
     * 获取指定时间间隔分钟后的时间
     *
     * @param date 指定的时间
     * @param min  间隔分钟数
     * @return 间隔分钟数后的时间
     */
    public static Date addMinutes(Date date, int min) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * 根据时间返回指定术语 可自行调整
     *
     * @param hourday 小时
     * @return
     */
    public static String showTimeView(int hourday) {
        if (hourday >= 19 && hourday <= 24) {
            return "晚上";
        } else if (hourday >= 0 && hourday <= 6) {
            return "凌晨";
        } else if (hourday > 6 && hourday <= 12) {
            return "上午";
        } else if (hourday > 12 && hourday < 19) {
            return "下午";
        }
        return null;
    }

    /**
     * 将秒数转换为日时分秒，
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        second = second / 1000;
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数
        String h = "";
        if (hours < 10) {
            h = "0" + hours;
        } else {
            h = "" + hours;
        }
        String m = "";
        if (minutes < 10) {
            m = "0" + minutes;
        } else {
            m = "" + minutes;
        }
        String s = "";
        if (second < 10) {
            s = "0" + second;
        } else {
            s = "" + second;
        }
        return h + ":" + m + ":" + s;
    }

    /**
     * @param date1 >date2 返回 1
     * @param date2 >date1 返回 -1 相等 返回0
     * @return
     * @author hukui
     * @time 2020/11/16
     * @Description 比较连个日期 是否相等  只比较日期不比较时间
     */
    public static int compareData(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        int year1;
        int month1;
        int day1;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date1);

        year1 = cal.get(Calendar.YEAR);
        month1 = cal.get(Calendar.MONTH);
        day1 = cal.get(Calendar.DAY_OF_MONTH);
        int year2;
        int month2;
        int day2;

        Calendar cal2 = Calendar.getInstance();
        cal2.clear();
        cal2.setTime(date2);

        year2 = cal2.get(Calendar.YEAR);
        month2 = cal2.get(Calendar.MONTH);
        day2 = cal2.get(Calendar.DAY_OF_MONTH);

        if (year1 > year2) {
            return 1;
        } else if (year1 < year2) {
            return -1;
        } else {
            if (month1 > month2) {
                return 1;
            } else if (month1 < month2) {
                return -1;
            } else {

                if (day1 > day2) {
                    return 1;
                } else if (day1 < day2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }


    }
}
