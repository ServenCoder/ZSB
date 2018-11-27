package com.serven.zsb.toolkit.date;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通用的日期工具类
 * Date: 2018/11/27
 */
public class DateUtils {

    //SimpleDateFormat是线程不安全的，所以采用ThreadLocal解决多线程问题
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();
    private static final Object object = new Object();

    public DateUtils() {
    }

    /**
     *
     * @param pattern
     * @return
     * @throws RuntimeException
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = (SimpleDateFormat) threadLocal.get();
        //单例模式
        if (dateFormat == null) {
            Object var2 = object;
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }

        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }

        return num;
    }

    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }

        return dateString;
    }

    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }

        return myDate;
    }

    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0L;
        Map<Long, long[]> map = new HashMap();
        List<Long> absoluteValues = new ArrayList();
        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); ++i) {
                    for (int j = i + 1; j < timestamps.size(); ++j) {
                        long absoluteValue = Math.abs((Long) timestamps.get(i) - (Long) timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = new long[]{(Long) timestamps.get(i), (Long) timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                long minAbsoluteValue = -1L;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = (Long) absoluteValues.get(0);

                    for (int i = 1; i < absoluteValues.size(); ++i) {
                        if (minAbsoluteValue > (Long) absoluteValues.get(i)) {
                            minAbsoluteValue = (Long) absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1L) {
                    long[] timestampsLastTmp = (long[]) map.get(minAbsoluteValue);
                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
                    }
                }
            } else {
                timestamp = (Long) timestamps.get(0);
            }
        }

        if (timestamp != 0L) {
            date = new Date(timestamp);
        }

        return date;
    }

    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null && getDateStyle(date) != null) {
            isDate = true;
        }

        return isDate;
    }

    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap();
        List<Long> timestamps = new ArrayList();
        DateStyle[] var4 = DateStyle.values();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            DateStyle style = var4[var6];
            if (!style.isShowOnly()) {
                Date dateTmp = null;
                if (date != null) {
                    try {
                        ParsePosition pos = new ParsePosition(0);
                        dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                        if (pos.getIndex() != date.length()) {
                            dateTmp = null;
                        }
                    } catch (Exception var10) {
                        ;
                    }
                }

                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                    map.put(dateTmp.getTime(), style);
                }
            }
        }

        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = (DateStyle) map.get(accurateDate.getTime());
        }

        return dateStyle;
    }

    public static Date StringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return StringToDate(date, dateStyle);
    }

    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception var4) {
                ;
            }
        }

        return myDate;
    }

    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = StringToDate(date, dateStyle.getValue());
        }

        return myDate;
    }

    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception var4) {
                ;
            }
        }

        return dateString;
    }

    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }

        return dateString;
    }

    public static String StringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newPattern);
    }

    public static String StringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newDateStyle);
    }

    public static String StringToString(String date, String olddPattern, String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }

    public static String StringToString(String date, DateStyle olddDteStyle, String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newParttern);
        }

        return dateString;
    }

    public static String StringToString(String date, String olddPattern, DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = StringToString(date, olddPattern, newDateStyle.getValue());
        }

        return dateString;
    }

    public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
        }

        return dateString;
    }

    public static String addYear(String date, int yearAmount) {
        return addInteger((String) date, 1, yearAmount);
    }

    public static Date addYear(Date date, int yearAmount) {
        return addInteger((Date) date, 1, yearAmount);
    }

    public static String addMonth(String date, int monthAmount) {
        return addInteger((String) date, 2, monthAmount);
    }

    public static Date addMonth(Date date, int monthAmount) {
        return addInteger((Date) date, 2, monthAmount);
    }

    public static String addDay(String date, int dayAmount) {
        return addInteger((String) date, 5, dayAmount);
    }

    public static Date addDay(Date date, int dayAmount) {
        return addInteger((Date) date, 5, dayAmount);
    }

    public static String addHour(String date, int hourAmount) {
        return addInteger((String) date, 11, hourAmount);
    }

    public static Date addHour(Date date, int hourAmount) {
        return addInteger((Date) date, 11, hourAmount);
    }

    public static String addMinute(String date, int minuteAmount) {
        return addInteger((String) date, 12, minuteAmount);
    }

    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger((Date) date, 12, minuteAmount);
    }

    public static String addSecond(String date, int secondAmount) {
        return addInteger((String) date, 13, secondAmount);
    }

    public static Date addSecond(Date date, int secondAmount) {
        return addInteger((Date) date, 13, secondAmount);
    }

    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }

    public static int getYear(Date date) {
        return getInteger(date, 1);
    }

    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    public static int getMonth(Date date) {
        return getInteger(date, 2) + 1;
    }

    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    public static int getDay(Date date) {
        return getInteger(date, 5);
    }

    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    public static int getHour(Date date) {
        return getInteger(date, 11);
    }

    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    public static int getMinute(Date date) {
        return getInteger(date, 12);
    }

    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    public static int getSecond(Date date) {
        return getInteger(date, 13);
    }

    public static String getDate(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }

    public static String getDate(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    public static String getTime(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 返回星期
     * @param date
     * @return
     */
    public static WeekUtils getWeek(String date) {
        WeekUtils week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }

        return week;
    }

    public static WeekUtils getWeek(Date date) {
        WeekUtils week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(7) - 1;
        switch (weekNumber) {
            case 0:
                week = WeekUtils.SUNDAY;
                break;
            case 1:
                week = WeekUtils.MONDAY;
                break;
            case 2:
                week = WeekUtils.TUESDAY;
                break;
            case 3:
                week = WeekUtils.WEDNESDAY;
                break;
            case 4:
                week = WeekUtils.THURSDAY;
                break;
            case 5:
                week = WeekUtils.FRIDAY;
                break;
            case 6:
                week = WeekUtils.SATURDAY;
        }

        return week;
    }

    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }

    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = StringToDate(getDate(date), DateStyle.YYYY_MM_DD);
        Date otherDateTmp = StringToDate(getDate(otherDate), DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / 86400000L);
        }

        return num;
    }

    /**
     * 返回农历信息
     * @param date
     * @return
     */
    public static SimpleLunarCalendar getSimpleLunarCalendar(String date) {
        return new SimpleLunarCalendar(StringToDate(date));
    }

    public static SimpleLunarCalendar getSimpleLunarCalendar(Date date) {
        return new SimpleLunarCalendar(date);
    }
}
