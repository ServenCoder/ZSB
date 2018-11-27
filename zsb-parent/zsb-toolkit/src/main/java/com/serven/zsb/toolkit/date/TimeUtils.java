package com.serven.zsb.toolkit.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/27
 */
public class TimeUtils {
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "hh:mm";
    public static final String FORMAT_DATE_TIME_24 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE_TIME_12 = "yyyy-MM-dd hh:mm";
    public static final String FORMAT_MONTH_DAY_TIME_24 = "MM月dd日 HH:mm";
    public static final String FORMAT_MONTH_DAY_TIME_12 = "MM月dd日 hh:mm";
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();
    public static final int YEAR = 31536000;
    public static final int MONTH = 2592000;
    public static final int DAY = 86400;
    public static final int HOUR = 3600;
    public static final int MINUTE = 60;
    private static final Object object = new Object();

    public TimeUtils() {
    }

    /**
     * @return
     * @throws RuntimeException
     */
    private static SimpleDateFormat getDateFormat() throws RuntimeException {
        SimpleDateFormat dateFormat = (SimpleDateFormat) threadLocal.get();
        //单例模式
        if (dateFormat == null) {
            Object var2 = object;
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat();
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }

        return dateFormat;
    }

    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000L;
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > 31536000L) {
            timeStr = timeGap / 31536000L + "年前";
        } else if (timeGap > 2592000L) {
            timeStr = timeGap / 2592000L + "个月前";
        } else if (timeGap > 86400L) {
            timeStr = timeGap / 86400L + "天前";
        } else if (timeGap > 3600L) {
            timeStr = timeGap / 3600L + "小时前";
        } else if (timeGap > 60L) {
            timeStr = timeGap / 60L + "分钟前";
        } else {
            timeStr = "刚刚";
        }

        return timeStr;
    }

    public static Long getDayAfterNow(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000L;
        Long dayAfter = null;
        dayAfter = timeGap / 86400L;
        return dayAfter;
    }

    public static String getFormatTimeFromTimestamp(long timestamp, String format) {
        SimpleDateFormat sdf;
        if (format != null && !format.trim().equals("")) {
            sdf = getDateFormat();
            sdf.applyPattern(format);
        } else {
            sdf = getDateFormat();
            sdf.applyPattern("yyyy-MM-dd");
            int currentYear = Calendar.getInstance().get(1);
            int year = Integer.valueOf(sdf.format(new Date(timestamp)).substring(0, 4));
            System.out.println("currentYear: " + currentYear);
            System.out.println("year: " + year);
            if (currentYear == year) {
                sdf.applyPattern("MM月dd日 HH:mm");
            } else {
                sdf.applyPattern("yyyy-MM-dd HH:mm");
            }
        }

        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public static String getMixTimeFromTimestamp(long timestamp, long partionSeconds, String format) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000L;
        return timeGap <= partionSeconds ? getDescriptionTimeFromTimestamp(timestamp) : getFormatTimeFromTimestamp(timestamp, format);
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf;
        if (format != null && !format.trim().equals("")) {
            sdf = getDateFormat();
            sdf.applyPattern(format);
        } else {
            sdf = getDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm");
        }

        return sdf.format(new Date());
    }

    public static Date getTimeFromString(String timeStr, String format) {
        SimpleDateFormat sdf;
        if (format != null && !format.trim().equals("")) {
            sdf = getDateFormat();
            sdf.applyPattern(format);
        } else {
            sdf = getDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm");
        }

        try {
            return sdf.parse(timeStr);
        } catch (ParseException var3) {
            return new Date();
        }
    }

    public static String getStringFromTime(Date date, String format) {
        SimpleDateFormat sdf;
        if (format != null && !format.trim().equals("")) {
            sdf = getDateFormat();
            sdf.applyPattern(format);
        } else {
            sdf = getDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm");
        }

        return sdf.format(date);
    }

    public static long getTimestampAfterHours(int hours) {
        return System.currentTimeMillis() + (long) (hours * 3600) * 1000L;
    }

    public static long getTimestampAfterDays(int days) {
        return System.currentTimeMillis() + (long) (days * 86400) * 1000L;
    }

    public static long getTimestampAfterDays(long timestamp, int days) {
        return timestamp + (long) (days * 86400) * 1000L;
    }

    public static long getTimestampBeforeDays(long timestamp, int days) {
        return timestamp - (long) (days * 86400) * 1000L;
    }

    public static long getTimestampAfterHours(long timestamp, int hours) {
        return timestamp + (long) (hours * 3600) * 1000L;
    }

    public static long getTimestampAfterMinutes(int minutes) {
        return System.currentTimeMillis() + (long) (minutes * 60 * 1000);
    }

    public static long getTimestampAfterMinutes(long timeStamp, int minutes) {
        return timeStamp + (long) (minutes * 60 * 1000);
    }

    public static long getTimeStampBeginingOfDate(Long timeStamp) throws Exception {
        SimpleDateFormat sdf = getDateFormat();
        return sdf.parse(getFormatTimeFromTimestamp(timeStamp, "yyyy-MM-dd")).getTime();
    }

    public static long getTimeStampEndOfDate(Long timeStamp) throws Exception {
        SimpleDateFormat sdf = getDateFormat();
        String time = getFormatTimeFromTimestamp(System.currentTimeMillis(), "yyyy-MM-dd");
        return getTimestampAfterHours(sdf.parse(time).getTime(), 24) - 1000L;
    }

    public static long getMonthTimeStampFromTimeStr(String time) throws Exception {
        Date date = getTimeFromString(time, "yyyy-MM");
        return date.getTime();
    }

    public static long getAMonthLaterTimeStampFromTimeStr(String time) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getTimeFromString(time, "yyyy-MM"));
        calendar.set(2, calendar.get(2) + 1);
        return calendar.getTime().getTime();
    }

    public static boolean isWeekend(Long timeStamp) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        Integer day = calendar.get(7);
        return day == 7 || day == 1;
    }

    public static boolean isSaturday(Long timeStamp) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        Integer day = calendar.get(7);
        return day == 7;
    }

    public static boolean isSunday(Long timeStamp) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        Integer day = calendar.get(7);
        return day == 1;
    }
}
